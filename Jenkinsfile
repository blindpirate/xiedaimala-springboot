String buildNumber = env.BUILD_NUMBER;
String timestamp = new Date().format('yyyyMMddHHmmss');
String projectName = env.JOB_NAME.split(/\//)[0];
// e.g awesome-project/release/RELEASE-1.0.0
String branchName = env.JOB_NAME.split(/\//)[1..-1].join(/\//);

println("${buildNumber} ${timestamp} ${projectName}");

String version = "${buildNumber}-${timestamp}-${projectName}";

node {
    checkout scm;

    if(params.BuildType=='Rollback') {
        return deploy()
    } else if(params.BuildType=='Normal'){
        return normalCIBuild(version)
    } else if(branchName == 'master'){
        setScmPollStrategyAndBuildTypes(['Normal', 'Rollback']);
    }
}

def normalCIBuild(String version) {
    stage('test')

    sh('./mvnw clean test')

    stage('package')

    sh("./mvnw package && docker build . -t 47.103.59.183:5000/xdml-springboot:${version}")

    //stage('deploy')

    //input 'deploy?'
}

def deployVersion() {
}

def setScmPollStrategyAndBuildTypes(List buildTypes) {
    def propertiesArray = [
            parameters([choice(choices: buildTypes.join('\n'), description: '', name: 'BuildType')]),
            pipelineTriggers([[$class: "SCMTrigger", scmpoll_spec: "* * * * *"]])
    ];
    properties(propertiesArray);
}

def deploy() {
}
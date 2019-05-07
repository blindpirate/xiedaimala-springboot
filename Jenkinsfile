String buildNumber = env.BUILD_NUMBER;
String timestamp = new Date().format('yyyyMMddHHmmss');
String projectName = env.JOB_NAME.split(/\//)[0];

println("${buildNumber} ${timestamp} ${projectName}");

String version = "${buildNumber}-${timestamp}-${projectName}";

node {
    checkout scm;

    def buildType = parameterOrDefault(defaultValue: 'Init', parameter: 'BuildType');

    if(buildType=='Deploy') {
        return deploy()
    } else {
        return normalCIBuild(version)
    }
}

def normalCIBuild(String version) {
    setScmPollStrategyAndBuildTypes(['Normal', 'DeployTest']);

    if ('Init' == parameterOrDefault(defaultValue: 'Init', parameter: 'BuildType')) {
        return;
    }

    stage('test')

    sh('./mvnw test')

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
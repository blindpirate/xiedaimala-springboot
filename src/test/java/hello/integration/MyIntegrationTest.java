package hello.integration;

//import hello.Application;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.env.Environment;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import javax.inject.Inject;
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "classpath:test.properties")
public class MyIntegrationTest {
//    @Inject
//    Environment environment;
//
//    @Test
//    public void notLoggedInByDefault() throws IOException, InterruptedException {
//
//        String port = environment.getProperty("local.server.port");
//        System.out.println(port);
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("http://localhost:" + port + "/auth"))
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println(response.statusCode());
//        System.out.println(response.body());
//
//        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertTrue(response.body().contains("用户没有登录"));
//    }
}

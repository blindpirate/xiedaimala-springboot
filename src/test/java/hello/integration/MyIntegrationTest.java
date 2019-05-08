package hello.integration;

import hello.Application;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class MyIntegrationTest {
    @Inject
    Environment environment;

    @Test
    public void notLoggedInByDefault() throws IOException {
        String port = environment.getProperty("local.server.port");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://localhost:" + port + "/auth");
            httpclient.execute(httpget, (ResponseHandler<String>) httpResponse -> {
                Assertions.assertEquals(200, httpResponse.getStatusLine().getStatusCode());
                Assertions.assertTrue(EntityUtils.toString(httpResponse.getEntity()).contains("用户没有登录"));
                return null;
            });
        } finally {
            httpclient.close();
        }
    }
}

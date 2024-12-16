package qa.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CreateAuthToken {

    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    @BeforeTest
    public  void setup(){
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    @AfterTest
    public void tearDown() {playwright.close();}

    @Test
    public void createAuthToken() throws IOException {

        Properties prop = new Properties();

        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//utils//data.properties");
        prop.load(fis);
        String endPoint = prop.getProperty("authEndPoint");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("password", "admin");
        data.put("username", "admin");
        APIResponse apiAuthTokenResponse = requestContext.post(endPoint,
                RequestOptions.create()

                        .setData(data));

        System.out.println(" Status code is " + apiAuthTokenResponse.status());

        Assert.assertEquals(apiAuthTokenResponse.status(), 200);

        System.out.println(apiAuthTokenResponse.text());

        System.out.println("---Print api body ------");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiAuthTokenResponse.body());

        String jsonPrettyResponse = jsonResponse.toPrettyString();

        System.out.println(jsonPrettyResponse);
    }
}

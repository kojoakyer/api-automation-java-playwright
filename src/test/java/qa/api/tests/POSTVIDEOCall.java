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


public class POSTVIDEOCall {

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
    public void createVideo() throws IOException {


        Properties prop = new Properties();

        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//utils//data.properties");
        prop.load(fis);
        String endPoint = prop.getProperty("endpoint");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("category", "Sports");
        data.put("name", "McLaren");
        data.put("releaseDate", "2013-09-04");
        data.put("reviewScore", 90);
        APIResponse apiPostResponse = requestContext.post(endPoint,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(data));

        System.out.println(apiPostResponse.status());

        Assert.assertEquals(apiPostResponse.status(), 200);

        Assert.assertEquals(apiPostResponse.statusText(), "OK");

        System.out.println(apiPostResponse.text());

        System.out.println("---Print api body ------");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiPostResponse.body());

        String jsonPrettyResponse = jsonResponse.toPrettyString();

        System.out.println(jsonPrettyResponse);

    }

}

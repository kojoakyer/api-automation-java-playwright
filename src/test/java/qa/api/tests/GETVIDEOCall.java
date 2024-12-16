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
import java.util.Properties;

public class GETVIDEOCall {


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
    public void getSingleVideo() throws IOException {

        Properties prop = new Properties();

        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//utils//data.properties");
        prop.load(fis);
        String endPoint = prop.getProperty("endpoint");
        APIResponse apiResponse = requestContext.get(endPoint,
                RequestOptions.create()
                        .setQueryParam("id", 2)
        );

        int statusCode = apiResponse.status();

        String statusText = apiResponse.text();

        System.out.println("Response status code is " + statusCode);
        System.out.println("Response text code is " + statusText);

        Assert.assertEquals(statusCode, 200);
        Assert.assertTrue(apiResponse.ok());

        System.out.println("---Print api body ------");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());

        String jsonPrettyResponse = jsonResponse.toPrettyString();

        System.out.println(jsonPrettyResponse);
    }



}



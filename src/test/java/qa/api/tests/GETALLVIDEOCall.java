package qa.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class GETALLVIDEOCall {


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
    public void getAllVideos() throws IOException {

        Properties prop = new Properties();

        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//utils//data.properties");
        prop.load(fis);
        String endPoint = prop.getProperty("endpoint");
        APIResponse apiResponse = requestContext.get(endPoint);

        int statusCode = apiResponse.status();

        String statusText = apiResponse.statusText();

        System.out.println("response status code is" + statusCode);
        Assert.assertEquals(statusCode, 200);
        Assert.assertTrue(apiResponse.ok());
        System.out.println("response text code is" + statusText);

        System.out.println(Arrays.toString(apiResponse.body()));

        System.out.println("---Print api body ------");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());

        String jsonPrettyResponse = jsonResponse.toPrettyString();

        System.out.println(jsonPrettyResponse);

        System.out.println("---Print api url ------");
        System.out.println(apiResponse.url());

        System.out.println("---Print api headers ------");
        Map<String, String> headersMap = apiResponse.headers();
        System.out.println(headersMap);
        Assert.assertEquals(headersMap.get("content-type"), "application/json");



    }


}

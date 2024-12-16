package qa.api.tests;

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

public class DELETEVIDEOCall {

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
    public void deleteVideo() throws IOException {

        Properties prop = new Properties();

        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//utils//data.properties");
        prop.load(fis);
        String endPoint = prop.getProperty("endpoint");

        APIResponse apiDeleteResponse = requestContext.delete(endPoint,
                RequestOptions.create()
                        .setQueryParam("id", 2)
        );

        int statusCode = apiDeleteResponse.status();

        String statusText = apiDeleteResponse.text();

        System.out.println("Response status code is " + statusCode);
        System.out.println("Response text code is " + statusText);

        Assert.assertEquals(statusCode, 200);
        Assert.assertTrue(apiDeleteResponse.ok());


        //Try to get the user
        System.out.println("---Print get user userId - 404 ------");
        APIResponse apiResponse = requestContext.get(endPoint,
                RequestOptions.create()
                        .setQueryParam("id", 2)
        );

        int statusCode2 = apiResponse.status();

        String statusText2 = apiResponse.statusText();

        System.out.println("Response status code is " + statusCode2);
        System.out.println("Response text code is " + statusText2);

        Assert.assertEquals(statusCode2, 404);
        Assert.assertEquals(apiResponse.statusText(), "Not Found");



    }
}

package test;

import io.restassured.specification.RequestSpecification;
import model.RequestCapbility;
import model.TokenHender;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import static io.restassured.RestAssured.given;

public class BaseTest implements RequestCapbility {
    protected String baseUri;
    protected String accessToken;
    protected String bookPathPrefix;

    @BeforeSuite
    public void beforeSuite() {

        System.out.println(accessToken);
        String baseUriEnv = System.getProperty("baseUri");
        if (baseUriEnv != null) {
            baseUri = baseUriEnv;
        }
        if(baseUriEnv.isEmpty()){
            throw new RuntimeException("Please support base URL");
        }
           System.out.println(baseUriEnv);
        bookPathPrefix = "/orders";
        accessToken = TokenHender.getToken(baseUri);
    }

}

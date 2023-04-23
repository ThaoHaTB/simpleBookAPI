package test;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BookAPICUD extends BaseTest {
    String orderId="";
    RequestSpecification request;
    ResponseBody body;
    Response response;
    @BeforeClass
    public void setUp(){
        request = given();
        request.header(defaultHeader);
        request.header(getAuthenticateHeader.apply(accessToken));
    }
    @Test(priority = 1)
    public void TC_001_SubmitAnOderSuccessfully() {
        HashMap<String, Object> dataBody = new HashMap<String, Object>();
        Faker faker = new Faker();
        dataBody.put("bookId", 1);
        dataBody.put("customerName", faker.name().fullName());
        response = request.body(dataBody).post(bookPathPrefix);
        body = response.getBody();
        JsonPath jsp = new JsonPath(body.asString());
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 201 Created");
        Assert.assertEquals(jsp.getString("created"), "true");
        orderId = jsp.getString("orderId");
    }

    @Test
    public void TC_002_SubmitAnOderInvalidBookID() {
        HashMap<String, Object> dataBody = new HashMap<String, Object>();
        Faker faker = new Faker();
        dataBody.put("bookId", "abcd");
        dataBody.put("customerName", faker.name().fullName());
        response = request.body(dataBody).post("/orders");
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 400 Bad Request");
         body = response.getBody();
        JsonPath jsp = new JsonPath(body.asString());
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 400 Bad Request");
        Assert.assertEquals(jsp.getString("error"), "Invalid or missing bookId.");
    }
    @Test(priority = 2)
    public void TC_003_updateOrderSuccessfully(){
        HashMap<String, Object> dataBody = new HashMap<String, Object>();
        Faker faker = new Faker();
        dataBody.put("customerName", faker.name().fullName());
        String updatePath = "/orders/" + orderId;
        response =request.body(dataBody).patch(updatePath);
        Assert.assertEquals(response.statusLine(),"HTTP/1.1 204 No Content");
    }
    @Test(priority = 3)
    public void TC_004_deleteOrderSuccessfully() {
        String updatePath = "/orders/" +orderId;
        response = request.delete(updatePath);
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 204 No Content");
    }
}

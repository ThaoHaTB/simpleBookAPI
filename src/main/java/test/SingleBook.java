package test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SingleBook extends BaseTest{
    @Test()
    public void getABook() {
        RestAssured.given().when().get("/books/2").then().log().all()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8")
                .body("id", Matchers.equalTo(2)).body("name", Matchers.equalTo("Just as I Am"))
                .body("author", Matchers.equalTo("Cicely Tyson")).body("price", Matchers.equalTo(20.33F))
                .body("available", Matchers.equalTo(false));
    }

    @Test()
    public void getABookWithInvalidID() {
        RestAssured.given().when().get("/books/30").then().log().all()
                .statusCode(404)
                .header("Content-Type", "application/json; charset=utf-8").header("Content-Length", "30").assertThat()
                .body("error", Matchers.equalTo("No book with id 30"));
    }

    @Test()
    public void getABookAndExtractResponse() {
        Response response = RestAssured.given().when().get("/books/2").then().extract().response();

        // Validate status line
        System.out.println("Status received => " + response.getStatusLine());
        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK", "Correct status code returned");

        // Validate status code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        // Validate header
        String contentType = response.header("Content-Type");
        Assert.assertEquals(contentType, "application/json; charset=utf-8");

        // Body
        ResponseBody body = response.getBody();
        JsonPath jsp = new JsonPath(body.asString());
        Assert.assertEquals(Integer.parseInt(jsp.getString("id")), 2);
        Assert.assertEquals(jsp.getString("author"), "Cicely Tyson", "Correct author");

    }
}

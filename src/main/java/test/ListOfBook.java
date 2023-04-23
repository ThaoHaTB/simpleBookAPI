package test;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ListOfBook extends BaseTest {
    @Test()
    public void TC_005_getAListOfBook_200() {
        RestAssured.given().when().get("/books").then().log().all().statusCode(200);
    }

    @Test(priority=2)
    public void TC_006_getAListOfBook_200_OptionalQueryParameters() {
        RestAssured.given().when().get("/books?type=fiction&limit=1").then().log().all().statusCode(200)
                .body("find{it.id=1}.type", Matchers.equalTo("fiction"));
    }

    @Test(priority=3)
    public void TC_007_getAListOfBook_400_InccorrectType() {
        RestAssured.given().when().get("/books?type=fict&limit=1").then().log().all()
                .statusCode(400).body(
                        "error",
                        Matchers.equalTo("Invalid value for query parameter 'type'. Must be one of: fiction, non-fiction."));
    }
}

package model;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static model.RequestCapbility.defaultHeader;

public class TokenHender {
    public static String getToken(String baseUri) {
        RestAssured.baseURI = baseUri;
        RequestSpecification request = given();
        request.header(defaultHeader);
        Faker faker = new Faker();
        HashMap<String, Object> dataBody = new HashMap<String, Object>();
        dataBody.put("clientName", faker.name().fullName());
        dataBody.put("clientEmail", faker.internet().emailAddress());
        Response response = request.body(dataBody).post("/api-clients/");
        ResponseBody body = response.getBody();
        JsonPath jsp = new JsonPath(body.asString());
        String token= jsp.getString("accessToken");
        return token;
    }
}

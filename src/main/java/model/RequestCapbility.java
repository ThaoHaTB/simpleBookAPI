package model;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.function.Function;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public interface RequestCapbility {
    Header defaultHeader = new Header("Content-type", "application/json;charset=UTF-8");

    Function<String, Header> getAuthenticateHeader = accessToken -> {
        if (accessToken == null) {
            throw new IllegalArgumentException("[Error] Token can't be null");
        }
        return new Header("Authorization", "Bearer " + accessToken);

    };
}

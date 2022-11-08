package util;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RestAssuredUtil {

    public static void setBaseURI() {
        RestAssured.baseURI = "http://localhost:8080/";
    }


    public static void setBasePath(String basePathTerm) {
        RestAssured.basePath = basePathTerm;
    }


    public static void resetBaseURI() {
        RestAssured.baseURI = null;
    }


    public static void resetBasePath() {
        RestAssured.basePath = null;
    }


    public static void setContentType(ContentType Type) {
        given().contentType(Type);
    }

    public static JsonPath getJsonPath(Response res) {
        String json = res.asString();
        return new JsonPath(json);
    }
}
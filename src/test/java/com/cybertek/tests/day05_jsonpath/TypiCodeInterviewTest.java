package com.cybertek.tests.day05_jsonpath;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class TypiCodeInterviewTest {
    @BeforeAll
    public static void setUp() {
        baseURI = "https://jsonplaceholder.typicode.com";
    }
    /**
     * {
     *     "id": 1,
     *     "name": "Leanne Graham",
     *     "username": "Bret",
     *     "email": "Sincere@april.biz",
     *     "address": {
     *         "street": "Kulas Light",
     *         "suite": "Apt. 556",
     *         "city": "Gwenborough",
     *         "zipcode": "92998-3874",
     *         "geo": {
     *             "lat": "-37.3159",
     *             "lng": "81.1496"
     *         }
     *     },
     *     "phone": "1-770-736-8031 x56442",
     *     "website": "hildegard.org",
     *     "company": {
     *         "name": "Romaguera-Crona",
     *         "catchPhrase": "Multi-layered client-server neural-net",
     *         "bs": "harness real-time e-markets"
     *     }
     * }
     */

    @Test
    public void getUserTest() {
        Response response = given().accept(ContentType.JSON).when().get("/users/1");

        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(200,response.statusCode());
        response.prettyPrint();

        // convert response body to Jsonpath
        JsonPath json = response.jsonPath();

        System.out.println("name = " + json.getString("name"));
        System.out.println("city = " + json.getString("address.city"));

        // print company name

        System.out.println("company.name = " + json.getString("company.name"));

        // to print lng
         String lng =  json.getString("address.geo.lng");
        System.out.println("address.geo = " + json.getString("address.geo.lng"));
        System.out.println("lng = " + lng);

    }
}

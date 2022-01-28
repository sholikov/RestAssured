package com.cybertek.tests.day03_api_parameters;

import com.cybertek.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpartanWithApiParamsTest {

    @BeforeAll
    public static void  setUp(){
        baseURI= ConfigurationReader.getProperty("spartan.url");

    }

    /**
     * Given Accept type is json
     * And path parameter id is 24
     * When I send request to /api/spartans/24
     * -----
     * Then status code should be 200
     * And content type should be "application/json"
     * and json body should contain "Correy"
     */
    @Test
    public void getSpartanPathParamTest() {
        Response response = given().accept(ContentType.JSON).// add header to request
                when().get(baseURI+ "/api/spartans/24");
        System.out.println("status code = " + response.statusCode());
        assertEquals(200, response.statusCode());

        System.out.println("content type = " + response.contentType());
        assertEquals("application/json",response.contentType());
        response.prettyPrint();
        assertTrue(response.asString().contains("Julio"));

    }
    /**
     * Given Accept type is json
     * And path parameter id is 1000
     * When I send request to /api/spartans/1000
     * ----------------------------------------- always we should divide into 2, sending request and respond
     * Then status code should be 404
     * And content type should be "application/json"
     * and json body should contain "Not Found"
     */
    @Test
    public void getSpartanPathParamNegativeTest() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",1000)
                .when().get("/api/spartans/{id}");

        System.out.println("status line = " + response.statusLine());
        assertEquals(404, response.statusCode());

        System.out.println("content type = " + response.contentType());
        assertEquals("application/json", response.contentType());

        response.prettyPrint();
        assertTrue(response.asString().contains("Not Found"));


    }
}

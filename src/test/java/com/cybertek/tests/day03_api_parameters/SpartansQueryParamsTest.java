package com.cybertek.tests.day03_api_parameters;

import com.cybertek.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpartansQueryParamsTest {

    @BeforeAll
    public static void setUp() {
        System.out.println("Set Up Method: assigning value to baseURI variable");
        baseURI = ConfigurationReader.getProperty("spartan.url");


    }

    /**
     * Given accept type is Json
     * And query parameter values are:
     * gender|Female
     * nameContains|e
     * When user sends GET request to /api/spartans/search
     * Then response status code should be 200
     * And response content-type: application/json
     * And "Female" should be in response body
     * And "Janette" should be in response body
     */
    @Test
    public void searchForSpartanQueryTest() {
        Response response = given().accept(ContentType.JSON).and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "e").when().get("/api/spartans/search");

        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(200,response.statusCode());

        System.out.println("response.contentType() = " + response.contentType());
        assertEquals("application/json", response.contentType());
        System.out.println(response.prettyPrint());

        assertTrue(response.asString().contains("Female"));
        assertTrue(response.asString().contains("Janette"));


    }
}

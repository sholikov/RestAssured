package com.cybertek.tests.homeworks1;

import com.cybertek.tests.ORDSTestBase;
import com.cybertek.tests.ZippopotamTestBase;
import com.cybertek.tests.pojo.Place;
import com.cybertek.tests.pojo.Spartan;
import com.cybertek.tests.pojo.ZipcodeCorrect;
import com.cybertek.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZipcodeHomeWork extends ZippopotamTestBase {

    @Test
    public  void validZipcodeTest() {
        Response response = given().accept(ContentType.JSON).get("/22031");

        System.out.println("Response.statusCode = " + response.statusCode());
        System.out.println("Response contentType  = " + response.contentType());
        assertEquals(200,response.statusCode());
        assertEquals("application/json",response.contentType());




    }
}

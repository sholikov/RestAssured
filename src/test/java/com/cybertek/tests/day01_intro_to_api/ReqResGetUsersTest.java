package com.cybertek.tests.day01_intro_to_api;

import com.cybertek.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReqResGetUsersTest {



    @Test
    public void getAllUsersApiTest(){
        //send a get request and save response inside the Response object
        Response response = RestAssured.get(ConfigurationReader.getProperty("reqres.users.api.url"));

        //print response status code
        System.out.println(response.statusCode());
        Assertions.assertEquals(200,response.statusCode());
        //print response body
        String prettyPrint = response.prettyPrint();
        Assertions.assertTrue(prettyPrint.contains("George"));


    }

    @Test
    public void getSingleUserApiTest() {
        //send a get request and save response inside the Response object
        Response response = RestAssured.get(ConfigurationReader.getProperty("reqres.users.api.url")+"/5");

        //print response status code
        System.out.println(response.statusCode());
        Assertions.assertEquals(200,response.statusCode());
        //print response body
        String prettyPrint = response.prettyPrint();
        Assertions.assertTrue(prettyPrint.contains("Charles"));

    }

/**
 * Send get request to API Endpoint:
 *     "https://reqres.in/api/users/50"
 * Response status code should be 404
 * Response body should contain "{}"
 */
  @Test
 public void getSingleUserNegativeApiTest() {
      Response response = RestAssured.get(ConfigurationReader.getProperty("reqres.users.api.url") + "/50");
      System.out.println(response.statusCode());
      Assertions.assertEquals(404,response.statusCode());
      response.prettyPrint();
      Assertions.assertTrue(response.asString().contains("{\n    \n}"));

  }
}
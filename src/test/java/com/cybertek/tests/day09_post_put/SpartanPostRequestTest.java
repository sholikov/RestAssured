package com.cybertek.tests.day09_post_put;

import com.cybertek.tests.SpartanTestBase;
import com.cybertek.tests.SpartanTestBase;
import com.cybertek.tests.pojo.Spartan;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class SpartanPostRequestTest extends SpartanTestBase {
    /**
     * given accept is json
     * and content type is json
     * when I send POST request to /api/spartans
     * with  {
     * "gender": "Male",
     * "name": "RestAssured Post,
     * "phone": 2112562345
     * }
     * Then status code is 201
     * And "spartan is born" message should be displayed
     */
    @Test
    public void postSpartanTest() {
        // one of the  short cut
        String requestJson = "{\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"name\": \"New Request\",\n" +
                "  \"phone\": 2112562345\n" + "}";

        Response response = given().accept(ContentType.JSON).and().contentType(ContentType.JSON).
                and().body(requestJson).when().post("/api/spartans");


        System.out.println("response.statusCode() = " + response.statusCode());
        response.prettyPrint();
        // verify status code
        assertThat(response.statusCode(), is(201));// hamcrest matcher
        assertEquals(201, response.statusCode());

        // verify header
        assertThat(response.contentType(), is("application/json"));

        // verify json response body

        assertEquals("A Spartan is Born!", response.path("success"));
        System.out.println("response.path(\"success\") = " + response.path("success"));

        assertThat(response.path("success"), is(equalTo("A Spartan is Born!")));

    }

    @Test
    public void postSpartanWithMapTest() {

        Map<String, Object> requestMap = new LinkedHashMap<>();
        requestMap.put("name", "Jackson");
        requestMap.put("gender", "Male");
        requestMap.put("phone", 23658974565l);

        Response response = given().accept(ContentType.JSON).and().contentType("application/json")
                .and().body(requestMap).when().post("/api/spartans");

        System.out.println("response.statusCode() = " + response.statusCode());
/**
 * Response verifications
 * status code is 201
 * content type is json
 * name is Jackson
 * gender is Male
 * phone is 23658974565l
 */
        assertThat(response.statusCode(), is(201));
        assertThat(response.contentType(), is("application/json"));
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), is("A Spartan is Born!"));
        assertThat(jsonPath.getString("data.name"), is("Jackson"));
        assertThat(jsonPath.getString("data.gender"), is("Male"));
        assertThat(jsonPath.getLong("data.phone"), is(23658974565l));
        assertThat(jsonPath.getInt("data.id"), greaterThan(0));


    }
    @Test
    public void postSpartanAndVerifyWithMapTest() {
        Map<String, Object> requestMap = new LinkedHashMap<>();
        requestMap.put("name" , "WoodenSpoon");
        requestMap.put("gender" , "Male");
        requestMap.put("phone", 3455431234L);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(requestMap)
                .when().post("/api/spartans");

        assertThat(response.statusCode(), is(201));
        //convert json response
        Map<String, Object> responseMap = response.jsonPath().getMap("data");
        System.out.println("responseMap = " + responseMap);

        //compare response data is matching with request data
        assertThat(responseMap.get("name"),equalTo(requestMap.get("name")));
        assertThat(responseMap.get("gender"),equalTo(requestMap.get("gender")));
        assertThat(responseMap.get("phone"),equalTo(requestMap.get("phone")));
    }

    @Test
    public void postSpartanWithPojoTest(){
        // create object of Spartan and set values
        Spartan reqSpartan = new Spartan();
        reqSpartan.setName("POJOpost");
        reqSpartan.setGender("Female");
        reqSpartan.setPhone(9877891234L);

        // serialization: java object => json
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(reqSpartan).log().all()//automatically convert spartan object to json
                .when().post("/api/spartans");


        System.out.println("response.statusCode() = " + response.statusCode());
        assertThat(response.statusCode(),is(201));
        assertThat(response.contentType(),is("application/json"));
        // verify json body
        //assign response spartan info into another Spartan object then compare 2  spartan values

        // deserialization json
        Spartan resSpartan = response.jsonPath().getObject("data",Spartan.class);// spartan info inside data that's reason we cant use asList
        assertThat(resSpartan.getName(),is(reqSpartan.getName()));
        assertThat(resSpartan.getGender(),equalTo(reqSpartan.getGender()));
        assertThat(resSpartan.getPhone(),is(reqSpartan.getPhone()));


    }
}

package com.cybertek.tests.day08_hamcrest_post;

import com.cybertek.tests.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanHamcrestTest extends SpartanTestBase {
    @Test
    public void singleSpartanTest() {
        given().accept(ContentType.JSON).when().get("/api/spartans/24")
                .then().assertThat().statusCode(200).
                and().contentType("application/json")
                .and().body("id", is(24), "name", is("Julio"), "gender", is("Male"), "phone", is(9393139934l))
                .log().all();
    }
    @Test
    public void getMapTest(){

        /**
         * in below example:
         * send get request
         * verify status code and header
         * then convert json body to Map object and return
         * DE-serialization
         */
        Map <String, Object> spartanMap = given().accept(ContentType.JSON).when().get("/api/spartans/24")
                .then().assertThat().statusCode(200)
                .and().contentType("application/json").log().all()
                .and().extract().body().as(Map.class);// convert json body to Map object and return

        System.out.println("spartanMap = " + spartanMap);

        // read id value from map then assert it is 24
        assertThat(spartanMap.get("id"),equalTo(24));

        // check the keys of json
        assertThat(spartanMap.keySet(),containsInAnyOrder("id","name","gender","phone"));

        //check all values of json
        assertThat(spartanMap.values(),hasItems(24,"Julio","Male",9393139934l));


    }
    @Test
    public void getSpartanNamesTest(){
        /**
         * given accept type json
         * get request to /api/spartans
         * then status code is 200
         * and content type is json
         * Then extract names of spartans into a List<String>
         */
        List<String> names = given().accept(ContentType.JSON).when().get("/api/spartans")
                .then().assertThat().statusCode(200)
                .and().contentType("application/json").log().all()
                .and().extract().body().jsonPath().getList("name");

        System.out.println("names = " + names);
        assertThat(names, hasSize(105));
        assertThat(names, hasItems("Lorenza", "Nona", "Sol", "Jaimie"));
    }
    @Test
    public void getTotalElementTest(){
        /**
         * given accept type json
         * name contains "v"
         * gender is "Male'
         * get request to /api/spartans/search
         * then status code is 200
         * and content type is json
         * and return totalElement value as an int
         */

        int totalElement= given().accept(ContentType.JSON).and().queryParams("nameContains", "v", "gender", "Male")
                .when().get("/api/spartans/search")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().extract().path("totalElement");
          // and().extract().body().jsonPath().getInt("totalElement")

        System.out.println("totalElement = " + totalElement);
        assertThat(totalElement,is(4));

    }
    @Test
    public void invalidSpartanTest(){
        /**
         * get /api/spartans/2400
         * in single statement, verify status code 404 and status is "not found"
         */
        given().accept(ContentType.JSON).when().get("/api/spartans/1000")
                .then().assertThat().statusCode(404)
                .and().body("error",equalTo("Not Found"));

    }
}

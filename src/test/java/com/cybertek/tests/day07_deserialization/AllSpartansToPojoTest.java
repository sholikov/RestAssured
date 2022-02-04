package com.cybertek.tests.day07_deserialization;

import com.cybertek.tests.pojo.Spartan;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class AllSpartansToPojoTest {
    @Test
    public void getAllSpartansTest() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");
        System.out.println("Status code = " + response.statusCode());

        //AllSpartans allSpartans = response.as(AllSpartans.class);


        List<Spartan> allSpartans = response.jsonPath().getList("", Spartan.class);
        System.out.println(allSpartans.get(0));

        System.out.println("total spartans count = " + allSpartans.size());

    }
}
package com.cybertek.tests.day05_jsonpath;

import com.cybertek.tests.ORDSTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ORDSGetEmployeesPathTest extends ORDSTestBase {

    @Test
    public void getAllITProgrammersTest() {
        // query parameter with HashMap
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("q", "{\"job_id\":\"IT_PROG\"}");

        Response response = given().accept(ContentType.JSON).and().queryParams(paramMap).when().get("/employees");

        System.out.println("response.statusCode() = " + response.statusCode());
        response.prettyPrint();


        // print first employee emp id, first name and last name, email
        // we go like abs. path, from parent to child
        System.out.println("first employee id = " + response.path("items[0].employee_id"));
        System.out.println("first employee first name= " + response.path("items[0].first_name"));
        System.out.println("first employee last name= " + response.path("items[0].last_name"));
        System.out.println("first employee email= " + response.path("items[0].email"));

        // you want to email all IT_PROGs, save all emails into list of string

        List<String> allEmails= response.path("items.email");
        System.out.println("allEmails.size() = " + allEmails.size());
        System.out.println("allEmails = " + allEmails);


        // i want to save all IT_PROgs, save all the phones into list of the string

        List<String>allPhones = response.path("items.phone_number");
        System.out.println("allPhones = " + allPhones);

        // verify that 590.423.4568 is among phone numbers, is very important to use contain with List
        assertTrue(allPhones.contains("590.423.4568"));

    }


}

package com.cybertek.tests.day05_jsonpath;

import com.cybertek.tests.ORDSTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ORDSGetRequestWithJsonPathTest extends ORDSTestBase {

    /**
     * Given accept is json
     * when I send get request to ords/hr/employees/103
     * Then status code is 200
     * and content type header is json
     * and employee_id is 103
     * and first_name is Alexander
     * and last_name is Hunold
     * and salary is 9000
     */
    @DisplayName("GET ords/hr/employees/103 and jsonPath")
    @Test
    public void jsonPathSingleEmpInfoTest() {

        Response response = given().accept(ContentType.JSON).when().get("/employees/103");


        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(200, response.statusCode());

        System.out.println("response.contentType() = " + response.contentType());
        assertEquals("application/json", response.contentType());

        // assign json response body to JsonPath Object

        JsonPath json = response.jsonPath();

        // read values from jsonPath object
        int empId = json.get("employee_id");
        String firstName = json.getString("first_name");
        String lastName = json.getString("last_name");
        int salary = json.getInt("salary");

        System.out.println("empId = " + empId);
        System.out.println("firstName = " + firstName);
        System.out.println("lastName = " + lastName);
        System.out.println("salary = " + salary);


        assertEquals(103, empId);
        //assertEquals(103, empId); another way of using directly instead of assigning to variables
        assertEquals("Alexander", firstName);
        assertEquals("Hunold", lastName);
        assertEquals(9000, salary);


    }
}

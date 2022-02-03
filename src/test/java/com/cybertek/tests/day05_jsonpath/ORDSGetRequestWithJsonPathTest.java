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

    @DisplayName("GET ords/hr/employees and using jsonPath filters")
    @Test
    public void jsonPathFilterTest(){
        Response response = given().accept(ContentType.JSON).and().queryParam("limit",200).when().get("/employees");
        System.out.println("response.statusCode() = " + response.statusCode());

       // response.prettyPrint();
        JsonPath json = response.jsonPath();
        // employees that work in department 90
        List<String> empList = json.getList("items.findAll{it.department_id==90}.first_name");
        System.out.println("empList = " + empList);
        System.out.println("empList.size() = " + empList.size());

        // names of employees who are "IT-PROG"
        List<String> itProgrammers = json.getList("items.findAll {it.job_id==\"IT_PROG\"}.first_name");
        System.out.println("maleEmployees = " + itProgrammers);

        //emp ids of Employees whose salary is more than 5000
        List<String> empIds = json.getList("items.findAll{it.salary>=5000}.employee_id");
        System.out.println("empIds = " + empIds);
        System.out.println("empIds.size() = " + empIds.size());

        // find the person firstName with max salary
        String fistNameMaxSalary = json.getString("items.max{it.salary}.first_name");
        System.out.println("fistNameMaxSalary = " + fistNameMaxSalary);

        // find the person firstName with max salary
        String fistNameMinSalary = json.getString("items.min{it.salary}.first_name");
        System.out.println("fistNameMaxSalary = " + fistNameMinSalary);


    }
}

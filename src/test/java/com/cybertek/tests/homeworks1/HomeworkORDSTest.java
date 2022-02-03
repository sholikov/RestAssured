package com.cybertek.tests.homeworks1;
import com.cybertek.tests.ORDSTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HomeworkORDSTest extends ORDSTestBase {

/**
 * - Given accept type is Json
 * - Path param value- US
 * - When users sends request to /countries
 * - Then status code is 200
 * - And Content - Type is Json
 * - And country_id is US
 * - And Country_name is United States of America
 * - And Region_id is
 */
@DisplayName("GET ords/hr/countries/US,Q1")
@Test
   public void homeWorkOneORDSApiTest(){
       Response response =  given().accept(ContentType.JSON).and().get("/countries/US");

       System.out.println("response.statusCode() = " + response.statusCode());
       assertEquals(200,response.statusCode());
       response.prettyPrint();

       JsonPath jsonPath = response.jsonPath();
    System.out.println("country_id= " + jsonPath.getString("country_id"));
    System.out.println("country_name = " + jsonPath.getString("country_name"));
    System.out.println("region_id = " + jsonPath.getString("region_id"));
    assertTrue(response.asString().contains("US"));
    assertTrue(response.asString().contains("United States of America"));
    assertTrue(response.asString().contains("2"));
}
/**
 *  Given accept type is Json
 * - Query param value - q={"department_id":80}
 * - When users sends request to /employees
 * - Then status code is 200
 * - And Content - Type is Json
 * - And all job_ids start with 'SA'
 * - And all department_ids are 80
 * - Count is 25
 */
@DisplayName("GET ords/hr/employees/  Query param value q={department_id:80}")
@Test
public void homeWorkSecondORDSApiTest() {
    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("q", "{\"department_id\":80}");

    Response response =  given().accept(ContentType.JSON).and().queryParams(paramMap).when().get("/employees");

    System.out.println("response.statusCode() = " + response.statusCode());
    assertEquals(200,response.statusCode());
    assertEquals("application/json",response.contentType());


    JsonPath jsonPath = response.jsonPath();
    List<String> departId= jsonPath.getList("items.findAll{it.department_id==80}.department_id");
    List<String> jobId= jsonPath.getList("items.findAll{it.job_id.startsWith('SA')}.job_id");
    System.out.println("departId count  = " + departId.size());
    System.out.println("Department Id = " + departId);
    System.out.println("jobId = " + jobId);
    System.out.println("jobId count = " + jobId.size());
    assertEquals(25,jobId.size(),"count does match");



}
    @DisplayName("GET ords/hr/employees/  Query param value q=region_id : 3")
    @Test
    public void homeWorkThirdORDSApiTest() {

        /**
         * - Given accept type is Json
         * -Query param value q= region_id 3
         * - When users sends request to /countries
         * - Then status code is 200
         * - And all regions_id is 3
         * - And count is 6
         * - And hasMore is false
         * - And Country_name are;
         * Australia,China,India,Japan,Malaysia,Singapore
         */
        Map<String, String> paramMap = new HashMap<>();
     //   paramMap.put("q", "{\"region_id\":3}");
        paramMap.put("region_id","3");

        Response response =  given().accept(ContentType.JSON).and().queryParams(paramMap).when().get("/countries");

        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(200,response.statusCode());
        System.out.println("Response content Type = " + response.contentType());
        assertEquals("application/json",response.contentType());
      // response.prettyPrint();


        JsonPath jsonPath = response.jsonPath();
        List<String> allRegionWithId3 = jsonPath.getList("items.findAll{it.region_id==3}");
        System.out.println("allRegionWithId3.size() = " + allRegionWithId3.size());

        List<String>actualCountries = jsonPath.getList("items.findAll{it.region_id==3}.country_name");
        System.out.println("actualCountries = " + actualCountries);
        List<String>expectedCountries = new ArrayList<>(Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore"));
        assertEquals(expectedCountries,actualCountries,"Does not match");



    }


}



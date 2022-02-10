package com.cybertek.tests.day11_put_request;

import com.cybertek.tests.SpartanTestBase;
import com.cybertek.tests.pojo.Spartan;
import com.cybertek.utilities.RestUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UpdateSpartanTest extends SpartanTestBase {
    /**
     *
     * Given content type is json
     * When i send put request to /api/spartans/1126
     * With json body:
     * {
     *   "gender": "Male",
     *   "name": "Changed",
     *   "phone": 1234567890
     * }
     * Then status code is 200
     * And content type is json
     * And json response should contain updated values
     */

    @Test
    public void updateSpartanTest(){

        Map<String, Object> spartanMap = new LinkedHashMap<>();
        spartanMap.put("gender", "Male");
        spartanMap.put("name", "Whatever");
        spartanMap.put("phone", 1234567890);

       given().contentType(ContentType.JSON)
               .and().body(spartanMap)
               .when().put("/api/spartans/106")
               .then().assertThat().statusCode(is(204));
    }

    /**
     given content type is json
     When i send patch request to /api/spartans/1126
     With json body:
     {
     "phone": 1234567890
     }
     Then status code is 204
     */
    @Test
    public void patchSpartanTest() {
        //map for patch request body
        Map<String,Object> spartanMap = new LinkedHashMap<>();
        spartanMap.put("phone", 5555555555L);
        //send patch request and check status code
        given().contentType(ContentType.JSON)
                .and().pathParam("id", 1)
                .and().body(spartanMap)
                .when().patch("/api/spartans/{id}")
                .then().assertThat().statusCode(204);

        //send get request with same spartan id and verify phone number update
        Spartan spartan = RestUtils.getSpartan(1126);
        assertThat(spartan.getPhone(), is(spartanMap.get("phone")));

    }
}

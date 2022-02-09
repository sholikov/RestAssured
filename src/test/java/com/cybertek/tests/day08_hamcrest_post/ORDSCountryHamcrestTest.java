package com.cybertek.tests.day08_hamcrest_post;

import com.cybertek.tests.ORDSTestBase;
import com.cybertek.tests.pojo.Country;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;


public class ORDSCountryHamcrestTest extends ORDSTestBase {
    @Test
    public void getCountryToPojoTest(){
        /**
         * send get request to /ords/hr/countries/ZW
         * verify status code is 200
         * header is json
         * and extract body as Country pojo object
         * Country country = ....
         */

        Country countryZimbabwe = given().accept(ContentType.JSON).when().get("/countries/ZW")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().extract().body().as(Country.class);

        System.out.println("countryZimbabwe = " + countryZimbabwe);

    }


}

package com.cybertek.tests.pojo;
import com.cybertek.tests.SpartanTestBase;
import com.cybertek.tests.pojo.Spartan;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
@Data
public class SpartanSearch {

    private List<Spartan>content;
    private int totalElement;

}

/* EmployeeControllerTest.java
Test for the EmployeeController
Author: Sharfaa Sedick Anthony 220041571
Date: 16 June 2022 */

package za.ac.cput.schoolmanagement.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.schoolmanagement.domain.Employee;
import za.ac.cput.schoolmanagement.domain.Name;
import za.ac.cput.schoolmanagement.factory.EmployeeFactory;
import za.ac.cput.schoolmanagement.factory.NameFactory;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired private EmployeeController controller;
    @Autowired private TestRestTemplate restTemplate;
    private Employee employee;
    private Name name;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        assertNotNull(controller);
        this.name = NameFactory.build("Sharfaa", "","Anthony");
        this.employee = EmployeeFactory.build("220041571","220041571@mycput.ac.za", name);
        this.baseUrl = "http://localhost:" + this.port + "/schoolmanagement/employee/";
    }

    @Order(1)
    @Test
    void save(){
        String url = baseUrl + "save";
        System.out.println(url);
        ResponseEntity<Employee> response = this.restTemplate
                .postForEntity(url, this.employee, Employee.class);
        System.out.println(response);
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }

    @Order(2)
    @Test
    void read(){
        String url = baseUrl + "read/" + this.employee.getStaffId();
        System.out.println(url);
        ResponseEntity<Employee> response = this.restTemplate.getForEntity(url, Employee.class);
        System.out.println(response);
        assertAll(
                ()-> assertEquals(HttpStatus.OK, response.getStatusCode()),
                ()-> assertNotNull(response.getBody())
        );
    }

    @Order(3)
    @Test
    void delete(){
        String url = baseUrl + "delete/" + this.employee.getStaffId();
        System.out.println(url);
        this.restTemplate.delete(url);
    }

    @Order(4)
    @Test
    void findAll(){
        String url = baseUrl + "all";
        System.out.println(url);
        ResponseEntity<Employee[]> response =
                this.restTemplate.getForEntity(url, Employee[].class);
        System.out.println(Arrays.asList(response.getBody()));
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(0, response.getBody().length)
        );
    }
}
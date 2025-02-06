package com.employee;

import com.employee.dtos.DepartmentDto;
import com.employee.dtos.EmployeesDto;
import com.employee.entity.Department;
import com.employee.services.EmployeesService;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeesControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    EmployeesService employeesService;
    private final String firstName = RandomStringUtils.randomAlphabetic(10);
    private final String lastName = RandomStringUtils.randomAlphabetic(10);
    private final String departmentName = RandomStringUtils.randomAlphabetic(20);

    Gson gson = new Gson();
    private Department department;
    private final String baseUrl = "/api/employees";

    @BeforeEach
    public void setup() {
        DepartmentDto departmentDto = DepartmentDto.builder().name(departmentName).build();
        String responseBody = gson.toJson(employeesService.addDepartment(departmentDto).getBody());
        department = gson.fromJson(responseBody, Department.class);
    }

    @Test
    @DisplayName("testCreateSuccess")
    public void testCreateSuccess() throws Exception {
        EmployeesDto.CreateDto createDto = EmployeesDto.CreateDto.builder().firstName(firstName).lastName(lastName).email(lastName + "@gmail.com").salary(new BigDecimal(5000)).departmentId(department.getId()).build();
        RequestBuilder request = MockMvcRequestBuilders.post(baseUrl).content(gson.toJson(createDto)).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
    }

    @Test
    @DisplayName("testCreateFailed")
    public void testCreateFailed() throws Exception {
        //Invalid email address
        EmployeesDto.CreateDto createDto = EmployeesDto.CreateDto.builder().firstName(firstName).lastName(lastName).email(lastName + "gmail.com").salary(new BigDecimal(5000)).departmentId(department.getId()).build();
        RequestBuilder request = MockMvcRequestBuilders.post(baseUrl).content(new Gson().toJson(createDto)).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @DisplayName("testUpdateSuccess")
    public void testUpdateSuccess() throws Exception {
        EmployeesDto.EditDto editDto = EmployeesDto.EditDto.builder().firstName(firstName).build();
        RequestBuilder request = MockMvcRequestBuilders.put(baseUrl + "/" + 1L).content(gson.toJson(editDto)).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("testUpdateFailed")
    public void testUpdateFailed() throws Exception {
        //Invalid Id
        EmployeesDto.EditDto editDto = EmployeesDto.EditDto.builder().firstName(firstName).build();
        RequestBuilder request = MockMvcRequestBuilders.put(baseUrl + "/" + 0L).content(new Gson().toJson(editDto)).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    @DisplayName("testFindAll")
    public void testFindAll() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get(baseUrl).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("testFindByIdSuccess")
    public void testFindByIdSuccess() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get(baseUrl + "/1").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("testFindByIdFailed")
    public void testFindByIdFailed() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get(baseUrl + "/0").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isNoContent()).andReturn();
    }

    @Test
    @DisplayName("testDeleteSuccess")
    public void testDeleteSuccess() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete(baseUrl + "/1").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("testDeleteFailed")
    public void testDeleteFailed() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete(baseUrl + "/1000").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isNotFound()).andReturn();
    }
}

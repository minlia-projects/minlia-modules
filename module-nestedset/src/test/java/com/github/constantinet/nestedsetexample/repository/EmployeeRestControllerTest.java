//package com.github.constantinet.nestedsetexample.repository;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(EmployeeRestController.class)
//public class EmployeeRestControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private EmployeeService service;
//
//    // write test cases here
//    @Test
//    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
//        throws Exception {
//
//        Employee alex = new Employee("alex");
//
//        List<Employee> allEmployees = Arrays.asList(alex);
//
//        given(service.getAllEmployees()).willReturn(allEmployees);
//
//        mvc.perform(get("/api/employees")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$", hasSize(1)))
//            .andExpect(jsonPath("$[0].name", is(alex.getName())));
//    }
//
//}
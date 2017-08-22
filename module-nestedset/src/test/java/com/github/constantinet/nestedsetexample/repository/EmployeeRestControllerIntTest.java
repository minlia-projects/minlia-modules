//package com.github.constantinet.nestedsetexample.repository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(
//  webEnvironment = WebEnvironment.RANDOM_PORT,
//  classes = Application.class)
//@AutoConfigureMockMvc
//@TestPropertySource(
//  locations = "classpath:application-integrationtest.properties")
//public class EmployeeRestControllerIntTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private EmployeeRepository repository;
//
//    // write test cases here
//    @Test
//    public void givenEmployees_whenGetEmployees_thenStatus200()
//        throws Exception {
//
//        createTestEmployee("bob");
//
//        mvc.perform(get("/api/employees")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(content()
//                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$[0].name", is("bob")));
//    }
//
//}
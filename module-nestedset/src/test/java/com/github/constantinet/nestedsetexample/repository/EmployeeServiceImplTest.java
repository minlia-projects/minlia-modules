//package com.github.constantinet.nestedsetexample.repository;
//
//@RunWith(SpringRunner.class)
//public class EmployeeServiceImplTest {
//
//    @TestConfiguration
//    static class EmployeeServiceImplTestContextConfiguration {
//
//        @Bean
//        public EmployeeService employeeService() {
//            return new EmployeeServiceImpl();
//        }
//    }
//
//    @Autowired
//    private EmployeeService employeeService;
//
//    @MockBean
//    private EmployeeRepository employeeRepository;
//
//    // write test cases here
//
//    @Before
//    public void setUp() {
//        Employee alex = new Employee("alex");
//
//        Mockito.when(employeeRepository.findByName(alex.getName()))
//            .thenReturn(alex);
//    }
//    @Test
//    public void whenValidName_thenEmployeeShouldBeFound() {
//        String name = "alex";
//        Employee found = employeeService.getEmployeeByName(name);
//
//        assertThat(found.getName())
//            .isEqualTo(name);
//    }
//
//}
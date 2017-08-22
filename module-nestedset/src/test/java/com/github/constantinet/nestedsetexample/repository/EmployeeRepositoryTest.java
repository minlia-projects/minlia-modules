//package com.github.constantinet.nestedsetexample.repository;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class EmployeeRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    // write test cases here
//    @Test
//    public void whenFindByName_thenReturnEmployee() {
//        // given
//        Employee alex = new Employee("alex");
//        entityManager.persist(alex);
//        entityManager.flush();
//
//        // when
//        Employee found = employeeRepository.findByName(alex.getName());
//
//        // then
//        assertThat(found.getName())
//            .isEqualTo(alex.getName());
//    }
//}
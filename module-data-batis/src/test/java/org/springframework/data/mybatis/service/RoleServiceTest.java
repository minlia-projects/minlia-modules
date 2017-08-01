package org.springframework.data.mybatis.service;

import com.minlia.cloud.body.query.QueryOperator;
import com.minlia.cloud.data.batis.support.persistence.QuerySpecifications;
import com.minlia.cloud.data.batis.support.query.ApiSearchRequestBody;
import com.minlia.cloud.data.batis.support.query.QueryCondition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mybatis.config.sample.TestConfig;
import org.springframework.data.mybatis.domain.sample.Group;
import org.springframework.data.mybatis.domain.sample.Role;
import org.springframework.data.mybatis.repository.RoleSearchRequestBody;
import org.springframework.data.mybatis.service.sample.RoleService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.springframework.data.domain.Sort.Direction.ASC;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class RoleServiceTest {

    @Autowired
    RoleService roleService;


    Role manager, tester, developer, assistant;
    Group group1, group2;

    @Before
    public void setUp() throws Exception {
        manager = new Role("manager222", group1);
        tester = new Role("tester222", group2);
        developer = new Role("developer222", group2);
        assistant = new Role("assistant222", group1);
        roleService.save(Arrays.asList(manager, tester, developer, assistant).get(0));
    }


    @Test
    public void testSave() {
        Role role = new Role("assistant222");
        assertNull(role.getId());
         roleService.save(role);
//        assertNotNull(role.getId());
    }



//
//    @Autowired
//    BibleItemRepository repository;

    //    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_SEARCH + "')")
//    @RequestMapping(value = "{code}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ApiOperation(value = "查询所有子项[根据父code]", notes = "查询所有子项[根据父code]", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @Test
    public void searchByConditions() {
        String code="xxx";
//        Pageable  pageable=new PageRequest(0,10);
        Pageable pageable=new PageRequest(0, 10, new Sort(ASC, "name"));
        ApiSearchRequestBody<RoleSearchRequestBody> body=new ApiSearchRequestBody<>();
        body.getConditions().add(new QueryCondition("name", QueryOperator.eq, code));
        List pageableFound=roleService.findAll(QuerySpecifications.buildSpecification(body));
//        Page pageableFound1=roleService.findAll(body,pageable);
//        return SuccessResponseBody.builder().payload(pageableFound).build();
        assertNull(pageableFound);
    }


    @Test
    public void respectsPageableOrderOnQueryGenerateFromMethodName() throws Exception {
        Pageable pageable=new PageRequest(0, 10, new Sort(ASC, "firstname"));
//        Page<User> ascending = roleService.findAll(pageable,new Object(), "Matthews");


//        repository.findAll(selectStatement, countStatement, pm, paramsMap)

//        Page<User> descending = userRepository.findByLastnameIgnoringCase(new PageRequest(0, 10, new Sort(DESC, "firstname")), "Matthews");
//        assertThat(ascending.getTotalElements(), is(2L));
//        assertThat(descending.getTotalElements(), is(2L));
//        assertThat(ascending.getContent().get(0).getFirstname(), is(not(equalTo(descending.getContent().get(0).getFirstname()))));
//        assertThat(ascending.getContent().get(0).getFirstname(), is(equalTo(descending.getContent().get(1).getFirstname())));
//        assertThat(ascending.getContent().get(1).getFirstname(), is(equalTo(descending.getContent().get(0).getFirstname())));
    }
}

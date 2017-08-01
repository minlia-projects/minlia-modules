/*
 *
 *   Copyright 2016 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.springframework.data.mybatis.repository;

import com.google.common.collect.Maps;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.body.query.QueryOperator;
import com.minlia.cloud.data.batis.support.persistence.QuerySpecifications;
import com.minlia.cloud.data.batis.support.persistence.SpecificationDetail;
import com.minlia.cloud.data.batis.support.query.ApiSearchRequestBody;
import com.minlia.cloud.data.batis.support.query.QueryCondition;
import com.minlia.cloud.data.batis.support.util.QueryUtil;
import io.swagger.annotations.ApiOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mybatis.config.sample.TestConfig;
import org.springframework.data.mybatis.domain.sample.Role;
import org.springframework.data.mybatis.domain.sample.User;
import org.springframework.data.mybatis.repository.sample.RoleRepository;
import org.springframework.data.mybatis.repository.sample.UserRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author Jarvis Song
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserRepositoryFinderTests {


    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    User dave, carter, oliver;
    Role drummer, guitarist, singer;

    @Before
    public void setUp() {

        drummer = roleRepository.save(new Role("DRUMMER"));
        guitarist = roleRepository.save(new Role("GUITARIST"));
        singer = roleRepository.save(new Role("SINGER"));

        dave = userRepository.save(new User("Dave", "Matthews", "dave@dmband.com", singer));
        carter = userRepository.save(new User("Carter", "Beauford", "carter@dmband.com", singer, drummer));
        oliver = userRepository.save(new User("Oliver August", "Matthews", "oliver@dmband.com"));
    }

    @Test
    public void testSimpleCustomCreatedFinder() {

        User user = userRepository.findByEmailAddressAndLastname("dave@dmband.com", "Matthews");
        assertEquals(dave, user);
    }

    @Test
    public void returnsNullIfNothingFound() {

        User user = userRepository.findByEmailAddress("foobar");
        assertEquals(null, user);
    }

    @Test
    public void testAndOrFinder() {

        List<User> users = userRepository.findByEmailAddressAndLastnameOrFirstname("dave@dmband.com", "Matthews", "Carter");

        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.contains(dave));
        assertTrue(users.contains(carter));
    }

    @Test
    public void executesPagingMethodToPageCorrectly() {

        Page<User> page = userRepository.findByLastname(new PageRequest(0, 1), "Matthews");
        assertThat(page.getNumberOfElements(), is(1));
        assertThat(page.getTotalElements(), is(2L));
        assertThat(page.getTotalPages(), is(2));
    }

    @Test
    public void executesPagingMethodToListCorrectly() {

        List<User> list = userRepository.findByFirstname("Carter", new PageRequest(0, 1));
        assertThat(list.size(), is(1));
    }

    @Test
    public void executesInKeywordForPageCorrectly() {

        Page<User> page = userRepository.findByFirstnameIn(new PageRequest(0, 1), "Dave", "Oliver August");

        assertThat(page.getNumberOfElements(), is(1));
        assertThat(page.getTotalElements(), is(2L));
        assertThat(page.getTotalPages(), is(2));
    }

    @Test
    public void executesNotInQueryCorrectly() throws Exception {

        List<User> result = userRepository.findByFirstnameNotIn(Arrays.asList("Dave", "Carter"));
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(oliver));
    }

    @Test
    public void findsByLastnameIgnoringCase() throws Exception {
        List<User> result = userRepository.findByLastnameIgnoringCase("BeAUfoRd");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(carter));
    }

    @Test
    public void findsByLastnameIgnoringCaseLike() throws Exception {
        List<User> result = userRepository.findByLastnameIgnoringCaseLike("BeAUfo%");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(carter));
    }

    @Test
    public void findByLastnameAndFirstnameAllIgnoringCase() throws Exception {
        List<User> result = userRepository.findByLastnameAndFirstnameAllIgnoringCase("MaTTheWs", "DaVe");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(dave));
    }

    @Test
    public void respectsPageableOrderOnQueryGenerateFromMethodName() throws Exception {
        Page<User> ascending = userRepository.findByLastnameIgnoringCase(new PageRequest(0, 10, new Sort(ASC, "firstname")), "Matthews");
        Page<User> descending = userRepository.findByLastnameIgnoringCase(new PageRequest(0, 10, new Sort(DESC, "firstname")), "Matthews");
        assertThat(ascending.getTotalElements(), is(2L));
        assertThat(descending.getTotalElements(), is(2L));
        assertThat(ascending.getContent().get(0).getFirstname(), is(not(equalTo(descending.getContent().get(0).getFirstname()))));
        assertThat(ascending.getContent().get(0).getFirstname(), is(equalTo(descending.getContent().get(1).getFirstname())));
        assertThat(ascending.getContent().get(1).getFirstname(), is(equalTo(descending.getContent().get(0).getFirstname())));
    }

    @Test
    public void executesQueryToSlice() {

        Slice<User> slice = userRepository.findSliceByLastname("Matthews", new PageRequest(0, 1, ASC, "firstname"));

        assertThat(slice.getContent(), hasItem(dave));
        assertThat(slice.hasNext(), is(true));
    }



    @Test
    public void executesQueryToSlice1() {

        String selectStatement="";
        String countStatement="";
        Map<String, Object> paramsMap = Maps.newHashMap();

        String [] columns=new String[]{};

    //<X extends T> Page<T> findAll(Pageable pageable, X condition, String... columns);
//        SpecificationDetail<Role> specificationDetail=new SpecificationDetail();
//        specificationDetail.setPersistentClass(Role.class);
//        String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(
//                specificationDetail.getAndQueryConditions(),
//                specificationDetail.getOrQueryConditions(),
//                null,
//                paramsMap, true);
//
//
//
//        Map<String, Object> otherParam = new HashMap<String, Object>();
//        if (null != columns) {
//            otherParam.put("_specifiedFields", columns);
//        }

//        PageModel<T> pm=null;
//
//        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
//        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
//        pm.setPageInstance(PreconditionsHelper.isNotEmpty(selectStatement) && PreconditionsHelper.isNotEmpty(countStatement) ?
//                roleRepository.findAll(selectStatement, countStatement, pm, paramsMap) : roleRepository.findAll(isBasic, pm, paramsMap));




//        return findByPager(pageable, "_findByPager", "_countByCondition", condition, otherParam,new String[0]);




//        Slice<User> slice = userRepository.findAll(selectStatement, countStatement, pm, paramsMap);
//
//        assertThat(slice.getContent(), hasItem(dave));
//        assertThat(slice.hasNext(), is(true));
    }


//    @Autowired
//    QuerySpecifications spec;
//
//    @Autowired
//    BibleItemRepository repository;

//    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_SEARCH + "')")
//    @RequestMapping(value = "{code}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ApiOperation(value = "查询所有子项[根据父code]", notes = "查询所有子项[根据父code]", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    public StatefulBody searchByConditions(@PathVariable String code, @PageableDefault Pageable pageable, @RequestBody ApiSearchRequestBody<RoleSearchRequestBody> body) {
//        body.getConditions().add(new QueryCondition("bible.code", QueryOperator.eq, code));
//        Page pageableFound=roleRepository.findAll(body, pageable);
//        return SuccessResponseBody.builder().payload(pageableFound).build();
//    }



    @Test
    public void executesMethodWithNotContainingOnStringCorrectly() {
        assertThat(userRepository.findByLastnameNotContaining("u"), containsInAnyOrder(dave, oliver));
    }

//    @Test
//    public void translatesContainsToMemberOf() {
//
//        List<User> singers = userRepository.findByRolesContaining(singer);
//
//        assertThat(singers, hasSize(2));
//        assertThat(singers, hasItems(dave, carter));
//        assertThat(userRepository.findByRolesContaining(drummer), contains(carter));
//    }
//
//    @Test
//    public void translatesNotContainsToNotMemberOf() {
//        assertThat(userRepository.findByRolesNotContaining(drummer), hasItems(dave, oliver));
//    }

}

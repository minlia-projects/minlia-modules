///*
// *
// *   Copyright 2016 the original author or authors.
// *
// *   Licensed under the Apache License, Version 2.0 (the "License");
// *   you may not use this file except in compliance with the License.
// *   You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// *   Unless required by applicable law or agreed to in writing, software
// *   distributed under the License is distributed on an "AS IS" BASIS,
// *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *   See the License for the specific language governing permissions and
// *   limitations under the License.
// *
// */
//
//package com.microsoft.crm.v1.test;
//
//import TestConfig;
//import com.microsoft.crm.v1.dao.RoleRepository;
//import com.microsoft.crm.v1.dao.UserDao;
//import com.microsoft.crm.v1.domain.Role;
//import com.microsoft.crm.v1.domain.User;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Slice;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.*;
//import static org.springframework.data.domain.Sort.Direction.ASC;
//import static org.springframework.data.domain.Sort.Direction.DESC;
//
///**
// * @author Jarvis Song
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Transactional
//public class UserRepositoryFinderTests {
//
//
//    @Autowired
//    UserDao userDao;
//    @Autowired
//    RoleRepository roleRepository;
//
//    User dave, carter, oliver;
//    Role drummer, guitarist, singer;
//
//    @Before
//    public void setUp() {
//
//        drummer = roleRepository.save(new Role("DRUMMER"));
//        guitarist = roleRepository.save(new Role("GUITARIST"));
//        singer = roleRepository.save(new Role("SINGER"));
//
//        dave = userDao.save(new User("Dave", "Matthews", "dave@dmband.com", singer));
//        carter = userDao.save(new User("Carter", "Beauford", "carter@dmband.com", singer, drummer));
//        oliver = userDao.save(new User("Oliver August", "Matthews", "oliver@dmband.com"));
//    }
//
//    @Test
//    public void testSimpleCustomCreatedFinder() {
//
//        User user = userDao.findByEmailAddressAndLastname("dave@dmband.com", "Matthews");
//        assertEquals(dave, user);
//    }
//
//
//    @Test
//    public void returnsNullIfNothingFound() {
//
//        User user = userDao.findByEmailAddress("foobar");
//        assertEquals(null, user);
//    }
//
//    @Test
//    public void testAndOrFinder() {
//
//        List<User> users = userDao.findByEmailAddressAndLastnameOrFirstname("dave@dmband.com", "Matthews", "Carter");
//
//        assertNotNull(users);
//        assertEquals(2, users.size());
//        assertTrue(users.contains(dave));
//        assertTrue(users.contains(carter));
//    }
//
//    @Test
//    public void executesPagingMethodToPageCorrectly() {
//
//        Page<User> page = userDao.findByLastname(new PageRequest(0, 1), "Matthews");
//        assertThat(page.getNumberOfElements(), is(1));
//        assertThat(page.getTotalElements(), is(2L));
//        assertThat(page.getTotalPages(), is(2));
//    }
//
//    @Test
//    public void findUseMapper1() {
//
//        List<User> page = userDao.findUseMapper1(  "Matthews");
//        for(User user:page){
//            System.out.println(user);
//        }
//        System.out.println(page.size());
//        assertThat(page,notNullValue());
//    }
//
//    @Test
//    public void findUseMapper() {
//
//        List<User> page = userDao.findUseMapper(  "Matthews");
//        for(User user:page){
//            System.out.println(user);
//        }
//        System.out.println(page.size());
//        assertThat(page,notNullValue());
//    }
//
//    @Test
//    public void executesPagingMethodToListCorrectly() {
//
//        List<User> list = userDao.findByFirstname("Carter", new PageRequest(0, 1));
//        assertThat(list.size(), is(1));
//    }
//
//    @Test
//    public void executesInKeywordForPageCorrectly() {
//
//        Page<User> page = userDao.findByFirstnameIn(new PageRequest(0, 1), "Dave", "Oliver August");
//
//        assertThat(page.getNumberOfElements(), is(1));
//        assertThat(page.getTotalElements(), is(2L));
//        assertThat(page.getTotalPages(), is(2));
//    }
//
//    @Test
//    public void executesNotInQueryCorrectly() throws Exception {
//
//        List<User> result = userDao.findByFirstnameNotIn(Arrays.asList("Dave", "Carter"));
//        assertThat(result.size(), is(1));
//        assertThat(result.get(0), is(oliver));
//    }
//
//    @Test
//    public void findsByLastnameIgnoringCase() throws Exception {
//        List<User> result = userDao.findByLastnameIgnoringCase("BeAUfoRd");
//        assertThat(result.size(), is(1));
//        assertThat(result.get(0), is(carter));
//    }
//
//    @Test
//    public void findsByLastnameIgnoringCaseLike() throws Exception {
//        List<User> result = userDao.findByLastnameIgnoringCaseLike("BeAUfo%");
//        assertThat(result.size(), is(1));
//        assertThat(result.get(0), is(carter));
//    }
//
//    @Test
//    public void findByLastnameAndFirstnameAllIgnoringCase() throws Exception {
//        List<User> result = userDao.findByLastnameAndFirstnameAllIgnoringCase("MaTTheWs", "DaVe");
//        assertThat(result.size(), is(1));
//        assertThat(result.get(0), is(dave));
//    }
//
//    @Test
//    public void respectsPageableOrderOnQueryGenerateFromMethodName() throws Exception {
//        Page<User> ascending = userDao.findByLastnameIgnoringCase(new PageRequest(0, 10, new Sort(ASC, "firstname")), "Matthews");
//        Page<User> descending = userDao.findByLastnameIgnoringCase(new PageRequest(0, 10, new Sort(DESC, "firstname")), "Matthews");
//        assertThat(ascending.getTotalElements(), is(2L));
//        assertThat(descending.getTotalElements(), is(2L));
//        assertThat(ascending.getContent().get(0).getFirstname(), is(not(equalTo(descending.getContent().get(0).getFirstname()))));
//        assertThat(ascending.getContent().get(0).getFirstname(), is(equalTo(descending.getContent().get(1).getFirstname())));
//        assertThat(ascending.getContent().get(1).getFirstname(), is(equalTo(descending.getContent().get(0).getFirstname())));
//    }
//
//    @Test
//    public void executesQueryToSlice() {
//
//        Slice<User> slice = userDao.findSliceByLastname("Matthews", new PageRequest(0, 1, ASC, "firstname"));
//
//        assertThat(slice.getContent(), hasItem(dave));
//        assertThat(slice.hasNext(), is(true));
//    }
//
//    @Test
//    public void executesMethodWithNotContainingOnStringCorrectly() {
//        assertThat(userDao.findByLastnameNotContaining("u"), containsInAnyOrder(dave, oliver));
//    }
//
////    @Test
////    public void translatesContainsToMemberOf() {
////
////        List<User> singers = userDao.findByRolesContaining(singer);
////
////        assertThat(singers, hasSize(2));
////        assertThat(singers, hasItems(dave, carter));
////        assertThat(userDao.findByRolesContaining(drummer), contains(carter));
////    }
////
////    @Test
////    public void translatesNotContainsToNotMemberOf() {
////        assertThat(userDao.findByRolesNotContaining(drummer), hasItems(dave, oliver));
////    }
//
//}

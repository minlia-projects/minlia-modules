package com.minlia.modules.rbac.endpoint;

import com.google.common.collect.Lists;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.body.query.Order;
import com.minlia.cloud.body.query.QueryOperator;
import com.minlia.cloud.query.specification.batis.QueryCondition;
import com.minlia.cloud.query.specification.batis.QuerySpecifications;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.query.specification.batis.body.ApiQueryRequestBody;
import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.query.UserQueryRequestBody;
import com.minlia.modules.rbac.service.UserQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 8/2/17.
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
@Api(tags = "用户1", value = "用户1", description = "用户1")
public class UserEndpoint {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserDao userDao;

    @Autowired
    UserQueryService userQueryService;

//    @Autowired
//    UserRepository userRepository;

    @ApiOperation(
            value = "获取一个指定ID的实体",
            notes = "获取一个指定ID的实体",
            httpMethod = "POST",
            produces = "application/json"
    )
    @PostMapping(value = "")
    public StatefulBody findOne(@PageableDefault Pageable pageable, @RequestBody ApiQueryRequestBody<UserQueryRequestBody> body) {

        body.getConditions().add(new QueryCondition("firstName", QueryOperator.like, "%x%"));
        SpecificationDetail<User> spec = QuerySpecifications.bySearchQueryCondition(
                body.getConditions(),
//                QueryCondition.ne(Person.F_STATUS, User.FLAG_DELETE).setAnalytiColumnPrefix("a"),
                QueryCondition.ne("id", 1l));
//        spec.orAll(orQueryConditions);
        spec.setOrders(Lists.newArrayList(new Order("id", Order.Direction.asc)));


//        userDao.findByOverrridingMethod();




//        List<User> userFound=userDao.findUseMapper("%x%");

        User user=new User();
        String name= RandomStringUtils.randomAlphabetic(4);
        String lastname= RandomStringUtils.randomAlphabetic(4);

        user.setFirstName(name);
        user.setLastName(lastname);
        user.setEmail(name+"@qq.com");
        user.setUsername(name);
        user.setPassword(bCryptPasswordEncoder.encode("admin"));


//        user.setDateTime(DateTime.now());
//        user.setMyDate(new Date());

//        user.setDateTime(DateTime.now());
//        user.setZonedDateTime(ZonedDateTime.now());

//        user.setTime(new Date());



        userDao.insert(user);

         user=new User();
         name= RandomStringUtils.randomAlphabetic(4);
         lastname= RandomStringUtils.randomAlphabetic(4);
        user.setFirstName(name);
        user.setLastName(lastname);
        user.setEmail(name+"@qq.com");
        user.setUsername(name);
        user.setPassword(bCryptPasswordEncoder.encode("admin"));


        //        user.setTimestamp(new Date());
//        user.setDate(new Date());

        userDao.insert(user);


//        userRepository.save(user);


//      User us=  userRepository.findOne(2l);
//        log.debug("userRepository.findOne {}",us);

        User user1 =userDao.findOne(1l);

        log.debug("userRepository.findOne {}",user1);

//        List<User> users = userRepository.findAll();


        Page<User> found =  userQueryService.findBasePage(pageable,spec,true);
        return SuccessResponseBody.builder().payload(found).build();
    }


}

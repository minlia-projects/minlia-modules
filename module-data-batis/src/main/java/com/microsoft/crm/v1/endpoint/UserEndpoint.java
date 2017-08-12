package com.microsoft.crm.v1.endpoint;

import com.google.common.collect.Lists;
import com.microsoft.crm.v1.dao.UserDao;
import com.microsoft.crm.v1.domain.User;
import com.microsoft.crm.v1.repository.UserRepository;
import com.microsoft.crm.v1.service.UserQueryService;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.body.query.Order;
import com.minlia.cloud.body.query.QueryOperator;
import com.minlia.cloud.query.specification.batis.BatisSpecifications;
import com.minlia.cloud.query.specification.batis.QueryCondition;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.query.specification.batis.body.BatisApiSearchRequestBody;
import com.qianyi.body.PersonSearchRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 8/2/17.
 */
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Api(tags = "用户1", value = "用户1", description = "用户1")
public class UserEndpoint {


    @Autowired
    UserDao userDao;

    @Autowired
    UserQueryService userQueryService;

    @Autowired
    UserRepository userRepository;

    @ApiOperation(
            value = "获取一个指定ID的实体",
            notes = "获取一个指定ID的实体",
            httpMethod = "POST",
            produces = "application/json"
    )
    @PostMapping(value = "")
    public StatefulBody findOne(@PageableDefault Pageable pageable, @RequestBody BatisApiSearchRequestBody<PersonSearchRequestBody> body) {

        body.getConditions().add(new QueryCondition("firstname", QueryOperator.like, "%"));
        SpecificationDetail<User> spec = BatisSpecifications.bySearchQueryCondition(
                body.getConditions(),
//                QueryCondition.ne(Person.F_STATUS, User.FLAG_DELETE).setAnalytiColumnPrefix("a"),
                QueryCondition.ne("id", 1l));
//        spec.orAll(orQueryConditions);
        spec.setOrders(Lists.newArrayList(new Order("id", Order.Direction.desc)));


//        userDao.findByOverrridingMethod();





        Page<User> found =  userQueryService.findBasePage(pageable,spec,true);

//        List<User> userFound=userDao.findUseMapper("%x%");

        User user=new User();
        String name= RandomStringUtils.randomAlphabetic(4);
        String lastname= RandomStringUtils.randomAlphabetic(4);
        user.setFirstname(name);
        user.setLastname(lastname);
        user.setEmailAddress(name+"@qq.com");
//        userDao.insert(user);


        userRepository.save(user);


//        userRepository.findOne(2l);

        userRepository.getOne(1l);
        log.debug("userRepository.findOne {}",userRepository);

        return SuccessResponseBody.builder().payload(found).build();
    }


}

package com.qianyi.listener;

import com.google.common.collect.Maps;
import com.qianyi.dao.PersonDao;
import com.qianyi.domain.Person;
import com.qianyi.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Map;

@Slf4j
@Component
public class PersonSeedDataInitializeListener implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

//    @Autowired
//    private RoleRepository WSAFroleRepository;
//
//    @Autowired
//    PermissionCreationService permissionCreationService;

    @Autowired
    private PersonDao personDao;
    @Autowired
    private PersonRepository personRepository;
    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        log.debug("Starting Initialize Person Seed Data ");
        if (alreadySetup) {
            return;
        }

        //定义一个MAP, 根据MAP插入初始化数据
        Map<String, String> initialAdminPermissions = Maps.newHashMap();

        String entityName = "数据字典";

        PodamFactory factory = new PodamFactoryImpl();

// This will use constructor with minimum arguments and
// then setters to populate POJO

        for (int i = 0; i < 2; i++) {
//            Person entity = factory.manufacturePojoWithFullData(Person.class);
            Person entity=new Person();
            personRepository.save(entity);

        }
        //菜单
//        initialAdminPermissions.put(BibleService.ENTITY_CREATE, SecurityConstants.CREATE_OPERATION_DESCRIPTION_CN + entityName);
//        initialAdminPermissions.put(BibleService.ENTITY_READ, SecurityConstants.READ_OPERATION_DESCRIPTION_CN + entityName);
//        initialAdminPermissions.put(BibleService.ENTITY_UPDATE, SecurityConstants.UPDATE_OPERATION_DESCRIPTION_CN + entityName);
//        initialAdminPermissions.put(BibleService.ENTITY_DELETE, SecurityConstants.DELETE_OPERATION_DESCRIPTION_CN + entityName);
//        initialAdminPermissions.put(BibleService.ENTITY_SEARCH, SecurityConstants.SEARCH_OPERATION_DESCRIPTION_CN + entityName);
//
//
//        permissionCreationService.initialAdminPermissions(initialAdminPermissions);

        alreadySetup = true;
    }


}
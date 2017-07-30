package com.minlia.cloud.data.batis.support.web;

import com.minlia.cloud.body.query.Combo;
import com.minlia.cloud.body.query.ComboData;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractDataEntity;
import com.minlia.cloud.data.batis.support.repository.JpaCustomeRepository;
import com.minlia.cloud.data.batis.support.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AbstractTreeEndpoint<SERVICE extends TreeService, ENTITY extends AbstractDataEntity> extends AbstractBaseEndpoint<SERVICE, ENTITY> {

    @Autowired
    protected SERVICE service;

    @Autowired
    JpaCustomeRepository jpaCustomeRepository;

    @ResponseBody
    @RequestMapping(value = "checkByProperty", method = RequestMethod.GET)
    public synchronized boolean checkByProperty(ENTITY entity) {
        return service.doCheckByProperty(entity);
    }

    @ResponseBody
    @RequestMapping(value = "checkByPK", method = RequestMethod.GET)
    public synchronized boolean checkByPK(ENTITY entity) {
        return service.doCheckByPK(entity);
    }

    @RequestMapping(value = "findJson")
    public void findJson(Combo combo, HttpServletResponse response) {
        List<ComboData> comboDataList = jpaCustomeRepository.findJson(combo);
        writeJsonHttpResponse(comboDataList, response);
    }

}

package com.minlia.cloud.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.query.body.QueryRequestBody;
import com.minlia.cloud.query.specification.batis.body.ApiQueryRequestBody;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApiReadOnlyEndpoint<BODY extends QueryRequestBody, RESOURCE, PK> {

    /**
     * 查找所有并以Page方式返回
     */
    StatefulBody findAll(@RequestBody ApiQueryRequestBody<BODY> body, @PageableDefault Pageable pageable);

    /**
     * 查找所有并以List方式返回
     */
    StatefulBody findAll(@RequestBody ApiQueryRequestBody<BODY> body);

    /**
     * 根据ID查找并返回单个对象
     */
    StatefulBody findOne(@PathVariable PK id);

}
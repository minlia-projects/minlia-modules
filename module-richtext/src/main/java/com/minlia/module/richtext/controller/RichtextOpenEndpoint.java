//package com.minlia.module.richtext.controller;
//
//import com.minlia.cloud.body.Response;
//import com.minlia.cloud.constant.ApiPrefix;
//import com.minlia.module.richtext.bean.RichtextCro;
//import com.minlia.module.richtext.bean.RichtextUro;
//import com.minlia.module.richtext.service.RichtextService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@Api(tags = "System Rich Text Test")
//@RestController
//@RequestMapping(value = ApiPrefix.OPEN + "richtext")
//@Profile("dev")
//public class RichtextOpenEndpoint {
//
//    @Autowired
//    private RichtextService richtextService;
//
//    @ApiOperation(value = "create")
//    @PostMapping(value = "create")
//    public Response create(@Valid @RequestBody RichtextCro cro) {
//        return Response.success(richtextService.create(cro));
//    }
//
//    @ApiOperation(value = "update")
//    @PutMapping(value = "update")
//    public Response update(@Valid @RequestBody RichtextUro uro) {
//        return Response.success(richtextService.update(uro));
//    }
//
//    @ApiOperation(value = "delete")
//    @DeleteMapping(value = "delete/{id}")
//    public Response update(@PathVariable Long id) {
//        richtextService.delete(id);
//        return Response.success();
//    }
//
//    @ApiOperation(value = "id")
//    @GetMapping(value = "{id}")
//    public Response findOne(@PathVariable Long id) {
//        return Response.success(richtextService.queryById(id));
//    }
//
//    @ApiOperation(value = "list")
//    @PostMapping(value = "list")
//    public Response list(@RequestBody RichtextQRO qro) {
//        return Response.success(richtextService.queryList(qro));
//    }
//
//    @ApiOperation(value = "page")
//    @PostMapping(value = "page")
//    public Response page(@RequestBody RichtextQRO qro) {
//        return Response.success(richtextService.queryPage(qro));
//    }
//
//}
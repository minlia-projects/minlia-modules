//package com.minlia.cloud.endpoint;
//
//import com.minlia.cloud.body.StatefulBody;
//import com.minlia.cloud.body.impl.SuccessResponseBody;
//import com.minlia.cloud.code.ApiCode;
//import com.minlia.cloud.service.ReadWriteService;
//import com.minlia.cloud.utils.ApiPreconditions;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Persistable;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.validation.Valid;
//import java.io.Serializable;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//
///**
// * Abstract REST backend using a service implementation
// * <p/>
// * <p>You should extend this class when you want to use a 3 layers pattern : Repository, Service and Controller
// * If you don't have a real service (also called business layer), consider using RepositoryBasedRestController</p>
// * <p/>
// * <p>Default implementation uses "id" field (usually a PK) in order to identify resources in web request.
// * If your want to identity resources by a slug (human readable identifier), your should override findOne() method with for example :
// * <p/>
// * <pre>
// * <code>
// * {@literal @}Override
// * public Sample findOne({@literal @}PathVariable String id) {
// * Sample sample = service.findByName(id);
// * if (sample == null) {
// * throw new NotFoundException();
// * }
// * return sample;
// * }
// * </code>
// * </pre>
// *
// * @param <ENTITY> Your resource class to manage, maybe an entity or DTO class
// */
//@Slf4j
//public abstract class AbstractApiEndpoint<SERVICE extends ReadOnlyService<DAO,ENTITY,PK>, ENTITY extends Persistable<PK>,PK extends Serializable>  implements
//        ApiEndpoint<ENTITY,PK> {
//
//    @Autowired
//    protected  SERVICE service;
//
//    public AbstractApiEndpoint() {
//        Class<?> c = getClass();
//        Type type = c.getGenericSuperclass();
//        if (type instanceof ParameterizedType) {
//            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
//            Class<Persistable> clz=(Class<Persistable>)parameterizedType[1];
//
//        }
//    }
//
//    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
////    @RequestMapping( method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody create(@Valid @RequestBody ENTITY resource) {
//        return SuccessResponseBody.builder().payload(service.save(resource)).build();
//    }
//
//    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
////    @RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody update(@PathVariable PK id, @Valid @RequestBody ENTITY resource) {
//        ApiPreconditions.checkNotNull(id, ApiCode.NOT_FOUND);
//        ENTITY retreivedResource = (ENTITY) service.getOne(id);
//        ApiPreconditions.checkNotNull(retreivedResource, ApiCode.NOT_FOUND);
//        return SuccessResponseBody.builder().payload(service.update(resource)).build();
//    }
//
////    @ApiOperation(value = "获取分页后的结果", notes = "获取分页后的结果", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
////    @RequestMapping(value="findPaginated",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
////    public ApiResponseBody findPaginated(Pageable pageable) {
////        Page<T> paginated = service.findAll(pageable);
////        return SuccessResponseBody.builder().payload(paginated).build();
////    }
//
////    @ApiOperation(value = "获取所有结果", notes = "获取所有结果", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
////    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
////    public ApiResponseBody findAll() {
////        List<T> allEntries = service.findAll();
////        return SuccessResponseBody.builder().payload(allEntries).build();
////    }
//
//    @ApiOperation(value = "获取一个指定ID的实体", notes = "获取一个指定ID的实体", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
////    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody findOne(@PathVariable PK id) {
//        ApiPreconditions.checkNotNull(id, ApiCode.NOT_FOUND);
//        ENTITY retreivedResource = (ENTITY) service.getOne(id);
//        ApiPreconditions.checkNotNull(retreivedResource, ApiCode.NOT_FOUND);
//        return SuccessResponseBody.builder().payload(retreivedResource).build();
//    }
//
//    @ApiOperation(value = "删除指定ID的实体", notes = "删除指定ID的实体", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
////    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody delete(@PathVariable PK id) {
//        service.delete(id);
//        return SuccessResponseBody.builder().message("OK").build();
//    }
//
////    @ApiOperation(value = "查询并分页", notes = "查询并分页", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
////    public StatefulBody searchPageable(@RequestBody JpaApiSearchRequestBody body , @PageableDefault Pageable pageable) {
////        Page<ENTITY> found=service.findPageByBody(body,pageable);
////        return SuccessResponseBody.builder().message("OK").payload(found).build();
////    }
////
////    @ApiOperation(value = "查询值集", notes = "查询值集", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
////    public StatefulBody searchList(@RequestBody ApiQueryRequestBody body) {
////        List<ENTITY> found=service.findListByBody(body);
////        return SuccessResponseBody.builder().message("OK").payload(found).build();
////    }
//
//}

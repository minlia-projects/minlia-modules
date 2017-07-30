package com.minlia.cloud.data.jpa.support.web;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.data.jpa.support.service.IService;
import com.minlia.cloud.utils.ApiPreconditions;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Persistable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * Abstract REST endpoint using a service implementation
 * <p/>
 * <p>You should extend this class when you want to use a 3 layers pattern : Repository, Service and Controller
 * If you don't have a real service (also called business layer), consider using RepositoryBasedRestController</p>
 * <p/>
 * <p>Default implementation uses "id" field (usually a Long) in order to identify resources in web request.
 * If your want to identity resources by a slug (human readable identifier), your should override findOne() method with for example :
 * <p/>
 * <pre>
 * <code>
 * {@literal @}Override
 * public Sample findOne({@literal @}PathVariable String id) {
 * Sample sample = this.getService().findByName(id);
 * if (sample == null) {
 * throw new NotFoundException();
 * }
 * return sample;
 * }
 * </code>
 * </pre>
 *
 * @param <T> Your resource class to manage, maybe an entity or DTO class
 */
@Slf4j
public abstract class AbstractApiEndpoint<T extends Persistable<Long>> implements
        ApiEndpoint<T> {

    private IService service;

    protected abstract IService<T> getService();

    /**
     * You should override this setter in order to inject your service with @Inject annotation
     *
     * @param service The service to be injected
     */
    public  void setService(IService service) {
        this.service= service;
    }

    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping( method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody create(@Valid @RequestBody T resource) {
        return SuccessResponseBody.builder().payload(this.getService().create(resource)).build();
    }

    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@PathVariable Long id, @Valid @RequestBody T resource) {
        ApiPreconditions.checkNotNull(id, ApiCode.NOT_FOUND);
        T retreivedResource = (T) this.getService().findOne(id);
        ApiPreconditions.checkNotNull(retreivedResource, ApiCode.NOT_FOUND);
        return SuccessResponseBody.builder().payload(this.getService().update(resource)).build();
    }

//    @ApiOperation(value = "获取分页后的结果", notes = "获取分页后的结果", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value="findPaginated",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ApiResponseBody findPaginated(Pageable pageable) {
//        Page<T> paginated = this.getService().findAll(pageable);
//        return SuccessResponseBody.builder().payload(paginated).build();
//    }

//    @ApiOperation(value = "获取所有结果", notes = "获取所有结果", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ApiResponseBody findAll() {
//        List<T> allEntries = this.getService().findAll();
//        return SuccessResponseBody.builder().payload(allEntries).build();
//    }

    @ApiOperation(value = "获取一个指定ID的实体", notes = "获取一个指定ID的实体", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody findOne(@PathVariable Long id) {
        ApiPreconditions.checkNotNull(id, ApiCode.NOT_FOUND);
        T retreivedResource = (T) this.getService().findOne(id);
        ApiPreconditions.checkNotNull(retreivedResource, ApiCode.NOT_FOUND);
        return SuccessResponseBody.builder().payload(retreivedResource).build();
    }

    @ApiOperation(value = "删除指定ID的实体", notes = "删除指定ID的实体", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody delete(@PathVariable Long id) {
        this.getService().delete(id);
        return SuccessResponseBody.builder().message("OK").build();
    }
}

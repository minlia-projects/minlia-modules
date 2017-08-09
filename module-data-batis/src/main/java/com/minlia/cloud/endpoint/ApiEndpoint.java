package com.minlia.cloud.endpoint;

import com.minlia.cloud.body.StatefulBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * REST endpoint interface
 *
 * @param <ENTITY> Your resource Domain to manage, maybe an entity or DTO class
 */
public interface ApiEndpoint<ENTITY,PK> {

    /**
     * Create a new resource<br />
     * REST webservice published : POST /
     *
     * @param resource The resource to create
     * @return CREATED http status code if the request has been correctly processed, with updated resource enclosed in the body, usually with and additional identifier automatically created by the database
     */

    StatefulBody create(@Valid @RequestBody ENTITY resource);

    /**
     * Update an existing resource<br/>
     * REST webservice published : PUT /{id}
     *
     * @param id       The identifier of the resource to update, usually a PK or String identifier. It is explicitely provided in order to handle cases where the identifier could be changed.
     * @param resource The resource to update
     * @return OK http status code if the request has been correctly processed, with the updated resource enclosed in the body
     */

    StatefulBody update(@PathVariable PK id, @Valid @RequestBody ENTITY resource);

    /**
     * Find all resources, and return a paginated and optionaly sorted collection<br/>
     * REST webservice published : GET /search?page=0&size=20 or GET /search?page=0&size=20&direction=desc&properties=name
     */
//    ApiResponseBody findPaginated(Pageable pageable);


    /**
     * Find all resources, and return the full collection (plain list not paginated)<br/>
     * REST webservice published : GET /?page=no
     *
     * @return OK http status code if the request has been correctly processed, with the list of all resource enclosed in the body.
     * Be careful, this list should be big since it will return ALL resources. In this case, consider using paginated findAll method instead.
     */
//    ApiResponseBody findAll();


    /**
     * Find a resource by its identifier<br/>
     * REST webservice published : GET /{id}
     *
     * @param id The identifier of the resouce to find
     * @return OK http status code if the request has been correctly processed, with resource found enclosed in the body
     */
    StatefulBody findOne(@PathVariable PK id);


    /**
     * Delete a resource by its identifier<br />
     * REST webservice published : DELETE /{id}<br />
     * Return No Content http status code if the request has been correctly processed
     *
     * @param id The identifier of the resource to delete
     */
    StatefulBody delete(@PathVariable PK id);


//    public StatefulBody searchPageable(@RequestBody JpaApiSearchRequestBody body , @PageableDefault Pageable pageable);

//    public StatefulBody searchList(@RequestBody BatisApiSearchRequestBody body);
}
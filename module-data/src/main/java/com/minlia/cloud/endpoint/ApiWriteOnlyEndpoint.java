package com.minlia.cloud.endpoint;

import com.minlia.cloud.body.StatefulBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface ApiWriteOnlyEndpoint<RESOURCE, PK> extends ApiEndpoint<RESOURCE, PK> {

    /**
     * Create a new resource<br />
     * REST webservice published : POST /
     *
     * @param resource The resource to create
     * @return CREATED http status code if the request has been correctly processed, with updated resource enclosed in the body, usually with and additional identifier automatically created by the database
     */

    StatefulBody create(@Valid @RequestBody RESOURCE resource);

    /**
     * Update an existing resource<br/>
     * REST webservice published : PUT /{id}
     *
     * @param id       The identifier of the resource to update, usually a PK or String identifier. It is explicitely provided in order to handle cases where the identifier could be changed.
     * @param resource The resource to update
     * @return OK http status code if the request has been correctly processed, with the updated resource enclosed in the body
     */

    StatefulBody update(@Valid @RequestBody RESOURCE resource);

    /**
     * Delete a resource by its identifier<br />
     * REST webservice published : DELETE /{id}<br />
     * Return No Content http status code if the request has been correctly processed
     *
     * @param id The identifier of the resource to delete
     */
    StatefulBody delete(@PathVariable PK id);

}
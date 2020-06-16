package com.minlia.app.demo.nestedset.endpoint;

/*-
 * #%L
 * minlia
 * %%
 * Copyright (C) 2005 - 2020 Minlia Team
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import com.minlia.app.demo.nestedset.entity.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.minlia.module.nestedset.NestedNodeRepository;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/cat")
public class RouteEndpoint {

    private final NestedNodeRepository<Long, Team> repository;

    public RouteEndpoint(NestedNodeRepository<Long, Team> repository) {
        this.repository = repository;
    }

    @GetMapping(value = "ok")
    public List ok() {
        log.debug("OK");

        Team entity = new Team();
        entity.setName("1第一级分类");
        entity.setDiscriminator("tree_1");
        repository.insertAsFirstRoot(entity);


        Team entity13 = new Team();
        entity13.setName("13第二级分类");
        entity13.setDiscriminator("tree_1");
        repository.insertAsFirstChildOf(entity13, entity);

        Team entity2 = new Team();
        entity2.setName("1第二级分类");
        entity2.setDiscriminator("tree_1");
        repository.insertAsFirstChildOf(entity2, entity);

        Team entity3 = new Team();
        entity3.setName("3第二级分类");
        entity3.setDiscriminator("tree_1");
        repository.insertAsFirstChildOf(entity3, entity2);


        List<Team> list = repository.getTreeAsList(entity3);
        return list;
    }
}

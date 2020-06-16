package com.minlia.app.demo.nestedset.endpoint;


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

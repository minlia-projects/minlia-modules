package com.minlia.module.richtext.v1.service;

import com.minlia.boot.utils.ApiPreconditions;
import com.minlia.boot.v1.code.ApiCode;
import com.minlia.module.richtext.v1.body.RichtextCreateBody;
import com.minlia.module.richtext.v1.body.RichtextQueryBody;
import com.minlia.module.richtext.v1.body.RichtextUpdateBody;
import com.minlia.module.richtext.v1.domain.Richtext;
import com.minlia.module.richtext.v1.repository.RichtextRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RichtextServiceImpl implements RichtextService {

    @Autowired
    private Mapper mapper;
    @Autowired
    private RichtextRepository repository;

    @Override
    public Richtext create(RichtextCreateBody body) {
        Richtext richtext = mapper.map(body,Richtext.class);
        return repository.save(richtext);
    }

    @Override
    public Richtext update(RichtextUpdateBody body) {
        Richtext richtext = repository.findOne(body.getId());
        ApiPreconditions.is(null == richtext, ApiCode.NOT_FOUND,"记录不存在");
        mapper.map(body,richtext);
        return repository.save(richtext);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public long count(RichtextQueryBody body) {
        return repository.count(specBuilder(body));
    }

    @Override
    public Richtext findOne(RichtextQueryBody body) {
        return repository.findOne(specBuilder(body));
    }

    @Override
    public List<Richtext> findList(RichtextQueryBody body) {
        return repository.findAll(specBuilder(body));
    }

    @Override
    public Page<Richtext> findPage(RichtextQueryBody body, Pageable pageable) {
        return repository.findAll(specBuilder(body),pageable);
    }

    private Specification<Richtext> specBuilder(RichtextQueryBody body){
        Specification<Richtext> spec = new Specification<Richtext>() {
            public Predicate toPredicate(Root<Richtext> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (null != body.getId())
                    list.add(cb.equal(root.get("id").as(Long.class), body.getId()));
                if (null != body.getType())
                    list.add(cb.equal(root.get("type").as(String.class), body.getType()));
                if (null != body.getTitle())
                    list.add(cb.like(root.get("title").as(String.class), body.getTitle()));
                if (null != body.getCode())
                    list.add(cb.equal(root.get("code").as(String.class), body.getCode()));

                query.orderBy(cb.asc(root.get("id").as(Long.class)));

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return spec;
    }

}

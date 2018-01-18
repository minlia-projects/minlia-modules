package com.minlia.module.tencent.miniapp.service;

import com.minlia.module.tencent.miniapp.body.WechatOpenAccountQueryBody;
import com.minlia.module.tencent.miniapp.domain.WechatOpenAccount;
import com.minlia.module.tencent.miniapp.repository.WechatOpenAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WechatOpenAccountServiceImpl implements WechatOpenAccountService {

    @Autowired
    private WechatOpenAccountRepository repository;

    @Override
    public WechatOpenAccount create(WechatOpenAccount openAccount) {
        return repository.save(openAccount);
    }

    @Override
    public WechatOpenAccount update(WechatOpenAccount openAccount) {
        return repository.save(openAccount);
    }

    @Override
    public void updateUserByUnionId(Long userId, String unionId) {
        repository.updateUserByUnionId(userId,unionId);
    }

    @Override
    public long countByUnionIdAndUserIdIsNotNull(String unionId) {
        return repository.countByUnionIdAndUserIdIsNotNull(unionId);
    }

    @Override
    public List<WechatOpenAccount> findByUnionIdAndUserIdIsNotNull(String unionId) {
        return repository.findByUnionIdAndUserIdIsNotNull(unionId);
    }

    @Override
    public WechatOpenAccount findOne(WechatOpenAccountQueryBody body) {
        return repository.findOne(specBuilder(body));
    }

    @Override
    public List<WechatOpenAccount> findList(WechatOpenAccountQueryBody body) {
        return repository.findAll(specBuilder(body));
    }

    private Specification<WechatOpenAccount> specBuilder(WechatOpenAccountQueryBody requestBody){
        Specification<WechatOpenAccount> spec = new Specification<WechatOpenAccount>() {
            public Predicate toPredicate(Root<WechatOpenAccount> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (null != requestBody.getUserId())
                    list.add(cb.equal(root.get("userId"), requestBody.getUserId()));
                if (null != requestBody.getUnionId())
                    list.add(cb.equal(root.get("unionId"), requestBody.getUnionId()));
                if (null != requestBody.getOpenId())
                    list.add(cb.equal(root.get("openId"), requestBody.getOpenId()));
                if (null != requestBody.getWxCode())
                    list.add(cb.equal(root.get("wxCode"), requestBody.getWxCode()));
                if (null != requestBody.getOpenType())
                    list.add(cb.equal(root.get("openType"), requestBody.getOpenType()));
                if (null != requestBody.getOpenSubitem())
                    list.add(cb.equal(root.get("openSubitem"), requestBody.getOpenSubitem()));
                if (null != requestBody.getIsSubscribe())
                    list.add(cb.equal(root.get("isSubscribe"), requestBody.getIsSubscribe()));

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return spec;
    }

}

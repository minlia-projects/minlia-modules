package com.minlia.module.riskcontrol.service;

import com.alibaba.fastjson.JSON;
import com.minlia.module.redis.config.RedisFactory;
import com.minlia.module.riskcontrol.constant.RiskConstants;
import com.minlia.module.riskcontrol.entity.RiskDrools;
import com.minlia.module.riskcontrol.repository.RiskDroolsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author garen
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RiskDroolsService {

    private final RiskDroolsRepository riskDroolsRepository;
    private static RedisTemplate<String, Object> redisTemplate = RedisFactory.get(RiskConstants.Cache.DB_INDEX);

    @PostConstruct
    public void init() {
        reset();
    }

    public void reset() {
        log.info("风控规者缓存更新开始");
        redisTemplate.delete(RiskConstants.Cache.DROOL);
        List<RiskDrools> drools = riskDroolsRepository.findAll(Example.of(RiskDrools.builder().disFlag(false).build()));
        for (RiskDrools riskDrools : drools) {
            redisTemplate.opsForHash().put(RiskConstants.Cache.DROOL, riskDrools.getRuleKey(), riskDrools);
        }
        log.info("风控规者缓存更新成功");
    }

    public void publish(RiskDrools riskDrools) {
        this.save(riskDrools);
        if (!riskDrools.getDisFlag()) {
            redisTemplate.opsForHash().put(RiskConstants.Cache.DROOL, riskDrools.getRuleKey(), riskDrools);
        }
    }

    public void delete(Long id) {
        RiskDrools riskDrools = riskDroolsRepository.getOne(id);
        riskDroolsRepository.deleteById(id);
        redisTemplate.opsForHash().delete(RiskConstants.Cache.DROOL, riskDrools.getRuleKey());
    }

    private void save(RiskDrools riskDrools) {
        if (riskDroolsRepository.exists(Example.of(RiskDrools.builder().ruleKey(riskDrools.getRuleKey()).build()))) {
            log.info("黑名单已存在，{}", JSON.toJSONString(riskDrools));
        } else {
            riskDroolsRepository.save(riskDrools);
            log.info("黑名单添加成功，{}", JSON.toJSONString(riskDrools));
        }
    }

    public RiskDrools get(String key) {
        return (RiskDrools) redisTemplate.opsForHash().get(RiskConstants.Cache.DROOL, key);
    }

    public List<RiskDrools> findAll(RiskDrools riskDrools) {
        return riskDroolsRepository.findAll(Example.of(riskDrools));
    }

}
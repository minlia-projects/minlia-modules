package com.minlia.module.riskcontrol.service;

import com.minlia.module.redis.config.RedisFactory;
import com.minlia.module.riskcontrol.constant.RiskConstants;
import com.minlia.module.riskcontrol.entity.RiskList;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 黑名单 服务类
 *
 * @author garen
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RiskBlackIpService {

    private final RiskListService riskListService;
    private static RedisTemplate<String, Object> redisTemplate = RedisFactory.get(RiskConstants.Cache.DB_INDEX);

    @PostConstruct
    public void init() {
        reset();
    }

    public void reset() {
        log.info("黑名单缓存更新开始");
        redisTemplate.delete(RiskConstants.Cache.BLACK_IP);
        List<RiskList> riskLists = riskListService.findByTypeAndDimension(RiskTypeEnum.BLACK, RiskList.EnumDimension.IP);
        if (CollectionUtils.isNotEmpty(riskLists)) {
            RedisFactory.get(RiskConstants.Cache.DB_INDEX).opsForSet().add(RiskConstants.Cache.BLACK_IP, riskLists.stream().map(RiskList::getValue).collect(Collectors.toList()).toArray());
        }
        log.info("黑名单缓存更新完成");
    }

    public void publish(String value) {
        redisTemplate.opsForSet().add(RiskConstants.Cache.BLACK_IP, value);
    }

    public void delete(String value) {
        RedisFactory.get(1).opsForSet().remove(RiskConstants.Cache.BLACK_IP, value);
    }

    public boolean match(String value) {
        return redisTemplate.opsForSet().isMember(RiskConstants.Cache.BLACK_IP, value);
    }

}
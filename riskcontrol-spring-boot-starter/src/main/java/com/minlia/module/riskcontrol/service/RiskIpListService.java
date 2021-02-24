package com.minlia.module.riskcontrol.service;

import com.alibaba.fastjson.JSON;
import com.minlia.cloud.constant.SymbolConstants;
import com.minlia.module.riskcontrol.entity.RiskIpList;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import com.minlia.module.riskcontrol.event.RiskIplistReloadEvent;
import com.minlia.module.riskcontrol.repository.RiskIpListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RiskIpListService {

    public static String channel = "risk:iplist";

    private Map<Long, RiskIpList> blackListMap;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RiskIpListRepository riskIpListRepository;

    @PostConstruct
    public void init() {
//        new Thread(() -> {
//            redisDao.subscribe(new JedisPubSub() {
//                @Override
//                public void onMessage(String pchannel, String message) {
//                    log.info("redis通知，channel={},message={}", pchannel, message);
//                    if (channel.equals(pchannel)) {
//                        updateCache();
//                    }
//                }
//            }, channel);
//        }).start();
        updateCache();
    }

    public void pub(RiskIpList riskIpList) {
        add(riskIpList);
        redisTemplate.convertAndSend(channel, "");
    }

    public List<RiskIpList> getAll() {
        return blackListMap.values().stream().collect(Collectors.toList());
    }

    public void updateCache() {
        List<RiskIpList> riskIpLists = riskIpListRepository.findAllByDisFlag(false);
        blackListMap = riskIpLists.stream().collect(Collectors.toConcurrentMap(RiskIpList::getId, Function.identity()));
        log.info("黑名单缓存更新成功");

        RiskIplistReloadEvent.onReload();
    }

    public void add(RiskIpList riskIpList) {
        if (riskIpListRepository.existsAllByCountryAndStart(riskIpList.getCountry(), riskIpList.getStart())) {
            log.info("黑名单已存在，{}", JSON.toJSONString(riskIpList));
        } else {
            riskIpListRepository.save(riskIpList);
            log.info("黑名单添加成功，{}", JSON.toJSONString(riskIpList));
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id) {
        riskIpListRepository.deleteById(id);
        redisTemplate.convertAndSend(channel, "");
    }

    public RiskIpList queryById(Long id) {
        return riskIpListRepository.findById(id).get();
    }

    public List<RiskIpList> queryAll(RiskIpList riskIpList) {
        return riskIpListRepository.findAll(Example.of(riskIpList));
    }

//    /**
//     * 是否存在
//     *
//     * @param type
//     * @param value
//     * @return
//     */
//    public boolean exists(RiskTypeEnum type, String value) {
//        return riskIpListRepository.existsAllByCountryAndStart(riskIpList.getCountry(), riskIpList.getStart());
//    }

    public boolean contain(RiskTypeEnum enumType, String ip) {
        Long ipNumber = ipToNumber(ip);
        for (Map.Entry<Long, RiskIpList> entry : blackListMap.entrySet()) {
            if (entry.getValue().getType().equals(enumType) && ipNumber >= entry.getValue().getStart() && ipNumber <= entry.getValue().getEnd()) {
                return true;
            }
        }
        return false;
    }

    private static Long ipToNumber(String ip) {
        String[] ips = ip.split(SymbolConstants.DOT_ZY);
        Long number = Long.valueOf(ips[0]) * (256 * 256 * 256) + Long.valueOf(ips[1]) * (256 * 256) + Long.valueOf(ips[2]) * (256) + Long.valueOf(ips[3]);
        return number;
    }

    public static void main(String[] args) {
        Long l = ipToNumber("172.0.0.1");
        System.out.println(l);
        Long l1 = ipToNumber("172.255.255.255");
        System.out.println(l1);
    }

}
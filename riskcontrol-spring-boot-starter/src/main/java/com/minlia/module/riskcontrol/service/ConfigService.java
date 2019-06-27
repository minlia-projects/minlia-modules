package com.minlia.module.riskcontrol.service;

import com.minlia.module.riskcontrol.entity.RiskConfig;
import com.minlia.module.riskcontrol.repository.RiskConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Slf4j
@Service
public class ConfigService {

    private Map<String, RiskConfig> configMap;

    @Autowired
    private RiskConfigRepository riskConfigRepository;

    public List<RiskConfig> queryAll() {
        return riskConfigRepository.findAll();
    }

    public void save(RiskConfig riskConfig) {
        riskConfigRepository.save(riskConfig);
    }

    public String get(String key) {
        return "ON";
    }

}

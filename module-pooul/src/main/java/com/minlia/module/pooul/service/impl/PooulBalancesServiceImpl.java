package com.minlia.module.pooul.service.impl;

import com.minlia.module.pooul.bean.dto.PooulBlancesDTO;
import com.minlia.module.pooul.bean.dto.PooulDTO;
import com.minlia.module.pooul.bean.to.PooulInternalTransfersTO;
import com.minlia.module.pooul.bean.to.PooulMerchantCTO;
import com.minlia.module.pooul.config.PooulProperties;
import com.minlia.module.pooul.service.PooulAuthService;
import com.minlia.module.pooul.service.PooulBalancesService;
import com.minlia.module.pooul.util.PooulToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by garen on 2018/9/6.
 */
@Slf4j
@Service
public class PooulBalancesServiceImpl implements PooulBalancesService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public PooulProperties pooulProperties;

    @Autowired
    private PooulAuthService pooulAuthService;

    @Override
    public PooulBlancesDTO queryBalances(String merchantId) {
        HttpEntity<PooulMerchantCTO> httpEntity = new HttpEntity(null, pooulAuthService.getHeaders());
        ResponseEntity<PooulBlancesDTO> responseEntity = restTemplate.exchange(pooulProperties.getHost() + query_balances_url, HttpMethod.GET, httpEntity, PooulBlancesDTO.class, merchantId);
        return responseEntity.getBody();
    }

    @Override
    public Object queryHistory(String merchantId) {
        return null;
    }

    @Override
    public PooulDTO internalTransfers(String merchantId, PooulInternalTransfersTO transfersTO) {
        //判断付款方余额是否足够 TODO
        PooulBlancesDTO blancesDTO = this.queryBalances(transfersTO.getPayer_merchant_id());
        if (blancesDTO.isSuccess()) {
            if (CollectionUtils.isEmpty(blancesDTO.getData()) || blancesDTO.getData().get(0).getBalance() < transfersTO.getAmount()) {
//                return Response.failure("pooul.transfer.message.100000");
            }
        }
        Map map = new BeanMap(transfersTO);
        String token = PooulToken.create(map);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity httpEntity = new HttpEntity(token,headers);
        PooulDTO pooulDTO = restTemplate.postForObject(pooulProperties.getHost() + internal_transfers_url + merchantId, httpEntity, PooulDTO.class);
        return pooulDTO;
    }

}

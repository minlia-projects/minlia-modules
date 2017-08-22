package com.minlia.module.bible.v1.service;

import com.minlia.boot.utils.ApiPreconditions;
import com.minlia.boot.v1.code.ApiCode;
import com.minlia.module.bible.v1.domain.BibleItem;
import com.minlia.module.bible.v1.query.BibleItemSearchRequestBody;
import com.minlia.module.bible.v1.repository.BibleItemRepository;
import com.minlia.module.bible.v1.repository.BibleRepository;
import com.minlia.module.data.query.v2.DynamicSpecifications;
import com.minlia.module.data.query.v2.Operator;
import com.minlia.module.data.query.v2.QueryCondition;
import com.minlia.module.data.query.v2.body.ApiSearchRequestBody;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by will on 6/28/17.
 * 提供公开查询方法
 *
 */
@Service
public class BibleOpenSearchService  {

    @Autowired
    DynamicSpecifications spec;

    @Autowired
    BibleItemRepository bibleItemRepository;

    /**
     * 根据BIBLE CODE key 查询出配置值
     * @param bibleCode
     * @param key
     * @return
     */
    public String get(String bibleCode,String key){
        String value="";
        //校验不可为空
        ApiPreconditions.checkNotNull(bibleCode, ApiCode.NOT_NULL);
        ApiPreconditions.checkNotNull(key, ApiCode.NOT_NULL);

        ApiSearchRequestBody<BibleItemSearchRequestBody> body=new ApiSearchRequestBody<>();
        body.getConditions().add(new QueryCondition("bible.code", Operator.eq, bibleCode));
        body.getConditions().add(new QueryCondition("code", Operator.eq, key));
        List<BibleItem> bibleItemList= bibleItemRepository.findAll(this.spec.buildSpecification(body));

        if(!CollectionUtils.isEmpty(bibleItemList)){
            for(BibleItem bibleItem:bibleItemList){
                String codeFound=bibleItem.getCode();
                if(codeFound.equalsIgnoreCase(key)){
                    value= bibleItem.getLabel();
                    break;
                }
            }
        }
        ApiPreconditions.checkNotNull(value,ApiCode.NOT_FOUND);
        return value;
    }
}

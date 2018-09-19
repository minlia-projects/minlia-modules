package com.minlia.modules.qcloud.faceid.body;

import com.minlia.module.data.body.QueryRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2018/4/19.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QcloudFaceIdRecordQueryRequestBody implements QueryRequestBody{

    private String userId;

    private String orderNo;

    private Boolean isAuth;

    private Long id;

    private String idNo;

}

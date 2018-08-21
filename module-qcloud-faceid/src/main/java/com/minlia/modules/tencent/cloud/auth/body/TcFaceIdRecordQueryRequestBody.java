package com.minlia.modules.tencent.cloud.auth.body;

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
public class TcFaceIdRecordQueryRequestBody implements QueryRequestBody{

    private String userId;

    private String orderNo;

    private Boolean isAuth;

    private Long id;

    private String idNo;

}

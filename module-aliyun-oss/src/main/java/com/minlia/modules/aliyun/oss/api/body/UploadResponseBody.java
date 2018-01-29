package com.minlia.modules.aliyun.oss.api.body;

import com.minlia.cloud.body.StatefulBody;
import lombok.Data;

/**
 * Created by garen on 2017/8/30.
 */
@Data
public class UploadResponseBody extends StatefulBody {

    private String link;

}

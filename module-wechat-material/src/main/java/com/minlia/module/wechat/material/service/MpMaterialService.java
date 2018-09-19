package com.minlia.module.wechat.material.service;


import com.minlia.cloud.body.Response;

public interface MpMaterialService {

//    String MEDIA_GET_URL = "https://api.weixin.qq.com/cgi-bin/media/get";
//    String MEDIA_UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?type=%s";
//    String IMG_UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
//    String NEWS_ADD_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news";
//    String NEWS_UPDATE_URL = "https://api.weixin.qq.com/cgi-bin/material/update_news";

//    String MATERIAL_ADD_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?type=%s";
//    String MATERIAL_DEL_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material";
    String MATERIAL_GET_URL = "https://api.weixin.qq.com/cgi-bin/material/get_material";
    String MATERIAL_BATCHGET_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
//    String MATERIAL_GET_COUNT_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";

    void init(String type, Integer offset, Integer count);

    /**
     * 获取图文永久素材的信息
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738730
     * 请求方式: POST
     * 接口url：https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN
     * @param mediaId 永久素材的id
     */
    Response materialGet(String mediaId);

    /**
     * 获取永久素材列表
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738734
     * 请求方式: POST
     * 接口url: https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN
     * @param type   媒体类型, 请看{@link me.chanjar.weixin.common.api.WxConsts}
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count  返回素材的数量，取值在1到20之间
     */
    Response materialBatchGet(String type, Integer offset, Integer count);

}

package com.minlia.module.lbsyun.body.request;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * 创建表（create geotable）接口 请求体
 * Created by garen on 2018/6/9.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoPoiDeleteRequest implements ApiRequestBody{

    private Long id;

    /**
     * id列表以,分隔的id
     * 可选。最多1000个id，如果设置了批量删除is_total_del = 1并且没有设置id字段，则优先根据ids删除多条poi, 其它条件将被忽略.
     */
    private String ids;

    /**
     * 位置数据名称
     */
    @Size(max = 128)
    private String title;

    /**
     * 位置数据类别
     */
    @Size(max = 200)
    private String tags;

    /**
     * 查询的矩形区域
     * 可选。格式x1,y1;x2,y2分别代表矩形的左上角和右下角
     */
    @Size(max = 100)
    private String bounds;

    /**
     * 创建数据的对应数据表id
     * 必选
     */
    private String geotable_id;

    /**
     * 用户的访问权限key
     * 必选、string(50)
     */
    private String ak;

    /**
     * 标记为批量删除可选。
     * 如需删除一条以上数据，在设定数据范围的条件时，还需要将该字段设为1。
     * 注意：若仅设为1，而不设定数据范围的条件（如不指定ids、tag等），则默认为删除全表数据
     */
    private Integer is_total_del;

}

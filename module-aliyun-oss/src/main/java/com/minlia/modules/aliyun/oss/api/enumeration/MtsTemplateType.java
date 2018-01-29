package com.minlia.modules.aliyun.oss.api.enumeration;

/**
 * Created by garen on 2017/8/29.
 * aliyun MTS transcoding temmplate type enum.
 * FHD: 码率（kpbs） =<3800    分辨率 1920
 * HD:  码率（kpbs） =2000     分辨率 1280
 * SD:  码率（kpbs） =<1000    分辨率 848
 * LD:  码率（kpbs） =<600     分辨率 640
 * XLD: 码率（kpbs） =<300     分辨率 320
 */
public enum MtsTemplateType {

    MP4_FHD("S00000001-200040","mp4"),
    MP4_HD("S00000001-200030","mp4"),
    MP4_SD("S00000001-200020","mp4"),
    MP4_LD("S00000001-200010","mp4"),
    MP4_XLD("S00000001-200000","mp4"),

    FLV_FHD("S00000001-000040","mp4"),
    FLV_HD("S00000001-000030","mp4"),
    FLV_SD("S00000001-000020","mp4"),
    FLV_LD("S00000001-000010","mp4"),
    FLV_XLD("S00000001-000000","mp4");

    private String value;
    private String format;

    MtsTemplateType(String templateId, String format) {
        this.value = templateId;
        this.format = format;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}

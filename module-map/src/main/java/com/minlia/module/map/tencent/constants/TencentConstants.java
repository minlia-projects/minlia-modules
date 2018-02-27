package com.minlia.module.map.tencent.constants;

/**
 * Created by Calvin On 2017/12/17.
 */
public class TencentConstants {

    /**
     * 腾讯地图 Key
     */
    public static final String[] QQ_MAP_KEYS = {
            "MV6BZ-XZC3D-N5C4E-HY4QR-Z6ZV2-5DFFQ",
            "B6NBZ-F6VWR-AWLW3-WFYWF-DOGE6-7WFST",
            "YKYBZ-HZEWP-HHDD6-VANQR-2BCKE-GCBCK",
            "6MKBZ-WG2WD-B3O4R-HYWHH-IHLV6-L7BIO",
            "V6IBZ-RYOCU-MD7V4-4B4UW-6LVUO-BJFMR",
            "XHSBZ-NO3KQ-UXU5A-GFUDV-ILVSO-2RBUA",
            "XZCBZ-MQMLI-UE4GQ-5C335-6NO75-JEFDH",
            "CWXBZ-FM6L4-6RTUM-DQEFE-OAMZE-F3BX2",
            "ZOGBZ-3PBW5-2WNID-QSSNY-UKPD5-FPF25",
            "VW6BZ-2MWW6-L2BS6-MGLSB-BWKG2-B3BXU",
            "YSEBZ-RLCHU-MCPVC-4YYWU-WQN53-IJFGQ",
            "SW5BZ-DKFKU-4FWV2-2XH4U-XMGWZ-OYFGX",
            "HPNBZ-B426V-CZQPP-UN4R6-QYOF2-MYFU3",
            "COVBZ-PGML5-VPWIN-QR3OM-XS26O-24FPI",
            "KHRBZ-O4PC2-JKPUF-CN6JB-GKL3O-J6FHO",
            "PVVBZ-GIVKU-QPCVK-44VDG-GB35T-7DFSJ",
            "EFMBZ-DYICX-CCL4Z-ZO57V-I7BPQ-6UFMZ",
            "T3BBZ-6F2R6-5HJSR-MYHSY-D5V4J-32BMP",
            "FPOBZ-UT2K2-ZFYUC-CX67E-IOOYS-7XFQ6",
            "66CBZ-YJ464-3XZU5-XPLNL-F7QH7-XNFJL"
    };

    /**
     * 根据key, 获取腾讯地图-省、市、区 URL
     */
    public  static final String QQ_MAP_URL_LIST = "http://apis.map.qq.com/ws/district/v1/list?key=%s";

    /**
     * 获取腾讯地图-省下的子级、 市下的子级、区下的子级 URL
     */
    public static final String QQ_MAP_URL_GET_CHILDREN = "http://apis.map.qq.com/ws/district/v1/getchildren?&id=%s&key=%s";
}

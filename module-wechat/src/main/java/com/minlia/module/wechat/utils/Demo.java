package com.minlia.module.wechat.utils;

/**
 * @author zhonghongqiang
 *         Created on 2018-04-28.
 */
public class Demo {

    public static void main(String[] args) throws Exception {
//        String appId = "wx4f4bc4dec97d474b";
//        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
//        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
//        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
//        System.out.println(WXBizDataCrypt.decrypt(encryptedData,iv,sessionKey,appId));


        String appId = "wxb7fab50b2771d89f";

//        String sessionKey = "eeXntxf5eUddYXxslSAuUA==";
//        String encryptedData = "uwd0+SVo37T5Vj/4Jg6YrgCc8Q2SgA6Pm7qsty/rNALKa/HUGm3MPSUAlAGv6yVIraSj57PVL8yX4c+wB8x+1BTAJsnYDspWLSABnwf0GL66E/3t1iv7DsR9BdLUphZI66F3B/QuQBT12qKxZ6mBh4gF7GHMJXiVthTldg4ItwZO45IqKWVoKw5785E/x4FBH4HQevzQOxAkh2HBx+dat7vrccLJU3WCfzMQBbOWgzoXkerO2v9W2IiQ+11+l7/GorQBzmFr1UVuWXNNh469uIBcGxYyeb4dnlNq3H1NOfdv20I60TkhOuhkIo1QrQTUdqxpbX9w+gtSF28k+/1Wqu5KHnOo8KYWXGRH4Ed7kyjADTxsNZ9Spn6sMQ6cYGb3rY7LP3FdoARnEyhWGxLO2ypRvpu1E6+xaZq+m7Y/yNoQO0eZogNhlUb3GKQFEqoZLGT8OOTj5bM1/l+fymoxOJVdaIyxKsevqhXwOQjM0i6H8AGKEm/KX+m3T2jR19FHLWhr83AjVMWaCbzkQERQTRCilvyqIZhxiTilzse50+A=";
//        String iv = "gRu0rYF0Bw7RdemjP68ccQ==";

        String sessionKey = "OO5c1NsQFDVcB6pcSxTFKw==";
        String encryptedData = "Y0oSpv6chfE3xVelW/BVRIxhSWQpItFOCTBS83yz93f8N/qBik81sBwAXhemKIFtteFR8eLM65hCEUtFBBuj+kMqAHsWIbcLorY2k5+AaSiRfA+nujUBDD/VMQzlJyP0nzxYb91msePX8o40kR1kOdZ9keQRJ7RAe57jn1zSJhYUIgdGV4kgzbWYhGOKlMDJk8Xuej0YzgqCM9ACdZfoRiPxQeX9Uz1NnZSy75kPtyrRTPV/Oe16KNSGrkLSiBbu+K5RmS5xv9NPqyefxAb5GE176+2nuDSB57HVP2XULc81VKUcOGHswAMnzBeyNB0Np8Q3J4nlEPaVtW7fVF4VJwRnMPPztp82COO25TGNYBxD7jBXi2UUy0eOHwa+PlihucCM08to2BZU1F1KCsj0fl+HwY1QeJ0lyguclcBQnzo2JZKSqpFd5U5YI6YKnDQ53y8ffBJYQWySk4ANbtR0baNzXJV7ukfPrva2mZWgV5ogYkkQlr5rXXwy0xkzCWHIeyJwQbm1IazBQPL2CKOrlscKPDXZCGthLrvSqAXry0c=";
        String iv = "9J1ickI8foSI3VYw77GcaQ==";

//        String sessionKey = "OO5c1NsQFDVcB6pcSxTFKw==";
//        String encryptedData = "TnR+xSehcQzIIL3YFwOvj00Q/IpVvUq01EyJrgidDTIv657Fkgc/MTq5K2XoQitTUQPSStW+xRWpCcA442HPohVqDKxuXo6nJFhOIDadz34/o2E/a0sxXl1Ow4aH0eVZhMKKmoHBXWJI675hl5HYiaqLuk5APxpEXNz9WhvQkFg06pFDeGi+c6mBh4C3omWeYfstZdwMGCni7FUalaPTYULOcTShgrv+L2Le4bcZa6QYK3Lz9obatomrVrb+/yfwHo076H2olRpQBU5WfyW0Fs6oRGfYD1VKJfbEyfyDWwaULGXaIMUWAuEgUp4aQ6zLk5R2FhySaanEnPMu0xLQ0/Au6hFaxH5EBZxCXIy0UM99nJnwkqHkAnCnEA/GddJP0B5JWu12fcqhp0KgBOJE7iZASwO28HtWPaqFXr7vvje34aQ3MtMh435GKb1ptHMYG9C7tsioPEZSDdcSIHfOMlb0KLqeKQKI35YXqxR5TSClCTAPOqYjU5yjTab+HijCAIAcVQFeGhulsgRqpZni+T1FE9DirNEXo0dced32fmk=";
//        String iv = "YuVwUCC9OqGwoV/8YGyIJw==";

        System.out.println(WXBizDataCrypt.decrypt(encryptedData,iv,sessionKey,appId));




// 解密后的数据为
//
// data = {
//   "nickName": "Band",
//   "gender": 1,
//   "language": "zh_CN",
//   "city": "Guangzhou",
//   "province": "Guangdong",
//   "country": "CN",
//   "avatarUrl": "http://wx.qlogo.cn/mmopen/vi_32/aSKcBBPpibyKNicHNTMM0qJVh8Kjgiak2AHWr8MHM4WgMEm7GFhsf8OYrySdbvAMvTsw3mo8ibKicsnfN5pRjl1p8HQ/0",
//   "unionId": "ocMvos6NjeKLIBqg5Mr9QjxrP1FA",
//   "watermark": {
//     "timestamp": 1477314187,
//     "appid": "wx4f4bc4dec97d474b"
//   }
// }
    }
}
package com.minlia.module.wechat.utils;

/**
 * @author zhonghongqiang
 *         Created on 2018-04-28.
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        String appId = "wx4f4bc4dec97d474b";
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
        System.out.println(WXBizDataCrypt.decrypt(encryptedData,iv,sessionKey,appId));

        appId = "wx469ffdb81de47e4d";
//        appId = "wxb7fab50b2771d89f";
        sessionKey = "+TuFNB/P0ZOglY08jJzHdg==";

        encryptedData = "ONaf3N/nPvtON8HKF9nUEYURHAwAL9fGLi2M6cZTrVk2rguZrM7sh9+vCLB/5KDagdu0YOkYnziyxYQM0VxKBTYGbVdQdsZnRr4Ewi4vYhGj2lnj6oVaFoENGrRdnRLWjTjT85AZ1xphEzfHpoMAy8A7cLLKtIR24fA6q0KtGS1lMRajFr5HUi8lH4Ucb0nZejlZEuWkBk8q5E5NdwiG/3KFirE43i4+6r8kv61yzWC1WyjrimJvANP6kcH9rqRb2O0HD5qMJmiVdcrMFKaWEVOxz1W0vodQaDDuxwy8xsuO8cpk++vg/7X0Ia22RPp+fB1HbXBdGoYc8c8tOJ7JDbvUG00ENN8CVoy/pf+ZVwGEifIab06fkHIiW4tCvYM5oDiX4OSUROgGukX0ySWr8QGXDHcm0+JQKNsm+9+qxKSFl040k4u7RqkgWdTbISsz8O+KP6n2OYBhuGCHDQE8ojR6TJFUPJ4XLgNiz46YmAc/20YOxbo8VNDjgTFad9e5NelcEdUIx29UoMcr87hm5yrfm9NiHKVW8pkilNq3FuGbT2lJQ517VrREavjft562";
        iv = "q64qVuDDU8Ef1chU6YoDsA==";
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
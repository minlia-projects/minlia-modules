
### 场景:
APP支付, 小程序的在线支付
使用上游的统一下单, 创建预订单的SDK

结合了外部或官方的SDK完成



### 使用步骤:

注意: 以下配置文件必须在当前运行的项目里面配置, 否则可能出现调用找不到Bean的情况

1. 在项目中引入

```
<dependency>
    <groupId>com.minlia.modules</groupId>
    <artifactId>module-unified-payment</artifactId>
    <version>${project.version}</version>
</dependency>
```




2. 项目使用时的配置文件

使用Lzay可以防止Bean初始化失败

```
import com.minlia.module.unified.payment.sdk.alipay.v1.AlipayConfig;
import com.minlia.module.unified.payment.sdk.alipay.v1.AlipayCreatePreOrderService;
import com.minlia.module.unified.payment.sdk.wechat.v1.WechatConfig;
import com.minlia.module.unified.payment.sdk.wechat.v1.WechatCreatePreOrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class UnifiedPaymentConfiguration {


  @Bean
  @Lazy
  public AlipayConfig alipayCredential() {
    //从Bible里读取出来相关的配置信息
    AlipayConfig config = new AlipayConfig();

    //设置所有参数
//    credential.setAppId("");
//    credential.setCertificate();

    return config;
  }

  @Bean
  @Lazy
  public WechatConfig wechatCredential() {
    //从Bible里读取出来相关的配置信息
    WechatConfig config = new WechatConfig();

    //设置所有参数
//    credential.setAppId("");
//    credential.setCertificate();

    //设置所有参数
    return config;
  }


  @Bean
  @Lazy
  public WechatCreatePreOrderService wechatCreatePreOrderService() {
    WechatCreatePreOrderService service = new WechatCreatePreOrderService(wechatCredential());
    return service;
  }

  @Bean
  @Lazy
  public AlipayCreatePreOrderService alipayCreatePreOrderService() {
    AlipayCreatePreOrderService service = new AlipayCreatePreOrderService(alipayCredential());
    return service;
  }
}
````




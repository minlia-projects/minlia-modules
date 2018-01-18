
        <!--
        一款批量推送微信模板消息、客服消息、阿里大于模板短信等的小而美的工具
        https://github.com/rememberber/WePush-->



        <!--
        

        https://github.com/pandaTED/hellowx/blob/284470ceae1a268ad12f45f55058d18f9a2c2fd0/src/main/java/cn/panda/controller/WxMpPortalController.java-->
    
    
   ***************************************************************************************************** 
    2017-9-19
    Update Notes:
    
    1. 修改WechatMpConfig.java  61-64 行为从Bible取值出来, 现在是从配置文件里面取出来, 可能出现不知取值是什么的情况. 
    因为jar包里有很多这种配置, 不知道哪一个生效了.
    ```
    
    @Bean
    @ConditionalOnMissingBean
    public WxMpConfigStorage configStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
//        下面注释行的appid、secret、token、aeske 是从 application.properties中初始化的。
//        configStorage.setAppId(this.wechatProperties.getAppId());
//        configStorage.setSecret(this.wechatProperties.getSecret());
//        configStorage.setToken(this.wechatProperties.getToken());
//        configStorage.setAesKey(this.wechatProperties.getAesKey());

        //TODO 修改后，从配置文件bilble里获取 appid、secret、token、aeskey
        configStorage.setAppId(bibleItemService.get(WECHAT_MP_BIBLE_CODE,APPID_BIBLE_ITEM_CODE));
        configStorage.setSecret(bibleItemService.get(WECHAT_MP_BIBLE_CODE,SECRET_BIBLE_ITEM_CODE));
        configStorage.setToken(bibleItemService.get(WECHAT_MP_BIBLE_CODE, TOKEN_BIBLE_ITEM_CODE));
        configStorage.setAesKey(bibleItemService.get(WECHAT_MP_BIBLE_CODE,AES_KEY_BIBLE_ITEM_CODE));
        return configStorage;
        
    2.在WechatMpSeedDataInitializeListener.java中添加初始化常量
    
    //TODO 微信公众号:在业务mp中listen先初始化bible,后获取值
    public static final String WECHAT_MP_BIBLE_CODE="WechatMp";
    public static final String APPID_BIBLE_ITEM_CODE="appId";
    public static final String SECRET_BIBLE_ITEM_CODE="secret";
    public static final String AES_KEY_BIBLE_ITEM_CODE="aesKey";
    public static final String TOKEN_BIBLE_ITEM_CODE="token";

    //TODO 小程序服务号:在业务mp中listen先初始化bible,后获取值
    public static final String WECHAT_MINIAPP_BIBLE_CODE="WechatMiniapp";
    public static final String MINIAPP_APPID_BIBLE_ITEM_CODE="appId";
    public static final String MINIAPP_SECRET_BIBLE_ITEM_CODE="secret";

   *******************************************************************************************************    
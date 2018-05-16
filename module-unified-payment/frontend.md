
参考项目 我用这个项目发起一微信和支付宝的交易
https://github.com/Gerryzhang/pay


支付宝插件
https://github.com/ejiyuan/cordova-plugin-alipay


微信支付 (客户端直接发起交易, 不推荐, 安全性是个问题, 将改造成由服务端创建订单)

https://github.com/xu-li/cordova-plugin-wechat/

--variable wechatappid=YOUR_WECHAT_APPID

cordova plugin add cordova-plugin-wechat --variable wechatappid=YOUR_WECHAT_APPID, or using plugman, phonegap, ionic

回调参数

{gmt_create=2017-09-14 06:50:01, charset=UTF-8, seller_email=moffvape@gmail.com, subject=App支付测试Java, sign=SUeUzWkBGIo1fdjbiBwksUSGWGBsVRuP06swPgGh1Lu0+H0ByR2sjgfZAIz6c9yzXfeNWSLfITe1Og723TENMj6O1rzPTE9x+X1tFuP0HRhSY+eIKm+xNg2zPI1m9jWwgAvTQ76yHsFosOt2oXZldc2nuPM2TIIPGA5lEfu+OJF8KVloAPB+FsT5vljCCg7cWHNqe6ZPC+BlG0KSQSOOmMHD9tnPUqdF9z5MWQYPaWbQc8WjdfxVD58RPOgnVZjzplbsngh7+L+jqEGk/b9dr8fbbQsAG6U2TTUpm+nusCLr9RLMywM1WIt92GpnpjaLpI1qoNvdS4BEId6YHwC3OQ==, body=我是测试数据, buyer_id=2088002650456370, invoice_amount=0.01, notify_id=ca05718872fd99b2239cea08ca14177iuu, fund_bill_list=[{"amount":"0.01","fundChannel":"ALIPAYACCOUNT"}], notify_type=trade_status_sync, trade_status=TRADE_SUCCESS, receipt_amount=0.01, app_id=2017082408355971, buyer_pay_amount=0.01, sign_type=RSA2, seller_id=2088721842507129, gmt_payment=2017-09-14 06:50:01, notify_time=2017-09-14 06:50:02, version=1.0, out_trade_no=Order114417771125, total_amount=0.01, trade_no=2017091421001004370220642129, auth_app_id=2017082408355971, buyer_logon_id=mmi***@live.com, point_amount=0.00}

{charset=UTF-8, subject=App支付测试Java, sign=SUeUzWkBGIo1fdjbiBwksUSGWGBsVRuP06swPgGh1Lu0+H0ByR2sjgfZAIz6c9yzXfeNWSLfITe1Og723TENMj6O1rzPTE9x+X1tFuP0HRhSY+eIKm+xNg2zPI1m9jWwgAvTQ76yHsFosOt2oXZldc2nuPM2TIIPGA5lEfu+OJF8KVloAPB+FsT5vljCCg7cWHNqe6ZPC+BlG0KSQSOOmMHD9tnPUqdF9z5MWQYPaWbQc8WjdfxVD58RPOgnVZjzplbsngh7+L+jqEGk/b9dr8fbbQsAG6U2TTUpm+nusCLr9RLMywM1WIt92GpnpjaLpI1qoNvdS4BEId6YHwC3OQ==, invoiceAmount=0.01, body=我是测试数据, buyerId=2088002650456370, buyerLogonId=mmi***@live.com, receiptAmount=0.01, sellerId=2088721842507129, gmtPayment=2017-09-14 06:50:01, appId=2017082408355971, outTradeNo=Order114417771125, signType=RSA2, fundBillList=[{"amount":"0.01","fundChannel":"ALIPAYACCOUNT"}], pointAmount=0.00, tradeNo=2017091421001004370220642129, notifyTime=2017-09-14 06:50:02, gmtCreate=2017-09-14 06:50:01, sellerEmail=moffvape@gmail.com, version=1.0, totalAmount=0.01, notifyType=trade_status_sync, tradeStatus=TRADE_SUCCESS, buyerPayAmount=0.01, notifyId=ca05718872fd99b2239cea08ca14177iuu, authAppId=2017082408355971}

{"gmt_create":"2017-09-14 06:50:01","charset":"UTF-8","seller_email":"moffvape@gmail.com","subject":"App支付测试Java","sign":"SUeUzWkBGIo1fdjbiBwksUSGWGBsVRuP06swPgGh1Lu0+H0ByR2sjgfZAIz6c9yzXfeNWSLfITe1Og723TENMj6O1rzPTE9x+X1tFuP0HRhSY+eIKm+xNg2zPI1m9jWwgAvTQ76yHsFosOt2oXZldc2nuPM2TIIPGA5lEfu+OJF8KVloAPB+FsT5vljCCg7cWHNqe6ZPC+BlG0KSQSOOmMHD9tnPUqdF9z5MWQYPaWbQc8WjdfxVD58RPOgnVZjzplbsngh7+L+jqEGk/b9dr8fbbQsAG6U2TTUpm+nusCLr9RLMywM1WIt92GpnpjaLpI1qoNvdS4BEId6YHwC3OQ==","body":"我是测试数据","buyer_id":"2088002650456370","invoice_amount":"0.01","notify_id":"ca05718872fd99b2239cea08ca14177iuu","fund_bill_list":"[{\"amount\":\"0.01\",\"fundChannel\":\"ALIPAYACCOUNT\"}]","notify_type":"trade_status_sync","trade_status":"TRADE_SUCCESS","receipt_amount":"0.01","app_id":"2017082408355971","buyer_pay_amount":"0.01","sign_type":"RSA2","seller_id":"2088721842507129","gmt_payment":"2017-09-14 06:50:01","notify_time":"2017-09-14 06:50:02","version":"1.0","out_trade_no":"Order114417771125","total_amount":"0.01","trade_no":"2017091421001004370220642129","auth_app_id":"2017082408355971","buyer_logon_id":"mmi***@live.com","point_amount":"0.00"}

发起交易的参数

alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017082408355971&biz_content={"body":"我是测试数据","out_trade_no":"Order114417771125","product_code":"QUICK_MSECURITY_PAY","subject":"App支付测试Java","timeout_express":"30m","total_amount":"0.01"}&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http://v8.s1.natapp.cc/api/open/n/alipay&sign=vBiEaBeeJHXYCzID1nyyEsk74g8Ew8plBYw6MgDOZxLnlHe+OV2V/o+dvYLrVd38FXZvzqX4AZSlcoM/n1mFhMJzlZPpbkf5W2d+4U3XqJZ/sOCJ2in18u8nILPpnfEDbT2Nh2oWl+Hrg+7DiQ62R1JtTlmvgJNELtng17O0IohJwIRe6G2EEranFNaGdjNgwjIBZg+pf9QsJWjsT8cgzNv0y7npjbl4f1eun8TBAVrWvkRMzQvJEbD/gOm5ODhagyIx1tKJDXp5s7q4kBdteHlMyEEie6Vv20dnPGQ7VV/hrbz5x60cIfkXomC8Riz5Ht14HnpTSrNyRnACdiiY7A==&sign_type=RSA2&timestamp=2017-09-14 06:52:35&version=1.0

alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017082408355971&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22Order114417771125%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95Java%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fv8.s1.natapp.cc%2Fapi%2Fopen%2Fn%2Falipay&sign=vBiEaBeeJHXYCzID1nyyEsk74g8Ew8plBYw6MgDOZxLnlHe%2BOV2V%2Fo%2BdvYLrVd38FXZvzqX4AZSlcoM%2Fn1mFhMJzlZPpbkf5W2d%2B4U3XqJZ%2FsOCJ2in18u8nILPpnfEDbT2Nh2oWl%2BHrg%2B7DiQ62R1JtTlmvgJNELtng17O0IohJwIRe6G2EEranFNaGdjNgwjIBZg%2Bpf9QsJWjsT8cgzNv0y7npjbl4f1eun8TBAVrWvkRMzQvJEbD%2FgOm5ODhagyIx1tKJDXp5s7q4kBdteHlMyEEie6Vv20dnPGQ7VV%2Fhrbz5x60cIfkXomC8Riz5Ht14HnpTSrNyRnACdiiY7A%3D%3D&sign_type=RSA2&timestamp=2017-09-14+06%3A52%3A35&version=1.0

以下是潮哥给我的

this.order = "app_id=2017082808427206&biz_content=%7b%22body%22%3a%22%e9%86%89%e6%b8%b8%e6%b1%89%e5%8c%96VIP%e6%9c%8d%e5%8a%a1%22%2c%22out_trade_no%22%3a%2220170914010123JM7O02%22%2c%22product_code%22%3a%22QUICK_MSECURITY_PAY%22%2c%22subject%22%3a%22%e5%a4%a9%e5%91%bd30%e5%a4%a9VIP%22%2c%22timeout_express%22%3a%2230m%22%2c%22total_amount%22%3a%2215.00%22%7d&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2fpay.sotgame.com%3a90%2fapi%2fPay%2fAlipayNotify&sign_type=RSA2&timestamp=2017-09-14+01%3a01%3a23&version=1.0&sign=Z1IUMuk830dDGqqXQL%2fhQyEcIxvGN3I0l1APfJPCq%2fbqRnzkWVvgI7%2bbpYIUyxiSTwLqAxK1SWYjepIGEoV6z0gf6v3CeyEWBwNX0B%2b4Dsidxsj%2bqGXAyhHK6PbPoNinaueAy8TVB%2bqWUTFEJntQR8PffMzzubC9QWR6DkPhoCuoM77aFCINLAHSidZUk5o%2bbHkkMEubP6Vij9CAtbMoDIDaoEA1%2bCB%2byPnxLaB1TBh7VKrNnPs%2f9RI63Ey50Fp8zoXs64UiZJHTDij%2by454F5I49paPfQCkI%2fAytR%2fyPZz4%2b4t62Tm%2bG8mAnOuSfNPxhzznmvF95Wa3PuusVreSQA%3d%3d"

console.log(this.order);
cordova.plugins.alipay.payment(this.order,function success(e){
    alert(JSON.stringify(e))
    console.log(e)
},function error(e){
    alert(JSON.stringify(e))
    console.log(e)
});





# 使用SPRING SECURITY + JWT来适应现代应用架构的安全认证

现代化应用架构中, 需要一个后台支持所有前端应用调用, 如: 小程序, 公众号, APP, PC等所有终端可以使用一套后端.

在我们的实际项目中, 需要完成以上需求就需要对原架构进行优化. 我们选择了SPRING SECURITY, JWT框架来适应我们的需求.


## 理解JWT
一串由三部分分别ENCODE之后并由.相连接的串组成一个TOKEN

HEADER.PAYLOAD.SIGNATURE

### HEADER: 头部
```
{
  'typ': 'JWT',
  'alg': 'HS256'
}
```

### PAYLOAD: 荷载
```
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```

### SIGNATURE: 签名

```
data = base64urlEncode( header ) + “.” + base64urlEncode( payload )
signature = Hash( data, secret );
```
第一部分我们称它为头部（header),第二部分我们称其为荷载（payload, 一个人携带个钱包的概念)，第三部分是签名（signature).

详情请参考以下文章: 

[MOZILLAZG](https://mozillazg.github.io/2015/06/hello-jwt.html)

[JIANSHU](http://www.jianshu.com/p/576dbf44b2ae)

## 在项目中的作用:

结合SPRING SECURITY用来进行用户登录, 校验权限
前端在Local Storage里面存储登录后取到的Token, 并在每个请求Header中加上此Token达到访问时能进行Token认证的目的. 即: 在访问时就知道是哪个用户在请求API.

## 与传统应用的区别:
传统应用使用COOKIE或SESSION来存储当前访问者信息, 我们在一些不支持COOKIE的场景可能就会出现方案无法COVER的情况.


## 具体与项目的结合,请参见如下文件:

[JwtAuthenticationProvider.java](https://github.com/minlia-projects/minlia-modules/blob/dev/will/module-security/src/main/java/com/minlia/modules/security/authentication/jwt/JwtAuthenticationProvider.java)

[AjaxAuthenticationProvider.java](https://github.com/minlia-projects/minlia-modules/blob/dev/will/module-security/src/main/java/com/minlia/modules/security/authentication/ajax/AjaxAuthenticationProvider.java)

[RbacAuthenticationService.java](https://github.com/minlia-projects/minlia-modules/blob/dev/will/module-rebecca/src/main/java/com/minlia/modules/rbac/authentication/service/RbacAuthenticationService.java)


## 特别鸣谢: SPRING SECURITY, JWT, 文中提及链接的博主






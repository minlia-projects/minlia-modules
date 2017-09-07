
### 使用POSTMAN进行API自动化测试
### 首先安装postman的命令行工具 newman
`npm install newman-cli -g`
### 参数说明见这里: 

https://solidgeargroup.com/test-automation-api
https://www.npmjs.com/package/newman


修改registration.csv进行数据初始化, 将使用此文件中的数据进行用户注册

`newman run User_Registration.postman_collection.json -e dev.postman_environment.json -d registration.csv -n 1`


执行结果如下


```
User Registration

→ Username Availablitity Check
  POST http://127.0.0.1:10010/api/user/availablitity [200 OK, 553B, 108ms]
  ✓  业务返回不为空
  ✓  业务返回正确性
  ✓  检测用户名是否可用
  ✓  返回的状态码是200
  ✓  返回体为正常的JSON

→ Send Secure Code
  POST http://127.0.0.1:10010/api/user/secureCode/USER_REGISTRATION/send [200 OK, 571B, 76ms]
  ✓  业务返回正确
  ✓  业务返回不为空
  ┌
  │ 'Code ', '0722'
  └
  ✓  发送验证码成功
  ✓  验证码尚未被使用过
  ✓  返回体为正常的JSON

→ Registration
  POST http://127.0.0.1:10010/api/user/registration [200 OK, 592B, 276ms]
  ✓  业务返回正确性测试
  ✓  用户注册成功
  ✓  返回体为正常的JSON

┌─────────────────────────┬──────────┬──────────┐
│                         │ executed │   failed │
├─────────────────────────┼──────────┼──────────┤
│              iterations │        1 │        0 │
├─────────────────────────┼──────────┼──────────┤
│                requests │        3 │        0 │
├─────────────────────────┼──────────┼──────────┤
│            test-scripts │        3 │        0 │
├─────────────────────────┼──────────┼──────────┤
│      prerequest-scripts │        1 │        0 │
├─────────────────────────┼──────────┼──────────┤
│              assertions │       13 │        0 │
├─────────────────────────┴──────────┴──────────┤
│ total run duration: 665ms                     │
├───────────────────────────────────────────────┤
│ total data received: 597B (approx)            │
├───────────────────────────────────────────────┤
│ average response time: 153ms                  │
```
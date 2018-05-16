//package com.minlia.module.unified.payment.sdk.callback;
//
//import java.io.BufferedReader;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.SortedMap;
//import java.util.TreeMap;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.hcb.zzb.dto.FinanceRecord;
//import com.hcb.zzb.dto.Orders;
//import com.hcb.zzb.dto.Users;
//import com.hcb.zzb.service.IFinanceRecordService;
//import com.hcb.zzb.service.IOrderService;
//import com.hcb.zzb.service.IPlatformConfigService;
//import com.hcb.zzb.service.IUsersService;
//import com.hcb.zzb.util.Signature;
//import com.tencent.common.XMLParser;
//
//@RestController
//public class WeChatPayConfirmController {
//	@Autowired
//	IOrderService orderService;
//	@Autowired
//	IFinanceRecordService financeRecordService;
//	@Autowired
//	IUsersService userService;
//	@Autowired
//	IPlatformConfigService platformConfigService;
//
//	@RequestMapping(value="wechatPayComfirm",method=RequestMethod.POST)
//	@Transactional
//	public String weChatPayConfirm(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		BufferedReader reader = null;
//
//		reader = request.getReader();
//		String line = "";
//		String xmlString = null;
//		StringBuffer inputString = new StringBuffer();
//
//		while ((line = reader.readLine()) != null) {
//			inputString.append(line);
//		}
//		xmlString = inputString.toString();
//		request.getReader().close();
//		System.out.println("----接收到的数据如下：---" + xmlString);
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		String result_code = "";
//		String out_trade_no = "";
//		map = XMLParser.getMapFromXML(xmlString);
//		result_code = (String) map.get("result_code");
//		out_trade_no = (String) map.get("out_trade_no");
//
//		if(checkSignPay(xmlString)) {
//			Orders order = orderService.selectByOrdersUuid(out_trade_no);
//			if(order.getOrderStatus()==3) {
//				return returnXMLPay(result_code);
//			}else {
//				FinanceRecord finance=new FinanceRecord();
//				finance.setCreateAt(new Date());
//				finance.setFinanceRecordUuid(UUID.randomUUID().toString().replaceAll("-", ""));
//				finance.setFinanceType(2);//交易类型；1：收入；2：支出
//				finance.setMoney(order.getTotalPrice());
//				finance.setOrderRecordType(2);//订单记录类型；1：押金；2：租车费用；3：赔偿费用
//				finance.setOrderUuid(order.getOrderUuid());
//				finance.setRecordType(3);//记录类型；1：充值；2：提现；3：订单
//				finance.setUserUuid(order.getUserUuid());
//
//				financeRecordService.insertSelective(finance);
//				order.setOrderStatus(3);
//				order.setPayTime(new Date());
//				order.setPayType(3);
//				orderService.updateByPrimaryKeySelective(order);
//
//				return returnXMLPay(result_code);
//			}
//		}else {
//			return returnXMLPay("FAIL");
//		}
//
//	}
//
//
//	/**
//	 * 确认还车（微信支付异步回调）
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//
//	@RequestMapping(value="wechatPayComfirmCollectCar",method=RequestMethod.POST)
//	@Transactional
//	public String weChatPayConfirm1(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		BufferedReader reader = null;
//
//		reader = request.getReader();
//		String line = "";
//		String xmlString = null;
//		StringBuffer inputString = new StringBuffer();
//
//		while ((line = reader.readLine()) != null) {
//			inputString.append(line);
//		}
//		xmlString = inputString.toString();
//		request.getReader().close();
//		System.out.println("----接收到的数据如下：---" + xmlString);
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		String result_code = "";
//		String out_trade_no = "";
//		map = XMLParser.getMapFromXML(xmlString);
//		result_code = (String) map.get("result_code");
//		out_trade_no = (String) map.get("out_trade_no");
//
//		if(checkSignPay(xmlString)) {
//			Orders order = orderService.selectByOrdersUuid(out_trade_no);
//			if(order.getOrderStatus()==5||order.getOrderStatus()==6) {
//				return returnXMLPay(result_code);
//			}else {
//				float payMoney=0;
//				if(order.getCompensateMoney()!=null) {
//					payMoney=order.getTotalPrice()+order.getCompensateMoney();
//				}else {
//					payMoney=order.getTotalPrice();
//				}
//				//租客收支明细
//				FinanceRecord finance=new FinanceRecord();
//				finance.setCreateAt(new Date());
//				finance.setFinanceRecordUuid(UUID.randomUUID().toString().replaceAll("-", ""));
//				finance.setFinanceType(2);//交易类型；1：收入；2：支出
//				finance.setMoney(payMoney);
//				finance.setOrderRecordType(2);//订单记录类型；1：押金；2：租车费用；3：赔偿费用
//				finance.setOrderUuid(order.getOrderUuid());
//				finance.setRecordType(3);//记录类型；1：充值；2：提现；3：订单
//				finance.setUserUuid(order.getUserUuid());
//				finance.setPayType(3);//支付方式：1：余额；2：支付宝；3：微信；4：银行卡
//
//				financeRecordService.insertSelective(finance);
//
//				//车主余额
//				Users carOwner=userService.selectByUserUuid(order.getCarOwnerUuid());
//				float nowBalance = carOwner.getBalance()==null?0:carOwner.getBalance();
//				carOwner.setBalance(nowBalance+payMoney);
//				userService.updateByPrimaryKeySelective(carOwner);
//				//车主收支明细
//				FinanceRecord finance1=new FinanceRecord();
//				finance1.setCreateAt(new Date());
//				finance1.setFinanceRecordUuid(UUID.randomUUID().toString().replaceAll("-", ""));
//				finance1.setFinanceType(1);//交易类型；1：收入；2：支出
//				finance1.setMoney(payMoney);
//				finance1.setOrderRecordType(2);//订单记录类型；1：押金；2：租车费用；3：赔偿费用
//				finance1.setOrderUuid(order.getOrderUuid());
//				finance1.setRecordType(3);//记录类型；1：充值；2：提现；3：订单
//				finance1.setUserUuid(order.getCarOwnerUuid());
//				finance1.setPayType(3);//支付方式：1：余额；2：支付宝；3：微信；4：银行卡
//				financeRecordService.insertSelective(finance1);
//
//
//				order.setOrderStatus(5);
//				order.setPayTime(new Date());
//				order.setPayType(3);
//				orderService.updateByPrimaryKeySelective(order);
//
//				return returnXMLPay(result_code);
//			}
//		}else {
//			return returnXMLPay("FAIL");
//		}
//
//	}
//
//
//
//
//
//	private boolean checkSignPay(String xmlString) {
//
//		Map<String, Object> map = null;
//
//		try {
//
//			map = XMLParser.getMapFromXML(xmlString);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		String signFromAPIResponse = map.get("sign").toString();
//
//		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
//
//			System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
//
//			return false;
//
//		}
//		System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);
//
//		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
//
//		map.put("sign", "");
//
//		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
//
//		String signForAPIResponse = getSignPay(map);
//
//		if (!signForAPIResponse.equals(signFromAPIResponse)) {
//
//			// 签名验不过，表示这个API返回的数据有可能已经被篡改了
//
//			System.out.println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为" + signForAPIResponse);
//
//			return false;
//
//		}
//
//		System.out.println("恭喜，API返回的数据签名验证通过!!!");
//
//		return true;
//
//	}
//
//	private String returnXMLPay(String return_code) {
//
//		return "<xml><return_code><![CDATA["
//
//				+ return_code
//
//				+ "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
//	}
//
//	public String getSignPay(Map<String, Object> map) {
//		SortedMap<String, Object> signParams = new TreeMap<String, Object>();
//		for (Map.Entry<String, Object> stringStringEntry : map.entrySet()) {
//			signParams.put(stringStringEntry.getKey(), stringStringEntry.getValue());
//		}
//		signParams.remove("sign");
//		String sign = Signature.getSign(signParams);
//		return sign;
//	}
//}

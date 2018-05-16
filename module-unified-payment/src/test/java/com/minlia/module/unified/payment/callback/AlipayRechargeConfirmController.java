//package com.minlia.module.unified.payment.sdk.callback;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//
//import com.alipay.api.AlipayConstants;
//import com.hcb.zzb.dto.FinanceRecord;
//import com.hcb.zzb.dto.RechargeRecord;
//import com.hcb.zzb.dto.Users;
//import com.hcb.zzb.service.IFinanceRecordService;
//import com.hcb.zzb.service.IRechargeRecordService;
//import com.hcb.zzb.service.IUsersService;
//import com.hcb.zzb.util.Alipay;
//import com.hcb.zzb.util.AlipayApiException;
//import com.hcb.zzb.util.AlipaySignature;
//
//@RestController
//public class AlipayRechargeConfirmController {
//	@Autowired
//	IRechargeRecordService rechargeRecordService;
//	@Autowired
//	IUsersService usersService;
//	@Autowired
//	IFinanceRecordService financeRecordService;
//
//	@RequestMapping(value="confirmNotifyAlipayRecharge",method=RequestMethod.POST)
//	@Transactional(readOnly = false)
//	public String confirmNotifyAlipayRecharge(HttpServletRequest request) throws SQLException, IOException,AlipayApiException{
//		String sign = null;
//		String sign_type = null;
//		String out_trade_no = null;
//		String trade_status = null;
//		Map<String, String> params = new HashMap<String, String>();
//		Map requestParams = request.getParameterMap();
//		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			String name = (String) iter.next();
//			String[] values = (String[]) requestParams.get(name);
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//			}
//			System.out.println(">>>>>参数" + name + ":" + valueStr);
//			if (name.equals("sign")) {
//				sign = valueStr;
//			} else if (name.equals("sign_type")) {
//				sign_type = valueStr;
//			} else if (name.equals("out_trade_no")) {
//				out_trade_no = valueStr;
//			} else if (name.equals("trade_status")){
//				trade_status = valueStr;
//			}
//			params.put(name, valueStr);
//		}
//		System.out.println("++++++++++++++++trade_status:" + trade_status);
//	    //交易关闭订单
//	    if("TRADE_SUCCESS".equals(trade_status)){
//	    	requestParams.get("trade_status");
//	    	String content = AlipaySignature.getSignCheckContentV1(params);
//	    	String utf_8 = AlipayConstants.CHARSET_UTF8;
//	    	boolean flag = false;
//	    	flag = AlipaySignature.rsaCheck(content, sign, Alipay.PUBLIC_KEY, utf_8, sign_type);
//	    	if(flag) {
//	    		RechargeRecord recharge=rechargeRecordService.selectByUuid(out_trade_no);
//	    		Users user=usersService.selectByUserUuid(recharge.getUserUuid());
//	    		float money=recharge.getMoney();
//	    		float balance=user.getBalance()==null?0:user.getBalance();
//	    		BigDecimal totalMoney=new BigDecimal(Float.toString(money));
//	    		BigDecimal balance2=new BigDecimal(Float.toString(balance));
//	    		BigDecimal total=balance2.add(totalMoney);
//	    		if(recharge.getRechargeStatus()==2) {
//	    			return "success";
//	    		}else {
//	    			user.setBalance(total.floatValue());
//	    			recharge.setRechargeStatus(2);
//	    			//添加充值的收支明细
//	    			FinanceRecord finance=new FinanceRecord();
//	    			finance.setCreateAt(new Date());
//	    			finance.setFinanceRecordUuid(UUID.randomUUID().toString().replaceAll("-", ""));
//	    			finance.setFinanceType(1);
//	    			finance.setMoney(recharge.getMoney());
//	    			finance.setRechargeRecordUuid(recharge.getRechargeRecordUuid());
//	    			finance.setRecordType(1);
//	    			finance.setUserUuid(recharge.getUserUuid());
//	    			finance.setPayType(2);
//
//	    			int i1 = financeRecordService.insertSelective(finance);
//	    			int i2 = usersService.updateByPrimaryKeySelective(user);
//	    			int i3 = rechargeRecordService.updateByPrimaryKeySelective(recharge);
//	    			if(i1 > 0 && i2 >0 && i3 >0) {
//	    				return "success";
//	    			}else {
//	    				return "failure";
//	    			}
//	    		}
//
//
//	    	}else {
//	    		return "failure";
//	    	}
//	    }else{
//	    	return "success";
//	    }
//	}
//}

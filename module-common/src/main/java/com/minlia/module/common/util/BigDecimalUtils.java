package com.minlia.module.common.util;

import java.math.BigDecimal;

/**
 * Utility to help comparison of {@link BigDecimal}.
 *
 * The only way to compare {@link BigDecimal} is to get result of compare
 * function of {@link BigDecimal} and compare the result with -1, 0 and 1.
 *
 * Although it is straight forward however it lacks expressiveness and decreases
 * readability. For instance look at this line of code :
 *
 * <pre>
 * <code>
 *     if(balance.compareTo(maxAmount) < 0))
 * </code>
 * </pre>
 *
 * the code above try to check condition "balance &lt; maxAmount". You
 * definitely spotted the problem. now imagine how hard it can be if you have to
 * read some code with a lot of {@link BigDecimal} comparison!! </b>
 * {@link BigDecimalUtils} makes comparison of {@link BigDecimal}s more easier
 * and more readable than the comparator method. look how above code are written
 * by the help of this library.
 *
 * <pre>
 * <code>
 *     if( is(balance).lt(maxAmount) )
 * </code>
 * </pre>
 *
 *
 * @author adigozalpour
 *
 */
public class BigDecimalUtils {

	private BigDecimalUtils() {
    }

	/**
	 * Entry points of {@link BigDecimalUtils} <br/>
	 * <br/>
	 * Example usage:
	 *
	 * <pre>
	 * <code>
	 *      is(three).eq(four); //Equal
	 * 		is(two).gt(two);    //Greater than
	 * 		is(two).gte(one);   //Greater than equal
	 * 		is(three).lt(two);  //Less than
	 * 		is(three).lte(two); //Less than equal
	 *
	 *      is(three).notEq(four); //Not Equal
	 * 		is(two).notGt(two);    //Not Greater than
	 * 		is(two).notGte(one);   //Not Greater than equal
	 * 		is(three).notLt(two);  //Not Less than
	 * 		is(three).notLte(two); //Not Less than equal
	 *
	 *      is(three).isZero();
	 *      is(three).notZero();
	 *      is(three).isPositive(); // greater than zero
	 *      is(three).isNegetive(); // less than zero
	 *      is(three).isNonPositive(); //less than or equal zero
	 *      is(three).isNonNegetive(); //greater than or equal zero
	 * </code>
	 * </pre>
	 *
	 * @param decimal
	 *            your {@link BigDecimal}
	 *
	 * @return {@link BigDecimalWrapper}
	 *
	 * @see #isNot(BigDecimal)
	 */
	public static BigDecimalWrapper is(BigDecimal decimal) {

		return new BigDecimalWrapper(decimal);
	}

	/**
	 * Entry points of {@link BigDecimalUtils} <br/>
	 * <br/>
	 * Example usage:
	 *
	 * <pre>
	 * <code>
	 *      is(three).eq(four); //Equal
	 * 		is(two).gt(two);    //Greater than
	 * 		is(two).gte(one);   //Greater than equal
	 * 		is(three).lt(two);  //Less than
	 * 		is(three).lte(two); //Less than equal
	 *
	 *      is(three).notEq(four); //Not Equal
	 * 		is(two).notGt(two);    //Not Greater than
	 * 		is(two).notGte(one);   //Not Greater than equal
	 * 		is(three).notLt(two);  //Not Less than
	 * 		is(three).notLte(two); //Not Less than equal
	 *
	 *      is(three).isZero();
	 *      is(three).notZero();
	 *      is(three).isPositive(); // greater than zero
	 *      is(three).isNegetive(); // less than zero
	 *      is(three).isNonPositive(); //less than or equal zero
	 *      is(three).isNonNegetive(); //greater than or equal zero
	 *
	 *      is(three).isNullOrZero(); //is null or zero
	 *      is(three).notNullOrZero(); //not null or zero
	 * </code>
	 * </pre>
	 *
	 * @param decimal
	 *            your {@link BigDecimal}
	 *
	 * @return {@link BigDecimalWrapper}
	 *
	 * @see #isNot(BigDecimal)
	 */
	public static BigDecimalWrapper is(double dbl) {
		return is(BigDecimal.valueOf(dbl));
	}

	/**
	 * 汉语中数字大写
	 */
	private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆",
			"伍", "陆", "柒", "捌", "玖" };
	/**
	 * 汉语中货币单位大写，这样的设计类似于占位符
	 */
	private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元",
			"拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
			"佰", "仟" };
	/**
	 * 特殊字符：整
	 */
	private static final String CN_FULL = "整";
	/**
	 * 特殊字符：负
	 */
	private static final String CN_NEGATIVE = "负";
	/**
	 * 金额的精度，默认值为2
	 */
	private static final int MONEY_PRECISION = 2;
	/**
	 * 特殊字符：零元整
	 */
	private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

	/**
	 * 把输入的金额转换为汉语中人民币的大写
	 *
	 * @param numberOfMoney
	 *            输入的金额
	 * @return 对应的汉语大写
	 */
	public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {
		StringBuffer sb = new StringBuffer();
		// -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
		// positive.
		int signum = numberOfMoney.signum();
		// 零元整的情况
		if (signum == 0) {
			return CN_ZEOR_FULL;
		}
		//这里会进行金额的四舍五入
		long number = numberOfMoney.movePointRight(MONEY_PRECISION)
				.setScale(0, 4).abs().longValue();
		// 得到小数点后两位值
		long scale = number % 100;
		int numUnit = 0;
		int numIndex = 0;
		boolean getZero = false;
		// 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
		if (!(scale > 0)) {
			numIndex = 2;
			number = number / 100;
			getZero = true;
		}
		if ((scale > 0) && (!(scale % 10 > 0))) {
			numIndex = 1;
			number = number / 10;
			getZero = true;
		}
		int zeroSize = 0;
		while (true) {
			if (number <= 0) {
				break;
			}
			// 每次获取到最后一个数
			numUnit = (int) (number % 10);
			if (numUnit > 0) {
				if ((numIndex == 9) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
				}
				if ((numIndex == 13) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
				}
				sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				getZero = false;
				zeroSize = 0;
			} else {
				++zeroSize;
				if (!(getZero)) {
					sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				}
				if (numIndex == 2) {
					if (number > 0) {
						sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
					}
				} else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				}
				getZero = true;
			}
			// 让number每次都去掉最后一个数
			number = number / 10;
			++numIndex;
		}
		// 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
		if (signum == -1) {
			sb.insert(0, CN_NEGATIVE);
		}
		// 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
		if (!(scale > 0)) {
			sb.append(CN_FULL);
		}
		return sb.toString();
	}

//	public static void main(String[] args) {
//		double money = 2020004.0099999999;
//		BigDecimal numberOfMoney = new BigDecimal(money);
//		String s = NumberToCN.number2CNMontrayUnit(numberOfMoney);
//		System.out.println("你输入的金额为：【"+ money +"】   #--# [" +s.toString()+"]");
//	}

}

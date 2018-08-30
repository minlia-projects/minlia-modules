package com.minlia.modules.security.code;

import com.minlia.cloud.annotation.i18n.Localize;
import com.minlia.cloud.annotation.i18n.Localized;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.constant.Constants.LanguageTypes;

/**
 * Created by will on 6/17/17.
 * 安全相关API响应码
 */
@Localized
public class SecurityApiCode extends ApiCode {

    public SecurityApiCode() {
        throw new AssertionError();
    }

    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Not Found: the API you requested could not be found."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "请求的API无法找到"
            )}
    )
    public static final int NOT_FOUND = 40004;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Not Login: Please login first."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "未登录, 请先登录."
            )}
    )
    public static final int NOT_LOGIN = 40001;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Not Authorized: Please confirm your permission."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "未授权, 请确认权限."
            )}
    )
    public static final int NOT_AUTHORIZED = 40003;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Not Null: Please confirm that could not be null."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "不能为空, 请确保不为空."
            )}
    )
    public static final int NOT_NULL = 50001;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Invalid Secure Code: Please confirm that the validity."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "无效验证码, 请确保验证码的有效性."
            )}
    )
    public static final int INVALID_SECURE_CODE = 50002;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Invalid Credential: Please confirm your credential."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "无效登录凭证, 请确保用户名与密码的有效性."
            )}
    )
    public static final int INVALID_CREDENTIAL = 50003;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Account Unavailable: Please confirm your credential is not locked."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "账户不可用, 请确认账户是否被锁定."
            )}
    )
    public static final int ACCOUNT_DISABLED = 50004;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Login Failed: Please confirm your credential."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "登录失败, 请确认账户与密码是否匹配."
            )}
    )
    public static final int LOGIN_FAILED = 50005;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Invalid User: Please confirm your credential."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "无效用户, 请确认账户与密码是否匹配."
            )}
    )
    public static final int INVALID_USER = 50006;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Invalid Raw Password: Please confirm your raw password."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "无效的原始密码, 请确认原始密码是否正确."
            )}
    )
    public static final int INVALID_RAW_PASSWORD = 50007;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Has Children: Please confirm the sub items is empty."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "尚有子项,无法删除: 请确认子项已全部删除"
            )}
    )
    public static final int COULD_NOT_DELETE_HAS_CHILDREN = 50008;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Access Token Expired: Please extend or login again."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "访问令牌已过期: 请延期或重新登录"
            )}
    )
    public static final int ACCESS_TOKEN_HAS_EXPIRED = 50009;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Authentication Failed: Please confirm your credential."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "认证失败: 请确认登录凭证是否正确."
            )}
    )
    public static final int AUTHENTICATION_FAILED = 50010;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Data Already Exists: Please confirm the data you requested."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "数据已存在: 请确认请求的数据是否存在."
            )}
    )
    public static final int DATA_ALREADY_EXISTS = 50011;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Unsupported Request Method: Please confirm the request method."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "不支持的请求方法: 请确认请求的方法."
            )}
    )
    public static final int UNSUPPORTED_REQUEST_METHOD = 50012;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Access Token Invalid: Please confirm the token requested."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "访问令牌无效: 请确认请求的令牌"
            )}
    )
    public static final int ACCESS_TOKEN_INVALID = 50013;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "User is not available: Please confirm that the requested username is already occupied."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "用户不可用: 请确认请求的用户名是否已被占用"
            )}
    )
    public static final int USER_IS_NOT_AVAILABLE = 50014;
    @Localized(
            values = {@Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "en_US",
                    message = "Remote business invoked with failure result."
            ), @Localize(
                    type = LanguageTypes.ExceptionsApiCode,
                    locale = "zh_CN",
                    message = "远程业务调用返回结果不成功"
            )}
    )
    public static final int REMOTE_BUSINESS_INVOKED_WITH_FAILURE_RESULT = 50015;

}

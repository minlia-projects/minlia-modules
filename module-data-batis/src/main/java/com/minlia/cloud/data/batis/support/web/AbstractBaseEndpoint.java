package com.minlia.cloud.data.batis.support.web;

import com.minlia.cloud.data.batis.support.persistence.entity.AbstractDataEntity;
import com.minlia.cloud.data.batis.support.service.BaseService;
import com.minlia.cloud.utils.Encodes;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AbstractBaseEndpoint<SERVICE extends BaseService, ENTITY extends AbstractDataEntity> extends GeneralEndpoint {


    /**
     * 输出到客户端
     *
     * @param fileName 输出文件名
     */
    public void write(HttpServletResponse response, SXSSFWorkbook wb, String fileName) {
        try {
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


//    protected boolean beanValidatorAjax(Object object, Class<?>... groups) {
//        return beanValidator(null, object, groups);
//    }

//    /**
//     * 服务端参数有效性验证
//     *
//     * @param object 验证的实体对象
//     * @param groups 验证组
//     * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
//     */
//    protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
//        try {
//            BeanValidators.validateWithException(validator, object, groups);
//        } catch (ConstraintViolationException ex) {
//            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
//            list.add(0, "数据验证失败：");
//            String[] msg = list.toArray(new String[]{});
//            if (model == null) {
//                throw new RuntimeMsgException(PublicUtil.toAppendStr(msg));
//            } else {
//                model.addAttribute(CustomMessage.createError(msg));
//            }
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 服务端参数有效性验证
//     *
//     * @param object 验证的实体对象
//     * @param groups 验证组
//     * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
//     */
//    protected boolean beanValidatorRe(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
//        try {
//            BeanValidators.validateWithException(validator, object, groups);
//        } catch (ConstraintViolationException ex) {
//            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
//            list.add(0, "数据验证失败：");
//            redirectAttributes.addAttribute(CustomMessage.createError(list.toArray(new String[]{})));
//            return false;
//        }
//        return true;
//    }


}

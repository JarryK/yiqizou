package com.web.model.qo;

import com.web.model.Register;
import javax.validation.constraints.NotBlank;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>com.web.model.qo<br>
 * <b>创建人：</b>DadaYu<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2021/4/12 11:42<br>
 */
public class RegisterAppendQo extends Register {

    @NotBlank(message = "真实名字不能为空")
    private String realName;

    @NotBlank(message = "认证照片1不能为空")
    private String registerPhoto1;

    @NotBlank(message = "认证照片2不能为空")
    private String registerPhoto2;

    @NotBlank(message = "认证照片3不能为空")
    private String registerPhoto3;
}

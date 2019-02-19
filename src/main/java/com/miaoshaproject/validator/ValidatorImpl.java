package com.miaoshaproject.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;


import javax.validation.ConstraintValidator;
import javax.validation.Validation;
import javax.xml.validation.Validator;
import java.util.Set;

public class ValidatorImpl implements InitializingBean{

    private javax.validation.Validator validator;

    // 实现校验方法并返回校验结果
/*    private ValidationResult validate(Object bean){
        ValidationResult result = new ValidationResult();
        Set<ConstraintValidator<Object>> constraintValidatorSet = validator.validate(bean);
        if (constraintValidatorSet.size() > 0){
            // 有错误
            result.setHasErrors(true);
            constraintValidatorSet.forEach(constraintViolation ->{
                String errMsg = constraintViolation.getMessage();
                String propertiesName = constraintViolation.getp
            });
        }
    }*/

    @Override
    public void afterPropertiesSet() throws Exception {
        // 将hibernate validator通过工厂的初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}

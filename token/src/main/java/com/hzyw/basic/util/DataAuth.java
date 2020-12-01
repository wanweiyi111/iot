package com.hzyw.basic.util;

import java.lang.annotation.*;


/**
 * 数据权限实现的注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataAuth {

    /**
     * equipmentCode </br>
     * 权限设备编码字段</br>
     * 值为："设备编码字段名(有别名 使用别名)"</br>
     * 如：原sql为"select equipment_code as equipmentcode from 权限业务表  auth, 业务表A ya where auth.org_id=123",</br>
     * 则该注解应填值为："equipmentcode"
     * @return
     */
    String equipmentCode() default "";

}

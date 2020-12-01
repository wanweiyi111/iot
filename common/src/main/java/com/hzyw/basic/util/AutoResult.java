package com.hzyw.basic.util;

import java.lang.annotation.*;


/**
 * 标注自动封装给前台页面结果集
 *
 * @author hao yuan
 * @date 2019.08.07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoResult {
}

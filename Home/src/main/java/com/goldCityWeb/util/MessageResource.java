/**
 * 
 */
package com.goldCityWeb.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标明该类使用哪个消息文件资源。<br/>
 * 所有的消息文件应当存放在/messages/目录下<br/>
 * 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageResource {
	public String value() default "";
}

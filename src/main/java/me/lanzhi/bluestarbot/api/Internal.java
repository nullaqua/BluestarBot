package me.lanzhi.bluestarbot.api;

import java.lang.annotation.*;

/**
 * 若某个方法,类,子段等有此注解,说明它是插件的内部实现,内部实现不建议调用
 * 插件的内部实现可能在任意版本发生变化,且不会像API一样提供向下兼容.
 * 您的插件如果调用了内部实现,可能因此出错
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE,
         ElementType.ANNOTATION_TYPE,
         ElementType.METHOD,
         ElementType.CONSTRUCTOR,
         ElementType.FIELD,
         ElementType.PACKAGE})
public @interface Internal
{
}

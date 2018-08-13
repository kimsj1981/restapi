package com.sjkim.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.sjkim.common.LogLevel;
import com.sjkim.common.LogType;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Loggable {

	LogLevel logLevel();

	LogType logType();
}

package com.wit.blogs.annotations;

import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@CurrentSecurityContext(expression = "authentication.getName()")
public @interface CurrentUserName {
}

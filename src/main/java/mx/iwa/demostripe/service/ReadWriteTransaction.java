package mx.iwa.demostripe.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT,
    readOnly = false,
    rollbackFor = {Exception.class, RuntimeException.class})
public @interface ReadWriteTransaction {}

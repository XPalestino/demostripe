package mx.iwa.demostripe.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.LOWEST_PRECEDENCE)
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {}

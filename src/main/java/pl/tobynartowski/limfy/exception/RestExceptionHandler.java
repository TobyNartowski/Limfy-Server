package pl.tobynartowski.limfy.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransactionSystemException.class)
    protected ResponseEntity<Set<String>> handleTransactionException(TransactionSystemException e) throws Throwable{
        Throwable cause = e.getCause();
        if (!(cause instanceof RollbackException)) {
            throw cause;
        } else if (!(cause.getCause() instanceof ConstraintViolationException)) {
            throw cause.getCause();
        }

        Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause.getCause()).getConstraintViolations();
        Set<String> errors = new HashSet<>(constraintViolations.size());
        errors.addAll(constraintViolations.stream()
            .map(constraintViolation -> String.format("%s - '%s' %s", constraintViolation.getPropertyPath(),
                    constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
            .collect(Collectors.toSet()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

package com.integration.leatherback.exceptions;

import com.integration.leatherback.models.BaseResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GenericExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseResponse> handleException(EntityNotFoundException ex) {
        log.error("Entity Not Found Exception ::: {}", ex);
        BaseResponse response = new BaseResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleException(RuntimeException ex) {
        log.error("Runtime Exception ::: {}", ex);
        String msg = ex.getMessage();

        if (msg.contains("Internal Server Error")) {
            msg = "We seemed to have missed a step. kindly try again later.";
        } else if (msg.contains("response == null")) {
            msg = "Oops! we could not find that spark, try again";
        }
        BaseResponse response = new BaseResponse(msg);
        return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<BaseResponse> handleException(ResourceAccessException ex) {
        log.error("Resource Access Exception ::: {}", ex);
        BaseResponse response = new BaseResponse("Leatherback is currently unreachable");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponse> handleException(DataIntegrityViolationException ex, WebRequest request) {
        log.error("Data Integrity Violation Exception ::: {}", ex);

        String message = ex.getMostSpecificCause().getMessage();

        if (message != null) {
            message = message.split("for")[0];
        }

        BaseResponse baseResponse = new BaseResponse(message);

        return new ResponseEntity<>(baseResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse> handleException(ConstraintViolationException ex) {
        log.error("Constraint Violation Exception ::: {}", ex);
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append("\n").append(violation.getMessage()));
            errorMessage = builder.toString();
        }
        BaseResponse baseResponse = new BaseResponse(errorMessage);

        return new ResponseEntity<>(baseResponse, HttpStatus.CONFLICT);
    }
}

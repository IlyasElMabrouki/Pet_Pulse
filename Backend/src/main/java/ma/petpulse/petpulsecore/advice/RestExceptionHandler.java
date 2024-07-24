package ma.petpulse.petpulsecore.advice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import ma.petpulse.petpulsecore.exceptions.ApplicationNotFoundException;
import ma.petpulse.petpulsecore.exceptions.ReportNotFoundException;
import ma.petpulse.petpulsecore.exceptions.RequestBodyNotValid;
import ma.petpulse.petpulsecore.exceptions.UserNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSqlIntegrityException(HttpServletRequest req,SQLIntegrityConstraintViolationException ex){
        String error = "Unable to submit post: " + ex.getMessage();
        return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, error));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(HttpServletRequest req,NoSuchElementException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
        response.setMessage("The row for address is not existent: " + req.getRequestURI());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(HttpServletRequest req, UsernameNotFoundException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED);
        response.setMessage("Bad credentials: " + req.getRequestURI());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(HttpServletRequest req, BadCredentialsException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED);
        response.setMessage("Bad credentials: " + req.getRequestURI());
        return buildResponseEntity(response);
    }

    // access denied 403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(HttpServletRequest req,AccessDeniedException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN);
        response.setMessage("Access denied: " + ex.getMessage() + " " + req.getRequestURI());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(HttpServletRequest req,SignatureException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN);
        response.setMessage("Invalid token: " + req.getRequestURI());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(HttpServletRequest req,ExpiredJwtException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN);
        response.setMessage("Expired token: " + req.getRequestURI());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(HttpServletRequest req,UserNotFoundException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
        response.setMessage(ex.getMessage() + " " + req.getRequestURI());
        return buildResponseEntity(response);
    }


    public ResponseEntity<Object> handleMethodArgumentNotValid(HttpServletRequest req,MethodArgumentNotValidException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);
        response.setMessage("Validation error: " + ex.getMessage() + " " + req.getRequestURI());
        return buildResponseEntity(response);
    }

    @Override
    protected ResponseEntity<java.lang.Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleMethodArgumentNotValid(((ServletWebRequest) request).getRequest(), ex);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(HttpServletRequest req, IllegalStateException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);
        response.setMessage("Illegal state: " + ex.getMessage() + " " + req.getRequestURI());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<Object> handleApplicationNotFoundException(HttpServletRequest req, ApplicationNotFoundException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
        response.setMessage(ex.getMessage() + " " + req.getRequestURI());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<Object> handleReportNotFoundException(HttpServletRequest req, ReportNotFoundException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
        response.setMessage(ex.getMessage() + " " + req.getRequestURI());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(RequestBodyNotValid.class)
    public ResponseEntity<Object> handleRequestBodyNotValidException(HttpServletRequest req, RequestBodyNotValid ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);
        response.setMessage(ex.getMessage() + " " + req.getRequestURI());
        return buildResponseEntity(response);
    }



    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse){
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

}

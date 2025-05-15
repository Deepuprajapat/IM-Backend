package com.realestate.invest.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLTransientConnectionException;
import java.time.DateTimeException;
import java.util.ConcurrentModificationException;
import org.apache.catalina.connector.ClientAbortException;
import org.hibernate.HibernateException;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.service.connection.ConnectionDetailsNotFoundException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

/**
 * @author Abhishek Srivastav
 * @version 1.0
 * @implNote This class is used for global exception & response handling 
 */
@ControllerAdvice
public class GlobalExceptionHandler 
{

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

/**
 * Handles SQL exceptions and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The SQL exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleSQLException(SQLException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("SQL Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles Hibernate exceptions and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The Hibernate exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<?> handleHibernateException(HibernateException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Hibernate Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles Persistence Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The Persistence Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<?> handleJpaException(PersistenceException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Persistence Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles NullPointer Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The NullPointer Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Null Pointer Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles Throwable Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The Throwable Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleOtherExceptions(Throwable ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Throwable Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles MethodArgumentTypeMismatch Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The MethodArgumentTypeMismatch Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Method Argument Type Mismatch Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles Arithmetic Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The Arithmetic Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Object> handleArithmeticException(ArithmeticException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Arithmetic Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles ClassCast Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The ClassCast Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<Object> handleClassCastException(ClassCastException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Class Cast Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles DateTimeException Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The DateTimeException Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<Object> handleDateTimeExceptions(DateTimeException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Date Time Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles ArrayIndexOutOfBounds Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The ArrayIndexOutOfBounds Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<Object> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Array Index Out Of Bounds Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles NegativeArraySize Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The NegativeArraySize Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(NegativeArraySizeException.class)
    public ResponseEntity<Object> handleNegativeArraySizeException(NegativeArraySizeException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Negative Array Size Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles ArrayStore Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The ArrayStore Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(ArrayStoreException.class)
    public ResponseEntity<Object> handleArrayStoreException(ArrayStoreException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("ArrayStore Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles UnsupportedOperation Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The UnsupportedOperation Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> handleUnsupportedOperationException(UnsupportedOperationException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Unsupported Operation Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles ConcurrentModification Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The ConcurrentModification Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(ConcurrentModificationException.class)
    public ResponseEntity<Object> handleConcurrentModificationException(ConcurrentModificationException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Concurrent Modification Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles IOException and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The IOException that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException(IOException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("IO Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles Instantiation Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The Instantiation Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(InstantiationException.class)
    public ResponseEntity<Object> handleInstantiationException(InstantiationException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Instantiation Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles FileNotFound Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The FileNotFound Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("FileNotFoundException Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles IllegalThreadState Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The IllegalThreadState Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(IllegalThreadStateException.class)
    public ResponseEntity<?> handleIllegalThreadStateException(IllegalThreadStateException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Illegal Thread State Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

/**
 * Handles Socket Timeout Exception and returns an error response with a 500 Internal Server Error status code.
 *
 * @param ex The Socket Timeout Exception that occurred.
 * @return A ResponseEntity containing an ErrorResponse.
 */
    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<?> handleSocketTimeoutException(SocketTimeoutException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Socket Timeout Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles Client Abort Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex The Client Abort Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(ClientAbortException.class)
    public ResponseEntity<?> handleClientAbortException(ClientAbortException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Client Abort Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles Connect Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex The Connect Timeout Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<?> handleConnectException(ConnectException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Connection Exception: " + ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles Interrupted Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex The Interrupted Timeout Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<?> handleInterruptedException(InterruptedException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Interrupted Exception: " + ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handles Entity Not Found Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex The Entity Not Found Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Entity Not Found Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handles Illegal Argument Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex The Illegal Argument Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Illegal Argument Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles JDBC Connection Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex The JDBC Connection Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<?> handleJDBCConnectionException(JDBCConnectionException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("JDBC Connection Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles Cannot Create Transaction Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex The Cannot Create Transaction Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<?> handleCannotCreateTransactionException(CannotCreateTransactionException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Cannot Create Transaction Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles Cannot HttpRequest Method Not Supported Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex The HttpRequest Method Not Supported Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("HttpRequest Method Not Supported Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles Cannot Constraint Violation Not Supported Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex The Constraint Violation Not Supported Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Constraint Violation Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handles Cannot Transaction System Not Supported Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex The Transaction System Not Supported Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<?> handleTransactionSystemException(TransactionSystemException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Transaction System Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handles SQL Non Transient Connection Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex SQL Non Transient Connection Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(SQLNonTransientConnectionException.class)
    public ResponseEntity<?> handleSQLNonTransientConnectionException(SQLNonTransientConnectionException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("SQL Non Transient Connection Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handles Data Access Resource Failure Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex Data Access Resource Failure Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<?> handleDataAccessResourceFailureException(DataAccessResourceFailureException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Data Access Resource Failure Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handles SQL Transient Connection Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex SQL Transient Connection Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(SQLTransientConnectionException.class)
    public ResponseEntity<?> handleSQLTransientConnectionException(SQLTransientConnectionException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("SQL Transient Connection Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handles Connection Details Not Found Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex Connection Details Not Found Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(ConnectionDetailsNotFoundException.class)
    public ResponseEntity<?> handleConnectionDetailsNotFoundException(ConnectionDetailsNotFoundException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Connection Details Not Found Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handles Illegal State Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex Illegal State Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Illegal State Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles Request Rejected  Exception and returns an error response with a 500 Internal Server Error status code.
     *
     * @param ex Request Rejected  Exception that occurred.
     * @return A ResponseEntity containing an ErrorResponse.
     */
    @ExceptionHandler(RequestRejectedException.class)
    public ResponseEntity<?> handleRequestRejectedException(RequestRejectedException ex) 
    {
        ErrorResponse errorResponse = new ErrorResponse("Request Rejected Exception: "+ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.info(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

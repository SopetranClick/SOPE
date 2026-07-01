package com.sope.sopetran_click.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura los errores de validación (por ejemplo, cuando en el DTO usas @NotBlank, @NotNull, etc.
     * y el cliente no envía esos datos obligatorios).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("estado", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Datos de entrada inválidos");
        body.put("mensaje", "La solicitud contiene datos faltantes o con formato incorrecto.");

        // Extraemos cada campo fallido junto con su respectivo mensaje de validación
        Map<String, String> detallesErrores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            detallesErrores.put(error.getField(), error.getDefaultMessage());
        }
        body.put("campos_invalidos", detallesErrores);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura errores cuando el cliente envía un JSON mal estructurado, roto,
     * o valores que no corresponden al tipo de dato esperado (ej: texto en un campo Double).
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("estado", HttpStatus.BAD_REQUEST.value());
        body.put("error", "JSON mal formado");
        body.put("mensaje", "El cuerpo de la solicitud no se puede procesar. Verifica comas, llaves o tipos de datos.");
        body.put("detalle_tecnico", ex.getMostSpecificCause().getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura errores cuando se envía un tipo de dato incorrecto en la URL.
     * Ejemplo: si el backend espera un ID numérico "/estates/12" y el usuario envía "/estates/hola".
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("estado", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Tipo de parámetro incorrecto");

        String mensaje = String.format("El parámetro '%s' debe ser de tipo %s y enviaste '%s'",
                ex.getName(), ex.getRequiredType().getSimpleName(), ex.getValue());
        body.put("mensaje", mensaje);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura errores cuando el cliente intenta acceder a una ruta usando un método HTTP no soportado.
     * Ejemplo: intentar usar un POST en una ruta donde solo se permite GET.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("estado", HttpStatus.METHOD_NOT_ALLOWED.value());
        body.put("error", "Método HTTP no permitido");

        String mensaje = String.format("El método %s no está soportado para esta ruta. Los métodos permitidos son: %s",
                ex.getMethod(), ex.getSupportedHttpMethods());
        body.put("mensaje", mensaje);

        return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Captura las excepciones controladas de tipo RuntimeException lanzadas por tus servicios
     * (como "Finca no encontrada" o "Alojamiento base no encontrado").
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("mensaje", ex.getMessage());
        body.put("descripcion", request.getDescription(false));

        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex.getMessage().toLowerCase().contains("no encontrada") || ex.getMessage().toLowerCase().contains("no encontrado")) {
            status = HttpStatus.NOT_FOUND;
        }

        body.put("estado", status.value());
        body.put("error", status.getReasonPhrase());

        return new ResponseEntity<>(body, status);
    }

    /**
     * Capturador de último recurso para cualquier otro error imprevisto (ej. fallos en la conexión a la base de datos).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("mensaje", "Ocurrió un error inesperado en el servidor. Inténtelo más tarde.");
        body.put("detalle", ex.getMessage());
        body.put("estado", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
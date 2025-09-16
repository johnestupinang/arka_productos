package com.arka.products.utilidades.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Para errores de Producto
    @ExceptionHandler(ProductoException.class)
    public ResponseEntity<ErrorResponse> handleProductoException(ProductoException exception){
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensaje("Error en producto")
                .estado(exception.getStatus().value())
                .fecha(LocalDateTime.now())
                .errores(errores)
                .build();

        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    //Para errores de Paus
    @ExceptionHandler(PaisException.class)
    public ResponseEntity<ErrorResponse> handlePaisException(PaisException exception){
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensaje("Error en pais")
                .estado(exception.getStatus().value())
                .fecha(LocalDateTime.now())
                .errores(errores)
                .build();

        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    //Para errores de Marca
    @ExceptionHandler(MarcaException.class)
    public ResponseEntity<ErrorResponse> handleMarcaException(MarcaException exception){
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensaje("Error en marca")
                .estado(exception.getStatus().value())
                .fecha(LocalDateTime.now())
                .errores(errores)
                .build();

        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    //Para errores de Categoria
    @ExceptionHandler(CategoriaException.class)
    public ResponseEntity<ErrorResponse> handleCategoriaException(CategoriaException exception){
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensaje("Error en categoria")
                .estado(exception.getStatus().value())
                .fecha(LocalDateTime.now())
                .errores(errores)
                .build();

        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    //Para errores de Proveedor
    @ExceptionHandler(ProveedorException.class)
    public ResponseEntity<ErrorResponse> handleProveedorException(ProveedorException exception){
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensaje("Error en proveedor")
                .estado(exception.getStatus().value())
                .fecha(LocalDateTime.now())
                .errores(errores)
                .build();

        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    // Handler genérico (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        Map<String,String> errores = new HashMap<>();
        errores.put("mensaje", ex.getMessage() != null ? ex.getMessage() : "Error interno");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensaje("Error interno del servidor")
                .estado(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .fecha(LocalDateTime.now())
                .errores(errores)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {

        Map<String, String> error = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            error.put(field, message);
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensaje("Validacion de la peticion fallida")
                .estado(HttpStatus.BAD_REQUEST.value())
                .fecha(LocalDateTime.now())
                .errores(error)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @Getter
    @Setter
    @Builder
    public static class ErrorResponse{
        private String mensaje;
        private int estado;
        private LocalDateTime fecha;
        private Map<String, String> errores;

        public ErrorResponse(String mensaje, int estado, LocalDateTime fecha, Map<String, String> errores){
            this.mensaje = mensaje;
            this.estado = estado;
            this.fecha = fecha;
            this.errores = errores;
        }

    }

}

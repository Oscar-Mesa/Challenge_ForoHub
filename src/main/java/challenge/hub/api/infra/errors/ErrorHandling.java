package challenge.hub.api.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ErrorHandling {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity Error404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity Error400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(DataErrorValidation::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    private record DataErrorValidation(String field, String error) {
        public DataErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}

package toyproject.genshin.teybatguidecrawler.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class TeybatExceptionHandler {

    @ExceptionHandler(TeybatException.class)
    public ResponseEntity<String> handleNotFoundException(
            Exception exception,
            HttpServletRequest request
    ) {
        log.error("🧨 url: {}, message: {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}

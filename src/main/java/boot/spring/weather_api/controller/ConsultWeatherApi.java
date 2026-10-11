package boot.spring.weather_api.controller;

import boot.spring.weather_api.service.WeatherService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class ConsultWeatherApi {

    private final WeatherService weatherService;

    public ConsultWeatherApi(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    @RateLimiter(name = "apiweather", fallbackMethod = "fallback")
    public ResponseEntity<?> consultCity(@PathVariable("city") String city) {
        return ResponseEntity.ok(weatherService.checkCity(city));
    }

    public ResponseEntity<?> fallback(String city, RequestNotPermitted ex) {
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Limite de requisicoes atingido. Tente novamente em alguns segundos.");
    }
}

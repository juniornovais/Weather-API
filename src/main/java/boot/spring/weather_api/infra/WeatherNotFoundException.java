package boot.spring.weather_api.infra;

public class WeatherNotFoundException extends RuntimeException{

    public WeatherNotFoundException (String message) {
        super(message);
    }
}

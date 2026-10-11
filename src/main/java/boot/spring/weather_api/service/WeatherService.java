package boot.spring.weather_api.service;

import boot.spring.weather_api.dto.VisualCrossingResponse;
import boot.spring.weather_api.infra.WeatherNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherService {
    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    public VisualCrossingResponse checkCity(String city){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = apiUrl + "/{city}?unitGroup=metric&key={key}&contentType=json";

            ResponseEntity<VisualCrossingResponse> resp = restTemplate.getForEntity(
                    url,
                    VisualCrossingResponse.class,
                    city,
                    apiKey
            );
            return resp.getBody();
        } catch (Exception e)  {
            throw new WeatherNotFoundException("City not found.");
        }
    }
}

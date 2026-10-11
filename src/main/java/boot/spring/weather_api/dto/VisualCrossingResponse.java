package boot.spring.weather_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VisualCrossingResponse(
        Integer queryCost,
        Double latitude,
        Double longitude,
        String resolvedAddress,
        String address,
        String timezone,
        Double tzoffset,
        String description,
        List<DayDto> days
) {



        @JsonIgnoreProperties(ignoreUnknown = true)
        public record DayDto(
                String datetime,
                Long datetimeEpoch,
                Double tempmax,
                Double tempmin,
                Double temp,
                Double humidity,
                Double precip,
                Double precipprob,
                Double windgust,
                Double windspeed,
                Double winddir,
                Double pressure,
                Double cloudcover,
                Double visibility,
                String sunrise,
                Long sunriseEpoch,
                String sunset,
                Long sunsetEpoch,
                Object conditions, // Pode vir como String ou número dependendo do formato, mantemos Object ou String
                Object description,
                Object icon,
                List<String> preciptype,
                List<String> stations
        ) {}
}
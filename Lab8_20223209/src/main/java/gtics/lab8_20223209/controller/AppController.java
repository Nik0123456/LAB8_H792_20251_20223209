package gtics.lab8_20223209.controller;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import gtics.lab8_20223209.entity.MonitoreoClimatico;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    @Autowired
    private gtics.lab8_20223209.repository.MonitoreoClimaticoRepository monitoreoClimaticoRepository;

    @GetMapping("/api/current-time")
    public ResponseEntity<HashMap<String,Object>> getCurrentTime(@RequestParam("ciudad") String ciudad) {

        HashMap<String,Object> response = new HashMap<>();

        String call = "https://api.weatherapi.com/v1/forecast.json?key=88e12060abad41ab97212738250906&q="+ ciudad + "&days=1";

        try {
            RestTemplate restTemplate = new RestTemplate();
            String apiResponse = restTemplate.getForObject(call, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(apiResponse);
            JsonNode current = root.path("current");
            if (!current.isMissingNode()) {
                String conditionText = current.path("condition").path("text").asText();
                double feelslikeC = current.path("feelslike_c").asDouble();
                double temp_c = current.path("temp_c").asDouble();
                int humidity = current.path("humidity").asInt();
                response.put("temp_c", temp_c);
                response.put("condition_text", conditionText);
                response.put("feelslike_c", feelslikeC);
                response.put("humidity", humidity);
            } else {
                response.put("error", "No se encontró información de clima actual para la ciudad especificada.");
            }
        } catch (Exception e) {
            response.put("error", "Error al consultar la API externa: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/specific-time")
    public ResponseEntity<HashMap<String,Object>> getSpecificTime(@RequestParam("ciudad") String ciudad, @RequestParam("hora") String hora) {
        HashMap<String,Object> response = new HashMap<>();
        String call = "https://api.weatherapi.com/v1/forecast.json?key=88e12060abad41ab97212738250906&q="+ ciudad + "&dt=" + hora;
        try {
            RestTemplate restTemplate = new RestTemplate();
            String apiResponse = restTemplate.getForObject(call, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(apiResponse);
            JsonNode forecast = root.path("forecast").path("forecastday").get(0).path("hour");
            if (forecast.isArray()) {
                for (JsonNode hourNode : forecast) {
                    if (hourNode.path("time").asText().contains(hora)) {
                        HashMap<String, Object> hourData = new HashMap<>();
                        String timeFull = hourNode.path("time").asText();
                        String onlyHour = timeFull.substring(timeFull.length() - 5); // Extrae solo HH:mm
                        hourData.put("hour", onlyHour);
                        hourData.put("temp_c", hourNode.path("temp_c").asDouble());
                        hourData.put("condition", hourNode.path("condition").path("text").asText());
                        response.put("city", ciudad);
                        response.put("forecast", java.util.Collections.singletonList(hourData));
                        return ResponseEntity.ok(response);
                    }
                }
                response.put("error", "No se encontró información de clima para la hora especificada.");
            } else {
                response.put("error", "No se encontró información de clima actual para la ciudad especificada.");
            }
        } catch (Exception e) {
            response.put("error", "Error al consultar la API externa: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/api/save-data", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveData(@RequestBody MonitoreoClimatico monitoreo) {
        try {
            monitoreoClimaticoRepository.save(monitoreo);
            return ResponseEntity.status(HttpStatus.CREATED).body("Datos guardados correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar los datos: " + e.getMessage());
        }
    }

}

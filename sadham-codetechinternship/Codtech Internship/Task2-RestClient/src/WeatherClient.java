import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

/**
 * WeatherClient.java
 * ----------------------------------------------------
 * This program consumes a public weather REST API,
 * sends an HTTP request, parses the JSON response,
 * and displays it in a structured console format.
 * ----------------------------------------------------
 * Developed for CODTECH Java Internship Task-2
 */
public class WeatherClient {

    public static void main(String[] args) {
        try {
            // Create HTTP client instance
            HttpClient client = HttpClient.newHttpClient();

            // Public API URL (Example: weather data for Chennai)
            String apiURL = "https://wttr.in/Chennai?format=j1";

            // Build HTTP GET request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiURL))
                    .GET()
                    .build();

            // Send request and store response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON response
            JSONObject json = new JSONObject(response.body());
            JSONObject current = json.getJSONArray("current_condition").getJSONObject(0);

            String temperature = current.getString("temp_C");
            String weatherDesc = current.getJSONArray("weatherDesc").getJSONObject(0).getString("value");
            String humidity = current.getString("humidity");
            String windSpeed = current.getString("windspeedKmph");

            // Display data in structured format
            System.out.println("\n===== WEATHER REPORT =====");
            System.out.println("Location     : Chennai");
            System.out.println("Temperature  : " + temperature + "°C");
            System.out.println("Condition    : " + weatherDesc);
            System.out.println("Humidity     : " + humidity + "%");
            System.out.println("Wind Speed   : " + windSpeed + " km/h");
            System.out.println("=========================\n");

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
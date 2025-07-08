
import java.net.URI;
import java.net.http.HttpClient; 
import java.net.http.HttpRequest; 
import java.net.http.HttpResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper; 

public class HttpClientExample {

    public static void main(String[] args) {
    
        HttpClient client = HttpClient.newHttpClient();
        String apiUrl = "https://api.github.com/users/google/repos"; 

        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET() 
                .build();

        try {
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

           
            System.out.println("Response Status Code: " + response.statusCode()); 
            System.out.println("\nResponse Body (first 500 chars):\n" + response.body().substring(0, Math.min(response.body().length(), 500))); // Print the response body.

            :
            System.out.println("\n--- Parsing JSON (Optional) ---");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());

            if (rootNode.isArray()) {
                System.out.println("First 3 repository names:");
                for (int i = 0; i < Math.min(rootNode.size(), 3); i++) {
                    JsonNode repoNode = rootNode.get(i);
                    String repoName = repoNode.get("name").asText();
                    System.out.println("- " + repoName);
                }
            } else {
                System.out.println("Response is not a JSON array.");
            }


        } catch (IOException | InterruptedException e) {
            System.err.println("Error during HTTP request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
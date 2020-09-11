package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Converter {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseCurrency;
    private final String targetCurrency;
    private final float amount;

    public Converter(String baseCurrency, String targetCurrency, float amount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
    }

    private float getRate() throws JsonProcessingException {

        String url = "https://api.exchangeratesapi.io/latest?" + baseCurrency;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseEntity.getBody());
        String rates1 = "rates";
        JsonNode name = root.get(rates1);
        JsonNode rates = name.get(targetCurrency);
        float rate = rates.floatValue();
        System.out.println("\nThe rate of " + baseCurrency + " against " + targetCurrency + " is: " + rate);

        return rate;
    }

    public float exchange() throws JsonProcessingException {
        float conversion = getRate() * amount;
        System.out.println(amount + " " + baseCurrency + " = " + conversion + " " + targetCurrency);
        return conversion;
    }
}

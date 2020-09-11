package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class GetUserInput {
    private final RestTemplate restTemplate = new RestTemplate();

    private List<String> getListOfCurrencies() throws JsonProcessingException {
        String url = "https://api.exchangeratesapi.io/latest";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseEntity.getBody());
        String rates = "rates";
        JsonNode name = root.get(rates);

        List<String> list = new ArrayList<>();
        Iterator<Map.Entry<String, JsonNode>> fields = name.fields();
        list.add("EUR");
        while(fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String fieldName = field.getKey();
            list.add(fieldName);
        }
        list.sort(String::compareToIgnoreCase);
        return list;
    }

    public HashMap<String, String> getCurrencies() throws JsonProcessingException {

        List<String> currencies = getListOfCurrencies();

        boolean check1 = false;
        boolean check2 = false;
        HashMap<String, String> selectedCurrencies = new HashMap<>();

        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nChoose a base currency among: " + currencies + "\n>>");
            String baseCurrencyInput = scanner.nextLine().toUpperCase();

            boolean baseCurrencyCheck = currencies.contains(baseCurrencyInput);

            if(!baseCurrencyCheck) {
                System.out.println(baseCurrencyInput + " does not exist, choose another one");
            } else {
                System.out.println("selected currency: " + baseCurrencyInput);
                selectedCurrencies.put("base_currency", baseCurrencyInput);
                check1 = true;
            }
        } while (!check1);

        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nChoose a target currency among: " + currencies + "\n>>");
            String targetCurrencyInput = scanner.nextLine().toUpperCase();

            boolean targetCurrencyCheck = currencies.contains(targetCurrencyInput);

            if(!targetCurrencyCheck) {
                System.out.println(targetCurrencyInput + " does not exist, choose another one");
            } else {
                System.out.println("selected currency: " + targetCurrencyInput);
                selectedCurrencies.put("target_currency", targetCurrencyInput);
                check2 = true;
            }
        } while (!check2);
        return selectedCurrencies;
    }

    public float getAmount() {

        boolean intChecker = false;
        float amount = 0;

        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose an amount to convert: ");
            if (!scanner.hasNextFloat()) {
                System.out.println("This is not a positive number, please try again.");
            } else{
                amount = scanner.nextFloat();
                intChecker = true;
                if (amount <= 0) {
                    System.out.println("This is not a positive number, please try again.");
                    intChecker = false;
                }
            }
        } while (!intChecker);

        return amount;
    }
}

import com.fasterxml.jackson.core.JsonProcessingException;
import utils.Converter;
import utils.GetUserInput;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        GetUserInput input = new GetUserInput();
        HashMap<String, String> currencies = input.getCurrencies();
        float amount = input.getAmount();

        Converter converter = new Converter(currencies.get("base_currency"),currencies.get("target_currency"), amount);
        //Converter converter = new Converter("EUR", "CZK", 100);
        converter.exchange();
    }
}

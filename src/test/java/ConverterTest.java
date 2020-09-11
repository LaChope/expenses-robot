import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import utils.Converter;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    @Test
    void testConversionWithFixedInput() throws JsonProcessingException {
        Converter converter = new Converter("EUR", "CZK", 100);
        assertEquals(2658.5,  converter.exchange());
    }
}
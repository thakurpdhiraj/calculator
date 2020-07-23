package in.dhitha.tofrompanels.currency;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Slf4j
public class CurrencyUtil {

  private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private static final HashMap<String,Double> rates = new HashMap<>();
  public static final List<String> currencyList =
      Arrays.asList(
          "AUD", "BGN", "BRL", "CAD", "CHF", "CLP", "CNY", "CZK", "DKK", "EUR", "GBP", "HKD",
          "HRK", "HUF", "IDR", "ILS", "INR", "ISK", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD",
          "PHP", "PLN", "RON", "RUB", "SEK", "SGD", "THB", "TRY", "USD", "ZAR");

  public static void getExchangeRate(Date selectedDate) {
    log.info("Exchange Api called");
    String result = null;
    String fetchDate = simpleDateFormat.format(selectedDate);
    String fetchUrl = "https://api.exchangeratesapi.io/" + fetchDate;
    HttpGet request = new HttpGet(fetchUrl);
    try (CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(request)) {
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        result = EntityUtils.toString(entity);
        EntityUtils.consumeQuietly(entity);
        JsonElement jsonElement = JsonParser.parseString(result);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject rateObject = jsonObject.get("rates").getAsJsonObject();
        for (String rate : rateObject.keySet()) {
          rates.put(rate, rateObject.get(rate).getAsDouble());
        }
      }
    } catch (IOException e) {
      log.error("Error while fetching exchange rate  : ", e);
    }
  }

  public static  Map<String,Double> getExchangeValue(Double fromValue, Double toValue, String fromSelected,String toSelected){
    Map<String,Double> returnMap = new HashMap<>();
    Double fromConverted = rates.get(fromSelected);
    Double toConverted = rates.get(toSelected);
    returnMap.put("fromValue",toValue * (fromConverted / toConverted));
    returnMap.put("toValue",fromValue * (toConverted / fromConverted));
    return returnMap;
  }

}

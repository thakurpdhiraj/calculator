package in.dhitha.tofrompanels.time;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeUtil {
  public static final List<String> timeList;
  // list of time values wrt 1 year
  public static final HashMap<String, Double> timeValues;

  static {
    timeList =
        Arrays.asList(
            "Microseconds",
            "Milliseconds",
            "Seconds",
            "Minutes",
            "Hours",
            "Days",
            "Weeks",
            "Years");
    timeValues = new HashMap<>();
    timeValues.put("Years", 1.0);
    timeValues.put("Weeks", 52.17857);
    timeValues.put("Days", 365.25);
    timeValues.put("Hours", 8766.0);
    timeValues.put("Minutes", 525960.0);
    timeValues.put("Seconds", 31557600.0);
    timeValues.put("Milliseconds", 31557600000.0);
    timeValues.put("Microseconds", 31557600000000.0);
  }

  public static Map<String,Double> convertTime(Double fromValue, Double toValue, String fromSelected, String toSelected){
    Map<String,Double> returnMap = new HashMap<>();
    Double fromConverted = timeValues.get(fromSelected);
    Double toConverted = timeValues.get(toSelected);
    returnMap.put("fromValue",toValue * (fromConverted / toConverted));
    returnMap.put("toValue",fromValue * (toConverted / fromConverted));
    return returnMap;
  }
}

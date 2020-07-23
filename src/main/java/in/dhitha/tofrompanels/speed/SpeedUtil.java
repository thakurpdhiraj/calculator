package in.dhitha.tofrompanels.speed;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeedUtil {
  public static final List<String> speedList;
  // list of length values wrt 1 nautical mile
  public static final HashMap<String, Double> speedValues;

  static {
    speedList =
        Arrays.asList(
            "Mach",
            "Knots",
            "Miles per hour",
            "Feet per second",
            "Kilometers per hour",
            "Meters per second",
            "Centimeters per second");
    speedValues = new HashMap<>();
    speedValues.put("Mach", 1.0);
    speedValues.put("Knots", 661.5474);
    speedValues.put("Miles per hour", 761.2975);
    speedValues.put("Feet per second", 1116.47);
    speedValues.put("Kilometers per hour", 1225.08);
    speedValues.put("Meters per second", 340.3);
    speedValues.put("Centimeters per second", 34030.0);
  }

  public static Map<String,Double> convertSpeed(Double fromValue, Double toValue, String fromSelected, String toSelected){
    Map<String,Double> returnMap = new HashMap<>();
    Double fromConverted = speedValues.get(fromSelected);
    Double toConverted = speedValues.get(toSelected);
    returnMap.put("fromValue",toValue * (fromConverted / toConverted));
    returnMap.put("toValue",fromValue * (toConverted / fromConverted));
    return returnMap;
  }
}
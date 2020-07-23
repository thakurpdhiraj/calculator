package in.dhitha.tofrompanels.area;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaUtil {
  public static final List<String> areaList;
  // list of area values wrt 1 sq miles
  public static final HashMap<String, Double> areaValues;

  static {
    areaList =
        Arrays.asList(
            "Square Millimeters",
            "Square Centimeters",
            "Square Meters",
            "Hectares",
            "Square Kilometers",
            "Square Inches",
            "Square Feet",
            "Square Yards",
            "Acres",
            "Square Miles");
    areaValues = new HashMap<>();
    areaValues.put("Square Miles", 1.0);
    areaValues.put("Acres", 640.0);
    areaValues.put("Square Yards", 3097600.0);
    areaValues.put("Square Feet", 27878400.0);
    areaValues.put("Square Inches", 4014489600.0);
    areaValues.put("Square Kilometers", 2.589988);
    areaValues.put("Hectares", 258.9988);
    areaValues.put("Square Meters", 2589988.0);
    areaValues.put("Square Centimeters", 25899881103.0);
    areaValues.put("Square Millimeters", 2589988110336.0);
  }

  public static Map<String, Double> convertArea(
      Double fromValue, Double toValue, String fromSelected, String toSelected) {
    Map<String,Double> returnMap = new HashMap<>();
    Double fromConverted = areaValues.get(fromSelected);
    Double toConverted = areaValues.get(toSelected);
    returnMap.put("fromValue",toValue * (fromConverted / toConverted));
    returnMap.put("toValue",fromValue * (toConverted / fromConverted));
    return returnMap;
  }
}

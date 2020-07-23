package in.dhitha.tofrompanels.length;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LengthUtil {
  public static final List<String> lengthList;
  // list of length values wrt 1 nautical mile
  public static final HashMap<String, Double> lengthValues;

  static {
    lengthList =
        Arrays.asList(
            "Nautical Miles",
            "Miles",
            "Yards",
            "Feet",
            "Inches",
            "Kilometers",
            "Meters",
            "Centimeters",
            "Millimeters",
            "Microns",
            "Nanometers");
    lengthValues = new HashMap<>();
    lengthValues.put("Nautical Miles", 1.0);
    lengthValues.put("Miles", 1.150779);
    lengthValues.put("Yards", 2025.372);
    lengthValues.put("Feet", 6076.115);
    lengthValues.put("Inches", 72913.39);
    lengthValues.put("Kilometers", 1.852);
    lengthValues.put("Meters", 1852.0);
    lengthValues.put("Centimeters", 185200.0);
    lengthValues.put("Millimeters", 1852000.0);
    lengthValues.put("Microns", 1852000000.0);
    lengthValues.put("Nanometers", 1852000000000.0);
  }

  public static Map<String,Double> convertLength(Double fromValue, Double toValue, String fromSelected, String toSelected){
    Map<String,Double> returnMap = new HashMap<>();
    Double fromConverted = lengthValues.get(fromSelected);
    Double toConverted = lengthValues.get(toSelected);
    returnMap.put("fromValue",toValue * (fromConverted / toConverted));
    returnMap.put("toValue",fromValue * (toConverted / fromConverted));
    return returnMap;
  }
}

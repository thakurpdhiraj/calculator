package in.dhitha.tofrompanels.volume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VolumeUtil {
  public static final List<String> volumeList;
  // list of length values wrt 1 nautical mile
  public static final HashMap<String, Double> volumeValues;

  static {
    volumeList =
        Arrays.asList(
            "Litres",
            "Millilitres",
            "Cubic Centimeters",
            "Cubic Meters",
            "Cubic Inches",
            "Cubic Feet",
            "Cubic Yards");
    volumeValues = new HashMap<>();
    volumeValues.put("Litres", 1.0);
    volumeValues.put("Millilitres", 1000.0);
    volumeValues.put("Cubic Centimeters", 1000.0);
    volumeValues.put("Cubic Meters", 0.001);
    volumeValues.put("Cubic Inches", 61.02374);
    volumeValues.put("Cubic Feet", 0.035315);
    volumeValues.put("Cubic Yards", 0.001308);
  }

  public static Map<String,Double> convertVolume(Double fromValue, Double toValue, String fromSelected, String toSelected){
    Map<String,Double> returnMap = new HashMap<>();
    Double fromConverted = volumeValues.get(fromSelected);
    Double toConverted = volumeValues.get(toSelected);
    returnMap.put("fromValue",toValue * (fromConverted / toConverted));
    returnMap.put("toValue",fromValue * (toConverted / fromConverted));
    return returnMap;
  }
}

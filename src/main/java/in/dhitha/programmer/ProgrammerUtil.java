package in.dhitha.programmer;

public class ProgrammerUtil {

  public static String decimalToBinary(Long decNumber) throws Exception {
    return Long.toBinaryString(decNumber);
  }

  public static String decimalToHex(Long decNumber) throws Exception {
    return Long.toHexString(decNumber);
  }

  public static String decimalToOctal(Long decNumber) throws Exception {
    return Long.toOctalString(decNumber);
  }

  public static Long toDecimal(String system, String number) {
    switch (system) {
      case "DEC":
        return Long.parseLong(number);
      case "BIN":
        return Long.parseLong(number, 2);
      case "HEX":
        return Long.parseLong(number, 16);
      case "OCT":
        return Long.parseLong(number, 8);
      default:
        throw new IllegalArgumentException();
    }
  }
}

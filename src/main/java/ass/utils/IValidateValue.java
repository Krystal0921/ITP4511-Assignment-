package ass.utils;

public interface IValidateValue {
  public static boolean isAllValid(IValidateValue... values) {
    for (IValidateValue value : values) {
      if (!value.isValid())
        return false;
    }

    return true;
  }

  public static String getAllErrorMessage(IValidateValue... values) {
    StringBuilder sb = new StringBuilder();
    boolean firstError = true;

    for (IValidateValue value : values) {
      if (value.isValid())
        continue;

      if (firstError) {
        firstError = false;
      } else {
        sb.append("\n");
      }

      sb.append(value.getErrorMessage());
    }

    return sb.toString();
  }

  public boolean isValid();

  public String getErrorMessage();
}
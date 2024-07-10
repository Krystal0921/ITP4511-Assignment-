package ass.utils;

public class ServletParam<T> implements IValidateValue {
  private String name;
  private T defaultValue;
  private T value;
  private boolean valid;

  public ServletParam(String name) {
    this.name = name;
    this.defaultValue = null;
    this.value = null;
    this.valid = false;
  }

  public ServletParam(String name, T defaultValue) {
    this(name);
    this.defaultValue = defaultValue;
    this.value = defaultValue;
  }

  public String getName() {
    return this.name;
  }

  public T getValue() {
    return this.valid ? this.value : this.defaultValue;
  }

  public void setValue(T value) {
    if (value == null) {
      this.value = null;
      this.valid = false;
    } else {
      this.value = value;
      this.valid = true;
    }
  }

  public boolean isValid() {
    return this.valid;
  }

  public String getErrorMessage() {
    return String.format("Invalid value for param '%s'", this.name);
  }
}
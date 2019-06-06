package utility;

public class Validations {

    public static boolean isFloat(String value) {
        try {
            if (value != null) {
                Float.parseFloat(value);
                return true;
            }
        } catch (NumberFormatException ex) {
        }
        return false;
    }
    public static boolean isEmpty(String value){
        return value.trim().isEmpty();
    }
}

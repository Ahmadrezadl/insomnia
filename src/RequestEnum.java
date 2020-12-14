import java.util.Arrays;

public enum RequestEnum {
    GET,
    POST,
    PUT,
    DELETE;
    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}


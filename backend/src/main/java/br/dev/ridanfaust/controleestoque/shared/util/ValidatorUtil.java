package br.dev.ridanfaust.controleestoque.shared.util;

import java.util.Collection;
import java.util.Map;

public class ValidatorUtil {

    private ValidatorUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isEmpty(Object o) {
        return switch (o) {
            case null -> true;
            case String s -> s.isEmpty();
            case Number number -> number.doubleValue() == 0.0;
            case Object[] objects -> objects.length == 0;
            case int[] ints -> ints.length == 0;
            case Collection<?> objects -> objects.isEmpty();
            case Map<?, ?> map -> map.isEmpty();
            default -> false;
        };
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isMaiorQueZero(Number number) {
        return number != null && number.doubleValue() > 0.0;
    }

    public static boolean isMenorQueZero(Number number) {
        return number != null && number.doubleValue() < 0.0;
    }

}

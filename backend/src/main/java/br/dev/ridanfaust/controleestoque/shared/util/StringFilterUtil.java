package br.dev.ridanfaust.controleestoque.shared.util;

import static br.dev.ridanfaust.controleestoque.shared.util.ValidatorUtil.isEmpty;

public class StringFilterUtil {

    private StringFilterUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String lower(String value) {
        return isEmpty(value) ? null : value.trim().toLowerCase();
    }

}

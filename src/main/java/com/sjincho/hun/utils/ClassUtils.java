package com.sjincho.hun.utils;

import java.util.Optional;

public class ClassUtils {

    private ClassUtils() {
    }

    public static <T> Optional<T> getSafeCastInstance(Object o, Class<T> clazz) {
        if (clazz.isInstance(o)) {
            return Optional.of(clazz.cast(o));
        }
        return Optional.empty();
    }
}

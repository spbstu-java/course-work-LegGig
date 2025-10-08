package core.lab_2;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class lab_2 {
    public static void main(String[] args) {
        TestClass testInstance = new TestClass();
        invokeAnnotatedMethods(testInstance);
    }

    private static void invokeAnnotatedMethods(Object instance) {
        Class<?> clazz = instance.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(InvokeTimes.class)) {
                if (Modifier.isPublic(method.getModifiers())) {
                    continue;
                }

                InvokeTimes annotation = method.getAnnotation(InvokeTimes.class);
                int times = annotation.value();

                method.setAccessible(true);

                Class<?>[] paramTypes = method.getParameterTypes();
                Object[] params = createDefaultValues(paramTypes);

                System.out.println("Invoking method: " + method.getName() + " " + times + " times");

                for (int i = 0; i < times; i++) {
                    try {
                        method.invoke(instance, params);
                    } catch (Exception e) {
                        System.out.println("Error invoking method " + method.getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    private static Object[] createDefaultValues(Class<?>[] paramTypes) {
        Object[] values = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> type = paramTypes[i];

            values[i] = switch (type.getName()) {
                case "int" -> 0;
                case "long" -> 0L;
                case "double" -> 0.0;
                case "float" -> 0.0f;
                case "boolean" -> false;
                case "char" -> 'A';
                case "byte" -> (byte) 0;
                case "short" -> (short) 0;
                case "java.lang.String" -> "default";
                default -> null;
            };
        }
        return values;
    }
}
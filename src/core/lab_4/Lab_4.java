package core.lab_4;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Locale;
import java.util.function.Function;
import java.util.LinkedHashMap;

public class Lab_4 {

    public static OptionalDouble average(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average();
    }

    public static List<String> toNewStrings(List<String> strings) {
        return strings.stream()
                .map(s -> "_new_" + s.toUpperCase(Locale.ROOT))
                .collect(Collectors.toList());
    }

    public static List<Integer> squaresOfDistinct(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(e -> e.getKey() * e.getKey())
                .collect(Collectors.toList());
    }

    public static <T> T lastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, b) -> b)
                .orElseThrow(() -> new NoSuchElementException("Коллекция пуста"));
    }

    public static int sumOfEvenNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    public static Map<Character, String> toMapByFirstChar(List<String> strings) {
        return strings.stream()
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.length() > 1 ? s.substring(1) : "",
                        (existing, replacement) -> replacement,
                        LinkedHashMap::new
                ));
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Среднее: " + average(numbers));

        List<String> strings = Arrays.asList("apple", "banana", "cherry");
        System.out.println("Префикс + верхний регистр: " + toNewStrings(strings));

        List<Integer> duplicates = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        System.out.println("Квадраты уникальных элементов: " + squaresOfDistinct(duplicates));

        Collection<String> collection = Arrays.asList("a", "b", "c");
        System.out.println("Последний элемент: " + lastElement(collection));

        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("Сумма чётных чисел массива: " + sumOfEvenNumbers(arr));

        List<String> words = Arrays.asList("apple", "banana", "avocado", "cherry");
        System.out.println("Map первым символом: " + toMapByFirstChar(words));
    }
}
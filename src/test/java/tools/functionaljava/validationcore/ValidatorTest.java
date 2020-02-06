package tools.functionaljava.validationcore;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class ValidatorTest {


    private Predicate<String> size10 = toCheck -> toCheck.length() > 10;
    private Predicate<String> size20 = toCheck -> toCheck.length() > 20;
    private ValidationRule<String> size = ValidationRule.of(size10.or(size20), "Length bigger than 10", "Length is less than 10");
    private ValidationRule<String> size20Rule = ValidationRule.of(toCheck -> toCheck.length() > 20, "Length bigger than 20", "Length is less than 20");


    @Test
    public void test() {
        List<ValidationResult> results = Stream.of("12345678901", "PATATA")
                .map(data -> new Validator<>(size, ValidatorMode.FAIL_FAST).and(size20Rule).apply(data))
                .collect(Collectors.toList());
        System.out.println();
    }

    @Test
    public void throwException() throws ValidationException {
        assertThrows(ValidationException.class, () -> new Validator<>(size)
                .apply("1")
                .throwException());
    }
}

package tools.functionaljava.validationcore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {

    private static final ValidationRule<String> NUMERIC_STRING = ValidationRule.of(toCheck -> toCheck.matches("[0-9]+"), "Numeric string", "String contains non-numeric characters");
    private static final ValidationRule<String> ALPHABETIC_STRING = ValidationRule.of(toCheck -> toCheck.matches("[a-zA-Z]+"), "Alphabetic string", "String contains non-alphabetic characters");
    private static final ValidationRule<String> LESS_THAN_TEN_CHARS = ValidationRule.of(toCheck -> toCheck.length() < 10, "Less than 10 characters string", "String contains 10 or more characters");

    @Test
    public void applyWithTwoFailingValidationRulesInFailFastMode() {
        Validator<String> stringValidator = Validator.of(NUMERIC_STRING, ValidatorMode.FAIL_FAST).and(ALPHABETIC_STRING);
        ValidationResult validationResult = stringValidator.apply("+-");
        assertEquals(ValidationStatus.FAIL, validationResult.getStatus());
        assertEquals(1, validationResult.getFailList().size());
        assertTrue(validationResult.getSuccessList().isEmpty());
    }

    @Test
    public void applyWithTwoFailingValidationRulesInFailLastMode() {
        Validator<String> stringValidator = Validator.of(NUMERIC_STRING, ValidatorMode.FAIL_LAST).and(ALPHABETIC_STRING);
        ValidationResult validationResult = stringValidator.apply("+-");
        assertEquals(ValidationStatus.FAIL, validationResult.getStatus());
        assertEquals(2, validationResult.getFailList().size());
        assertTrue(validationResult.getSuccessList().isEmpty());
    }

    @Test
    public void applyWithLastFailingValidationRuleInFailFastMode() {
        Validator<String> stringValidator = Validator.of(NUMERIC_STRING, ValidatorMode.FAIL_FAST).and(ALPHABETIC_STRING);
        ValidationResult validationResult = stringValidator.apply("123");
        assertEquals(ValidationStatus.FAIL, validationResult.getStatus());
        assertEquals(1, validationResult.getFailList().size());
        assertEquals(1, validationResult.getSuccessList().size());
    }

    @Test
    public void applyWithLastFailingValidationRulesInFailLastMode() {
        Validator<String> stringValidator = Validator.of(NUMERIC_STRING, ValidatorMode.FAIL_LAST).and(ALPHABETIC_STRING);
        ValidationResult validationResult = stringValidator.apply("123");
        assertEquals(ValidationStatus.FAIL, validationResult.getStatus());
        assertEquals(1, validationResult.getFailList().size());
        assertEquals(1, validationResult.getSuccessList().size());
    }

    @Test
    public void applyWithFirstFailingValidationRuleInFailFastMode() {
        Validator<String> stringValidator = Validator.of(NUMERIC_STRING, ValidatorMode.FAIL_FAST).and(ALPHABETIC_STRING);
        ValidationResult validationResult = stringValidator.apply("abc");
        assertEquals(ValidationStatus.FAIL, validationResult.getStatus());
        assertEquals(1, validationResult.getFailList().size());
        assertTrue(validationResult.getSuccessList().isEmpty());
    }

    @Test
    public void applyWithFirstFailingValidationRulesInFailLastMode() {
        Validator<String> stringValidator = Validator.of(NUMERIC_STRING, ValidatorMode.FAIL_LAST).and(ALPHABETIC_STRING);
        ValidationResult validationResult = stringValidator.apply("abc");
        assertEquals(ValidationStatus.FAIL, validationResult.getStatus());
        assertEquals(1, validationResult.getFailList().size());
        assertEquals(1, validationResult.getSuccessList().size());
    }

    @Test
    public void applyWithTwoPassingValidationRulesInFailFastMode() {
        Validator<String> stringValidator = Validator.of(LESS_THAN_TEN_CHARS, ValidatorMode.FAIL_FAST).and(ALPHABETIC_STRING);
        ValidationResult validationResult = stringValidator.apply("abc");
        assertEquals(ValidationStatus.SUCCESS, validationResult.getStatus());
        assertTrue(validationResult.getFailList().isEmpty());
        assertEquals(2, validationResult.getSuccessList().size());
    }

    @Test
    public void applyWithTwoPassingValidationRulesInFailLastMode() {
        Validator<String> stringValidator = Validator.of(LESS_THAN_TEN_CHARS, ValidatorMode.FAIL_LAST).and(ALPHABETIC_STRING);
        ValidationResult validationResult = stringValidator.apply("abc");
        assertEquals(ValidationStatus.SUCCESS, validationResult.getStatus());
        assertTrue(validationResult.getFailList().isEmpty());
        assertEquals(2, validationResult.getSuccessList().size());
    }

}

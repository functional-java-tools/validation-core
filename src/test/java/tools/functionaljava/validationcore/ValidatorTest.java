package tools.functionaljava.validationcore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorTest {

    private static final ValidationRule<String> NUMERIC_STRING = ValidationRule.of(toCheck -> toCheck.matches("[0-9]+"), "Numeric string", "String contains non-numeric characters");
    private static final ValidationRule<String> ALPHABETIC_STRING = ValidationRule.of(toCheck -> toCheck.matches("[a-zA-Z]+"), "Alphabetic string", "String contains non-alphabetic characters");
    private static final ValidationRule<String> LESS_THAN_TEN_CHARS = ValidationRule.of(toCheck -> toCheck.length() < 10, "Less than 10 characters string", "String contains 10 or more characters");

    @Test
    public void applyWithTwoFailingValidationRulesInFailFastMode() {
        ValidationResult validationResult = getValidator(NUMERIC_STRING, ALPHABETIC_STRING, ValidatorMode.FAIL_FAST).apply("+-");
        assertValidationResult(validationResult, ValidationStatus.FAIL, 0, 1);
    }

    @Test
    public void applyWithTwoFailingValidationRulesInFailLastMode() {
        ValidationResult validationResult = getValidator(NUMERIC_STRING, ALPHABETIC_STRING, ValidatorMode.FAIL_LAST).apply("+-");
        assertValidationResult(validationResult, ValidationStatus.FAIL, 0, 2);
    }

    @Test
    public void applyWithLastFailingValidationRuleInFailFastMode() {
        ValidationResult validationResult = getValidator(NUMERIC_STRING, ALPHABETIC_STRING, ValidatorMode.FAIL_FAST).apply("123");
        assertValidationResult(validationResult, ValidationStatus.FAIL, 1, 1);
    }

    @Test
    public void applyWithLastFailingValidationRulesInFailLastMode() {
        ValidationResult validationResult = getValidator(NUMERIC_STRING, ALPHABETIC_STRING, ValidatorMode.FAIL_LAST).apply("123");
        assertValidationResult(validationResult, ValidationStatus.FAIL, 1, 1);
    }

    @Test
    public void applyWithFirstFailingValidationRuleInFailFastMode() {
        ValidationResult validationResult = getValidator(NUMERIC_STRING, ALPHABETIC_STRING, ValidatorMode.FAIL_FAST).apply("abc");
        assertValidationResult(validationResult, ValidationStatus.FAIL, 0, 1);
    }

    @Test
    public void applyWithFirstFailingValidationRulesInFailLastMode() {
        ValidationResult validationResult = getValidator(NUMERIC_STRING, ALPHABETIC_STRING, ValidatorMode.FAIL_LAST).apply("abc");
        assertValidationResult(validationResult, ValidationStatus.FAIL, 1, 1);
    }

    @Test
    public void applyWithTwoPassingValidationRulesInFailFastMode() {
        ValidationResult validationResult = getValidator(LESS_THAN_TEN_CHARS, ALPHABETIC_STRING, ValidatorMode.FAIL_FAST).apply("abc");
        assertValidationResult(validationResult, ValidationStatus.SUCCESS, 2, 0);
    }

    @Test
    public void applyWithTwoPassingValidationRulesInFailLastMode() {
        ValidationResult validationResult = getValidator(LESS_THAN_TEN_CHARS, ALPHABETIC_STRING, ValidatorMode.FAIL_LAST).apply("abc");
        assertValidationResult(validationResult, ValidationStatus.SUCCESS, 2, 0);
    }

    @Test
    public void getValidatorWithDefaultMode() {
        ValidationResult validationResult = Validator.of(NUMERIC_STRING)
                .and(ALPHABETIC_STRING)
                .apply("abc");
        assertValidationResult(validationResult, ValidationStatus.FAIL, 1, 1);
    }

    private Validator<String> getValidator(ValidationRule<String> firstRule, ValidationRule<String> secondRule, ValidatorMode validatorMode) {
        return Validator.of(firstRule, validatorMode).and(secondRule);
    }

    private void assertValidationResult(ValidationResult validationResult, ValidationStatus validationStatus, int successListSize, int failListSize) {
        assertEquals(validationStatus, validationResult.getStatus());
        assertEquals(successListSize, validationResult.getSuccessList().size());
        assertEquals(failListSize, validationResult.getFailList().size());
    }

}

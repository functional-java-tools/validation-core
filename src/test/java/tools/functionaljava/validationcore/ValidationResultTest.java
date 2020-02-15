package tools.functionaljava.validationcore;

import org.junit.jupiter.api.Test;
import tools.functionaljava.validationcore.ValidationResult.ValidationResulBuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class ValidationResultTest {

    private static final ValidationRuleResult FAILED = ValidationRuleResult.invalid("Dummy rule", "Error");
    private static final ValidationRuleResult SUCCESS = ValidationRuleResult.valid("Dummy rule");

    @Test
    public void validationResultIsFailedAndThrowException() {
        assertThrows(ValidationException.class, () -> new ValidationResulBuilder()
                .withValidationRuleResult(FAILED)
                .build()
                .throwException());
    }

    @Test
    public void validationResultIsSuccessAndDoNotThrowException() {
        try {
            new ValidationResulBuilder()
                    .withValidationRuleResult(SUCCESS)
                    .build()
                    .throwException();
        } catch (ValidationException e) {
            fail("No expected exception");
        }
    }

}

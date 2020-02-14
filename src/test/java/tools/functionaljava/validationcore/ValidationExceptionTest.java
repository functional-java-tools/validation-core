package tools.functionaljava.validationcore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationExceptionTest {

    @Test
    public void createNewValidationExceptionWithMessage() {
        assertThrows(ValidationException.class, () -> {
            throw new ValidationException("Dummy message");
        });
    }

}

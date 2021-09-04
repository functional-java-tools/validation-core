package tools.functionaljava.validationcore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ValidationResult {
    private ValidationStatus status;
    private List<String> successList;
    private List<String> failList;

    private ValidationResult(ValidationStatus status, List<String> successList, List<String> failList) {
        this.status = status;
        this.successList = successList;
        this.failList = failList;
    }

    public ValidationStatus getStatus() {
        return status;
    }

    public List<String> getSuccessList() {
        return Collections.unmodifiableList(this.successList);
    }

    public List<String> getFailList() {
        return Collections.unmodifiableList(failList);
    }

    public void throwException() throws ValidationException {
        Optional.of(status)
                .filter(ValidationStatus.SUCCESS::equals)
                .orElseThrow(() -> new ValidationException(String.join(",", failList)));
    }

    static class ValidationResultBuilder {
        private List<String> successList = new ArrayList<>();
        private List<String> failList = new ArrayList<>();

        ValidationResultBuilder withValidationRuleResult(ValidationRuleResult validationRuleResult) {
            if (validationRuleResult.getValidationStatus() == ValidationStatus.SUCCESS) {
                successList.add(validationRuleResult.getRuleName());
            } else {
                failList.add(validationRuleResult.getErrorMessage());
            }
            return this;
        }

        ValidationResult build() {
            return new ValidationResult(failList.isEmpty() ? ValidationStatus.SUCCESS : ValidationStatus.FAIL, successList, failList);
        }
    }

}

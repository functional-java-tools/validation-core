package tools.functionaljava.validationcore;

class ValidationRuleResult {
    private String ruleName;
    private ValidationStatus validationStatus;
    private String errorMessage;

    private ValidationRuleResult(String ruleName, ValidationStatus validationStatus) {
        this(ruleName, validationStatus, "");
    }

    private ValidationRuleResult(String ruleName, ValidationStatus validationStatus, String errorMessage) {
        this.ruleName = ruleName;
        this.validationStatus = validationStatus;
        this.errorMessage = errorMessage;
    }

    String getRuleName() {
        return ruleName;
    }

    ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    String getErrorMessage() {
        return errorMessage;
    }

    static ValidationRuleResult valid(String ruleName) {
        return new ValidationRuleResult(ruleName, ValidationStatus.SUCCESS);
    }

    static ValidationRuleResult invalid(String ruleName, String errorMessage) {
        return new ValidationRuleResult(ruleName, ValidationStatus.FAIL, errorMessage);
    }
}

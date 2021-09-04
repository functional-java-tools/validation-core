package tools.functionaljava.validationcore;


import tools.functionaljava.validationcore.ValidationResult.ValidationResultBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static tools.functionaljava.validationcore.ValidationStatus.FAIL;
import static tools.functionaljava.validationcore.ValidatorMode.FAIL_FAST;
import static tools.functionaljava.validationcore.ValidatorMode.FAIL_LAST;

public class Validator<T> implements Function<T, ValidationResult> {
    private List<ValidationRule<T>> validationRuleList = new ArrayList<>();
    private ValidatorMode mode;

    private Validator(ValidationRule<T> ruleToValidate, ValidatorMode validatorMode) {
        this.validationRuleList.add(ruleToValidate);
        this.mode = validatorMode;
    }

    public static <T> Validator<T> of(ValidationRule<T> ruleToValidate, ValidatorMode validatorMode) {
        return new Validator<>(ruleToValidate, validatorMode);
    }

    public static <T> Validator<T> of(ValidationRule<T> ruleToValidate) {
        return new Validator<>(ruleToValidate, FAIL_LAST);
    }

    public Validator<T> and(ValidationRule<T> ruleToValidate) {
        validationRuleList.add(ruleToValidate);
        return this;
    }

    @Override
    public ValidationResult apply(T t) {
        ValidationResultBuilder validationResultBuilder = new ValidationResultBuilder();

        for (ValidationRule<T> validationRule : validationRuleList) {
            ValidationRuleResult validationRuleResult = validationRule.apply(t);
            validationResultBuilder.withValidationRuleResult(validationRuleResult);
            if (mode == FAIL_FAST && validationRuleResult.getValidationStatus() == FAIL) {
                break;
            }
        }

        return validationResultBuilder.build();
    }
}

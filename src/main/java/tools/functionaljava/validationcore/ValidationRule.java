package tools.functionaljava.validationcore;

import java.util.function.Function;
import java.util.function.Predicate;

import static tools.functionaljava.validationcore.ValidationRuleResult.invalid;
import static tools.functionaljava.validationcore.ValidationRuleResult.valid;

@FunctionalInterface
public interface ValidationRule<T> extends Function<T, ValidationRuleResult> {

    static <T> ValidationRule<T> of(Predicate<T> predicate, String ruleName, String failMessage) {
        return toCheck -> predicate.test(toCheck) ? valid(ruleName) : invalid(ruleName, failMessage);
    }

}

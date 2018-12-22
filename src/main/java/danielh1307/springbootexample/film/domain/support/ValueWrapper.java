package danielh1307.springbootexample.film.domain.support;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class ValueWrapper {

    private final String value;

    protected ValueWrapper(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        return Objects.equals(this.value, ((ValueWrapper) other).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + this.value + ")";
    }

    public static <V extends Collection<? extends ValueWrapper>> List<String> toValues(V valueWrappers) {
        return valueWrappers.stream()
                .map(ValueWrapper::getValue)
                .collect(toList());
    }

}

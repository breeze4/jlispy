package lispy;

import java.util.List;

@FunctionalInterface
public interface FunctionListArgs<T, R> {
    R apply(List<?> args);
}

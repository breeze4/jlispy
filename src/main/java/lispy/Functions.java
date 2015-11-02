package lispy;

import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class Functions {

    public static final Function LENGTH = o -> {
        if (o instanceof Object[]) {
            return ((Object[]) o).length;
        } else if (o instanceof Collection) {
            return ((Collection) o).size();
        } else {
            throw new IllegalArgumentException("not a valid object to get length of");
        }
    };

    public static final FunctionListArgs MULTIPLY = primitiveOpFactory(
            (runningProduct, arg) -> runningProduct * arg,
            (runningProduct, arg) -> runningProduct * arg);

    public static final FunctionListArgs ADD = primitiveOpFactory(
            (runningProduct, arg) -> runningProduct + arg,
            (runningProduct, arg) -> runningProduct + arg);

    public static final FunctionListArgs SUBTRACT = primitiveOpFactory(
            (runningProduct, arg) -> runningProduct - arg,
            (runningProduct, arg) -> runningProduct - arg);

    public static final FunctionListArgs DIVIDE = primitiveOpFactory(
            (runningProduct, arg) -> runningProduct / arg,
            (runningProduct, arg) -> runningProduct / arg);

    private static FunctionListArgs primitiveOpFactory(BinaryOperator<Long> longOp, BinaryOperator<Float> floatOp) {
        return (args) -> {
            if (args.get(0) instanceof Long) {
                return args.stream()
                        .map(arg -> ((Long) arg))
                        .reduce(longOp).get();
            } else if (args.get(0) instanceof Float) {
                return args.stream()
                        .map(arg -> (Float) arg)
                        .reduce(floatOp).get();
            } else {
                throw new IllegalArgumentException("not a valid object to operate on");
            }
        };
    }
}

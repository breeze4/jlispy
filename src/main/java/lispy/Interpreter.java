package lispy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Interpreter {

    public static Object eval(Object input, Environment env) {
        if (input instanceof String) {
            Object envProc = env.getFn((String) input);

            return envProc;
        } else if (!(input instanceof List)) {
            return input;
        } else {
            List<Object> sExpr = (List<Object>) input;
            Object result = invokeFn(sExpr, env);

            return result;
        }
    }

    public static Object invokeFn(List<Object> sExpr, Environment env) {
        // evaluate the first and the rest of the args
        List<Object> argValues = sExpr.stream()
                .map(arg -> eval(arg, env))
                .collect(Collectors.toList());

        // then evaluate the procedure as applied to the list of arg values

        Object proc = argValues.get(0);
        List<Object> args = argValues.stream().skip(1).collect(Collectors.toList());
        if(proc instanceof FunctionListArgs) {
            Object result = ((FunctionListArgs) proc).apply(args);
            return result;
        }

        return null;
    }

    public static List<Object> parse(String program) {
        Queue<String> tokenQueue = toQueue(program);
        List<Object> parsed = readFromTokens(tokenQueue);

        return parsed;
    }

    public static List<Object> readFromTokens(Queue<String> tokens) {
        if (tokens.size() == 0)
            throw new IllegalArgumentException("Unexpected EOF while reading");

        String token = tokens.poll(); // pop '('
        List<Object> expr = new ArrayList<>();
        while (!")".equals(tokens.peek())) {
            token = tokens.peek();
            if ("(".equals(token)) {
                expr.add(readFromTokens(tokens));
            } else if (")".equals(token)) {
                throw new IllegalArgumentException("Unexpected ')'");
            } else {
                expr.add(atom(tokens.poll()));
            }
        }
        tokens.poll(); // pop ')'
        return expr;
    }

    private static Object atom(String token) {
        // return Longs and Floats boxed, Strings as strings
        // this is how Peter Norvig does it in python, in my defense!
        try {
            Long parsedLong = Long.parseLong(token);
            return parsedLong;
        } catch (NumberFormatException e1) {
            try {
                Float parsedFloat = Float.parseFloat(token);
                return parsedFloat;
            } catch (NumberFormatException e2) {
                return token;
            }
        }
    }

    public static List<String> tokenize(String line) {
        String result = line.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ");
        String[] split = result.split("\\s");
        return Arrays.stream(split)
                .filter(s -> !"".equals(s))
                .map(s -> (String) s)
                .collect(Collectors.toList());
    }

    public static Queue<String> toQueue(String input) {
        List<String> tokens = tokenize(input);
        return tokens.stream().collect(Collectors.toCollection(LinkedList::new));
    }
}

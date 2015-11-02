package lispy;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class InterpreterTest extends AbstractParseTest {

    @Test
    public void testBasicTokenizing() {
        String input = "(begin (define r 10) (* pi (* r r)))";
        List<String> expected = ImmutableList.<String>builder()
                .add("(", "begin", "(", "define", "r", "10", ")",
                        "(", "*", "pi", "(", "*", "r", "r", ")", ")", ")").build();
        // ['(', 'begin', '(', 'define', 'r', '10', ')', '(', '*', 'pi', '(', '*', 'r', 'r', ')', ')', ')']
        List<String> actual = Interpreter.tokenize(input);
        assertNestedListEquals(expected, actual);
    }

    @Test
    public void testBasicSubExpressionParsing() {
        String input = "(define r 10)";
        List<Object> expected = ImmutableList.builder()
                .add("define", "r", 10L).build();
        Object actual = Interpreter.readFromTokens(Interpreter.toQueue(input));
        assertNestedListEquals(expected, (List<Object>) actual);
    }

    @Test
    public void testNestedSubExpressionParsing() {
        String input = "(begin (define r 10))";
        List<Object> subExpr = ImmutableList.builder()
                .add("define", "r", 10L).build();
        List<Object> expected = ImmutableList.builder()
                .add("begin", subExpr).build();
        Object actual = Interpreter.readFromTokens(Interpreter.toQueue(input));
        assertNestedListEquals(expected, (List<Object>) actual);
    }

    @Test
    public void testBasicParsing() {
        String input = "(begin (define r 10) (* pi (* r r)))";
        // ['begin', ['define', 'r', 10], ['*', 'pi', ['*', 'r', 'r']]]
        List<Object> defineClause = ImmutableList.builder()
                .add("define", "r", 10L).build();
        List<Object> rSqr = ImmutableList.builder()
                .add("*", "r", "r").build();
        List<Object> piRSqr = ImmutableList.builder()
                .add("*", "pi", rSqr).build();
        List<Object> expected = ImmutableList.builder()
                .add("begin", defineClause, piRSqr).build();
        List<Object> actual = Interpreter.parse(input);
        assertNestedListEquals(expected, actual);
        System.out.println(Arrays.deepToString(actual.toArray()));
    }
}

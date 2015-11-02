package lispy;

import org.junit.Test;

import java.util.Arrays;

import static lispy.Environment.standardEnv;
import static lispy.Functions.ADD;
import static lispy.Functions.DIVIDE;
import static lispy.Functions.MULTIPLY;
import static lispy.Functions.SUBTRACT;
import static lispy.Interpreter.eval;
import static lispy.Interpreter.parse;
import static org.junit.Assert.assertEquals;

public class EvalTest extends AbstractParseTest {

    @Test
    public void testMultiplyFn() {
        assertEquals(6L, MULTIPLY.apply(Arrays.asList(2L, 3L)));
        assertEquals(6.0F, MULTIPLY.apply(Arrays.asList(2.0F, 3.0F)));
    }

    @Test
    public void testEvalMultiply() {
        assertEquals(6L, eval(parse("(* 2 3)"), standardEnv()));
        assertEquals(24L, eval(parse("(* 2 (* 4 3))"), standardEnv()));
        assertEquals(120L, eval(parse("(* (* 2 5) (* 4 3))"), standardEnv()));
    }

    @Test
    public void testAddFn() {
        assertEquals(5L, ADD.apply(Arrays.asList(2L, 3L)));
        assertEquals(5.5F, ADD.apply(Arrays.asList(2.5F, 3.0F)));
    }

    @Test
    public void testEvalAdd() {
        assertEquals(5L, eval(parse("(+ 2 3)"), standardEnv()));
        assertEquals(9L, eval(parse("(+ 2 (+ 4 3))"), standardEnv()));
        assertEquals(17L, eval(parse("(+ (* 2 5) (+ 4 3))"), standardEnv()));
    }

    @Test
    public void testSubtractFn() {
        assertEquals(-1L, SUBTRACT.apply(Arrays.asList(2L, 3L)));
        assertEquals(1.5F, SUBTRACT.apply(Arrays.asList(4.5F, 3.0F)));
    }

    @Test
    public void testEvalSubtract() {
        assertEquals(-1L, eval(parse("(- 2 3)"), standardEnv()));
        assertEquals(3L, eval(parse("(+ 2 (- 4 3))"), standardEnv()));
        assertEquals(3L, eval(parse("(- (* 2 5) (+ 4 3))"), standardEnv()));
    }

    @Test
    public void testDivideFn() {
        assertEquals(2L, DIVIDE.apply(Arrays.asList(5L, 2L)));
        assertEquals(2.0F, DIVIDE.apply(Arrays.asList(6.5F, 3.25F)));
    }

    @Test
    public void testEvalDivide() {
        assertEquals(2L, eval(parse("(/ 5 2)"), standardEnv()));
        assertEquals(1.5F, eval(parse("(/ 2.0 (/ 4.0 3.0))"), standardEnv()));
        assertEquals(1.4285715F, eval(parse("(/ (* 2.0 5.0) (+ 4.0 3.0))"), standardEnv()));
    }
}

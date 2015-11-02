package lispy;

import org.junit.Assert;

import java.util.List;

public abstract class AbstractParseTest {

    public static void assertNestedListEquals(List<?> expected, List<?> actual) {
        Assert.assertEquals(expected.size(), actual.size());

        for (int i = 0; i < actual.size(); i++) {
            Object exp = expected.get(i);
            Object act = actual.get(i);
            if (exp instanceof List && act instanceof List) {
                assertNestedListEquals((List<Object>) exp, (List<Object>) act);
            } else if (exp instanceof String && act instanceof String) {
                Assert.assertEquals(exp, act);
            } else if (exp instanceof Long && act instanceof Long) {
                Assert.assertEquals(exp, act);
            } else if (exp instanceof Float && act instanceof Float) {
                Assert.assertEquals(exp, act);
            } else {
                Assert.fail();
            }
        }
    }
}

import lispy.Interpreter;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> tokens = Interpreter.tokenize("(begin (define r 10) (* pi (* r r)))");
        System.out.println(tokens);
    }
}

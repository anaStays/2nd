import org.junit.jupiter.api.Test;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void checkBraces() {
        Calculations calc=new Calculations();
        String actual = StringProcessor.check("(2+2)+(3+1+(2-(1");
        String expected = "Incorrect amount of braces\n";

        assertEquals(actual,expected);
    }

    @Test
    void checkOK() {
        Calculations calc=new Calculations();
        String actual = StringProcessor.check("(2+2)+(3+1)+(2)-(1)");
        String expected = "OK";

        assertEquals(actual,expected);
    }

    @Test
    void calculationNoVars() {
        Calculations calc = new Calculations();
        Scanner scan = new Scanner("");
        float actual = calc.calculation("(2+2)^2+4.5/0.9*2-1",scan);
        float expected = 25.0f;

        assertEquals(actual,expected);
    }

    @Test
    void calculationWithVars() {
        Calculations calc = new Calculations();
        Scanner scan = new Scanner("1,5");
        float actual = calc.calculation("x^2+ x-3",scan);
        float expected = 0.75f;

        assertEquals(actual,expected);
    }

}
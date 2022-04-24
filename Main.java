
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String pattern = "^(?!\\*)((\\+|-)?(?!$|\\+|-|=|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+={1}((\\+|-)?(?!$|\\+|-|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|$))+$";

    static void reducedForm(String input) {
        String[] arr = input.split("=");
        String leftHandSide = arr[0];
        String rightHandSide = arr[1];
        rightHandSide = "8 * X^0 - 6 * X^1 + 0 * X^2 - 5.6 * X^3";
        if (rightHandSide.matches("^\\d.+")) {
            rightHandSide = ("+").concat(rightHandSide);
        }
        // System.out.println(rightHandSide);
        for (int i = 0; i < rightHandSide.length(); i++) {
            if (rightHandSide.charAt(i) == '+') {
                rightHandSide = rightHandSide.substring(0, i) + "#-" + rightHandSide.substring(i + 1);
                i++;
            } else if (rightHandSide.charAt(i) == '-') {
                rightHandSide = rightHandSide.substring(0, i) + "#+" + rightHandSide.substring(i + 1);
                i++;

            }
        }
        System.out.println(rightHandSide);

        String tab[] = rightHandSide.split("#");
        for (String s : tab)
            System.out.println(s);
    }

    public static void main(String[] args) {
        try {

            if ((args[0].matches(pattern)) == false)
                throw new ComputorV1Exception("Equation BAD FORMAT\n");
            reducedForm(args[0]);


//             // Pattern p = Pattern.compile("^(?!\\*)((\\+|-)?(?!$|\\+|-|=|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+");
//             Pattern p = Pattern.compile("^((\\+|-)?((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+");
            
// // System.out.println(("+8.12*X^0-6*X^1+0*X^2-5.6*X^3+12+12*X^10").matches("^((\\+|-)?((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+"));
//             // Matcher m = p.matcher("+8.12*X^0-6*X^1+0*X^2-5.6*X^3+12+12*X^10");
//             Matcher m = p.matcher("+8*X^2+2*X^3");
//             if (m.matches()) {
//                 // System.out.println("whole " + m.group(0));
//                 for (int i = 0; i < m.groupCount(); i++)
//                     System.out.println("first " + m.group(i));
//             //     // System.out.println("second " + m.group(2));
//             }
//             // while (m.find()) {
//             //     // Get the group matched using group() method
//             //     System.out.println(m.group());
//             // }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class PolynomialEquation {
    float coefficient;
    char variable;
    int degree; // exponent
    // int constant;

    PolynomialEquation(float cf, char v, int d)
    {
        this.coefficient = cf;
        this.variable = v;
        this.degree = d;
    }

}

// ^((\+|-)?((\d+(\.\d+)?)?(\*(?=[A-z]))?([A-z](\^\d+)?)?)(?=\+|-|=|$))+
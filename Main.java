
import java.util.*;

public class Main {
    private static final String pattern = "^(?!\\*)((\\+|-)?(?!$|\\+|-|=|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+={1}((\\+|-)?(?!$|\\+|-|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|$))+$";
    private static Integer maxDegree;
    public static ArrayList<PolynomialEquation> Polynomials = new ArrayList<PolynomialEquation>();

    static String reducedForm(String input, char letter) {

        for (int i = 0; i < input.length(); i++) {
            if ((i == 0 && input.charAt(i) == letter) || (i > 0 && input.charAt(i) == letter && input.charAt(i - 1) != '*')) {
                input = input.substring(0, i) + ("1*").concat(Character.toString(letter)) + input.substring(i + 1);
                i += 2;
            }
            if ((i == input.length() - 1 && input.charAt(i) == letter) || (i + 1 != input.length() && input.charAt(i) == letter && input.charAt(i + 1) != '^')) {
                input = input.substring(0, i) + Character.toString(letter).concat("^1") + input.substring(i + 1);
                i += 2;
            }
        }
        System.out.println("input = " + input);
// System.exit(0);
        String[] arr = input.split("=");
        String leftHandSide = arr[0];
        String rightHandSide = arr[1];
        // rightHandSide = "8.123*X^109991-6*X^4+11*X^2-5.6*X^3+33-15-8*X^2";
        // leftHandSide = "2*X^5-6*X^4+11*X^2-5.6*X^3+22-15-18*X^2";

        if (Character.isDigit(rightHandSide.charAt(0)))
            rightHandSide = ("+").concat(rightHandSide);

        // if (rightHandSide.matches("^\\d+"))
        // rightHandSide = ("+").concat(rightHandSide);

        if (Character.isDigit(leftHandSide.charAt(0)))
            leftHandSide = ("+").concat(leftHandSide);
        System.out.println("rightHandSide = " + rightHandSide);
        for (int i = 0; i < rightHandSide.length(); i++) {
            if (rightHandSide.charAt(i) == '+') {
                rightHandSide = rightHandSide.substring(0, i) + "#-" + rightHandSide.substring(i + 1);
                i++;
            } else if (rightHandSide.charAt(i) == '-') {
                rightHandSide = rightHandSide.substring(0, i) + "#+" + rightHandSide.substring(i + 1);
                i++;
            }
        }
        leftHandSide = leftHandSide.replace("+", "#+");
        leftHandSide = leftHandSide.replace("-", "#-");

        String fullEquation = leftHandSide.concat(rightHandSide);
        System.out.println("newRightHandSide = " + rightHandSide);
        System.out.println("full = " + fullEquation);

        String[] splitString = fullEquation.split("#");

        System.out.println("len = " + splitString.length);

        float sumNumbers = 0;

        for (String str : splitString) {
            System.out.println("===== " + str + " ======");
            if (str.indexOf(letter) == -1) {
                if (!str.isEmpty())
                    sumNumbers += Float.parseFloat(str);
            } else {
                int endIndex = (str.indexOf('*') != -1) ? str.indexOf('*') : str.indexOf(letter);
                PolynomialEquation p = new PolynomialEquation(
                        Float.parseFloat(str.substring(0, endIndex)),
                        Integer.parseInt(str.substring(str.indexOf('^') + 1)), letter);

                Polynomials.add(p);
            }
        }

        System.out.println("Summmmm " + sumNumbers);

        System.out.println("****** sorted ****");

        // Polynomials.forEach(p -> System.out.println(p));
        Collections.sort(Polynomials, new Comparator<PolynomialEquation>() {
            @Override
            public int compare(PolynomialEquation p1, PolynomialEquation p2) {
                return Integer.compare(p1.getDegree(), p2.getDegree());
            }
        });

        Polynomials.forEach(p -> System.out.println(p));

        System.out.println("****** Sum ****");

        for (int i = 0; i < Polynomials.size() - 1; i++) {
            if (Polynomials.get(i).getDegree() == Polynomials.get(i + 1).getDegree()) {
                float sumCoef = Polynomials.get(i).getCoefficient() + Polynomials.get(i + 1).getCoefficient();
                PolynomialEquation pl = new PolynomialEquation(sumCoef, Polynomials.get(i).getDegree(), letter);
                Polynomials.set(i + 1, pl);
                Polynomials.remove(i);
                i--;
            }
        }

        Polynomials.forEach(p -> System.out.println(p));
        System.out.println("****** Concat ****");

        StringBuilder reduced = new StringBuilder();

        Polynomials.forEach(p -> {
            reduced.append(p.toString());
        });

        maxDegree = Integer.parseInt(reduced.substring(reduced.lastIndexOf("^") + 1));

        if (sumNumbers >= 0)
            reduced.append("+" + Float.toString(sumNumbers) + " = 0");
        else
            reduced.append(Float.toString(sumNumbers) + " = 0");

        System.out.println("Reduced form: " + reduced);
        System.out.println("Polynomial degree: " + maxDegree);
        // String reducedEqu = reduced.toString().replace(".0", "");

        Float A;
        if (reduced.indexOf("^2") != -1) {
            if (Polynomials.size() == 2)
                A = Polynomials.get(1).getCoefficient();
            else
                A = Polynomials.get(0).getCoefficient();
        } else
            A = 0.0f;

        Float B = (reduced.indexOf("^1") != -1) ? Polynomials.get(0).getCoefficient() : 0.0f;

        System.out.println("A = " + A);
        System.out.println("B = " + B);
        System.out.println("sumNumbers = " + sumNumbers);

        Double delta = (double) (B * B - 4 * A * sumNumbers);

        System.out.println("delta = " + delta);
        // ax2 + bx + c ==> b*b – 4ac
        // bx + ax^2 + c

        Float res;
        if (reduced.indexOf("^2") == -1) {
            if (sumNumbers == 0)
                res = 0.0f;
            else {
                res = (sumNumbers * -1) / Polynomials.get(0).getCoefficient();
            }
            System.out.println("The solution is: \n" + res);
        }

        else {
            if (delta > 0) {
                System.out.println("Discriminant is strictly positive, the two solutions are: ");

                Double x1 = (-B + Math.sqrt(delta)) / (2 * A);
                Double x2 = (-B - Math.sqrt(delta)) / (2 * A);

                System.out.println("x1 = " + x1);
                System.out.println("x2 = " + x2);
            }

            if (delta == 0) {
                System.out.println("Discriminant is Equal to Zero: ");

            }
            
            if (delta < 0) {
                System.out.println("Discriminant is strictly Negative, the two solutions are: ");
                System.out.println("the quadratic equation has two different complex roots : ");

            }
        }

        return " ";
    }

    public static void main(String[] args) {
        try {

            if ((args[0].matches(pattern)) == false)
                throw new ComputorV1Exception("Equation Bad Syntaxe FORMAT\n");
            char letter = 'X';
            for (int i = 0; i < args[0].length(); i++) {
                if (Character.isAlphabetic(args[0].charAt(i))) {
                    letter = args[0].charAt(i);
                    break;
                }
            }
            final char tempLetter = letter;
            long countVariables = args[0].chars().filter(ch -> ch == tempLetter).count();
            long countAlphas = args[0].chars().filter(ch -> Character.isLetter(ch)).count();

            System.out.println("countVariables = " + countVariables);
            System.out.println("countAlphas = " + countAlphas);

            if (countVariables != countAlphas)
                throw new ComputorV1Exception("Equation BAD FORMAT: There Are Multiple Diffirent Variables\n");

            reducedForm(args[0], letter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class PolynomialEquation {
    private float coefficient;
    char variable;
    private int degree;

    PolynomialEquation(float cf, int d, char v) {
        this.coefficient = cf;
        this.variable = v;
        this.degree = d;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public int getDegree() {
        return degree;
    }

    @Override
    public String toString() {
        String coef;
        if (coefficient >= 0)
            coef = "+" + Float.toString(this.coefficient);
        else
            coef = Float.toString(this.coefficient);
        return coef + "*" + Character.toString(variable) + "^" + this.degree;
    }
}
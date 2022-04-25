
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String pattern = "^(?!\\*)((\\+|-)?(?!$|\\+|-|=|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+={1}((\\+|-)?(?!$|\\+|-|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|$))+$";
    private static Integer maxDegree;
    public static ArrayList<PolynomialEquation> Polynomials = new ArrayList<PolynomialEquation>();

    static String reducedForm(String input) {

        String[] arr = input.split("=");
        String leftHandSide = arr[0];
        String rightHandSide = arr[1];
        // rightHandSide = "8.123*X^109991-6*X^4+11*X^2-5.6*X^3+33-15-8*X^2";
        // leftHandSide = "2*X^5-6*X^4+11*X^2-5.6*X^3+22-15-18*X^2";

        if (rightHandSide.matches("^\\d.+"))
            rightHandSide = ("+").concat(rightHandSide);
        if (leftHandSide.matches("^\\d.+"))
            leftHandSide = ("+").concat(leftHandSide);
        System.out.println(rightHandSide);
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
        System.out.println(rightHandSide);
        System.out.println("full = " + fullEquation);

        String[] splitString = fullEquation.split("#");

        System.out.println("len = " + splitString.length);

        float sumNumbers = 0;

        for (String str : splitString) {
            System.out.println("===== " + str + " ======");
            if (str.indexOf('*') == -1) {
                if (!str.isEmpty())
                    sumNumbers += Float.parseFloat(str);
            } else {
                PolynomialEquation p = new PolynomialEquation(
                        Float.parseFloat(str.substring(0, str.indexOf('*'))),
                        Integer.parseInt(str.substring(str.indexOf('^') + 1)));

                Polynomials.add(p);
            }
        }

        System.out.println("Summmmm " + sumNumbers);
        Polynomials.forEach(p -> System.out.println(p));
        Collections.sort(Polynomials, new Comparator<PolynomialEquation>() {
            @Override
            public int compare(PolynomialEquation p1, PolynomialEquation p2) {
                return Integer.compare(p1.getDegree(), p2.getDegree());
            }
        });

        System.out.println("****** sorted ****");

        Polynomials.forEach(p -> System.out.println(p));

        System.out.println("****** Sum ****");

        for (int i = 0; i < Polynomials.size() - 1; i++) {
            if (Polynomials.get(i).getDegree() == Polynomials.get(i + 1).getDegree()) {
                float sumCoef = Polynomials.get(i).getCoefficient() + Polynomials.get(i + 1).getCoefficient();
                PolynomialEquation pl = new PolynomialEquation(sumCoef, Polynomials.get(i).getDegree());
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

        // System.out.println(reducedEqu);

        Double delta = Polynomials.get(0).getCoefficient() * sumNumbers  * -4 + Math.sqrt(Polynomials.get(1).getCoefficient());

        System.out.println("delta = " + delta);
// ax2 + bx + c  ==> b2 – 4ac
// bx + ax^2 + c
        return " ";
    }

    public static void main(String[] args) {
        try {

            if ((args[0].matches(pattern)) == false)
                throw new ComputorV1Exception("Equation BAD FORMAT\n");
            reducedForm(args[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class PolynomialEquation {
    private float coefficient;
    // char variable;
    private int degree; // exponent
    // int constant;

    PolynomialEquation(float cf, int d) {
        this.coefficient = cf;
        // this.variable = v;
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

        return coef + "*X^" + this.degree;
    }
}
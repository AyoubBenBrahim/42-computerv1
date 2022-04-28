
import java.util.*;
import noName.*;

public class Main {
    private static final String pattern = "^(?!\\*)((\\+|-)?(?!$|\\+|-|=|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+={1}((\\+|-)?(?!$|\\+|-|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|$))+$";
    private static Integer maxDegree;
    public static ArrayList<PolynomialEquation> Polynomials = new ArrayList<PolynomialEquation>();

    static String reducedForm(String input, char letter) {

// X^2

// 4X^2
// -X^2

// 2*X

        if (input.charAt(0) == letter)
            input = ("1*").concat(Character.toString(letter)) + input.substring(1);
        
        
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == letter && input.charAt(i - 1) != '*') {
                if (Character.isDigit(input.charAt(i - 1))) {
                    input = input.substring(0, i) + ("*").concat(Character.toString(letter)) + input.substring(i + 1);
                    i++;
                } else {
                    input = input.substring(0, i) + ("1*").concat(Character.toString(letter)) + input.substring(i + 1);
                    i += 2;
                }
            }
            if ((input.charAt(i) == letter && i == input.length() - 1)
                    || (input.charAt(i) == letter && i + 1 != input.length() &&  input.charAt(i + 1) != '^')) {
                input = input.substring(0, i) + Character.toString(letter).concat("^1") + input.substring(i + 1);
                i += 2;
            }
        }
        // System.out.println("input = " + input);
        String[] arr = input.split("=");
        String leftHandSide = arr[0];
        String rightHandSide = arr[1];
        // rightHandSide = "8.123*X^109991-6*X^4+11*X^2-5.6*X^3+33-15-8*X^2";
        // leftHandSide = "2*X^5-6*X^4+11*X^2-5.6*X^3+22-15-18*X^2";

        if (Character.isDigit(rightHandSide.charAt(0)))
            rightHandSide = ("+").concat(rightHandSide);

        if (Character.isDigit(leftHandSide.charAt(0)))
            leftHandSide = ("+").concat(leftHandSide);

        System.out.println("\nrightHandSide = " + rightHandSide);
        System.out.println("leftHandSide  = " + leftHandSide + "\n");
    
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
        // System.out.println("newRightHandSide = " + rightHandSide);
        // System.out.println("full = " + fullEquation);

        String[] splitString = fullEquation.split("#");

        // System.out.println("len = " + splitString.length);

        float sumNumbers = 0;

        System.out.println("****** Extracting Terms ****");

        for (String str : splitString) {
            System.out.println(str );
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

        // System.out.println("Summmmm " + sumNumbers);

        System.out.println("\n****** Sort Terms By Degree ****\n");

        // Polynomials.forEach(p -> System.out.println(p));
        Collections.sort(Polynomials, new Comparator<PolynomialEquation>() {
            @Override
            public int compare(PolynomialEquation p1, PolynomialEquation p2) {
                return Integer.compare(p1.getDegree(), p2.getDegree());
            }
        });

        Polynomials.forEach(p -> System.out.println(p));

        System.out.println("\n****** Sum By Same Degree ****\n");

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
        System.out.println("\n****** Concat Terms ****\n");

        StringBuilder reduced = new StringBuilder();

        Polynomials.forEach(p -> {
            reduced.append(p.toString());
        });

                System.out.println("Reduced formmmmm: " + reduced);

        System.out.println("educed.substring(reduced.lastIndexOf(^)" + reduced.substring(reduced.lastIndexOf("^")).toString());
        // System.out.println("educed.substring(reduced.lastIndexOf(^) + 1" + reduced.substring(reduced.lastIndexOf("^") + 1).toString());

        maxDegree = Integer.parseInt(reduced.substring(reduced.lastIndexOf("^") + 1));

        if (sumNumbers > 0)
            reduced.append("+" + (detectType.isInteger(sumNumbers) ? Integer.toString((int) sumNumbers)
                    : Float.toString(sumNumbers) + " = 0"));
        else if (sumNumbers == 0)
            reduced.append(" = 0");
        else
            reduced.append((detectType.isInteger(sumNumbers) ? Integer.toString((int) sumNumbers)
                    : Float.toString(sumNumbers) + " = 0"));

        // System.out.println("Reduced form: " + reduced);
        if (reduced.charAt(0) == '+')
            reduced.replace(0, 1, "");
        
        System.out.print("Reduced form: ");
        reduced.toString().chars().forEach(
                c -> {
                    if (c == '+' || c == '-' || c == '*')
                        System.out.print(" " + Character.toString((char) c) + " ");
                    else
                        System.out.print(Character.toString((char) c));
                });
        System.out.print("\n");

        System.out.println("Polynomial degree: " + maxDegree + "\n");
       
        if (maxDegree > 2) {
            System.err.println("The polynomial degree is strictly greater than 2, I can't solve.");
            System.exit(1);
        }
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
        System.out.println("C = " + sumNumbers);

        System.out.println("    Where: Ax^2 + Bx + C = 0\n");
        
        Double delta = (double) (B * B - 4 * A * sumNumbers);
        
        System.out.println("delta = B^2 - 4AC = " + delta + "\n");


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
                
                System.out.println("x1 = -B + sqrt(Δ) / 2A = " + x1);
                System.out.println("x2 = -B - sqrt(Δ) / 2A = " + x2);
            }

            if (delta == 0) {
                System.out.println("Discriminant is Equal to Zero ");
                System.out.println("the quadratic equation has two equal real roots: ");
                System.out.println("x1 = x2 = -B / 2A");

                System.out.println("x1 = x2 = " + (-B / (2 * A)));
            }

            if (delta < 0) {
                System.out.println("Discriminant is strictly Negative ");
                System.out.println("the quadratic equation has two different complex roots : ");

                System.out.println(
                        "x1 = (-B + β) / (2 * A) = (" + (-B.intValue() + " + " + "β") + ") / " + (2 * A.intValue()));
                System.out.println(
                        "x2 = (-B - β) / (2 * A) = (" + (-B.intValue() + " - " + "β") + ") / " + (2 * A.intValue()));
                System.out.println("where β^2 = Delta");
                System.out.println("Δ = " + delta.intValue() + " = " + (-delta.intValue()) + "i²");
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
            coef = "+" + (detectType.isInteger(coefficient) ? Integer.toString((int) this.coefficient)
                    : Float.toString(this.coefficient));
        else
            coef = Float.toString(this.coefficient);
        if (getCoefficient() == 0)
            return "";
        return coef + "*" + Character.toString(variable) + "^" + this.degree;
    }
}
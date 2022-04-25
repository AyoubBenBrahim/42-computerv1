
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String pattern = "^(?!\\*)((\\+|-)?(?!$|\\+|-|=|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+={1}((\\+|-)?(?!$|\\+|-|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|$))+$";

    static void reducedForm(String input) {
        ArrayList<PolynomialEquation> Polynomials = new ArrayList<PolynomialEquation>();

        String[] arr = input.split("=");
        String leftHandSide = arr[0];
        String rightHandSide = arr[1];
        rightHandSide = "8.2*X^10-6*X^4+11*X^2-5.6*X^3+2222222-15-8*X^2";
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

        // String tab[] = rightHandSide.split("#");
        // for (String s : tab)
        // System.out.println(s);

        // String[] splitString = rightHandSide.split("\\*|[A-z]|\\#");
        // String[] splitString = rightHandSide.split("\\*|[A-z]|\\^");
        String[] splitString = rightHandSide.split("#");

        // for (String s : splitString)
        // System.out.println(s);

        System.out.println("len = " + splitString.length);

        float sum = 0;
        for (String str : splitString) {
            // for (int i = 0; i < str.length(); i++) {
            // if (rightHandSide.charAt(i) == '+')
            System.out.println("===== " + str + " ======");
            if (str.indexOf('*') == -1) {
                if (!str.isEmpty())
                    sum += Float.parseFloat(str);
            } else {
                // System.out.println("indexOf('*') = " + str.indexOf('*'));
                // System.out.println("substring(0, str.indexOf('*') = " + str.substring(0,
                // str.indexOf('*')));
                // System.out.println("indexOf('^') = " + str.indexOf('^'));
                // System.out.println("substring(str.indexOf('^') = " +
                // str.substring(str.indexOf('^') + 1));

                // System.out.println("coef = " + Float.parseFloat(str.substring(0,
                // str.indexOf('*'))));
                // System.out.println("ouus = " +
                // Integer.parseInt(str.substring(str.indexOf('^') + 1)));

                PolynomialEquation p = new PolynomialEquation(
                        Float.parseFloat(str.substring(0, str.indexOf('*'))),
                        Integer.parseInt(str.substring(str.indexOf('^') + 1)));

                Polynomials.add(p);
            }
        }

        System.out.println(sum);
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

        // for (PolynomialEquation p : Polynomials) {

        // }

        ListIterator<PolynomialEquation> iterator = Polynomials.listIterator();

        while (iterator.hasNext()) {
            // if (Integer.compare(iterator.next().getDegree(), iterator.)
            System.out.println(iterator.next());
        }

        ArrayList<Integer> numbs = new ArrayList<Integer>();
        numbs.add(1);
        numbs.add(1);
        numbs.add(1);
        numbs.add(2);
        numbs.add(2);
        numbs.forEach(p -> System.out.println(p));
        System.out.println("*********");
        // Get the iterator
        // Iterator<String> it = numbs.iterator();

        // while (it.hasNext())
        // System.out.println((it.next()));

        for (int i = 0; i < numbs.size() - 1; i++) {
            // System.out.println(numbs.get(i) + " next " + numbs.get(i + 1));

            if (numbs.get(i) == numbs.get(i+1) || (i >= 1 && numbs.get(i) == numbs.get(i - 1)))
            {
                int somme = numbs.get(i) + numbs.get(i+1);
                numbs.set(i+1, somme * 10000);
                numbs.remove(i);
                // i--;
                // numbs.add(i, somme * 10000);
                // numbs.remove(i + 1);
            }
            // if (i == 2) {
            //     numbs.remove(i);
            //     numbs.add(i, i * 100);
            // }

        }

        // numbs.add(0, 200);
        numbs.forEach(p -> System.out.println(p));

    }

    public static void main(String[] args) {
        try {

            if ((args[0].matches(pattern)) == false)
                throw new ComputorV1Exception("Equation BAD FORMAT\n");
            reducedForm(args[0]);

            // // Pattern p =
            // Pattern.compile("^(?!\\*)((\\+|-)?(?!$|\\+|-|=|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+");
            // Pattern p =
            // Pattern.compile("^((\\+|-)?((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+");

            // //
            // System.out.println(("+8.12*X^0-6*X^1+0*X^2-5.6*X^3+12+12*X^10").matches("^((\\+|-)?((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+"));
            // // Matcher m = p.matcher("+8.12*X^0-6*X^1+0*X^2-5.6*X^3+12+12*X^10");
            // Matcher m = p.matcher("+8*X^2+2*X^3");
            // if (m.matches()) {
            // // System.out.println("whole " + m.group(0));
            // for (int i = 0; i < m.groupCount(); i++)
            // System.out.println("first " + m.group(i));
            // // // System.out.println("second " + m.group(2));
            // }
            // // while (m.find()) {
            // // // Get the group matched using group() method
            // // System.out.println(m.group());
            // // }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class PolynomialEquation {
    float coefficient;
    // char variable;
    int degree; // exponent
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
        return this.coefficient + " ^ " + this.degree;
    }

}
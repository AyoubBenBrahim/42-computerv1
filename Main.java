import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static final String pattern = "^(?!\\*)((\\+|-)?(?!$|\\+|-|=|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|=|$))+={1}((\\+|-)?(?!$|\\+|-|\\*)((\\d+(\\.\\d+)?)?(\\*(?=[A-z]))?([A-z](\\^\\d+)?)?)(?=\\+|-|$))+$";

    static void myMethod() {
        System.out.println("I just got executed!");
    }

    public static void main(String[] args) {
        try {
            // System.out.println("input is " + args[0]);

            if ((args[0].matches(pattern)) == false)
                throw new ComputorV1Exception("Equation BAD FORMAT\n");
            System.out.println("Congrats");

        } catch (ComputorV1Exception e) {
            e.printStackTrace();
        }
    }
}

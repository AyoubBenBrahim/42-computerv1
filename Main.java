import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static int xxxxxxxx;

    public static void main(String[] args) {
        try {
            String type;
            int height;

            if (args.length != 1) {
                throw new MyCustomException("Invalid Argument:\n\t./computor 8 * X^0 - 6 * X^1 + 0 * X^2 - 5.6 * X^3 = 3 * X^0");
            }

            System.out.println(args.length);

            if (Arrays.toString(args).isEmpty())
            System.out.println("emptyyy");
      
            System.out.println("input is " + Arrays.toString(args));

            // if ((pattern.matches("^[A-Z][A-z0-9]+$")) == false)
            //     throw new MyCustomException("AireCraft Name Starts With UpperCase Alpha\n");

        } catch (MyCustomException e) {
            e.printStackTrace();
        }
    }
}

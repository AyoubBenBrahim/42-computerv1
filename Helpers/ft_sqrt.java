package Helpers;

public class ft_sqrt {
    public static double sqrt(double X) {
        for (int i = 1; i < X; ++i) {
            int p = i * i;
            if (p == X) {
                // perfect square
                return i;
            }
            if (p > X) {
                // found left part of decimal
                return sqrt(X, i - 1, i);
            }
        }
        return Double.NaN;
    }
    
    
    private static double sqrt(double X, double low, double high) {
        double mid = (low + high) / 2;
        double p = mid * mid;
     
         //System.out.println(low + " " + high + " " + mid + " " + p);
    
        if (p == X || Double.toString(p).length() >= 16 ) {
            return mid;
        }
        if (p < X) {
            return sqrt(X, mid, high);
        }
        return sqrt(X, low, mid);
    }
}

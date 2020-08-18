package heniko.pathfinding.util;

/**
 *
 * @author Niko Hernesniemi
 */
public class Mathematics {

    /**
     * Returns the bigger number.
     *
     * @param a First input number.
     * @param b Second input number.
     * @return Bigger of the two.
     */
    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    /**
     * Returns the bigger number.
     *
     * @param a First input number.
     * @param b Second input number.
     * @return Bigger of the two.
     */
    public static double max(double a, double b) {
        return a > b ? a : b;
    }

    /**
     * Returns the smaller number.
     *
     * @param a First input number.
     * @param b Second input number.
     * @return Smaller of the two.
     */
    public static int min(int a, int b) {
        return a < b ? a : b;
    }

    /**
     * Returns the smaller number.
     *
     * @param a First input number.
     * @param b Second input number.
     * @return Smaller of the two.
     */
    public static double min(double a, double b) {
        return a < b ? a : b;
    }

    /**
     * Returns the absolute value of input.
     *
     * @param i Input number.
     * @return Absolute value.
     */
    public static double abs(double i) {
        return i < 0 ? -i : i;
    }

    /**
     * Returns the absolute value of input.
     *
     * @param i Input number.
     * @return Absolute value.
     */
    public static int abs(int i) {
        return i < 0 ? -i : i;
    }

    /**
     * Returns approximation of the square root of input. This implementation
     * should be a lot slower than Math.sqrt().
     *
     * @param i Input number.
     * @return Square root approximation.
     */
    public static double sqrt(double i) throws IllegalArgumentException {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        double lower = 0;
        double higher = Mathematics.max(i, 1);
        double middle = (lower + higher) / 2;
        double approximation = middle * middle;
        // While error is over 0.0000001
        while (Mathematics.abs(approximation - i) > 0.0000001) {
            if (approximation < i) {
                lower = (lower + middle) / 2;
            } else if (approximation > i) {
                higher = (higher + middle) / 2;
            }
            middle = (lower + higher) / 2;
            approximation = middle * middle;
        }
        return middle;
    }
}

package algorithms;

/**
 * Diagonal distance heuristic.
 *
 * @author Niko Hernesniemi
 */
public class DiagonalHeuristic implements Heuristic {

    private double SQRT2 = Math.sqrt(2);

    /**
     * Constructor for DiagonalHeuristic
     */
    public DiagonalHeuristic() {
    }

    /**
     * Calculates diagonal distance between source and destination points.
     *
     * @param sx Source x-coordinate
     * @param sy Source y-coordinate
     * @param ex Destination x-coordinate
     * @param ey Destination y-coordinate
     * @return Diagonal distance
     */
    @Override
    public double getHValue(int sx, int sy, int ex, int ey) {
        // Based on:
        // http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#diagonal-distance
        double dx = Math.abs(sx - ex);
        double dy = Math.abs(sy - ey);
        return (dx + dy) + (SQRT2 - 2) * Math.min(dx, dy);
    }

}

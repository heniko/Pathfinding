package algorithms;

/**
 * Interface for different heuristics.
 *
 * @author Niko Hernesniemi
 */
public interface Heuristic {

    /**
     * Returns heuristic distance from source (sx, sy) to destination (ex, ey).
     *
     * @param sx Source x-coordinate
     * @param sy Source y-coordinate
     * @param ex Destination x-coordinate
     * @param ey Destination y-coordinate
     * @return Heuristic value calculated
     */
    double getHValue(int sx, int sy, int ex, int ey);
}

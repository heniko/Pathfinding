package heniko.pathfinding.domain;

import heniko.pathfinding.util.Mathematics;

/**
 * Euclidean distance heuristic.
 *
 * @author Niko Hernesniemi
 */
public class EuclideanHeuristic implements Heuristic {

    /**
     * Constructor for EuclideanHeuristic.
     */
    public EuclideanHeuristic() {
    }

    /**
     * Calculates Euclidean distance between source and destination coordinates.
     *
     * @param sx Source x-coordinate
     * @param sy Source y-coordinate
     * @param ex Destination x-coordinate
     * @param ey Destination y-coordinate
     * @return Euclidean distance
     */
    @Override
    public double getHValue(int sx, int sy, int ex, int ey) {
        // Based on:
        // http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#euclidean-distance
        double dx = Mathematics.abs(sx - ex);
        double dy = Mathematics.abs(sy - ey);
        return Math.sqrt(dx * dx + dy * dy);
    }

}

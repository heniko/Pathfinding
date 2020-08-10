package heniko.pathfinding.domain;

/**
 * Djikstra heuristic for A*.
 *
 * @author Niko Hernesniemi
 */
public class DjikstraHeuristic implements Heuristic {

    /**
     * Constructor for DjikstraHeuristic.
     */
    public DjikstraHeuristic() {
    }

    /**
     * Always returns 0.
     *
     * @param sx Source x-coordinate
     * @param sy Source y-coordinate
     * @param ex Destination x-coordinate
     * @param ey Destination y-coordinate
     * @return Always 0
     */
    @Override
    public double getHValue(int sx, int sy, int ex, int ey) {
        return 0;
    }

}

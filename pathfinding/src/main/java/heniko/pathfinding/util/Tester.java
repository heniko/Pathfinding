package heniko.pathfinding.util;

import heniko.pathfinding.domain.AStar;
import heniko.pathfinding.domain.DiagonalHeuristic;
import heniko.pathfinding.domain.DjikstraHeuristic;
import heniko.pathfinding.domain.EuclideanHeuristic;
import heniko.pathfinding.domain.JPS;
import heniko.pathfinding.domain.Pathfinder;
import heniko.pathfinding.io.MapReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Performance tester for algorithms and data structures.
 *
 * @author Niko Hernesniemi
 */
public class Tester {

    private static final long NANO_TO_MILLI = 1000000;
    private final ArrayList<String> lines;

    /**
     * Runs all performance tests and prints results.
     */
    public Tester() {
        lines = new ArrayList<>();
    }

    public void test() {
        //testMap(new File("./data/Berlin_0_1024.map"), "Berlin_0_1024");
        lines.add("");
        testMap(new File("./data/16room_001.map"), "16room_001");
        lines.add("");
        testMap(new File("./data/64room_009.map"), "64room_009");
        lines.add("");
        //testAndPrintSqrt();

        for (String line : lines) {
            System.out.println(line);
        }
    }

    /*
    Useful methods for all tests
     */
    private double getStd(long[] times, double mean) {
        double s = 0;
        for (long time : times) {
            s += Math.pow(time - mean, 2);
        }
        return Math.sqrt(s / (times.length - 1));
    }

    private double getAverage(long[] times) {
        double s = 0;
        for (long time : times) {
            s += time;
        }
        return s / times.length;
    }

    private long getMin(long[] times) {
        long min = times[0];
        for (long time : times) {
            if (time < min) {
                min = time;
            }
        }
        return min;
    }

    private long getMax(long[] times) {
        long max = times[0];
        for (long time : times) {
            if (time > max) {
                max = time;
            }
        }
        return max;
    }

    private void printTimeStatistics(long[] times, String name) {
        double avg = getAverage(times);
        double std = getStd(times, avg);
        long max = getMax(times);
        long min = getMin(times);

        lines.add(String.format("| %s | %.5f | %.5f | %.5f | %.5f |",
                name,
                avg / NANO_TO_MILLI,
                std / NANO_TO_MILLI,
                (double) max / NANO_TO_MILLI,
                (double) min / NANO_TO_MILLI
        ));
    }

    private void printStatisticsHeader(String headerText) {
        lines.add(String.format("| %s | Average time (ms) | Standard deviation (ms) | Max time (ms) | Min time (ms) |",
                headerText
        ));
        lines.add("|---|---|---|---|---|");
    }

    /*
    Benchmarking different pathfinding algorithms
     */
    private void testMap(File file, String mapName) {
        MapReader mr = new MapReader(file);

        int height = mr.getHeight();
        int width = mr.getWidth();
        boolean[][] isWall = mr.getMap();
        Node start = new Node(1, width - 2);
        Node end = new Node(height - 2, 1);

        // Print path lengths for each algorithm
        lines.add("Statistics for each pathfinding algorithm in map: " + mapName);
        lines.add("");
        lines.add("| Algorithm | Path length |");
        lines.add("|---|---|");
        // Djikstra
        Pathfinder pf;
        pf = new AStar(start, end, width, height, isWall, new DjikstraHeuristic());
        pf.solve();
        lines.add("| Djikstra's algorithm | " + pf.getPathLength() + " |");
        // AStar euclidean
        pf = new AStar(start, end, width, height, isWall, new EuclideanHeuristic());
        pf.solve();
        lines.add("| Euclidean distance A* |" + pf.getPathLength() + " |");
        // Astar diagonal
        pf = new AStar(start, end, width, height, isWall, new DiagonalHeuristic());
        pf.solve();
        lines.add("| Diagonal distance A* |" + pf.getPathLength() + " |");
        // Jump point search
        pf = new JPS(start, end, width, height, isWall, new EuclideanHeuristic());
        pf.solve();
        lines.add("| Jump point search |" + pf.getPathLength() + " |");

        // Actual performance testing starts here
        int numberOfTest = 100;
        long[] djikstraRes = new long[numberOfTest];
        long[] eucRes = new long[numberOfTest];
        long[] diagRes = new long[numberOfTest];
        long[] jpsRes = new long[numberOfTest];

        for (int i = 0; i < numberOfTest; i++) {
            // Djikstra's algorithm
            pf = new AStar(start, end, width, height, isWall, new DjikstraHeuristic());
            djikstraRes[i] = timeToSolve(pf);
            // AStar euclidean
            pf = new AStar(start, end, width, height, isWall, new EuclideanHeuristic());
            eucRes[i] = timeToSolve(pf);
            // Astar diagonal
            pf = new AStar(start, end, width, height, isWall, new DiagonalHeuristic());
            diagRes[i] = timeToSolve(pf);
            // Jump point search
            pf = new JPS(start, end, width, height, isWall, new EuclideanHeuristic());
            jpsRes[i] = timeToSolve(pf);
        }

        lines.add("");
        printStatisticsHeader("Algorithm");
        printTimeStatistics(djikstraRes, "Djikstra's algorithm");
        printTimeStatistics(eucRes, "Euclidean distance A*");
        printTimeStatistics(diagRes, "Diagonal distance A*");
        printTimeStatistics(jpsRes, "Jump point search");
    }

    private long timeToSolve(Pathfinder pf) {
        long start = System.nanoTime();

        pf.solve();

        long end = System.nanoTime();

        return end - start;
    }

    /*
    Benchmarking My implementation of sqrt against Java Math.sqrt.
     */
    private void testAndPrintSqrt() {
        int runs = 100;
        int iters = 1000000;

        long[] resultsJava = new long[runs];
        long[] resultsOwn = new long[runs];

        for (int i = 0; i < runs; i++) {
            resultsJava[i] = testJavaSqrt(iters);
            resultsOwn[i] = testOwnSqrt(iters);
        }

        printStatisticsHeader("Implementation");
        printTimeStatistics(resultsJava, "Math.sqrt()");
        printTimeStatistics(resultsOwn, "Mathematics.sqrt()");
    }

    private long testJavaSqrt(int iters) {
        Random rnd = new Random();
        rnd.setSeed(0);

        long start = System.nanoTime();

        for (int i = 0; i < iters; i++) {
            Math.sqrt(rnd.nextDouble() * 1000);
        }

        long end = System.nanoTime();
        return end - start;
    }

    private long testOwnSqrt(int iters) {
        Random rnd = new Random();
        rnd.setSeed(0);

        long start = System.nanoTime();

        for (int i = 0; i < iters; i++) {
            Mathematics.sqrt(rnd.nextDouble() * 1000);
        }

        long end = System.nanoTime();
        return end - start;
    }
}

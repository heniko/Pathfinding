package heniko.pathfinding.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Utility for reading maps. Information about map format
 * https://movingai.com/benchmarks/formats.html.
 *
 * @author Niko Hernesniemi
 */
public class MapReader {

    private int height;
    private int width;
    private boolean[][] map;

    /**
     * Reads and parses map.
     *
     * @param file Map file.
     */
    public MapReader(File file) {
        try {
            readMap(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets height of the map.
     *
     * @return Height of the map.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets width of the map.
     *
     * @return Width of the map.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets map.
     *
     * @return Map.
     */
    public boolean[][] getMap() {
        return map;
    }

    private ArrayList<String> readLines(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> res = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            res.add(line);
        }

        return res;
    }

    private void readMap(File file) throws IOException {
        ArrayList<String> lines = readLines(file);

        // Read begin lines of the map
        String mapType = lines.get(0);
        String heightS = lines.get(1);
        String widthS = lines.get(2);

        // Parse map size
        height = Integer.parseInt(heightS.split(" ")[1]);
        width = Integer.parseInt(widthS.split(" ")[1]);

        //Read map
        map = new boolean[width][height];
        for (int i = 0; i < height; i++) {
            // First line of the map is 4
            char[] charray = lines.get(i + 4).toCharArray();
            for (int j = 0; j < width; j++) {
                // If terrain is not passable
                if (charray[j] == '@' || charray[j] == 'O') {
                    // Mark spot as wall
                    map[i][j] = true;
                }
            }
        }
    }
}

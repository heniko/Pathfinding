package graph;

import algorithms.AStar;
import java.util.ArrayList;

public final class Graph {

    int startX, startY, endX, endY, sizeX, sizeY;
    int[][] guiState;
    boolean[][] isWall;

    public Graph(int[][] guiState, int sizeX, int sizeY) {
        startX = 0;
        startY = 0;
        endX = sizeX - 1;
        endY = sizeY - 1;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.guiState = guiState;
        this.isWall = new boolean[sizeX][sizeY];
        changeNode(startX, startY, 1);
        changeNode(endX, endY, 2);
    }

    public void changeNode(int x, int y, int type) {
        /*
        0 = Empty
        1 = Start
        2 = End
        3 = Wall
         */
        switch (type) {
            case 1:
                setStart(x, y);
                break;
            case 2:
                setEnd(x, y);
                break;
            case 3:
                setWall(x, y);
                break;
            default:
                setEmpty(x, y);
        }
    }

    private void setStart(int x, int y) {
        if (!isEnd(x, y)) {
            // Change old start to empty in guiState
            guiState[startX][startY] = 0;
            // Change isWall of new start to false since wall can't be over start
            isWall[x][y] = false;
            // Change start position
            startX = x;
            startY = y;
            // Change start position in guiState
            guiState[x][y] = 1;
        }
    }

    private void setEnd(int x, int y) {
        if (!isStart(x, y)) {
            guiState[endX][endY] = 0;
            isWall[x][y] = false;
            endX = x;
            endY = y;
            guiState[x][y] = 2;
        }
    }

    private void setWall(int x, int y) {
        // Check that wall is not set over start or end
        if (!isStart(x, y) && !isEnd(x, y)) {
            isWall[x][y] = true;
            guiState[x][y] = 3;
        }
    }

    private void setEmpty(int x, int y) {
        // Check that empty spot is not set over start or end
        if (!isStart(x, y) && !isEnd(x, y)) {
            isWall[x][y] = false;
            guiState[x][y] = 0;
        }
    }

    private boolean isStart(int x, int y) {
        return x == startX && y == startY;
    }

    private boolean isEnd(int x, int y) {
        return x == endX && y == endY;
    }

    public void solve() {
        Node start = new Node(startX, startY);
        Node end = new Node(endX, endY);
        AStar astar = new AStar(start, end, sizeX, sizeY, isWall);
        ArrayList<Node> path = astar.solve();

        for (Node node : path) {
            int x = node.getX();
            int y = node.getY();
            if (!isStart(x, y) && !isEnd(x, y)) {
                guiState[x][y] = 4;
            }
        }
    }
}

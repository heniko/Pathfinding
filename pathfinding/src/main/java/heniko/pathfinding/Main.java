package heniko.pathfinding;

import heniko.pathfinding.util.Tester;
import javafx.application.Application;
import heniko.pathfinding.ui.GUI;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("test")) {
            new Tester().test();
        } else {
            Application.launch(GUI.class, args);
        }
    }
}

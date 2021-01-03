package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainWindow mw = new MainWindow(primaryStage);
        Time time = new Time(mw);
        mw.setTime(time);
        time.start();
        GameController gc = new GameController(mw);
        mw.setGameController(gc);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

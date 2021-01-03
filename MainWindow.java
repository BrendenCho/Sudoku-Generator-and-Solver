package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainWindow {

Stage stage;
Text timeText = new Text("00:00");
Time time;
Pane pane  = new Pane();
GameController gc;
    HBox menu = new HBox();
    Button newBttn = new Button("New Game");
    Button clearBttn = new Button("Clear Board");
    Button checkBttn = new Button("Check Board");
    Button solveBttn = new Button("Solve");


public MainWindow(Stage stage){
this.stage = stage;
Scene scene = new Scene(pane,450,500) ;

timeText.setFont(Font.font(18));

menu.setPadding(new Insets(5,5,5,5));
menu.setSpacing(10);
menu.setMaxHeight(50);
menu.getChildren().add(newBttn);
menu.getChildren().add(clearBttn);
menu.getChildren().add(checkBttn);
menu.getChildren().add(solveBttn);
menu.getChildren().add(timeText);
pane.getChildren().add(menu);
stage.setTitle("Sudoku | Brenden Cho");
stage.setScene(scene);
stage.setResizable(false);
stage.show();
}

public Text getTimeText(){
    return timeText;
}

public void setTime(Time time){
this.time = time;

    stage.setOnCloseRequest(e ->{
        time.exit();
    });
}

public Pane getPane(){
    return pane;
}

public void setGameController(GameController gc){
this.gc = gc;



newBttn.setOnAction(e ->{
    gc.clearAll();
    gc.generate();
    time.reset();
});

clearBttn.setOnAction(e ->{
    gc.clear();
});

checkBttn.setOnAction(e ->{
   if(gc.check() == true) {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setContentText("You won in " + time.toTime());
       alert.setTitle("Brenden Cho");

        alert.setOnCloseRequest(ee ->{
            gc.clearAll();
            gc.generate();
            time.reset();
        });



       alert.show();
   }
   });

solveBttn.setOnAction(e -> {
    gc.clearErrors();
    gc.solve();
});
}















}

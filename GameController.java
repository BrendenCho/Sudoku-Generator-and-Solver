package sample;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GameController {

MainWindow mw;
Node [][] board = new Node[9][9];
final int length = 50;
boolean error = false;
Queue<Node> errorQueue = new LinkedList<Node>();

public GameController(MainWindow mw){
this.mw = mw;
fillAndDraw();
generate();
}

public void fillAndDraw(){
    int xC = 0;
    int yC = 50;
    for(int x = 0; x < 9; x++){
       xC = 0;
        for(int y = 0; y < 9;y++){
            board[x][y] = new Node();
            board[x][y].setRect(new Rectangle(length,length));
            board[x][y].setText(new Text(""));
            board[x][y].getRect().setX(xC);
            board[x][y].getRect().setY(yC);
            board[x][y].getText().setFont(Font.font(30));
            board[x][y].getText().setX(xC + 17);
            board[x][y].getText().setY(yC + 35);
            board[x][y].getRect().setFill(Color.WHITE);
            board[x][y].getRect().setStroke(Color.BLACK);
            board[x][y].setX(x);
            board[x][y].setY(y);
            final int xx = x;
            final int yy = y;
            board[x][y].getRect().setOnMouseClicked(e ->{
                handleClick(board[xx][yy]);
            });

            board[x][y].getText().setOnMouseClicked(e ->{
                handleClick(board[xx][yy]);
            });


            final Rectangle rect = board[x][y].getRect();
            final Text text = board[x][y].getText();
            Platform.runLater(() ->{
                mw.getPane().getChildren().add(rect);
                mw.getPane().getChildren().add(text);
            });
           xC += 50;

        }
    yC += 50;
    }
}

public void handleClick(Node node){

    if(node.isAnchored() == false) {

        if(error == true){
         clearErrors();
         error = false;
        }



       if (node.getValue().equals("9")) {
           node.setValue("");
       } else if (node.getValue().equals("")) {
           node.setValue("1");
       } else {
           node.setValue(Integer.toString(Integer.parseInt(node.getValue()) + 1));
       }

       Platform.runLater(() -> {
           node.getText().setText(node.getValue());
       });

   }

}

public void clear(){
    for(int x = 0; x < 9;x++){
        for(int y = 0; y < 9;y++){
            if(board[x][y].isAnchored() == false){
                board[x][y].setValue("");
                final int xx = x;
                final int yy = y;
                Platform.runLater(() ->{
                    board[xx][yy].getText().setText(board[xx][yy].getValue());
                });

            }



        }

    }

    if(error == true){
        clearErrors();
        error = false;
    }
}

public boolean check(){

    for(int x = 0; x < 9;x++){
        for(int y = 0; y < 9;y++){
            if(checkRow(x,y,false) == false || checkColumn(x,y,false) == false || checkGrid(x,y,false) == false ){
                error = true;
                return false;

            }
        }
    }

    return true;
}

private boolean checkRow(int row,int column,boolean generate){
    if(generate == false) {
        if (board[row][column].getValue().equals("")) {
            errorQueue.add(board[row][column]);
            highlightError(board[row][column]);
            return false;
        }
    }
for(int x = 0; x < 9; x++){
    if(x == row){
        continue;
    }else if(board[x][column].getValue().equals(board[row][column].getValue())){
      if(generate == false) {
          errorQueue.add(board[x][column]);
          errorQueue.add(board[row][column]);
          highlightError(board[x][column]);
          highlightError(board[row][column]);
      }
        return false;
    }
}
    return true;
}

private boolean checkColumn(int row,int column,boolean generate){
  if(generate == false) {
      if (board[row][column].getValue().equals("")) {

          errorQueue.add(board[row][column]);
          highlightError(board[row][column]);
          return false;
      }
  }
    for(int x = 0; x < 9; x++){
        if(x == column){
            continue;
        }else if(board[row][x].getValue().equals(board[row][column].getValue())){
            if(generate == false) {
                errorQueue.add(board[row][x]);
                errorQueue.add(board[row][column]);
                highlightError(board[row][x]);
                highlightError(board[row][column]);
            }
            return false;
        }
    }
    return true;
}

private boolean checkGrid(int row, int column,boolean generate){
if(generate == false) {
    if (board[row][column].getValue().equals("")) {
        errorQueue.add(board[row][column]);
        highlightError(board[row][column]);
        return false;
    }
}
    int temprow = 0;
    int tempcolumn = 0;
    if(row >= 6 && row <= 8){
        temprow = 6;
    }else if(row >= 3 && row <= 5){
        temprow = 3;
    }else{
        temprow = 0;
    }

    if(column >= 6 && column <= 8){
        tempcolumn = 6;
    }else if(column >= 3 && column <= 5){
        tempcolumn = 3;
    }else{
        tempcolumn = 0;
    }

    for(int x = 0; x < 3;x++){
        for(int y = 0; y < 3;y++){
            if(temprow + x == row && tempcolumn + y == column) {
                continue;
            }
            else if(board[temprow + x][tempcolumn + y].getValue().equals(board[row][column].getValue())) {
              if(generate == false) {
                  errorQueue.add(board[temprow + x][tempcolumn + y]);
                  errorQueue.add(board[row][column]);
                  highlightError(board[temprow + x][tempcolumn + y]);
                  highlightError(board[row][column]);
              }
                return false;
            }
        }
    }



    return true;
}

    private void testFill(String num){
        int pos = 0;
        for(int x = 0; x < 9;x++){
            for(int y = 0; y < 9;y++){
                board[x][y].setValue(Character.toString(num.charAt(pos)));
                pos++;
                final int xx= x;
                final int yy = y;
                Platform.runLater(() ->{
                    board[xx][yy].getText().setText(board[xx][yy].getValue());
                });


            }
        }
    }

    private void highlightError(Node n){
        Platform.runLater(() -> {
            n.getRect().setFill(Color.RED);
        });
    }

    public void clearErrors(){
        while(!errorQueue.isEmpty()){
            Node n = errorQueue.peek();
            Platform.runLater(() -> {
                n.getRect().setFill(Color.WHITE);
            });
            errorQueue.remove();
        }

}
    public void generate(){
    for(int x = 0; x < 17;x++){
        int randX = (int)(Math.random() * 8);
        int randY = (int)(Math.random() * 8);
        boolean arr[] = new boolean[9];

        while(true){
            boolean reroll = true;
            for(int z = 0;z < arr.length;z++){
                if(arr[z] == false){
                    reroll = false;
                }
            }

            if(reroll == true){
                randX = (int)(Math.random() * 8) ;
                randY = (int)(Math.random() * 8) ;
                arr = new boolean[9];
            }

            int r = (int)(Math.random() * 8) + 1;
            arr[r - 1] = true;
            board[randX][randY].setValue(Integer.toString(r));

            if(checkRow(randX,randY,true) == true && checkColumn(randX,randY,true) == true && checkGrid(randX,randY,true) == true ){
                final int xx = randX;
                final int yy = randY;
                board[xx][yy].setAnchored(true);
                Platform.runLater(() -> {
                    board[xx][yy].getText().setText(board[xx][yy].getValue());
                });

                break;
            }

            board[randX][randY].setValue("");

        }

    }
    }


    public void solve(){
        Stack<Node> stack = new Stack<Node>();

        Node te = getNextUnanchoredNeighbor(0,0,true);
        if(te == null)
            return;

        stack.push(te);

        while(true) {
     //   try {
      //     Thread.sleep(2000);
       //}catch(InterruptedException e){}

        Node n = stack.peek();
      //  print();

        if(n.getValue().equals("")){
            n.setValue("1");
            Platform.runLater(() ->{
                board[n.getX()][n.getY()].getText().setText(n.getValue());
            });
        }else if(n.getValue().equals("9")){
            n.setValue("");
            stack.pop();
            continue;
        }else{
            n.setValue(Integer.toString(Integer.parseInt(n.getValue())+1));
        }

        if(solveCollision(n.getX(),n.getY()) == true){
            Node temp = getNextUnanchoredNeighbor(n.getX(),n.getY(),false);
            if(temp == null)
                return;

            Platform.runLater(() ->{
                n.getText().setText(n.getValue());
            });


            stack.push(temp);
        }




        }

    }

    private boolean solveCollision(int row,int column){
    return solveRow(row,column) == true && solveColumn(row,column) == true && solveGrid(row,column) == true;
    }

    private boolean solveRow(int row,int column){
        for(int x = 0; x < 9;x++){
            if(board[row][x].getValue().equals("")){
                continue;
            }else if(x == column){
                continue;
            }else if(board[row][x].getValue().equals(board[row][column].getValue())){
                return false;
            }
        }
         return true;
    }

    private boolean solveColumn(int row,int column){
        for(int x = 0; x < 9;x++){
            if(board[x][column].getValue().equals("")){
                continue;
            }else if(x == row){
                continue;
            }else if(board[x][column].getValue().equals(board[row][column].getValue())){
                return false;
            }
        }
        return true;
    }

    private boolean solveGrid(int row, int column){

        int temprow = 0;
        int tempcolumn = 0;
        if(row >= 6 && row <= 8){
            temprow = 6;
        }else if(row >= 3 && row <= 5){
            temprow = 3;
        }else{
            temprow = 0;
        }

        if(column >= 6 && column <= 8){
            tempcolumn = 6;
        }else if(column >= 3 && column <= 5){
            tempcolumn = 3;
        }else{
            tempcolumn = 0;
        }

        for(int x = 0; x < 3;x++){
            for(int y = 0; y < 3;y++){
                if(temprow + x == row && tempcolumn + y == column) {
                    continue;
                }else if(board[temprow + x][tempcolumn + y].getValue().equals("")){
                    continue;
                }
                else if(board[temprow + x][tempcolumn + y].getValue().equals(board[row][column].getValue())) {
                    return false;
                }
            }
        }



        return true;
    }


    private Node getNextUnanchoredNeighbor(int row,int column,boolean first){

        for(int x = 0; x < 9;x++){
            for(int y = 0; y < 9;y++){
                if(first == true) {
                    if (row == 0 && column == 0 && board[0][0].isAnchored() == false) {
                        return board[0][0];
                    }
                }
                if(x < row){
                    continue;
                }else if(x == row && y <= column){
                    continue;
                }else if(board[x][y].isAnchored() == false){
                    return board[x][y];
                }

            }
        }


        return null;
    }

    private void print(){
        for(int x = 0; x < 9;x++){
            System.out.println();
            for(int y = 0; y < 9;y++){
                if(board[x][y].getValue().equals("")){
                    System.out.print("_ ");
                }else{
                    System.out.print(board[x][y].getValue() + " ");
                }
            }
        }
        System.out.println();
    }

    public void clearAll(){
        for(int x = 0; x < 9;x++){
            for(int y = 0; y < 9;y++){
                board[x][y].setValue("");
                board[x][y].setAnchored(false);
                final int xx = x;
                final int yy = y;
                Platform.runLater(() ->{
                    board[xx][yy].getText().setText(board[xx][yy].getValue());
                });
            }
        }
    }

}

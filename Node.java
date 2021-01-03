package sample;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Node {

    private String value = "";
    private boolean anchored = false;
    private Rectangle rect;
    private Text text;
    private int x;
    private int y;


    public Node(){}

    public Node(String value){
        this.value = value;
    }

    public Node(String value,boolean anchored){
        this.value = value;
        this.anchored = anchored;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isAnchored() {
        return anchored;
    }

    public void setAnchored(boolean anchored) {
        this.anchored = anchored;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

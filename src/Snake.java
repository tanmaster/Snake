import java.util.ArrayList;

/**
 * Created by tanyucel on 21.12.16.
 */
public class Snake {

    private Coordinate head;
    private ArrayList<Coordinate> body;

    Snake(int xSize, int ySize) {
        int x = (int) (Math.random() * xSize);
        int y = (int) (Math.random() * ySize);

        head = new Coordinate(x, y);
        body = new ArrayList<>();
    }

    void move(int direction){

    }

    void grow(){
        body.add(0, new Coordinate(head.getX(), head.getY()));
    }

    void eat(){

    }



}

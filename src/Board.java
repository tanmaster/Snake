import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tan on 13.01.2017.
 */

// TODO: 15.01.2017 2 Panels, one Board that is drawn only once, and one where the snake will be redrawn on top of the Board.
class Board extends JPanel implements KeyListener {

    /**
     * Frame that will contain the panel that will be drawn in.
     */
    private JFrame frame;

    /**
     * Current player points.
     */
    // TODO: 15.01.2017 maybe make this a snake variable
    private int points;

    /**
     * The height and width of a single Field of the Board.
     */
    private int widthHeight;

    /**
     * Contains the Fields that will be drawn. todo: maybe this is not needed.
     */
    private ArrayList<Field> map;

    /**
     * The snake that will slither around.
     */
    private Snake snake;

    /**
     * The food that will be eaten by snake.
     */
    private Field food;

    /**
     * Width of the Board in pixels.
     */
    private int x;

    /**
     * Height of the Board in pixels.
     */
    private int y;

    /**
     * Boolean that will be set to true after a KeyEvent happens so we will have to wait until the new Board is drawn
     * before we listen to a new KeyEvent.
     */
    private boolean done;

    private Board(int x, int y, int widthHeight) {
        frame = new JFrame("Snek");
        frame.setVisible(true);
        frame.setSize(500, 500);
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        /*
      Panel that will be added to the Frame.
     */
        JPanel panel = new TestPane();
        frame.add(panel);
        frame.addKeyListener(this);
        createMap(500, 500);

        points = 0;
        done = false;

        this.x = x;
        this.y = y;
        this.widthHeight = widthHeight;
        snake = new Snake();

        frame.setSize(x + 7, y + 50);
        frame.setResizable(false);
        createMap(x, y);
        food = new Field(0, 0);
        food.setRandom();
        food.setColor(Color.red);
    }

    private void createMap(int x, int y) {
        this.map = new ArrayList<>();

        for (int i = 0; i < x; i += 20) {
            for (int j = 0; j < y; j += 20) {
                map.add(new Field(i, j));
            }
        }
    }

    /**
     * This will run the program.
     *
     * @return True if snake was able to move, false else.
     */
    private boolean test() {
        boolean a = snake.move();
        frame.repaint();
        done = false;
        return a;
    }

    /**
     * A method to end and exit the program.
     */
    void end() {
        this.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && snake.getDirection() != Direction.WEST && !done) {
            snake.setDirection(Direction.EAST);
            done = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && snake.getDirection() != Direction.EAST && !done) {
            snake.setDirection(Direction.WEST);
            done = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && snake.getDirection() != Direction.NORTH && !done) {
            snake.setDirection(Direction.SOUTH);
            done = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP && snake.getDirection() != Direction.SOUTH && !done) {
            snake.setDirection(Direction.NORTH);
            done = true;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public class TestPane extends JPanel {

        TestPane() {
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        /**
         * This will paint the Map, the Snake and the Food after every call once. This is not very efficient since the
         * map is redrawn every time.
         *
         * @param g
         */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();


            for (Field f : map) {
                g2d.setColor(f.getColor());
                g2d.drawRect(f.getX1(), f.getY1(), widthHeight, widthHeight);
            }

            for (Field f : snake.getBody()) {
                g2d.setColor(snake.getColor());
                g2d.fillRect(f.getX1() + 1, f.getY1() + 1, widthHeight - 1, widthHeight - 1);
            }
            g2d.setColor(food.getColor());
            g2d.fillRect(food.getX1() + 1, food.getY1() + 1, widthHeight - 1, widthHeight - 1);
            g2d.drawString("Punkte: " + points, 0, y + 20);

            g2d.dispose();


        }

    }

    public class Snake {

        /**
         * The body of the Snake which consists of several Fields. The first element in this list is the head of the
         * snake.
         */
        ArrayList<Field> body;
        /**
         * A direction that is essentially an enum (NORTH, EAST, SOUTH, WEST)
         */
        Direction direction;

        /**
         * Color of the Snake.
         */
        Color color;


        Snake() {
            this.body = new ArrayList<>();
            Field start = new Field(0, 0);
            start.setRandom();
            this.body.add(start);
            System.out.println(this.body.get(0));
            direction = Direction.SOUTH;

            color = Color.green;
        }

        ArrayList<Field> getBody() {
            return body;
        }

        Color getColor() {
            return color;
        }

        void setDirection(Direction direction) {
            this.direction = direction;
        }

        Direction getDirection() {
            return direction;
        }

        /**
         * This will make the snake grow. A Field will be added in front of the currents snake's head.
         */
        void grow() {
            Field oldHead = body.get(0);
            switch (direction) {
                case EAST:
                    body.add(0, new Field(oldHead.getX1() + widthHeight, oldHead.getY1()));
                    break;
                case NORTH:
                    body.add(0, new Field(oldHead.getX1(), oldHead.getY1() - widthHeight));
                    break;
                case SOUTH:
                    body.add(0, new Field(oldHead.getX1(), oldHead.getY1() + widthHeight));
                    break;
                case WEST:
                    body.add(0, new Field(oldHead.getX1() - widthHeight, oldHead.getY1()));
                    break;
                default:
                    break;

            }

            check();

        }

        /**
         * This will perform a move for the Snake. Essentially the Tail (last element of the Snake's body) will be
         * removed and one Field will be added in front of the Snakes head. The position of the new head is defined by
         * the direction the snake is currently headed to. If Snake runs into a food, grow() will be called and a new
         * food will be spawned. It is also ensured that the newly spawned food didn't spawn on the Snake's body.
         *
         * @return True if Snake was able to move, false if it ran into itself.
         */
        boolean move() {
            Field head = body.get(0);
            switch (direction) {
                case EAST:
                    body.add(0, new Field(head.getX1() + widthHeight, head.getY1()));
                    body.remove(body.size() - 1);
                    break;
                case NORTH:
                    body.add(0, new Field(head.getX1(), head.getY1() - widthHeight));
                    body.remove(body.size() - 1);
                    break;
                case SOUTH:
                    body.add(0, new Field(head.getX1(), head.getY1() + widthHeight));
                    body.remove(body.size() - 1);
                    break;
                case WEST:
                    body.add(0, new Field(head.getX1() - widthHeight, head.getY1()));
                    body.remove(body.size() - 1);
                    break;
                default:
                    break;

            }

            boolean flag;
            if (check()) {
                if (body.get(0).equals(food)) {
                    points++;
                    grow();

                    do {
                        food.setRandom();
                        flag = false;
                        for (Field f : body) {
                            if (f.equals(food)) {
                                flag = true;
                            }
                        }
                    } while (flag);

                }
                return true;
            } else return false;
        }

        /**
         * This performs a Field.check for every field of the body.
         *
         * @return False if Snake bit itself, true else.
         */
        boolean check() {
            for (Field f : body) {
                f.check();

            }

            for (int i = 1; i < body.size(); i++) {
                if (body.get(0).equals(body.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public class Field {

        private int x1;
        private int y1;

        private Color color;

        Field(int x1, int y1) {
            this.x1 = x1;
            this.y1 = y1;
            this.color = Color.blue;
        }

        int getX1() {
            return x1;
        }

        int getY1() {
            return y1;
        }

        void setX1(int x1) {
            this.x1 = x1;
        }

        void setY1(int y1) {
            this.y1 = y1;
        }

        Color getColor() {
            return color;
        }

        void setColor(Color color) {
            this.color = color;
        }

        /**
         * This will set the field to a new random position between (0,0) and (x,y).
         */
        void setRandom() {

            this.setX1(((int) (new Random().nextFloat() * (x / widthHeight))) * widthHeight);
            this.setY1(((int) (new Random().nextFloat() * (y / widthHeight))) * widthHeight);

        }

        /**
         * Checks if a field's x and y values are in bounds. If they are out of bounds (outside of map) the snake will
         * re-enter the map from the other side.
         */
        void check() {
            if (this.getX1() < 0) {
                this.setX1(x + this.getX1());
            }
            if (this.getY1() < 0) {
                this.setY1(y + this.getY1());
            }
            if (this.getX1() >= x) {
                this.setX1(0);
            }
            if (this.getY1() >= y) {
                this.setY1(0);
            }

        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Field field = (Field) o;

            if (x1 != field.x1) return false;
            return y1 == field.y1;

        }

        @Override
        public int hashCode() {
            int result = x1;
            result = 31 * result + y1;
            return result;
        }

        @Override
        public String toString() {
            return "Field{" +
                    "x1=" + x1 +
                    ", y1=" + y1 +
                    ", color=" + color +
                    '}';
        }
    }

    public enum Direction {
        NORTH(0),
        EAST(1),
        SOUTH(2),
        WEST(3);

        private final int value;

        Direction(int val) {
            this.value = val;
        }

        public int getValue() {
            return value;
        }
    }


    public static void main(String[] args) {

        while (true) {
            Board board = new Board(800, 800, 20);
            while (board.test()) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            board.end();

        }


    }
}

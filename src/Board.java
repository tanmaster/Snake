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
// TODO: 22.01.17 improve this :^)
public class Board extends JPanel implements KeyListener {

    /**
     * Frame that will contain the panel that will be drawn in.
     */
    private JFrame frame;

    /**
     * Current player points.
     */
    private int points;

    /**
     * The height and width of a single Field of the Board.
     */
    private int widthHeight;

    /**
     * The snek that will slither around.
     */
    private snek snek;

    /**
     * The food that will be eaten by snek.
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
        frame = new JFrame("snek");
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(x + 7, y + 50);
        frame.setResizable(false);
        frame.add(this);
        frame.addKeyListener(this);

        this.x = x;
        this.y = y;
        this.widthHeight = widthHeight;

        points = 0;
        done = false;
        snek = new snek();
        food = new Field(0, 0);
        food.setRandom();
        food.setColor(Color.red);
    }


    /**
     * This will run the program.
     *
     * @return True if snek was able to move, false else.
     */
    private boolean step() {
        boolean a = snek.move();
        frame.repaint();
        done = false;
        return a;
    }

    /**
     * A method to end and exit the program.
     */
    private void end() {
        this.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && snek.getDirection() != Direction.WEST && !done) {
            snek.setDirection(Direction.EAST);
            done = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && snek.getDirection() != Direction.EAST && !done) {
            snek.setDirection(Direction.WEST);
            done = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && snek.getDirection() != Direction.NORTH && !done) {
            snek.setDirection(Direction.SOUTH);
            done = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP && snek.getDirection() != Direction.SOUTH && !done) {
            snek.setDirection(Direction.NORTH);
            done = true;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        for (Field f : snek.getBody()) {
            g2d.setColor(snek.getColor());
            g2d.fillRect(f.getX1() + 1, f.getY1() + 1, widthHeight - 1, widthHeight - 1);
        }
        g2d.setColor(food.getColor());
        g2d.fillRect(food.getX1() + 1, food.getY1() + 1, widthHeight - 1, widthHeight - 1);
        g2d.drawString("Punkte: " + points, 0, y + 20);

        g2d.dispose();


    }


    private class snek {

        /**
         * The body of the snek which consists of several Fields. The first element in this list is the head of the
         * snek.
         */
        ArrayList<Field> body;

        /**
         * A direction that is essentially an enum (NORTH, EAST, SOUTH, WEST)
         */
        Direction direction;

        /**
         * Color of the snek.
         */
        Color color;


        snek() {
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
         * This will make the snek grow. A Field will be added in front of the currents snek's head.
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
         * This will perform a move for the snek. Essentially the Tail (last element of the snek's body) will be
         * removed and one Field will be added in front of the Snakes head. The position of the new head is defined by
         * the direction the snek is currently headed to. If snek runs into a food, grow() will be called and a new
         * food will be spawned. It is also ensured that the newly spawned food didn't spawn on the snek's body.
         *
         * @return True if snek was able to move, false if it ran into itself.
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
         * @return False if snek bit itself, true else.
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

    private class Field {

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
         * Checks if a field's x and y values are in bounds. If they are out of bounds (outside of map) the snek will
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

    private enum Direction {
        NORTH(0),
        EAST(1),
        SOUTH(2),
        WEST(3);

        private final int value;

        Direction(int val) {
            this.value = val;
        }
    }

    static {
        //this will run immediately after first call of a Board Object
        System.out.println("aaa");
    }

    public static void main(String[] args) {
        while (true) {
            Board board = new Board(800, 800, 20);
            while (board.step()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            board.end();

        }


    }
}

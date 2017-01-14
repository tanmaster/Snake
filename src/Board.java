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
class Board extends JPanel implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && snake.getDirection() != Direction.WEST)
            snake.setDirection(Direction.EAST);
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && snake.getDirection() != Direction.EAST)
            snake.setDirection(Direction.WEST);
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && snake.getDirection() != Direction.NORTH)
            snake.setDirection(Direction.SOUTH);
        else if (e.getKeyCode() == KeyEvent.VK_UP && snake.getDirection() != Direction.SOUTH)
            snake.setDirection(Direction.NORTH);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private JFrame frame;
    private JPanel panel;

    private int widthHeight;
    private ArrayList<Field> map;
    private Snake snake;
    private Field food;
    private int x;
    private int y;


    Board() {
        frame = new JFrame("Snek");
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        panel = new TestPane();
        frame.add(panel);
        frame.addKeyListener(this);
        createMap(500, 500);
        snake = new Snake();
    }

    Board(int x, int y, int widthHeight) {
        this();
        this.x = x;
        this.y = y;
        this.widthHeight = widthHeight;
        frame.setSize(x + 7, y + 30);
        frame.setResizable(false);
        createMap(x, y);

    }

    private void createMap(int x, int y) {
        this.map = new ArrayList<>();

        for (int i = 0; i < x; i += 20) {
            for (int j = 0; j < y; j += 20) {
                map.add(new Field(i, j));
            }
        }
    }

    boolean test() {
        snake.grow();
        boolean a = snake.move();
        frame.repaint();

        return a;
    }

    void end(){
        this.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public class TestPane extends JPanel {

        public TestPane() {
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

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
            g2d.dispose();


        }

    }


    public class Snake {

        ArrayList<Field> body;
        Direction direction;
        Color color;

        Snake() {
            this.body = new ArrayList<>();
            this.body.add(new Field(0, 0));
            direction = Direction.SOUTH;

            color = Color.green;
        }

        public ArrayList<Field> getBody() {
            return body;
        }

        public Color getColor() {
            return color;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public Direction getDirection() {
            return direction;
        }

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

            return check();

        }

        boolean check() {
            for (Field f : body) {
                if (f.getX1() < 0) {
                    f.setX1(x + f.getX1());
                }
                if (f.getY1() < 0) {
                    f.setY1(y + f.getY1());
                }
                if (f.getX1() >= x) {
                    f.setX1(0);
                }
                if (f.getY1() >= y) {
                    f.setY1(0);
                }


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

        public int getX1() {
            return x1;
        }

        public int getY1() {
            return y1;
        }

        public void setX1(int x1) {
            this.x1 = x1;
        }

        public void setY1(int y1) {
            this.y1 = y1;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Field field = (Field) o;

            if (x1 != field.x1) return false;
            if (y1 != field.y1) return false;
            return color != null ? color.equals(field.color) : field.color == null;
        }

        @Override
        public int hashCode() {
            int result = x1;
            result = 31 * result + y1;
            result = 31 * result + (color != null ? color.hashCode() : 0);
            return result;
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
        Board board = new Board(800, 800, 20);
        while (board.test()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        board.end();


    }
}

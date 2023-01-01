import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel implements ActionListener {
    int boardHeight = 400;
    int boardWidth = 400;
    int x[] = new int[boardHeight * boardWidth];
    int y[] = new int[boardHeight * boardWidth];
    int dots;
    int apple_x = 80;
    int apple_y = 80;
    int dotSize = 10;
    Image apple;
    Image body;
    Image head;

    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;

    Timer timer;
    int DELAY = 100;
    int Rand_Pos = 39;
    boolean inGame = true;
    public GameBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(boardHeight, boardWidth));
        setBackground(Color.BLACK);
        loadImages();
        initializeGame();
    }
    public void initializeGame(){
        dots = 3;
        for(int i = 0; i < dots; i ++) {
            x[i] = 150 + i * dotSize;
            y[i] = 150;
        }
        timer = new Timer(DELAY,this);
        timer.start();
    }
    private void loadImages() {
        ImageIcon imageApple = new ImageIcon("src/resources/apple.png");
        apple = imageApple.getImage();

        ImageIcon imageHead = new ImageIcon("src/resources/head.png");
        head = imageHead.getImage();

        ImageIcon imageBody = new ImageIcon("src/resources/dot.png");
        body = imageBody.getImage();
    }
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
       if(inGame) {
           graphics.drawImage(apple, apple_x, apple_y,this);
           for(int i=0; i<dots; i++){
               if(i==0) {
                   graphics.drawImage(head, x[0], y[0], this);
               }
               else {
                   graphics.drawImage(body, x[i], y[i], this);
               }
           }
           Toolkit.getDefaultToolkit().sync();
       }

       else {
           gameOver(graphics);
       }
    }
    public void move() {
        for(int i = dots-1; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
       if(leftDirection) {
           x[0] -= dotSize;
       }
       if(rightDirection) {
           x[0] += dotSize;
       }
       if(upDirection) {
           y[0] -= dotSize;
       }
       if(downDirection) {
           y[0] += dotSize;
       }
    }

    private void locateApple() {
        int r = (int)(Math.random()*(Rand_Pos));
        apple_x = r*dotSize;

        r = (int)(Math.random()*(Rand_Pos));
        apple_y = r*dotSize;
    }
    private void checkApple() {
        if(x[0] == apple_x && y[0] == apple_y) {
            dots++;
            locateApple();
        }
    }

    private void checkCollision() {
        if(x[0] < 0) {
            inGame = false;
        }
        if(x[0] >= boardWidth) {
            inGame = false;
        }
        if(y[0] < 0) {
            inGame = false;
        }
        if(y[0] >= boardHeight) {
            inGame = false;
        }
        for(int i=dots-1; i >= 3; i--) {
            if(x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                break;
            }
        }
    }
    private void gameOver(Graphics graphics) {
        String msg = "Game Over";
        Font small = new Font("Helvetica" , Font.BOLD, 16);
        FontMetrics metrics = getFontMetrics(small);
        graphics.setColor(Color.BLUE);
        graphics.setFont(small);
        graphics.drawString(msg,(boardWidth - metrics.stringWidth(msg))/2, boardHeight/2);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(inGame == true) {
            checkApple();
            checkCollision();
            move();

        }

        repaint();
    }

    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int key = keyEvent.getKeyCode();
            if((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if((key == KeyEvent.VK_UP) && (!downDirection)) {
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }
    }
}



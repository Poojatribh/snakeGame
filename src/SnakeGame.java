import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGame extends JFrame {
    public GameBoard board;
    public SnakeGame() {
        board = new GameBoard();
        add(board);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame snakeGame = new SnakeGame();
    }
}

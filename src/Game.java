import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game extends JPanel implements ActionListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int tileSize = 25;

    // Snake
    Tile snakeHead;

    // Food
    Tile food;
    Random random;

    // Game Logic
    Timer gameLoop;

    public Game() {
        setPreferredSize(new Dimension(Board.width, Board.height));
        setBackground(Color.black);

        snakeHead = new Tile(5, 5);
        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < Board.width / tileSize; i++) {
            g.drawLine(i*tileSize, 0, i*tileSize, Board.height);
            g.drawLine(0, i*tileSize, Board.width,  i*tileSize);
        }

        // Food
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

        // Snake
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
    }

    public void placeFood() {
        food.x = random.nextInt(Board.width / tileSize);
        food.y = random.nextInt(Board.height / tileSize);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}

import helpers.InputEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener {
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
    ArrayList<Tile> snakeBody;

    // Food
    Tile food;
    Random random;

    // Game Logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    public Game() {
        setPreferredSize(new Dimension(Board.width, Board.height));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 1;

        gameLoop = new Timer(200, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Food
        g.setColor(Color.red);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        // Snake Head
        g.setColor(Color.GREEN);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // Snake body
        snakeBody.forEach(tile -> g.fill3DRect(tile.x * tileSize, tile.y * tileSize, tileSize, tileSize, true));

        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(Board.width / tileSize);
        food.y = random.nextInt(Board.height / tileSize);
    }

    public void move() {
        // Eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // Snake body
        for (int i = snakeBody.size() -1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // Snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Game Over conditions
        snakeBody.forEach(snakePart -> {
            if (collision(snakeHead, snakePart)) gameOver = true;
        });

        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > Board.width ||
                snakeHead.y *tileSize < 0 || snakeHead.y * tileSize > Board.height) {
            gameOver = true;
        }
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameOver) gameLoop.stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (InputEvent.pressedUp(e) && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (InputEvent.pressedDown(e) && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (InputEvent.pressedLeft(e) && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (InputEvent.pressedRight(e) && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

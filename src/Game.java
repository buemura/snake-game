import entities.Tile;
import helpers.InputEvent;
import ui.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener {
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

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Food
        g.setColor(Color.red);
        g.fill3DRect(food.x * Tile.size, food.y * Tile.size, Tile.size, Tile.size, true);

        // Snake Head
        g.setColor(Color.GREEN);
        g.fill3DRect(snakeHead.x * Tile.size, snakeHead.y * Tile.size, Tile.size, Tile.size, true);

        // Snake body
        snakeBody.forEach(tile -> g.fill3DRect(tile.x * Tile.size, tile.y * Tile.size, Tile.size, Tile.size, true));

        // Score
        Score.drawScore(g, gameOver, snakeBody.size());
    }

    public void placeFood() {
        food.x = random.nextInt(Board.width / Tile.size);
        food.y = random.nextInt(Board.height / Tile.size);
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

        if (snakeHead.x * Tile.size < 0 || snakeHead.x * Tile.size > Board.width ||
                snakeHead.y *Tile.size < 0 || snakeHead.y * Tile.size > Board.height) {
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

import entities.Food;
import entities.Snake;
import entities.Tile;
import helpers.InputEvent;
import ui.Board;
import ui.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Game extends JPanel implements ActionListener, KeyListener {
    // Snake
    Snake snakeHead;
    ArrayList<Snake> snakeBody;

    // Food
    Food food;

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

        snakeHead = new Snake(5, 5);
        snakeBody = new ArrayList<Snake>();

        food = new Food(10, 10);
        food.generate();

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

    public void move() {
        // Eat food
        if (snakeHead.ateFood(food)) {
            snakeBody.add(new Snake(food.x, food.y));
            food.generate();
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
            if (snakeHead.collidedWithBody(snakePart)) gameOver = true;
        });

        if (snakeHead.leftMap()) gameOver = true;
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

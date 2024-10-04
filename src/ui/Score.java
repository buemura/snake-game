package ui;

import entities.Tile;

import java.awt.*;

public class Score {
    public static void drawScore(Graphics g, boolean gameOver, int score) {
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        if (!gameOver) {
            g.drawString("Score: " + String.valueOf(score), Tile.size - 16, Tile.size);
            return;
        }

        g.setColor(Color.red);
        g.drawString("Game Over: " + String.valueOf(score), Tile.size - 16, Tile.size);
    }
}

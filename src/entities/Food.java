package entities;

import ui.Board;

import java.util.Random;

public class Food extends Tile {
    private final Random random;

    public Food(int x, int y) {
        super(x, y);
        this.random = new Random();
    }

    public void generate() {
        this.x = random.nextInt(Board.width / Tile.size);
        this.y = random.nextInt(Board.height / Tile.size);
    }
}

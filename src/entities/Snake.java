package entities;

import ui.Board;

public class Snake extends Tile {
    public Snake(int x, int y) {
        super(x, y);
    }

    public boolean leftMap() {
        return this.x * Tile.size < 0 || this.x * Tile.size > Board.width || this.y * Tile.size < 0 || this.y * Tile.size > Board.height;
    }

    public boolean collidedWithBody(Snake bodyPart) {
        return this.x == bodyPart.x && this.y == bodyPart.y;
    }

    public boolean ateFood(Food food) {
        return this.x == food.x && this.y == food.y;
    }
}

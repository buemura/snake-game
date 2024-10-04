import ui.Board;

public class Main {
    public static void main(String[] args) {
        Board board = new Board("Snake Game");
        board.start(new Game());
    }
}
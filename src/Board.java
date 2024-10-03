import javax.swing.*;

public class Board extends JFrame {
    public static int width = 600;
    public static int height = 600;

    public Board(String boardName) {
        super(boardName);
        super.setSize(Board.width, Board.height);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void start(JPanel panel) {
        super.add(panel);
        super.pack();
        panel.requestFocus();
        super.setVisible(true);
    }
}

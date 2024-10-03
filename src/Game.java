import javax.swing.*;
import java.awt.*;

public class Game extends JPanel {
    public Game() {
        setPreferredSize(new Dimension(Board.width, Board.height));
        setBackground(Color.black);
    }
}

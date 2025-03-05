import javax.swing.*;

public class PizzaGUIRunner {
    public static void main(String[] args) {
        // Run the Pizza order GUI
        SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame frame = new PizzaGUIFrame();
            frame.setVisible(true);
        });
    }
}

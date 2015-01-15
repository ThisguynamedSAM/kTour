import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {
    private final int BOARD_DIMENSIONS = 8;

    public Main() {
        super();
    }

    /** init app JFrame wrapper */
    public void createAndShowGui() {
        setSize(new Dimension(500, 500));
        setLayout(new GridLayout(1, 1));
        setContentPane(new kTour(BOARD_DIMENSIONS));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /** clean up resources here */
    public void onWindowClosing() {
        System.exit(0);
    }

    public static void main(String[] args) {
        final Main app = new Main();

        app.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                app.onWindowClosing();
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                app.createAndShowGui();
            }
        });
    }
}
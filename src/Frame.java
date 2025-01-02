import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame() {
        this.setTitle("GCM");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        ImageIcon icon = new ImageIcon("gcm.png");
        this.setIconImage(icon.getImage());
        this.setLayout(new BorderLayout());
    }
}


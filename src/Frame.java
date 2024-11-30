import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame() {
        this.setTitle("GCM");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(960, 540);
        this.setVisible(true);
        ImageIcon icon = new ImageIcon("gcm.png");
        this.setIconImage(icon.getImage());
        this.setLayout(null);


    }
}

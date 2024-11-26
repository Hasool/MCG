import javax.swing.*;
import java.awt.*;
//hada khloh liya
public class Frame extends JFrame {
    Frame(){
        this.setTitle("GCM");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(540,960);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(0xADDBEA));

        ImageIcon icon = new ImageIcon("gcm.png");
        this.setIconImage(icon.getImage());
    }
}

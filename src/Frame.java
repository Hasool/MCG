import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame() {
        initializeFrame();
    }

    private void initializeFrame() {
        setTitle("GCM");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(getFrameIcon());
        setLayout(new BorderLayout());
        setVisible(true);
    }

    private Image getFrameIcon() {
        ImageIcon icon = new ImageIcon("gcm.png");
        return icon.getImage();
    }
}
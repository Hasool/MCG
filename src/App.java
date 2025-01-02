import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class App {

    public void Choose(Owner owner) {
        if (owner.doctors.isEmpty() && owner.patients.isEmpty()) {
            owner.Owners(false);
        } else {
            refreshFrame(choose(owner));
        }
    }

    protected JPanel choose(Owner owner) {
        ImageBackgroundPanel jPanel = new ImageBackgroundPanel("resources/bg.png");
        jPanel.setLayout(new BorderLayout());

        JPanel panel = createButtonPanel(owner);
        jPanel.add(panel, BorderLayout.WEST);

        return jPanel;
    }

    private JPanel createButtonPanel(Owner owner) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Main.secondBtn.darker());

        panel.add(Box.createVerticalGlue());

        JButton admin = createCenteredButton("Admin", e -> owner.Owners(false));
        panel.add(admin);

        panel.add(Box.createRigidArea(new Dimension(0, 50)));

        JButton doctor = createCenteredButton("Doctor", e -> refreshFrame(DocUser(null, false)));
        doctor.setEnabled(!owner.doctors.isEmpty());
        panel.add(doctor);

        panel.add(Box.createRigidArea(new Dimension(400, 50)));

        JButton patient = createCenteredButton("Patient", e -> refreshFrame(patUser(null)));
        patient.setEnabled(!owner.patients.isEmpty());
        panel.add(patient);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JButton createCenteredButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(Main.mainBtn);
        button.setMaximumSize(new Dimension(200, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(action);
        return button;
    }

    protected JPanel DocUser(Doctor doct, boolean t) {
        final Doctor[] docHolder = {doct};
        JPanel panel = new JPanel();
        panel.setBackground(Main.mainBg);
        panel.setLayout(new GridBagLayout());

        if (doct == null) {
            panel.add(createDoctorSignInPanel(docHolder));
        } else {
            JPanel docPanel = doct.user(t);
            panel.add(docPanel);
        }

        return panel;
    }

    private JPanel createDoctorSignInPanel(final Doctor[] docHolder) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel CNTitle = new JLabel("Sign In");
        CNTitle.setFont(new Font("Arial", Font.BOLD, 16));
        CNTitle.setForeground(Main.mainBtn.darker());
        panel.add(CNTitle, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel NName = new JLabel("Enter your full Name:");
        NName.setForeground(Main.mainBtn);
        panel.add(NName, gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 25));
        panel.add(nameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel password = new JLabel("Enter your ID:");
        password.setForeground(Main.mainBtn);
        panel.add(password, gbc);

        gbc.gridx = 1;
        JTextField IdField = new JTextField();
        IdField.setPreferredSize(new Dimension(200, 25));
        panel.add(IdField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 1;
        JButton submit = new JButton("Submit");
        submit.setBackground(Main.mainBtn);
        submit.addActionListener(e -> handleDoctorSignIn(nameField, IdField, docHolder));
        panel.add(submit, gbc);

        return panel;
    }

    private void handleDoctorSignIn(JTextField nameField, JTextField IdField, final Doctor[] docHolder) {
        boolean doctorFound = false;
        for (Doctor doctor : Main.owner.doctors) {
            if (Objects.equals(IdField.getText(), doctor.Id) &&
                    Objects.equals(nameField.getText(), doctor.fullName)) {
                docHolder[0] = doctor;
                doctorFound = true;
                break;
            }
        }
        if (!doctorFound) {
            JOptionPane.showMessageDialog(Main.frame, "Something seems wrong. Try again.");
            refreshFrame(choose(Main.owner));
        } else {
            refreshFrame(DocUser(docHolder[0], false));
        }
    }

    protected JPanel patUser(Patient patient) {
        final Patient[] patHolder = {patient};
        JPanel panel = new JPanel();
        panel.setBackground(Main.mainBg);
        panel.setLayout(new GridBagLayout());

        if (patHolder[0] == null) {
            panel.add(createPatientSignInPanel(patHolder));
        } else {
            patient.user();
        }

        return panel;
    }

    private JPanel createPatientSignInPanel(final Patient[] patHolder) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel CNTitle = new JLabel("Sign In");
        CNTitle.setForeground(Main.mainBtn.darker());
        CNTitle.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(CNTitle, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel NName = new JLabel("Enter your full Name:");
        NName.setForeground(Main.mainBtn);
        panel.add(NName, gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 25));
        panel.add(nameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel password = new JLabel("Enter your password:");
        password.setForeground(Main.mainBtn);
        panel.add(password, gbc);

        gbc.gridx = 1;
        JTextField PasswordField = new JTextField();
        PasswordField.setPreferredSize(new Dimension(200, 25));
        panel.add(PasswordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 1;
        JButton submit = getJButton(nameField, PasswordField, patHolder);
        submit.setBackground(Main.mainBtn);
        panel.add(submit, gbc);

        return panel;
    }

    private JButton getJButton(JTextField nameField, JTextField PasswordField, final Patient[] patHolder) {
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> handlePatientSignIn(nameField, PasswordField, patHolder));
        return submit;
    }

    private void handlePatientSignIn(JTextField nameField, JTextField PasswordField, final Patient[] patHolder) {
        boolean patientFound = false;
        for (Patient patient1 : Main.owner.patients) {
            if (Objects.equals(nameField.getText(), patient1.fullName) &&
                    Objects.equals(PasswordField.getText(), patient1.code)) {
                patHolder[0] = patient1;
                patientFound = true;
                break;
            }
        }
        if (!patientFound) {
            JOptionPane.showMessageDialog(Main.frame, "Something seems wrong. Try again.");
            refreshFrame(choose(Main.owner));
        } else {
            refreshFrame(patUser(patHolder[0]));
        }
    }

    private void refreshFrame(JPanel panel) {
        Main.frame.getContentPane().removeAll();
        Main.frame.getContentPane().add(panel, BorderLayout.CENTER);
        Main.frame.revalidate();
        Main.frame.repaint();
    }
}
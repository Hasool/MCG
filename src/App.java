import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class App {

    public void Choose(Owner owner) {
        if (owner.doctors.isEmpty() && owner.patients.isEmpty()) {
            owner.Owners(false);
        } else {
            Main.frame.getContentPane().removeAll();
            Main.frame.getContentPane().add(choose(owner));
            Main.frame.revalidate();
            Main.frame.repaint();
        }
    }

    protected JPanel choose(Owner owner) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        JButton admin = new JButton("Admin");
        admin.addActionListener(e -> owner.Owners(false));
        panel.add(admin, gbc);

        gbc.gridy = 1;
        JButton doctor = new JButton("Doctor");
        doctor.setEnabled(!owner.doctors.isEmpty());
        doctor.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Main.frame.add(DocUser(null,false), BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        panel.add(doctor, gbc);

        gbc.gridy = 2;
        JButton patient = new JButton("Patient");
        patient.setEnabled(!owner.patients.isEmpty());
        patient.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Main.frame.add(patUser(null), BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        panel.add(patient, gbc);

        return panel;
    }


    protected JPanel DocUser(Doctor doct,boolean t) {
        final Doctor[] docHolder = {doct};
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if (doct == null) {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            JLabel CNTitle = new JLabel("Sign In");
            CNTitle.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(CNTitle, gbc);

            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            JLabel NName = new JLabel("Enter your full Name:");
            panel.add(NName, gbc);

            gbc.gridx = 1;
            JTextField nameField = new JTextField();
            nameField.setPreferredSize(new Dimension(200, 25));
            panel.add(nameField, gbc);

            gbc.gridy = 2;
            gbc.gridx = 0;
            JLabel password = new JLabel("Enter your ID:");
            panel.add(password, gbc);

            gbc.gridx = 1;
            JTextField IdField = new JTextField();
            IdField.setPreferredSize(new Dimension(200, 25));
            panel.add(IdField, gbc);

            gbc.gridy = 3;
            gbc.gridx = 1;
            JButton submit = new JButton("Submit");
            submit.addActionListener(e -> {
                boolean doctorFound = false;
                for (Doctor doctor : Main.owner.doctors) {
                    if (Objects.equals(IdField.getText(), doctor.Id) &&
                            Objects.equals(nameField.getText(), doctor.fullName)) {
                        docHolder[0] = doctor; // Assign to the array
                        doctorFound = true;
                        break;
                    }
                }
                if (!doctorFound) {
                    JOptionPane.showMessageDialog(Main.frame, "Something seems wrong. Try again.");
                    Main.frame.getContentPane().removeAll();
                    Main.frame.getContentPane().add(choose(Main.owner));
                } else {
                    Main.frame.getContentPane().removeAll();
                    Main.frame.getContentPane().add(DocUser(docHolder[0],false)); // Use the array value
                }
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            panel.add(submit, gbc);
        } else {
            JPanel docPanel = doct.user(t);
            panel.add(docPanel);
        }

        return panel;
    }

    protected JPanel patUser(Patient patient){
        final Patient[] patHolder = {patient};
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if (patHolder[0] == null){
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            JLabel CNTitle = new JLabel("Sign In");
            CNTitle.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(CNTitle, gbc);

            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            JLabel NName = new JLabel("Enter your full Name:");
            panel.add(NName, gbc);

            gbc.gridx = 1;
            JTextField nameField = new JTextField();
            nameField.setPreferredSize(new Dimension(200, 25));
            panel.add(nameField, gbc);

            gbc.gridy = 2;
            gbc.gridx = 0;
            JLabel password = new JLabel("Enter your password:");
            panel.add(password, gbc);

            gbc.gridx = 1;
            JTextField PasswordField = new JTextField();
            PasswordField.setPreferredSize(new Dimension(200, 25));
            panel.add(PasswordField, gbc);

            gbc.gridy = 3;
            gbc.gridx = 1;
            JButton submit = getJButton(nameField, PasswordField, patHolder);
            panel.add(submit, gbc);
        } else {
            patient.user();
        }

        return panel;
    }

    private JButton getJButton(JTextField nameField, JTextField PasswordField, Patient[] patHolder) {
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
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
                Main.frame.getContentPane().removeAll();
                Main.frame.getContentPane().add(choose(Main.owner));
            } else {
                Main.frame.getContentPane().removeAll();
                Main.frame.getContentPane().add(patUser(patHolder[0]));
            }
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        return submit;
    }
}

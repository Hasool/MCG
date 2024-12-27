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
        patient.addActionListener(e -> handlePatientClick());
        panel.add(patient, gbc);

        return panel;
    }

    // Event Handlers
    private void handleAdminClick() {
        JOptionPane.showMessageDialog(Main.frame, "Admin button clicked!");
    }

    private void handleDoctorClick() {
        JOptionPane.showMessageDialog(Main.frame, "Doctor button clicked!");
    }

    private void handlePatientClick() {
        JOptionPane.showMessageDialog(Main.frame, "Patient button clicked!");
    }

    protected JPanel DocUser(Doctor doct,boolean t) {
        final Doctor[] docHolder = {doct}; // Use an array to hold the doc reference
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


    protected void PatUser(Patient pat){
        if (pat == null){
            System.out.println("enter your full name ");
            String tempName = Main.reader.next();

            System.out.println("enter your password");
            String tempCode = Main.reader.next();

            for (Patient patient : Main.owner.patients){
                if (patient.fullName.equals(tempName) && patient.code.equals(tempCode)) {
                    System.out.println("hi " + patient.fullName);
                    pat = patient;
                    break;
                }
            }
            if (pat == null){
                System.out.println("there's no doctor with those information");
                App app = new App();
                app.Choose(Main.owner);
            }
            System.out.println("----------------------------\n");

            System.out.println("0: sign out" +
                    "\n1: see your information" +
                    "\n2: update your password" +
                    "\n3: see your next appointment" +
                    (pat.medicalHistories.isEmpty()?"":"\n4: see your MedicalHistory"));

            byte c;
            do{
                c = Main.reader.nextByte();
            }while (c<0 || c>3);

            if(c == 0){
                Main.signOut();
            }
            else if (c == 1) {
                System.out.println(pat.toString());
                System.out.println("----------------------");
                System.out.println("0: sign out" +
                        "\n1: update your information" +
                        "\n2: return");
                byte ch;
                do{
                    ch = Main.reader.nextByte();
                }while (ch<0 || ch>2);

                if (ch==0){
                    Main.signOut();
                }
                else if (ch==1) {
                    pat.setInfo();
                }
                else {
                    PatUser(pat);
                }

            }
            else if (c==2) {
                System.out.println("enter your current password");
                String tempCode1 = Main.reader.next();

                if (pat.code.equals(tempCode1)){
                    pat.setCode();
                } else {
                    System.out.println("you're wrong");
                    PatUser(pat);
                }

            }
            else if (c==3){
                System.out.println(pat.getAppointment());
            }
            else if (c==4 && !pat.medicalHistories.isEmpty()){
                pat.getAppointment();
            }

        }
    }
}

/**/
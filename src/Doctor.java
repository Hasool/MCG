import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Doctor extends Human {
    protected String Id;
    protected String code = null;
    protected ArrayList<Patient> patients = new ArrayList<>();
    protected int patientsNumber = 0;

    public void setId(String z1, String z2) {
        if (z1 == null || z2 == null) {
            if (z1 == null) {
                Id = Id.substring(0, 2) + z2 + Id.substring(6);
            } else {
                Id = z1 + Id.substring(2);
            }
        } else {
            Id = z1 + z2;

            Random random = new Random();
            String z3;
            do {
                int number = random.nextInt(1000);
                z3 = String.format("%03d", number);
            } while (IsNotNew(Id, z3));

            Id = Id + z3;
        }
    }

    public String IdToString(String id) {
        String[] Specialties = {
                "General Medicine", "Cardiology", "Pediatrics",
                "Obstetrics and Gynecology", "General Surgery",
                "Ophthalmology", "Neurology", "Dermatology and Cosmetology",
                "Pulmonology", "Otolaryngology", "Nephrology and Urology",
                "Oncology", "Dentistry", "Psychiatry", "Rheumatology"
        };

        String[] specialties = {
                "GM", "C0", "P0",
                "OG", "GS", "O0",
                "N0", "DC", "P1",
                "O1", "NU", "O2",
                "D0", "P2", "R0"
        };

        for (int i = 0; i < 15; i++) {
            if (id.startsWith(specialties[i])) {
                return ("a " + Specialties[i] + " began employment in " + id.substring(2, 6));
            }
        }
        return "";
    }

    public Boolean IsNotNew(String id, String z3) {
        for (Doctor doctor : Main.owner.doctors) {
            if (doctor.Id.equals(id + z3)) {
                return true;
            }
        }
        return false;
    }

    private void addNumericKeyListener(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignore non-digit input
                }
            }
        });
    }

    public void Return(boolean t) {
        App app = new App();
        Main.frame.getContentPane().removeAll();
        Main.frame.add(app.DocUser(this, t), BorderLayout.CENTER);
        Main.frame.revalidate();
        Main.frame.repaint();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JPanel user(boolean t) {
        JPanel userPanel = new JPanel(new GridBagLayout());
        userPanel.setBackground(Main.mainBg);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if (this.code == null || t) {
            // Create Password Section
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            JLabel CNTitle = new JLabel("<html><b><h2 style='color: #30B2AD;'>Create a Password</h2></b></html>");
            userPanel.add(CNTitle, gbc);

            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            JLabel CPassword = new JLabel("<html><b>Enter a password:</b></html>");
            userPanel.add(CPassword, gbc);

            gbc.gridx = 1;
            JTextField passwordField = new JTextField(20);
            passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
            userPanel.add(passwordField, gbc);

            gbc.gridy = 2;
            gbc.gridx = 0;
            JLabel password = new JLabel("<html><b>Confirm the password:</b></html>");
            userPanel.add(password, gbc);

            gbc.gridx = 1;
            JTextField passField = new JTextField(20);
            passField.setFont(new Font("Arial", Font.PLAIN, 14));
            userPanel.add(passField, gbc);

            gbc.gridy = 3;
            gbc.gridx = 1;
            JButton submit = new JButton("Submit");
            submit.setBackground(new Color(0xFF11FF00, true));
            submit.setForeground(Color.WHITE);
            submit.setFocusPainted(false);
            submit.addActionListener(e -> {
                if ((!passField.getText().isEmpty()) && Objects.equals(passField.getText(), passwordField.getText())) {
                    setCode(passwordField.getText());
                } else {
                    JOptionPane.showMessageDialog(Main.frame, "Something seems wrong. Try again.");
                }
                Return(false);
            });
            userPanel.add(submit, gbc);
        } else {
            // Login Section
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            JLabel CNTitle = new JLabel("<html><b><h2 style='color: #30B2AD;'>Hello " + this.fullName + "</h2></b></html>");
            userPanel.add(CNTitle, gbc);

            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            JLabel password = new JLabel("<html><b>Enter your password:</b></html>");
            userPanel.add(password, gbc);

            gbc.gridx = 1;
            JTextField passField = new JTextField(20);
            passField.setFont(new Font("Arial", Font.PLAIN, 14));
            userPanel.add(passField, gbc);

            gbc.gridy = 2;
            gbc.gridx = 1;
            JButton submit = new JButton("Submit");
            submit.setBackground(new Color(0xFF11FF00, true));
            submit.setForeground(Color.WHITE);
            submit.setFocusPainted(false);
            submit.addActionListener(e -> {
                String enteredPassword = passField.getText();
                if (Objects.equals(enteredPassword, this.code)) {
                    Main.frame.getContentPane().removeAll();
                    docNav();
                } else {
                    JOptionPane.showMessageDialog(Main.frame, "Invalid password. Please try again.");
                    passField.setText("");
                }
            });
            userPanel.add(submit, gbc);
        }

        return userPanel;
    }

    protected void docNav() {
        JPanel docPanel = new JPanel();
        docPanel.setLayout(new BoxLayout(docPanel, BoxLayout.X_AXIS));
        docPanel.setBackground(Main.mainBg);
        docPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeLabel = new JLabel("<html><b><h2 style='color: #30B2AD;'>Welcome, " + this.fullName + "!</h2></b></html>");
        welcomeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        docPanel.add(welcomeLabel);

        docPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        JButton doctorSettingsButton = createNavButton("See Your Information", e -> {
            Main.frame.getContentPane().removeAll();
            docNav();
            Main.frame.add(seeInfo(), BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        docPanel.add(doctorSettingsButton);

        docPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton patientSettingsButton = createNavButton("Update Your Password", e -> Return(true));
        docPanel.add(patientSettingsButton);

        docPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton changeCredentialsButton = createNavButton("Your Patients", e -> {
            Main.frame.getContentPane().removeAll();
            docNav();
            Main.frame.add(urPatNav(), BorderLayout.WEST);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        docPanel.add(changeCredentialsButton);

        docPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        docPanel.add(Box.createHorizontalGlue());

        JButton signOutButton = createNavButton("Sign Out", e -> {
            Main.frame.getContentPane().removeAll();
            Main.signOut();
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        signOutButton.setBackground(new Color(0xFF615D));
        docPanel.add(signOutButton);

        Main.frame.getContentPane().add(docPanel, BorderLayout.NORTH);
        Main.frame.revalidate();
        Main.frame.repaint();
    }

    protected JPanel seeInfo() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Main.mainBg.brighter());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        JLabel fullNameLabel = new JLabel("<html><b><h1 style='color: #30B2AD;'>" + this.fullName + "</h1></b></html>");
        infoPanel.add(fullNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        JLabel phoneLabel = new JLabel("<html><b>Phone:</b> " + this.phoneNumber + "</html>");
        infoPanel.add(phoneLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        JLabel ageLabel = new JLabel("<html><b>Age:</b> " + this.age + " years old</html>");
        infoPanel.add(ageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        JLabel idLabel = new JLabel(IdToHtml(this.Id));
        infoPanel.add(idLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        JLabel patientsLabel = new JLabel("<html><b>Patients:</b> " + this.patientsNumber + " patient" + (this.patientsNumber > 1 ? "s" : "") + "</html>");
        infoPanel.add(patientsLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        ImageIcon editImage = new ImageIcon("resources/edit.png");
        Image scaledEditImage = editImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton editIcon = new JButton(new ImageIcon(scaledEditImage));
        editIcon.setBackground(new Color(0xFF11FF00, true));
        editIcon.setFocusPainted(false);
        editIcon.setToolTipText("Edit Profile");
        editIcon.addActionListener(e -> editDoc());
        infoPanel.add(editIcon, gbc);

        return infoPanel;
    }

    protected void editDoc() {
        Main.frame.getContentPane().removeAll();
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Main.mainBg.brighter());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel remarqueLabel = new JLabel("<html><b><h3 style='color: #30B2AD;'>If the old information is correct, leave the field empty.</h3></b></html>");
        panel.add(remarqueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("<html><b>Enter the name:</b></html>");
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        JTextField fullNameField = new JTextField(20);
        fullNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel phoneLabel = new JLabel("<html><b>Enter the phone number:</b></html>");
        panel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        JTextField phoneField = new JTextField(20);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        panel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel ageLabel = new JLabel("<html><b>Enter the age:</b></html>");
        panel.add(ageLabel, gbc);

        gbc.gridx = 1;
        JTextField ageField = new JTextField(20);
        ageField.setFont(new Font("Arial", Font.PLAIN, 14));
        ageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        panel.add(ageField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        JButton submitButton = new JButton("Submit Changes");
        submitButton.setBackground(new Color(0xFF11FF00, true));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> {
            if (!fullNameField.getText().isEmpty()) {
                this.fullName = fullNameField.getText();
            }
            if (!phoneField.getText().isEmpty()) {
                this.phoneNumber = phoneField.getText();
            }
            if (!ageField.getText().isEmpty()) {
                this.age = ageField.getText();
            }

            Main.frame.getContentPane().removeAll();
            docNav();
            Main.frame.add(seeInfo(), BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        panel.add(submitButton, gbc);

        Main.frame.add(panel, BorderLayout.CENTER);
        Main.frame.revalidate();
        Main.frame.repaint();
    }

    protected String IdToHtml(String id) {
        String[] Specialties = {
                "General Medicine", "Cardiology", "Pediatrics",
                "Obstetrics and Gynecology", "General Surgery",
                "Ophthalmology", "Neurology", "Dermatology and Cosmetology",
                "Pulmonology", "Otolaryngology", "Nephrology and Urology",
                "Oncology", "Dentistry", "Psychiatry", "Rheumatology"
        };

        String[] specialties = {
                "GM", "C0", "P0",
                "OG", "GS", "O0",
                "N0", "DC", "P1",
                "O1", "NU", "O2",
                "D0", "P2", "R0"
        };

        for (int i = 0; i < 15; i++) {
            if (id.startsWith(specialties[i])) {
                return "<html><b>Specialty:</b> <h2 style='color: #2E86C1;'>" + Specialties[i] + "</h2>" +
                        "<b>Employment Start:</b> <h2 style='color: #2E86C1;'>" + id.substring(2, 6) + "</h2></html>";
            }
        }
        return "";
    }

    protected JPanel urPatNav() {
        JPanel yourPatSet = new JPanel();
        yourPatSet.setLayout(new BoxLayout(yourPatSet, BoxLayout.PAGE_AXIS));
        yourPatSet.setBackground(Main.mainBg);
        yourPatSet.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("<html><b><h2 style='color: #30B2AD;'>Your Patient Settings</h2></b></html>");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        yourPatSet.add(titleLabel);

        yourPatSet.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton addPatientButton = createNavButton("Add Patient", e -> {
            Main.frame.getContentPane().removeAll();
            docNav();
            Main.frame.add(urPatNav(), BorderLayout.WEST);
            Main.frame.getContentPane().add(addPat(0, null), BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        yourPatSet.add(addPatientButton);

        yourPatSet.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton viewPatientsButton = createNavButton("View All Patients", e -> {
            Main.frame.getContentPane().removeAll();
            docNav();
            Main.frame.add(urPatNav(), BorderLayout.WEST);
            Main.frame.getContentPane().add(Main.owner.viewAllPats(this), BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        viewPatientsButton.setEnabled(!patients.isEmpty());
        yourPatSet.add(viewPatientsButton);

        yourPatSet.add(Box.createVerticalGlue());

        JButton backButton = createNavButton("Back", e -> urPatNav());
        backButton.setBackground(Main.signOut_back);
        yourPatSet.add(backButton);

        yourPatSet.add(Box.createRigidArea(new Dimension(0, 20)));

        return yourPatSet;
    }

    private JButton createNavButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 30));
        button.setBackground(Main.mainBtn);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.addActionListener(action);
        return button;
    }

    protected JPanel addPat(int i, Patient patient) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Main.mainBg);

        Color ended = new Color(0x53BA61);
        Color current = new Color(0x82DBEF);
        Color next = new Color(0xFF0000);

        JPanel stages = new JPanel();
        stages.setLayout(new BoxLayout(stages, BoxLayout.PAGE_AXIS));
        stages.setBackground(next);
        stages.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel personalInformation = createStagePanel(
                "Personal Information", "resources/businessman.png", i == 0 ? current : ended
        );
        stages.add(personalInformation);

        JPanel medicalInformation = createStagePanel(
                "Medical Information", "resources/medical.png", i == 1 ? current : (i == 2 ? ended : next)
        );
        stages.add(medicalInformation);

        JPanel saveInformation = createStagePanel(
                "Save Information", "resources/bookmark.png", i == 2 ? current : next
        );
        stages.add(saveInformation);

        panel.add(stages, BorderLayout.WEST);
        panel.add(patStageForme(personalInformation, medicalInformation, saveInformation, patient), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStagePanel(String title, String iconPath, Color backgroundColor) {
        JPanel stagePanel = new JPanel();
        stagePanel.setLayout(new BoxLayout(stagePanel, BoxLayout.X_AXIS));
        stagePanel.setPreferredSize(new Dimension(200, 50));
        stagePanel.setMaximumSize(new Dimension(200, 50));
        stagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        stagePanel.setBackground(backgroundColor);
        stagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel titleLabel = new JLabel("   " + title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        stagePanel.add(titleLabel);

        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));
        stagePanel.add(iconLabel);

        return stagePanel;
    }

    protected JPanel patStageForme(JPanel PI, JPanel MI, JPanel SI, Patient patient) {
        Patient tempPat = patient == null ? new Patient() : patient;
        JPanel panel = new JPanel();
        panel.setBackground(Main.mainBg.brighter());
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        if (Objects.equals(PI.getBackground(), new Color(0x82DBEF))) {
            JLabel FName = new JLabel("enter the Patient full name");
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(FName, gbc);

            JTextField FNameField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(FNameField, gbc);

            JLabel age = new JLabel("enter the Patient age : ");
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(age, gbc);

            JTextField ageField = new JTextField(15);
            addNumericKeyListener(ageField);
            gbc.gridx = 1;
            panel.add(ageField, gbc);

            JLabel PNumber = new JLabel("enter the Patient phone number : ");
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(PNumber, gbc);

            JTextField PNumberField = new JTextField(15);
            addNumericKeyListener(PNumberField);
            gbc.gridx = 1;
            panel.add(PNumberField, gbc);

            JLabel code = new JLabel("enter a password for the  patient to use : ");
            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(code, gbc);

            JTextField codeField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(codeField, gbc);

            JButton submitButton = new JButton("Next");
            gbc.gridx = 4;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            submitButton.addActionListener(e -> {
                boolean emptyTest = (FNameField.getText().isEmpty() || ageField.getText().isEmpty() || PNumberField.getText().isEmpty() || codeField.getText().isEmpty());
                boolean validTest = !(ageField.getText().matches("\\d+") && PNumberField.getText().matches("\\d+"));
                if (emptyTest || validTest) {
                    JOptionPane.showMessageDialog(Main.frame, "Something seems wrong. Try again.");
                    Main.frame.getContentPane().removeAll();
                    docNav();
                    Main.frame.add(urPatNav(), BorderLayout.WEST);
                    Main.frame.add(addPat(0, null), BorderLayout.CENTER);
                } else {
                    tempPat.fullName = FNameField.getText();
                    tempPat.age = ageField.getText();
                    tempPat.phoneNumber = PNumberField.getText();
                    tempPat.code = codeField.getText();
                    Main.frame.getContentPane().removeAll();
                    docNav();
                    Main.frame.add(urPatNav(), BorderLayout.WEST);
                    Main.frame.add(addPat(1, tempPat), BorderLayout.CENTER);
                }
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            panel.add(submitButton, gbc);
        } else if (Objects.equals(MI.getBackground(), new Color(0x82DBEF))) {
            JLabel msg = new JLabel("you can just pass if this is just a registration and there's no appointment");
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(msg, gbc);

            JLabel date = new JLabel("enter the Patient date : ");
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(date, gbc);

            JLabel day = new JLabel("day : ");
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(day, gbc);

            JTextField dayField = new JTextField(5);
            ((AbstractDocument) dayField.getDocument()).setDocumentFilter(new IntegerOnlyFilter());
            gbc.gridx = 1;
            panel.add(dayField, gbc);

            JLabel month = new JLabel("month(number) : ");
            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(month, gbc);

            JTextField monthField = new JTextField(5);
            ((AbstractDocument) monthField.getDocument()).setDocumentFilter(new IntegerOnlyFilter());
            gbc.gridx = 1;
            panel.add(monthField, gbc);

            JLabel year = new JLabel("year : ");
            gbc.gridx = 0;
            gbc.gridy = 4;
            panel.add(year, gbc);

            JTextField yearField = new JTextField(5);
            ((AbstractDocument) yearField.getDocument()).setDocumentFilter(new IntegerOnlyFilter());
            gbc.gridx = 1;
            panel.add(yearField, gbc);


            JButton submitButton = new JButton("Next");
            gbc.gridx = 1;
            gbc.gridy = 7;
            gbc.gridwidth = 2;
            submitButton.addActionListener(e -> {
                if (dayField.getText().isEmpty() && monthField.getText().isEmpty() && yearField.getText().isEmpty() ) {
                    Main.frame.getContentPane().removeAll();
                    docNav();
                    Main.frame.add(urPatNav(), BorderLayout.WEST);
                    Main.frame.add(addPat(2, tempPat), BorderLayout.CENTER);
                } else if (dayField.getText().isEmpty() || monthField.getText().isEmpty() || yearField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(Main.frame, "Something seems wrong. Try again.");
                    Main.frame.getContentPane().removeAll();
                    docNav();
                    Main.frame.add(urPatNav(), BorderLayout.WEST);
                    Main.frame.add(addPat(1, patient), BorderLayout.CENTER);
                } else {
                    tempPat.appointment = new MedicalHistory(this);
                    tempPat.appointment.doc = this;
                    tempPat.patDoc = this;
                    try {
                        int day1 = Integer.parseInt(dayField.getText());
                        int month1 = Integer.parseInt(monthField.getText());
                        int year1 = Integer.parseInt(yearField.getText());

                        tempPat.appointment.date = new Dmy(day1, month1, year1);
                    } catch (NumberFormatException c) {
                        System.out.println("Invalid input: Please enter numeric values for day, month, year, and hour.");
                    } catch (Exception c) {
                        System.out.println("An error occurred: " + c.getMessage());
                    }
                    Main.frame.getContentPane().removeAll();
                    docNav();
                    Main.frame.add(urPatNav(), BorderLayout.WEST);
                    Main.frame.add(addPat(2, tempPat), BorderLayout.CENTER);
                }
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            panel.add(submitButton, gbc);
        } else {
            assert patient != null;
            JLabel FName = new JLabel(patient.toString());
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(FName, gbc);

            JButton saveButton = new JButton("save");
            gbc.gridx = 4;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            saveButton.addActionListener(e -> {
                Main.owner.patients.add(patient);
                patients.add(patient);
                patient.patDoc = this;
                this.phoneNumber += 1;
                Main.frame.getContentPane().removeAll();
                docNav();
                Main.frame.add(urPatNav(), BorderLayout.WEST);
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            panel.add(saveButton, gbc);
        }

        return panel;
    }

    static class IntegerOnlyFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string.matches("\\d+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
            if (string.matches("\\d+")) {
                super.replace(fb, offset, length, string, attr);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }

    @Override
    public String toString() {
        return (this.fullName + ", " + this.phoneNumber + ", " + this.age + " years old, \n" + IdToString(Id));
    }
}
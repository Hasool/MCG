import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

public class Patient extends Human{
    protected MedicalHistory appointment;
    protected ArrayList<MedicalHistory> futureAppointment=new ArrayList<>();
    protected ArrayList<MedicalHistory> medicalHistories=new ArrayList<>();
    protected Doctor patDoc = null;
    protected String code;


    protected boolean isAppointment(){
        return this.appointment != null;
    }//just boolean fun

    public void user() {

        appointment = findNextAppointment(futureAppointment);
        Main.frame.getContentPane().removeAll();
        JPanel patPanel = new JPanel();
        patPanel.setLayout(new BoxLayout(patPanel, BoxLayout.X_AXIS));
        patPanel.setBackground(Main.mainBg);

        patPanel.add(Box.createRigidArea(new Dimension(10, 50)));

        JLabel welcomeLabel = new JLabel("Welcome, "+this.fullName+"!");
        welcomeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(Main.mainBtn);
        patPanel.add(welcomeLabel);

        patPanel.add(Box.createRigidArea(new Dimension(50, 50)));

        JButton SeeInfo = new JButton("see your information");
        SeeInfo.setAlignmentY(Component.CENTER_ALIGNMENT);
        SeeInfo.setMaximumSize(new Dimension(200, 30));
        SeeInfo.setBackground(Main.mainBtn);
        SeeInfo.addActionListener(e-> {
            Main.frame.getContentPane().removeAll();
            user();
            Main.frame.add(seeInfo(), BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        patPanel.add(SeeInfo);

        patPanel.add(Box.createRigidArea(new Dimension(30, 0)));

        JButton appBtn = new JButton("your next appointment");
        appBtn.setEnabled(isAppointment());
        appBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
        appBtn.setMaximumSize(new Dimension(200, 30));
        appBtn.setBackground(Main.mainBtn);
        appBtn.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Main.frame.getContentPane().add(patPanel, BorderLayout.NORTH);
            Main.frame.add(nextApp(appointment),BorderLayout.WEST);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        patPanel.add(appBtn);


        patPanel.add(Box.createRigidArea(new Dimension(30, 0)));

        JButton fAppBtn = new JButton("your future appointment");
        fAppBtn.setEnabled(!futureAppointment.isEmpty() || futureAppointment.size()<=1);
        fAppBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
        fAppBtn.setMaximumSize(new Dimension(200, 30));
        fAppBtn.setBackground(Main.mainBtn);
        fAppBtn.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Main.frame.getContentPane().add(patPanel, BorderLayout.NORTH);
            Main.frame.add(nextApps(),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        patPanel.add(fAppBtn);


        patPanel.add(Box.createRigidArea(new Dimension(30, 0)));

        JButton MhBtn = new JButton("your Medical History");
        MhBtn.setEnabled(!medicalHistories.isEmpty());
        MhBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
        MhBtn.setMaximumSize(new Dimension(200, 30));
        MhBtn.setBackground(Main.mainBtn);
        MhBtn.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Main.frame.getContentPane().add(patPanel, BorderLayout.NORTH);
            Main.frame.add(prevApps(),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        patPanel.add(MhBtn);

        patPanel.add(Box.createHorizontalGlue());

        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        signOutButton.setFocusable(false);
        signOutButton.setMaximumSize(new Dimension(200, 30));
        signOutButton.setBackground(Main.signOut_back);
        signOutButton.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Main.signOut();
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        patPanel.add(signOutButton);

        patPanel.add(Box.createRigidArea(new Dimension(20, 50)));

        Main.frame.getContentPane().add(patPanel, BorderLayout.NORTH);
        Main.frame.revalidate();
        Main.frame.repaint();
    }


    protected JPanel nextApp(MedicalHistory a) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Main.mainBg);

        panel.add(Box.createRigidArea(new Dimension(400, 20)));

        JLabel label = new JLabel(a.toString());
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(Main.mainBtn);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        JButton moreInfo = new JButton("<html><center>Read more about<br>Dr. " + a.doc.fullName + "</center></html>");
        moreInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        moreInfo.setMaximumSize(new Dimension(200,70));
        moreInfo.setBackground(Main.mainBtn);
        moreInfo.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            user();
            Main.frame.add(nextApp(a), BorderLayout.WEST);
            Main.frame.add(more(a.doc), BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        panel.add(moreInfo);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton chA = new JButton("<html><center>Ask to change the date<br>of the appointment</center></html>");
        chA.setAlignmentX(Component.CENTER_ALIGNMENT);
        chA.setMaximumSize(new Dimension(200, 70));
        chA.setBackground(Main.mainBtn);
        chA.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            user();
            Main.frame.add(nextApp(a), BorderLayout.WEST);
            Main.frame.add(chA(), BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        panel.add(chA);

        return panel;
    }

    protected JScrollPane prevApps() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Main.mainBg.brighter()); // Brighter background for contrast
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased padding
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;

        // Fixed size for each medical history panel
        Dimension fixedSize = new Dimension(300, 200);

        for (int i = 0; i < medicalHistories.size(); i++) {
            MedicalHistory history = medicalHistories.get(i);
            boolean noDiagnosis = history.medicalDiagnosis == null || history.medicalDiagnosis.isEmpty() || history.medicalDiagnosis.equals("n");
            boolean noMedicine = history.medicine == null || history.medicine.isEmpty() || history.medicine.equals("n");

            // Create the label with formatted HTML text
            String toLabel = String.format(
                    "<html><div style='text-align: center;'>" +
                            "<b>Date:</b> %s<br>" +
                            "<b>Diagnosis:</b> %s<br>" +
                            "<b>Medicine:</b> %s" +
                            "</div></html>",
                    history.toString(),
                    noDiagnosis ? "No information" : history.medicalDiagnosis,
                    noMedicine ? "No information" : history.medicine
            );

            JLabel label = new JLabel(toLabel);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setFont(new Font("Arial", Font.PLAIN, 12)); // Consistent font

            // Create a panel for each medical history
            JPanel panelOfReq = new JPanel(new BorderLayout());
            panelOfReq.setBackground(Main.secondBg); // Light purple background
            panelOfReq.setPreferredSize(fixedSize);
            panelOfReq.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Add a border
            panelOfReq.add(label, BorderLayout.CENTER);

            // Add the panel to the grid
            gbc.gridx = i % 3; // 3 columns
            gbc.gridy = i / 3;
            panel.add(panelOfReq, gbc);
        }

        // Wrap the panel in a scroll pane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(Main.mainBg.brighter()); // Match background

        return scrollPane;
    }

    protected JScrollPane nextApps() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Main.mainBg.brighter());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;


        Dimension fixedSize = new Dimension(300, 200);

        for (int i = 0; i < futureAppointment.size(); i++) {
            JLabel label = new JLabel(futureAppointment.get(i).toString());
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.TOP);

            JPanel panelOfReq = new JPanel();
            panelOfReq.setLayout(new BorderLayout());
            panelOfReq.setBackground(Main.secondBg);
            panelOfReq.setPreferredSize(fixedSize);

            panelOfReq.add(label, BorderLayout.CENTER);

            JButton btn = new JButton("more");
            btn.setBackground(Main.mainBtn);
            int finalI = i;
            btn.addActionListener(e -> {
                Main.frame.getContentPane().removeAll();
                user();
                Main.frame.add(nextApp(futureAppointment.get(finalI)),BorderLayout.WEST);
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            panelOfReq.add(btn, BorderLayout.NORTH);

            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            panel.add(panelOfReq, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }



    protected JPanel more(Doctor doctor) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(Main.mainBg.brighter());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Doctor's Full Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel fullNameLabel = new JLabel("<html><b><h1 style='color: #30B2AD;'>" + doctor.fullName + "</h1></b></html>");
        infoPanel.add(fullNameLabel, gbc);

        // Phone Number
        gbc.gridy = 1;
        JLabel phoneLabel = new JLabel("<html><b>Phone:</b> " + doctor.phoneNumber + "</html>");
        infoPanel.add(phoneLabel, gbc);

        // Age
        gbc.gridy = 2;
        JLabel ageLabel = new JLabel("<html><b>Age:</b> " + doctor.age + " years old</html>");
        infoPanel.add(ageLabel, gbc);

        // ID and Specialties
        gbc.gridy = 3;
        JLabel idLabel = new JLabel(doctor.IdToHtml(doctor.Id));
        infoPanel.add(idLabel, gbc);

        // Number of Patients
        gbc.gridy = 4;
        JLabel patientsLabel = new JLabel("<html><b>Patients:</b> " + doctor.patientsNumber + " patient" + (doctor.patientsNumber > 1 ? "s" : "") + "</html>");
        infoPanel.add(patientsLabel, gbc);

        // Request to Change Doctor Button
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        JButton editIcon = new JButton("Request to Change Doctor");
        editIcon.setBackground(new Color(0xFF11FF00, true));
        editIcon.setForeground(Color.WHITE); // White text
        editIcon.setFocusPainted(false); // Remove focus border
        editIcon.addActionListener(e -> {
            JLabel w = new JLabel("<html>the patient :" + fullName+","+ code + "; asks you to change his doctor (" + doctor.fullName + ", " + doctor.Id + ")</html>");
            Main.owner.requests.add(w);
            JOptionPane.showMessageDialog(Main.frame, "The request has been sent.");
        });
        infoPanel.add(editIcon, gbc);


        return infoPanel;
    }

    protected JPanel chA() {
        JPanel changingPanel = new JPanel();
        changingPanel.setLayout(new GridBagLayout());
        changingPanel.setBackground(Main.mainBg.brighter());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        JLabel fullNameLabel = new JLabel("Enter the new date for your appointment:");
        fullNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        changingPanel.add(fullNameLabel, gbc);

        // Labels for Day, Month, Year
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        JLabel dayLabel = new JLabel("Day:");
        dayLabel.setForeground(Main.mainBtn);
        gbc.gridx = 0;
        changingPanel.add(dayLabel, gbc);

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setForeground(Main.mainBtn);
        gbc.gridx = 1;
        changingPanel.add(monthLabel, gbc);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setForeground(Main.mainBtn);
        gbc.gridx = 2;
        changingPanel.add(yearLabel, gbc);

        // Input Fields for Day, Month, Year
        gbc.gridy = 2;

        JTextField dayField = new JTextField(5);
        gbc.gridx = 0;
        changingPanel.add(dayField, gbc);

        JTextField monthField = new JTextField(5);
        gbc.gridx = 1;
        changingPanel.add(monthField, gbc);

        JTextField yearField = new JTextField(5);
        gbc.gridx = 2;
        changingPanel.add(yearField, gbc);

        // Submit Button
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 3;

        JButton submit = new JButton("Submit Changes");
        submit.setBackground(Main.mainBtn);
        submit.addActionListener(e -> {
            try {
                int day = Integer.parseInt(dayField.getText());
                int month = Integer.parseInt(monthField.getText());
                int year = Integer.parseInt(yearField.getText());
                Dmy newDate = new Dmy(day, month, year);

                JLabel requestMessage =new JLabel("<html>"+fullName + " requests to change the appointment from " +
                        appointment.date.toString() + " to " + newDate.toString()+"</html>");
                Main.owner.requests.add(requestMessage);
                JOptionPane.showMessageDialog(Main.frame, "Request has been sent.");
                Main.frame.getContentPane().removeAll();
                user();
                Main.frame.revalidate();
                Main.frame.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Main.frame, "Invalid date entered. Please try again.");
            } catch (Exception ex) {
                System.out.println("An error occurred: " + ex.getMessage());
            }
        });
        changingPanel.add(submit, gbc);

        return changingPanel;
    }



    protected JPanel seeInfo() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(Main.mainBg); // Set background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel fullNameLabel = new JLabel("<html><b><h1 style='color: #30B2AD;'>" + this.fullName + "</h1></b></html>");
        infoPanel.add(fullNameLabel, gbc);

        // Phone Number
        gbc.gridy = 1;
        JLabel phoneLabel = new JLabel("<html><b>Phone:</b> " + this.phoneNumber + "</html>");
        infoPanel.add(phoneLabel, gbc);

        // Age
        gbc.gridy = 2;
        JLabel ageLabel = new JLabel("<html><b>Age:</b> " + this.age + " years old</html>");
        infoPanel.add(ageLabel, gbc);

        // Edit Button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        ImageIcon editImage = new ImageIcon("resources/edit.png");
        Image scaledEditImage = editImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton editIcon = new JButton(new ImageIcon(scaledEditImage));
        editIcon.setBackground(new Color(0xFF11FF00, true));
        editIcon.setFocusPainted(false); // Remove focus border
        editIcon.setToolTipText("Edit Profile"); // Add tooltip
        editIcon.addActionListener(e -> editPat(0));
        infoPanel.add(editIcon, gbc);

        return infoPanel;
    }



    protected void editPat(int i) {
        Main.frame.getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Main.mainBg); // Set background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Instruction Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel remarqueLabel = new JLabel("<html><b><h3 style='color: #30B2AD;'>If the old information is correct, leave the field empty.</h3></b></html>");
        panel.add(remarqueLabel, gbc);

        // Full Name Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("<html><b>Enter the name:</b></html>");
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        JTextField fullNameField = new JTextField(20);
        fullNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(fullNameField, gbc);

        // Phone Number Field
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

        // Age Field (with input validation)
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel ageLabel = new JLabel("<html><b>Enter the age:</b></html>");
        panel.add(ageLabel, gbc);

        gbc.gridx = 1;
        JTextField ageField = new JTextField(20);
        ageField.setFont(new Font("Arial", Font.PLAIN, 14));
        // Input validation: Allow only numbers
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

        // Submit Button
        gbc.gridx = 1;
        gbc.gridy = 4;
        JButton submitButton = new JButton("Submit Changes");
        submitButton.setBackground(new Color(0xFF11FF00, true)); // Green background
        submitButton.setForeground(Color.WHITE); // White text
        submitButton.setFocusPainted(false); // Remove focus border
        submitButton.addActionListener(e -> {
            // Update fields if they are not empty
            if (!fullNameField.getText().isEmpty()) {
                this.fullName = fullNameField.getText();
            }
            if (!phoneField.getText().isEmpty()) {
                this.phoneNumber = phoneField.getText();
            }
            if (!ageField.getText().isEmpty()) {
                this.age = ageField.getText();
            }

            // Refresh the UI based on the context
            Main.frame.getContentPane().removeAll();
            if (i == 0) {
                user();
                Main.frame.add(seeInfo(), BorderLayout.CENTER);
            } else if (i == 1) {
                Main.owner.Owners(true);
                Main.frame.add(Main.owner.editPatient(this, null), BorderLayout.WEST);
            } else if (patDoc != null) {
                patDoc.docNav();
                Main.frame.add(Main.owner.editPatient(this, patDoc), BorderLayout.WEST);
            }
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        panel.add(submitButton, gbc);

        // Add the panel to the frame
        Main.frame.add(panel, BorderLayout.CENTER);
        Main.frame.revalidate();
        Main.frame.repaint();
    }


    public static Doctor getDocFromId(String id){
        for (Doctor doctor : Main.owner.doctors){
            if (Objects.equals(doctor.Id,id)){
                return doctor;
            }
        }
        return null ;
    }

    public static MedicalHistory findNextAppointment(ArrayList<MedicalHistory> medicalHistories) {
        if (medicalHistories == null || medicalHistories.isEmpty()) {
            return null ;
        }

        LocalDate now = LocalDate.now();
        MedicalHistory nextAppointment = medicalHistories.getFirst();
        long minDifference = Math.abs(ChronoUnit.DAYS.between(now, nextAppointment.date.getDmy()));

        for (MedicalHistory MH : medicalHistories) {
            long difference = Math.abs(ChronoUnit.DAYS.between(now, MH.date.getDmy()));
            if (difference < minDifference) {
                minDifference = difference;
                nextAppointment = MH;
            }
        }

        return nextAppointment;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Phone: %s, Age: %s", this.fullName, this.phoneNumber, this.age);
    }
}

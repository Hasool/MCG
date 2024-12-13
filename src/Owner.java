
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;


public class Owner {
    protected ArrayList<Doctor> doctors ;
    protected ArrayList<Patient> patients ;
    private String password;
    private String name;

    public Owner(){
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        name = "admin";
        password = "admin";
    }


    public void Owners(boolean isLoggedIn) {
        Main.frame.getContentPane().removeAll();

        if (isLoggedIn) {
            // Owner Panel on the left
            JPanel ownerPanel = new JPanel();
            ownerPanel.setLayout(new BoxLayout(ownerPanel, BoxLayout.X_AXIS));
            ownerPanel.setBackground(new Color(0x98D1FA));

            ownerPanel.add(Box.createRigidArea(new Dimension(10, 50)));

            JLabel welcomeLabel = new JLabel("Welcome, Owner!");
            welcomeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            ownerPanel.add(welcomeLabel);

            ownerPanel.add(Box.createRigidArea(new Dimension(50, 50)));

            JButton doctorSettingsButton = new JButton("Doctor Settings");
            doctorSettingsButton.setAlignmentY(Component.CENTER_ALIGNMENT);
            doctorSettingsButton.setMaximumSize(new Dimension(200, 30));
            doctorSettingsButton.addActionListener(e -> {
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(DocSettings(), BorderLayout.WEST);
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            ownerPanel.add(doctorSettingsButton);

            ownerPanel.add(Box.createRigidArea(new Dimension(30, 0)));

            JButton patientSettingsButton = new JButton("Patient Settings");
            patientSettingsButton.setAlignmentY(Component.CENTER_ALIGNMENT);
            patientSettingsButton.setMaximumSize(new Dimension(200, 30));
            patientSettingsButton.addActionListener(e -> PatientSettings());
            ownerPanel.add(patientSettingsButton);

            ownerPanel.add(Box.createRigidArea(new Dimension(30, 0)));

            JButton changeCredentialsButton = new JButton("Change Name/Password");
            changeCredentialsButton.setAlignmentY(Component.CENTER_ALIGNMENT);
            changeCredentialsButton.setMaximumSize(new Dimension(200, 30));
            changeCredentialsButton.addActionListener(e -> change());
            ownerPanel.add(changeCredentialsButton);

            ownerPanel.add(Box.createRigidArea(new Dimension(30, 0)));

            ownerPanel.add(Box.createHorizontalGlue());

            JButton signOutButton = new JButton("Sign Out");
            signOutButton.setAlignmentY(Component.CENTER_ALIGNMENT);
            signOutButton.setFocusable(false);
            signOutButton.setMaximumSize(new Dimension(200, 30));
            signOutButton.addActionListener(e -> Main.signOut());
            ownerPanel.add(signOutButton);

            ownerPanel.add(Box.createRigidArea(new Dimension(20, 50)));

            Main.frame.add(ownerPanel, BorderLayout.NORTH);
        } else {
            // Login Panel (Unchanged)
            JPanel loginPanel = new JPanel();
            loginPanel.setLayout(new GridBagLayout());
            loginPanel.setBackground(Color.lightGray);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            JLabel nameLabel = new JLabel("Enter your name:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            loginPanel.add(nameLabel, gbc);

            JTextField nameField = new JTextField(15);
            gbc.gridx = 1;
            loginPanel.add(nameField, gbc);

            JLabel passwordLabel = new JLabel("Enter your password:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            loginPanel.add(passwordLabel, gbc);

            JPasswordField passwordField = new JPasswordField(15);
            gbc.gridx = 1;
            loginPanel.add(passwordField, gbc);

            JButton submitButton = new JButton("Submit");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            submitButton.addActionListener(e -> {
                String inputName = nameField.getText();
                String inputPassword = new String(passwordField.getPassword());

                if (validateOwner(inputName, inputPassword)) {
                    Owners(true);
                } else {
                    JOptionPane.showMessageDialog(Main.frame, "Invalid credentials. Try again.");
                }
            });
            loginPanel.add(submitButton, gbc);

            Main.frame.add(loginPanel, BorderLayout.CENTER);
        }

        Main.frame.revalidate();
        Main.frame.repaint();
    }





    private boolean validateOwner(String inputName, String inputPassword) {
        return Objects.equals(inputName, name) && Objects.equals(inputPassword, password);
    }

    public JPanel DocSettings() {

        JPanel DocSettingsPanel = new JPanel();
        DocSettingsPanel.setLayout(new BoxLayout(DocSettingsPanel, BoxLayout.PAGE_AXIS));
        DocSettingsPanel.setBackground(new Color(0x98D1FA));

        DocSettingsPanel.add(Box.createRigidArea(new Dimension(250, 20)));

        JLabel titleLabel = new JLabel("Doctor Settings");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        DocSettingsPanel.add(titleLabel);

        DocSettingsPanel.add(Box.createRigidArea(new Dimension(250, 20)));

        JButton addDoctorButton = new JButton("Add Doctor");
        addDoctorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addDoctorButton.setMaximumSize(new Dimension(200, 30));
        addDoctorButton.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Owners(true);
            Main.frame.add(DocSettings(), BorderLayout.WEST);
            Main.frame.add(addDoc(0,null),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        DocSettingsPanel.add(addDoctorButton);

        DocSettingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton viewDoctorsButton = new JButton("View All Doctors");
        viewDoctorsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewDoctorsButton.setMaximumSize(new Dimension(200, 30));
        viewDoctorsButton.setEnabled(!doctors.isEmpty());
        viewDoctorsButton.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Owners(true);
            Main.frame.add(DocSettings(), BorderLayout.WEST);
            Main.frame.add(viewAllDocs(),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        DocSettingsPanel.add(viewDoctorsButton);

        DocSettingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton deleteDoctorButton = new JButton("Delete Doctor");
        deleteDoctorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteDoctorButton.setMaximumSize(new Dimension(200, 30));
        deleteDoctorButton.setEnabled(!doctors.isEmpty());
        deleteDoctorButton.addActionListener(e -> System.out.println("Delete Doctor"));
        DocSettingsPanel.add(deleteDoctorButton);

        DocSettingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton updateDoctorButton = new JButton("Update Doctor");
        updateDoctorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateDoctorButton.setMaximumSize(new Dimension(200, 30));
        updateDoctorButton.setEnabled(!doctors.isEmpty());
        updateDoctorButton.addActionListener(e -> System.out.println("Update Doctor"));
        DocSettingsPanel.add(updateDoctorButton);

        DocSettingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        DocSettingsPanel.add(Box.createVerticalGlue());

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(200, 30));
        backButton.addActionListener(e -> Owners(true));
        DocSettingsPanel.add(backButton);

        DocSettingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        return DocSettingsPanel;
    }




    protected JPanel addDoc(int i,Doctor doctor) {
        JPanel addDocPanel = new JPanel(new BorderLayout());

        // Colors for stages
        Color ended = new Color(0x53BA61);
        Color current = new Color(0x82DBEF);
        Color next = new Color(0xFF0000);


        JPanel stages = new JPanel();
        stages.setBackground(next);
        stages.setLayout(new BoxLayout(stages, BoxLayout.PAGE_AXIS));


        JPanel personalInformation = new JPanel();
        personalInformation.setLayout(new BoxLayout(personalInformation, BoxLayout.X_AXIS));
        personalInformation.setPreferredSize(new Dimension(200,50));
        personalInformation.setMaximumSize(new Dimension(200, 50));
        personalInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
        personalInformation.setBackground(i==0?current:ended);

        JLabel PI = new JLabel("   Personal Information");
        ImageIcon PIImage = new ImageIcon("resources/businessman.png");
        Image scaledPIImage = PIImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon PIImageIcon = new ImageIcon(scaledPIImage);
        JLabel PIIcon = new JLabel(PIImageIcon);
        personalInformation.add(PI);
        personalInformation.add(PIIcon);

        stages.add(personalInformation);

        JPanel professionalInformation = new JPanel();
        professionalInformation.setLayout(new BoxLayout(professionalInformation, BoxLayout.X_AXIS));
        professionalInformation.setPreferredSize(new Dimension(200,50));
        professionalInformation.setMaximumSize(new Dimension(200, 50));
        professionalInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
        professionalInformation.setBackground(i==1?current:(i==2?ended:next));

        JLabel PrI = new JLabel("   professional Information");
        ImageIcon PrIImage = new ImageIcon("resources/expertise.png");
        Image scaledPrIImage = PrIImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon PrIImageIcon = new ImageIcon(scaledPrIImage);
        JLabel PrIIcon = new JLabel(PrIImageIcon);
        professionalInformation.add(PrI);
        professionalInformation.add(PrIIcon);
        stages.add(professionalInformation);

        JPanel saveInformation = new JPanel();
        saveInformation.setLayout(new BoxLayout(saveInformation , BoxLayout.X_AXIS));
        saveInformation.setPreferredSize(new Dimension(200,50));
        saveInformation.setMaximumSize(new Dimension(200, 50));
        saveInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveInformation.setBackground(i==2?current:next);

        JLabel SI = new JLabel("   save Information");
        ImageIcon SIImage = new ImageIcon("resources/bookmark.png");
        Image scaledSIImage = SIImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon SIImageIcon = new ImageIcon(scaledSIImage);
        JLabel SIIcon = new JLabel(SIImageIcon);
        SIIcon.setAlignmentX(Component.RIGHT_ALIGNMENT);
        saveInformation.add(SI);
        saveInformation.add(SIIcon);
        stages.add(saveInformation);

        addDocPanel.add(stages,BorderLayout.WEST);
        addDocPanel.add(stageForme(personalInformation,professionalInformation,saveInformation,doctor),BorderLayout.CENTER);

        return addDocPanel;

    }

    protected JPanel stageForme(JPanel PI,JPanel PrI,JPanel SI,Doctor doctor) {
        Doctor tempDoc = doctor==null?new Doctor():doctor;
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        if (Objects.equals(PI.getBackground(), new Color(0x82DBEF))){
            JLabel FName = new JLabel("enter the doctor full name : ");
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(FName, gbc);

            JTextField FNameField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(FNameField, gbc);


            JLabel age = new JLabel("enter the doctor age : ");
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(age, gbc);

            JTextField ageField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(ageField, gbc);


            JLabel PNumber = new JLabel("enter the doctor phone number : ");
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(PNumber, gbc);

            JTextField PNumberField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(PNumberField, gbc);


            JButton submitButton = new JButton("Next");
            gbc.gridx = 4;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            submitButton.addActionListener(e -> {
                if (FNameField.getText().isEmpty() || ageField.getText().isEmpty() || PNumberField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(Main.frame, "somthing seem wrong. Try again.");
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(DocSettings(), BorderLayout.WEST);
                    Main.frame.add(addDoc(0,null),BorderLayout.CENTER);
                    Main.frame.revalidate();
                    Main.frame.repaint();
                }else {
                    tempDoc.fullName = FNameField.getText();
                    tempDoc.age = ageField.getText();
                    tempDoc.phoneNumber = PNumberField.getText();
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(DocSettings(), BorderLayout.WEST);
                    Main.frame.add(addDoc(1,tempDoc),BorderLayout.CENTER);
                    Main.frame.revalidate();
                    Main.frame.repaint();
                }

            });

            panel.add(submitButton, gbc);
        }
        else if (Objects.equals(PrI.getBackground(), new Color(0x82DBEF))) {
            String[] buttonLabels = {
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

            JFrame frame = new JFrame("Medical Specialties");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 300);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

            final JButton[] lastClickedButton = {null};
            final String[] z1 = new String[1];

            for (int i = 0; i < buttonLabels.length; i++) {
                String buttonText = buttonLabels[i];
                String specialtyCode = specialties[i];

                JButton button = new JButton(buttonText);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        z1[0] =specialtyCode;

                        if (lastClickedButton[0] != null) {
                            lastClickedButton[0].setEnabled(true);
                        }

                        button.setEnabled(false);
                        lastClickedButton[0] = button;
                    }
                });

                buttonPanel.add(button);
            }

            JScrollPane scrollPane = new JScrollPane(buttonPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            panel.add(scrollPane);


            JLabel enterYear = new JLabel("Enter the year of employment : ");
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(enterYear, gbc);

            JTextField enterYearField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(enterYearField, gbc);

            JButton submitButton = new JButton("Next");
            gbc.gridx = 4;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            submitButton.addActionListener(e -> {

                if (enterYearField.getText().length()!=4){
                    JOptionPane.showMessageDialog(Main.frame, "Enter a valid year. Try again.");
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(DocSettings(), BorderLayout.WEST);
                    Main.frame.add(addDoc(1,tempDoc),BorderLayout.CENTER);
                    Main.frame.revalidate();
                    Main.frame.repaint();
                }else {
                    tempDoc.setId(z1[0],enterYearField.getText());
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(DocSettings(), BorderLayout.WEST);
                    Main.frame.add(addDoc(2,tempDoc),BorderLayout.CENTER);
                    Main.frame.revalidate();
                    Main.frame.repaint();
                }

            });

            panel.add(submitButton, gbc);
        }
        else {
            JLabel FName = new JLabel(tempDoc.toString());
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(FName, gbc);

            JButton saveButton = new JButton("Next");
            gbc.gridx = 4;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            saveButton.addActionListener(e -> {
                Main.owner.doctors.add(tempDoc);
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(DocSettings(), BorderLayout.WEST);
                Main.frame.revalidate();
                Main.frame.repaint();
            });

            panel.add(saveButton, gbc);
        }


        return panel;
    }

    protected JPanel viewAllDocs() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Doctor doctor : Main.owner.doctors) {
            JLabel text = new JLabel(doctor.toString());
            panel.add(text);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(0));

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(navPanel, BorderLayout.WEST);

        return container;
    }





    /*public void DocSettings() {
        System.out.println("hello in doctors settings" + "\n0: exit to owner page" + "\n1: add a doctor" +
                (this.doctors.isEmpty()
                        ?""
                        :("\n2: see all doctors\n3: delete a doctor\n4: update a doctor")
                ));

        int c;
        do {
            c = Main.reader.nextByte();
        }while (c<0||c>4);

        if (c==2 && !doctors.isEmpty()) {
            for (Doctor doctor : doctors){
                System.out.println(doctor.toString());
            }
        } else if (c==3 && !doctors.isEmpty()) {
            System.out.println("enter doctor name");
            String tempName = Main.reader.next();

            System.out.println("enter doctor id");
            String tempID = Main.reader.next();

            int i = doctors.size();
            for (Doctor doctor : doctors){
                if ((doctor.fullName.equals(tempName)) && (doctor.Id.equals(tempID))){
                    System.out.println( doctor.toString() + " , is deleted");
                    doctors.remove(doctor);
                    break;
                }
            }
            if (i==doctors.size()){
                System.out.println("there's no doctor with those information");
            }
        } else if (c==4 && !doctors.isEmpty()) {
            System.out.println("enter doctor name");
            String tempName = Main.reader.next();

            System.out.println("enter doctor id");
            String tempID = Main.reader.next();

            for (Doctor doctor : doctors){
                if ((doctor.fullName.equals(tempName)) && (doctor.Id.equals(tempID))){
                    System.out.println( doctor.toString() + " , founded " +
                            "\n1: add a patient" +
                            "\n2: update personal info");
                }
            }
            System.out.println("there's no doctor with those information");
        }
        Owners(true);
    }*/


    public void PatientSettings() {
        System.out.println("hello in patient settings" + "\n0: exit to owner page" + "\n1: add a patient" +
                (this.patients.isEmpty()
                        ?""
                        :("\n2: see all patients\n3: delete a patient\n4: update a patient")
                ));

        int c;
        do {
            c = Main.reader.nextByte();
        }while (c<0||c>4);

        if (c==1) {
            Patient tempPat = new Patient();
            System.out.println("personal information " + "\nPatient full name");
            tempPat.fullName = Main.reader.next();

            System.out.println("Patient phone number");
            do{
                tempPat.phoneNumber = Main.reader.next();
            }while (!tempPat.phoneNumber.startsWith("0"));

            System.out.println("Patient age");
            //tempPat.age = Main.reader.nextByte();

            System.out.println("Patient new code");
            tempPat.code=Main.reader.next();

            patients.add(tempPat);
            System.out.println(tempPat.fullName + " is in the system");

        } else if (c==2 && !patients.isEmpty()) {
            for (Patient patient : patients){
                System.out.println(patient.toString());
            }
        } else if (c==3 && !patients.isEmpty()) {
            System.out.println("enter patient name");
            String tempName = Main.reader.next();

            System.out.println("enter patient code");
            String tempCode = Main.reader.next();

            int i = patients.size();
            for (Patient patient : patients){
                if ((patient.fullName.equals(tempName)) && (patient.code.equals(tempCode))){
                    System.out.println( patient.toString() + " , is deleted");
                    patients.remove(patient);
                    break;
                }
            }
            if (i==patients.size()){
                System.out.println("there's no patient with those information");
            }
        } else if (c==4 && !patients.isEmpty()) {
            System.out.println("enter patient name");
            String tempName = Main.reader.next();

            System.out.println("enter patient code");
            String tempCode = Main.reader.next();

            for (Patient patient : patients){
                if ((patient.fullName.equals(tempName)) && (patient.code.equals(tempCode))){
                    System.out.println( patient.toString() + " , founded " +
                            "\n0: return" +
                            "\n1: add an appointment");
                    byte ch ;
                    do {
                        ch = Main.reader.nextByte();
                    }while (ch<0 || ch>1);
                    if(ch==1){
                        patient.setAppointment(this,null);
                    }else {
                        Owners(true);
                    }
                    break;


                }
            }
            System.out.println("there's no doctor with those information");
        }

        Owners(true);
    }


    void change(){
        System.out.println("0:return" +
                "\n1:change the name" +
                "\n2:change the password");

        int c;
        do {
            c = Main.reader.nextByte();
        }while (c<0||c>2);
        if(c==1){
            System.out.println("enter the new name");
            String newName = Main.reader.next();
            setName(newName);
            System.out.println("name changed");
        }
        else if(c==2) {
            System.out.println("enter the old password");
            String newName;
            do {
                newName = Main.reader.next();
            }while (!Objects.equals(newName, name));
            setName(newName);
            System.out.println("password changed");
        }
        Owners(true);


    }



    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
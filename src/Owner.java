
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
            patientSettingsButton.addActionListener(e ->{
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(PatSettings(), BorderLayout.WEST);
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            ownerPanel.add(patientSettingsButton);

            ownerPanel.add(Box.createRigidArea(new Dimension(30, 0)));

            JButton changeCredentialsButton = new JButton("Change Name/Password");
            changeCredentialsButton.setAlignmentY(Component.CENTER_ALIGNMENT);
            changeCredentialsButton.setMaximumSize(new Dimension(200, 30));
            changeCredentialsButton.addActionListener(e -> {
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(change(), BorderLayout.CENTER);
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            ownerPanel.add(changeCredentialsButton);

            ownerPanel.add(Box.createRigidArea(new Dimension(30, 0)));

            ownerPanel.add(Box.createHorizontalGlue());

            JButton signOutButton = new JButton("Sign Out");
            signOutButton.setAlignmentY(Component.CENTER_ALIGNMENT);
            signOutButton.setFocusable(false);
            signOutButton.setMaximumSize(new Dimension(200, 30));
            signOutButton.addActionListener(e -> {
                Main.frame.getContentPane().removeAll();
                Main.signOut();
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            ownerPanel.add(signOutButton);

            ownerPanel.add(Box.createRigidArea(new Dimension(20, 50)));

            Main.frame.add(ownerPanel, BorderLayout.NORTH);
        } else {
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






    //doc settings

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
        deleteDoctorButton.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Owners(true);
            Main.frame.add(DocSettings(), BorderLayout.WEST);
            Main.frame.add(deleteDoctor(),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        DocSettingsPanel.add(deleteDoctorButton);

        DocSettingsPanel.add(Box.createVerticalGlue());

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(200, 30));
        backButton.addActionListener(e -> Owners(true));
        DocSettingsPanel.add(backButton);

        DocSettingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        return DocSettingsPanel;
    }

    //hna tbda add doctor

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
            JLabel FName = new JLabel("enter the doctor full name");
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
                }else {
                    tempDoc.fullName = FNameField.getText();
                    tempDoc.age = ageField.getText();
                    tempDoc.phoneNumber = PNumberField.getText();
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(DocSettings(), BorderLayout.WEST);
                    Main.frame.add(addDoc(1,tempDoc),BorderLayout.CENTER);
                }
                Main.frame.revalidate();
                Main.frame.repaint();

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
                    Main.frame.add(addDoc(1,doctor),BorderLayout.CENTER);
                    Main.frame.revalidate();
                    Main.frame.repaint();
                }else {
                    doctor.setId(z1[0],enterYearField.getText());
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(DocSettings(), BorderLayout.WEST);
                    Main.frame.add(addDoc(2,doctor),BorderLayout.CENTER);
                    Main.frame.revalidate();
                    Main.frame.repaint();
                }

            });

            panel.add(submitButton, gbc);
        }
        else {
            JLabel FName = new JLabel(doctor.toString());
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(FName, gbc);

            JButton saveButton = new JButton("Next");
            gbc.gridx = 4;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            saveButton.addActionListener(e -> {
                Main.owner.doctors.add(doctor);
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

    //hna tkhlas add doctor

    protected JPanel viewAllDocs() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        int row = 0, col = 0;

        for (Doctor doctor : Main.owner.doctors) {
            JPanel docPanel = new JPanel();
            docPanel.setLayout(new BorderLayout());
            docPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Optional border

            JPanel navPanel = new JPanel();
            navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));
            navPanel.add(Box.createRigidArea(new Dimension(10, 30)));

            ImageIcon deleteImage = new ImageIcon("resources/delete.png");
            Image scaledDeleteImage = deleteImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JButton deleteIcon = new JButton(new ImageIcon(scaledDeleteImage));
            deleteIcon.setBackground(new Color(0xFFFF0000, true));
            deleteIcon.addActionListener(e -> {
                Main.owner.doctors.remove(doctor);
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(DocSettings(), BorderLayout.WEST);
                Main.frame.revalidate();
                Main.frame.repaint();

            });
            navPanel.add(deleteIcon);

            navPanel.add(Box.createHorizontalGlue());

            ImageIcon editImage = new ImageIcon("resources/edit.png");
            Image scaledEditImage = editImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JButton editIcon = new JButton(new ImageIcon(scaledEditImage));
            editIcon.setBackground(new Color(0xFF11FF00, true));
            editIcon.addActionListener(e->{
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(editDoctor(0,doctor), BorderLayout.WEST);
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            navPanel.add(editIcon);

            navPanel.add(Box.createRigidArea(new Dimension(10, 30)));

            JTextArea textArea = new JTextArea(doctor.toString()+" "+doctor.Id);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            textArea.setOpaque(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 12));

            docPanel.add(navPanel, BorderLayout.NORTH);
            docPanel.add(textArea, BorderLayout.CENTER);

            gbc.gridx = col;
            gbc.gridy = row;
            mainPanel.add(docPanel, gbc);

            col++;
            if (col == 2) {
                col = 0;
                row++;
            }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel container = new JPanel(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);

        return container;
    }

    protected JPanel deleteDoctor(){
        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(new BorderLayout());
            JPanel searchPanel = new JPanel();
            searchPanel.setLayout(new GridBagLayout());
            searchPanel.setBackground(Color.lightGray);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            JLabel nameLabel = new JLabel("Enter the doctor id :");
            gbc.gridx = 0;
            gbc.gridy = 0;
            searchPanel.add(nameLabel, gbc);

            JTextField idField = new JTextField(15);
            gbc.gridx = 1;
            searchPanel.add(idField, gbc);


            JButton submitButton = new JButton("search");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            submitButton.addActionListener(e -> {
                for (Doctor doctor : Main.owner.doctors){
                    if (Objects.equals(doctor.Id,idField.getText())){
                        JOptionPane.showMessageDialog(Main.frame, (doctor.toString()+" have been deleted"));
                        Main.owner.doctors.remove(doctor);
                        Main.frame.getContentPane().removeAll();
                        Owners(true);
                        Main.frame.add(DocSettings(), BorderLayout.WEST);
                        Main.frame.revalidate();
                        Main.frame.repaint();
                    }
                }
                JOptionPane.showMessageDialog(Main.frame, "Invalid doctor ID. Try again.");
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(DocSettings(), BorderLayout.WEST);
                Main.frame.add(deleteDoctor(),BorderLayout.CENTER);
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            searchPanel.add(submitButton, gbc);
            deletePanel.add(searchPanel,BorderLayout.CENTER);
        return deletePanel;
    }

    //hna tbda edit doctor

    protected JPanel editDoctor(int i,Doctor doctorToEdit){
        JPanel editPanel= new JPanel();
            if (doctorToEdit == null){
                JPanel searchPanel = new JPanel();
                searchPanel.setLayout(new GridBagLayout());
                searchPanel.setBackground(Color.lightGray);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);

                JLabel nameLabel = new JLabel("Enter the doctor id:");
                gbc.gridx = 0;
                gbc.gridy = 0;
                searchPanel.add(nameLabel, gbc);

                JTextField IDField = new JTextField(15);
                gbc.gridx = 1;
                searchPanel.add(IDField, gbc);


                JButton submitButton = new JButton("Submit");
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                submitButton.addActionListener(e -> {
                    String inputID = IDField.getText();

                    for(Doctor doctor : Main.owner.doctors) {
                        if (Objects.equals(doctor.Id,inputID)) {
                            Main.frame.getContentPane().removeAll();
                            Owners(true);
                            Main.frame.add(DocSettings(), BorderLayout.WEST);
                            Main.frame.add(editDoctor(0,doctor),BorderLayout.CENTER);
                            Main.frame.revalidate();
                            Main.frame.repaint();
                        }
                    }
                    JOptionPane.showMessageDialog(Main.frame, "Invalid ID. Try again.");
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(DocSettings(), BorderLayout.WEST);
                    Main.frame.add(editDoctor(0,null),BorderLayout.CENTER);
                    Main.frame.revalidate();
                    Main.frame.repaint();
                });
                searchPanel.add(submitButton, gbc);

                editPanel.add(searchPanel, BorderLayout.CENTER);
            }else {
                JPanel addDocPanel = new JPanel(new BorderLayout());

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
                saveInformation.setBackground(i==1?current:(i==2?ended:next));

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
                addDocPanel.add(stageEditForme(personalInformation,professionalInformation,saveInformation,doctorToEdit),BorderLayout.CENTER);

                editPanel.add(addDocPanel);
            }
        return editPanel;
    }

    protected JPanel stageEditForme(JPanel PeE,JPanel PrE,JPanel SE,Doctor doctor){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        if (Objects.equals(PeE.getBackground(), new Color(0x82DBEF))){
            JLabel msg = new JLabel("If you want to leave information as it was, leave it blank.");
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(msg, gbc);

            JLabel FName = new JLabel("enter the doctor full name :");
            gbc.gridx = 0;
            gbc.gridy = 1;

            JTextField FNameField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(FNameField, gbc);


            JLabel age = new JLabel("enter the doctor age : ");
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(age, gbc);

            JTextField ageField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(ageField, gbc);


            JLabel PNumber = new JLabel("enter the doctor phone number : ");
            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(PNumber, gbc);

            JTextField PNumberField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(PNumberField, gbc);


            JButton submitButton = new JButton("Next");
            gbc.gridx = 4;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            submitButton.addActionListener(e -> {

                doctor.fullName = FNameField.getText().isEmpty()?doctor.fullName:FNameField.getText();
                doctor.age = ageField.getText().isEmpty()?doctor.age:ageField.getText();
                doctor.phoneNumber = PNumberField.getText().isEmpty()?doctor.phoneNumber:PNumberField.getText();
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(DocSettings(), BorderLayout.WEST);
                Main.frame.add(editDoctor(1,doctor),BorderLayout.CENTER);
                Main.frame.revalidate();
                Main.frame.repaint();
            });

            panel.add(submitButton, gbc);
        }
        else if (Objects.equals(PrE.getBackground(), new Color(0x82DBEF))) {
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

            z1[0]=null;

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

                if (enterYearField.getText().length()!=4 || !enterYearField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(Main.frame, "Enter a valid year. Try again.");
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(DocSettings(), BorderLayout.WEST);
                    Main.frame.add(addDoc(1,doctor),BorderLayout.CENTER);
                }else {
                    doctor.setId(
                            z1[0]==null?null:z1[0],
                            enterYearField.getText().isEmpty()?null:enterYearField.getText()
                    );
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(DocSettings(), BorderLayout.WEST);
                    Main.frame.add(addDoc(2,doctor),BorderLayout.CENTER);
                }
                Main.frame.revalidate();
                Main.frame.repaint();

            });

            panel.add(submitButton, gbc);
        }
        else {
            JLabel FName = new JLabel(doctor.toString());
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(FName, gbc);

            JButton saveButton = new JButton("Next");
            gbc.gridx = 4;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            saveButton.addActionListener(e -> {
                Main.owner.doctors.add(doctor);
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

    //hna tkhlas edit doctor



    protected JPanel PatSettings(){
        JPanel PatSettingsPanel = new JPanel();
        PatSettingsPanel.setLayout(new BoxLayout(PatSettingsPanel, BoxLayout.PAGE_AXIS));
        PatSettingsPanel.setBackground(new Color(0x98D1FA));

        PatSettingsPanel.add(Box.createRigidArea(new Dimension(250, 20)));

        JLabel titleLabel = new JLabel("Patient Settings");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        PatSettingsPanel.add(titleLabel);

        PatSettingsPanel.add(Box.createRigidArea(new Dimension(250, 20)));

        JButton addPatientButton = new JButton("Add Patient");
        addPatientButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPatientButton.setMaximumSize(new Dimension(200, 30));
        addPatientButton.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Owners(true);
            Main.frame.add(PatSettings(), BorderLayout.WEST);
            Main.frame.add(addPat(0,null),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        PatSettingsPanel.add(addPatientButton);

        PatSettingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton viewPatientsButton = new JButton("View All Patients");
        viewPatientsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewPatientsButton.setMaximumSize(new Dimension(200, 30));
        viewPatientsButton.setEnabled(!patients.isEmpty());
        viewPatientsButton.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Owners(true);
            Main.frame.add(PatSettings(), BorderLayout.WEST);
            Main.frame.add(viewAllPats(null),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        PatSettingsPanel.add(viewPatientsButton);

        PatSettingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton deletePatientButton = new JButton("Delete Patient");
        deletePatientButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deletePatientButton.setMaximumSize(new Dimension(200, 30));
        deletePatientButton.setEnabled(!patients.isEmpty());
        deletePatientButton.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Owners(true);
            Main.frame.add(DocSettings(), BorderLayout.WEST);
            Main.frame.add(deletePatient(),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        PatSettingsPanel.add(deletePatientButton);

        PatSettingsPanel.add(Box.createVerticalGlue());

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(200, 30));
        backButton.addActionListener(e -> Owners(true));
        PatSettingsPanel.add(backButton);

        PatSettingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        return PatSettingsPanel;
    }

    protected JPanel addPat(int i,Patient patient){
        JPanel addPatPanel = new JPanel(new BorderLayout());

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

        JPanel medicalInformation = new JPanel();
        medicalInformation.setLayout(new BoxLayout(medicalInformation, BoxLayout.X_AXIS));
        medicalInformation.setPreferredSize(new Dimension(200,50));
        medicalInformation.setMaximumSize(new Dimension(200, 50));
        medicalInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
        medicalInformation.setBackground(i==1?current:(i==2?ended:next));

        JLabel MI = new JLabel("   medical Information");
        ImageIcon MIImage = new ImageIcon("resources/medical.png");
        Image scaledMIImage = MIImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon MIImageIcon = new ImageIcon(scaledMIImage);
        JLabel MIIcon = new JLabel(MIImageIcon);
        medicalInformation.add(MI);
        medicalInformation.add(MIIcon);
        stages.add(medicalInformation);

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

        addPatPanel.add(stages,BorderLayout.WEST);
        addPatPanel.add(patStageForme(personalInformation,medicalInformation,saveInformation,patient),BorderLayout.CENTER);

        return addPatPanel;
    }

    protected JPanel patStageForme(JPanel PI,JPanel MI,JPanel SI,Patient patient){
        Patient tempPat = patient==null?new Patient():patient;
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        if (Objects.equals(PI.getBackground(), new Color(0x82DBEF))){
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
            gbc.gridx = 1;
            panel.add(ageField, gbc);


            JLabel PNumber = new JLabel("enter the Patient phone number : ");
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(PNumber, gbc);

            JTextField PNumberField = new JTextField(15);
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
                if (emptyTest || validTest ){
                    JOptionPane.showMessageDialog(Main.frame, "somthing seem wrong. Try again.");
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(PatSettings(), BorderLayout.WEST);
                    Main.frame.add(addPat(0,null),BorderLayout.CENTER);
                }
                else {
                    tempPat.fullName = FNameField.getText();
                    tempPat.age = ageField.getText();
                    tempPat.phoneNumber = PNumberField.getText();
                    tempPat.code = codeField.getText();
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(PatSettings(), BorderLayout.WEST);
                    Main.frame.add(addPat(1,tempPat),BorderLayout.CENTER);
                }
                Main.frame.revalidate();
                Main.frame.repaint();

            });

            panel.add(submitButton, gbc);
        }
        else if (Objects.equals(MI.getBackground(), new Color(0x82DBEF))) {
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
            gbc.gridx = 1;
            panel.add(dayField, gbc);

            JLabel month = new JLabel("month(number) : ");
            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(month, gbc);

            JTextField monthField = new JTextField(5);
            gbc.gridx = 1;
            panel.add(monthField, gbc);

            JLabel year = new JLabel("year : ");
            gbc.gridx = 0;
            gbc.gridy = 4;
            panel.add(year, gbc);

            JTextField yearField = new JTextField(5);
            gbc.gridx = 1;
            panel.add(yearField, gbc);

            JLabel Hour = new JLabel("enter the Hour of the appointment : ");
            gbc.gridx = 0;
            gbc.gridy = 5;
            panel.add(Hour, gbc);

            JTextField HourField = new JTextField(20);
            gbc.gridx = 1;
            panel.add(HourField, gbc);

            JLabel doc = new JLabel("enter the doctor id : ");
            gbc.gridx = 0;
            gbc.gridy = 6;
            panel.add(doc, gbc);

            JTextField docField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(docField, gbc);

            JLabel meDiagnosis = new JLabel("enter the medical Diagnosis : ");
            gbc.gridx = 0;
            gbc.gridy = 7;
            panel.add(meDiagnosis, gbc);

            JTextField meDiagnosisField = new JTextField(20);
            gbc.gridx = 1;
            panel.add(meDiagnosisField, gbc);

            JLabel medicine = new JLabel("enter the medicine : ");
            gbc.gridx = 0;
            gbc.gridy = 8;
            panel.add(medicine, gbc);

            JTextField medicineField = new JTextField(20);
            gbc.gridx = 1;
            panel.add(medicineField, gbc);

            JButton submitButton = new JButton("Next");
            gbc.gridx = 9;
            gbc.gridy =5;
            gbc.gridwidth = 2;
            submitButton.addActionListener(e -> {
                if (dayField.getText().isEmpty() && monthField.getText().isEmpty() && yearField.getText().isEmpty() &&
                        docField.getText().isEmpty() && meDiagnosisField.getText().isEmpty() && medicineField.getText().isEmpty() ) {
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(PatSettings(), BorderLayout.WEST);
                    Main.frame.add(addPat(2,patient),BorderLayout.CENTER);


                }
                else if (dayField.getText().isEmpty() || monthField.getText().isEmpty() || yearField.getText().isEmpty() ||
                        docField.getText().isEmpty() || meDiagnosisField.getText().isEmpty() || medicineField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(Main.frame, "somthing seem wrong. Try again.");
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(PatSettings(), BorderLayout.WEST);
                    Main.frame.add(addPat(1,patient),BorderLayout.CENTER);
                } else if(patient.getDocFromId(docField.getText())==null){
                    JOptionPane.showMessageDialog(Main.frame, "there's no doctor with "+docField.getText()+ ". Try again.");
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(PatSettings(), BorderLayout.WEST);
                    Main.frame.add(addPat(1,patient),BorderLayout.CENTER);
                }
                else {
                    patient.appointment = new MedicalHistory(null,patient.getDocFromId(docField.getText()));
                    patient.appointment.doc = patient.getDocFromId(docField.getText());
                    patient.appointment.medicalDiagnosis = meDiagnosisField.getText();
                    patient.appointment.medicalDiagnosis = medicineField.getText();
                    try {
                        int day1 = Integer.parseInt(dayField.getText());
                        int month1 = Integer.parseInt(monthField.getText());
                        int year1 = Integer.parseInt(year.getText());
                        int hour = Integer.parseInt(HourField.getText());

                        String date1 = String.format("%02d/%02d/%04d", day1, month1, year1);
                        String time = String.format("%02d:00", hour);

                        patient.appointment.date = MedicalHistory.createAppointment(date1, time);
                    } catch (NumberFormatException c) {
                        System.out.println("Invalid input: Please enter numeric values for day, month, year, and hour.");
                    } catch (Exception c) {
                        System.out.println("An error occurred: " + c.getMessage());
                    }
                    patient.getDocFromId(docField.getText()).patients.add(patient);
                    patient.getDocFromId(docField.getText()).phoneNumber += 1;
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(PatSettings(), BorderLayout.WEST);
                    Main.frame.add(addPat(2,patient),BorderLayout.CENTER);
                }
                Main.frame.revalidate();
                Main.frame.repaint();

            });

            panel.add(submitButton, gbc);
        }
        else {
            JLabel FName = new JLabel(patient.toString());
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(FName, gbc);

            JButton saveButton = new JButton("Next");
            gbc.gridx = 4;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            saveButton.addActionListener(e -> {
                Main.owner.patients.add(patient);
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(PatSettings(), BorderLayout.WEST);
                Main.frame.revalidate();
                Main.frame.repaint();
            });

            panel.add(saveButton, gbc);
        }

        return panel;
    }

    protected JPanel viewAllPats(Doctor doctor){
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        int row = 0, col = 0;

        ArrayList<Patient> patientArrayList = (doctor==null)?patients:doctor.patients;

        for (Patient patient : patientArrayList) {
            JPanel patPanel = new JPanel();
            patPanel.setLayout(new BorderLayout());
            patPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Optional border

            JPanel navPanel = new JPanel();
            navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));
            navPanel.add(Box.createRigidArea(new Dimension(10, 30)));

            ImageIcon deleteImage = new ImageIcon("resources/delete.png");
            Image scaledDeleteImage = deleteImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JButton deleteIcon = new JButton(new ImageIcon(scaledDeleteImage));
            deleteIcon.setBackground(new Color(0xFFFF0000, true));
            deleteIcon.addActionListener(e -> {
                Main.owner.patients.remove(patient);
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(PatSettings(), BorderLayout.WEST);
                Main.frame.revalidate();
                Main.frame.repaint();

            });
            navPanel.add(deleteIcon);

            navPanel.add(Box.createHorizontalGlue());

            ImageIcon editImage = new ImageIcon("resources/edit.png");
            Image scaledEditImage = editImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JButton editIcon = new JButton(new ImageIcon(scaledEditImage));
            editIcon.setBackground(new Color(0xFF11FF00, true));
            editIcon.addActionListener(e->{
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(editPatient(patient), BorderLayout.WEST);
                Main.frame.revalidate();
                Main.frame.repaint();
            });
            navPanel.add(editIcon);

            navPanel.add(Box.createRigidArea(new Dimension(10, 30)));

            JTextArea textArea = new JTextArea(patient.toString()+" "+patient.code);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            textArea.setOpaque(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 12));

            patPanel.add(navPanel, BorderLayout.NORTH);
            patPanel.add(textArea, BorderLayout.CENTER);

            gbc.gridx = col;
            gbc.gridy = row;
            mainPanel.add(patPanel, gbc);

            col++;
            if (col == 2) {
                col = 0;
                row++;
            }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel container = new JPanel(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);

        return container;
    }

    protected JPanel deletePatient(){
        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(new BorderLayout());
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setBackground(Color.lightGray);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Enter the patient full name :");
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(15);
        gbc.gridx = 1;
        searchPanel.add(nameField, gbc);

        JLabel codeLabel = new JLabel("Enter the patient password :");
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(codeLabel, gbc);

        JTextField codeField = new JTextField(15);
        gbc.gridx = 1;
        searchPanel.add(codeField, gbc);


        JButton submitButton = new JButton("search");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        submitButton.addActionListener(e -> {
            for (Patient patient : Main.owner.patients){
                if (Objects.equals(patient.fullName,nameField.getText()) && Objects.equals(patient.code,codeField.getText())){
                    JOptionPane.showMessageDialog(Main.frame, (patient.toString()+" have been deleted"));
                    Main.owner.patients.remove(patient);
                    Main.frame.getContentPane().removeAll();
                    Owners(true);
                    Main.frame.add(PatSettings(), BorderLayout.WEST);
                    Main.frame.revalidate();
                    Main.frame.repaint();
                }
            }
            JOptionPane.showMessageDialog(Main.frame, "Invalid name or password. Try again.");
            Main.frame.getContentPane().removeAll();
            Owners(true);
            Main.frame.add(PatSettings(), BorderLayout.WEST);
            Main.frame.add(deletePatient(),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        searchPanel.add(submitButton, gbc);
        deletePanel.add(searchPanel,BorderLayout.CENTER);
        return deletePanel;
    }

    protected JPanel editPatient(Patient patient){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel,BoxLayout.PAGE_AXIS));
        navPanel.setBackground(new Color(0x85B9DE));

        navPanel.add(Box.createRigidArea(new Dimension(250, 20)));

        JButton editPatientButton = new JButton("Edit Patient");
        editPatientButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editPatientButton.setMaximumSize(new Dimension(200, 30));
        editPatientButton.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            Owners(true);
            Main.frame.add(PatSettings(), BorderLayout.WEST);
            Main.frame.add(editPatient(patient),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        navPanel.add(editPatientButton);

        return panel;
    }



    public JPanel change() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(changeName());
        splitPane.setBottomComponent(changePassword());
        splitPane.setDividerSize(2);
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.5);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    protected JPanel changeName() {
        JPanel changeName = new JPanel();
        changeName.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel CNTitle = new JLabel("Change the Name");
        CNTitle.setFont(new Font("Arial", Font.BOLD, 16));
        changeName.add(CNTitle, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel NName = new JLabel("Enter the New Name:");
        changeName.add(NName, gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 25));
        changeName.add(nameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel CNName = new JLabel("Confirm the New Name:");
        changeName.add(CNName, gbc);

        gbc.gridx = 1;
        JTextField confNameField = new JTextField();
        confNameField.setPreferredSize(new Dimension(200, 25));
        changeName.add(confNameField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel password = new JLabel("Enter the Password:");
        changeName.add(password, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        changeName.add(passwordField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 1;
        JButton submit = new JButton("Submit");
        submit.addActionListener(e->{
            if (
                    Objects.equals(passwordField.getText(),this.password)
                    &&
                    Objects.equals(nameField.getText(),confNameField.getText())
            ){
                setName(nameField.getText());
                JOptionPane.showMessageDialog(Main.frame, "the name now is "+nameField.getText());
                Main.frame.getContentPane().removeAll();
                Owners(true);
            }else{
                JOptionPane.showMessageDialog(Main.frame, "somthing seem wrong. Try again.");
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(change(), BorderLayout.CENTER);
            }
        });

        changeName.add(submit, gbc);

        return changeName;
    }

    protected JPanel changePassword() {
        JPanel changePassword = new JPanel();
        changePassword.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel CPTitle = new JLabel("Change the Password");
        CPTitle.setFont(new Font("Arial", Font.BOLD, 16));
        changePassword.add(CPTitle, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel NPassword = new JLabel("Enter the New Password:");
        changePassword.add(NPassword, gbc);

        gbc.gridx = 1;
        JTextField passwordField3 = new JTextField();
        passwordField3.setPreferredSize(new Dimension(200, 25));
        changePassword.add(passwordField3, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel CNPassword = new JLabel("Confirm the New Password:");
        changePassword.add(CNPassword, gbc);

        gbc.gridx = 1;
        JTextField confPasswordField = new JTextField();
        confPasswordField.setPreferredSize(new Dimension(200, 25));
        changePassword.add(confPasswordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel oldPassword = new JLabel("Enter the Old Password:");
        changePassword.add(oldPassword, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        changePassword.add(passwordField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 1;
        JButton submit = new JButton("Submit");
        submit.addActionListener(e->{
            if (
                    Objects.equals(passwordField.getText(),this.password)
                            &&
                            Objects.equals(passwordField3.getText(),confPasswordField.getText())
            ){
                setPassword(passwordField3.getText());
                JOptionPane.showMessageDialog(Main.frame, "the name now is "+passwordField3.getText());
                Main.frame.getContentPane().removeAll();
                Owners(true);
            }else{
                JOptionPane.showMessageDialog(Main.frame, "somthing seem wrong. Try again.");
                Main.frame.getContentPane().removeAll();
                Owners(true);
                Main.frame.add(change(), BorderLayout.CENTER);
            }
        });

        changePassword.add(submit, gbc);

        return changePassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
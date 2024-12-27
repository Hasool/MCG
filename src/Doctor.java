import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Doctor extends Human{
    protected String Id;
    protected String code = null;
    protected ArrayList<Patient> patients = new ArrayList<>();
    protected int patientsNumber = 0;



    public void setPatients(){
        System.out.println("0: sign out" +
                "\n1: select a patient" +
                "\n2: return");

        byte c;
        do{
            c = Main.reader.nextByte();
        }while (c<0 || c>2);

        if (c==0)Main.signOut();
        else if (c==1) {
            System.out.println("enter the patient index");
            int i ;
            do{
                i = Main.reader.nextInt();
            }while (i-1<0 || i>patients.size());
            setPatient(patients.get(i-1));
        }

    }


    public void setId(String z1 , String z2) {

        if (z1==null || z2==null){
            if (z1 == null){
                Id = Id.substring(0,2) + z2 + Id.substring(6);
            }else {
                Id = z1 + Id.substring(2);
            }
        }else{
            Id = z1+ z2;

            Random random = new Random();
            String z3;
            do {
                int number = random.nextInt(1000);
                z3 = String.format("%03d", number);
            }while (IsNotNew(Id,z3));

            Id = Id + z3;
        }


    }

    public String IdToString(String id){
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
            if(id.startsWith(specialties[i])){
                return ("a "+Specialties[i] + " begin his employment in ") + id.substring(2,6);
            }
        }
        return "";
    }



    public void getPatients() {
        int i =1;
        for (Patient patient : patients) {
            System.out.println(i + ": " + patient.toString());
        }
    }

    public Boolean IsNotNew(String id , String z3){
        for (Doctor doctor : Main.owner.doctors){
            if (doctor.Id.equals(id+z3)){
                return true;
            }
        }
        return false;

    }


    public void setPatient(Patient patient) {

    }

    public void Return(boolean t){
        App app = new App();
        Main.frame.getContentPane().removeAll();
        Main.frame.add(app.DocUser(this,t), BorderLayout.CENTER);
        Main.frame.revalidate();
        Main.frame.repaint();
    }


    public void setCode(String code) {

        this.code = code;
    }

    public JPanel user(boolean t){
        JPanel userPanel= new JPanel();
        userPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if (this.code==null || t){
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            JLabel CNTitle = new JLabel("Create a password");
            CNTitle.setFont(new Font("Arial", Font.BOLD, 16));
            userPanel.add(CNTitle, gbc);

            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            JLabel CPassword = new JLabel("enter a password:");
            userPanel.add(CPassword, gbc);

            gbc.gridx = 1;
            JTextField passwordField = new JTextField();
            passwordField.setPreferredSize(new Dimension(200, 25));
            userPanel.add(passwordField, gbc);

            gbc.gridy = 2;
            gbc.gridx = 0;
            JLabel password = new JLabel("Confirm the password");
            userPanel.add(password, gbc);

            gbc.gridx = 1;
            JTextField passField = new JTextField();
            passField.setPreferredSize(new Dimension(200, 25));
            userPanel.add(passField, gbc);

            gbc.gridy = 3;
            gbc.gridx = 1;
            JButton submit = new JButton("Submit");
            submit.addActionListener(e->{
                if (Objects.equals(passField.getText(),passwordField.getText())){
                    setCode(passwordField.getText());
                }else{
                    JOptionPane.showMessageDialog(Main.frame, "Something seems wrong. Try again.");
                }
                Return(false);
            });
            userPanel.add(submit);
        }else{
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            JLabel CNTitle = new JLabel("Hello " + this.fullName);
            CNTitle.setFont(new Font("Arial", Font.BOLD, 16));
            userPanel.add(CNTitle, gbc);

            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            JLabel password = new JLabel("Enter your password:");
            userPanel.add(password, gbc);

            gbc.gridx = 1;
            JTextField passField = new JTextField();
            passField.setPreferredSize(new Dimension(200, 25));
            userPanel.add(passField, gbc);

            gbc.gridy = 2;
            gbc.gridx = 1;
            JButton submit = new JButton("Submit");
            submit.addActionListener(e -> {
                String enteredPassword = passField.getText(); // Get user input
                if (Objects.equals(enteredPassword, this.code)) {
                    Main.frame.getContentPane().removeAll();
                    docNav(); // Navigate to the doctor's dashboard
                } else {
                    JOptionPane.showMessageDialog(Main.frame, "Invalid password. Please try again.");
                    passField.setText(""); // Clear the password field for re-entry
                }
            });
            userPanel.add(submit,gbc);
        }

        return userPanel;
    }

    protected void docNav(){
        JPanel docPanel = new JPanel();
        docPanel.setLayout(new BoxLayout(docPanel, BoxLayout.X_AXIS));
        docPanel.setBackground(new Color(0x98D1FA));

        docPanel.add(Box.createRigidArea(new Dimension(10, 50)));

        JLabel welcomeLabel = new JLabel("Welcome, "+this.fullName+"!");
        welcomeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        docPanel.add(welcomeLabel);

        docPanel.add(Box.createRigidArea(new Dimension(50, 50)));

        JButton doctorSettingsButton = new JButton("see your information");
        doctorSettingsButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        doctorSettingsButton.setMaximumSize(new Dimension(200, 30));
        doctorSettingsButton.addActionListener(e-> {
            Main.frame.getContentPane().removeAll();
            docNav();
            Main.frame.add(seeInfo(), BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        docPanel.add(doctorSettingsButton);

        docPanel.add(Box.createRigidArea(new Dimension(30, 0)));

        JButton patientSettingsButton = new JButton("update your password");
        patientSettingsButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        patientSettingsButton.setMaximumSize(new Dimension(200, 30));
        patientSettingsButton.addActionListener(e -> {
            Return(true);
        });
        docPanel.add(patientSettingsButton);

        docPanel.add(Box.createRigidArea(new Dimension(30, 0)));

        JButton changeCredentialsButton = new JButton("your patients");
        changeCredentialsButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        changeCredentialsButton.setMaximumSize(new Dimension(200, 30));
        changeCredentialsButton.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            docNav();
            Main.frame.add(urPatNav(), BorderLayout.WEST);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        docPanel.add(changeCredentialsButton);

        docPanel.add(Box.createRigidArea(new Dimension(30, 0)));

        docPanel.add(Box.createHorizontalGlue());

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
        docPanel.add(signOutButton);

        docPanel.add(Box.createRigidArea(new Dimension(20, 50)));
        Main.frame.getContentPane().add(docPanel, BorderLayout.NORTH);
        Main.frame.revalidate();
        Main.frame.repaint();
    }

    protected JPanel seeInfo() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        JLabel fullNameLabel = new JLabel("<html><b><h1>" + this.fullName + "</h1></b></html>");
        infoPanel.add(fullNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        JLabel phoneLabel = new JLabel("Your phone number is "+ this.phoneNumber );
        infoPanel.add(phoneLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        JLabel ageLabel = new JLabel("<html><h2>" + this.age + " years old</h2></html>");
        infoPanel.add(ageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        JLabel idLabel = new JLabel(IdToHtml(this.Id));
        infoPanel.add(idLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        JLabel patientsLabel = new JLabel("You have " + this.patientsNumber +" patient" +((this.patientsNumber>1) ? "s" : ""));
        infoPanel.add(patientsLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        ImageIcon editImage = new ImageIcon("resources/edit.png");
        Image scaledEditImage = editImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton editIcon = new JButton(new ImageIcon(scaledEditImage));
        editIcon.setBackground(new Color(0xFF11FF00, true));
        editIcon.addActionListener(e -> System.out.println("edit trigger"));
        infoPanel.add(editIcon, gbc);

        return infoPanel;
    }

    protected String IdToHtml(String id){
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
            if(id.startsWith(specialties[i])){
                return "<html> a <h2>"+Specialties[i] + "</h2><br> begin his employment in <h2>" + id.substring(2,6)+"</h2><br><html>";
            }
        }
        return "";
    }

    protected JPanel urPatNav(){
        JPanel yourPatSet = new JPanel();
        yourPatSet.setLayout(new BoxLayout(yourPatSet, BoxLayout.PAGE_AXIS));
        yourPatSet.setBackground(new Color(0x98D1FA));

        yourPatSet.add(Box.createRigidArea(new Dimension(250, 20)));

        JLabel titleLabel = new JLabel("your patient Settings");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        yourPatSet.add(titleLabel);

        yourPatSet.add(Box.createRigidArea(new Dimension(250, 20)));

        JButton addPatientButton = new JButton("Add Patient");
        addPatientButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPatientButton.setMaximumSize(new Dimension(200, 30));
        addPatientButton.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            docNav();
            Main.frame.add(urPatNav(), BorderLayout.WEST);
            Main.frame.getContentPane().add(addPat( 0,null),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        yourPatSet.add(addPatientButton);

        yourPatSet.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton viewPatientsButton = new JButton("View All Doctors");
        viewPatientsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewPatientsButton.setMaximumSize(new Dimension(200, 30));
        viewPatientsButton.setEnabled(!patients.isEmpty());
        viewPatientsButton.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            docNav();
            Main.frame.add(urPatNav(), BorderLayout.WEST);
            Main.frame.getContentPane().add(Main.owner.viewAllPats(this),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        yourPatSet.add(viewPatientsButton);

        yourPatSet.add(Box.createVerticalGlue());

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(200, 30));
        backButton.addActionListener(e -> urPatNav());
        yourPatSet.add(backButton);

        yourPatSet.add(Box.createRigidArea(new Dimension(0, 20)));

        return yourPatSet;
    }

    protected JPanel addPat(int i , Patient patient){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

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

        panel.add(stages,BorderLayout.WEST);
        panel.add(patStageForme(personalInformation,medicalInformation,saveInformation,patient),BorderLayout.CENTER);

        return panel;
    }

    protected JPanel patStageForme(JPanel PI,JPanel MI,JPanel SI,Patient patient){
        Patient tempPat = patient==null?new Patient():patient;
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        if(Objects.equals(PI.getBackground(), new Color(0x82DBEF))){
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
            submitButton.addActionListener(e->{
                boolean emptyTest = (FNameField.getText().isEmpty() || ageField.getText().isEmpty() || PNumberField.getText().isEmpty() || codeField.getText().isEmpty());
                boolean validTest = !(ageField.getText().matches("\\d+") && PNumberField.getText().matches("\\d+"));
                if (emptyTest || validTest ){
                    JOptionPane.showMessageDialog(Main.frame, "somthing seem wrong. Try again.");
                    Main.frame.getContentPane().removeAll();
                    docNav();
                    Main.frame.add(urPatNav(), BorderLayout.WEST);
                    Main.frame.add(addPat( 0,null),BorderLayout.CENTER);
                }
                else {
                    tempPat.fullName = FNameField.getText();
                    tempPat.age = ageField.getText();
                    tempPat.phoneNumber = PNumberField.getText();
                    tempPat.code = codeField.getText();
                    Main.frame.getContentPane().removeAll();
                    docNav();
                    Main.frame.add(urPatNav(), BorderLayout.WEST);
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

            JLabel meDiagnosis = new JLabel("enter the medical Diagnosis : ");
            gbc.gridx = 0;
            gbc.gridy = 6;
            panel.add(meDiagnosis, gbc);

            JTextField meDiagnosisField = new JTextField(20);
            gbc.gridx = 1;
            panel.add(meDiagnosisField, gbc);

            JLabel medicine = new JLabel("enter the medicine : ");
            gbc.gridx = 0;
            gbc.gridy = 7;
            panel.add(medicine, gbc);

            JTextField medicineField = new JTextField(20);
            gbc.gridx = 1;
            panel.add(medicineField, gbc);

            JButton submitButton = new JButton("Next");
            gbc.gridx = 1;
            gbc.gridy =8;
            gbc.gridwidth = 2;
            submitButton.addActionListener(e->{
                if (dayField.getText().isEmpty() && monthField.getText().isEmpty() && yearField.getText().isEmpty() &&
                        meDiagnosisField.getText().isEmpty() && medicineField.getText().isEmpty() ) {
                    Main.frame.getContentPane().removeAll();
                    docNav();
                    Main.frame.add(urPatNav(), BorderLayout.WEST);
                    Main.frame.add(addPat(2,patient),BorderLayout.CENTER);
                }
                else if (dayField.getText().isEmpty() || monthField.getText().isEmpty() || yearField.getText().isEmpty() ||
                        meDiagnosisField.getText().isEmpty() || medicineField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(Main.frame, "somthing seem wrong. Try again.");
                    Main.frame.getContentPane().removeAll();
                    docNav();
                    Main.frame.add(urPatNav(), BorderLayout.WEST);
                    Main.frame.add(addPat(1,patient),BorderLayout.CENTER);
                }
                else {
                    tempPat.appointment = new MedicalHistory(null,this);
                    tempPat.appointment.doc = tempPat.getDocFromId(this.Id);
                    tempPat.appointment.medicalDiagnosis = meDiagnosisField.getText();
                    tempPat.appointment.medicalDiagnosis = medicineField.getText();
                    try {
                        int day1 = Integer.parseInt(dayField.getText());
                        int month1 = Integer.parseInt(monthField.getText());
                        int year1 = Integer.parseInt(year.getText());
                        int hour = Integer.parseInt(HourField.getText());

                        String date1 = String.format("%02d/%02d/%04d", day1, month1, year1);
                        String time = String.format("%02d:00", hour);

                        tempPat.appointment.date = MedicalHistory.createAppointment(date1, time);
                    } catch (NumberFormatException c) {
                        System.out.println("Invalid input: Please enter numeric values for day, month, year, and hour.");
                    } catch (Exception c) {
                        System.out.println("An error occurred: " + c.getMessage());
                    }
                    this.patients.add(tempPat);
                    this.phoneNumber += 1;
                    Main.frame.getContentPane().removeAll();
                    docNav();
                    Main.frame.add(urPatNav(), BorderLayout.WEST);
                    Main.frame.add(addPat(2,tempPat),BorderLayout.CENTER);
                }
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
                docNav();
                Main.frame.add(urPatNav(), BorderLayout.WEST);
                Main.frame.revalidate();
                Main.frame.repaint();
            });

            panel.add(saveButton, gbc);
        }

        return panel;
    }




    @Override
    public String toString(){
        return (this.fullName + ", " + this.phoneNumber + ", " + this.age + " years old, \n" + IdToString(Id) );
    }

}

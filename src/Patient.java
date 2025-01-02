import javax.swing.*;
import java.awt.*;
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



    public void user() {

        appointment = findNextAppointment(futureAppointment);
        Main.frame.getContentPane().removeAll();
        JPanel patPanel = new JPanel();
        patPanel.setLayout(new BoxLayout(patPanel, BoxLayout.X_AXIS));
        patPanel.setBackground(new Color(0x98D1FA));

        patPanel.add(Box.createRigidArea(new Dimension(10, 50)));

        JLabel welcomeLabel = new JLabel("Welcome, "+this.fullName+"!");
        welcomeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        patPanel.add(welcomeLabel);

        patPanel.add(Box.createRigidArea(new Dimension(50, 50)));

        JButton SeeInfo = new JButton("see your information");
        SeeInfo.setAlignmentY(Component.CENTER_ALIGNMENT);
        SeeInfo.setMaximumSize(new Dimension(200, 30));
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

    protected boolean isAppointment(){
        return this.appointment != null;
    }

    protected JPanel nextApp(MedicalHistory a) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(new Color(0x98D1FA));

        panel.add(Box.createRigidArea(new Dimension(350, 20)));

        JLabel label = new JLabel(a.toString());
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(label);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton moreInfo = new JButton("read more about dr."+a.doc.fullName);
        moreInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        moreInfo.setMaximumSize(new Dimension(200, 30));
        moreInfo.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            user();
            Main.frame.add(nextApp(a),BorderLayout.WEST);
            Main.frame.add(more(a.doc),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        panel.add(moreInfo);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton chA = new JButton("Ask to change the date of the appointment ");
        chA.setAlignmentX(Component.CENTER_ALIGNMENT);
        chA.setMaximumSize(new Dimension(200, 30));
        chA.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            user();
            Main.frame.add(nextApp(a),BorderLayout.WEST);
            Main.frame.add(chA(),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        panel.add(chA);

        return panel;
    }

    protected JScrollPane prevApps(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;


        Dimension fixedSize = new Dimension(300, 200);

        for (int i = 0; i < medicalHistories.size(); i++) {
            JLabel label = new JLabel(medicalHistories.get(i).toString());
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.TOP);

            JPanel panelOfReq = new JPanel();
            panelOfReq.setLayout(new BorderLayout());
            panelOfReq.setBackground(new Color(0x6E0A1133, true));
            panelOfReq.setPreferredSize(fixedSize);

            panelOfReq.add(label, BorderLayout.CENTER);

            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            panel.add(panelOfReq, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    protected JScrollPane nextApps() {
        JPanel panel = new JPanel(new GridBagLayout());
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
            panelOfReq.setBackground(new Color(0x6E0A1133, true));
            panelOfReq.setPreferredSize(fixedSize);

            panelOfReq.add(label, BorderLayout.CENTER);

            JButton btn = new JButton("more");
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

    protected JPanel more(Doctor doctor){
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel fullNameLabel = new JLabel("<html><b><h1>" + doctor.fullName + "</h1></b></html>");
        infoPanel.add(fullNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel phoneLabel = new JLabel("phone number is "+ doctor.phoneNumber );
        infoPanel.add(phoneLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel ageLabel = new JLabel("<html><h2>" + doctor.age + " years old</h2></html>");
        infoPanel.add(ageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel idLabel = new JLabel(doctor.IdToHtml(doctor.Id));
        infoPanel.add(idLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel patientsLabel = new JLabel("have " + doctor.patientsNumber +" patient" +((doctor.patientsNumber>1) ? "s" : ""));
        infoPanel.add(patientsLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;

        JButton editIcon = new JButton("request to change the doctor");
        editIcon.setBackground(new Color(0xFF11FF00, true));
        editIcon.addActionListener(e -> {
            JLabel w = new JLabel("<html>"+fullName+" asks you to change his doctor ("+doctor.fullName+","+doctor.Id+")</html>");
            Main.owner.requests.add(w);
            JOptionPane.showMessageDialog(Main.frame, "the request has been sent");
        });
        infoPanel.add(editIcon, gbc);


        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;

        JButton back = new JButton("request to change the doctor");
        back.setBackground(new Color(0xFF009DFF, true));
        back.addActionListener(e -> {
            Main.frame.getContentPane().removeAll();
            user();
            Main.frame.add(nextApp(appointment),BorderLayout.WEST);
            Main.frame.add(more(appointment.doc),BorderLayout.CENTER);
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        infoPanel.add(back, gbc);

        return infoPanel;
    }

    protected JPanel chA() {
        JPanel changingPanel = new JPanel();
        changingPanel.setLayout(new GridBagLayout());
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
        gbc.gridx = 0;
        changingPanel.add(dayLabel, gbc);

        JLabel monthLabel = new JLabel("Month:");
        gbc.gridx = 1;
        changingPanel.add(monthLabel, gbc);

        JLabel yearLabel = new JLabel("Year:");
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


    protected JPanel seeInfo(){
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel fullNameLabel = new JLabel("<html><b><h1>" + this.fullName + "</h1></b></html>");
        infoPanel.add(fullNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JLabel phoneLabel = new JLabel("Your phone number is "+ this.phoneNumber );
        infoPanel.add(phoneLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JLabel ageLabel = new JLabel("<html><h2>" + this.age + " years old</h2></html>");
        infoPanel.add(ageLabel, gbc);


        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        ImageIcon editImage = new ImageIcon("resources/edit.png");
        Image scaledEditImage = editImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton editIcon = new JButton(new ImageIcon(scaledEditImage));
        editIcon.setBackground(new Color(0xFF11FF00, true));
        editIcon.addActionListener(e -> editPat(0));
        infoPanel.add(editIcon, gbc);

        return infoPanel;
    }



    protected void editPat(int i){
        Main.frame.getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel remarqueLabel = new JLabel("<html><b><h4>if the old information is right than leave the field</h4></b></html>");
        panel.add(remarqueLabel, gbc);

        gbc.gridy=1;
        gbc.gridwidth = 1;
        JLabel name = new JLabel("enter the name");
        panel.add(name,gbc);

        gbc.gridx=1;
        JTextField fullName = new JTextField(20);
        panel.add(fullName,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel phone = new JLabel("enter the phone number");
        panel.add(phone,gbc);

        gbc.gridx=1;
        JTextField phoneField = new JTextField(20);
        panel.add(phoneField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel age = new JLabel("enter the age ");
        panel.add(age,gbc);

        gbc.gridx=1;
        JTextField ageField = new JTextField(20);
        panel.add(ageField,gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        JButton btn = new JButton("submit the changes");
        btn.addActionListener(e->{
            if (!fullName.getText().isEmpty()){
                this.fullName=fullName.getText();
            }
            if (!ageField.getText().isEmpty()){
                this.age=ageField.getText();
            }
            if (!phoneField.getText().isEmpty()){
                this.phoneNumber=phoneField.getText();
            }
            Main.frame.getContentPane().removeAll();
            if (i == 0){
                user();
                Main.frame.add(seeInfo(), BorderLayout.CENTER);
            } else if (i == 1) {
                Main.owner.Owners(true);
                Main.frame.add(Main.owner.editPatient(this,null), BorderLayout.WEST);
            }else if (patDoc != null){
                patDoc.docNav();
                Main.frame.add(Main.owner.editPatient(this,patDoc), BorderLayout.WEST);
            }
            Main.frame.revalidate();
            Main.frame.repaint();
        });
        panel.add(btn,gbc);


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
    public String toString(){
        return (this.fullName + ", " + this.phoneNumber + ", " + this.age  );
    }
}

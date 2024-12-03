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

    public void Owners(boolean B){
        if (B){
            System.out.println("hi owner" +
                    "\nyou can do a lot of things from her " +
                    "\n0: sign out" +
                    "\n1: doctors settings " +
                    "\n2: patient settings" +
                    "\n3: update the name & password");
            int c;
            do {
                c = Main.reader.nextByte();
            }while (c<0||c>3);
            if(c==1)DocSettings();
            else if(c==2) {
                PatientSettings();
            } else if (c==3) {
                change();
            }else {
                Main.signOut();
            }
        }else{
            System.out.println("enter the name ");
            String tempString = Main.reader.next();
            if (!Objects.equals(tempString, name)){
                System.out.println("name is wrong");
                Owners(false);
            }
            else{
                System.out.println("enter the password ");
                tempString = Main.reader.next();
                if (!Objects.equals(tempString, password)){
                    System.out.println("password is wrong");
                    Owners(false);
                }else{
                    Owners(true);
                }
            }
        }
    }


    public void DocSettings() {
        System.out.println("hello in doctors settings" + "\n0: exit to owner page" + "\n1: add a doctor" +
                (this.doctors.isEmpty()
                        ?""
                        :("\n2: see all doctors\n3: delete a doctor\n4: update a doctor")
                ));

        int c;
        do {
            c = Main.reader.nextByte();
        }while (c<0||c>4);

        if (c==0){
            Main.signOut();
        }else if (c==1) {
            Doctor tempDoc = new Doctor();
            System.out.println("personal information " + "\n Doctor full name");
            tempDoc.fullName = Main.reader.next();

            System.out.println("Doctor phone number");
            do{
                tempDoc.phoneNumber = Main.reader.next();
            }while (!tempDoc.phoneNumber.startsWith("0"));

            System.out.println("Doctor age");
            do{
                tempDoc.age = Main.reader.nextByte();
            }while (tempDoc.age<25 || tempDoc.age>80);

            System.out.println("id");
            tempDoc.setId();

            doctors.add(tempDoc);
            System.out.println(tempDoc.fullName + " is in the system");

        } else if (c==2 && !doctors.isEmpty()) {
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
    }


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
            do{
                tempPat.age = Main.reader.nextByte();
            }while (tempPat.age<25 || tempPat.age>80);

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
import java.util.Objects;

public class App {

    public void Choose(Owner owner){
        if (owner.doctors.isEmpty() && owner.patients.isEmpty()){
            owner.Owners(false);
        }else {
            System.out.println("1: owner " +
                    (owner.doctors.isEmpty()?"": "\n2: doctor") +
                    (owner.patients.isEmpty()?"": "\n3: Patient"));
            byte c;
            do{
                c = Main.reader.nextByte();
            }while (c<1 || c>3);

            if(c == 1){
                owner.Owners(false);
            } else if (c == 2) {
                DocUser(null);
            }else{
                PatUser(null);
            }
        }
    }

    protected void DocUser(Doctor doc){
        if(doc==null){
            System.out.println("enter your full name ");
            String tempName = Main.reader.next();

            System.out.println("enter your ID");
            String tempID = Main.reader.next();

            for (Doctor doctor: Main.owner.doctors){
                if (doctor.fullName.equals(tempName) && doctor.Id.equals(tempID)){
                    System.out.println("hi "+doctor.fullName);
                    if (doctor.code == null){
                        System.out.println("this is your first time in the System");
                        doctor.setCode();
                    }else {
                        System.out.println("please enter your password");
                        String tempCode = Main.reader.next();

                        if (!doctor.code.equals(tempCode)){
                            System.out.println("you're wrong");
                            Main.signOut();
                        }

                    }

                    doc = doctor;

                    break;
                }
            }
            if (doc == null){
                System.out.println("there's no doctor with those information");
                App app = new App();
                app.Choose(Main.owner);
            }
        }
        System.out.println("----------------------------\n");

        System.out.println("0: sign out" +
                "\n1: see your information" +
                "\n2: update your password" +
                "\n3: add a patient" +
                (doc.patients.isEmpty()?"":"\n4: see all your patients"));

        byte c;
        do{
            c = Main.reader.nextByte();
        }while (c<0 || c>3);

        if(c == 0){
            Main.signOut();
        }
        else if (c == 1) {
            System.out.println(doc.toString());
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
                doc.setInfo();
            }
            else {
                DocUser(doc);
            }

        }
        else if (c==2) {
            System.out.println("enter your current password");
            String tempCode = Main.reader.next();

            if (doc.code.equals(tempCode)){
                doc.setCode();
            } else {
                System.out.println("you're wrong");
                DocUser(doc);
            }

        }
        else if (c==3){
            Patient tempPat = new Patient();
            tempPat.setAppointment(null,doc);
            System.out.println("personal information " + "\nPatient full name");
            tempPat.fullName = Main.reader.next();

            System.out.println("Patient phone number");
            do{
                tempPat.phoneNumber = Main.reader.next();
            }while (!tempPat.phoneNumber.startsWith("0"));

            System.out.println("Patient age");
 //           do{
 //               tempPat.age = Main.reader.nextByte();
 //           }while (tempPat.age<0);

            tempPat.code = tempPat.fullName + tempPat.age;

            System.out.println("the Patient code is : " + tempPat.code);

            doc.patients.add(tempPat);
            Main.owner.patients.add(tempPat);
            System.out.println(tempPat.fullName + " is in the system");
        }
        else if (c==4 && !doc.patients.isEmpty()){
            doc.getPatients();
        }
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

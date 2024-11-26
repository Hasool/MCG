import java.util.ArrayList;
import java.util.Objects;

public class Owner {
    protected ArrayList<Doctor> doctors ;
    protected ArrayList<Patient> patients ;

    public Owner(){
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
    }

    public void Owners(String name, String password, boolean B){
        if (B){
            System.out.println("hi owner" +
                    "\nyou can do a lot of things from her " +
                    "\n1: doctors settings " +
                    "\n2: patient settings");
            int c;
            do {
                c = Main.reader.nextByte();
            }while (c<1||c>2);
            if(c==1)DocSettings();
            else PatientSettings();
        }else{
            System.out.println("enter the name ");
            String tempString = Main.reader.next();
            if (!Objects.equals(tempString, name)){
                System.out.println("name is wrong");
                Main.main(new String[]{""});
            }else{
                System.out.println("enter the password ");
                tempString = Main.reader.next();
                if (!Objects.equals(tempString, password)){
                    System.out.println("password is wrong");
                    Main.main(new String[]{""});
                }else{
                    System.out.println("hi owner\nyou can do a lot of things from her \n1: doctors settings \n2: patient settings");
                    int c;
                    do {
                        c = Main.reader.nextByte();
                    }while (c<1||c>2);
                    if(c==1)DocSettings();
                    else PatientSettings();
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

        if (c==1) {
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
            tempDoc.Id=Main.reader.next();

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
        }else{
            Owners("admin","admin",true);
        }



    }


    public void PatientSettings() {
        System.out.println("hello in patient settings" + "\n0: exit to owner page" + "\n1: add a patient" +
                (this.patients.isEmpty()
                        ?""
                        :("\n2: see all patients\n3: delete a patient\n4: update a patient")
                ));

        Owners("admin","admin",true);
    }
}

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Doctor extends Human{
    protected String[] Specialties = {
            "GM","C0","P0",
            "OG", "GS","O0",
            "N0","DC", "P1",
            "O1","NU","O2",
            "D0","P2","R0"
    };
    protected String Id;
    protected String code = null;
    protected ArrayList<Patient> patients = new ArrayList<>();
    protected int patientsNumber = 0;




    public void setInfo(){
        System.out.println("-------------------");
        System.out.println("0: sign out" +
                "\n1: update your full name" +
                "\n2: update your phone number" +
                "\n3: update your password" +
                "\n4: return");

        int c;
        do {
            c = Main.reader.nextByte();
        }while (c<0||c>4);
        if(c==0)Main.signOut();
        else if(c==1) {
            setFullName();
            System.out.println("your name now is "+this.fullName);
            Return();
        }
        else if (c==2){
            setPhoneNumber();
            System.out.println("your phone number now is "+this.phoneNumber);
            Return();
        }
        else if (c==3) {
            System.out.println("enter your current password");
            String tempCode = Main.reader.next();

            if (this.code.equals(tempCode)){
                this.setCode();
            } else {
                System.out.println("you're wrong");
                Return();
            }

        }
        else {
            Return();
        }

    }



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


    public void setPatient(Patient patient) {

    }

    public void Return(){
        App app = new App();
        app.DocUser(this);
    }


    public void setCode() {
        System.out.println("Please create a password");
        String tempCode = Main.reader.next();
        String tempCode1;

        do{
            System.out.println("rewrite the password");
            tempCode1 = Main.reader.next();

        }while (!Objects.equals(tempCode, tempCode1));

        this.code = tempCode;
        System.out.println("you have to resign in\n---------------------");
        App app = new App();
        app.DocUser(null);
    }

    public void setId() {




        System.out.println("Choose the Specialty: \n" +
                "1. General Medicine \n2. Cardiology \n3. Pediatrics \n4. Obstetrics and Gynecology " +
                "\n5. General Surgery \n6. Ophthalmology \n7. Neurology \n8. Dermatology and Cosmetology " +
                "\n9. Pulmonology \n10. Otolaryngology \n11. Nephrology and Urology \n12. Oncology " +
                "\n13. Dentistry \n14. Psychiatry \n15. Rheumatology");

        byte c;

        do {
            c = Main.reader.nextByte();
        }while (c<1 || c>15);

        switch (c) {
            case 1:
                Id = Specialties[0];
                break;
            case 2:
                Id = Specialties[1];
                break;
            case 3:
                Id = Specialties[2];
                break;
            case 4:
                Id = Specialties[3];
                break;
            case 5:
                Id = Specialties[4];
                break;
            case 6:
                Id = Specialties[5];
                break;
            case 7:
                Id = Specialties[6];
                break;
            case 8:
                Id = Specialties[7];
                break;
            case 9:
                Id = Specialties[8];
                break;
            case 10:
                Id = Specialties[9];
                break;
            case 11:
                Id = Specialties[10];
                break;
            case 12:
                Id = Specialties[11];
                break;
            case 13:
                Id = Specialties[12];
                break;
            case 14:
                Id = Specialties[13];
                break;
            case 15:
                Id = Specialties[14];
                break;
            default:
                System.out.println("Invalid choice. Please select a number between 1 and 15.");
                setId();
        }

        System.out.println("Enter the year of employment");
        String z2 ;
        do {
            z2 = Main.reader.next();
        }while (z2.length()!=4);

        Id = Id+z2;

        Random random = new Random();
        String z3;
        do {
            int number = random.nextInt(1000);
            z3 = String.format("%03d", number);
        }while (IsNotNew(Id,z3));

        Id = Id + z3;

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

    @Override
    public String toString(){
        return (this.fullName + ", " + this.phoneNumber + ", " + this.age + ", " + this.Id );
    }

}

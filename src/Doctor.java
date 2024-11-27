import java.util.ArrayList;
import java.util.Objects;

public class Doctor extends Human{
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

    public void getPatients() {
        int i =1;
        for (Patient patient : patients) {
            System.out.println(i + ": " + patient.toString());
        }
    }

    @Override
    public String toString(){
        return (this.fullName + ", " + this.phoneNumber + ", " + this.age + ", " + this.patientsNumber );
    }

}

import java.util.ArrayList;
import java.util.Objects;

public class Patient extends Human{
    protected MedicalHistory appointment;
    protected ArrayList<MedicalHistory> medicalHistories;
    protected String code;

    public String getAppointment() {
        return appointment.toString();
    }

    public void getMedicalHistories() {
        for (MedicalHistory medicalHistory : medicalHistories){
            medicalHistory.toString();
        }
    }

    public void setAppointment(Owner owner , Doctor doctor) {
        appointment = new MedicalHistory(owner,doctor);
    }

    public void setInfo() {
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
        app.PatUser(null);
    }

    public void Return(){
        App app = new App();
        app.PatUser(this);
    }

    protected Doctor getDocFromId(String id ){
        for (Doctor doctor : Main.owner.doctors){
            if (Objects.equals(doctor.Id,id)){
                return doctor;
            }
        }
        return null ;
    }

    @Override
    public String toString(){
        return (this.fullName + ", " + this.phoneNumber + ", " + this.age  );
    }
}

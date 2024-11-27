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

        }else if (c==2){

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
    }



    @Override
    public String toString(){
        return (this.fullName + ", " + this.phoneNumber + ", " + this.age + ", " + this.patientsNumber );
    }

}

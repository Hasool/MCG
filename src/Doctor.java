import java.util.ArrayList;

public class Doctor extends Human{
    protected String Id;
    protected ArrayList<Patient> patients;
    protected int patientsNumber = 0;


    @Override
    public String toString(){
        return (this.fullName + ", " + this.phoneNumber + ", " + this.age + ", " + this.patientsNumber );
    }

}

import java.util.ArrayList;

public class Patient extends Human{
    protected MedicalHistory appointment;
    protected ArrayList<MedicalHistory> medicalHistories;
    protected String code;

    @Override
    public String toString(){
        return (this.fullName + ", " + this.phoneNumber + ", " + this.age  );
    }
}

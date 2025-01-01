import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MedicalHistory {
    protected Dmy date;
    protected Doctor doc;
    protected String medicalDiagnosis;
    protected String medicine;


    public MedicalHistory(Doctor doctor) {
            this.doc = doctor;

    }



    @Override
    public String toString() {
        return (
                "<html>the appointment is in "+date.toString()+
                "\n with dr."+doc.fullName+"<html>"
                );
    }


}

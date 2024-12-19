import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MedicalHistory {
    protected LocalDateTime date;
    protected Doctor doc;
    protected String medicalDiagnosis;
    protected String medicine;


    public MedicalHistory(Owner owner,Doctor doctor) {
        if (owner!=null){
            this.doc = assignDoctor();
        }else {
            this.doc = doctor;
        }

    }

     static LocalDateTime createAppointment(String dmy, String hm) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");


        LocalDate date = parseDate(dmy, dateFormatter);


        LocalTime time = parseTime(hm, timeFormatter);

        return LocalDateTime.of(date, time);
    }

    private Doctor assignDoctor() {
        System.out.println("Enter the doctor ID (enter 0 if the patient has no doctor):");
        String ID = Main.reader.next();
        if (!ID.equals("0")) {
            for (Doctor doctor : Main.owner.doctors) {
                if (doctor.Id.equals(ID)) {
                    return doctor;
                }
            }
            System.out.println("There's no doctor with this ID.");
        }
        return null;
    }

    private static LocalDate parseDate(String input, DateTimeFormatter formatter) {
        try {
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use dd/MM/yyyy.");
            return null;
        }
    }

    private static LocalTime parseTime(String input, DateTimeFormatter formatter) {
        try {
            return LocalTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Use HH:mm.");
            return null;
        }
    }

    @Override
    public String toString() {
        return (doc != null) ? doc.fullName : "No doctor assigned.";
    }



    public void setDoc(Doctor doc) {
        this.doc = doc;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

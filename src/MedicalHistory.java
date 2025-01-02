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
        return String.format(
                "<html>The appointment is on %s<br>with Dr. %s<html>",
                date != null ? date.toString() : "N/A",
                doc != null ? doc.fullName : "N/A"
        );
    }
}
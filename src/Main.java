import java.awt.*;import java.time.LocalDate;public class Main {    static Owner owner = new Owner();    static Frame frame = new Frame();    static Color mainBg = new Color(0xbdc0bf);    static Color secondBg = new Color(0x42F3EB);    static Color mainBtn = new Color(0x30B2AD);    static Color secondBtn = new Color(0x6E30B2AD, true);    static Color signOut_back = new Color(0xFF0000);    public static void main(String[] args) {        processPastAppointments();        signOut();    }    private static void processPastAppointments() {        for (Patient patient : owner.patients) {            patient.futureAppointment.removeIf(fA -> {                if (isAppointmentInPast(fA)) {                    patient.medicalHistories.add(fA);                    return true;                }                return false;            });        }    }    /**     * Checks if an appointment date is in the past.     *     * @param appointment The appointment to check.     * @return True if the appointment is in the past, otherwise false.     */    private static boolean isAppointmentInPast(MedicalHistory appointment) {        return appointment.date.getDmy().isBefore(LocalDate.now());    }    /**     * Signs out and transitions to the app's main menu.     */    static void signOut() {        App app = new App();        app.Choose(owner);    }}
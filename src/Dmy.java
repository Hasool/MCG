import java.time.LocalDate;

public class Dmy {
    private final int day;
    private final int month;
    private final int year;

    public Dmy(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public LocalDate getDmy() {
        return LocalDate.of(year, month, day);
    }


    @Override
    public String toString() {
        return day+"/"+month+"/"+year;
    }
}

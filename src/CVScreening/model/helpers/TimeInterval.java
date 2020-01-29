package CVScreening.model.helpers;

import java.time.LocalDate;
import java.time.Period;

public class TimeInterval {

    private LocalDate start;
    private LocalDate end;
    private Period period;

    private TimeInterval() {
    }

    private TimeInterval(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
        this.period = Period.between(start, end);
    }

    public LocalDate getStartDate() {
        return start;
    }

    public LocalDate getEndDate() {
        return end;
    }

    public Period getPeriod() {
        return period;
    }

    public static TimeInterval between(LocalDate start, LocalDate end) {
        return new TimeInterval(start, end);
    }

    public static TimeInterval parse(String timeInterval) {
        String[] split = timeInterval.split(" ");
        LocalDate start = LocalDate.parse(split[1]);
        LocalDate end = LocalDate.parse(split[3]);
        return new TimeInterval(start, end);
    }

    @Override
    public String toString() {
        return "From " + start +
                " to " + end +
                " (" + (period.getYears() > 0 ? period.getYears() + " years and " : "") +
                period.getMonths() + " months)";
    }
}
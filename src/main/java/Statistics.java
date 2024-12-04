import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
public class Statistics {

    int totalTraffic;
    LocalDateTime minTime;
    LocalDateTime maxTime;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
    }

    public void addEntry(LogEntry logEntry) {

        totalTraffic += logEntry.getResponseSize();

        if (minTime == null || logEntry.getTime().isBefore(minTime)) {
            minTime = logEntry.getTime();
        }

        if (maxTime == null || logEntry.getTime().isAfter(maxTime)) {
            maxTime = logEntry.getTime();
        }
    }

    public double getTrafficRate() {

        long hours;

        if (minTime == null || maxTime == null) {
            hours = 0;
        } else {
            hours = Duration.between(minTime, maxTime).toHours();
        }
        return (double) totalTraffic / (hours == 0 ? 1 : hours);
    }
}
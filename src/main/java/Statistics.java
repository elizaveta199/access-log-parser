import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Getter
public class Statistics {

    private final HashSet<String> pages = new HashSet<>();
    private final HashMap<String, Integer> osStatistic = new HashMap<>();

    int totalTraffic;
    LocalDateTime minTime;
    LocalDateTime maxTime;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
    }

    public void addEntry(LogEntry logEntry) {
        if (logEntry.getResponseCode() == 200) {
            pages.add(logEntry.getPath());
        }

        String os = logEntry.getAgent().getOs();
        osStatistic.put(os, osStatistic.getOrDefault(os, 0) + 1);

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

    public HashSet<String> getAllPages() {
        return pages;
    }

    public HashMap<String, Double> getOsStatistics() {
        int totalOsCount = osStatistic.values().stream().mapToInt(Integer::intValue).sum();
        HashMap<String, Double> osStatsPercentage = new HashMap<>();

        for (Map.Entry<String, Integer> entry : osStatistic.entrySet()) {
            double percentage = (double) entry.getValue() / totalOsCount;
            osStatsPercentage.put(entry.getKey(), percentage);
        }
        return osStatsPercentage;
    }
}
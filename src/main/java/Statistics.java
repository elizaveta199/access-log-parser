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
    private final HashSet<String> notFoundPages = new HashSet<>();
    private final HashMap<String, Integer> browserStatistic = new HashMap<>();

    long totalTraffic;
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

        if (logEntry.getResponseCode() == 404) {
            notFoundPages.add(logEntry.getPath());
        }

        String browser = logEntry.getAgent().getBrowser();
        browserStatistic.put(browser, browserStatistic.getOrDefault(browser, 0) + 1);

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

    public HashSet<String> getAllNotFoundPages() {
        return notFoundPages;
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

    public HashMap<String, Double> getBrowserStatistic() {
        int totalBrowserCount = browserStatistic.values().stream().mapToInt(Integer::intValue).sum();
        HashMap<String, Double> browserStatisticPercentage = new HashMap<>();

        for (Map.Entry<String, Integer> entry : browserStatistic.entrySet()) {
            double percentage = (double) entry.getValue() / totalBrowserCount;
            browserStatisticPercentage.put(entry.getKey(), percentage);
        }
        return browserStatisticPercentage;
    }
}
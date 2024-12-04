import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class LogEntry {

    private final String ipAddr;
    private final LocalDateTime time;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final UserAgent agent;


    public enum HttpMethod {
        GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, CONNECT
    }

    public LogEntry(String log) {
        String[] parts = log.split(" ");
        this.ipAddr = parts[0];

        String timeString = log.substring(log.indexOf("[") + 1, log.indexOf("]"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        this.time = LocalDateTime.parse(timeString, formatter);

        String requestString = log.split("\"")[1];
        String[] requestParts = requestString.split(" ");
        this.method = HttpMethod.valueOf(requestParts[0].toUpperCase());
        this.path = requestParts[1];

        this.responseCode = Integer.parseInt(parts[8]);

        String responseSizeString = parts[9];
        this.responseSize = responseSizeString.equals("-") ? 0 : Integer.parseInt(responseSizeString);


        String refererString = log.split("\"")[3];
        this.referer = refererString.equals("-") ? null : refererString;

        String[] partsUA = log.split("\"");
        this.agent = new UserAgent(partsUA[5]);
    }
}
import lombok.Getter;

@Getter
public class UserAgent {
    private final String os;
    public final String browser;

    public UserAgent(String userAgentString) {
        this.os = extractOS(userAgentString);
        this.browser = extractBrowser(userAgentString);
    }

    public String extractOS(String userAgentString) {
        if (userAgentString.contains("Windows NT")) {
            return "Windows";
        } else if (userAgentString.contains("Macintosh")) {
            return "macOS";
        } else if (userAgentString.contains("Linux")) {
            return "Linux";
        } else {
            return "Unknown";
        }
    }

    public String extractBrowser(String userAgentString) {
        if (userAgentString.contains("Edg")) {
            return "Edge";
        } else if (userAgentString.contains("Firefox")) {
            return "Firefox";
        } else if (userAgentString.contains("Chrome")) {
            return "Chrome";
        } else if (userAgentString.contains("Opera")) {
            return "Opera";
        } else {
            return "Unknown";
        }
    }

    public boolean isYandexBot(String userAgentString) {
        return userAgentString.contains("YandexBot");
    }

    public boolean isGooglebot(String userAgentString) {
        return userAgentString.contains("Googlebot");
    }
}
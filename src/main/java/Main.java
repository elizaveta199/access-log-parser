import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        while (true) {
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (isDirectory) {
                System.out.println("Путь указан к папке, а не к файлу");
                continue;
            }
            if (fileExists) {
                count += 1;
                System.out.println("Файл существует, путь указан верно");
                System.out.println("Это файл номер " + count);

                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader =
                            new BufferedReader(fileReader);
                    String line;
                    int totalLines = 0;
                    int googlebotCount = 0;
                    int yandexBotCount = 0;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        if (length > 1024) {
                            throw new LineTooLongException("В файле есть строка длиннее 1024 символов. ");
                        }
                        String[] parts = line.split("\"");
                        if (parts.length > 5) {
                            String userAgent = parts[5];
                            int startLine = userAgent.indexOf('(');
                            int endLine = userAgent.indexOf(')');
                            if (startLine != -1 && endLine != -1 && startLine < endLine) {
                                String firstBrackets = userAgent.substring(startLine + 1, endLine);
                                String[] userAgentParts = firstBrackets.split(";");
                                if (userAgentParts.length >= 2) {
                                    for (int i = 0; i < userAgentParts.length; i++) {
                                        userAgentParts[i] = userAgentParts[i].trim();
                                    }
                                    String fragment = userAgentParts[1];
                                    String bot = fragment.split("/")[0];
                                    if (bot.equalsIgnoreCase("Googlebot")) {
                                        googlebotCount++;
                                    } else if (bot.equalsIgnoreCase("YandexBot")) {
                                        yandexBotCount++;
                                    }
                                }
                            }
                        }
                        totalLines++;
                    }
                    System.out.println("Общее количество строк в файле: " + totalLines);
                    System.out.println("Количество запросов от Googlebot: " + googlebotCount);
                    System.out.println("Количество запросов от YandexBot: " + yandexBotCount);
                    double googleDivTotal = (double) googlebotCount / totalLines;
                    double yandexDivTotal = (double) yandexBotCount / totalLines;
                    System.out.println("Доля запросов от Googlebot: " + googleDivTotal);
                    System.out.println("Доля запросов от YandexBot: " + yandexDivTotal);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                System.out.println("Файл не существует или указан неверный путь");
            }
        }
    }
}

class LineTooLongException extends RuntimeException {
    public LineTooLongException(String message) {
        super(message);
    }
}
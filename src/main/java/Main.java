import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        Statistics statistics = new Statistics();
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

                        LogEntry logEntry = new LogEntry(line);
                        if (logEntry.getIpAddr() == null) {
                            continue;
                        }
                        statistics.addEntry(logEntry);
                        String[] partsUA = line.split("\"");
                        if (logEntry.getAgent().isGooglebot(partsUA[5])) {
                            googlebotCount++;
                        } else if (logEntry.getAgent().isYandexBot(partsUA[5])) {
                            yandexBotCount++;
                        } else {
                            continue;
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
                    System.out.println("Объем данных общий: " + statistics.getTotalTraffic());
                    System.out.println("Средний объем трафика за час: " + statistics.getTrafficRate());
                    //System.out.println("Список всех страниц: " + statistics.getAllPages());
                    System.out.println("Статистика операционных систем: " + statistics.getOsStatistics());
                    System.out.println("Список всех ненайденных страниц: " + statistics.getAllNotFoundPages());
                    System.out.println("Статистика браузеров: " + statistics.getBrowserStatistic());


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
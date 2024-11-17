import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
                    int maxLength = 0;
                    int minLength = Integer.MAX_VALUE;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        if (length > 1024) {
                            throw new LineTooLongException("В файле есть строка длиннее 1024 символов. ");
                        }
                        totalLines++;
                        if (length > maxLength) {
                            maxLength = length;
                        }
                        if (length < minLength) {
                            minLength = length;
                        }
                    }

                    System.out.println("Общее количество строк в файле: " + totalLines);
                    System.out.println("Длина самой длинной строки в файле: " + maxLength);
                    System.out.println("Длина самой короткой строки в файле: " + minLength);

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
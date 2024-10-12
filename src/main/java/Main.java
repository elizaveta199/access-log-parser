import java.io.File;
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
            } else {
                System.out.println("Файл не существует или указан неверный путь");
            }
        }
    }
}
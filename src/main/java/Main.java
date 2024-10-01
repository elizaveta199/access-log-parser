import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число: ");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число: ");
        int secondNumber = new Scanner(System.in).nextInt();

        System.out.print("Сумма чисел = ");
        System.out.println(firstNumber + secondNumber);

        System.out.print("Разность чисел = ");
        System.out.println(firstNumber - secondNumber);

        System.out.print("Произведение чисел = ");
        System.out.println(firstNumber * secondNumber);

        System.out.print("Частное чисел = ");
        System.out.println((double) firstNumber / secondNumber);
    }
}

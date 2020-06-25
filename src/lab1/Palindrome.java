package lab1;

import java.util.Scanner;
/**
 * @author Myachins
 */
public class Palindrome {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); // Объявляем Scanner
        System.out.println("Введиет количество слов: ");
        int size = scan.nextInt()+1 ;
        String[] s = new String[size]; // Создаём массив s размером в size
        System.out.println("Введите строки:");
        for (int i = 0; i < size; i++) {
            s[i] = scan.nextLine(); // Заполняем массив элементами, введёнными с клавиатуры
        }


        for (String i:s) {
            if (isPalindrome(i) == true){
                System.out.println("<" + i + ">" + " является палиндромом");
            } else {
                System.out.println("<" + i + ">" + " не является палиндромом");
            }
        }
    }
    /** Переворот строки */
    public static String reverseString(String s) {
        String b = ("");
        for (int i = 0; i < s.length(); i++) {
            b += s.charAt(s.length()-i-1);
        }
        return b;
    }
    /** Проверяет равенсто строки с перевернутой строкой */
    public static boolean isPalindrome(String s){
        if (s.equals(reverseString(s))){
            return true;
        } else {
            return false;
        }
    }
}
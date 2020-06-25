package lab1;

public class Primes {

    public static void main(String[] args) {
        System.out.println("Простые числа:");
        /** цикл,отправляет число в метод IsPrime и выводит его
         * если оно является простым  */
        for (int i = 2; i < 100; i++) {
            if (IsPrime(i) == true) {
                System.out.println(i);
            }
        }
    }
    /** метот проверяет число на правильность
     * (если число делится без остатка только на себя) */
    public static boolean IsPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String first = scanner.nextLine().toLowerCase();
        String second = scanner.nextLine().toLowerCase();

        Map<Character, Integer> firstMap = new HashMap<>();
        Map<Character, Integer> secondMap = new HashMap<>();


        for (int i = 0; i < first.length(); i++) {
            Character currentChar = first.charAt(i);
            firstMap.put(currentChar, firstMap.getOrDefault(currentChar, 0) + 1);
        }
        for (int i = 0; i < second.length(); i++) {
            Character currentChar = second.charAt(i);
            secondMap.put(currentChar, secondMap.getOrDefault(currentChar, 0) + 1);
        }

        System.out.println(firstMap.equals(secondMap) ? "yes" : "no");

    }
}
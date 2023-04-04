import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String availableWords = scanner.nextLine();
        String wordsToScribe = scanner.nextLine();

        var availableWordMap = generateCharCountMap(availableWords);
        var wordToScribeMap = generateCharCountMap(wordsToScribe);

        for (char x : wordToScribeMap.keySet()) {
            if (wordToScribeMap.get(x) > availableWordMap.getOrDefault(x, 0)) {
                System.out.println("You are busted");
                return;
            }
        }
        System.out.println("You get money");
    }


    private static Map<Character, Integer> generateCharCountMap(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        return map;
    }
}
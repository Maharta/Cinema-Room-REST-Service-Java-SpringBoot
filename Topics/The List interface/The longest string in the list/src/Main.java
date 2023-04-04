import java.util.*;

public class Main {

    static void changeList(List<String> list) {
        String longest = "";

        for (String s : list) {
            if (s.length() > longest.length()) {
                longest = s;
            }
        }

        Collections.fill(list, longest);
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        List<String> lst = Arrays.asList(s.split(" "));
        changeList(lst);
        lst.forEach(e -> System.out.print(e + " "));
    }
}
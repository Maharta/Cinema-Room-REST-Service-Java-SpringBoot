import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        List<Integer> numbers = Arrays
                .stream(input.split(" ")).map(Integer::parseInt)
                .collect(Collectors.toList());

        int n = scanner.nextInt();

        List<Integer> closeNumbers = new ArrayList<>(numbers.size());
        int smallestDistance = Integer.MAX_VALUE;

        for (Integer number : numbers) {
            int distance = Math.abs(n - number);
            if (distance < smallestDistance) {
                smallestDistance = distance;
                closeNumbers.clear();
                closeNumbers.add(number);
            } else if (distance == smallestDistance) {
                closeNumbers.add(number);
            }
        }


        closeNumbers.sort(Comparator.naturalOrder());
        for (Integer closeNumber : closeNumbers) {
            System.out.print(closeNumber + " ");
        }

    }
}
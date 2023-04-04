import java.util.List;

class Counter {

    public static boolean checkTheSameNumberOfTimes(int elem, List<Integer> list1, List<Integer> list2) {
        // implement the method
        int firstCount = 0;
        for (Integer integer : list1) {
            if (integer.equals(elem)) {
                firstCount++;
            }
        }

        int secondCount = 0;
        for (Integer integer : list2) {
            if (integer.equals(elem)) {
                secondCount++;
            }
        }

        return firstCount == secondCount;
    }
}
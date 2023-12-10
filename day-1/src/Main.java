import java.io.*;
import java.util.ArrayList;

public class Main {
    ArrayList<String> dataList = new ArrayList<>();

    public void readData() throws IOException {
        File file = new File("input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null)
            dataList.add(st);
    }


    private int resolveTask(boolean isTask2) {
        int sum = 0;

        for (String row : dataList) {
            ArrayList<String> numbersInRow = new ArrayList<>();
            if (isTask2) row = detectDigitsFromString(row);
            String[] chars = row.split("");
            for (String c : chars) {
                try {
                    int num = Integer.parseInt(c);
                    numbersInRow.add(String.valueOf(num));
                } catch (NumberFormatException ignored) {
                }
            }

            String firstValue = numbersInRow.getFirst();
            String lastValue = numbersInRow.getLast();
            sum += Integer.parseInt(firstValue + lastValue);
        }

        return sum;
    }

    private int resolveTask1() {
        return resolveTask(false);
    }

    private int resolveTask2() {
        return resolveTask(true);
    }

    private String detectDigitsFromString(String digitRow) {
        digitRow = digitRow.replaceAll("one", "o1ne");
        digitRow = digitRow.replaceAll("two", "tw2o");
        digitRow = digitRow.replaceAll("three", "thr3ee");
        digitRow = digitRow.replaceAll("four", "fo4ur");
        digitRow = digitRow.replaceAll("five", "fi5ve");
        digitRow = digitRow.replaceAll("six", "si6x");
        digitRow = digitRow.replaceAll("seven", "se7ven");
        digitRow = digitRow.replaceAll("eight", "ei8ght");
        digitRow = digitRow.replaceAll("nine", "ni9ne");
        return digitRow;
    }


    public static void main(String[] args) throws IOException {
        Main program = new Main();
        program.readData();
        System.out.println(program.resolveTask1());
        System.out.println(program.resolveTask2());
    }
}
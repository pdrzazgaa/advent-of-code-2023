import java.io.*;
import java.util.ArrayList;

public class Main {
    ArrayList<String> dataList = new ArrayList<>();
    public void readData() throws IOException{
        File file = new File("input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null)
            dataList.add(st);
    }

    public int resolveTask1(){
        int sum = 0;

        for (String row: dataList){
            ArrayList<String> numbersInRow = new ArrayList<>();
            String[] chars = row.split("");
            for (String c: chars){
                try {
                    int num = Integer.parseInt(c);
                    numbersInRow.add(String.valueOf(num));
                } catch (NumberFormatException ignored) {}
            }

            String firstValue = numbersInRow.getFirst();
            String lastValue = numbersInRow.getLast();
            sum += Integer.parseInt(firstValue+lastValue);
        }

        return sum;
    }

    public static void main(String[] args) throws IOException {
         Main program = new Main();
         program.readData();
        System.out.println(program.resolveTask1());
    }
}
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    List<List<String>> charsMatrix = new ArrayList<>();
    public void readData() throws IOException {
        File file = new File("input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null){
            charsMatrix.add(List.of(st.split("")));
        }
    }

    public int resolveTask1(){
        int sum = 0;
        for (int rowIndex = 0; rowIndex < charsMatrix.size(); rowIndex++){
            List<String> row = charsMatrix.get(rowIndex);
            for (int charIndex = 0; charIndex < charsMatrix.size(); charIndex++){
                try {
                    int num = Integer.parseInt(row.get(charIndex));
                    List<Integer> indexes = getNumbersIndexes(row, charIndex);
                    boolean isComponent = false;
                    for (int index:indexes) {
                        isComponent = isComponent | isDigitConnected(rowIndex, index);
                    }
                    if (isComponent) {
                        String strNumber = "";
                        for (int index:indexes) {
                            strNumber += row.get(index);
                            charIndex = index;
                        }
                        sum += Integer.parseInt(strNumber);
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        return sum;
    }

    private List<Integer> getNumbersIndexes(List<String> chars, int startIndex){
        List<Integer> indexes = new ArrayList<>();
        do{
            try{
                Integer.parseInt(chars.get(startIndex));
                indexes.add(startIndex++);
            } catch (NumberFormatException e) {
                break;
            }
        }while(startIndex < chars.size());
        return indexes;
    }

    private boolean isDigitConnected(int row, int column){
        int top, bottom, left, right;
        if (row != charsMatrix.size()-1) bottom = row + 1; else bottom = row;
        if (row != 0) top = row - 1; else top = row;
        if (column != charsMatrix.get(row).size() - 1) right = column + 1; else right = column;
        if (column != 0) left = column - 1; else left = column;
        boolean isDigitConnected = false;
        for (int i = top; i <= bottom; i++){
            for (int j = left; j <= right; j++){
                if ((i != row || j != column) && !charsMatrix.get(i).get(j).equals(".")) {
                    try {
                        Integer.parseInt(charsMatrix.get(i).get(j));
                    } catch (NumberFormatException e) {
                        isDigitConnected = true;
                        break;
                    }
                }
            }
        }
        return isDigitConnected;
    }

    public static void main(String[] args) throws IOException {
        Main program = new Main();
        program.readData();
        System.out.println(program.resolveTask1());
    }
}
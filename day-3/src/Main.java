import java.io.*;
import java.util.*;

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
                    Integer.parseInt(row.get(charIndex));
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

    public int resolveTask2(){
        int sum = 0;
        HashMap<Coordinates, List<Integer>> starsNumbers = new HashMap<>();
        Set<Coordinates> starsCoordinates = new HashSet<>();
        for (int rowIndex = 0; rowIndex < charsMatrix.size(); rowIndex++){
            List<String> row = charsMatrix.get(rowIndex);
            for (int charIndex = 0; charIndex < charsMatrix.size(); charIndex++){
                try {
                    Integer.parseInt(row.get(charIndex));
                    // getting indexes of number
                    List<Integer> indexes = getNumbersIndexes(row, charIndex);
                    boolean isComponent = false;
                    for (int index:indexes) {
                        // Checking if number touches *
                        isComponent = isComponent | isStarConnectedWithTwoNumbers(rowIndex, index, starsCoordinates);
                    }
                    if (isComponent) {
                        // Creating number
                        String strNumber = "";
                        for (int index:indexes) {
                            strNumber += row.get(index);
                            charIndex = index;
                        }
                        int number = Integer.parseInt(strNumber);
                        // Added number to location point of *
                        for (Coordinates coordinates : starsCoordinates) {
                            if (starsNumbers.containsKey(coordinates)) {
                                starsNumbers.get(coordinates).add(number);
                            }
                            else {
                                List<Integer> list = new ArrayList<>();
                                list.add(number);
                                starsNumbers.put(coordinates, list);
                            }
                            starsCoordinates.clear();
                        }
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        // Computing the sum of multiplications of 2 numbers
        for (Coordinates coordinates: starsNumbers.keySet()){
            int multiplication = 1;
            if (starsNumbers.get(coordinates).size() == 2){
                for (int num : starsNumbers.get(coordinates))
                    multiplication*=num;
                sum += multiplication;
            }
        }
        return sum;
    }

    private boolean isStarConnectedWithTwoNumbers(int row, int column, Set<Coordinates> starsAmount){
        int top, bottom, left, right;
        if (row != charsMatrix.size()-1) bottom = row + 1; else bottom = row;
        if (row != 0) top = row - 1; else top = row;
        if (column != charsMatrix.get(row).size() - 1) right = column + 1; else right = column;
        if (column != 0) left = column - 1; else left = column;
        boolean isNextToStar = false;
        for (int i = top; i <= bottom; i++){
            for (int j = left; j <= right; j++){
                if (i != row || j != column) {
                    if (charsMatrix.get(i).get(j).equals("*")) {
                        starsAmount.add(new Coordinates(i, j));
                        isNextToStar = true;
                    }
                }
            }
        }
        return isNextToStar;
    }

    public static void main(String[] args) throws IOException {
        Main program = new Main();
        program.readData();
        System.out.println(program.resolveTask1());
        System.out.println(program.resolveTask2());
    }
}
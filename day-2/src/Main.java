import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    HashMap<Integer, ArrayList<ArrayList<HashMap<String,Integer>>>> data = new HashMap<>();
    HashMap<String, Integer> colorsAmount;

    public Main() {
        colorsAmount = new HashMap<>();
        colorsAmount.put("blue", 14);
        colorsAmount.put("green", 13);
        colorsAmount.put("red", 12);
    }

    public void readData() throws IOException {
        File file = new File("input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        String[] t1, t2, t3;
        ArrayList<ArrayList<HashMap<String,Integer>>> roundsInGame;
        ArrayList<HashMap<String,Integer>> turnsInRound;
        while ((st = br.readLine()) != null){
            st = st.replaceAll("Game ", "");
            t1 = st.split(": ");
            int gameIndex = Integer.parseInt(t1[0]);
            roundsInGame = new ArrayList<>();
            t1 = t1[1].split("; ");
            for (String round : t1){
                turnsInRound = new ArrayList<>();
                t2 = round.split(", ");
                for (String turn: t2){
                    t3 = turn.split(" ");
                    HashMap<String, Integer> map = new HashMap<>();
                    map.put(t3[1], Integer.parseInt(t3[0]));
                    turnsInRound.add(map);
                }
                roundsInGame.add(turnsInRound);
            }
            data.put(gameIndex, roundsInGame);
        }
    }

    public int resolveTask1(){
        int sum = 0;
        for (int game: data.keySet()){
            boolean isPlayable = true;
            for (ArrayList<HashMap<String,Integer>> round: data.get(game)){
                for (HashMap<String,Integer> turn: round){
                    if (turn.get("red") != null && colorsAmount.get("red") < turn.get("red")) isPlayable = false;
                    if (turn.get("blue") != null && colorsAmount.get("blue") < turn.get("blue")) isPlayable = false;
                    if (turn.get("green") != null && colorsAmount.get("green") < turn.get("green")) isPlayable = false;
                }
            }
            if (isPlayable) sum+=game;
        }
        return sum;
    }

    public int resolveTask2(){
        int sum = 0;
        for (int game: data.keySet()){
            HashMap<String, Integer> minSets = new HashMap<>();
            minSets.put("red", 0);
            minSets.put("green", 0);
            minSets.put("blue", 0);
            for (ArrayList<HashMap<String,Integer>> round: data.get(game)){
                for (HashMap<String,Integer> turn: round){
                    if (turn.get("red") != null && minSets.get("red") < turn.get("red")) minSets.put("red", turn.get("red"));
                    if (turn.get("blue") != null && minSets.get("blue") < turn.get("blue")) minSets.put("blue", turn.get("blue"));
                    if (turn.get("green") != null && minSets.get("green") < turn.get("green")) minSets.put("green", turn.get("green"));
                }
            }
            sum+=minSets.get("red")*minSets.get("green")*minSets.get("blue");
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        Main program = new Main();
        program.readData();
        System.out.println(program.resolveTask1());
        System.out.println(program.resolveTask2());
    }
}
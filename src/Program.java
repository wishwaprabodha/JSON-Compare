import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Program {


    public static Map<String, Object> temp = new TreeMap<>();

    public static void main(String[] args) {
        Program program = new Program();
        String out = " ";
        int flag;
        JSONScanner jsonScanner = new JSONScanner(System.in);
        while (jsonScanner.hasNext()) {
            String token = jsonScanner.next();
            out = out + token;
        }
        if (out.indexOf("[") > 0) {
            flag = 2;
        } else {
            flag = 1;
        }
        jsonScanner = new JSONScanner(out);
        Map<String, Object> finalM1 = program.determineJSON(jsonScanner, flag);
        temp = new TreeMap<>();
        Map<String, Object> finalM2 = program.determineJSON(jsonScanner, flag);

        String m1 = finalM1.toString().replace('=', ':');
        String m2 = finalM2.toString().replace('=', ':');
        System.out.println("************************");


        if (program.compareJSON(finalM1, finalM2)) {
            System.out.println(m1);
            System.out.println("Equal");
        } else {
            System.out.println(m1);
            System.out.println(m2);
            System.out.println("NOT Equal");
        }

    }

    public Map<String, Object> sortedSimpleJSON(JSONScanner jsonScanner) {
        String input = jsonScanner.next();
        if (input.equals("{") || input.equals(",")) {
            String key = jsonScanner.next();
            if (jsonScanner.next().equals(":")) {
                String value = jsonScanner.next();
                temp.put(key, value);
                temp = sortedSimpleJSON(jsonScanner);
            }
        }

        return temp;
    }

    public Map<String, Object> sortedSimpleArrayJSON(JSONScanner jsonScanner) {
        List<String> values = new ArrayList<>();
        String input = jsonScanner.next();
        if (input.equals("{") || input.equals(",")) {
            String key = jsonScanner.next();
            if (jsonScanner.next().equals(":")) {
                String value = jsonScanner.next();
                if (value.equals("[")) {
                    String condition = jsonScanner.next();
                    while (!condition.equals("]")) {
                        if (condition.equals(",")) {
                            condition = jsonScanner.next();
                        }
                        values.add(condition);
                        condition = jsonScanner.next();
                    }
                    temp.put(key, values);
                    temp = sortedSimpleArrayJSON(jsonScanner);
                } else {
                    String K = key.replace('"', ' ');
                    String V = value.replace('"', ' ');
                    temp.put(K, V);
                    temp = sortedSimpleArrayJSON(jsonScanner);
                }
            }
        } else if (input.equals("}")) {
            return temp;
        }
        return temp;
    }

    public boolean compareJSON(Map<String, Object> m1, Map<String, Object> m2) {
        return m1.equals(m2);
    }

    public Map<String, Object> determineJSON(JSONScanner jsonScanner, int flag) {
        Map<String, Object> myMap = new TreeMap<>();
        if (flag == 1) {
            myMap = sortedSimpleJSON(jsonScanner);
        } else if (flag == 2) {
            myMap = sortedSimpleArrayJSON(jsonScanner);
        } else {
            System.out.println("Invalid Input");
        }
        return myMap;
    }
}

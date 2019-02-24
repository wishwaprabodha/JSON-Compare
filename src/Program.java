import java.util.*;

public class Program {



    public static Map<String,Object> temp=new TreeMap<>();


    public Map<String,Object> sortedSimpleJSON(JSONScanner jsonScanner){
        String input = jsonScanner.next();
        if (input.equals("{")||input.equals(",")) {
            String key = jsonScanner.next();
            if (jsonScanner.next().equals(":")) {
                String value = jsonScanner.next();
                String K = key.replace('"', ' ');
                String V = value.replace('"', ' ');
                temp.put(key, value);
                temp=sortedSimpleJSON(jsonScanner);
            }
        }

        return temp;
    }


    public Map<String,Object> sortedSimpleArrayJSON(JSONScanner jsonScanner){
        List<String> values=new ArrayList<>();
        String input = jsonScanner.next();
        if (input.equals("{")||input.equals(",")) {
            String key = jsonScanner.next();
            if (jsonScanner.next().equals(":")) {
                String value = jsonScanner.next();
                if(value.equals("[")){
                    String condition=jsonScanner.next();
                    while (!condition.equals("]")){
                        if (condition.equals(",")){
                            condition=jsonScanner.next();
                            }
                        values.add(condition);
                        condition=jsonScanner.next();
                        }
                    String K = key.replace('"', ' ');
                    for(String it:values){
                        it = value.replace('"', ' ');
                        }
                    temp.put(K, values);
                    temp=sortedSimpleArrayJSON(jsonScanner);
                    }
                else {
                    String K = key.replace('"', ' ');
                    String V = value.replace('"', ' ');
                    temp.put(K, V);
                    temp = sortedSimpleArrayJSON(jsonScanner);
                    }
            }
        }
        else if(input.equals("}")) {
            return temp;
        }
        return temp;
    }

    public boolean compareJSON(Map<String,Object> m1,Map<String,Object> m2){
        if(m1.equals(m2)){
            return true;
        }
        return false;
    }

    public Map<String,Object> determineJSON(JSONScanner jsonScanner,int flag){
        Map<String,Object> myMap=new TreeMap<>();
        if (flag==1) {
            myMap = sortedSimpleJSON(jsonScanner);
        }
        else if (flag==2) {
            myMap = sortedSimpleArrayJSON(jsonScanner);
        }
        else {
            System.out.println("Invalid Input");
        }
        return myMap;
    }


    public static void main(String args[]) {
        Program program=new Program();
        System.out.println("Enter '1' for Simple JSON, Enter '2' for Array JSON ");
        Scanner scanner=new Scanner(System.in);
        int flag=scanner.nextInt();
        System.out.println("Enter First Value: ");
        JSONScanner jsonScanner1 = new JSONScanner(System.in);
        Map<String,Object> finalM1=program.determineJSON(jsonScanner1,flag);
        temp=new TreeMap<>();
        System.out.println("Enter Second Value: ");
        JSONScanner jsonScanner2 = new JSONScanner(System.in);
        Map<String,Object> finalM2=program.determineJSON(jsonScanner2,flag);

        if (program.compareJSON(finalM1,finalM2)){
            System.out.println("Equal");
            }
        else {
            System.out.println("NOT Equal");
            }
        String m1=finalM1.toString().replace('=',':');
        String m2=finalM2.toString().replace('=',':');
        System.out.println("************************");
        System.out.println(m1);
        System.out.println(m2);
    }
}

import java.util.*;

public class Program {



    Map<String,Object> temp=new TreeMap<>();


    public Map<String,Object> sortedSimpleJSON(JSONScanner jsonScanner){
        String input = jsonScanner.next();
        if (input.equals("{")||input.equals(",")) {
            String key = jsonScanner.next();
            if (jsonScanner.next().equals(":")) {
                String value = jsonScanner.next();
                String K = key.replace('"', ' ');
                String V = value.replace('"', ' ');
                this.temp.put(K, V);
                this.temp=sortedSimpleJSON(jsonScanner);
            }
        }
        else if(input.equals("}")) {
            return this.temp;
        }
        return this.temp;
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
                    this.temp.put(K, values);
                    this.temp=sortedSimpleArrayJSON(jsonScanner);
                    }
                else {
                    String K = key.replace('"', ' ');
                    String V = value.replace('"', ' ');
                    this.temp.put(K, V);
                    this.temp = sortedSimpleArrayJSON(jsonScanner);
                    }
            }
        }
        else if(input.equals("}")) {
            return this.temp;
        }
        return this.temp;
    }

    public boolean compareJSON(Map<String,Object> m1,Map<String,Object> m2){
        if(m1.equals(m2)){
            return true;
        }
        return false;
    }


    public static void main(String args[]) {
        Program program=new Program();
        JSONScanner jsonScanner1 = new JSONScanner(System.in);
        Map<String,Object> finalM1=program.sortedSimpleJSON(jsonScanner1);
        JSONScanner jsonScanner2 = new JSONScanner(System.in);
        Map<String,Object> finalM2=program.sortedSimpleJSON(jsonScanner2);
        if (program.compareJSON(finalM1,finalM2)){
            System.out.println("Equal");
            }
        else {
            System.out.println("NOT Equal");
            }
        System.out.println("************************");
        System.out.println(finalM1.toString());
        System.out.println(finalM2.toString());
    }
}

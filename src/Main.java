import java.util.*;

public class Main {

    /*Funtion that reads token from JSONScanner and use recursive approach
    * get the tokens and categorize accordingly.
    * Function accepts a jsonScanner object as a parameter and returns
    * an Object
    * */


    private static Object getObject(JSONScanner jsonScanner) {
        String part = jsonScanner.next();


        /**
         * switch statement is used to break into code with defined singletons.
         * */
        switch (part) {
            case "{": {

                /*
                * A Map with key,value pairs in <String, Object> type is used
                * as the primary data structure
                * */
                 Map<String, Object> child = new TreeMap<>();

                String condition = part;
                Object value;

                while (!condition.equals("}")) {
                    String key = jsonScanner.next();
                    jsonScanner.next(); // remove ':'
                    //Recursive call
                    value = getObject(jsonScanner);
                    child.put(key, value);

                    condition = jsonScanner.next(); // ',' | '}'
                }

                return child;
            }
            case "[": {
                // list
                List<Object> elements = new ArrayList<>();

                Object condition = part;

                while (!condition.equals("]")) {
                    Object value = getObject(jsonScanner);
                    elements.add(value);

                    condition = jsonScanner.next(); // ',' | ']'
                }

                return elements;
            }
            default:
                return part; // string | number | boolean | null

        }
    }

            // Main Function
    public static void main(String[] args) {

        // instantiating the JSONScanner
        JSONScanner jsonScanner1 = new JSONScanner(System.in);

        Object object1 = getObject(jsonScanner1);
        Object object2 = getObject(jsonScanner1);

        boolean status = object1.equals(object2);
        String m1=object1.toString().replace('=',':');
        String m2=object2.toString().replace('=',':');

        if (status) {
            System.out.println("Objects are equal");
            System.out.println(m2);
        } else {
            System.out.println("Objects are not equal");
            System.out.println(m1);
            System.out.println(m2);
        }


        System.out.println("**********************");


    }
}

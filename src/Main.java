import java.util.*;

public class Main {

    private static Object getObject(JSONScanner jsonScanner) {
        String part = jsonScanner.next();

        switch (part) {
            case "{": {
                // object
                Map<String, Object> child = new TreeMap<>();

                String condition = part;
                Object value;

                while (!condition.equals("}")) {
                    String key = jsonScanner.next();
                    jsonScanner.next(); // remove ':'
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

    public static void main(String[] args) {
        // write your code here
        JSONScanner jsonScanner1 = new JSONScanner(System.in);
        //JSONScanner jsonScanner2 = new JSONScanner(System.in);

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

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class JSONScanner {
    final static char [] singletons =  { '{', '}', '[', ']', ',', ':' };

    static {
        Arrays.sort(singletons);
    }

    private char [] obj;       // character array containing the JSON object
    private int idx = 0;       // current index

    /* This constructor takes a String, whose contents is to be tokenized.
     * Param: s : String, the contents of which is to be tokenized
     */
    public JSONScanner( String s ) {
        obj = s.toCharArray();
    }


    /* This constructor takes a InputStream object (such as System.in, whose
     * contents is to be tokenized.
     * Param: s : InputStream, the contents of which is to be tokenized
     */
    public JSONScanner( InputStream in ) {
        Scanner sin = new Scanner(in);
        String s = "";
        while (sin.hasNextLine()) {
            String line = sin.nextLine();
            if (line.equals("EOF") || line.equals("EOF\n")) {
                break;
            }
            s = s + line;
        }
        obj = s.toCharArray();
    }


    /* Returns the next JSON token in the stream.
     * Returns: String: the next token or exits if no more tokens are available
     */
    public String next() {
        for (; idx < obj.length; idx++) {
            if (Arrays.binarySearch(singletons,obj[idx]) >= 0) {
                idx++;
                return "" + obj[idx - 1];
            } else if ((obj[idx] == '-') || Character.isDigit(obj[idx])) {
                return getNumber();
            } else if (obj[idx] == '"') {
                return getString();
            } else if (Character.isLetter(obj[idx])) {
                return getLiteral();
            } else if (!Character.isWhitespace(obj[idx])) {
                System.err.println("Unexpected input: " + obj[idx]);
                System.exit(1);
            }
        }

        System.err.println("Unexpected end of input");
        System.exit(1);
        return null;
    }

    /* Returns true if there is a next JSON token in the stream.
     * Returns: boolean
     */
    public boolean hasNext() {
        for (; idx < obj.length; idx++) {
            if (!Character.isWhitespace(obj[idx])) {
                return true;
            }
        }
        return false;
    }

    /* Returns the JSON number in the character stream
     * Returns: String: the JSON number
     */
    private String getNumber() {
        String num = "";
        for (; idx < obj.length; idx++) {
            if ((obj[idx] == '-') || (obj[idx] == '+') || (obj[idx] == 'e') ||
                    (obj[idx] == 'E') || (obj[idx] == '.') ||
                    Character.isDigit(obj[idx])) {
                num = num + obj[idx];
            } else {
                return num;
            }
        }
        return num;
    }

    /* Returns the JSON string in the character stream, the method exits
     * if the string is not properly quoted.
     * Returns: String: the JSON string
     */
    private String getString() {
        String str = "\"";
        idx++;

        for (; idx < obj.length; idx++) {
            str += obj[idx];

            if (obj[idx] == '\\') {
                idx++;
                str += obj[idx];
            } else if (obj[idx] == '"') {
                idx++;
                return str;
            }
        }
        System.err.println("Unexpected end of input");
        System.exit(1);
        return null;
    }

    /* Returns the JSON literal in (null, true, false)
     * Returns: String: the JSON literal
     */
    private String getLiteral() {
        String str = "";
        while (Character.isLetter(obj[idx])) {
            str += obj[idx];
            idx++;
        }
        switch (str) {
            case "null":
            case "true":
            case "false":
                break;
            default:
                System.err.println("Unexpected input: " + str);
                System.exit(1);
        }
        return str;
    }


}

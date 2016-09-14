package HTTPServer.Parsers;

import java.util.ArrayList;

public class ParserHelper {
    public static String parseQuery(byte[] rawData, String searchQuery) {
        String result = "";
        String finalResult = "";
        ArrayList temp = new ArrayList(searchQuery.length());
        for(int character : rawData) {
            String c = "" + (char) character;
            if (temp.size() > (searchQuery.length() - 1)) {
                String test = String.join("", temp);
                if (test.contains(searchQuery)) {
                    int withoutQuery = result.length() - searchQuery.length();
                    finalResult = result.substring(0, withoutQuery);
                    break;
                }
                temp.remove(0);
            }
            temp.add(c);
            result += new String(String.valueOf(c));
        }
        return (!finalResult.isEmpty()) ? finalResult.trim() : result.trim();
    }

    public static String[] splitOnParameter(String input, String splitQuery) {
        return (input != null && splitQuery != null && input.contains(splitQuery)) ? input.split(splitQuery) : new String[0];
    }

    public static String splitOnParameter(String input, String splitQuery, int indexToTry) {
        if (input.contains(splitQuery)) {
            if (input.split(splitQuery).length > indexToTry) {
                return input.split(splitQuery)[indexToTry];
            }
        }
        return input;
    }
}

package parsers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class ValuesParser {
    private static final String TAG_VALUES = "values";
    private static final String TAG_ID = "id";
    private static final String TAG_VALUE = "value";
    private final Map<Long, String> values = new HashMap<>();

    public void parse(String jsonString) {
        try {
            JSONObject root = (JSONObject) new JSONParser().parse(jsonString);

            JSONArray array = (JSONArray) root.get(TAG_VALUES);
            for (Object o : array) {
                JSONObject value = (JSONObject) o;
                values.put((Long) value.get(TAG_ID), (String) value.get(TAG_VALUE));
            }

        } catch (ParseException e) {
            throw new RuntimeException("Ошибка с данными!");
        }
    }

    public Map<Long, String> getValues() {
        return values;
    }
}

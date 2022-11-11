package parsers;

import dto.Test;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestsParser {
    private static final String TAG_TESTS = "tests";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_VALUE = "value";
    private static final String TAG_VALUES = "values";

    private final List<Test> tests = new ArrayList<>();

    public void parse(String jsonString) {
        try {
            JSONObject root = (JSONObject) new JSONParser().parse(jsonString);

            JSONArray jsonArray = (JSONArray) root.get(TAG_TESTS);
            if (jsonArray.isEmpty())
                throw new RuntimeException("Файл с тестами пуст");

            parseValues(jsonArray, null);

        } catch (ParseException e) {
            throw new RuntimeException("Ошибка в формате файла со значениями");
        }
    }

    private void parseValues(JSONArray jsonArray, Test mainTest) {
        for (Object o : jsonArray) {
            JSONObject jsonObjectTest = (JSONObject) o;
            Test test = new Test((Long) jsonObjectTest.get(TAG_ID), (String) jsonObjectTest.get(TAG_TITLE));

            Object objectValues = jsonObjectTest.get(TAG_VALUES);
            if (objectValues != null)
                parseValues((JSONArray) objectValues, test);

            if (mainTest != null)
                mainTest.addTest(test);
            else
                tests.add(test);
        }
    }

    public void setValue(List<Test> tests, Map<Long, String> values) {
        if (tests == null)
            tests = this.tests;
        for (Test test : tests) {
            test.setValue(values.put(test.getId(), test.getValue()));
            if (test.getTests() != null)
                setValue(test.getTests(), values);
        }
    }

    public JSONArray createReportJson(List<Test> tests) {
        JSONArray jsonArrayTests = new JSONArray();
        if (tests == null)
            tests = this.tests;
        for (Test test : tests) {

            JSONObject jsonObjectTest = new JSONObject();
            if (test.getTests() != null)
                jsonObjectTest.put(TAG_VALUES, createReportJson(test.getTests()));

            jsonObjectTest.put(TAG_ID, test.getId());
            jsonObjectTest.put(TAG_TITLE, test.getTitle());
            jsonObjectTest.put(TAG_VALUE, test.getValue());
            jsonArrayTests.add(jsonObjectTest);
        }
        return jsonArrayTests;
    }
}



import parsers.TestsParser;
import parsers.ValuesParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Task3 {

    public static void main(String[] args) {

        if (args.length != 2) {
            throw new RuntimeException("���������� ������ ���� 2!");
        }
        String testsFileName = args[0];
        String valuesFileName = args[1];

        Path testsFile = Paths.get(testsFileName);
        Path valuesFile = Paths.get(valuesFileName);
        if (!Files.exists(testsFile) || !Files.exists(valuesFile))
            throw new RuntimeException("���� �� ������!");


        try {
            ValuesParser valuesJsonParser = new ValuesParser();
            valuesJsonParser.parse(new String(Files.readAllBytes(valuesFile)));

            TestsParser testsJsonParser = new TestsParser();
            testsJsonParser.parse(new String(Files.readAllBytes(testsFile)));

            testsJsonParser.setValue(null, valuesJsonParser.getValues());
            JSONArray jsonArrayTests = testsJsonParser.createReportJson(null);
            JSONObject root = new JSONObject();
            root.put("tests", jsonArrayTests);

            try (FileWriter fileWriter = new FileWriter(testsFile.toFile().getParent() + "/report.json");) {
                fileWriter.write(root.toJSONString());
                fileWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException("������ ������ � ����!");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("���� �� ������!");
        } catch (IOException e) {
            throw new RuntimeException("������ ������ �����!");
        }
    }
}


package dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Test {
    private long id;
    private String title;
    private String value;
    private List<Test> tests;

    public Test(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public void addTest(Test test) {
        if (tests == null)
            tests = new ArrayList<>();
        tests.add(test);
    }


}

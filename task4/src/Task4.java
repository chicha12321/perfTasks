import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task4 {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new RuntimeException("Аргумент должен быть один!");
        }
        String fileName = args[0];

        Path file = Paths.get(fileName);
        if (!Files.exists(file))
            throw new RuntimeException(String.format("Файл %s не найден !", fileName) );


        BufferedReader bufferedReader = new BufferedReader(new FileReader(file.toFile()));

        List<Integer> nums = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            nums.add(Integer.parseInt(line));
        }

        Collections.sort(nums);
        int median = nums.get(nums.size() / 2);

        int count = 0;
        for (Integer num : nums) {
            count += Math.abs(num - median);
        }
        System.out.println(count);

    }
}

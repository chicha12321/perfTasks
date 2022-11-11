public class Task1 {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new RuntimeException("Аргументов должно быть два!");
        }

        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        if (n <= 0 || m <= 0) {
            throw new RuntimeException("Неправильные входные данные!");
        }

        String result = "";
        int i = 1;
        while (true) {
            result += i;
            i = 1 + (i + m - 2) % n;
            if (i == 1) {
                break;
            }
        }
        System.out.println(result);
    }
}

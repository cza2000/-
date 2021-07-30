package check程序及说明.check程序及说明;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class check_answer {

    public static List<String> load_file(String file_path) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            String line = br.readLine();
            while (line != null) {
                list.add(line.strip());
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static void compare(String result_path, String compare_path) {
        List<String> result_lines = load_file(result_path);
        List<String> compare_lines = load_file(compare_path);
        int len1 = result_lines.size(), len2 = compare_lines.size();
        if (len1 != len2) {
            System.out.println("check failed with unequal lines");
            System.out.println("origin lines: " + len1);
            System.out.println("compare lines: " + len2);
            return ;
        }
        for (int i = 0; i < len1; i++) {
            if (!result_lines.get(i).equals(compare_lines.get(i))) {
                System.out.println("check failed...");
                System.out.println("origin output is: " + result_lines.get(i));
                System.out.println("compare output is: " + compare_lines.get(i));
                return ;
            }
        }
        System.out.println("check passed");
    }

    public static void main(String[] args) {
        String result_path = "./exp3_out.txt";
        String compare_path = "./exp3_out1.txt";
        compare(result_path, compare_path);
    }
}

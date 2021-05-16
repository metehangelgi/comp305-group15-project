package algorithm3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Algorithm3 {

    static Date date1;
    static Date date2;

    public static void main(String[] args) {
        Tree tree = new Tree();

        try {
            File file = new File("inputs/hallelujah_4.txt");
            Scanner scanner = new Scanner(file);

            String info = scanner.nextLine();

            StringTokenizer st = new StringTokenizer(info);
            int dataSize = Integer.parseInt(st.nextToken());
            int testSize = Integer.parseInt(st.nextToken());

            for (int i = 1; i <= dataSize; i++) {
                st = new StringTokenizer(scanner.nextLine());
                String key = st.nextToken();
                int parentIndex = Integer.parseInt(st.nextToken());

                tree.AddNode(i, key, parentIndex);
            }

            tree.Create();

            date1 = new Date();
            for (int i = 0; i < testSize; i++) {
                String[] query = scanner.nextLine().split("");
                System.out.println(tree.FindQuery(query));
            }

            date2 = new Date();
            long dif = date2.getTime() - date1.getTime();
            System.out.println("time needed in miliseconds: " + dif);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

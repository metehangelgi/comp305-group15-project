package algorithm3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Algorithm3_v1 {

    static Date date1;
    static Date date2;
    static Date date3;

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
            Integer[] output=new Integer[testSize];
            for (int i = 0; i < testSize; i++) {
                String[] query = scanner.nextLine().split("");
                output[i]=tree.FindQuery(query);
            }
            date2 = new Date();
            long dif = date2.getTime() - date1.getTime();
            for(int i=0;i<output.length;i++){
                System.out.println(output[i]);
            }
            date3=new Date();
            long dif2=date3.getTime()-date2.getTime();
            System.out.println("\ntime needed in miliseconds: "+dif);
            System.out.println("time needed in miliseconds for print: "+dif2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

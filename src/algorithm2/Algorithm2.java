package algorithm2;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class Algorithm2 {

    private static int numOfPeople;
    private static int numOfQuery;
    private static ArrayList<String>[][] people;
    private static ArrayList<String> query;
    static Date date1;
    static Date date2;
    static Date date3;
    static StringBuilder str;

    public static void main(String[] args) {

        File file = new File("inputs/hallelujah_4.txt");

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            st = br.readLine();
            String[] line = st.split(" ");
            numOfPeople = parseInt(line[0]);
            numOfQuery = parseInt(line[1]);
            query = new ArrayList<>();
            people = new ArrayList[numOfPeople+1][numOfPeople+1];
            people[0]=null;
            for (int i = 1; i <=numOfPeople; i++) {
                st = br.readLine();
                line = st.split(" ");
                people[i][0]=new ArrayList<>();
                people[i][1]=new ArrayList<>();

                people[i][0].add(line[0]);
                if(parseInt(line[1])!=0){
                    people[parseInt(line[1])][1].add(line[0]+" "+i);
                }

            }
            for (int i = 0; i < numOfQuery; i++) {
                st = br.readLine();
                query.add(st);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        date1=new Date();
        EachQuery(numOfQuery);
        date2=new Date();
        long dif=date2.getTime()-date1.getTime();
        System.out.println(str.toString());
        date3=new Date();
        long dif2=date3.getTime()-date2.getTime();
        System.out.println("\ntime needed in miliseconds: "+dif);
        System.out.println("time needed in miliseconds for print: "+dif2);


    }

    private static void EachQuery(int numOfQuery) {
        str = new StringBuilder();
        for (int i=0;i<numOfQuery;i++){
            String TempQuery=query.get(i);
            int result=findQuery(TempQuery);
            str.append(result).append("\n");
        }
    }

    private static int findQuery(String query) {
        int numFound = 0;
        for (int i=1;i<numOfPeople+1;i++){
            String tempQuery=query;
            if(people[i][0].get(0).equals(Character.toString(tempQuery.charAt(tempQuery.length()-1)))){
                numFound=numFound+checkPerson(people[i][1],tempQuery.substring(0,tempQuery.length()-1));
            }
        }

        return numFound;
    }

    private static int checkPerson(ArrayList<String> children, String tempQuery) {
        if(tempQuery.isEmpty()){
            return 1;
        } else if(children.isEmpty()){
            return 0;
        }

        String[] childData;
        for (String child:children) {
            childData = child.split(" ");
            if(childData[0].equals(Character.toString(tempQuery.charAt(tempQuery.length()-1)))){
                return checkPerson(people[parseInt(childData[1])][1],tempQuery.substring(0,tempQuery.length()-1));
            }
        }
        return 0;
    }
}
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
        int[] output=EachQuery(numOfQuery);
        for(int i=0;i<output.length;i++){
            System.out.println(output[i]);
        }
        date2=new Date();
        long dif=date2.getTime()-date1.getTime();
        //long seconds = TimeUnit.MILLISECONDS.toSeconds(dif);
        System.out.println("time needed in miliseconds: "+dif);


    }

    private static int[] EachQuery(int numOfQuery) {

        int[] output = new int[numOfQuery];
        for (int i=0;i<numOfQuery;i++){
            String TempQuery=query.get(i);
            output[i]=findQuery(TempQuery);
        }
        return output;
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
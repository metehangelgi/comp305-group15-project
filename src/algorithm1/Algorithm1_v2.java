package algorithm1;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class Algorithm1_v2 {

    private static int numOfPeople;
    private static int numOfQuery;
    private static String[][] people;
    private static ArrayList<String> query;
    static Date date1;
    static Date date2;
    static Date date3;
    static StringBuilder str;

    public static void main(String[] args){

        File file = new File("inputs/hallelujah_3.txt");

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            st = br.readLine();
            String[] line = st.split(" ");
            numOfPeople=parseInt(line[0]);
            numOfQuery=parseInt(line[1]);
            people= new String[numOfPeople+1][2];
            query = new ArrayList<>();
            people[0]=null;
            for (int i=1;i<numOfPeople+1;i++){
                st = br.readLine();
                line = st.split(" ");
                people[i][0]=line[0];
                people[i][1]=line[1];
            }
            for (int i=0;i<numOfQuery;i++){
                st = br.readLine();
                query.add(i,st);
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
        str=new StringBuilder();
        ArrayList<String> calculated_name = new ArrayList<>();
        ArrayList<Integer> calculated_result = new ArrayList<>();
        for (int i=0;i<numOfQuery;i++){
            String TempQuery=query.get(i);
            int result=0;
            int previous =0;
            for(int k = 0; k < calculated_name.size(); k++){
                if(calculated_name.get(k).equals(TempQuery)){
                    result=calculated_result.get(k);
                    previous=1;
                    break;
                }
            }
            if(previous==1){
                str.append(result).append("\n");
            }else{
                result=findQuery(TempQuery);
                str.append(result).append("\n");
                calculated_name.add(TempQuery);
                calculated_result.add(result);
            }
        }
    }

    private static int findQuery(String query) {

        int numFound = 0;
        for (int i=1;i<numOfPeople+1;i++){
            String TempQuery=query;
            numFound=numFound+checkPerson(people[i],TempQuery);
        }

        return numFound;
    }

    private static int checkPerson(String[] person, String query) {

        if(query.isEmpty()){
            return 1;
        } else if(person==null){
            return 0;
        } else if(person[0].equals(Character.toString(query.charAt(0)))){
            return checkPerson(people[parseInt(person[1])],query.substring(1));
        }
        return 0;
    }
}

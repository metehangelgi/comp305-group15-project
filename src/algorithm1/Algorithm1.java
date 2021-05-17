package algorithm1;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class Algorithm1 {

    private static int numOfPeople;
    private static int numOfQuery;
    private static String[][] people;
    private static ArrayList<String> query;
    static Date date1;
    static Date date2;

    public static void main(String[] args){

        File file = new File("inputs/hallelujah_2.txt");

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
        ArrayList<String> calculated_name = new ArrayList<String>();
        ArrayList<int> calculated_result = new ArrayList<int>();
        for (int i=0;i<numOfQuery;i++){
            String TempQuery=query.get(i);
            int result=0;
            int previous =0;
            for(int i = 0; i < calculated_name.size(); i++){
                if(calculated_name.get(i).equals(TempQuery)==0){
                    result=calculated_result.get(i);
                    previous=1;
                }
            }
            if(previous==1){
                output[i]=result;
            }else{
                output[i]=findQuery(TempQuery);
                calculated_name.add(TempQuery);
                calculated_result.add(output[i]);
            }
        }
        return output;
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

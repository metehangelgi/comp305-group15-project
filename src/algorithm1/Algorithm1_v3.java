package algorithm1;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class Algorithm1_v3 {

    private static int numOfPeople;
    private static int numOfQuery;
    private static String[][] people;
    private static ArrayList<String> query;
    private static Integer[] reference;

    private static ArrayList<String> calculated_name = new ArrayList<>();
    private static ArrayList<Integer> calculated_result = new ArrayList<>();
    private static ArrayList<ArrayList<Integer> > calculated_position = new ArrayList<>();

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
        // to short our query 
        Integer[] reference = query.toArray(new Integer[0]);
        bubblesrt(query);

        date1=new Date();
        EachQuery(numOfQuery);
        int[] output=rotate(numOfQuery);

        for(int i=0;i<output.length;i++){
            System.out.println(output[i]);
        }
        date2=new Date();
        long dif=date2.getTime()-date1.getTime();
        //long seconds = TimeUnit.MILLISECONDS.toSeconds(dif);
        System.out.println("time needed in miliseconds: "+dif);


    }
    // return order
    public static int[] rotate(int numOfQuery)
    {
        int[] output = new int[numOfQuery];
            for (int x=0; x < numOfQuery; x++) {
                String TempQuery=query.get(x);
                for (int i=0; i < calculated_name.size(); i++) {
                    if (calculated_name.get(i).equals(TempQuery)){
                        output[x]=calculated_result.get(i);
                    }
                }
            }
                return output;
    }
// buble short
    public static void bubblesrt(ArrayList<String> list)
    {
        String temp;
        int changed=0;
        if (list.size()>1) // check if the number of orders is larger than 1
        {
            for (int x=0; x<list.size(); x++) // bubble sort outer loop
            {
                for (int i=0; i < list.size()-i; i++) {
                    if (list.get(i).length()>list.get(i+1).length())
                    {
                        temp = list.get(i);
                        list.set(i,list.get(i+1) );
                        list.set(i+1, temp);
                        changed=1;
                    }
                }
                if(changed==0) break;
            }
        }

    }

    private static int[] EachQuery(int numOfQuery) {
        int[] output = new int[numOfQuery];


        for (int i=0;i<numOfQuery;i++){
            String TempQuery=query.get(i);
            int result=0;
            int previous =0;
            int similar=-1;
            for(int k = 0; k < calculated_name.size(); k++){
                if(calculated_name.get(k).equals(TempQuery)){
                    result=calculated_result.get(k);
                    previous=1;
                }else if((calculated_name.get(k).substring(TempQuery.length()+1)).equals(TempQuery)){
                    // take the longest similar 
                    if(calculated_name.get(similar).length()<calculated_name.get(k).length()){
                        similar =k;
                    }
                }
            }
            if(previous==1){
                output[i]=result;
            }else if(similar!=-1){
                ArrayList<Integer> a2 = new ArrayList<Integer>();
                a2=findQuerySome(TempQuery,similar);
                output[i]=a2.size();
                calculated_name.add(TempQuery);
                calculated_result.add(output[i]);
                calculated_position.add(a2);
            }else{
                ArrayList<Integer> a1 = new ArrayList<Integer>();
                a1=findQuery(TempQuery);
                output[i]=a1.size();
                calculated_name.add(TempQuery);
                calculated_result.add(output[i]);
                calculated_position.add(a1);
            }
        }
        return output;
    }

    private static ArrayList<Integer>  findQuerySome(String query,int sim) {
        ArrayList<Integer> position = new ArrayList<Integer>();
        ArrayList<Integer> position_check =calculated_position.get(sim);
        for (Integer i :position_check ){
            String TempQuery=query;
            if(checkPerson(people[i],TempQuery)==1){
                position.add(i);
            }
        }
        return position;
    }

    private static ArrayList<Integer>  findQuery(String query) {
        ArrayList<Integer> position = new ArrayList<Integer>();
        for (int i=1;i<numOfPeople+1;i++){
            String TempQuery=query;
            if(checkPerson(people[i],TempQuery)==1){
                position.add(i);
            }
        }
        return position;
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

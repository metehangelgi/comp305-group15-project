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
    private static String[] reference;

    private static ArrayList<String> calculated_name = new ArrayList<>();
    private static ArrayList<Integer> calculated_result = new ArrayList<>();
    private static ArrayList<ArrayList<Integer> > calculated_position = new ArrayList<>();

    static Date date1;
    static Date date2;
    static Date date3;

    static StringBuilder str;

    public static void main(String[] args){

        File file = new File("inputs/hallelujah_4.txt");

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
            reference=new String[numOfQuery];
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
                reference[i]=st;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        // to sort our query
        bubblesrt(query);

        date1=new Date();
        
        EachQuery(numOfQuery);
        rotate(numOfQuery);

        date2=new Date();
        long dif=date2.getTime()-date1.getTime();
        System.out.println(str.toString());
        date3=new Date();
        long dif2=date3.getTime()-date2.getTime();
        System.out.println("\ntime needed in miliseconds: "+dif);
        System.out.println("time needed in miliseconds for print: "+dif2);


    }
    // return order
    public static int[] rotate(int numOfQuery) {
        str = new StringBuilder();
        int[] output = new int[numOfQuery];
        for (int x=0; x < numOfQuery; x++) {
            String TempQuery=reference[x];
            for (int i=0; i < calculated_name.size(); i++) {
                if (calculated_name.get(i).equals(TempQuery)){
                    output[x]=calculated_result.get(i);
                    str.append(calculated_result.get(i)).append("\n");
                }
            }
        }
        return output;
    }
    // bubble sort
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
            int differen;
            for(int k = 0; k < calculated_name.size(); k++){
                differen=calculated_name.get(k).length()-TempQuery.length();
                if(calculated_name.get(k).equals(TempQuery)){
                    result=calculated_result.get(k);
                    previous=1;
                }else if(differen>0){
                    if(calculated_name.get(k).substring(differen).equals(TempQuery)){
                         // take the longest similar 
                        if(similar == -1){
                            similar = k;
                        }else if(calculated_name.get(similar).length()<calculated_name.get(k).length()){
                            similar =k;
                         }
                    }
                }
            }
            if(previous==1){
                output[i]=result;
            }else if(similar!=-1){
                ArrayList<Integer> a2;
                a2=findQuerySome(TempQuery,similar);
                output[i]=a2.size();
                calculated_name.add(TempQuery);
                calculated_result.add(output[i]);
                calculated_position.add(a2);
            }else{
                ArrayList<Integer> a1;
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
        ArrayList<Integer> position = new ArrayList<>();
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
        ArrayList<Integer> position = new ArrayList<>();
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

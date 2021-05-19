package algorithm4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Algorithm4_v1 {
    public static void main(String[] args) {
        DataStructure dataStructure=new DataStructure();
        GenericTree tree = new GenericTree(dataStructure);
        ArrayList<String> queries=new ArrayList<>();
        HashMap<String,Integer> queryMap=new HashMap<>();
        HashMap<String,AugmentedArrayList> queryMap2=new HashMap<>();
        StringBuilder str;
        try {
            File file = new File("inputs/hallelujah_3.txt");
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
            for (int i = dataSize; i < dataSize + testSize; i++) {
                st = new StringTokenizer(scanner.nextLine());
                String key = st.nextToken();
                queryMap.putIfAbsent(key,-1);
                if(!queryMap2.containsKey(key)){
                    AugmentedArrayList letterArrayList=new AugmentedArrayList();
                    for (int j = key.length()-1; j >= 0; j--) {
                        Letter letter=new Letter(key.charAt(j));
                        letterArrayList.add(letter);
                        if(j==key.length()-1){
                            letter.setNextChecksum(0);
                        }else {
                            letter.setNextChecksum(letterArrayList.get(letterArrayList.size()-2).getChecksum());
                        }
                    }
                    queryMap2.put(key,letterArrayList);
                }
                queries.add(key);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        str=new StringBuilder();
        Date date1=new Date();
        for (int i = 0; i < queries.size(); i++) {
            String key=queries.get(i);
            int result;
            int queryMemo=queryMap.get(key);
            if(queryMemo!=-1){
                result=queryMemo;
            }else {
                result=dataStructure.search(queryMap2.get(key));
                queryMap.put(key,result);
            }
            str.append(result).append("\n");
        }
        Date date2=new Date();
        System.out.println(str);
        Date date3=new Date();


        long dif=date2.getTime()-date1.getTime();
        long dif2=date3.getTime()-date2.getTime();
        System.out.println("Time Diff: "+dif);
        System.out.println("Time Diff for print: "+dif2);
    }
}

class DataStructure{
    private final ArrayList<AugmentedArrayList> lists;
    private final HashMap<Character, LinkedList<Information>> map;

    public DataStructure(){
        lists=new ArrayList<>();
        map=new HashMap<>();
    }

    public void addElement(Letter letter, Letter parentLetter){
        if(parentLetter == null){
            AugmentedArrayList augmentedArrayList=new AugmentedArrayList();
            augmentedArrayList.add(letter);
            letter.setNextChecksum(0);
            lists.add(augmentedArrayList);
        }else {
            int parentGPos = parentLetter.getGPos();
            int parentLPos = parentLetter.getLPos();
            AugmentedArrayList augmentedArrayList=lists.get(parentGPos);
            if(augmentedArrayList.size()==parentLPos+1){
                augmentedArrayList.add(letter);
                letter.setLPos(parentLPos+1);
                letter.setGPos(parentGPos);
            }else {
                AugmentedArrayList augmentedArrayList2=new AugmentedArrayList();
                augmentedArrayList2.setJumping(parentGPos);
                augmentedArrayList2.setShifting(parentLPos+1);
                augmentedArrayList2.add(letter);
                lists.add(augmentedArrayList2);
                letter.setLPos(0);
                letter.setGPos(lists.size()-1);
            }
            letter.setNextChecksum(parentLetter.getChecksum());
            letter.setLength(parentLetter.getLength()+1);
        }
        map.putIfAbsent(letter.getValue(),new LinkedList<>());
        map.get(letter.getValue()).add(letter.getInformation());
    }

    public void print(){
        for (Character c:map.keySet()) {
            map.get(c).forEach(s-> System.out.print(s+" "));
            System.out.println();
        }
    }

    public void printList(){
        for (AugmentedArrayList l:lists) {
            l.forEach(s-> System.out.print(s.getValue()+""+s.getLength()+" "));
            System.out.print(l.getJumping()+" "+l.getShifting());
            System.out.println();
        }
    }

    public int search(ArrayList<Letter> query){
        Letter firstElementInQuery=query.get(query.size()-1);
        LinkedList<Information> informationLinkedList=map.get(firstElementInQuery.getValue());
        int counter=0;
        if (informationLinkedList == null) {
            return counter;
        }
        if(query.size()==1){
            return informationLinkedList.size();
        }
        for (Information info : informationLinkedList) {
            //System.out.println(info);
            if(info.getLength()<query.size()||firstElementInQuery.getChecksum()> info.getTotalChecksum()
                    ||(info.getLength()==query.size() && info.getTotalChecksum()!=firstElementInQuery.getChecksum())) {
                continue;
            }
            AugmentedArrayList currentAugmentedList=lists.get(info.getGPos());
            int currentQueryPos= query.size()-1;
            int LPos=info.getLPos();
            Letter currentLetter = null;
            Letter currentQueryElement;
            boolean isAppropriate=true;
            while (currentQueryPos>=0){
                int lowerBound=Math.max(0,LPos-(currentQueryPos+1));
                int lowerQuery=currentQueryPos-(LPos-lowerBound);
                //System.out.println(info+" "+LPos+" "+lowerBound+" "+lowerQuery);
                if(!checksumControl(currentAugmentedList,query,LPos,lowerBound,currentQueryPos,lowerQuery, currentAugmentedList.getJumping())){
                    isAppropriate=false;
                    //System.out.println("Here");
                    break;
                }
                //System.out.println("here");
                int endPos=lowerBound==0?-1:lowerBound;
                //System.out.println("Lower Bound: "+lowerBound);
                for (int i = LPos, j=currentQueryPos; i >endPos&&currentQueryPos>=0 ; i--,j--) {
                    currentLetter=currentAugmentedList.get(i);
                    currentQueryElement=query.get(j);
                    //System.out.println("EndPos: "+endPos);
                    //System.out.println(currentLetter.getValue()+" "+currentQueryElement.getValue());
                    if(currentLetter.getValue()!=currentQueryElement.getValue()){
                        isAppropriate=false;
                        break;
                    }
                    currentQueryPos--;
                    //System.out.println("CurrentQueryPos2: "+currentQueryPos);
                }
                if(!isAppropriate){
                    break;
                }
                //System.out.println(currentAugmentedList.getJumping());
                if(lowerQuery<0||currentAugmentedList.getJumping()==-1){
                    //System.out.println("Here");
                    break;
                }
                //System.out.println(lowerQuery+" CurrentQueryPos:"+currentQueryPos);
                //currentQueryPos-=lowerQuery;

                LPos=currentAugmentedList.getShifting()-1;
                currentAugmentedList=lists.get(currentAugmentedList.getJumping());
                //System.out.println(currentQueryPos);
            }
            //System.out.println(currentLetter.getValue());
            if(isAppropriate){
                //System.out.println(info+" Here");
                counter++;
            }

        }
        return counter;
    }
    public boolean checksumControl(AugmentedArrayList current,ArrayList<Letter> query,int LPos,int lowerBound,int currentQueryPos,int lowerQuery,int jumping){
        int checksumOfQuery;
        int checksumOfFamily;
        //System.out.println(LPos+" "+lowerBound+" "+currentQueryPos+" "+lowerQuery+" ");

        if(LPos>currentQueryPos){
            //System.out.println(lowerQuery+" Lower "+currentQueryPos);

            checksumOfQuery=query.get(currentQueryPos).getChecksum();
            checksumOfFamily=current.get(LPos).getChecksum()-current.get(lowerBound).getChecksum();
        }
        else if(LPos==currentQueryPos&&jumping==-1){
            checksumOfQuery=query.get(currentQueryPos).getChecksum();
            checksumOfFamily=current.get(LPos).getChecksum();
            //System.out.println("Equal");
        } else {
            checksumOfQuery=
                    query.get(currentQueryPos).getChecksum()-
                            query.get(lowerQuery).getChecksum();
            checksumOfFamily=current.get(LPos).getChecksum()-current.get(lowerBound).getChecksum();
        }

        //System.out.println(checksumOfQuery+" "+checksumOfFamily);
        return checksumOfQuery==checksumOfFamily;
    }
}


class AugmentedArrayList extends ArrayList<Letter>{
    private int jumping;
    private int shifting;
    private String query;

    public AugmentedArrayList(){
        jumping=-1;
        shifting=0;
    }

    public int getJumping() {
        return jumping;
    }

    public void setJumping(int jumping) {
        this.jumping = jumping;
    }

    public int getShifting() {
        return shifting;
    }

    public void setShifting(int shifting) {
        this.shifting = shifting;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

class Letter {
    private final char value;
    private final Information information;

    public Letter(char value) {
        this.value = value;
        this.information =new Information(0,0);
    }

    public void setNextChecksum(int previousChecksum) {
        information.setTotalChecksum(previousChecksum+(int)value);
    }

    public void setLength(int length){
        information.setLength(length);
    }

    public void setGPos(int GPos) {
        this.information.setGPos(GPos);
    }

    public char getValue() {
        return value;
    }

    public int getLength() {
        return information.getLength();
    }

    public int getChecksum() {
        return information.getTotalChecksum();
    }

    public int getGPos() {
        return information.getGPos();
    }

    public int getLPos() {
        return information.getLPos();
    }

    public void setLPos(int LPos) {
        this.information.setLPos(LPos);
    }

    public Information getInformation() {
        return information;
    }
}

class Information {
    private int GPos;
    private int LPos;
    private int length=1;
    private int totalChecksum=0;

    public Information(int GPos, int LPos) {
        this.GPos = GPos;
        this.LPos = LPos;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTotalChecksum() {
        return totalChecksum;
    }

    public void setTotalChecksum(int totalChecksum) {
        this.totalChecksum = totalChecksum;
    }

    public int getGPos() {
        return GPos;
    }

    public int getLPos() {
        return LPos;
    }

    public void setGPos(int GPos) {
        this.GPos = GPos;
    }

    public void setLPos(int LPos) {
        this.LPos = LPos;
    }

    @Override
    public String toString() {
        return "Information{" +
                "GPos=" + GPos +
                ", LPos=" + LPos +
                ", length=" + length +
                ", totalChecksum=" + totalChecksum +
                '}';
    }
}

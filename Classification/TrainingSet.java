
/*
Author: ALLAN EMUSUGUT BARUA
P15/1719/2016
Java implementation of KNN algorithm
*/

package Classification;
import java.util.Arrays;

public class TrainingSet {
    
    private final int acidDurability;
    private final int strength;
    private String ObjectClass;
    private static int[] distance = new int[4];
    private int dist;
    
    
    public TrainingSet(int acidDurability,int strength, String ObjectClass){
        this.acidDurability = acidDurability;
        this.strength = strength;
        this.ObjectClass = ObjectClass;
    }
    
    public void calculateDistance(TrainingSet[] data){
        for(int i=0;i<data.length;i++){
            data[i].dist=Math.abs(acidDurability - data[i].acidDurability)*Math.abs(acidDurability - data[i].acidDurability) + Math.abs(strength - data[i].strength)* Math.abs(strength - data[i].strength);
            distance[i] = data[i].dist;
        }
        
    }
    
    public static void sortDistances(int neighbors){
        Arrays.parallelSort(distance);
        
    }
    
    public void vote(TrainingSet[] data,int neighbors){
        int bad = 0;
        int good = 0;
        for(int i=0;i<data.length;i++){
            if(data[i].dist <= distance[neighbors-1]){
                if(data[i].ObjectClass.equalsIgnoreCase("good"))
                    good++;
                else if(data[i].ObjectClass.equalsIgnoreCase("bad"))
                    bad++;
            }
        }
        if(good>=bad)
            this.ObjectClass = "good";
        else
            this.ObjectClass = "bad";
    }
    
    public void printResult(TrainingSet[] data){
        System.out.println("\n_____________________________________________________________________");
        System.out.println("DataObject\tAcid durability\t    Strength\tDistance\tClass");
        System.out.println("______________________________________________________________________");
        for(int i=0;i<data.length;i++){
            System.out.printf("  %d\t\t\t%d\t\t%d\t   %d\t\t%s\n",i+1,data[i].acidDurability,data[i].strength,data[i].dist,data[i].ObjectClass);
        }
        System.out.printf("\nAnswer\nacidDurability >>> %d\nstrength >>> %d\nClass >> %s\n",acidDurability,strength,ObjectClass);
    }
}

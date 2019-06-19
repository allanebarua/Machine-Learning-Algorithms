/*
Author: ALLAN EMUSUGUT BARUA
P15/1719/2016
Java implementation of KNN algorithm
*/

package Classification;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args){
        
        TrainingSet[] data = addData();
        Scanner input = new Scanner(System.in);
        
        System.out.println("Please enter the toilet paper's attributes as directed below");
        System.out.print("Acid Durability(seconds)   >>>  ");
        int durability = input.nextInt();
        System.out.print("Strength(Kgs/square meter) >>>  ");
        int strength = input.nextInt();
        
        System.out.print("\nPlease enter your prefered number of nearest neighbors\nchoice >>>  ");
        int choice = input.nextInt();
        if(choice!=2 && choice!=3){
            System.err.println("Invalid input");
            System.exit(0);
        }
        
        TrainingSet paper = new TrainingSet(durability,strength,"");
        paper.calculateDistance(data);
        TrainingSet.sortDistances(choice);
        paper.vote(data, choice);
        paper.printResult(data);
        
        
    }
    
    public static TrainingSet[] addData(){
        
        TrainingSet[] data = new TrainingSet[4];
        data[0] = new TrainingSet(7,7,"bad");
        data[1] = new TrainingSet(7,4,"bad");
        data[2] = new TrainingSet(3,4,"good");
        data[3] = new TrainingSet(1,4,"good");
        
        return data;
    }
    
}

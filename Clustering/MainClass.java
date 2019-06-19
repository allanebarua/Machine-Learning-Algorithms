
/*
Author: ALLAN EMUSUGUT BARUA
P15/1719/2016
Java implementation of K- Means algorithm
*/

package Clustering;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args){
        
        Scanner input = new Scanner(System.in);
        
        DataSet[] dataArray = new DataSet[4];
        dataArray[0] = new DataSet(1,1);
        dataArray[1] = new DataSet(2,1);
        dataArray[2] = new DataSet(4,3);
        dataArray[3] = new DataSet(5,4);
        
        System.out.println("How many clusters do you want to have");
        System.out.println("\t2 Clusters: Enter 2\n\t3 Clusters: Enter 3");
        System.out.print("Choice: ");
        int choice = input.nextInt();
        if(choice<1 || choice>4){
            System.err.println("Invalid input");
            System.exit(0);
        }
        
        do{
            DataSet.chooseCentroids(choice,dataArray);
            DataSet.computeDist(dataArray);
            DataSet.clusterObj(dataArray);
            DataSet.printState(dataArray);
        }
        while(!DataSet.convergenceCheck());               
    }
    
}

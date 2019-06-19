
/*
Author: ALLAN EMUSUGUT BARUA
P15/1719/2016
Java implementation of SOM
*/

package Somm;

public class MainClass {
    public static void main(String[] args){
        
        //training dataset
        int[][] trainingSet = {{1,3,4},
                            {2,4,7},
                            {4,8,9},
                            {4,8,3},
                            {4,8,675},
                            {10,867,9},
                            {0,78,4},
                            {67,9,29},
                            {4,0,359},
                            {412,48,90},
                            {41,568,999}
        };
    
        //size of the lattice
        int latticeSize = 5;
        
        //size of a training example
        int vectorSize = trainingSet[0].length;
        
        //number of iteration
        int iterations = 10;

        //creating a self organizing map object
        Som som1 = new Som(latticeSize, vectorSize, iterations);
        
        //training the som
        som1.train(trainingSet);
        
        
    }
    
    
    
    
    
}

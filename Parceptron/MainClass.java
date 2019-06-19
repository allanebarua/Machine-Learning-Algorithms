
/*
Author: ALLAN EMUSUGUT BARUA
P15/1719/2016
Java implementation of a perceptron
*/

package parcept;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args){
        
        
        System.out.println("\n\tIMPLEMENTATION OF PERCEPTRON WITH ONE HIDDEN LAYER");
        System.out.println("____________________________________________________________________________________________________");
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the following parameters:\nNumber of Nodes in the hidden layer:   ");
        int hiddenN = input.nextInt();
        
        if(hiddenN!=2){
            System.out.println("\n\nPlease enter 2 inorder to get a good table\n\n");
            System.exit(1);
        }
        
        Network net = new Network();
        
        //initialize data set
        int[][] dataSet= {{0,  0,  1},
                            {0,   1,  1},
                            {1,   0,  1},
                            {1,   1,  0}
        };
        
        
        int inputN = dataSet[0].length-1;  //the number of neurones in the input layer is equal to the number of inputs
        int outputN = 1;                   //Number of outputs
        
        
        
        //at weight initialization we should pass the number of nodes at each layer
        net.initializeWeights(inputN, hiddenN, outputN);
        net.train(dataSet);
        
    }
    
}




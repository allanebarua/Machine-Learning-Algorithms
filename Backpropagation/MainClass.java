
/*
Author: ALLAN EMUSUGUT BARUA
P15/1719/2016
Java implementation of backpropagation
*/

package Backpropagation;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args){
        
        
        System.out.println("\nIMPLEMENTATION OF BACKPROPAGATION WITH ONE HIDDEN LAYER");
        System.out.println("____________________________________________________________________________________________________");
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the following parameters:\nNumber of Nodes in the hidden layer:   ");
        int hiddenN = input.nextInt();
        if(hiddenN<2){
                System.exit(1);
        }
        
        Network net = new Network();
        //initialize data set
        double[][] dataSet= {{0.4,  -0.7,  0.1,},
                            {0.3,   -0.5,  0.05,},
                            {0.6,    0.1,  0.3,},
                            {0.2,    0.4,  0.25,},
                            {0.1,   -0.2,  0.12,}
        };
        
        //String path = "C:\\Users\\Allane\\Documents\\NetBeansProjects\\LearnJava\\src\\Backpropagation\\file.csv";
        //double[][] dataSet = CsvReader.data(path);  //reading training dataset
        
        int inputN = dataSet[0].length-1;  //the number of neurones in the input layer is equal to the number of inputs
        int outputN = 1;                   //Number of outputs
        
        
        
        //at weight initialization we should pass the number of nodes at each layer
        net.initializeWeights(inputN, hiddenN, outputN);
        net.train(dataSet);
        
        //PrintTable t = new PrintTable(2, "Allan", "Barua", "Emusugut", "Guy");
    }
    
}



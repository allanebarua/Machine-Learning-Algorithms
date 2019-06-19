
/*
Author: ALLAN EMUSUGUT BARUA
P15/1719/2016
Java implementation of backpropagation
*/

package Backpropagation;

import java.util.Date;
import java.util.Random;

public class Network {
    
    private double[][] H_weights, H_trans, O_weights, O_trans;
    private double[][] Oi,Ih,Oh,Io,Oo;
    private double[][] change_O, change_H;
    private double errSquare;
    private double totalErr =0;
    private double errRate = 1;
    private final static double n =0.32;
    private int iter= 0;
    private int count = 1;
    
    
    
    //function to initialize the weights of the network
    public void initializeWeights(int inputN, int hiddenN, int outputN){
        
        Random obj1 = new Random(new Date().getTime());
        
        //initialize weights for the hidden layer neurones
        H_weights = new double[inputN][hiddenN];
        for(int i=0;i<H_weights.length;i++){
            for(int j=0;j<H_weights[i].length;j++){
                H_weights[i][j] = obj1.nextDouble();
            }
        }
        
        //initialize weights for the output layer neurones
        O_weights = new double[hiddenN][outputN];
        for(int i=0;i<O_weights.length;i++){
            for(int j=0;j<O_weights[i].length;j++){
                O_weights[i][j] = obj1.nextDouble();
            }
        }
        
        System.out.println("\nInitialized weights of the hidden layer");
        vectorPrinter(H_weights);
        System.out.println("\n\nInitialized weights of the output layer");
        vectorPrinter(O_weights);
        
//        H_weights[0][0] =0.1;
//        H_weights[0][1] =0.4;
//        H_weights[1][0] =-0.2;
//        H_weights[1][1] =0.2;
//        
//        O_weights[0][0] =0.2;
//        O_weights[1][0] =-0.5;
    }
    
    
    
    
    
    //train the neurone network using back propagation
    public void train(double[][] data){
        
        do{            
            
            //output of the input layer using a linear function
            Oi = new double[data[iter].length-1][1];

            for(int j=0;j<Oi.length;j++){
                Oi[j][0] = data[iter][j];
            }

            //last value as the output
            double target = data[iter][data[iter].length-1];



            /*--------------------------------------------input of the hidden layer------------------------------------------*/

            //transpose of the matrix        
            H_trans = new double[H_weights[0].length][H_weights.length];
            H_trans=transpose(H_weights);        
            Ih = new double[H_trans.length][Oi[0].length];

            //dot product of the H_weights transpose and the Oi
            double sum;
            for(int i=0;i<H_trans.length;i++){
               sum =0;
               for(int j =0; j<H_trans[i].length;j++){
                   sum += H_trans[i][j] * Oi[j][0] ;
               }
               Ih[i][0] = sum;
            }

            /*--------------------------------------------Output of the hidden layer------------------------------------------*/

            //using the sigmoid activation function
            Oh = new double[Ih.length][Ih[0].length];
            for(int i=0;i<Oh.length;i++){
                Oh[i][0] = 1 / ( 1 + Math.exp((double)-Ih[i][0]));
            }

            /*--------------------------------------------input of the output layer------------------------------------------*/

            //transpose of the matrix
            O_trans = new double[O_weights[0].length][O_weights.length];
            O_trans = transpose(O_weights);
            Io = new double[O_trans.length][Oh[0].length];


            //dot product of the transpose of weights and the Oh
            for(int i=0;i<O_trans.length;i++){
               sum = 0;
               for(int j =0; j<O_trans[i].length;j++){
                   sum += O_trans[i][j] * Oh[j][i] ;
               }
               Io[i][0] = sum;
            }

            /*--------------------------------------------output of the output layer------------------------------------------*/

            //using the sigmoid function
            Oo = new double[Io.length][Io[0].length];
            for(int i=0;i<Oo.length;i++){
                Oo[i][0] = 1 / ( 1 + Math.exp(-Io[i][0]));
            }

            /*--------------------------------------------Error Propagation--------------------------------------------------*/
            
            //squared error of the system
            errSquare = (target - Oo[0][0]) * (target - Oo[0][0]);

            //neX using the delta rule
            // x is the output of the hidden layer
            // e  is d where d= (Yt - Ys)Ys(1-Ys)

            double d = (target - Oo[0][0]) * Oo[0][0] * (1 - Oo[0][0]);
            //System.out.println("Target: "+ target +"  a: "+ Oo[0][0] +" d: "+ d);

            //change in O weights is nex
            change_O = new double[Oh.length][Oh[0].length];
            for(int i=0;i<change_O.length;i++){
                change_O[i][0] = n * d * Oh[i][0];
            }

            //new weights for O_weights
            double [][] ON_weights = O_weights;
            for(int i=0;i<O_weights.length;i++){
                O_weights[i][0] = O_weights[i][0] + change_O[i][0];
            }

            //change in H_weights using nex
            //e2 = O_weights mult d
            
            //System.out.println("e*");

            double[][] e2 = new double[O_weights.length][O_weights[0].length];
            for(int i=0;i<e2.length;i++){
                for(int j=0;j<e2[i].length;j++){
                    e2[i][j] = ON_weights[i][j] * d; 
                }
            }
            //vectorPrinter(e2);

            //System.out.println("d2");
            //d2
            double[][] d2 = new double[e2.length][Oh[0].length];
            for(int i=0;i<d2.length;i++){
                for(int j=0;j<d2[i].length;j++){
                    d2[i][j] = e2[i][j] * Oh[i][j] * (1 - Oh[i][j]);
                }
            }
            //vectorPrinter(d2);

            //x2 = output of the input layer mult d2 transpose
            //transpose of d2
            double[][] d2_trans = new double[d2[0].length][d2.length];
            d2_trans = transpose(d2);
            double[][] x2 = new double[Oi.length][d2_trans[0].length];

            //multiplication
            for(int i=0;i<x2.length;i++){
                for(int j=0; j<x2[i].length;j++){
                    x2[i][j] = Oi[i][0] * d2_trans[0][j];
                }
            }


            //change in H_weights using nex
            change_H = new double[x2.length][x2[0].length];
            for(int i=0;i<change_H.length;i++){
                for(int j=0; j<change_H[i].length;j++){
                    change_H[i][j] = x2[i][j] * n;
                }
            }

            //new weights for H_weights
            for(int i=0;i<change_H.length;i++){
                for(int j=0; j<change_H[i].length;j++){
                    H_weights[i][j] = change_H[i][j] + H_weights[i][j];
                }
            }
            

            //calculate error rate (1/n) mult summation of error squared
            totalErr += errSquare;
                        
            display();
            //System.out.println("d = "+ d);
            
            if(iter ==data.length-1){
                errRate = (1.0/data.length) * totalErr;
                totalErr = 0;
                iter = 0;
                System.out.println("\n\n    EPOCH "+ count +" Error rate                    >>> "+ errRate);
                count++;
            }
                
            else
                iter++;
            
        }
        while(errRate>0.05);
 
    }
    
     
    
    
    
    public double[][] transpose(double[][] weights){
        //create a transpose matrix for the weights
        double[][] H_trans = new double[weights[0].length][weights.length];
        
        for(int i = 0; i < H_trans.length; i++){
      	    for(int j = 0; j < H_trans[0].length; j++)
            {
                H_trans[i][j]=weights[j][i];
            }
        }
        return H_trans; 
    }
    
    
    
    
    
    
    public void display(){
        System.out.println("\n\nEPOCH: "+ count +"                            Iteration "+ (iter + 1));
        System.out.println("______________________________________________________________________");
        
        System.out.println("    Output of the Input Layer");
        vectorPrinter(Oi);
        
        System.out.println("\n\n    Input of the Hidden Layer");
        vectorPrinter(Ih);
        
        System.out.println("\n\n    Output of the Hidden Layer");
        vectorPrinter(Oh);
        
        System.out.println("\n\n    Input of the Output Layer");
        vectorPrinter(Io);
        
        System.out.println("\n\n    Output of the Output Layer");
        vectorPrinter(Oo);
        
        System.out.println("\n\n    Squared Error                  >>> "+ errSquare);
        
        System.out.println("\n\n    Change in Output Layer Weights");
        vectorPrinter(change_O);
        
        System.out.println("\n\n    New Output layer Weights");
        vectorPrinter(O_weights);
        
        System.out.println("\n\n    Change in Hidden Layer Weights");
        vectorPrinter(change_H);
        
        System.out.println("\n\n    New Hidden layer Weights");
        vectorPrinter(H_weights);
        
        
    }
    
    
    
    
    
    
    public void vectorPrinter(double[][] vector){
        
        for(int i=0;i<vector.length;i++){
            for(int j=0;j<vector[i].length;j++){
                if(vector[i][j]<0)
                    System.out.printf("     %.4f  ",vector[i][j]);
                else   
                    System.out.printf("      %.4f  ",vector[i][j]);
            }
            System.out.println();
        }
    }
    
    

}
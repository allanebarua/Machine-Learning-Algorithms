
/*
Author: ALLAN EMUSUGUT BARUA
P15/1719/2016
Java implementation of a perceptron
*/

package parcept;

import java.util.Date;
import java.util.Random;

public class Network {
    
    private double[][] H_weights, H_trans, O_weights, O_trans;
    private double[][] Oi,Ih,Oh,Io,Oo;
    private double[][] change_O, change_H;
    private final static double n =0.32;
    private int iter= 0;
    private int count2=0;
    private boolean epoch;
    private int e,target;
    
    
    
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
        
        //printing the weights
        System.out.println("\nInitialized weights of the hidden layer");
        vectorPrinter(H_weights);
        System.out.println("\n\nInitialized weights of the output layer");
        vectorPrinter(O_weights);
    }
    
    
    
    
    
    //train the neurone network using back propagation
    public void train(int[][] data){
        
        do{
            count2++;
            
            epoch =false;
            
            while(!epoch){
               //output of the input layer using a linear function
               //System.out.println(iter);
                Oi = new double[data[iter].length-1][1];

                for(int j=0;j<Oi.length;j++){
                    Oi[j][0] = data[iter][j];
                }

                //last value as the output
                target = data[iter][data[iter].length-1];



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

                //using the hardlim function
                Oh = new double[Ih.length][Ih[0].length];
                for(int i=0;i<Oh.length;i++){
                    if(Ih[i][0]>0)
                        Oh[i][0] = 1;
                    else
                        Oh[i][0] = 0; 
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

                //using the hardlim function
                Oo = new double[Io.length][Io[0].length];
                for(int i=0;i<Oo.length;i++){
                    if(Io[i][0]>0)
                        Oo[i][0] = 1;
                    else
                        Oo[i][0] = 0;
                }

                /*--------------------------------------------Error Calculation--------------------------------------------------*/
                //using nex;
                e = target - (int)Oo[0][0];

                //change in weights to the output layer
                //nex
                change_O = new double[O_weights.length][O_weights[0].length];
                for(int i=0;i<O_weights.length;i++){
                    for(int j=0;j<O_weights[i].length;j++){
                        change_O[i][j] = Oh[i][j] * n * e;
                    }
                }


                //change in weights of the hidden layer
                change_H = new double[H_weights.length][H_weights[0].length];
                for(int i=0;i<H_weights.length;i++){
                    for(int j=0;j<H_weights[i].length;j++){
                        change_H[i][j] = Oi[i][0] * n * e;
                    }
                }
                
                //print the colum headings to the table
                if(iter==0)
                    printLabels();
                
                //check if the epoch is complete
                if(iter==data.length-1)
                    epoch = true;
                else
                    iter++;
                
                //print the values in a table
                display(iter);
                
                
                
                
                //new weights for O_weights
                for(int i=0;i<O_weights.length;i++){
                    O_weights[i][0] = O_weights[i][0] + change_O[i][0];
                }

                //new weights for H_weights
                for(int i=0;i<change_H.length;i++){
                    for(int j=0; j<change_H[i].length;j++){
                        H_weights[i][j] = change_H[i][j] + H_weights[i][j];
                    }
                }       
           }
           //reset the no of iterations for the next epoch
           iter=0;
        }
        while(count2<10);
 
    }
    
     
    
    
    //function to construct transpose of a matrix
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
    
    
    
    
    
    //print the values in tabular form
    public void display(int count){
        
        //print inputs
        for(int i=0;i<Oi.length;i++){
            System.out.printf("%d   ",(int)Oi[i][0]);
        }
        System.out.print("\t");
        
        //print weights
        for(int i=0;i<H_weights.length;i++){
            for(int j=0;j<H_weights[i].length;j++){
                System.out.printf("%.2f  ",H_weights[i][j]);
            }
            
        }
        System.out.print("\t");
        
        System.out.printf(" %d\t",target);
        System.out.printf("%d\t",(int)Oo[0][0]);
        System.out.printf("%d",e);
        
        System.out.print("\t\t");
        
        //print change in weights
        for(int i=0;i<change_H.length;i++){
            for(int j=0;j<change_H[i].length;j++){
                System.out.printf("%.2f   ",change_H[i][j]);
            }
            
        }
        
        if(count==0)
            System.out.print("  \t");
        else
            System.out.print("  \t\t");
        //print inputs
        for(int i=0;i<Oh.length;i++){
            System.out.printf("%d   ",(int)Oh[i][0]);
        }
        System.out.print("\t");
        
        //print weights
        for(int i=0;i<O_weights.length;i++){
            for(int j=0;j<O_weights[i].length;j++){
                System.out.printf("%.2f  ",O_weights[i][j]);
            }
            
        }
        System.out.print("\t");
        
        //print change in weights
        for(int i=0;i<change_O.length;i++){
            for(int j=0;j<change_O[i].length;j++){
                System.out.printf("%.2f   ",change_O[i][j]);
            }
            
        }
        
        
        
        System.out.println("\n\n");      
    }
    
    
    
    
    
    //printing the initial weights as matrices
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
    
    
    
    
    
    // dynamically creat labels depending on the number of nodes and inouts
    public void printLabels(){
        
        System.out.println("\n\nEPOCH: "+ count2);
        System.out.println("___________________________________________________________________________________________________________________________________________________________________________________");
        System.out.println("\tINPUT --> HIDDEN LAYER\t\t\t\t\t\t\t\t\t\t\t\tHIDDEN --> OUTPUT LAYER\n"
                + "-------------------------------------------------------------------------------------------------------------         -------------------------------------------------------------\n"
                + "inputs\t\t\tweights\t\t\t\t\t\t\t\tChange in weights\t\tinputs\t\t weights\tChange in weights");
        //inputs
        for(int i=1;i<=Oi.length;i++){
            System.out.printf("X%d  ",i);
        }
        System.out.print("\t");
        
        //weights
        for(int i=0;i<H_weights.length;i++){
            for(int j=1;j<=H_weights[i].length;j++){
                System.out.printf("W%dh%d  ",i+1,j);
            }
            
        }
        System.out.print("\t");
        
        System.out.printf("Target  ");
        System.out.printf("Output  ");
        System.out.printf("Error  ");
        
        System.out.print("  \t"); 
       
        //change in weights
        for(int i=0;i<change_H.length;i++){
            for(int j=1;j<=change_H[i].length;j++){
                System.out.printf("CW%dh%d  ",i+1,j);
            }
            
        }
        
        System.out.print("  \t\t"); 
        
        //inputs
        for(int i=1;i<=Oh.length;i++){
            System.out.printf("X%d  ",i);
        }
        System.out.print("\t");
        
        //weights
        for(int i=0;i<O_weights.length;i++){
            for(int j=1;j<=O_weights[i].length;j++){
                System.out.printf("W%dh%d  ",i+1,j);
            }
            
        }
       
        System.out.print("\t");
        //change in weights
        for(int i=0;i<change_O.length;i++){
            for(int j=1;j<=change_O[i].length;j++){
                System.out.printf("CW%dh%d  ",i+1,j);
            }
            
        }
        System.out.println("\n___________________________________________________________________________________________________________________________________________________________________________________");
        
    }
    
    

}
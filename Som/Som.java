
/*
Author: ALLAN EMUSUGUT BARUA
P15/1719/2016
Java implementation of SOM
*/

package Somm;
import java.util.Date;
import java.util.Random;

public class Som {
    
    private final int latticeSize;
    private final int vectorSize;
    private final int iterations;
    private final double[][][] lattice;
    private int[] bmu = new int[2];
    private final double learningRate = 0.2;
    
    public Som(int x, int y, int z){
        latticeSize = x;
        vectorSize = y;
        iterations = z;
        
        
        //creating the lattice
        lattice = new double[latticeSize][latticeSize][vectorSize];
        
        //initializing the lattice with values btn 0 - 255
        Random rand = new Random(new Date().getTime());
        for(int i=0;i<lattice.length;i++){
            for(int j=0;j<lattice[i].length; j++){
                for(int k=0;k<lattice[i][j].length;k++)
                    lattice[i][j][k] = rand.nextDouble();
            }
        }
    }
    

    public void train(int[][] trainingSet){
        
        int i;
        
        Random rand = new Random(new Date().getTime());
        
        for(i=0;i<iterations;i++){
            
            System.out.println("Iteration "+ i);
            System.out.println("__________________________________________________________________________________________________________\n");
            
            //choose a training example at random
            int [] vector = trainingSet[rand.nextInt(trainingSet.length)];
            
            //print the training example
            System.out.print("Vector ---> ");
            for(int l: vector)
                System.out.print(l + " ");            
            System.out.println();
            
            //calculate the BMU
            calculateBmu(vector);
            System.out.print("\nBMU ---> ");
            for(int l: bmu)
                System.out.print(l + " ");
            System.out.println();
            
            /*calculate the neighbouhood*/
            
            //calculate neighbouhood radius
            double radius = calculateRadius(i);
            
            //calculate learning rate
            double rate = decayRate(i);
            
            //update weights in the neighbouhood
            updateNeighbourhood(radius,rate,vector);
            
            //print the lattice
            printLattice();
            
        }        
    }
    
    public void calculateBmu(int[] vector){
        
        System.out.println("Calculating the BMU");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println("Node Position        Example's Weights         Node's weights\t\t\tDistance with Example");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        
            //calculate the BMU
            double sum;
            double min = Double.POSITIVE_INFINITY;
            for(int m=0; m<lattice.length; m++){
                for(int j=0;j<lattice[m].length; j++){

                    sum= 0;
                    for(int k=0; k<lattice[m][j].length; k++){
                        sum += Math.pow(Math.abs(lattice[m][j][k] - vector[k]),2); 
                    }
                    System.out.printf("[%d %d]\t\t\t{%d %d %d}",m,j,vector[0],vector[1],vector[2]);
                    System.out.printf("\t\t{%4.2f %4.2f %4.2f}",lattice[m][j][0],lattice[m][j][1],lattice[m][j][2]);
                    System.out.printf("\t\t\t%4.2f\n",sum);
                    //compare the distance with the current distance
                    if(sum< min){
                        min = sum;
                        bmu[0] = m;
                        bmu[1] = j;                    
                    }
                }
            }
    }
    
    
    
    //radius of the neighbourhood shrink over time
    public double calculateRadius(int timestep){
        //calculating current radius using an exponential decay function
        double radius=(latticeSize/2)*Math.exp((double)-timestep/((double)iterations/Math.log((double)latticeSize/2)));
        return radius;
    }
    
    
    
    //learning rate also decays over time
    public double decayRate(int timestep){    
        return learningRate * Math.exp(-timestep/((double)iterations));
        
    }
    
    
    
    public void updateNeighbourhood(double radius, double rate, int[] vector){
        System.out.printf("\n\n\nRadius        ---->%5.6f\n",radius);
        System.out.printf("Learning Rate ---->%5.6f\n\n", rate);
        System.out.println("Nodes in the neighbouhood calcualtion");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println("Node position      Distance from BMU       status      Influence");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        
        for(int i=0; i<lattice.length; i++){
            for(int j=0;j<lattice[i].length; j++){
                double distance = dist(i,j);
                double influence = 0;
                
                if(distance<=radius){
                    //decay update influence
                    influence = decayInfluence(distance,radius);
                    System.out.printf("[%d %d]",i,j);System.out.printf("                    %4.2f",distance);System.out.printf("               %s           %4.2f\n","Yes",influence);
                }
                
                else{
                    System.out.printf("[%d %d]",i,j);System.out.printf("                    %4.2f",distance);System.out.printf("               %s         %s\n","No","Not Applicable");
                }

                //updating the weights
                for(int k=0; k<lattice[i][j].length; k++){
                    
                    lattice[i][j][k]=lattice[i][j][k]+(influence*rate*(vector[k]-lattice[i][j][k]));
                }
            }
        }
    } 
    
    
    //distance between a given node and the bmu
    public double dist(int i, int j){
        double distance = Math.sqrt(Math.pow((bmu[0] - i),2) + Math.pow((bmu[1] - j),2));        
        return Math.sqrt(distance);
    }
    
    
    //decay the learning influence
    public double decayInfluence(double dist, double radius){
        
        return Math.exp(-(dist*dist)/(2*radius*radius));
        
    }
    
    //prints the lattice
    public void printLattice(){
        System.out.println("\nLattice");
            for(double [][]l: lattice){
                System.out.print("");
                for(double [] g: l){
                    System.out.print("[");
                    for(double f: g){
                        System.out.printf("%4.2f ",f);
                    }
                    System.out.print("] ");
                    System.out.print("\t");
                }
                System.out.println("");
            }
            System.out.print("\n\n\n\n");
    }
       
}

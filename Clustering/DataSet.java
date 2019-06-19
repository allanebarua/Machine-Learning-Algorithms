
/*
Author: ALLAN EMUSUGUT BARUA
P15/1719/2016
Java implementation of K- Means algorithm
*/

package Clustering;
import javafx.geometry.Point2D;

public class DataSet {
    private Point2D coord;
    private int group;
    private static double[][] centroids;
    private static double[][] distances;
    private static int round = 1;
    private static boolean groupTest;
    
    public DataSet(int x, int y){
        coord = new Point2D(x,y);  
    }
    
    public static void chooseCentroids(int choice, DataSet[] obj){
        groupTest = true;
        if(round==1){
            centroids = new double[choice][2];
            for(int i=0;i<choice;i++){
                centroids[i][0] = obj[i].coord.getX();
                centroids[i][1] = obj[i].coord.getY();           
            }            
        }
        else{   
            for(int i=0;i<centroids.length;i++){
                double sumX=0;
                double sumY=0;
                double num =0;
                for(int j=0;j<obj.length;j++){
                    
                    if(obj[j].group==i){
                        sumX += obj[j].coord.getX();
                        sumY += obj[j].coord.getY();
                        num++;
                    }
                }
                centroids[i][0] = sumX/num;
                centroids[i][1] = sumY/num;
                
            }             
        }
        
    }
    
    public static void computeDist(DataSet[] obj){
        distances = new double[obj.length][centroids.length];
        System.out.println("Round "+ round );
        System.out.println("--------------------------------------------------------------------------");
        for(int i=0;i<obj.length;i++){
            for(int j=0;j<centroids.length;j++){
                distances[i][j] = obj[i].coord.distance(centroids[j][0], centroids[j][1]);
                System.out.printf("DataObject %d Distance with centroid %d: %4.2f\n",i+1,j+1,distances[i][j]);
            }            
        }
    }
    
    public static void clusterObj(DataSet[] obj){
        for(int i=0;i<obj.length;i++){
            double min=distances[i][0];
            int m = 0;
            for(int j=1;j<distances[i].length;j++){
                if(min >= distances[i][j]){
                    min = distances[i][j];
                    m = j;
                }
            }
            //assign initial group
            if(round==1){
                obj[i].group = m;
                groupTest = false;
            }
            //check for group change first
            else{
                //compare first
                if(obj[i].group != m){
                    groupTest = false;
                    obj[i].group = m;                    
                }
                    
            }           
        }
        round++;
    }
    
    public static boolean convergenceCheck(){
        return groupTest;
    }
    
    public static void printState(DataSet[] obj){
        System.out.println("\nDataObject\t Coordinates\t Cluster\t Centroid");
        System.out.println("_________________________________________________________________");
        for(int i =0;i<obj.length;i++){
            System.out.printf("  %d\t\t(%4.2f,%4.2f)\t  %d\t\t(%4.2f,%4.2f)\n",
                            i+1,obj[i].coord.getX(),obj[i].coord.getY(),obj[i].group+1,
                            centroids[obj[i].group][0],centroids[obj[i].group][1]);
        }
        System.out.println("\n\n");
    }
    
}

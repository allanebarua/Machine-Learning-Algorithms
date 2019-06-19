
package Backpropagation;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader {

    public static double[][] data(String path) {

        String csvFile = path;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        double[][] trainingSet;
        int count=0;
        String[][] data = new String[2000][2000];

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {               
                // use comma as separator
                data[count] = line.split(cvsSplitBy);
                count++;

            }
            
            //System.out.println(count);
            trainingSet = new double[count][data[0].length];
            int i;
            for(i=0;i<(count);i++){
                for(int j=0;j<data[i].length;j++){
                    trainingSet[i][j] = Double.parseDouble(data[i][j]);
                }
            }
//            for(i=0;i<(count);i++){
//                for(int j=0;j<data[i].length-1;j++){
//                    trainingSet[i][j] = Double.parseDouble(data[i][j])/20;
//                }
//            }
            //System.out.println((count)+ " "+i);
            
//            for(double [] e: trainingSet){
//                for(double m: e)
//                    System.out.print(m + "  ");
//                System.out.println();
//            }
                        
        return trainingSet;
        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return null;

    }

}
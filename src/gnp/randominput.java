/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gnp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import static gnp.Gnp.attributeamount;
import static gnp.Gnp.dataamount;
import static gnp.Gnp.testdate;

/**
 *
 * @author test
 */
public class randominput {
    public static int randomrange(int min,int max){
        int randomvalue = min + (int)(Math.random() * ((max - min) + 1));
        return randomvalue;
    }
    public static int[][] randomdataset() throws IOException{
        int[][] attributerand = new int[attributeamount][2];
        for(int j=0;j<attributeamount;j++){
            int rand1 = randomrange(1,1000);
            int rand2 = rand1+randomrange(1,1000);
            attributerand[j][0] = rand1;
            attributerand[j][1] = rand2;
        }
        int[][] data = new int[dataamount][attributeamount];
        
        BufferedWriter out = new BufferedWriter(new FileWriter("log/"+testdate+"/log.csv"));
        out.write(" ,");
        for(int j=0;j<attributeamount;j++){
            int k=j+1;
            out.write(k+",");
        }
        out.newLine();
        for(int i=0;i<dataamount;i++){
            int k=i+1;
            out.write(k+",");
            for(int j=0;j<attributeamount;j++){
                    data[i][j] = randomrange(attributerand[j][0],attributerand[j][1]);
                    out.write(data[i][j]+",");
            }
            out.newLine();
        }
        out.close();
        return data;
    }
    public static int[][] randomdatasetpattern(int pattern) throws IOException{
        int[][] data = new int[dataamount][attributeamount];
        int[][] datapattern = new int[attributeamount][pattern+1];
        for(int j=0;j<attributeamount;j++){
            for(int k=0;k<(pattern);k++){
                datapattern[j][k] = randomrange(1,1000);
            }
        }
        BufferedWriter out = new BufferedWriter(new FileWriter("log/"+testdate+"/log.csv"));
        out.write(" ,");
        for(int j=0;j<attributeamount;j++){
            int k=j+1;
            out.write(k+",");
        }
        out.newLine();
        for(int i=0;i<dataamount;i++){
            for(int j=0;j<attributeamount;j++){
                int maxrand = randomrange(0,pattern);
                data[i][j] = datapattern[j][maxrand];
                out.write(data[i][j]+",");
            }
            out.newLine();
        }
        out.close();
        return data;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gnp;

import static gnp.Gnp.attributeamount;
import static gnp.Gnp.dataamount;
import static gnp.Gnp.testdate;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author test
 */
public class Statistics {
    double[] data;
    double size;
    
    public static double[] getstatistics(int[][] data) throws IOException{
        BufferedWriter out = new BufferedWriter(new FileWriter("log/"+testdate+"/static.log"));
        double[] stddev = new double[attributeamount];
        for(int j=0;j<attributeamount;j++){
            double[] test = getmaxmin(data,j);
            stddev[j] = test[2];
            out.write("attribute "+j+" min["+test[0]+"] max["+test[1]+"] stddev["+test[2]+"] "
                    + "mean ["+test[3]+"] variance["+test[4]+"] median["+test[5]+"]");
            out.newLine();
        }
        out.close();
        return stddev;
    }
    public static double[] getmaxmin(int[][] data,int attribute){
        double[] out = new double[6];
        int[] attr = new int[dataamount];
        for(int i=0;i<dataamount;i++){
            attr[i] = data[i][attribute];
        }
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < attr.length; i++) {
            if(attr[i] > max) {
                max = attr[i];
            }
        }
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < attr.length; i++) {
            if(attr[i] < min) {
                min = attr[i];
            }
        }
        double[] attr2 = converttodouble(attr);
        Statistics attr1 = new Statistics(attr2);
        out[0] = min;
        out[1] = max;
        out[2] = attr1.getStdDev();
        out[3] = attr1.getMean();
        out[4] = attr1.getVariance();
        out[5] = attr1.median();
        return out;
    }
    public static double[] converttodouble(int[] dataset){
        double[] out = new double[dataamount];
        for(int i=0;i<dataset.length;i++){
            out[i] = dataset[i];
        }
        return out;
    }
    public Statistics(double[] data) 
    {
        this.data = data;
        size = data.length;
    }   

    double getMean()
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
            return sum/size;
    }

        double getVariance()
        {
            double mean = getMean();
            double temp = 0;
            for(double a :data)
                temp += (mean-a)*(mean-a);
                return temp/size;
        }

        double getStdDev()
        {
            return Math.sqrt(getVariance());
        }

        public double median() 
        {
               double[] b = new double[data.length];
               System.arraycopy(data, 0, b, 0, b.length);
               Arrays.sort(b);

               if (data.length % 2 == 0) 
               {
                  return (b[(b.length / 2) - 1] + b[b.length / 2]) / 2.0;
               } 
               else 
               {
                  return b[b.length / 2];
               }
        }
}

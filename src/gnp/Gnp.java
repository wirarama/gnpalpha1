/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gnp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author test
 */
public class Gnp {
   
    /**
     * @param args the command line arguments
     */
    static final int attributeamount = 50;
    static final int dataamount = 2500;
    static int[] cluster = {500,300,600,400,350,269,390,600,987,897};
    static int clusterindex = 0;
    static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    static final Date date = new Date();
    static String testdate = dateFormat.format(date);
    static int[] selected = new int[dataamount];
    static int selectedindex = 0;
        
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        (new File("log/"+testdate+"")).mkdirs();
        /*readcsvfile test = new readcsvfile();
        int[][] data = test.readcsvfile("vehicle_preprocess2.csv");
        attributeamount = readcsvfile.attributeamount1;
        dataamount = readcsvfile.dataamount1;*/
        int[][] data = randominput.randomdataset();
        ram test0 = ram.ram(data);
        int[][][] test1 = ram.outruleattribute;
        int[][][] test2 = ram.makerule(data);
        double[] test3 = Statistics.getstatistics(data);
        //int[][] data = randominput.randomdatasetpattern(2,testdate);
        //double[] stddev = getstatistics(data);
        //getram(stddev,data);
        //knapsack knapsack = new knapsack();
        //knapsack.knapsack();
        plot.splitplot(data);
        
    }
    public static void getram(double[] stddev,int[][] data) throws IOException{
        BufferedWriter out1 = new BufferedWriter(new FileWriter("log/ram"+testdate+".log"));
        int[] supports = new int[dataamount];
        for(int i=0;i<dataamount;i++){
            if(inarray(selected,i)==false){
                supports[i] = findsupport(data,i,stddev,testdate);
                String clusterlabel;
                if(clusterindex>cluster.length){
                    clusterlabel = "undistributed ";
                }else if(clusterindex==cluster.length){
                    clusterlabel = "cluster "+clusterindex;
                    clusterindex = clusterindex+1;
                }else{
                    clusterlabel = "cluster "+clusterindex;
                }
                out1.write(clusterlabel+" ["+supports[i]+" data]");
                out1.newLine();
                selected[selectedindex] = i;
                selectedindex = selectedindex+1;
            }
        }
        out1.write("total support["+sumarray(supports)+"]");
        out1.close();
    }
    public static int findsupport(int[][] data,int index,double[] stddev,String testdate) throws IOException{
        int support = 0;
        int[] test = new int[attributeamount];
        boolean success = (new File("log/detail"+testdate+"")).mkdirs();
        String logfilelabel;
        if(clusterindex>cluster.length){
            logfilelabel = "undistributed";
        }else if(clusterindex==cluster.length){
            logfilelabel = "cluster-"+clusterindex;
        }else{
            logfilelabel = "cluster-"+clusterindex;
        }
        BufferedWriter out = new BufferedWriter(new FileWriter("log/detail"+testdate+"/"+logfilelabel+".csv"));
        BufferedWriter out1 = new BufferedWriter(new FileWriter("log/detail"+testdate+"/"+logfilelabel+".log"));
        for(int j=0;j<attributeamount;j++){
            test[j] = data[index][j];
            out.write(test[j]+",");
        }
        out.newLine();
        int[] rulepool = new int[attributeamount];
        int[] rulecount = new int[attributeamount];
        int rulepoolindex = 0;
        LOOP: for(double rate=1.0;rate>0;rate=rate-0.05){
            for(int i=0;i<dataamount;i++){
                int support1 = 0;
                for(int j=0;j<attributeamount;j++){
                    double min = test[j]-stddev[j];
                    double max = test[j]+stddev[j];
                    if(min<=data[i][j] && max>=data[i][j]){
                        support1 = support1+1;
                        if(inarray(rulepool,j)==false){
                            rulepool[rulepoolindex] = j;
                            rulepoolindex = rulepoolindex+1;
                            rulecount[j] = 1;
                        }else{
                            rulecount[j] = rulecount[j]+1;
                        }
                    }
                }
                if(support1 > (attributeamount*rate) && i!=index){
                    if(inarray(selected,i)==false){
                        selected[selectedindex] = i;
                        selectedindex = selectedindex+1;
                        support = support+1;
                        for(int j=0;j<attributeamount;j++){
                            out.write(data[i][j]+",");
                        }
                        out.newLine();
                        int clustersize;
                        if(clusterindex>=cluster.length){
                            clustersize = dataamount;
                        }else{
                            clustersize = cluster[clusterindex]-1;
                        }
                        if(support==clustersize){
                            clusterindex = clusterindex+1;
                            break LOOP;
                        }
                    }
                }
            }
        }
        for(int j=0;j<attributeamount;j++){
            double min = test[j]-stddev[j];
            double max = test[j]+stddev[j];
            out1.write("[attribute"+j+" is between "+min+" and "+max+"] hit "+(rulecount[j]/attributeamount));
            out1.newLine();
        }
        out1.close();
        out.close();
        support=support+1;
        return support;
    }
    public static boolean inarray(int[] data,int key){
        boolean exist = false;
        for(int i=0;i<data.length;i++){
            if(data[i]==key){
                exist = true;
                break;
            }
        }
        return exist;
    }
    public static int sumarray(int[] data){
        int sum = 0;
        for(int i=0;i<data.length;i++){
            sum = sum+data[i];
        }
        return sum+1;
    }
    
    
}

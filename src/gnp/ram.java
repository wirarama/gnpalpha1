/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gnp;

import static gnp.Statistics.getmaxmin;
import static gnp.Gnp.testdate;
import static gnp.Gnp.attributeamount;
import static gnp.Gnp.dataamount;
import static gnp.randominput.randomrange;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author test
 */

public class ram {
    static int[][][] outruleattribute;
    static int[][][] outbestruleattribute;
    static int limit =4;
    static int attrnode = (int) (attributeamount*0.2);
    static final int ruleamount = (int) (dataamount*0.2);
    static int[] rulesdata = new int[ruleamount];
    
    public static ram ram(int[][] data) throws IOException{
        BufferedWriter out = new BufferedWriter(new FileWriter("log/"+testdate+"/rulevariable.log"));
        //int limit =10;
        int[][][] ruleattribute = new int[attributeamount][limit][2];
        int[][][] bestruleattribute = new int[attributeamount][limit][2];
        int bestlimit = dataamount/4;
        for(int i=0;i<attributeamount;i++){
            double[] attributedata = getmaxmin(data,i);
            int k=0;
            int l=0;
            out.write("Attribute "+(i+1)+"\r\n");
            RULE: for(int j=(int) attributedata[0];j<=attributedata[1];j=(int) (j+attributedata[2])){
                int count = dataperattribute(data,i,j, (int) (j+attributedata[2]));
                //System.out.println(k+"\t"+j+"\t"+(int) (j+attributedata[2]));
                ruleattribute[i][k][0] = j;
                ruleattribute[i][k][1] = (int) (j+attributedata[2]);
                if(count>=bestlimit){
                    bestruleattribute[l][k][0] = j;
                    bestruleattribute[l][k][1] = (int) (j+attributedata[2]);
                    
                }
                out.write(k+"\t"+ruleattribute[i][k][0]+"\t"+ruleattribute[i][k][1]+"\t"+count+"\r\n");
                if((k+1)>=limit || attributedata[2]<1){
                    break RULE;
                }
                k++;
            }
            out.newLine();
        }
        out.close();
        ram output = new ram();
        ram.outruleattribute = ruleattribute;
        ram.outbestruleattribute = ruleattribute;
        return output;
    }
    public static int[][][] makerule(int[][] data) throws IOException{
        (new File("log/"+testdate+"/detail")).mkdirs();
        int[][][] rules = new int[ruleamount][attrnode][4];
        BufferedWriter out1 = new BufferedWriter(new FileWriter("log/"+testdate+"/rules.log"));
        for(int j=0;j<ruleamount;j++){
            BufferedWriter out = new BufferedWriter(new FileWriter("log/"+testdate+"/detail/rules"+(j+1)+".log"));
            int[][] rule = new int[attrnode][4];
            for(int i=0;i<attrnode;i++){
                int attrindex = randomrange(0,(attributeamount-1));
                int value = randomrange(0,(limit-1));
                rules[j][i][0] = attrindex;
                rules[j][i][1] = value;
                rules[j][i][2] = outruleattribute[attrindex][value][0];
                rules[j][i][3] = outruleattribute[attrindex][value][1];
                rule[i][0] = attrindex;
                rule[i][1] = value;
                rule[i][2] = outruleattribute[attrindex][value][0];
                rule[i][3] = outruleattribute[attrindex][value][1];
                out.write("attribute "+(attrindex+1)+"\t"+rules[j][i][2]+"\t"+rules[j][i][3]+"\r\n");
            }
            int supp = dataperrule(data,rule,j);
            double supppercent = ((double) supp/(double) dataamount)*100;
            int confidence = dataperattribute(data,rule[0][0],rule[0][2], rule[0][3]);
            double confpercent = ((double) supp/(double) confidence)*100;
            //System.out.println("dataamount "+supp+"\t"+supppercent);
            
            out1.write("rule "+(j+1)+"\t\t"+supp+"\t"+supppercent+"\t"+confpercent+"\r\n");
            out.close();
        }
        out1.close();
        return rules;
    }
    public static int dataperrule(int[][] data,int[][] rule,int index) throws IOException{
        BufferedWriter out = new BufferedWriter(new FileWriter("log/"+testdate+"/detail/rulesdata"+(index+1)+".csv"));
        int count = 0;
        for(int i=0;i<dataamount;i++){
            int supp = 0;
            for(int j=0;j<attrnode;j++){
                if(rule[j][2]<=data[i][rule[j][0]] && rule[j][3]>=data[i][rule[j][0]]){
                    supp=supp+1;
                }
            }
            if(supp==attrnode){
                count=count+1;
                for(int k=0;k<attributeamount;k++){
                    out.write(data[i][k]+",");
                }
                out.newLine();
            }
        }
        out.close();
        return count;
    }
    public static int dataperattribute(int[][] data,int index,int min, int max){
        int count = 0;
        for(int i=0;i<dataamount;i++){
            if(data[i][index]>=min && data[i][index]<=max){
                count=count+1;
            }
        }
        return count;
    }
}

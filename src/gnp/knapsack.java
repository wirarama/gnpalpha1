/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gnp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author test
 */
public class knapsack {
    static final int N=60;
    static int[] profit = new int[N+1];
    static int[] weight = new int[N+1];
    static int[][] left = new int[N+1][2];
    static int[] W1 = {100,400,200,320,400,200,300,600};
    static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static final Date date = new Date();
    static String testdate = dateFormat.format(date);
    
    public static void knapsack() throws IOException{
        for (int n = 1; n <= N; n++) {
            profit[n] = randomrange(1,100);
            weight[n] = randomrange(1,100);
        }
        BufferedWriter out = new BufferedWriter(new FileWriter("log/knapsack"+testdate+".log"));
        for(int j=0;j<W1.length;j++){
            out.write("--------------------------------------------------------------");
            out.newLine();
            left = new int[N+1][2];
            int W = W1[j];
            int[][] kp = knapsack(N,W,profit,weight);

            out.write("take ["+W+"]");
            out.newLine();
            int totalw = 0;
            int totalv = 0;
            for(int i=1;i<=N;i++){
                if(kp[i][0]!=0 && kp[i][1]!=0){
                    out.write(i+"\t"+kp[i][0]+"\t"+kp[i][1]);
                    out.newLine();
                    totalv = totalv+kp[i][0];
                    totalw = totalw+kp[i][1];
                }
            }
            out.write("total \t"+totalv+"\t"+totalw);
            out.newLine();

            profit = new int[N+1];
            weight = new int[N+1];
            out.newLine();
            out.write("left");
            out.newLine();
            int totalw1 = 0;
            int totalv1 = 0;
            int k=1;
            for(int i=1;i<=N;i++){
                if(left[i][0]!=0 && left[i][1]!=0){
                    out.write(i+"\t"+left[i][0]+"\t"+left[i][1]);
                    out.newLine();
                    totalv1 = totalv1+left[i][0];
                    totalw1 = totalw1+left[i][1];
                    profit[k] = left[i][0];
                    weight[k] = left[i][1];
                    k++;
                }
            }
            out.write("total \t"+totalv1+"\t"+totalw1);
            out.newLine();
        }
        out.close();
    }
    public static int[][] knapsack(int N,int W,int[] profit,int[] weight){
        int[][] out = new int[N+1][2];
        int[][] opt = new int[N+1][W+1];
        boolean[][] sol = new boolean[N+1][W+1];
        
        for (int n = 1; n <= N; n++) {
            for (int w = 1; w <= W; w++) {

                int option1 = opt[n-1][w];

                int option2 = Integer.MIN_VALUE;
                if (weight[n] <= w) option2 = profit[n] + opt[n-1][w-weight[n]];

                opt[n][w] = Math.max(option1, option2);
                sol[n][w] = (option2 > option1);
            }
        }

        boolean[] take = new boolean[N+1];
        for (int n = N, w = W; n > 0; n--) {
            if (sol[n][w]) { take[n] = true;  w = w - weight[n]; }
            else           { take[n] = false;                    }
        }

        int i=0;
        int j=0;
        for (int n = 1; n <= N; n++) {
            if(take[n]==true){
                out[j][0] = profit[n];
                out[j][1] = weight[n];
                j=j+1;
            }else{
                left[i][0] = profit[n];
                left[i][1] = weight[n];
                i=i+1;
            }
        }
        return out;
    }
    public static int randomrange(int min,int max){
        int randomvalue = min + (int)(Math.random() * ((max - min) + 1));
        return randomvalue;
    }
}

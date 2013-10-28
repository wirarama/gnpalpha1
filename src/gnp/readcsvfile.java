/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gnp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static gnp.Gnp.attributeamount;
import static gnp.Gnp.dataamount;
/**
 *
 * @author test
 */
public class readcsvfile {
    static int dataamount1=0;
    static int attributeamount1=0;
    public int[][] readcsvfile(String csvFile) {
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";
        int[][] data = new int[dataamount][attributeamount];
        try {
                br = new BufferedReader(new FileReader(csvFile));
                int i=0;
                while ((line = br.readLine()) != null) {
                        String[] out = line.split(cvsSplitBy);
                        for(int j=0;j<out.length;j++){
                            data[i][j] = Integer.parseInt(out[j]);
                        }
                        i++;
                        attributeamount1=out.length;
                }
                dataamount1=i;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
                if (br != null) {
                        try {
                                br.close();
                        } catch (IOException e) {
                        }
                }
        }
        return data;
  }
}

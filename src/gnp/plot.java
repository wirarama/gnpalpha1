/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gnp;
import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.AbstractPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.terminal.ImageTerminal;
import static gnp.Gnp.attributeamount;
import static gnp.Gnp.dataamount;
import static gnp.Gnp.testdate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author test
 */
public class plot {
    public static void splitplot(int[][] data){
        int x=1;
        for(int i=0;i<attributeamount;i=i+5){
            makeplot(data,true,i,x);
            x++;
        }
    }
    public static void makeplot(int[][] data,boolean fileexport,int start,int x){
         JavaPlot p = new JavaPlot();
        p.setTitle("test "+testdate);
        p.setKey(JavaPlot.Key.BELOW);
        p.getAxis("x").setLabel("data");
        p.getAxis("y").setLabel("value");
        int k=0;
        //int x=1;
        int end = start+5;
        if(end>=attributeamount) end = attributeamount;
        for(int i=start;i<end;i++){
            int[][] data1 = new int[dataamount][2];
            for(int j=0;j<dataamount;j++){
                data1[j][0] = j;
                data1[j][1] = data[j][i];
            }
            p.addPlot(data1);
            ((AbstractPlot) p.getPlots().get(k)).setTitle("attr "+(i+1));
            PlotStyle stl = ((AbstractPlot) p.getPlots().get(k)).getPlotStyle();
            stl.setStyle(Style.LINES);
            ImageTerminal png = new ImageTerminal();
            (new File("log/"+testdate+"/plot")).mkdirs();
            File file = new File("log/"+testdate+"/plot/plot("+x+").png");
            try {
                file.createNewFile();
                png.processOutput(new FileInputStream(file));
            } catch (FileNotFoundException ex) {
                System.err.print(ex);
            } catch (IOException ex) {
                System.err.print(ex);
            }
            if(fileexport==true){ p.setTerminal(png); }
            p.plot();
            if(fileexport==true){
                try {
                    ImageIO.write(png.getImage(), "png", file);
                } catch (IOException ex) {
                    System.err.print(ex);
                }
            }
            k++;
        }
    }
}

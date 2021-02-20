import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;
import java.util.Random;
import java.lang.Math;

public class KMeans extends Component {

    public static void main(String[] foo) {
        new KMeans();
    }

    /*public void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    }*/

    private void marchThroughImage(BufferedImage image) throws IOException {
        int w = image.getWidth();
        int h = image.getHeight();
        int x=0;
        Random rand = new Random();
        System.out.println("width, height: " + w + ", " + h);
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        int arr[][]=new int[k][3];
        int value[]=new int[h*w];
        int minDistanceIndex[]=new int[h*w];
        double sumArray[][]=new double[k][4];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.println("x,y: " + j + ", " + i);
                int pixel = image.getRGB(j, i);
                //printPixelARGB(pixel);
                System.out.println("");
                value[x]=pixel;
                x++;
            }
        }
        for(int i=0;i<h*w;i++) System.out.println(value[i]);
        for(int i=0;i<k;i++){
            for(int j=0;j<3;j++){
                arr[i][j]=rand.nextInt(256);
                //arr[i][j]=Double.valueOf(a);
            }
        }
        while(true)
        {
            int z=0;
            for(int j=0;j<h*w;j++)
            {
                double minDistance=99999999.99;
                for(int i=0;i<k;i++)
                {
                    double distance=Math.sqrt(Math.pow((arr[i][0]- ((value[j] >> 16) & 0xff)),2)+
                            Math.pow((arr[i][1]- ((value[j] >> 8) & 0xff)),2)+
                            Math.pow((arr[i][2]- ((value[j]) & 0xff)),2));
                    if(distance<minDistance)
                    {
                        minDistanceIndex[j]=i;
                        minDistance=distance;
                    }
                }
                sumArray[minDistanceIndex[j]][0]+=(value[j] >> 16) & 0xff;
                sumArray[minDistanceIndex[j]][1]+=(value[j] >> 8) & 0xff;
                sumArray[minDistanceIndex[j]][2]+=(value[j]) & 0xff;
                sumArray[minDistanceIndex[j]][3]+=1;
            }
            for(int i=0;i<k;i++)
            {
                sumArray[i][0]/=sumArray[i][3];
                sumArray[i][1]/=sumArray[i][3];
                sumArray[i][2]/=sumArray[i][3];
            }
            for(int i=0;i<k;i++)
            {
                System.out.println(sumArray[i][0]+" "+sumArray[i][1]+" "+sumArray[i][2]);
            }
            int count=0;
            for(int i=0;i<k;i++){
                if(Math.sqrt(Math.pow((arr[i][0]-sumArray[i][0]),2)+Math.pow((arr[i][1]-sumArray[i][1]),2)
                        +Math.pow((arr[i][2]-sumArray[i][2]),2))>=1.2){
                    count=1;
                    break;
                }
                else continue;
            }
            System.out.println(count);
            if(count==0)
                break;
            else
            {
                for(int i=0;i<k;i++)
                {
                    for(int j=0;j<3;j++)
                    {
                        arr[i][j]=(int)sumArray[i][j];
                    }
                }
                for(int i=0;i<k;i++)
                {
                    for(int j=0;j<4;j++)
                    {
                        sumArray[i][j]=0;
                    }
                }
            }
            System.out.println("Iteration: "+z);
            z++;
        }
        for(int i=0;i<k;i++) {
            for (int j = 0; j < 3; j++) System.out.println(arr[i][j]);
        }
        int a=0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                //System.out.println("x,y: " + j + ", " + i);
                int pixel = image.getRGB(j, i);
                Color temp;
                temp = new Color(arr[minDistanceIndex[a]][0],arr[minDistanceIndex[a]][1],arr[minDistanceIndex[a]][2]);
                image.setRGB(j, i, temp.getRGB());
                a++;
            }
        }
        ImageIO.write(image, "jpg", new File("image.bmp"));

    }

    public KMeans() {
        try {
            // get the BufferedImage, using the ImageIO class
            BufferedImage image =
                    ImageIO.read(this.getClass().getResource("download.jfif"));
            marchThroughImage(image);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
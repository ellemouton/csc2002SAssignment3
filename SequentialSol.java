
import java.util.*; 
import java.io.*;

public class SequentialSol{

    static long startTime = 0;
    private static void tick(){
        startTime = System.currentTimeMillis();    
    }
    private static float tock(){
        return (System.currentTimeMillis()-startTime)/1000.0f;
    }
    
    public static double sunMap[][] = null;
        
    public static void main(String[] args){
    
            String fileNameIn = args[0];
            String fileNameOut = args[1];

            String[] sunValues;
            int numTrees=0;
            Tree[] trees = null;
            
            //getting the data from text file
            
            try{
                //access text file
                BufferedReader bf = new BufferedReader(new FileReader(fileNameIn));
                
                //get sunmap info
                String[] sizeOfGrid = bf.readLine().split(" ");
                int sizeY = Integer.parseInt(sizeOfGrid[0]);
                int sizeX = Integer.parseInt(sizeOfGrid[1]);
                sunMap = new double[sizeY][sizeX];
                sunValues = bf.readLine().split(" ");

                //get tree info
                numTrees = Integer.parseInt(bf.readLine());
                trees = new Tree[numTrees];


                //populate sunmap array
                int counter = 0;
                
                for(int y = 0; y<sizeY;y++){
                    for(int x = 0; x<sizeX; x++){
                        sunMap[y][x]=Float.parseFloat(sunValues[counter]);
                        counter++;
                    }
                }
                
                //populate Tree array
                for(int i =0; i<numTrees; i++){
                    String[] treeInfo = bf.readLine().split(" ");
                    int yStart = Integer.parseInt(treeInfo[0]);
                    int xStart = Integer.parseInt(treeInfo[1]);
                    int size = Integer.parseInt(treeInfo[2]);
                    trees[i] = new Tree(yStart,xStart,size,0);
                }
                
                bf.close();
                
            }
            catch(Exception e){
                System.out.println(e);
            }
            
            double ans = 0;
            
            int sunMapYlimit = sunMap.length;
            int sunMapXlimit = sunMap[0].length;

            tick();

            for(int i=0;i<numTrees;i++){
                Tree t = trees[i];
                int xStart = t.xCorner;
                int yStart = t.yCorner;
                int xSize = t.size;
                int ySize = t.size;
                
                if(sunMapXlimit-(xStart+xSize)<0){
                    xSize = sunMapXlimit-xStart;
                }
                if(sunMapYlimit-(yStart+ySize)<0){
                    ySize = sunMapYlimit-yStart;
                }
                
                for(int y = yStart; y<yStart+ySize; y++){
                    for(int x = xStart; x<xStart+xSize; x++){
                        t.sunLight += sunMap[y][x];
                    }
                }
                ans+=t.sunLight;
                          
            }

            double total = 0;
            for(int i=0;i<numTrees;i++){
                total +=trees[i].sunLight;
            }

            System.out.println(tock());

            //write values to textfile
            try{
                FileWriter fw = new FileWriter(fileNameOut);
                BufferedWriter bfw = new BufferedWriter(fw);
                
                bfw.write(String.format("%.6f",ans/numTrees));
                bfw.newLine();
                bfw.write(numTrees+"");
            
                for(int i =0; i<numTrees; i++){
                    bfw.newLine();
                    bfw.write(String.format("%.6f",trees[i].sunLight));
                }
                
                bfw.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
            
    }//end main

}//end class
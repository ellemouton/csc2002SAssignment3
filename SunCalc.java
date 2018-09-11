
import java.util.concurrent.ForkJoinPool;
import java.util.*; 
import java.io.*;

public class SunCalc{
    
    static final ForkJoinPool fjPool = new ForkJoinPool();
    public static float sunMap[][] = null;
    static long startTime = 0;
    
    private static void tick(){
        startTime = System.currentTimeMillis();    
    }
    private static float tock(){
        return (System.currentTimeMillis()-startTime)/1000.0f;
    }
    
    static float sum(Tree[] arr){
        return fjPool.invoke(new SunThread(arr,0,arr.length));
    }
        
    public static void main(String[] args){
    
            String fileNameIn = args[0];
            String fileNameOut = args[1];

            String[] sunValues;
            int numTrees=1;
            Tree[] trees = null;
            
            //getting the data from tet file
            
            try{
                //access text file
                BufferedReader bf = new BufferedReader(new FileReader(fileNameIn));
                
                //get sunmap info
                String[] sizeOfGrid = bf.readLine().split(" ");
                int sizeX = Integer.parseInt(sizeOfGrid[0]);
                int sizeY = Integer.parseInt(sizeOfGrid[1]);
                sunMap = new float[sizeX][sizeY];
                sunValues = bf.readLine().split(" ");
                //get tree info
                numTrees = Integer.parseInt(bf.readLine());
                trees = new Tree[numTrees];

                //populate sunmap array
                int counter = 0;
                
                for(int x = 0; x<sizeX;x++){
                    for(int y = 0; y<sizeY; y++){
                        sunMap[x][y]=Float.parseFloat(sunValues[counter]);
                        counter++;
                    }
                }
                
                //populate Tree array
                for(int i =0; i<numTrees; i++){
                    String[] treeInfo = bf.readLine().split(" ");
                    int xStart = Integer.parseInt(treeInfo[0]);
                    int yStart = Integer.parseInt(treeInfo[1]);
                    int size = Integer.parseInt(treeInfo[2]);
                    trees[i] = new Tree(xStart,yStart,size,0);
                }
                
                bf.close();
                
            }
            catch(Exception e){
                System.out.println(e);
            }
            
            //write values to textfile
            try{
                FileWriter fw = new FileWriter(fileNameOut);
                BufferedWriter bfw = new BufferedWriter(fw);
     
                System.gc();
                
                tick();
                float sumOfSunlightOnTrees = sum(trees);
                System.out.println(tock());
           
                System.gc();
           
                bfw.write(String.format("%.6f",sumOfSunlightOnTrees/numTrees));
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
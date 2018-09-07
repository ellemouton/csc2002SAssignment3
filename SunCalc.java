
import java.util.concurrent.ForkJoinPool;
import java.util.*; 
import java.io.*;

public class SunCalc{
    
    static final ForkJoinPool fjPool = new ForkJoinPool();
    
    static float sum(Tree[] arr){
        return fjPool.invoke(new SunThread(arr,0,arr.length));
    }
    public static float sunMap[][] = null;
        
    public static void main(String[] args){
            String filename = "sample_input.txt";
           //String filename = "test.txt";
            String[] sunValues;
            int numTrees=1;
            Tree[] trees = null;
            
            //getting the data from tet file
            
            try{
                //access text file
                BufferedReader bf = new BufferedReader(new FileReader(filename));
                
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
                    trees[i] = new Tree(xStart,yStart,size);
                }
                
                bf.close();
                
            }
            catch(Exception e){
                System.out.println(e);
            }
            
            float sumOfSunlightOnTrees = sum(trees);
            System.out.println(sumOfSunlightOnTrees/numTrees);
            
            
    }//end main

}//end class
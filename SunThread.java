import java.util.concurrent.RecursiveTask;

public class SunThread extends RecursiveTask<Double>{
    int low;
    int high;
    Tree[] treeArray;

    static final int SEQUENTIAL_CUTOFF = 5000;
    
    SunThread(Tree[] tree, int l, int h){
        low = l;
        high = h;
        treeArray = tree;
    }
    
    protected Double compute(){
        if((high-low)<SEQUENTIAL_CUTOFF){
            System.out.println("im a new thread");

            double ans = 0;
            
            int sunMapYlimit = SunCalc.sunMap.length;
            int sunMapXlimit = SunCalc.sunMap[0].length;
                      
            
            for(int i=low;i<high;i++){
                Tree t = treeArray[i];
                int xStart = t.xCorner;
                int yStart = t.yCorner;
                int xSize = t.size;
                int ySize = t.size;
                double sun = 0;
                
                if(sunMapXlimit-(xStart+xSize)<0){
                    xSize = sunMapXlimit-xStart;
                }
                if(sunMapYlimit-(yStart+ySize)<0){
                    ySize = sunMapYlimit-yStart;
                }
                
                for(int y = yStart; y<yStart+ySize; y++){
                    for(int x = xStart; x<xStart+xSize; x++){

                        sun += SunCalc.sunMap[y][x];

                    }
                }
                t.sunLight = sun;
                ans+=sun;
                          
            }
            return ans;
        }
        else{
            SunThread left = new SunThread(treeArray, low, (high+low)/2);
            SunThread right = new SunThread(treeArray, (high+low)/2, high);
            left.fork();
            double rightAns = right.compute();
            double leftAns = left.join();
            return leftAns+rightAns; 
        }
    
    }//end compute
     
}//end class
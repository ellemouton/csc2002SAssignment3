import java.util.concurrent.RecursiveTask;

public class SunThread extends RecursiveTask<Float>{
    int low;
    int high;
    Tree[] treeArray;
    static final int SEQUENTIAL_CUTOFF = 500;
    
    int ans = 0;
    
    SunThread(Tree[] t, int l, int h){
        low = l;
        high = h;
        treeArray = t;
    }
    
    protected Float compute(){
        if((high-low)<SEQUENTIAL_CUTOFF){
            
            float ans = 0;
            
            int sunMapXlimit = SunCalc.sunMap.length;
            int sunMapYlimit = SunCalc.sunMap[0].length;
                      
            
            for(int i=low;i<high;i++){
                Tree t = treeArray[i];
                int xStart = t.xCorner;
                int yStart = t.yCorner;
                int xSize = t.size;
                int ySize = t.size;
                int sunlight = 0;
                
                if(sunMapXlimit-(xStart+xSize)<0){
                    xSize = sunMapXlimit-xStart;
                }
                if(sunMapYlimit-(yStart+ySize)<0){
                    ySize = sunMapYlimit-yStart;
                }
                
                for(int x = xStart; x<xStart+xSize; x++){
                    for(int y = yStart; y<yStart+ySize; y++){
                        ans+=SunCalc.sunMap[x][y];
                    }
                }
                //ans+=sunlight;
                //t.sunLight = sunlight;
                          
            }
            return ans;
        }
        else{
            SunThread left = new SunThread(treeArray, low, (high+low)/2);
            SunThread right = new SunThread(treeArray, (high+low)/2, high);
            
            left.fork();
            float rightAns = right.compute();
            float leftAns = left.join();
            return leftAns+rightAns; 
        }
    
    }//end compute
     
}//end class
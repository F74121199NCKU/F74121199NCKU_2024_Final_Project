
import java.util.ArrayList;

public class SelectClass {
    //9.存儲選取課程
    ArrayList<String> storeID; // 儲存ID
    ArrayList<String> storeTime; // 儲存上課時間
    ArrayList<String> storeName; //儲存課程名稱

    // 建構子
    public SelectClass() {
        storeID = new ArrayList<>();
        storeTime = new ArrayList<>();
        storeName = new ArrayList<>();
    }
    //存儲選課
    public void storeClass(String[][] classInformation, String ID){//輸入編號選課
        boolean find = false;
        for(int i = 0; i < classInformation.length; i++){
            if(classInformation[i][0] != null && classInformation[i][0].equals(ID)){
                storeID.add(ID);
                storeTime.add(classInformation[i][6]);
                storeName.add(classInformation[i][1]);
                find = true;
                break;
            }
        }
        if(!find) {
            System.out.println("The ID is wrong");
            return ;
        }
    }
    //查看時間有無重複
    public boolean checkTimeConflict(String[][] classInformation, String ID){
        int count = 0;
        for( count = 0; count < classInformation.length; count++){
            if(classInformation[count][0] != null && classInformation[count][0].equals(ID)){
                break;
            }
        }
        if(storeTime.contains(classInformation[count][6])){
            System.out.println("The time is conflict");
            return false;
        }else return true;
    }
    //查看課程有無重複
    public boolean checkClassReapeat(String[][] classInformation, String ID){
        int count = 0;
        for( count = 0; count < classInformation.length; count++){
            if(classInformation[count][0] != null && classInformation[count][0].equals(ID)){
                break;
            }
        }
        if(count == 69) {
            System.out.println("The ID is wrong");
            return false;
        }
        //ID重複
        if(storeID.contains(classInformation[count][0])){
            System.out.println("The class has been selected");
            return false;
        }
        //同型別的課程
        if(storeName.contains(classInformation[count][1])){
            System.out.println("The same type class has been selected");
            return false;
        }
        return true;
    }
    //展示已選課程
    public void showClassSelected(String[][] classInformation){
        for(int i = 0; i < classInformation.length; i++){
            if(storeID.contains(classInformation[i][0])){
                for(String e : classInformation[i]){
                    System.out.print(e+" ");
                }
                System.out.println();
            }
        }
    }

}

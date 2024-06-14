
import java.io.IOException;
import java.security.Identity;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Final_Project {
    public static final int CLASS_NUM = 68;
    public static final int INFORMATION_NUM = 8;

    public static void main(String[] args) {
        try {
            // 以外文選修為例子
            Document doc = Jsoup.connect("https://course.ncku.edu.tw/index.php?c=qry11215&i=C2FVOlE2UGxXfFZyAD0HZlRoASJUOgImATtTOgVuVmBYPlMxVzgDIlZrUTENOQYiBmMCJ105UGYEPQdiCjoDKQlrAW4Ka1FnUDBXOwRiUDpSZQE6BnpaLwY9UmUAawUjVGcDMgAkVXNVDQc4CWhSdlxhC38MaVYzBTxTJgdMA2MLIlU7UX9QP1c9VjMANgdlVGkBYVRpAjUBOlMoBSdWaFg5Uz1XIQMiVjRRcQ1cBm4GbwInXTlQdAQ9B2IKNANoCSABdAo7UTJQdFd3BGZQZVI6ASAGJlppBjdSYgB2BSFUYgM5ADBVJ1V1B2MJPlJtXH0LLgxoVnwFe1N3BzcDYws6VSJRcFB0VzxWYAA9B2RUaAF7VDoCPgEwU2gFPVZoWD9TPFdqA2tWYFE7DWoGOgYyAjVdMlA8BG4HagoyA2kJawFuCmtRZlAyVzsEflBzUm4BMQY7Wn4GYFJwAGoFY1Q5A2cAb1V6").get();
            String content = doc.text(); // 獲取內容
            content = content.substring(content.indexOf("'599'") + 5);

            SelectClass selectClass = new SelectClass();
            StringBuilder classIndex = new StringBuilder(); // 共有8個重要的資訊
            classIndex.append("系號-序號").append("科目名稱").append("限選條件").append("學分").append("教師姓名").append("已選課人數/餘額").append("時間").append("教室");
            String[] cutContent = content.substring(0, content.indexOf("系號-序號 課程碼-分班碼 屬性碼 A1-501")).replace("。", "").replace("東南亞文化學分學程", "").replaceAll("[\s]+", " ").split("Moodle");// 切割讀取到的內容
            String[][] cutLine = new String[CLASS_NUM + 1][]; // 切割content
            String[][] classInformation = new String[CLASS_NUM + 1][INFORMATION_NUM];// 68堂課，8個資訊

            for (int i = 1; i <= CLASS_NUM; i++) {
                 //把中文轉成英文
                //cutContent[i] = cutContent[i].replace("日文", "Japanese").replace("西班牙文", "Spanish").replace("法文", "Franch").replace("德文", "German").replace("韓文初級", "Junior-Korean").replace("越南語", "Vietnamese").replace("泰語", "Thai").replace("印尼語", "Indonesian");
                //cutContent[i] = cutContent[i].replace("（）","()").replace("二", "two").replace("四", "four").replace("六", "six");
                cutLine[i] = cutContent[i].split(" ");
                int count = 0;// 獲取了幾個資訊
                for (int j = 0; j < cutLine[i].length; j++) { //確保 j 的範圍在 cutLine[i] 內
                    if (j == 3 || j == 7 || j == 8 || j == 9 || j == 11 || j == 12 || j == 13 || j == 14) {
                        classInformation[i][count] = (j == 14) ? cutLine[i][j] + cutLine[i][j + 1] : cutLine[i][j];
                        count++;
                        classInformation[i][2] = "only-for-non-native-speaker";
                    }
                }

                // 測試
                /*for (String e : cutLine[i]) {
                    System.out.print(e + " ");
                }
                System.out.println("");*/
            }
            Scanner sc = new Scanner(System.in, "UTF-8");
            String choose;
            do {
                System.out.println("Which function do you want to use ? ");
                System.out.println("(0)left、(1) searchByIDRange、(2) searchByName、(3) searchByDay、(4)searchByClass、(5)displayAll、(6)checkStudentAmount");
                System.out.println("(7)selectClass、(8)checkTeacher、(9)showClassSelected、(10)checkDayAndTime、(11)checkPlaceForClass、(12)rateClass、(13)checkAverageRate");
                choose = sc.nextLine();

                switch (choose) {
                    case "0":
                        System.out.println("Left Successfully!");
                        break;
                    case "1":
                        System.out.println("Please enter the Range of ID you want to search");
                        String DayWanted1 = sc.nextLine();
                        String DayWanted2 = sc.nextLine();
                        searchByIDRange(classInformation, DayWanted1, DayWanted2);
                        break;
                    case "2":
                        System.out.println("Please enter the Name you want to search");
                        String NameWanted = sc.nextLine();//中文會有問題，需要把中文改成英文
                        searchByName(classInformation, NameWanted);
                        break;
                    case "3":
                        System.out.println("Please enter the Day you want to search");
                        String DayWanted = sc.nextLine();
                        searchByDay(classInformation, DayWanted);
                        break;
                    case "4":
                        System.out.println("Please enter the Classes you want to search");
                        String ClassWanted1 = sc.nextLine();
                        String ClassWanted2 = sc.nextLine();
                        searchByClass(classInformation, ClassWanted1,ClassWanted2);
                        break;
                    case "5":
                        displayAll(classInformation);
                        break;
                    case "6":
                        System.out.println("Pleae enter the Class you want to check for remain");
                        String IDforRemain = sc.nextLine();
                        checkStudentAmount(classInformation,IDforRemain);
                        break;
                    case "7":
                        System.out.println("Please enter the ID of the class you want to select");
                        String IDforSelect = sc.nextLine();
                        if(selectClass.checkClassReapeat(classInformation)&&selectClass.checkTimeConflict(classInformation, IDforSelect)){ //判斷有無重複課程或時間
                            selectClass.storeClass(classInformation, IDforSelect);
                        }
                        break;
                    case "8":
                        System.out.println("Please enter the ID to search teacher");
                        String IDforTeacher = sc.nextLine();
                        checkTeacher(classInformation, IDforTeacher);
                        break;
                    case "9":
                        selectClass.showClassSelected(classInformation);
                        break;
                    case "10":
                        System.out.println("Please enter the ID to search day and time"); 
                        String IDforDayAndTime = sc.nextLine();
                        checkDayAndTime(classInformation, IDforDayAndTime);
                        break;
                    case "11":
                        System.out.println("Please enter the ID to search place for class"); 
                        String IDforPlace = sc.nextLine();
                        checkPlaceForClass(classInformation, IDforPlace);
                        break;
                    case "12":
                        System.out.println("Please enter the ID to rate the class");
                        String IDforRate = sc.nextLine();
                        rateClass.rateTheClass(classInformation, IDforRate);
                        break;
                    case "13":
                        System.out.println("Please enter the ID to check the average rate");
                        String IDforAverage = sc.nextLine();
                        rateClass.checkRateAverage(classInformation, IDforAverage);
                        break;

                    default:
                        System.out.println("The function does not exist.\nPlease enter again.");
                        break;
                } 
            } while (!choose.equals("0"));
        }catch (Exception e) {
            e.printStackTrace(); // 打印異常信息
        }
    }
    // ID是編號0(僅能嚴格查詢)
    public static int searchByID(String[][] classInformation, String ID) {
        int i = 0;
        for (i = 0; i < classInformation.length; i++) {
            if (classInformation[i][0] != null && classInformation[i][0].equals(ID)) {//使用equals嚴格搜尋
                return i;
            }
        }
        System.out.println("The class does not exist");
        return -1;
    }
    //1.提供範圍搜尋
    public static void searchByIDRange(String[][] classInformation, String ID1,String ID2){
        int firstID = searchByID(classInformation,ID1);
        int secondID = searchByID(classInformation,ID2);
        if(firstID == -1||secondID == -1) {
            System.out.println("The range is wrong");
            return ;
        }
        for(int i = firstID;i<=secondID;i++){
            for (String e : classInformation[i]) {
                System.out.print(e + " ");
            }
            System.out.println(""); // 確保輸出後換行
        }
    }
    //2.Name是編號1(可以模糊查詢+查詢多個)
    public static void searchByName(String[][] classInformation, String Name) {
        Boolean find = false; //判斷有沒有找到
        for (int i = 0; i < classInformation.length; i++) {
            if (classInformation[i][1] != null && classInformation[i][1].contains(Name)) {//使用contains模糊搜尋
                find = true;
                for (String e : classInformation[i]) {
                    System.out.print(e + " ");
                }
                System.out.println(""); // 確保輸出後換行
            }
        }
        if(!find) System.out.println("The class does not exist");
    }
    //3.天數為編號6
    public static void searchByDay(String[][] classInformation, String Day){
        Boolean find = false; //判斷有沒有找到
        for (int i = 0; i < classInformation.length; i++) {
            if (classInformation[i][6] != null && (classInformation[i][6].contains("["+Day+"]"))) {//使用contains模糊搜尋
                find = true;
                for (String e : classInformation[i]) {
                    System.out.print(e + " ");
                }
                System.out.println(""); // 確保輸出後換行
            }
        }
        if(!find) System.out.println("The class does not exist");
    }
    //4.節數在編號6
    public static void searchByClass(String[][] classInformation, String Class1, String Class2){//名字可以改
        Boolean find = false; //判斷有沒有找到
        for (int i = 0; i < classInformation.length; i++) {
            if (classInformation[i][6] != null && (classInformation[i][6].contains(Class1+"~"+Class2))) {//使用contains模糊搜尋
                find = true;
                for (String e : classInformation[i]) {
                    System.out.print(e + " ");
                }
                System.out.println(""); // 確保輸出後換行
            }
        }
        if(!find) System.out.println("The class does not exist");
    }
    //5.展示訊息
    public static void displayAll(String[][] classInformation){
        for(int i = 1; i <= CLASS_NUM; i++){
            for (String e : classInformation[i]) {
                System.out.print(e + " ");
            }
            System.out.println("");
        }
    }
    //6.用ID查選課人數和餘額
    public static void checkStudentAmount(String[][] classInformation , String ID){
        for (int i = 1; i <= classInformation.length; i++) {
            if (classInformation[i][0] != null && classInformation[i][0].equals(ID)) {
                String [] numberParts = classInformation[i][5].split("/");
                System.out.println("People in this class:"+numberParts[0]);
                System.out.println("Remaining places:"+numberParts[1]);
                return;
            }
        }
    }
    //7.檢查還有哪些課有餘額
    public static void checkForClassRemainPlaces(String[][] classInformation){
        for (int i = 1; i <= classInformation.length; i++) {
            if (classInformation[i-1][0] != null) {
                String [] numberParts = classInformation[i-1][5].split("/");
                if (!numberParts[1].equals("0")) {
                    for (String e : classInformation[i-1]) {
                        System.out.print(e + " ");
                    }
                    System.out.println("");
                }               
            }
        }
        return;
    }
    //8.用ID查老師
    public static void checkTeacher(String[][] classInformation , String ID){
        for (int i = 1; i <= classInformation.length; i++) {
            if (classInformation[i][0] != null && classInformation[i][0].equals(ID)) {
            
                System.out.println("Teacher:"+classInformation[i][4]);
                
                return;
            }
        }
    }
    //10.用ID查星期幾、節數
    public static void checkDayAndTime(String[][] classInformation , String ID){
        int index = 0;//檢查是不是正常的ID
        for (int i = 0; i < classInformation.length; i++) {
            if (classInformation[i][0] != null && classInformation[i][0].equals(ID)) {
                index+=1;
            }
        }
        if (index<1) {
            System.out.println("The class does not exist");
            return;
        }

        for (int i = 0; i < classInformation.length; i++) {
            if (classInformation[i][0] != null && classInformation[i][0].equals(ID)) {

                String[] parts = classInformation[i][6].split("]");

                if (parts[0].contains("1")) {
                    System.out.println("The class is on Monday");
                }
                if (parts[0].contains("2")) {
                    System.out.println("The class is on Tuseday");
                }
                if (parts[0].contains("3")) {
                    System.out.println("The class is on Wednesday");
                }
                if (parts[0].contains("4")) {
                    System.out.println("The class is on Thursday");
                }
                if (parts[0].contains("5")) {
                    System.out.println("The class is on Friday");
                }
                
                System.out.println("Time:"+parts[1]);

                return;
            }
        }
    } 
    
    //11.用ID查上課地點
    public static void checkPlaceForClass(String[][] classInformation , String ID){
        int index = 0;//檢查是不是正常的ID
        for (int i = 0; i < classInformation.length; i++) {
            if (classInformation[i][0] != null && classInformation[i][0].equals(ID)) {
                index+=1;
            }
        }
        if (index<1) {
            System.out.println("The class does not exist");
            return;
        }

        for (int i = 0; i < classInformation.length; i++) {
            if (classInformation[i][0] != null && classInformation[i][0].equals(ID)) {
                String place = classInformation[i][7];
                System.out.println("Place for class:"+place);
                return;
            }
        }
    }    
    //還需要添加功能
}

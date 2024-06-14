import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class rateClass {
    //12.累積分數跟評分次數
    public static Map<String,Integer> rates = new HashMap<>();
    public static Map<String,Integer> rateCount = new HashMap<>();
    public static void rateTheClass(String[][] classInformation,String ID){
        Scanner sc = new Scanner(System.in);
        for (int i = 1; i <= classInformation.length; i++) {
            if (classInformation[i][0] != null) {
                if (classInformation[i][0] != null && classInformation[i][0].equals(ID)) {

                    System.out.println("How much do you like this class(1~5):");
                    System.out.println("Enter 0 to exit");

                    String score;
                    do{
                        score = sc.nextLine();
                        if (score.equals("0")) {
                            return;
                        }
                        if (score.matches("[1-5]")) {
                            int count = rateCount.getOrDefault(classInformation[i][0], 0);
                            rateCount.put(classInformation[i][0], count+1);//算次數
    
                            int alreadyCountRate = rates.getOrDefault(classInformation[i][0], 0);
                            rates.put(classInformation[i][0], alreadyCountRate+Integer.parseInt(score));//算分數
    
                        }else{
    
                            System.out.println("Wrong rating");
                            System.out.println("Enter 0 to exit");
                        }
                    }while(!score.equals("0"));
                    return;

                }
            }
        }
        
    }

    //13.計算平均輸出
    public static void checkRateAverage(String[][] classInformation,String ID){
        for (int i = 0; i < classInformation.length; i++) {
            if (classInformation[i][0] != null) {
                if (classInformation[i][0] != null && classInformation[i][0].equals(ID)) {
                    if (rateCount.get(ID)!=null && rates.get(ID)!=null) {
                        double average = (double)rates.get(ID)/rateCount.get(ID);
                        System.out.println("Average class rate:"+average);
                    }else{
                        System.out.println("The class has not be rated");
                    }
                }
            }
        }
    }
    
    //14.按照評分排名
}

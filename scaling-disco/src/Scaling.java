package src;

import java.io.IOException;

public class Scaling {
    public static void main(String[] args) {
        try {
            DiscoData discoData = new DiscoData("data/sales_data.csv");
            
            
            
            discoData.analyzeAndRecommend();
        } catch (IOException e) {
            System.out.println("Error reading data file: " + e.getMessage());
        }
    }
}

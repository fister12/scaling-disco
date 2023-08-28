package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscoData {
    private String dataFilePath;

    public DiscoData(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public void analyzeAndRecommend() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));
        String line;

        // Read and process the CSV data line by line
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            
            
            String dayOfWeek = fields[0];
            String monthlyDate = fields[1];
            String musicType = fields[2];
            String occasion = fields[3];
            int sales = Integer.parseInt(fields[4]);

            SalesTrendAnalysis();
            correlationAnalysis();
            if (sales > 1000) {
                System.out.println("Consider increasing investments for " + occasion);
            }

            // Example: Check for fields that might need decreased investments
            if (sales < 100) {
                System.out.println("Consider decreasing investments for " + occasion);
            }
        }

        reader.close();
    }
    private void SalesTrendAnalysis() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));

         Map<String, Integer> dayOfWeekSales = new HashMap<>();
        Map<String, Integer> dayOfWeekCount = new HashMap<>();
        String line;
        while (( line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String dayOfWeek = fields[0];
            int sales = Integer.parseInt(fields[4]);

            dayOfWeekSales.put(dayOfWeek, dayOfWeekSales.getOrDefault(dayOfWeek, 0) + sales);
            dayOfWeekCount.put(dayOfWeek, dayOfWeekCount.getOrDefault(dayOfWeek, 0) + 1);
        }

        reader.close();

        System.out.println("Average Sales by Day of the Week:");
        for (String day : dayOfWeekSales.keySet()) {
            int totalSales = dayOfWeekSales.get(day);
            int totalCount = dayOfWeekCount.get(day);
            double averageSales = (double) totalSales / totalCount;
            System.out.println(day + ": " + averageSales);
    }
}

private void correlationAnalysis() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));
    String line;

    double[] sales = new double[MAX_RECORDS];
    double[] customers = new double[MAX_RECORDS];
    int records = 0;

    // Read and process the CSV data line by line
    while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",");
        double salesValue = Double.parseDouble(fields[4]);
        double customersValue = Double.parseDouble(fields[5]);

        sales[records] = salesValue;
        customers[records] = customersValue;

        records++;
    }

    reader.close();

    // Calculate Pearson correlation coefficient
    double correlation = calculateCorrelation(sales, customers, records);

    System.out.println("Pearson Correlation Coefficient: " + correlation);
}

private double calculateCorrelation(double[] x, double[] y, int n) {
    double sumX = 0.0;
    double sumY = 0.0;
    double sumXY = 0.0;
    double sumXSquare = 0.0;
    double sumYSquare = 0.0;

    for (int i = 0; i < n; i++) {
        sumX += x[i];
        sumY += y[i];
        sumXY += x[i] * y[i];
        sumXSquare += x[i] * x[i];
        sumYSquare += y[i] * y[i];
    }

    double numerator = (n * sumXY) - (sumX * sumY);
    double denominator = Math.sqrt((n * sumXSquare - sumX * sumX) * (n * sumYSquare - sumY * sumY));

    return numerator / denominator;
}

private static final int MAX_RECORDS = 100; // Adjust as needed
}


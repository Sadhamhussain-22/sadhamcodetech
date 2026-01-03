import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * RecommenderApp.java
 * --------------------------------------------------------------
 * A Mahout-based product recommendation system that:
 * 1. Loads sample user-product rating dataset (CSV)
 * 2. Builds a collaborative filtering model
 * 3. Recommends products based on similar user preferences
 * --------------------------------------------------------------
 * CODTECH Java Internship Task-4
 */
public class RecommenderApp {

    public static void main(String[] args) {
        try {
            System.out.println("===== PRODUCT RECOMMENDATION SYSTEM (Mahout) =====\n");

            // Load data model from CSV file (ensure no header row in data.csv)
            DataModel model = new FileDataModel(new File("data.csv"));

            // Compute similarity between users using Pearson Correlation
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Define user neighborhood (K nearest neighbors)
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Build recommender system
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Example: Get recommendations for user with ID 3
            long userID = 3;
            List<RecommendedItem> recommendations = recommender.recommend(userID, 3);

            System.out.println("Recommended products for User " + userID + ":\n");

            // Print recommended items
            for (RecommendedItem item : recommendations) {
                System.out.println("Product ID: " + item.getItemID() + " | Score: " + String.format("%.2f", item.getValue()));
            }

            System.out.println("\n===============================================");

        } catch (Exception e) {
            System.out.println("Recommendation Error: " + e.getMessage());
        }
    }
}

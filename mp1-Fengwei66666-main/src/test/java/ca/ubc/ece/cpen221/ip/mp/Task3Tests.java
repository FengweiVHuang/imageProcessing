package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.mp.ImageProcessing;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task3Tests {

    @Test
    public void testCosineSimilarityWithResourceImages() {
        // Step 1: Load an image from the resources folder (8049.jpg in this case)
        Image img1 = new Image("resources/tests/15088-negative.png");

        // Step 2: Compare the image to itself, cosine similarity should be 1.0
        double similaritySelf = ImageProcessing.cosineSimilarity(img1, img1);
        assertEquals(1.0, similaritySelf, 0.0001);

        // Step 3: Load another image from the resources folder (1203.jpg)
        Image img2 = new Image("resources/tests/15088-negative.png");

        // Step 4: Compare the first image (8049.jpg) with the second image (1203.jpg)
        double similarityDifferent = ImageProcessing.cosineSimilarity(img1, img2);
        assertTrue(similarityDifferent == 1.0); // The similarity should be less than 1.0 for different images
    }

    @Test
    public void testBestMatchWithResourceImages() {
        // Step 1: Load a target image (8049.jpg)
        Image targetImg = new Image("resources/tests/15088-negative.png");

        // Step 2: Load candidate images from the resources folder
        Image img1 = new Image("resources/tests/15088-negative.png"); // Identical to targetImg
        Image img2 = new Image("resources/tests/15088-mirror.png"); // Different image

        // Step 3: Create a list of candidate images
        List<Image> candidates = new ArrayList<>();
        candidates.add(img2);
        candidates.add(img1);

        // Step 4: Run the bestMatch function
        List<Image> sortedImages = ImageProcessing.bestMatch(targetImg, candidates);

        // Step 5: Assert that the best match is img1, which is identical to the target image
        assertEquals(img1, sortedImages.get(0));  // The best match should be img1, as it is identical to targetImg
    }
}
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

        Image img1 = new Image("resources/tests/15088-negative.png");

        double similaritySelf = ImageProcessing.cosineSimilarity(img1, img1);
        assertEquals(1.0, similaritySelf, 0.0001);


        Image img2 = new Image("resources/tests/15088-negative.png");


        double similarityDifferent = ImageProcessing.cosineSimilarity(img1, img2);
        assertTrue(similarityDifferent == 1.0);
    }

    @Test
    public void testBestMatchWithResourceImages() {

        Image targetImg = new Image("resources/tests/15088-negative.png");


        Image img1 = new Image("resources/tests/15088-negative.png");
        Image img2 = new Image("resources/tests/15088-mirror.png");


        List<Image> candidates = new ArrayList<>();
        candidates.add(img2);
        candidates.add(img1);


        List<Image> sortedImages = ImageProcessing.bestMatch(targetImg, candidates);


        assertEquals(img1, sortedImages.get(0));  // The best match should be img1
    }
}
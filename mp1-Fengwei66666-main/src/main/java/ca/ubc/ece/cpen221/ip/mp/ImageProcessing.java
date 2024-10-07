package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides some simple operations involving
 * more than one image.
 */
public class ImageProcessing {

    private Image image;
    private int width;
    private int height;

    /* ===== TASK 3 ===== */
    public ImageProcessing(Image img) {
        image = img;
        width = img.width();
        height = img.height();

    }
    public static double cosineSimilarity(Image img1, Image img2) {
        int width1 = img1.width();
        int height1 = img1.height();
        int width2 = img2.width();
        int height2 = img2.height();


        if (width1 != width2 || height1 != height2) {
            throw new IllegalArgumentException("The images must have the same size!");
        }

        ImageTransformer imgProc1 = new ImageTransformer(img1);
        ImageTransformer imgProc2 = new ImageTransformer(img2);


        Image grayImg1 = imgProc1.grayscale();
        Image grayImg2 = imgProc2.grayscale();


        int N = width1 * height1;
        double[] vector1 = new double[N];
        double[] vector2 = new double[N];

        for (int row = 0; row < height1; row++) {
            for (int col = 0; col < width1; col++) {

                Color color1 = grayImg1.get(col, row);
                Color color2 = grayImg2.get(col, row);


                vector1[row * width1 + col] = getLuminance(color1);
                vector2[row * width1 + col] = getLuminance(color2);
            }
        }


        double dotProduct = 0.00;
        double magnitude1 = 0.00;
        double magnitude2 = 0.00;

        for (int i = 0; i < N; i++) {
            dotProduct += vector1[i] * vector2[i];
            magnitude1 += vector1[i] * vector1[i];
            magnitude2 += vector2[i] * vector2[i];
        }


        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);

        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0;
        }

        return dotProduct / (magnitude1 * magnitude2);
    }


    public static double getLuminance(Color color) {

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();


        return 0.2126 * red + 0.7152 * green + 0.0722 * blue;
    }



    public static List<Image> bestMatch(Image img, List<Image> matchingCandidates) {

        List<Double> similarities = new ArrayList<>();

        List<Image> sortedImages = new ArrayList<>(matchingCandidates);

        // Traverse
        for (Image candidate : matchingCandidates) {
            double similarity = cosineSimilarity(img, candidate);
            similarities.add(similarity);
        }

        // Sort
        for (int i = 0; i < sortedImages.size() - 1; i++) {
            for (int j = i + 1; j < sortedImages.size(); j++) {
                if (similarities.get(i) < similarities.get(j)) {
                    // Swap
                    Image tempImg = sortedImages.get(i);
                    sortedImages.set(i, sortedImages.get(j));
                    sortedImages.set(j, tempImg);

                    // Exchange
                    double tempSim = similarities.get(i);
                    similarities.set(i, similarities.get(j));
                    similarities.set(j, tempSim);
                }
            }
        }

        return sortedImages;
    }

}

package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;

import java.awt.*;
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
            throw new IllegalArgumentException("Images must have the same dimensions");
        }

        ImageTransformer imgProc1 = new ImageTransformer(img1);
        ImageTransformer imgProc2 = new ImageTransformer(img2);

        Image grayImg1 = imgProc1.grayscale();
        Image grayImg2 = imgProc2.grayscale();

    }

    public static List<Image> bestMatch(Image img, List<Image> matchingCandidates) {
        // TODO: Implement this method
        return null;
    }

}

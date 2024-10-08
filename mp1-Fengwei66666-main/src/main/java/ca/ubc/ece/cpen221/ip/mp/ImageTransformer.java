package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;

import java.awt.Color;
import java.util.*;
import java.awt.Point;

/**
 * This datatype (or class) provides operations for transforming an image.
 *
 * <p>The operations supported are:
 * <ul>
 *     <li>The {@code ImageTransformer} constructor generates an instance of an image that
 *     we would like to transform;</li>
 *     <li></li>
 * </ul>
 * </p>
 */

public class ImageTransformer {

    private Image image;
    private int width;
    private int height;
    private boolean[][] visited;
    private HashMap<Integer, int[]> regionData;
    /**
     * Creates an ImageTransformer with an image. The provided image is
     * <strong>never</strong> changed by any of the operations.
     *
     * @param img is not null
     */
    public ImageTransformer(Image img) {
        image = img;
        width = img.width();
        height = img.height();
        this.visited = new boolean[image.width()][image.height()];
        this.regionData = new HashMap<>();
    }

    /**
     * Obtain the grayscale version of the image.
     *
     * @return the grayscale version of the instance.
     */
    public Image grayscale() {
        Image gsImage = new Image(width, height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                Color color = image.get(col, row);
                Color gray = Image.toGray(color);
                gsImage.set(col, row, gray);
            }
        }
        return gsImage;
    }

    /**
     * Obtain a version of the image with only the red colours.
     *
     * @return a reds-only version of the instance.
     */
    public Image red() {
        Image redImage = new Image(width, height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                int originalPixel = image.getRGB(col, row);
                int alpha = (originalPixel >> 24) & 0xFF;
                int red = (originalPixel >> 16) & 0xFF;
                int desiredColor = (alpha << 24) | (red << 16) | (0 << 8) | (0);
                redImage.setRGB(col, row, desiredColor);
            }
        }
        return redImage;
    }


    /* ===== TASK 1 ===== */

    /**
     * Returns the mirror image of an instance.
     *
     * @return the mirror image of the instance.
     */

    public Image mirror() {
        int width = this.width;
        int height = this.height;


        Image mirroredImage = new Image(width, height);


        for (int row = 0; row < height; row++) {
            for (int col = 0; col <= width / 2; col++) {

                int mirroredCol = width - col - 1;


                int originalPixel = this.image.getRGB(col, row);
                int mirroredPixel = this.image.getRGB(mirroredCol, row);

                // Swap
                mirroredImage.setRGB(col, row, mirroredPixel);
                mirroredImage.setRGB(mirroredCol, row, originalPixel);
            }
        }

        return mirroredImage;
    }

    /**
     * <p>Returns the negative version of an instance.<br />
     * If the colour of a pixel is (r, g, b) then the colours of the same pixel
     * in the negative of the image are (255-r, 255-g, 255-b).</p>
     *
     * @return the negative of the instance.
     */
    public Image negative() {
        int width = this.width;
        int height = this.height;

        Image negativeImage = new Image(width, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                int originalPixel = this.image.getRGB(col, row);
                Color originalColor = new Color(originalPixel);


                int r = originalColor.getRed();
                int g = originalColor.getGreen();
                int b = originalColor.getBlue();


                int negR = 255 - r;
                int negG = 255 - g;
                int negB = 255 - b;


                Color negativeColor = new Color(negR, negG, negB);


                negativeImage.setRGB(col, row, negativeColor.getRGB());
            }
        }

        return  negativeImage;
    }

    private int posterizeChannel(int value) {
        if (value >= 0 && value <= 64) {
            return 32;
        } else if (value >= 65 && value <= 128) {
            return 96;
        } else if (value >= 129 && value <= 255) {
            return 222;
        }
        return value;
    }

    public Image posterize() {
        int width = this.width;
        int height = this.height;

        Image posterizedImage = new Image(width, height);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                int originalPixel = this.image.getRGB(col, row);
                Color originalColor = new Color(originalPixel);


                int r = posterizeChannel(originalColor.getRed());
                int g = posterizeChannel(originalColor.getGreen());
                int b = posterizeChannel(originalColor.getBlue());


                Color posterizedColor = new Color(r, g, b);


                posterizedImage.setRGB(col, row, posterizedColor.getRGB());
            }
        }
        return posterizedImage;
    }


    /* ===== TASK 2 ===== */

    public Image denoise() {
        int width = this.width;
        int height = this.height;

        Image denoisedImage = new Image(width, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                Color medianColor = getMedianNeighborColor(col, row);


                 denoisedImage.setRGB(col, row, medianColor.getRGB());
            }
        }

        return denoisedImage;
    }


    private Color getMedianNeighborColor(int col, int row) {
        List<Integer> redValues = new ArrayList<>();
        List<Integer> greenValues = new ArrayList<>();
        List<Integer> blueValues = new ArrayList<>();

        int width = this.width;
        int height = this.height;


        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborRow = row + i;
                int neighborCol = col + j;


                if (neighborRow >= 0 && neighborRow < height &&
                        neighborCol >= 0 && neighborCol < width) {


                    int rgb =  image.getRGB(neighborCol, neighborRow);
                    Color neighborColor = new Color(rgb);


                    redValues.add(neighborColor.getRed());
                    greenValues.add(neighborColor.getGreen());
                    blueValues.add(neighborColor.getBlue());
                }
            }
        }


        int medianRed = (int) getMedianValue(redValues);
        int medianGreen =(int) getMedianValue(greenValues);
        int medianBlue =(int) getMedianValue(blueValues);


        return new Color(medianRed, medianGreen, medianBlue);
    }


    private double getMedianValue(List<Integer> values) {
        Collections.sort(values);
        int middle = values.size() / 2;

        if (values.size() % 2 == 1) {
            return values.get(middle);
        } else {
            return (values.get(middle - 1) + values.get(middle)) / 2.0;
        }
    }



    public Image weather() {
        int width = this.width;
        int height = this.height;

        Image weatheredImage = new Image(width, height);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color minColor = getMinNeighborColor(col, row);
                weatheredImage.setRGB(col, row, minColor.getRGB());
            }
        }

        return weatheredImage;
    }

    private Color getMinNeighborColor(int x, int y) {
        int minR = 255, minG = 255, minB = 255;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborX = x + i;
                int neighborY = y + j;

                if (neighborX >= 0 && neighborX < this.width && neighborY >= 0 && neighborY < this.height) {
                    int neighborPixel = this.image.getRGB(neighborX, neighborY);
                    Color neighborColor = new Color(neighborPixel);

                    minR = Math.min(minR, neighborColor.getRed());
                    minG = Math.min(minG, neighborColor.getGreen());
                    minB = Math.min(minB, neighborColor.getBlue());
                }
            }
        }

        return new Color(minR, minG, minB);
    }

    public Image blockPaint(int blockSize) {
        int width = this.width;
        int height = this.height;


        Image blockPaintedImage = new Image(width, height);


        for (int row = 0; row < height; row += blockSize) {
            for (int col = 0; col < width; col += blockSize) {

                Color averageColor = getBlockAverageColor(col, row, blockSize);


                for (int i = row; i < row + blockSize && i < height; i++) {
                    for (int j = col; j < col + blockSize && j < width; j++) {
                        blockPaintedImage.setRGB(j, i, averageColor.getRGB());
                    }
                }
            }
        }

        return blockPaintedImage;
    }

    private Color getBlockAverageColor(int startX, int startY, int blockSize) {
        int totalR = 0, totalG = 0, totalB = 0;
        int count = 0;


        for (int i = startY; i < startY + blockSize && i < this.height; i++) {
            for (int j = startX; j < startX + blockSize && j < this.width; j++) {
                int pixel = this.image.getRGB(j, i);
                Color color = new Color(pixel);

                totalR += color.getRed();
                totalG += color.getGreen();
                totalB += color.getBlue();
                count++;
            }
        }


        int avgR = totalR / count;
        int avgG = totalG / count;
        int avgB = totalB / count;

        return new Color(avgR, avgG, avgB);
    }


    /* ===== TASK 4 ===== */


    public Image greenScreen(Color screenColour, Image backgroundImage) {
        int width = this.image.width();
        int height = this.image.height();
        int regionId = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!visited[x][y] && getPixelColor(x, y).equals(screenColour)) {

                    int area = FindRegion(x, y, screenColour, regionId);
                    regionId++;
                }
            }
        }
        int[] largestRegion = findLargestRegion();
        if (largestRegion == null) {
            return this.image;
        }

        int minX = largestRegion[1];
        int minY = largestRegion[2];
        int maxX = largestRegion[3];
        int maxY = largestRegion[4];


        int regionWidth = maxX - minX + 1;
        int regionHeight = maxY - minY + 1;


        Image AdjustImage = new Image(regionWidth,regionHeight);


            if (backgroundImage.width() < regionWidth || backgroundImage.height() < regionHeight) {

                tileAndReplace(AdjustImage, backgroundImage, minX, minY, maxX, maxY);
            }

            if (backgroundImage.width() >= regionWidth || backgroundImage.height() >= regionHeight) {

                Cut(AdjustImage, backgroundImage,  minX,  minY,  maxX, maxY);
            }


        Image resultImage = new Image(width, height);

        replaceRegionWithAdjustImage(resultImage, AdjustImage, screenColour, minX, minY, maxX, maxY);

        return resultImage;
    }

    // Tile
    public void tileAndReplace(Image resultImage, Image backgroundImage, int minX, int minY, int maxX, int maxY) {

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {

                int bgX = (x - minX) % backgroundImage.width();
                int bgY = (y - minY) % backgroundImage.height();


                resultImage.setRGB(x, y, backgroundImage.getRGB(bgX, bgY));
            }
        }
    }

    // Cut
    public static void Cut(Image adjustImage, Image backgroundImage,int minX, int minY, int maxX, int maxY) {
        int Xrange = maxX - minX;
        int Yrange = maxY - minY;

        for (int x = 0; x < Xrange; x++) {
            for (int y = 0; y < Yrange; y++) {
                int backgroundPixel = backgroundImage.getRGB(x, y);
                    adjustImage.setRGB(minX, minY, backgroundPixel);
                    minY++;
                }
            minX++;
            }

        }


    public void replaceRegionWithAdjustImage(Image resultImage, Image adjustImage, Color screenColour, int minX, int minY, int maxX, int maxY) {

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {

                if (getPixelColor(x, y).equals(screenColour)) {

                    resultImage.setRGB(x, y, adjustImage.getRGB(x, y));
                }
                else{
                    resultImage.setRGB(x, y, this.image.getRGB(x, y));
                }
            }
        }
    }


    private Color getPixelColor(int x, int y) {

        int rgb = image.getRGB(x, y);
        return new Color(rgb);
    }


    private int FindRegion(int startX, int startY, Color screenColour, int regionId) {

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startX, startY));
        visited[startX][startY] = true;


        int minX = startX, maxX = startX;
        int minY = startY, maxY = startY;
        int area = 0;


        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};


        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int x = p.x;
            int y = p.y;


            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
            area++;


            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];


                if (newX >= 0 && newX < image.width() && newY >= 0 && newY < image.height() &&
                        !visited[newX][newY] && getPixelColor(newX, newY).equals(screenColour)) {
                    queue.add(new Point(newX, newY));
                    visited[newX][newY] = true;
                }
            }
        }


        regionData.put(regionId, new int[]{area, minX, minY, maxX, maxY});
        return area;
    }



    private int[] findLargestRegion() {
        int maxArea = 0;
        int[] maxRegion = null;


        for (Map.Entry<Integer, int[]> entry : regionData.entrySet()) {
            int area = entry.getValue()[0];
            if (area > maxArea) {
                maxArea = area;
                maxRegion = entry.getValue();
            }
        }

        return maxRegion;
    }



    /* ===== TASK 5 ===== */

    public Image alignTextImage() {
        // TODO: Implement this method
        return null;
    }

    // determines the angle by which the image should
// be rotated to get the text aligned
    public double getTextAlignmentAngle() {
// TODO: Implement this method
        return 0; // TODO: Change this
    }

}

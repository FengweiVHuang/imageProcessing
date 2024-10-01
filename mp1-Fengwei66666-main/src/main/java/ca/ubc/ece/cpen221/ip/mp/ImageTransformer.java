package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.core.ImageProcessingException;
import ca.ubc.ece.cpen221.ip.core.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

        // Create a new image to store the mirrored result
        Image mirroredImage = new Image(width, height);


        for (int row = 0; row < height; row++) {
            for (int col = 0; col <= width / 2; col++) {
                // The mirrored symmetrical position
                int mirroredCol = width - col - 1;

                // Get the color value of the current pixel and the symmetrical pixel
                int originalPixel = this.image.getRGB(col, row);
                int mirroredPixel = this.image.getRGB(mirroredCol, row);

                // Swap the colors of the left and right pixels
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
                // Get the current pixel color
                int originalPixel = this.image.getRGB(col, row);
                Color originalColor = new Color(originalPixel);

                // Extract the RGB values
                int r = originalColor.getRed();
                int g = originalColor.getGreen();
                int b = originalColor.getBlue();

                // Calculate the negative RGB values
                int negR = 255 - r;
                int negG = 255 - g;
                int negB = 255 - b;

                // Create a new Color object with the negative RGB values
                Color negativeColor = new Color(negR, negG, negB);

                // Set the new pixel color in the negative image
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
                // Get the current pixel color
                int originalPixel = this.image.getRGB(col, row);
                Color originalColor = new Color(originalPixel);

                // Posterize the red, green, and blue components independently
                int r = posterizeChannel(originalColor.getRed());
                int g = posterizeChannel(originalColor.getGreen());
                int b = posterizeChannel(originalColor.getBlue());

                // Create a new Color object with the posterized RGB values
                Color posterizedColor = new Color(r, g, b);

                // Set the new pixel color in the posterized image
                posterizedImage.setRGB(col, row, posterizedColor.getRGB());
            }
        }
        return posterizedImage;
    }


    /* ===== TASK 2 ===== */

    public Image denoise() {
        int width = this.width;   // 使用类的成员变量
        int height = this.height; // 使用类的成员变量


        Image denoisedImage = new Image(width, height);


        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // Get the median value of this pixel
                Color medianColor = getMedianNeighborColor(col, row);

                // Set it with median value
                denoisedImage.setRGB(col, row, medianColor.getRGB());
            }
        }

        return denoisedImage;
    }

    private Color getMedianNeighborColor(int x, int y) {
        List<Integer> redValues = new ArrayList<>();
        List<Integer> greenValues = new ArrayList<>();
        List<Integer> blueValues = new ArrayList<>();

        // Traverse the pixel's neighbors in a 3x3 grid
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                int neighborX = x + i;
                int neighborY = y + j;


                if (neighborX >= 0 && neighborX < this.width && neighborY >= 0 && neighborY < this.height) {
                    // 获取邻居像素的颜色
                    int neighborPixel = this.image.getRGB(neighborX, neighborY);
                    Color neighborColor = new Color(neighborPixel);

                    // 将邻居像素的RGB通道值加入到列表中
                    redValues.add(neighborColor.getRed());
                    greenValues.add(neighborColor.getGreen());
                    blueValues.add(neighborColor.getBlue());
                }
            }
        }

        // Calculate the middle value of color
        int medianRed = getMedianValue(redValues);
        int medianGreen = getMedianValue(greenValues);
        int medianBlue = getMedianValue(blueValues);


        return new Color(medianRed, medianGreen, medianBlue);
    }

    private int getMedianValue(List<Integer> values) {
        int size = values.size();


        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (values.get(j) < values.get(minIndex)) {
                    minIndex = j;
                }
            }

            int temp = values.get(i);
            values.set(i, values.get(minIndex));
            values.set(minIndex, temp);
        }


        if (size % 2 == 1) {

            return values.get(size / 2);
        }

        else {

            return (values.get(size / 2 - 1) + values.get(size / 2)) / 2;
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

        // Create a new image to store the block painting effect
        Image blockPaintedImage = new Image(width, height);

        // Traverse the image, dividing it into blocks of blockSize
        for (int row = 0; row < height; row += blockSize) {
            for (int col = 0; col < width; col += blockSize) {
                // Get the average color of the current block
                Color averageColor = getBlockAverageColor(col, row, blockSize);

                // Set all pixels in the current block to the average color
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

        // Calculate the average value of the pixels in the block
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

        // Compute the average value for each color channel
        int avgR = totalR / count;
        int avgG = totalG / count;
        int avgB = totalB / count;

        return new Color(avgR, avgG, avgB);
    }


    /* ===== TASK 4 ===== */


    public Image greenScreen(Color screenColour, Image backgroundImage) {
        int width = this.image.width();
        int height = this.image.height();

        // 用来标记已经访问过的像素
        boolean[][] visited = new boolean[width][height];

        // 变量记录边界矩形
        int minX = width, minY = height, maxX = 0, maxY = 0;

        // BFS 队列
        Queue<int[]> queue = new LinkedList<>();

        // 遍历图片，查找与 screenColour 匹配的像素，并找出最大连通区域
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 如果当前像素未访问且颜色匹配
                if (!visited[x][y] && this.image.get(x, y).equals(screenColour)) {
                    // 初始化 BFS
                    queue.offer(new int[]{x, y});
                    visited[x][y] = true;

                    // 局部连通区域，寻找其边界
                    while (!queue.isEmpty()) {
                        int[] current = queue.poll();
                        int currX = current[0];
                        int currY = current[1];

                        // 更新边界矩形
                        minX = Math.min(minX, currX);
                        minY = Math.min(minY, currY);
                        maxX = Math.max(maxX, currX);
                        maxY = Math.max(maxY, currY);

                        // 四个方向的相邻像素
                        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

                        // 检查四个方向的相邻像素
                        for (int[] direction : directions) {
                            int newX = currX + direction[0];
                            int newY = currY + direction[1];

                            // 检查是否在图像范围内且未访问
                            if (isInBounds(newX, newY, width, height) && !visited[newX][newY]) {
                                // 如果颜色匹配，加入 BFS 队列
                                if (this.image.get(newX, newY).equals(screenColour)) {
                                    queue.offer(new int[]{newX, newY});
                                    visited[newX][newY] = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        // 定义边界矩形宽度和高度
        int rectWidth = maxX - minX + 1;
        int rectHeight = maxY - minY + 1;

        // 创建新图像，用背景图片替换指定区域的颜色
        Image resultImage = new Image(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 获取原始像素
                Color originalPixel = this.image.get(x, y);

                // 如果像素位于边界矩形内并且颜色匹配，则用背景图片替换
                if (x >= minX && x <= maxX && y >= minY && y <= maxY && originalPixel.equals(screenColour)) {
                    // 背景图片中的对应像素
                    int bgX = (x - minX) % backgroundImage.width();
                    int bgY = (y - minY) % backgroundImage.height();
                    Color bgPixel = backgroundImage.get(bgX, bgY);

                    // 替换为背景图片的像素
                    resultImage.set(x, y, bgPixel);
                } else {
                    // 保留原始像素
                    resultImage.set(x, y, originalPixel);
                }
            }
        }

        return resultImage;
    }

    // 辅助方法，检查坐标是否在图像范围内
    private boolean isInBounds(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /* ===== TASK 5 ===== */

    public Image alignTextImage() {
        // TODO: Implement this method
        return null;
    }
}

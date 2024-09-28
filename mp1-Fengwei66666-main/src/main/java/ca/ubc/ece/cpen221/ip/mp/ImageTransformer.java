package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.core.ImageProcessingException;
import ca.ubc.ece.cpen221.ip.core.Rectangle;

import java.awt.Color;

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
        // TODO: Implement this method
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
    // Test a github
    public Image mirror() {
        int width = this.width; // 直接使用类的成员变量
        int height = this.height; // 直接使用类的成员变量

        // 创建一个新的图像用于存储镜像效果
        Image mirroredImage = new Image(width, height); // 调用Image的构造函数

        // 遍历图像的每个像素
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width / 2; col++) {  // 只遍历左半边
                // 镜像对称位置
                int mirroredCol = width - 1 - col;

                // 获取当前像素和对称像素的颜色值
                int originalPixel = this.image.getRGB(col, row);
                int mirroredPixel = this.image.getRGB(mirroredCol, row);

                // 交换左右像素的颜色
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
        // TODO: Implement this method
        return null;
    }

    public Image posterize() {
        // TODO: Implement this method
        return null;
    }


    /* ===== TASK 2 ===== */

    public Image denoise() {
        // TODO: Implement this method
        return null;
    }

    /**
     * @return a weathered version of the image.
     */
    public Image weather() {
        // TODO: Implement this method
        return null;
    }

    public Image blockPaint(int blockSize) {
        // TODO: Implement this method
        return null;
    }


    /* ===== TASK 4 ===== */


    public Image greenScreen(Color screenColour, Image backgroundImage) {
        // TODO: Implement this method
        return null;
    }

    /* ===== TASK 5 ===== */

    public Image alignTextImage() {
        // TODO: Implement this method
        return null;
    }
}

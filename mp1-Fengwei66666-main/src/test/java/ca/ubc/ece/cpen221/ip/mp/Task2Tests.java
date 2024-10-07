package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Tests {

    @Test
    public void test_Weathering() {
        Image originalImg = new Image("resources/95006.jpg");
        Image expectedImg = new Image("resources/tests/95006-weathered.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.weather();
        assertEquals(expectedImg, outputImage);

    }

    // a simple test for denoise
    @Test
    public void test_Denoise_Simple() {
        // Step 1: Create a 3x3 image with some noise
        Image originalImg = new Image(3, 3);

        // Set the pixel colors manually
        // Image layout:
        // [ (255, 255, 255), (255, 255, 255), (255, 255, 255) ]
        // [ (255, 255, 255), (100, 100, 100), (255, 255, 255) ]
        // [ (255, 255, 255), (255, 255, 255), (255, 255, 255) ]
        Color white = new Color(255, 255, 255);
        Color noisyPixel = new Color(100, 100, 100);

        originalImg.set(0, 0, white);
        originalImg.set(0, 1, white);
        originalImg.set(0, 2, white);
        originalImg.set(1, 0, white);
        originalImg.set(1, 1, noisyPixel);  // Noisy pixel
        originalImg.set(1, 2, white);
        originalImg.set(2, 0, white);
        originalImg.set(2, 1, white);
        originalImg.set(2, 2, white);

        // Step 2: Expected output after denoising (the noisy pixel will be replaced with white)
        Image expectedImg = new Image(3, 3);

        // The expected result should be all white after denoising
        expectedImg.set(0, 0, white);
        expectedImg.set(0, 1, white);
        expectedImg.set(0, 2, white);
        expectedImg.set(1, 0, white);
        expectedImg.set(1, 1, white);  // The noisy pixel should be denoised to white
        expectedImg.set(1, 2, white);
        expectedImg.set(2, 0, white);
        expectedImg.set(2, 1, white);
        expectedImg.set(2, 2, white);

        // Step 3: Run the denoise method on the original image
        ImageTransformer transformer = new ImageTransformer(originalImg);
        Image outputImage = transformer.denoise();

        // Step 4: Assert that the output image matches the expected image
        assertEquals(expectedImg, outputImage);
    }

    // a more difficult test for denoise
    @Test
    public void test_Denoise_Complicate() {
        // Step 1: Create a 5x5 image with some noise
        Image originalImg = new Image(5, 5);

        // Set the pixel colors manually
        // Image layout:
        // [ (100, 100, 100), (200, 200, 200), (200, 200, 200), (200, 200, 200), (100, 100, 100) ]
        // [ (100, 100, 100), (100, 100, 100), (100, 100, 100), (100, 100, 100), (100, 100, 100) ]
        // [ (100, 100, 100), (100, 100, 100), (50, 50, 50)   , (100, 100, 100), (100, 100, 100) ]
        // [ (100, 100, 100), (100, 100, 100), (100, 100, 100), (100, 100, 100), (100, 100, 100) ]
        // [ (100, 100, 100), (200, 200, 200), (200, 200, 200), (200, 200, 200), (100, 100, 100) ]

        Color color100 = new Color(100, 100, 100);
        Color color200 = new Color(200, 200, 200);
        Color noisyPixel = new Color(50, 50, 50);

        // Set rows 0 and 4
        originalImg.set(0, 0, color100);
        originalImg.set(0, 1, color200);
        originalImg.set(0, 2, color200);
        originalImg.set(0, 3, color200);
        originalImg.set(0, 4, color100);
        originalImg.set(4, 0, color100);
        originalImg.set(4, 1, color200);
        originalImg.set(4, 2, color200);
        originalImg.set(4, 3, color200);
        originalImg.set(4, 4, color100);

        // Set rows 1 and 3
        for (int i = 0; i < 5; i++) {
            originalImg.set(1, i, color100);
            originalImg.set(3, i, color100);
        }

        // Set row 2 with a noisy pixel in the center
        originalImg.set(2, 0, color100);
        originalImg.set(2, 1, color100);
        originalImg.set(2, 2, noisyPixel);  // Noisy pixel
        originalImg.set(2, 3, color100);
        originalImg.set(2, 4, color100);

        // Step 2: Expected output after denoising
        Image expectedImg = new Image(5, 5);

        // The noisy pixel (2,2) should be denoised to (100, 100, 100)
        expectedImg.set(0, 0, color100);
        expectedImg.set(0, 1, color200);
        expectedImg.set(0, 2, color200);
        expectedImg.set(0, 3, color200);
        expectedImg.set(0, 4, color100);
        expectedImg.set(4, 0, color100);
        expectedImg.set(4, 1, color200);
        expectedImg.set(4, 2, color200);
        expectedImg.set(4, 3, color200);
        expectedImg.set(4, 4, color100);

        for (int i = 0; i < 5; i++) {
            expectedImg.set(1, i, color100);
            expectedImg.set(3, i, color100);
        }

        expectedImg.set(2, 0, color100);
        expectedImg.set(2, 1, color100);
        expectedImg.set(2, 2, color100);  // Noisy pixel corrected to (100, 100, 100)
        expectedImg.set(2, 3, color100);
        expectedImg.set(2, 4, color100);

        // Step 3: Run the denoise method on the original image
        ImageTransformer transformer = new ImageTransformer(originalImg);
        Image outputImage = transformer.denoise();

        // Step 4: Assert that the output image matches the expected image
        assertEquals(expectedImg, outputImage);
    }

    //siple test for block paint
    @Test
    public void test_BlockPaint_SimpleImage() {
        // Step 1: Create a 3x3 image with two alternating colors
        Image originalImg = new Image(3, 3);

        // Color c1 = (255, 191, 64, 191) and Color c2 = (255, 64, 191, 64)
        Color c1 = new Color(191, 64, 191);  // Without alpha for simplicity
        Color c2 = new Color(64, 191, 64);   // Without alpha for simplicity

        // (i + j) even -> c1, (i + j) odd -> c2
        originalImg.set(0, 0, c1); // Even
        originalImg.set(0, 1, c2); // Odd
        originalImg.set(0, 2, c1); // Even

        originalImg.set(1, 0, c2); // Odd
        originalImg.set(1, 1, c1); // Even
        originalImg.set(1, 2, c2); // Odd

        originalImg.set(2, 0, c1); // Even
        originalImg.set(2, 1, c2); // Odd
        originalImg.set(2, 2, c1); // Even

        // Step 2: Run blockPaint() with block size of 3
        ImageTransformer transformer = new ImageTransformer(originalImg);
        Image outputImage = transformer.blockPaint(3); // One 3x3 block covering the whole image

        // Step 3: Expected output - all pixels should be the average of c1 and c2
        Color expectedColor = new Color(134, 120, 134); // Averaged color (for simplicity, without alpha)

        Image expectedImg = new Image(3, 3);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                expectedImg.set(col, row, expectedColor);
            }
        }

        // Step 4: Assert that the output image matches the expected image
        assertEquals(expectedImg, outputImage);
    }
    @Test
    public void testDenoising() {

        Image img = new Image(3, 3); // create a 3*3 image

        // define RGB
        img.setRGB(0, 0, (55 << 16) | (80 << 8) | 20);
        img.setRGB(1, 0, (190 << 16) | (170 << 8) | 30);
        img.setRGB(2, 0, (100 << 16) | (110 << 8) | 100);
        img.setRGB(0, 1, (255 << 16) | (255 << 8) | 0);
        img.setRGB(1, 1, (255 << 16) | (255 << 8) | 255);
        img.setRGB(2, 1, (0 << 16) | (255 << 8) | 0);
        img.setRGB(0, 2, (200 << 16) | (100 << 8) | 0);
        img.setRGB(1, 2, (50 << 16) | (20 << 8) | 150);
        img.setRGB(2, 2, (0 << 16) | (0 << 8) | 0);

        ImageTransformer transformer = new ImageTransformer(img);
        Image denoisedImg = transformer.denoise();

        Image expectedImg = new Image(3, 3);
        // expected RGB after de noising
        expectedImg.setRGB(0, 0, (222 << 16) | (212 << 8) | 25);
        expectedImg.setRGB(1, 0, (145 << 16) | (212 << 8) | 25);
        expectedImg.setRGB(2, 0, (145 << 16) | (212 << 8) | 65);
        expectedImg.setRGB(0, 1, (195 << 16) | (135 << 8) | 25);
        expectedImg.setRGB(1, 1, (100 << 16) | (110 << 8) | 20);
        expectedImg.setRGB(2, 1, (75 << 16) | (140 << 8) | 65);
        expectedImg.setRGB(0, 2, (227 << 16) | (177 << 8) | 75);
        expectedImg.setRGB(1, 2, (125 << 16) | (177 << 8) | 0);
        expectedImg.setRGB(2, 2, (25 << 16) | (137 << 8) | 75);

        // check if two images are same
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertEquals(expectedImg.getRGB(column, row), denoisedImg.getRGB(column, row),
                        "Mismatch at pixel (" + column + ", " + row + ")");
            }
        }
    }
    @Test
    public void testBlockPaint() {
        Image img = new Image(4, 4); //creat a 4*4 pixel image

        img.setRGB(0, 0, (100 << 16) | (200 << 8) | 100); //define RGB value for every pixels
        img.setRGB(1, 0, (50 << 16) | (0 << 8) | 0);
        img.setRGB(2, 0, (150 << 16) | (250 << 8) | 250);
        img.setRGB(3, 0, (10 << 16) | (50 << 8) | 90);

        img.setRGB(0, 1, (0 << 16) | (0 << 8) | 250);
        img.setRGB(1, 1, (0 << 16) | (0 << 8) | 0);
        img.setRGB(2, 1, (100 << 16) | (100 << 8) | 0);
        img.setRGB(3, 1, (200 << 16) | (50 << 8) | 50);

        img.setRGB(0, 2, (80 << 16) | (200 << 8) | 60);
        img.setRGB(1, 2, (200 << 16) | (0 << 8) | 60);
        img.setRGB(2, 2, (200<< 16) | (200 << 8) | 200);
        img.setRGB(3, 2, (0 << 16) | (0 << 8) | 0);

        img.setRGB(0, 3, (100 << 16) | (120 << 8) | 0);
        img.setRGB(1, 3, (200 << 16) | (180 << 8) | 40);
        img.setRGB(2, 3, (0 << 16) | (0 << 8) | 100);
        img.setRGB(3, 3, (100 << 16) | (100 << 8) | 50);

        //creat ImageTransformer and do block painting
        ImageTransformer transformer = new ImageTransformer(img);
        Image blockedImg = transformer.blockPaint(2); // blockSize is 2

        // creat a expected image
        Image expectedImg = new Image(4, 4);

        expectedImg.setRGB(0, 0, (255 << 24) | (37 << 16) | (50 << 8) | 87);
        expectedImg.setRGB(1, 0, (255 << 24) | (37 << 16) | (50 << 8) | 87);
        expectedImg.setRGB(0, 1, (255 << 24) | (37 << 16) | (50 << 8) | 87);
        expectedImg.setRGB(1, 1, (255 << 24) | (37 << 16) | (50 << 8) | 87);

        expectedImg.setRGB(2, 0, (255 << 24) | (115 << 16) | (112 << 8) | 97);
        expectedImg.setRGB(3, 0, (255 << 24) | (115 << 16) | (112 << 8) | 97);
        expectedImg.setRGB(2, 1, (255 << 24) | (115 << 16) | (112 << 8) | 97);
        expectedImg.setRGB(3, 1, (255 << 24) | (115 << 16) | (112 << 8) | 97);

        expectedImg.setRGB(0, 2, (255 << 24) | (145 << 16) | (125 << 8) | 40);
        expectedImg.setRGB(1, 2, (255 << 24) | (145 << 16) | (125 << 8) | 40);
        expectedImg.setRGB(0, 3, (255 << 24) | (145 << 16) | (125 << 8) | 40);
        expectedImg.setRGB(1, 3, (255 << 24) | (145 << 16) | (125 << 8) | 40);

        expectedImg.setRGB(2, 2, (255 << 24) | (75 << 16) | (75 << 8) | 87);
        expectedImg.setRGB(3, 2, (255 << 24) | (75 << 16) | (75 << 8) | 87);
        expectedImg.setRGB(2, 3, (255 << 24) | (75 << 16) | (75 << 8) | 87);
        expectedImg.setRGB(3, 3, (255 << 24) | (75 << 16) | (75 << 8) | 87);

        // compair blockedImg and expectedImg
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                assertEquals(expectedImg.getRGB(col, row), blockedImg.getRGB(col, row),
                        "Mismatch at pixel (" + row + ", " + col + ")");
            }
        }
    }

}

package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Tests {

    @Test
    public void test_Mirroring() {
        Image originalImg = new Image("resources/15088.jpg");
        Image expectedImg = new Image("resources/tests/15088-mirror.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.mirror();
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_Negative() {
        Image originalImg = new Image("resources/15088.jpg");
        Image expectedImg = new Image("resources/tests/15088-negative.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.negative();
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_Posterize() {
        Image originalImg = new Image("resources/15088.jpg");
        Image expectedImg = new Image("resources/tests/15088-poster.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.posterize();
        assertEquals(expectedImg, outputImage);
    }

}

package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.mp.ImageTransformer;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import java.io.File;

public class Task4Tests {

    @Test
    public void testGreenScreen() {
        // Step 1: Load the image with a green screen and the background image
        Image greenScreenImage = new Image("resources/tests/Flag_of_Japan.svg.png");  // Assume this image has a green screen
        Image backgroundImage = new Image("resources/tests/15088-mirror.png");   // This will be placed over the green screen

        // Step 2: Set the green color (the green screen color in the image)
        Color greenColor = new Color(255, 255, 255);  // Pure green (can adjust to the green screen color in the image)

        // Step 3: Create an ImageTransformer object
        ImageTransformer transformer = new ImageTransformer(greenScreenImage);

        // Step 4: Apply the green screen method


        // Step 5: Save the output image to a file


        // You can also assert some conditions here if you want to verify the result programmatically.
    }
}
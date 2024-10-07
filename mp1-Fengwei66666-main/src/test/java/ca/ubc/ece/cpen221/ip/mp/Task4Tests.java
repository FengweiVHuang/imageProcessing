package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.mp.ImageTransformer;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import java.io.File;

public class Task4Tests {

    @Test
    public void testGreenScree() {
        // Step 1: Load the image with a green screen and the background image
        Image greenScreenImage = new Image("resources/tests/Flag_of_Japan.svg.png");  // Image with a green screen
        Image backgroundImage = new Image("resources/tests/15088-mirror.png");   // Background image for replacement

        // Step 2: Set the green screen color
        Color greenColor = new Color(188, 0, 45);  // Adjust to match the green screen color in the image

        // Step 3: Create an ImageTransformer object
        ImageTransformer transformer = new ImageTransformer(greenScreenImage);

        // Step 4: Apply the green screen transformation
        Image resultImage = transformer.greenScreen(greenColor, backgroundImage);

        // Step 5: Save the output image to a file
        String outputPath = "resources/tests/outputResult.png";
        resultImage.save(outputPath);  // Save the output image
        System.out.println("Result image saved at: " + outputPath);

        // Step 6: Optionally, display the result image in a window (if your environment supports it)
        // Note: You can use libraries like Swing to display the image in a GUI
        // JFrame frame = new JFrame();
        // frame.getContentPane().add(new JLabel(new ImageIcon(resultImage)));
        // frame.pack();
        // frame.setVisible(true);
    }
}
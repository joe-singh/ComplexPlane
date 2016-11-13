package mandy;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

/** Display for Mandelbrot Set. Saves a copy of the generated
 *  set as a .png file.
 *  @author Jyotirmai Singh */
public class Main extends Application {

    /** The size of the window. */
    static final double SIZE = 1500.0;
    /** The size of the positive x axis. The plane
     *  covered by the picture will be
     *  [AXIS_SIZE] x [AXIS_SIZE]. */
    static final double AXIS_SIZE = 4.0;
    /** Governs the color range of the plot. This color
     *  will approximate the areas outside the mandelbrot set. */
    static final int color = 0x00FFFF;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Canvas canvas = new Canvas(SIZE, SIZE);
        GraphicsContext g = canvas.getGraphicsContext2D();

        for (double i = -2*SIZE/AXIS_SIZE; i <= 2*SIZE/AXIS_SIZE; i++) {
            for (double j = -2*SIZE/AXIS_SIZE; j <= 2*SIZE/AXIS_SIZE; j++) {
                double re = (i / (SIZE/AXIS_SIZE));
                double im = (j / (SIZE/AXIS_SIZE));
                Complex z = new Complex(re, im);
                int num = Complex.mandelbrot(z);
                System.out.println(z);
              //  System.out.println(num);
                String hex = Integer.toHexString(color + num);
                g.setFill(Color.web(hex));
                double x = SIZE/(2*AXIS_SIZE) * re + SIZE/AXIS_SIZE;
                double y = SIZE/(2*AXIS_SIZE) * im + SIZE/AXIS_SIZE;

                g.fillOval(x, y, 1, 1);

            }
        }

        g.setFill(Color.BLACK);
        g.strokeLine(SIZE/AXIS_SIZE, 0, SIZE/AXIS_SIZE, 2*SIZE/AXIS_SIZE);
        g.strokeLine(0, SIZE/AXIS_SIZE, 2*SIZE/AXIS_SIZE, SIZE/AXIS_SIZE);
        WritableImage wim = new WritableImage((int) Math.ceil(2*SIZE/AXIS_SIZE), (int) Math.ceil(2*SIZE/AXIS_SIZE));
        canvas.snapshot(null, wim);
        root.getChildren().add(canvas);
        primaryStage.setTitle("Mandelbrot");
        Scene scene = new Scene(root, 2*SIZE/AXIS_SIZE, 2*SIZE/AXIS_SIZE);
        primaryStage.setScene(scene);
        primaryStage.show();

        File file = new File("Mandelbrot_AxisSize" + AXIS_SIZE + "Color" + color + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
        } catch (Exception s) {
            System.out.println("Fail");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

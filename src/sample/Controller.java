/* John Barnett
paint app
 */

package sample;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Stack;

public class Controller {
    //Fields
    private Timeline animation;
    private GraphicsContext gc;
    private Stack<Color> undo =new Stack<Color>();
    private Stack<Color> redo = new Stack<Color>();
    private Color currentC = new Color(0,0,0,0);
    // used an SVG to create my logo.
    private String svg = new String("M 329.00,14.00 C 329.00,14.00 329.00,138.00 329.00,138.00 "+
            " 329.00,166.80 330.76,195.79 323.87,224.00 "+
            " 317.97,248.15 306.22,274.63 280.00,281.84 "+
            " 276.37,282.84 270.71,284.06 267.00,283.90 "+
            " 262.46,283.70 254.27,281.90 250.00,280.25 "+
            " 244.62,278.18 242.15,276.46 237.88,272.69 "+
            " 212.14,249.98 223.26,212.83 243.93,191.04 "+
            " 248.95,185.74 254.19,179.91 261.00,177.00 "+
            " 261.00,177.00 248.90,192.00 248.90,192.00 "+
            " 241.08,202.48 233.15,218.73 233.00,232.00 "+
            " 232.80,249.50 239.68,263.44 256.00,271.22 "+
            " 260.45,273.33 266.03,275.14 271.00,274.90 "+
            " 312.78,248.34 319.74,218.00 320.00,196.00 "+
            " 320.00,196.00 320.00,21.00 320.00,21.00 "+
            " C 332.80,221.20 334.95,206.18 335.00,195.00 "+
            " 335.00,195.00 336.00,172.00 336.00,172.00 "+
            " 336.00,172.00 336.00,153.00 336.00,153.00 "+
            " 336.00,153.00 335.00,137.00 335.00,137.00 "+
            " 335.00,137.00 335.00,69.00 335.00,69.00 "+
            " 335.00,69.00 335.00,21.00 335.00,21.00 "+
            " 335.14,13.51 337.26,14.01 344.00,14.00 "+
            " 344.00,14.00 381.00,14.00 381.00,14.00 "+
            " 397.67,14.00 411.72,13.51 426.00,23.93 "+
            " 433.79,29.61 438.54,36.04 441.94,45.00 "+
            " 449.37,64.60 443.02,82.08 428.00,95.91 "+
            " 423.57,99.99 417.76,104.17 412.00,106.00 "+
            " 419.11,96.07 424.25,95.30 431.39,81.00 "+
            " 445.04,53.70 423.95,21.05 394.00,21.00 "+
            " 394.00,21.00 363.00,21.00 363.00,21.00 "+
            "357.08,21.00 350.71,21.62 345.00,20.00 "+
            "343.43,26.46 344.00,33.38 344.00,40.00 "+
            "342.82,184.88 340.28,210.73 336.37,226.00 "+
            "416.51,38.63 414.16,38.09 410.00,30.00 "+
            "415.68,32.43 419.41,35.97 422.96,41.00 Z "+
    "M 420.00,39.00 "+
    "C 420.00,39.00 421.00,40.00 421.00,40.00 "+
            "421.00,40.00 421.00,39.00 421.00,39.00 "+
            "421.00,39.00 420.00,39.00 420.00,39.00 Z "+
    "M 423.00,120.01 "+
    "C 451.63,131.92 459.91,158.30 449.19,186.00 "+
            "446.73,192.37 444.08,198.28 439.00,203.00 "+
            "440.78,193.13 444.86,187.67 445.00,176.00 "+
            "445.20,158.54 443.72,147.41 430.96,134.00 "+
            "420.50,123.01 418.11,123.66 409.00,116.00 "+
            "414.52,116.50 417.94,117.91 423.00,120.01 Z "+
    "M 430.19,144.00 "+
    "C 447.33,171.81 436.08,213.61 405.00,226.18 "+
            "400.22,228.11 393.15,229.92 388.00,229.92 "+
            "382.62,229.92 374.02,227.81 369.00,225.79 "+
            "362.56,223.21 355.65,219.18 351.00,214.00 "+
            "361.35,216.95 373.28,226.56 393.00,221.76 "+
            "421.66,214.78 433.46,177.92 425.79,152.00 "+
            "421.85,138.66 417.35,135.40 410.00,125.00 "+
            "418.73,128.41 425.37,136.19 430.19,144.00 Z "+
    "M 203.04,236.00 "+
    "C 203.26,230.49 205.60,221.16 207.61,216.00 "+
            "216.09,194.32 242.29,170.96 264.00,163.00 "+
            "260.00,167.83 254.71,171.20 250.00,175.29 "+
            "250.00,175.29 235.17,190.00 235.17,190.00 "+
            "222.66,204.01 211.50,224.60 214.30,244.00 "+
            "215.27,250.75 218.95,259.10 222.34,265.00 "+
            "228.88,276.40 235.29,278.51 242.00,286.00 "+
            "219.37,280.70 202.12,259.49 203.04,236.00 Z "+
    "M 421.00,213.00 "+
    "C 421.00,213.00 422.00,214.00 422.00,214.00 "+
            "422.00,214.00 422.00,213.00 422.00,213.00 "+
            "422.00,213.00 421.00,213.00 421.00,213.00 Z ");
    // Fields connected to fxml
    @FXML
    private BorderPane bPane;
    @FXML
    private RadioButton eraseRadio;
    @FXML
    private RadioButton drawRadio;
    @FXML
    private RadioButton fillRadio;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private MenuItem redoItem;
    @FXML
    private MenuItem undoItem;
    @FXML
    private Canvas canvas;
    //initialize takes whatever is inside it and executes at runtime
    public void initialize() {
        // graphics context allows you to paint to the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        // this translate centers the logo
        gc.translate(50,200);
        //append paints my logo on the canvas
        gc.appendSVGPath(svg);
        gc.fill();

        // this translate recenters the screen draw paints under the mouse
        gc.translate(-50,-200);
            /*set so when the fillRadio is selected and color chosen
               it is added to the undo stack
             */

            colorPicker.setOnAction(event -> {
                if(fillRadio.isSelected()) {
                    currentC = colorPicker.getValue();
                    undo.push(currentC);
                    gc.setFill(colorPicker.getValue());
                    gc.fill();
                }
            });
        /* undo pops color off the stack  pushs it onto the redo
         stack and changes  the logo fill with the color
          */
        undoItem.setOnAction(event -> {
            if (!undo.isEmpty()){
                currentC = undo.pop();
                redo.push(currentC);
                gc.setFill(currentC);
                gc.fill();
            }
        });
        /* redo pops the color off the stack pushes it
         onto the undo stack and changes the logo color
          */
        redoItem.setOnAction(event -> {
            if(!redo.isEmpty()){
               currentC = redo.pop();
               undo.push(currentC);
               gc.setFill(currentC);
               gc.fill();
            }
        });
    // adds the radio buttons to a group so only one can be selected at time
    final ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(drawRadio,fillRadio,eraseRadio);
     drawRadio.fire();
    // allows the user to draw using a color  or erase
    canvas.setOnMouseDragged(e->{
        double size = 15;
        //to center the drawing under the mouse
        double x = e.getX()-size/2;
        double y = e.getY()-size/2;

        if (drawRadio.isSelected()) {
            gc.setFill(colorPicker.getValue());
            gc.fillRect(x,y,size,size);

        }else if(eraseRadio.isSelected()){
            gc.clearRect(x,y,size,size);
        }
    });
        /* On a key pressed the text appears on the screen
        and spins around the logo in a circle I really like
        this one

         */
        bPane.setOnKeyPressed(event -> {
            Text text = new Text((event.getCode().toString()));
            text.setFont(Font.font(22));
            bPane.getChildren().add(text);
            // creating an instance of a circle to become the path
            Circle circlePath = new Circle(200);
            circlePath.setCenterX(400);
            circlePath.setCenterY(400);
            //the path transition which animates the key presses very fun discovery
            PathTransition pt = new PathTransition(Duration.seconds(3), circlePath, text);
            pt.setCycleCount(Animation.INDEFINITE);
            pt.play();
        });

    }




    // making the exit button usable
    public void exit(){
        Platform.exit();
    }
    // Saves an image in the source file of your art work
    public void save(){
        try{
            Image savedPic = canvas.snapshot(null,null);

            ImageIO.write(SwingFXUtils.fromFXImage(savedPic,null),"png",new File("paint.png"));
    } catch (Exception e){
           System.out.println("Failed to save image: Exception "+e);
        }
    }







}
package com.margus.edgeDetection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;

import com.margus.edgeDetection.imageProcessor.ImageProcessor;
import com.margus.edgeDetection.utilities.Point;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EdgeDetectionMain extends Application {
	
	public static final int IMAGE_WIDTH = 512;
	public static final int IMAGE_HEIGHT = IMAGE_WIDTH;

	public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Edge detection");
        
        Image image = getImage("resources/images/box2.png");
        
        ImageProcessor imageProcessor = new ImageProcessor(image);
        
        long start = System.nanoTime();
        imageProcessor.processImage();
        imageProcessor.reduceNoise();
        System.out.println("Completed in (ms): " + (System.nanoTime() - start) / 1_000_000);
        
        Set<Point> differences = imageProcessor.getDifferences();
        
        WritableImage writableImage = new WritableImage(IMAGE_WIDTH, IMAGE_HEIGHT);
        
        ImageView writableImageView = new ImageView(writableImage);
        ImageView imageView = new ImageView(image);
        
        drawDifferences(differences, writableImage);
        
        imageView.setFitHeight(IMAGE_HEIGHT);
        imageView.setFitWidth(IMAGE_WIDTH);
        
        StackPane root = new StackPane();
        root.getChildren().add(imageView);
        root.getChildren().add(writableImageView);
        primaryStage.setScene(new Scene(root, IMAGE_WIDTH, IMAGE_HEIGHT));
        primaryStage.show();
    }

	private void drawDifferences(Set<Point> differences, WritableImage writableImage) {
		PixelWriter pw = writableImage.getPixelWriter();
		for (Point point : differences) {
			pw.setColor(point.getX(), point.getY(), Color.DARKRED);
		}		
	}

	private Image getImage(String path) throws FileNotFoundException {
		FileInputStream input = new FileInputStream(path);
        return new Image(input);
	}

}

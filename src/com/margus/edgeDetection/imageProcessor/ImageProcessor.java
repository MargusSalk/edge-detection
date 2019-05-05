package com.margus.edgeDetection.imageProcessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.margus.edgeDetection.imageProcessor.noiseReducerStrategy.IslandAreaStrategy;
import com.margus.edgeDetection.imageProcessor.noiseReducerStrategy.NoiseReducer;
import com.margus.edgeDetection.imageProcessor.noiseReducerStrategy.NoiseReducerStrategy;
import com.margus.edgeDetection.utilities.Point;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ImageProcessor {
	
	private static final float DIFFERENCE_THRESOLD = 0.15f;
	
	private Set<Point> differences;
	private PixelReader pixelReader;
	private Image image;
	
	public ImageProcessor(Image image) {
		this.image = image;
		this.pixelReader = image.getPixelReader();
		this.differences = new HashSet<Point>();
	}
	
	public void processImage() {
		Color last = null;
		for (int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				Color current = pixelReader.getColor(x, y);
				
				if (last != null) {
					double difference = difference(current, last);
					
					if (difference > DIFFERENCE_THRESOLD) {
						Point point = new Point(x, y);
						
						differences.add(point);
					}
				}
				
				last = current;
			}
		}
		
		last = null;
		
		for (int y = 0; y < image.getWidth(); y++) {
			for(int x = 0; x < image.getHeight(); x++) {
				Color current = pixelReader.getColor(x, y);
				
				if (last != null) {
					double difference = difference(current, last);
					
					if (difference > DIFFERENCE_THRESOLD) {
						Point point = new Point(x, y);
						
						differences.add(point);
					}
				}
				
				last = current;
			}
		}
	}
	
	public void reduceNoise() {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		for (NoiseReducerStrategy strategy : noiseReducerStrategies(width, height)) {
			List<Point> pointsToReduce =  NoiseReducer.pointsToReduce(differences, strategy);
			differences.removeAll(pointsToReduce);
		}
	}
	
	private List<NoiseReducerStrategy> noiseReducerStrategies(int width, int height) {
		List<NoiseReducerStrategy> list = new ArrayList<NoiseReducerStrategy>();
		
//		list.add(new WithoutNeighbourStrategy(width, height, differences));
//		list.add(new RectangleRadiusStrategy(width, height, differences, 5, 2));
//		list.add(new RectangleRadiusStrategy(width, height, differences, 8, 3));
		list.add(new IslandAreaStrategy(width, height, differences));
//		list.add(new RectangleRadiusStrategy(width, height, differences, 8, 2));
		
		return list;
	}
	
	public Set<Point> getDifferences() {
		return this.differences;
	}
	
	private double difference(Color colorA, Color colorB) {		
		double redDiff 		= Math.abs(colorA.getRed() - colorB.getRed());
		double greenDiff 	= Math.abs(colorA.getGreen() - colorB.getGreen());
		double blueDiff		= Math.abs(colorA.getBlue() - colorB.getBlue());
		
		return redDiff + greenDiff + blueDiff;
	}
}

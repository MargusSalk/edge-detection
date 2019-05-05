package com.margus.edgeDetection.imageProcessor.noiseReducerStrategy;

import java.util.Set;

import com.margus.edgeDetection.utilities.Point;

public class RectangleRadiusStrategy extends NoiseReducerStrategyBaseImpl implements NoiseReducerStrategy {
	private int RADIUS = 2;
	private int REDUCE_POINT_COUNT_THRESHOLD = 6;

	public RectangleRadiusStrategy(int width, int height, Set<Point> differences) {
		super(width, height, differences);
	}
	
	public RectangleRadiusStrategy(int width, int height, Set<Point> differences, int threshold) {
		super(width, height, differences);
		this.REDUCE_POINT_COUNT_THRESHOLD = threshold;
	}
	
	public RectangleRadiusStrategy(int width, int height, Set<Point> differences, int threshold, int radius) {
		super(width, height, differences);
		this.REDUCE_POINT_COUNT_THRESHOLD = threshold;
		this.RADIUS = radius;
	}

	@Override
	public boolean needsReduced(Point point) {
		int x = point.getX();
		int y = point.getY();
		
		int leftBoundary = calculateNegativeBoundary(x);
		int upperBoundary = calculatePositiveBoundary(y, maxCoordinateY());
		int rightBoundary = calculatePositiveBoundary(x, maxCoordinateX());
		int bottomBoundary = calculateNegativeBoundary(y);
		
		int pointsCounter = 0;
		
		for (int xCoord = leftBoundary; xCoord <= rightBoundary; xCoord++) {
			for (int yCoord = bottomBoundary; yCoord <= upperBoundary; yCoord++) {
				if (detectedPixels[xCoord][yCoord] != null) {
					pointsCounter++;
					if (pointsCounter > REDUCE_POINT_COUNT_THRESHOLD) return false;
				}
			}
		}
		
		return pointsCounter <= REDUCE_POINT_COUNT_THRESHOLD;
	}
	
	private int calculateNegativeBoundary(int coordinate) {
		int result = coordinate - RADIUS;
		return result < 0 ? 0 : result;
	}
	
	private int calculatePositiveBoundary(int coordinate, int maxCoord) {
		int result = coordinate + RADIUS;
		return result > maxCoord ? maxCoord : result;
	}
}

package com.margus.edgeDetection.imageProcessor.noiseReducerStrategy;

import java.util.Set;

import com.margus.edgeDetection.utilities.Point;

public abstract class NoiseReducerStrategyBaseImpl implements NoiseReducerStrategy {
	
	public final int width;
	public final int height;
	protected Point[][] detectedPixels;
	
	public NoiseReducerStrategyBaseImpl(int width, int height, Set<Point> differences) {
		this.width = width;
		this.height = height;
		detectedPixels = new Point[width][height];
		initDetectedPixels(differences);
	}

	@Override
	public abstract boolean needsReduced(Point point);

	@Override
	public int maxCoordinateX() {
		return detectedPixels.length - 1;
	}

	@Override
	public int maxCoordinateY() {
		return detectedPixels[0].length - 1;
	}
	
	private void initDetectedPixels(Set<Point> differences) {
		for (Point point : differences) {
			detectedPixels[point.getX()][point.getY()] = point;
		}
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}

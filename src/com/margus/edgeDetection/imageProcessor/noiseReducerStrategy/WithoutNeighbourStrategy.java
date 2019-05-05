package com.margus.edgeDetection.imageProcessor.noiseReducerStrategy;

import java.util.Set;

import com.margus.edgeDetection.utilities.Point;

public class WithoutNeighbourStrategy extends NoiseReducerStrategyBaseImpl {
	
	private static final int NEIGHBOUR_COUNT_THRESHOLD = 2;

	public WithoutNeighbourStrategy(int width, int height, Set<Point> differences) {
		super(width, height, differences);
	}

	@Override
	public boolean needsReduced(Point point) {
		int x = point.getX();
		int y = point.getY();
		int existingNeighbourCount = 0;
		
		Point[] neighbours = getNeighbours(x, y);
		
		for (Point neighbour : neighbours) {
			if (neighbour != null) {
				existingNeighbourCount++;
			}
		}
		return existingNeighbourCount < NEIGHBOUR_COUNT_THRESHOLD;
	}

	private Point[] getNeighbours(int x, int y) {
		Point[] neighbours = new Point[4];
		
		neighbours[0] = getNeighbour(x - 1, y);
		neighbours[1] = getNeighbour(x + 1, y);
		neighbours[2] = getNeighbour(x, y - 1);
		neighbours[3] = getNeighbour(x, y + 1);
		
		return neighbours;
	}

	private Point getNeighbour(int x, int y) {
		try {
			return detectedPixels[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
}

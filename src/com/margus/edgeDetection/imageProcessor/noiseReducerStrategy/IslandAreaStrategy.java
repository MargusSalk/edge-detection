package com.margus.edgeDetection.imageProcessor.noiseReducerStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.margus.edgeDetection.utilities.Point;

public class IslandAreaStrategy extends NoiseReducerStrategyBaseImpl implements NoiseReducerStrategy {
	
	private int ISLAND_AREA_THRESHOLD = 10;
	private Set<Point> checkedForRemoval;
	private Set<Point> checkedForStay;
	
	public IslandAreaStrategy(int width, int height, Set<Point> differences) {
		super(width, height, differences);
		checkedForRemoval = new HashSet<Point>();
		checkedForStay = new HashSet<Point>();
	}

	@Override
	public boolean needsReduced(Point point) {
		if (checkedForRemoval.contains(point)) return true;
		if (checkedForStay.contains(point)) return false;
		return calculateArea(point).size() <= ISLAND_AREA_THRESHOLD;
	}

	private List<Point> calculateArea(Point point) {
		List<Point> area = trackArea(point);
		
		if (area.size() > ISLAND_AREA_THRESHOLD) {
			checkedForStay.addAll(area);
		} else {
			checkedForRemoval.addAll(area);
		}
		return area;
	}

	private List<Point> trackArea(Point point) {
		List<Point> area = new ArrayList<Point>();
		
		recursiveAreaTracker(area, point);
		
		return area;
	}

	private void recursiveAreaTracker(List<Point> area, Point point) {
		if (area.size() > ISLAND_AREA_THRESHOLD) return;
		if (area.contains(point) || point == null) return;
		
		area.add(point);
		
		for (Point neighbouringPoint : getNeighbours(point.getX(), point.getY())) {
			recursiveAreaTracker(area, neighbouringPoint);
		}
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

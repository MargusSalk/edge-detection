package com.margus.edgeDetection.imageProcessor.noiseReducerStrategy;

import com.margus.edgeDetection.utilities.Point;

public interface NoiseReducerStrategy {
	public boolean needsReduced(Point point);
	public int maxCoordinateX();
	public int maxCoordinateY();
}

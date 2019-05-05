package com.margus.edgeDetection.imageProcessor.noiseReducerStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.margus.edgeDetection.utilities.Point;

public class NoiseReducer {
	
	public static List<Point> pointsToReduce(Set<Point> differences, NoiseReducerStrategy noiseReducerStrategy) {
		List<Point> pointsToReduce = new ArrayList<Point>();
		
		for (Point point : differences) {
			if (noiseReducerStrategy.needsReduced(point)) {
				pointsToReduce.add(point);
			}
		}
		
		return pointsToReduce;
	}
}

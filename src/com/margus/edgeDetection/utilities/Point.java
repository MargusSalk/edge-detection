package com.margus.edgeDetection.utilities;

public class Point {
	
	private int x;
	private int y;
	
	public Point(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Point)) return false;
		Point other = (Point) o;
		return getX() == other.getX() && getY() == other.getY();
	}
	
	public String toString() {
		return "x: " + getX() + " y: " + getY();
	}
}

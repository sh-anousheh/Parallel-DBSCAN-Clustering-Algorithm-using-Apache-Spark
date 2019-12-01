package Parallel.DBSCAN;

import java.io.Serializable;

public class Point implements Serializable {

	private boolean isSeed;

	private double x;

	private double y;

	private int clId;

	private int partionId;

	public Point(double x, double y) {

		isSeed = false;

		this.x = x;

		this.y = y;

	}

	public boolean getIsSeed() {

		return isSeed;
	}

	public void setIsSeed(boolean input) {

		isSeed = input;
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}

	public int getPartionId() {

		return partionId;
	}

	public void setPartionId(int _partionId) {

		this.partionId = _partionId;
	}

	public int getClId() {

		return clId;
	}

	public void setClId(int _clId) {

		clId = _clId;
	}

	public double dist(Point p) {

		double dX = p.x - this.x;

		double dY = p.y - this.y;

		double res = Math.sqrt(dX * dX + dY * dY);

		return res;
	}

}

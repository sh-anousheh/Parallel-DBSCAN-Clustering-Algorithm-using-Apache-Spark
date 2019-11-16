package Parallel.DBSCAN;

import java.io.Serializable;

public class Point implements Serializable {

	private int num;

	private double x;

	private double y;

	private int clId;

	public int getNum() {

		return num;
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}

	public int getClId() {

		return clId;
	}

	public void setNum(int _num) {

		num = _num;
	}

	public void setX(double _x) {

		x = _x;
	}

	public void setY(double _y) {

		y = _y;
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

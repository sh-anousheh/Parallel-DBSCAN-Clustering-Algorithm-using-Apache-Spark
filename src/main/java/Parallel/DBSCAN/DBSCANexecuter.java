package Parallel.DBSCAN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.spark.TaskContext;

public class DBSCANexecuter implements Serializable {

	private static final long serialVersionUID = 1L;

	private double eps = 30;

	private int minpts = 5;

	public DBSCANexecuter() {

	}

	public Iterator<List<Point>> clustering(Iterator<Point> a, List<Point> cluster) {

		List<List<Point>> clusters = new ArrayList<List<Point>>();

		List<Point> points = new ArrayList<Point>();

		a.forEachRemaining(points::add);

		int clid = 1;

		for (int i = 0; i < points.size(); i++) {

			Point p = points.get(i);

			if (p.getClId() == 0) {

				if (expandCluster(points, p, clid, eps, minpts))

					clid++;
			}
		}

		int max = points.get(0).getClId();

		for (Point p : points) {

			if (max < p.getClId()) {

				max = p.getClId();
			}
		}

		for (int i = 0; i < max; i++) {

			clusters.add(new ArrayList<Point>());
		}

		for (Point p : points) {

			if (p.getClId() > 0) {

				clusters.get(p.getClId() - 1).add(p);

			}
		}

		return clusters.iterator();

	}

	private boolean expandCluster(List<Point> points, Point p, int clid, double eps, int minPts) {

		List<Point> firstNeighbors = regionQuery(points, p, eps);

		if (firstNeighbors.size() < minPts) {

			p.setClId(-1);

			return false;
		}

		else {

			for (int i = 0; i < firstNeighbors.size(); i++) {

				firstNeighbors.get(i).setClId(clid);

			}

			firstNeighbors.remove(p);

			while (firstNeighbors.size() > 0) {

				Point currentP = firstNeighbors.get(0);

				placeSeedsProcessing(currentP);

				List<Point> secondNeighbors = regionQuery(points, currentP, eps);

				if (secondNeighbors.size() >= minPts) {

					for (int i = 0; i < secondNeighbors.size(); i++) {

						Point resultP = secondNeighbors.get(i);

						if (resultP.getClId() == 0 || resultP.getClId() == -1) {

							if (resultP.getClId() == 0) {

								firstNeighbors.add(resultP);
							}

							resultP.setClId(clid);
						}
					}
				}

				firstNeighbors.remove(currentP);
			}
			return true;
		}
	}

	private List<Point> regionQuery(List<Point> points, Point p, double eps) {

		List<Point> region = new ArrayList<Point>();

		for (int i = 0; i < points.size(); i++) {

			double dist = p.dist(points.get(i));

			if (dist <= eps) {

				region.add(points.get(i));
			}

		}

		return region;
	}

	private void placeSeedsProcessing(Point p) {

		int partionId = TaskContext.getPartitionId();

		if (p.getPartionId() != partionId) {

			p.setIsSeed(true);
		}

	}

}

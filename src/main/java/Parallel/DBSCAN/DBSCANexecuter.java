package Parallel.DBSCAN;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class DBSCANexecuter {

	private double eps = 30;

	private int minpts = 5;

	public DBSCANexecuter() {

	}

	public void clustering(Iterator<Point> a) {

		List<List<Point>> clusters = new ArrayList<List<Point>>();

		List<Point> points = new ArrayList();

		a.forEachRemaining(points::add);

		int clid = 1;

		for (int i = 0; i < points.size(); i++) {

			Point p = points.get(i);

			if (p.getClId() == 0) {

				if (ExpandCluster(points, p, clid, eps, minpts))

					clid++;
			}
		}

		int max = points.get(0).getClId();

		for (Point p : points) {

			if (max < p.getClId()) {

				max = p.getClId();
			}
		}

		for (int i = 0; i <= max; i++) {

			clusters.add(new ArrayList<Point>());
		}

		for (Point p : points) {

			if (p.getClId() > 0) {

				clusters.get(p.getClId()).add(p);

			}
		}

		int sum = 0;

		for (int i = 1; i < clusters.size(); i++) {

			sum += clusters.get(i).size();

			System.out.println(String.format("Cluster  %s   number of points : %s", i, clusters.get(i).size()));
		}
		sum = 8614 - sum;

		if (sum > 0)

		{
			System.out.println(String.format("\n%s points are noise.", sum));

		} else {

			System.out.println("there is no noise!!");

		}
		System.out.println("\nenter the instance that you whant to know its cluster:(number of row)");

		Scanner reader = new Scanner(System.in);

		int num = Integer.parseInt(reader.next());

		reader.close();

		int cl = 0;

		for (Point p : points) {

			if (p.getNum() == num) {

				cl = p.getClId();

			}
		}

		if (cl != -1) {

			System.out.println(String.format("its cluster is : %s ", cl));

		} else {

			System.out.println("its noise!!");

		}

	}

	private boolean ExpandCluster(List<Point> points, Point p, int clid, double eps, int minPts) {

		List<Point> seeds = RegionQuery(points, p, eps);

		if (seeds.size() < minPts) {

			p.setClId(-1);

			return false;
		}

		else {

			for (int i = 0; i < seeds.size(); i++) {

				seeds.get(i).setClId(clid);

			}

			seeds.remove(p);

			while (seeds.size() > 0) {

				Point currentP = seeds.get(0);

				List<Point> result = RegionQuery(points, currentP, eps);

				if (result.size() >= minPts) {

					for (int i = 0; i < result.size(); i++) {

						Point resultP = result.get(i);

						if (resultP.getClId() == 0 || resultP.getClId() == -1) {

							if (resultP.getClId() == 0) {

								seeds.add(resultP);
							}

							resultP.setClId(clid);
						}
					}
				}

				seeds.remove(currentP);
			}
			return true;
		}
	}

	private List<Point> RegionQuery(List<Point> points, Point p, double eps) {

		List<Point> region = new ArrayList<Point>();

		for (int i = 0; i < points.size(); i++) {

			double dist = p.dist(points.get(i));

			if (dist <= eps) {

				region.add(points.get(i));
			}

		}

		return region;
	}

}

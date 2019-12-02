package Parallel.DBSCAN;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.TaskContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class App {

	public static void main(String[] args) {

		int partitionNum = 2;

		App app = new App();

		// SparkConf conf = new SparkConf().setAppName("App2").setMaster("local[*]");

		SparkConf conf = new SparkConf().setAppName("App2").setMaster(String.format("local[%d]", partitionNum));

		JavaSparkContext sc = new JavaSparkContext(conf);

		String fileName = "stp.txt";

		File currentDirFile = new File("");

		String root = currentDirFile.getAbsolutePath();

		String inputFile = root + "\\" + fileName;

		JavaRDD<String> lines = sc.textFile(inputFile);

		JavaRDD<Point> points = lines.map(s -> {

			int partionId = TaskContext.getPartitionId();

			String[] splited = s.split("\t");

			double x = Double.parseDouble(splited[0]);

			double y = Double.parseDouble(splited[1]);

			Point p = new Point(x, y);

			p.setPartionId(partionId);

			return p;

		});

		JavaRDD<Point> randomPoints = points.sample(false, 0.1);// ba ehtemal e 0.1 barmidare

		randomPoints = randomPoints.repartition(points.getNumPartitions());

		points = points.union(randomPoints);// Comment it to have the same result as
		// serial since there is no point from other clusters for place seeds and merge
		// after using union, my threads get doubled

		DBSCANexecuter dbscan = new DBSCANexecuter();

		List<Point> cluster = new ArrayList<Point>();

		JavaRDD<List<Point>> res = points.mapPartitions(a -> dbscan.clustering(a, cluster));

		List<List<Point>> clusters = res.collect();

		clusters = app.mergeClusters(clusters);

		app.printResults(clusters, points.count());

		System.out.println("the number of partions are: " + res.getNumPartitions());

	}

	private List<List<Point>> mergeClusters(List<List<Point>> clusters) {

		String unFinished = "unfinished";

		String finished = "finished";

		String[] status = new String[clusters.size()];

		for (int i = 0; i < status.length; i++) {

			status[i] = unFinished;
		}

		for (int i = 0; i < clusters.size() - 1; i++) {

			List<Point> seeds = new ArrayList<Point>();

			List<Integer> mergeCandidate = new ArrayList<Integer>();

			if (status[i] == unFinished) {

				for (Point p : clusters.get(i)) {

					if (p.getIsSeed()) {

						seeds.add(p);
					}
				}

				for (Point p1 : seeds) {

					for (int j = i + 1; j < clusters.size(); j++) {

						for (Point p2 : clusters.get(j)) {

							if (p2.getIsSeed() && p1.getX() == p2.getX() && p1.getY() == p2.getY()) {

								mergeCandidate.add(j);

								clusters.get(i).remove(p1);

								status[j] = finished;

							}
						}
					}
				}

				for (Integer index : mergeCandidate) {

					clusters.get(i).addAll(clusters.get(index));

					clusters.get(index).clear();
				}

				status[i] = finished;

			}

		}

		List<List<Point>> res = new ArrayList<List<Point>>();

		clusters.forEach(c -> {

			if (c.size() > 0) {

				res.add(c);
			}
		});

		return res;

	}

	private void printResults(List<List<Point>> clusters, long pointCount) {

		long sum = 0;

		for (int i = 0; i < clusters.size(); i++) {

			sum += clusters.get(i).size();

			System.out.println(String.format("Cluster  %s   number of points : %s", i + 1, clusters.get(i).size()));
		}
		sum = pointCount - sum;

		if (sum > 0)

		{
			System.out.println(String.format("\n%s points are noise.", sum));

		} else {

			System.out.println("there is no noise!!");

		}

	}

}

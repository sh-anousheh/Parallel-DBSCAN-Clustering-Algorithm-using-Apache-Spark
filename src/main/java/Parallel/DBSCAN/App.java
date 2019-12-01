package Parallel.DBSCAN;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.Partition;
import org.apache.spark.SparkConf;
import org.apache.spark.TaskContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class App {
	public static void main(String[] args) {

		// Decide on the number of threads [2]
		SparkConf conf = new SparkConf().setAppName("App2").setMaster("local[4]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		String fileName = "stp.txt";

		File currentDirFile = new File("");

		String root = currentDirFile.getAbsolutePath();

		String inputFile = root + "\\" + fileName;

		JavaRDD<String> lines = sc.textFile(inputFile);

		JavaRDD<String> randomLines = lines.sample(false, 0.1);// ba ehtemal e 0.1 barmidare

		lines.union(randomLines);

		JavaRDD<Point> points = lines.map(s -> {

			int partionId = TaskContext.getPartitionId();

			String[] splited = s.split("\t");

			double x = Double.parseDouble(splited[0]);

			double y = Double.parseDouble(splited[1]);

			Point p = new Point(x, y);

			p.setPartionId(partionId);

			return p;

		});

		List<Point> temp = points.collect();

		points.foreach(p1 -> {

			temp.forEach(p2 -> {

				if (p1.getX() == p2.getX() && p1.getY() == p2.getY()) {

					if (p1.getPartionId() != p2.getPartionId())

					{
						p1.setIsSeed(true);

						p2.setIsSeed(true);
					}
				}

			});
		});

		DBSCANexecuter dbscan = new DBSCANexecuter();

		List<Point> cluster = new ArrayList<Point>();

		JavaRDD<List<Point>> res = points.mapPartitions(a -> dbscan.clustering(a, cluster));

		List<List<Point>> clusters = res.collect();

		long sum = 0;

		for (int i = 0; i < clusters.size(); i++) {

			sum += clusters.get(i).size();

			System.out.println(String.format("Cluster  %s   number of points : %s", i + 1, clusters.get(i).size()));
		}
		sum = points.count() - sum;

		if (sum > 0)

		{
			System.out.println(String.format("\n%s points are noise.", sum));

		} else {

			System.out.println("there is no noise!!");

		}

		System.out.println("the number of partions are: " + res.getNumPartitions());

	}

}

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

		// taeen mikonam tu chandta thread bashe [2]
		SparkConf conf = new SparkConf().setAppName("App2").setMaster("local[4]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		// List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		// JavaRDD<Integer> distData = sc.parallelize(data);
		// System.out.println(distData.reduce((a, b) -> a + b));
		// ----------------------------------------------------

		String fileName = "stp.txt";

		File currentDirFile = new File("");

		String root = currentDirFile.getAbsolutePath();

		String inputFile = root + "\\" + fileName;

		JavaRDD<String> lines = sc.textFile(inputFile);

		JavaRDD<Point> points = lines.map(s -> {

			int partionId = TaskContext.getPartitionId();

			Point p = new Point();

			String[] splited = s.split("\t");

			p.setX(Double.parseDouble(splited[0]));

			p.setY(Double.parseDouble(splited[1]));
			
			p.SetPartionId(partionId);

			return p;

		});

		DBSCANexecuter dbscan = new DBSCANexecuter();

		List<Point> cluster = new ArrayList<Point>();

		JavaRDD<List<Point>> res = points.mapPartitions(a -> dbscan.clustering(a, cluster));

		List<List<Point>> clusters = res.collect();
		int sum = 0;

		for (int i = 0; i < clusters.size(); i++) {

			sum += clusters.get(i).size();

			System.out.println(String.format("Cluster  %s   number of points : %s", i + 1, clusters.get(i).size()));
		}
		sum = 8614 - sum;

		if (sum > 0)

		{
			System.out.println(String.format("\n%s points are noise.", sum));

		} else {

			System.out.println("there is no noise!!");

		}

		System.out.println("the number of partions are: " + res.getNumPartitions());

	}

}

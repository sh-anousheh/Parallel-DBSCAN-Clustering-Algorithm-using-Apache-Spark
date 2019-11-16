package Parallel.DBSCAN;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class App {
	public static void main(String[] args) {

		SparkConf conf = new SparkConf().setAppName("App2").setMaster("local");
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

		// int i = 0;

		JavaRDD<Point> points = lines.map(s -> {

			Point p = new Point();

			String[] splited = s.split("\t");

			p.setX(Double.parseDouble(splited[0]));

			p.setY(Double.parseDouble(splited[1]));

			// p.setNum(i + 1);

			return p;

		});
		for (Point p : points.collect()) {
			System.out.println(p.getX() + " " + p.getY() + " " + p.getNum());
		}

	}

}

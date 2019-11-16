package Parallel.DBSCAN;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;

public class App {
	public static void main(String[] args) {

		System.setProperty("hadoop.home.dir", "D:\\winutils");// should be added in any Spark project
		String logFile = "D:\\Spark\\README.md";
		SparkSession spark = SparkSession.builder().appName("App").config("spark.master", "local")
				.getOrCreate();// Use in any Spark project
		Dataset<String> logData = spark.read().textFile(logFile).cache();

		long numAs = logData.filter((FilterFunction<String>) (s -> s.contains("a"))).count();
		long numBs = logData.filter((FilterFunction<String>) (s -> s.contains("b"))).count();

		System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

		spark.stop();
	}
}
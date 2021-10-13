# Parallel DBSCAN Clustering Algorithm using Apache Spark

Full Description: https://sh-anousheh.github.io

Deliverable Paper:  https://sh-anousheh.github.io/Final_Paper.pdf

##  Objective
This implementation is an individual project done for the course COMP 5704: Parallel Algorithms and Applications in Data Science at Carleton University in the fall semester of 2019.

## Project Outline
DBSCAN (Density-based spatial clustering of applications with noise) is an unsupervised learning data clustering approach commonly used in data mining and machine learning. Based on a set of points, DBSCAN groups together points that are close to each other based on a distance measurement and a minimum number of points. Also, this algorithm simply finds outlier points that are in low-density regions. This algorithm is popular since it can divide data into clusters with arbitrary shapes. Moreover, DBSCAN does not require the number of clusters a priori as well as it is insensitive to the order of the points in the dataset.

However, applying DBSCAN with real-world data is challenging due to the size of datasets that have been growing exponentially. This algorithm goes through each point of the database multiple times. The time complexity of the DBSCAN is O(n2) which can be reduced to O(n log n) in some cases (n is the number of objects to be clustered). So the execution time for this algorithm highly increases when it comes to the massive dataset.

MapReduce is a desirable parallel programming platform based on shared-nothing architectures. With MapReduce, rather than sending data to where the application or logic resides, the logic is executed on the servers where the data already resides, to expedite processing.
The execution time of the DBSCAN algorithm significantly decreases using parallelization by MapReduce.

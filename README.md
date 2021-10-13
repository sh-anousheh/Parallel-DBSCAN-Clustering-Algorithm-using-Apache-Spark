# Parallel DBSCAN Clustering Algorithm using Apache Spark

Full Description: https://sh-anousheh.github.io

Deliverable Paper:  https://sh-anousheh.github.io/Final_Paper.pdf

##  Objective
This implementation is an individual project done for the course COMP 5704: Parallel Algorithms and Applications in Data Science at Carleton University

## Project Outline
DBSCAN (Density-based spatial clustering of applications with noise) is an unsupervised learning data clustering approach commonly used in data mining and machine learning. Based on a set of points, DBSCAN groups together points that are close to each other based on a distance measurement and a minimum number of points. Also, this algorithm simply finds outlier points that are in low-density regions. This algorithm is popular since it can divide data into clusters with arbitrary shapes. Moreover, DBSCAN does not require the number of clusters a priori as well as it is insensitive to the order of the points in the dataset.

However, applying DBSCAN with real-world data is challenging due to the size of datasets that have been growing exponentially. This algorithm goes through each point of the database multiple times. The time complexity of the DBSCAN is O(n2) which can be reduced to O(n log n) in some cases (n is the number of objects to be clustered). So the execution time for this algorithm highly increases when it comes to the massive dataset.

MapReduce is a desirable parallel programming platform based on shared-nothing architectures. With MapReduce, rather than sending data to where the application or logic resides, the logic is executed on the servers where the data already resides, to expedite processing.
The execution time of the DBSCAN algorithm significantly decreases using parallelization by MapReduce.

## Relevant References:

<ul>
  <p>
    <li>
       <div>
        M. Ester, H. Kriegel, J. Sander, and X. Xu. A density-based algorithm for discovering clusters in large spatial databases with noise.
        In Proceedings of 2nd International Conference on KDD, pages 226–231, 1996. [
        <a href="https://www.aaai.org/Papers/KDD/1996/KDD96-037.pdf" style="color: #07a">Download Here</a>]
      </div>
    </li>
  </p>
  
  <p>
      <li>
  <div>
        K. Shim, “MapReduce algorithms for big data analysis,” Proc. VLDB Endow., vol. 5, pp. 2016–2017, 2012. [
        <a href="https://dl.acm.org/citation.cfm?id=2367563" style="color: #07a">Download Here</a>]
      </div>
    </li>
  </p>  

 
   <p>
      <li>
   <div>
        Y. He et al. Mr-dbscan: an efficient parallel density-based clustering algorithm using mapreduce. In Parallel and Distributed 
        Systems (ICPADS), 2011 IEEE 17th International Conference on, pages 473–480. IEEE, 2011. [
        <a href="https://ieeexplore.ieee.org/abstract/document/6121313" style="color: #07a">Download Here</a>]
      </div>
    </li>
  </p>  

  
  
  <p>
      <li>
        <div>
        Bi-Ru, D., Lin, C.I.: Efficient Map/Reduce-Based DBSCAN Algorithm with Optimized Data Partition. In: Cloud
        Computing (CLOUD), 2012 IEEE 5th International Conference, pp. 59–66. Honolulu (2012). [
        <a href="https://ieeexplore.ieee.org/abstract/document/6253489" style="color: #07a">Download Here</a>]
      </div>
    </li>
  </p>  
  
  
  
   <p>
      <li>
      <div>
       Dianwei Han, Ankit Agrawal, Wei-Keng Liao, and Alok Choudhary. 2016. 
        A Novel Scalable DBSCAN Algorithm with Spark. In Proc. 2016 IEEE Int’l Sympo. 
        on Parallel and Distributed Processing. 1393–1402. [
        <a href="https://ieeexplore.ieee.org/abstract/document/7530030" style="color: #07a">Download Here</a>]
      </div>
    </li>
  </p>  
  
  
  
  <p>
      <li>
      <div>
       G. Luo, X. Luo, T. F. Gooch, L. Tian, and K. Qin, “A parallel dbscan algorithm based on spark,” in 2016 IEEE 
        International Conferences on Big Data and Cloud Computing (BDCloud), Social Computing and Networking (SocialCom),
        Sustainable Computing and Communications (SustainCom) (BDCloud-SocialComSustainCom), Oct 2016, pp. 548–553. [
        <a href="https://ieeexplore.ieee.org/abstract/document/7723739" style="color: #07a">Download Here</a>]
      </div>
    </li>
  </p> 
  
  
  
   <p>
      <li>
     <div>
       Han, Dianwei, et al. "Parallel DBSCAN Algorithm Using a Data Partitioning Strategy with Spark Implementation." 2018
        IEEE International Conference on Big Data (Big Data). IEEE, 2018. [
        <a href="https://ieeexplore.ieee.org/abstract/document/8622258" style="color: #07a">Download Here</a>]
      </div>
    </li>
  </p> 
  
  
  
  <p>
      <li>
      <div>
       J. Dean and S. Ghemawat. MapReduce: Simplified data processing on large clusters. Commun. ACM, 51(1):107–113, 2008. [
        <a href="https://dl.acm.org/citation.cfm?id=1327492" style="color: #07a">Download Here</a>]
      </div>
    </li>
  </p> 
  
  
</ul>

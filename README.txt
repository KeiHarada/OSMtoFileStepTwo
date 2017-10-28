----------------------------------------README HEADER----------------------------------------
Author: Erik V. Kjellberg
Last Edited: 8/7/2017
Contact: erik.villaman@gmail.com
----------------------------------------README HEADER----------------------------------------
--------------------------------------TABLE OF CONTENTS--------------------------------------

I. Requirements
II. Running
III. Input
IV. Output

--------------------------------------TABLE OF CONTENTS--------------------------------------
---------------------------------------I. REQUIREMENTS---------------------------------------

To run this program JAVA is required, it was developed in JAVA 1.8.0_144, though earlier versions
may be good enough, this has not been tested.

A MySQL database running should be available with data from parking places in the city of
Santander.

The project should be compiled and run along the MySQL driver, making it possible to query a
MySQL database, following external jar-file is needed:

mysql-connector-java-5.1.40-bin

The program assumes an already populated Database.

---------------------------------------I. REQUIREMENTS---------------------------------------
-----------------------------------------II. RUNNING-----------------------------------------

Main Class: "graph.SMGraph.class"  

-----------------------------------------II. RUNNING-----------------------------------------
-----------------------------------------III. INTPUT-----------------------------------------

nodes.txt
edges.txt

-----------------------------------------III. INTPUT-----------------------------------------
---------------------------------------IV. OUTPUT--------------------------------------------

santander_node.txt
the number of nodes is writtern on first line.
each line means (nodeID, longitude, latitude)

santander_edge.txt
each line means (from, to, distance)
we set the distance, between node and shops, "zero".

santander_poi.txt
each line means (nodeID, storeID, categoryID)

---------------------------------------IV. OUTPUT--------------------------------------------
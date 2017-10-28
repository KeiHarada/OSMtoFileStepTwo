package graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.jmx.snmp.Timestamp;

import sql.OLSQLQueries;

public class SMGraph {

	HashMap<Long, SMNode> nodes;
	ArrayList<SMEdge> edges;

	public static void main(String[] args) {
		System.out.println(new Timestamp(System.currentTimeMillis()).toString());
		SMGraph graph = new SMGraph();
		System.out.println("Importing nodes...");
		try {
			graph.importNodes();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Importing edges...");
		try {
			graph.importEdges();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Importing stores...");
		try {
			graph.importStores();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Exporting files...");
		try {
			graph.exportToFiles();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println(new Timestamp(System.currentTimeMillis()).toString());
	}

	private void exportToFiles() throws IOException {
		BufferedWriter nodeBW = new BufferedWriter(new FileWriter(new File("santander_node.txt")));
		BufferedWriter edgeBW = new BufferedWriter(new FileWriter(new File("santander_edge.txt")));
		BufferedWriter POIBW = new BufferedWriter(new FileWriter(new File("santander_poi.txt")));
		nodeBW.write(nodes.size() + System.getProperty("line.separator"));
		for (long i = 0; i < nodes.size(); i++) {
			SMNode n = nodes.get(i);
			nodeBW.write(n.getID() + "\t" + n.getLongitude() + "\t" + n.getLatitude() + System.getProperty("line.separator"));
			if (n instanceof SMShop) {
				SMShop s = (SMShop) n;
				POIBW.write(n.getID() + "\t" + s.getStoreID() + "\t" + s.getFixedCategory() + System.getProperty("line.separator"));
			}
		}
		for (SMEdge e : edges) {
			edgeBW.write(e.getFrom().getID() + "\t" + e.getTo().getID() + "\t" + e.getDist() + System.getProperty("line.separator"));
		}
		nodeBW.close();
		edgeBW.close();
		POIBW.close();
	}

	private void importStores() throws SQLException {
		OLSQLQueries queryMachine = new OLSQLQueries("localhost", "SmartSantander", "root", "onizukalab");
		ResultSet rs = queryMachine.getShops();
		// 1.longitude, 2.latitude, 3.storeid, 4.categoryid
		long identifier = nodes.size();
		ArrayList<SMShop> shops = new ArrayList<SMShop>();
		while (rs.next()) {
			shops.add(new SMShop(identifier, rs.getDouble(1), rs.getDouble(2), rs.getInt(3), rs.getInt(4)));
			nodes.put(identifier, shops.get(shops.size() - 1));
			identifier++;
		}
		for (SMShop shop : shops) {
			SMNode closest = null;
			double currentDist = -1;
			for (long i = 0; i < nodes.size() - shops.size(); i++) {
				double newDist = SMEdge.dist(shop, nodes.get(i));
				if (closest == null) {
					closest = nodes.get(i);
					currentDist = newDist;
				} else if (newDist < currentDist) {
					closest = nodes.get(i);
					currentDist = newDist;
				}
			}
			edges.add(new SMEdge(shop, closest, 1));
		}
		queryMachine.close();
	}

	private void importEdges() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("edges.txt"));
		String s;
		while ((s = br.readLine()) != null) {
			String[] ss = s.split(" ");
			SMNode from = nodes.get(Long.parseLong(ss[0]));
			SMNode to = nodes.get(Long.parseLong(ss[1]));
			if (from != null && to != null) {
				edges.add(new SMEdge(from, to));
			} else {
				if (from == null) {
					System.out.println(ss[0]);
				}
				if (to == null) {
					System.out.println(ss[1]);
				}
			}
		}
		br.close();
	}

	private void importNodes() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("nodes.txt"));
		//String s = br.readLine(); // skipping number of nodes.
		String s;
		while ((s = br.readLine()) != null) {
			String[] ss = s.split("\t");
			nodes.put(Long.parseLong(ss[0]), new SMNode(Long.parseLong(ss[0]), Double.parseDouble(ss[1]), Double.parseDouble(ss[2])));
		}
		br.close();
	}

	public SMGraph() {
		nodes = new HashMap<Long, SMNode>();
		edges = new ArrayList<SMEdge>();
	}
}

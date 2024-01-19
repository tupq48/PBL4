package dijkstra.algo;

import java.util.ArrayList;
import java.util.List;

import controller.Line;
import controller.Node;

public class Dijkstra {
	
	public void run(ArrayList<Node> nodes, ArrayList<Line> line)
	{
		
	}
	public static void main(String[] args) {
//        Vert vA = new Vert("A");
//        Vert vB = new Vert("B");
//        Vert vC = new Vert("C");
//        Vert vD = new Vert("D");
//        Vert vE = new Vert("E");
//        Vert vF = new Vert("F");
//      
//
//        vA.addNeighbour(new Edge(2, vA, vF));
//        vA.addNeighbour(new Edge(4, vA, vB));
//       // vA.addNeighbour(new Edge(0, vA, vC));
//        vC.addNeighbour(new Edge(3, vC, vB));
//        vC.addNeighbour(new Edge(2, vC, vD));
//        vB.addNeighbour(new Edge(3, vB, vC));
//        vB.addNeighbour(new Edge(4, vB, vA));
//        vB.addNeighbour(new Edge(3, vB, vE));
//        vD.addNeighbour(new Edge(2, vD, vC));
//        vD.addNeighbour(new Edge(1, vD, vE));
//        vE.addNeighbour(new Edge(1, vE, vD));
//        vE.addNeighbour(new Edge(3, vE, vB));
//        vE.addNeighbour(new Edge(3, vE, vF));
//        vF.addNeighbour(new Edge(3, vF, vE));
//        vF.addNeighbour(new Edge(2, vF, vA));
		List<Node> nodes = new ArrayList<>();
		List<Line> lines = new ArrayList<>();
//		nodes = InputDataListener.getNodes();
//		lines = InputDataListener.getLines();
		for (Line line : lines) {
			System.out.println(
					line.getStartNode().getName() + " " + line.getEndNode().getName() + " " + line.getWeight());
		}
		List<Vert> verts = new ArrayList<>();

		for (Node node : nodes) {
			//Vert a = verts.get(0);
			verts.add(node.getName1());
		}
		for (Vert vert : verts) {
			System.out.println("vert = " + vert.getName());
		}
		for (Line line : lines) {
			// System.out.println("vert = " + vert.getName());
			line.getStartNode().getName1().addNeighbour(
					new Edge(line.getWeight(), line.getStartNode().getName1(), line.getEndNode().getName1()));
			line.getEndNode().getName1().addNeighbour(
					new Edge(line.getWeight(), line.getEndNode().getName1(), line.getStartNode().getName1()));

		}

		PathFinder shortestPath = new PathFinder();
		shortestPath.ShortestP(verts.get(0));
		System.out.println("Khoáº£ng cÃ¡ch tá»‘i thiá»ƒu tá»«:");
		System.out.println("A Ä‘áº¿n B: " + verts.get(1).getDist());
		System.out.println("A Ä‘áº¿n C: " + verts.get(1).getDist());
		System.out.println("A Ä‘áº¿n D: " + verts.get(3).getDist());
//		System.out.println("A Ä‘áº¿n E: " + vE.getDist());
//		System.out.println("A Ä‘áº¿n F: " + vF.getDist());

		System.out.println("Ä�Æ°á»�ng Ä‘i ngáº¯n nháº¥t tá»«:");
		System.out.println("A Ä‘áº¿n B: " + shortestPath.getShortestP(verts.get(1)));
		System.out.println("A Ä‘áº¿n C: " + shortestPath.getShortestP(verts.get(2)));
		System.out.println("A Ä‘áº¿n D: " + shortestPath.getShortestP(verts.get(3)));
//		System.out.println("A Ä‘áº¿n E: " + shortestPath.getShortestP(vE));
//		System.out.println("A Ä‘áº¿n F: " + shortestPath.getShortestP(vF));

	}

	private static void findPath() {
		List<Node> nodes = new ArrayList<>();
		List<Line> lines = new ArrayList<>();
//		nodes = InputDataListener.getNodes();
//		lines = InputDataListener.getLines();
		for (Line line : lines) {
			System.out.println(
					line.getStartNode().getName() + " " + line.getEndNode().getName() + " " + line.getWeight());
		}
		List<Vert> verts = new ArrayList<>();

		for (Node node : nodes) {
			Vert a = verts.get(0);
			verts.add(node.getName1());
		}
		for (Vert vert : verts) {
			System.out.println("vert = " + vert.getName());
		}
		for (Line line : lines) {
			// System.out.println("vert = " + vert.getName());
			line.getStartNode().getName1().addNeighbour(
					new Edge(line.getWeight(), line.getStartNode().getName1(), line.getEndNode().getName1()));
			line.getEndNode().getName1().addNeighbour(
					new Edge(line.getWeight(), line.getEndNode().getName1(), line.getStartNode().getName1()));

		}

	}
}

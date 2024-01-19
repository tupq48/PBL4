package controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.Serializable;

public class Line implements Serializable{
	private Point startpoint;
	private Point endpoint;
	private Node startNode;
	private Node endNode;
	private int weight;
	private Color color = Color.black;
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

	public Point getStartpoint() {
		return startpoint;
	}
	
	public void setStartpoint(Point startpoint) {
		this.startpoint = startpoint;
	}
	
	public Point getEndpoint() {
		return endpoint;
	}
	
	public void setEndpoint(Point endpoint) {
		this.endpoint = endpoint;
	}
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public Line(Node startNode, Node endNode, int weight) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.weight = weight;
		this.startpoint = new Point((int)this.startNode.getCenterX(),(int)this.startNode.getCenterY());
		this.endpoint = new Point((int)this.endNode.getCenterX(),(int)this.endNode.getCenterY());
	}
	
	public Line() {
		// TODO Auto-generated constructor stub
	}


	public Line2D GetLine2D()
	{
		updatePoint();
		return new Line2D.Float(this.startpoint, this.endpoint);
	}

	public void updatePoint() {
		this.startpoint = new Point((int)this.startNode.getCenterX(),(int)this.startNode.getCenterY());
		this.endpoint = new Point((int)this.endNode.getCenterX(),(int)this.endNode.getCenterY());
	}

	
}

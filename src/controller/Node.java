package controller;

import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import dijkstra.algo.Vert;

public class Node implements Serializable{

	private Ellipse2D node;
	private String name;
	private Vert name1;
	
	public Ellipse2D getNode() {
		return node;
	}
	
	public void setNode(Ellipse2D node) {
		this.node = node;
	}
	
	public String getName() {
		return name;
		
	}public Vert getName1() {
		return name1;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Node(Ellipse2D node, String name, Vert name1)
	{
		this.node = node;
		this.name = name;
		this.name1 = name1;
	}
	
	public Node(Ellipse2D node, String name)
	{
		this.node = node;
		this.name = name;
	}
	
	public int getCenterX() {
		return (int)node.getCenterX();
	}
	
	public int getCenterY() {
		return (int)node.getCenterY();
	}
}

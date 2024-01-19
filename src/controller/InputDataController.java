package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import view.InputData;

public class InputDataController {

	InputData inDataView;
	InputDataClickHandler inDataClickHandler;
	
	ArrayList<Node> nodes;
	ArrayList<Line> lines;
	
	public InputDataController(InputData inputData, InputDataClickHandler clickHandler) 
	{
		this.inDataView = inputData;
		this.inDataClickHandler = clickHandler;
		nodes = new ArrayList<Node>();
		lines = new ArrayList<Line>();
	}
	
	public void update() {
		updateLines();
		updateNodes();
	}

	private void updateNodes() {
		
	}
	
	private void updateLines() {
		
	}
	
	public void addNewNode(Node node)
	{
		nodes.add(node);		
	}
	
	public void removeNode(Node node)
	{
		if (nodes.indexOf(node) != -1)
			nodes.remove(node);
	}
	
	public boolean nodesContainLocation(Point point)
	{
		if (nodes.size()>0)
		for (Node checkNode : nodes)
		{
			if (checkNode.getNode().contains(point))
			{
				return true;
			}
		}
		return false;
	}

	public boolean nameNodeAlreadyExist(String name) {
		if (nodes.size()>0)
		for (Node checkNode : nodes)
		{
			if (checkNode.getName().equals(name))
			{
				return true;
			}
		}
		return false;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public ArrayList<Line> getLines() {
		return lines;
	}

	public boolean isLineExistInLines(Line line) {
		for (Line baseline : lines)
		{
			if (baseline.getEndNode() == line.getEndNode())
				if (baseline.getStartNode() == line.getStartNode())
				{
					return true;
				}
					
			
			if (baseline.getEndNode() == line.getStartNode())
				if (baseline.getStartNode() == line.getEndNode())
				{
					return true;
				}
					
		}
		return false;
	}

	public void addNewLine(Line line) {
		lines.add(line);
	}

	public void changeWeightLine(Line line) {
		for (Line baseline : lines)
		{
			//check if node from A to B
			if (baseline.getEndNode() == line.getEndNode())
				if (baseline.getStartNode() == line.getStartNode())
				{
					baseline.setWeight(line.getWeight());
					return;
				}
					
			
			 //if node from B to A, also change weight
			if (baseline.getEndNode() == line.getStartNode())
				if (baseline.getStartNode() == line.getEndNode())
				{
					baseline.setWeight(line.getWeight());
					return;
				}
		}
	}

	public void removeLineContainNode(Node node) {
		if (lines.size()>0)
		{
			int index = 0;
			while (index < lines.size())
			{
				Line line = lines.get(index);
				if (line.getStartNode() == node || line.getEndNode() == node)
				{
					lines.remove(lines.indexOf(line));
					continue;
				}
				index++;
			}
		}
	}
}

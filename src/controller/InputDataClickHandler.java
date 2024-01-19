package controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import view.InputData;

public class InputDataClickHandler implements MouseListener, MouseMotionListener {

	public boolean addClicked, moveClicked, delClicked, lineClicked;
	InputData inData;
	InputDataController inDataController;
	public int radius = 40;
	public Node nodeDragged;
	public Point nodeDraggedOffset;
	private Node nodeStartLine;
	private Node nodeEndLine;
	public InputDataClickHandler(InputData inputData, InputDataController inDataController)
	{
		inData = inputData;
		this.inDataController = inDataController;
	}
	
	public void setInputDataController(InputDataController inDataController) {
		this.inDataController = inDataController;
		
	}

	private void setAllClickedFalse() {
		nodeStartLine = null;	//the 2 line-code to delete
		nodeEndLine = null;		//action draw line when mouse move
		addClicked = false;
		moveClicked = false;
		delClicked = false;
		lineClicked = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		System.out.println("mouse location: " + e.getX() + " " + e.getY());
		if (e.getSource() == inData.btnAdd)
		{
			setAllClickedFalse();
			addClicked = true;
		}
		else if (e.getSource() == inData.btnMove)
		{
			setAllClickedFalse();
			moveClicked = true;
		}
		else if (e.getSource() == inData.btnDelete)
		{
			setAllClickedFalse();
			delClicked = true;
		}
		else if (e.getSource() == inData.btnAddLine)
		{
			setAllClickedFalse();
			lineClicked = true;
		}
		else if (addClicked)
		{
			Point mouseLocation = e.getPoint();
			
			Ellipse2D newShapeNode = new Ellipse2D.Float(e.getX() - (radius / 2), 
													 	 e.getY() - (radius / 2) , 
													 	 radius,
													 	 radius);

			try 
			{
				String name = null;
				if (!inDataController.nodesContainLocation(mouseLocation))
				{
					name = JOptionPane.showInputDialog(inData , "Nhap ten Dinh");
					if (name.length() > 2)
					{
						JOptionPane.showMessageDialog(inData ,"TEN DINH CHI CO TOI DA 2 KI TU");
						return;
					}
					name = name.toUpperCase();
					if (inDataController.nameNodeAlreadyExist(name))
					{
						JOptionPane.showMessageDialog(inData ,"TEN DINH DA TON TAI");
						return;
					}
					if (name == null) return;
					if (name.trim().equals("")) throw new Exception();
					Node node = new Node(newShapeNode,name);
					inDataController.addNewNode(node);
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog( inData ,"Vui long dat ten cho Dinh");
				System.out.println(e1.getMessage());
			}
		}
		else if (delClicked)
		{
			Point mouseLocation = e.getPoint();
			ArrayList<Node> nodes = inDataController.getNodes();
			ArrayList<Line> lines = inDataController.getLines();
			if (nodes.size()>0)
			{
				for (Node node : nodes)
				{
					if (node.getNode().contains(mouseLocation))
					{
						inDataController.removeLineContainNode(node);
						nodes.remove(nodes.indexOf(node));
						break;
					}	
				}
				for (Line line : lines)
				{
					if (line.GetLine2D().getBounds().contains(mouseLocation))
					{
						System.out.println("xoa line ne");
						lines.remove(line);
						break;
					}
						
				}
			}
			
		}
		else if (lineClicked)
		{
			Point mouseLocation = e.getPoint();
			ArrayList<Node> nodes = inDataController.getNodes();
			if (nodes.size()>0)
			{
				for (Node node : nodes)
				{
					if (node.getNode().contains(mouseLocation))
					{
						Point p = new Point((int)node.getCenterX(), (int)node.getCenterY());
						if (nodeStartLine==null)
						{
							nodeStartLine = node;
							return;
						}
						else 
						{
							nodeEndLine = node;
							
							//System.out.println(nodeStartLine.getName() + "  " + nodeEndLine.getName());
							
							if (nodeStartLine == nodeEndLine) {
								nodeStartLine = null;
								nodeEndLine = null;
								return;
							}
							String value = JOptionPane.showInputDialog(inData , "Nhap khoang canh");
							if (value == null)
							{
								nodeStartLine = null;
								nodeEndLine = null;
								return;
							}
							try {
								int weight = Integer.parseInt(value);
								if (weight <= 0) throw new Exception();
								Line line = new Line(nodeStartLine, nodeEndLine, weight);
								if (!inDataController.isLineExistInLines(line))
								{
									System.out.println("line chua ton tai");
									inDataController.addNewLine(line);
								}
								else
								{
									System.out.println("thay doi trong so");
									System.out.println("line: " + line.getStartNode().getName() + " -> " + line.getEndNode().getName());
									inDataController.changeWeightLine(line);
								}
								
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(inData,"Khoang cach phai la so nguyen duong");
							} 
							nodeStartLine = null;
							nodeEndLine = null;
							return;
						}
					}
				}
			}	
			if (nodeStartLine != null && nodeEndLine == null)
			{
				nodeStartLine = null;
				nodeEndLine = null;
			}	
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (moveClicked)
		{
			for (Node node : inDataController.getNodes()) 
			{
	            if (node.getNode().contains(e.getPoint())) 
	            {
	            	nodeDragged = node;
	            	nodeDraggedOffset = new Point(node.getNode().getBounds().x - e.getX(), node.getNode().getBounds().y - e.getY());
	            	break;
	            }
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.nodeDragged = null;
		this.nodeDraggedOffset = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.nodeDragged = null;
		this.nodeDraggedOffset = null;
	}

	

	@Override
	public void mouseDragged(MouseEvent e) {
		if (moveClicked)
		{
			if (nodeDragged != null && nodeDraggedOffset != null)
				
			{
				Point to = e.getPoint();
	            to.x += nodeDraggedOffset.x;
	            to.y += nodeDraggedOffset.y;

	            Rectangle bounds = nodeDragged.getNode().getBounds();
	            bounds.setLocation(to);
	            nodeDragged.getNode().setFrame(bounds);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (nodeStartLine != null)
		{
			Point p1, p2;
			p2 = e.getPoint();
			p1 = new Point((int)nodeStartLine.getNode().getCenterX(), (int)nodeStartLine.getNode().getCenterY());
			inData.drawLineWhenMouseMove(p1,p2);
		}
	}
	
}

package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Client;
import view.SendDataView;

public class SendDataViewController {

	SendDataView sendView;
	
	public ArrayList<Node> nodes;
	public ArrayList<Line> lines;
	String startNodeName, endNodeName;
	public Client client;
	
	public SendDataViewController(SendDataView sendView)
	{
		this.sendView = sendView;
		client = new Client();
		try {
			client.connectToServer();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(sendView.application,"Không thể kết nối đến server, bạn vui lòng khởi động lại app!");
			sendView.application.everyThingOk = false;
		}
	}
	public void getData() {
		nodes = sendView.inputView.getNodes();
		lines = sendView.inputView.getLines();
		startNodeName = sendView.getStartNodeName();
		endNodeName = sendView.getEndNodeName();
	}
	public void sendData() {
		System.out.println("send data");
		//GUI DU LIEU LEN CHO SERVER XU LI
		client.setData(nodes, lines, startNodeName, endNodeName);
		client.sendData();
		System.out.println("send data thanh cong");
		client.receiveData();
		System.out.println("nhan data thanh cong");
		
		/*
			CLIENT DA NHAN DU LIEU THANH CONG, LUU TRONG 2 ARRAYLIST TRONG CLIENT LA: 
				+ DISTANCES: CHUA CAC DONG STRING, LA KET QUA KHOANG CACH TU DINH BAT DAU TOI CAC DINH CON LAI
				+ PATHS: CHUA CAC DONG STRING, LA KET QUA DUONG DI TU DINH BAT DAU TOI CAC DINH CON LAI
				
			-> CAN DUA DU LIEU NAY DEN CHO RESULTVIEW DE IN RA KET QUA LEN VIEW
		*/
		
		//GUI DATA DEN CHO RESULT VIEW
		sendView.application.resultView.getData(client.distances, client.paths, startNodeName, endNodeName);
		sendView.application.resultView.showData();
		
		/*
		 * THAY DOI MAU SAC CUA CAC DUONG NOI TU DINH BAT DAU TOI DINH KET THUC
		 */
		String finalPath = null;
		for (String path : client.paths)
		{
			if (path.startsWith(endNodeName))
			{
				finalPath = path;
				break;
			}
		}
		finalPath = finalPath.substring(finalPath.indexOf(": ")+2);
		System.out.println(finalPath);
		String firstVertName = null, secondVertName = null;
		boolean loop = true;
		while (loop)
		{
			secondVertName = firstVertName;
			int index = finalPath.indexOf(" -> ");
			if (index == -1) 
			{	
				firstVertName = finalPath;
				loop = false;
			}
			if (loop)
			{
				firstVertName = finalPath.substring(0, index);
				finalPath = finalPath.substring(index + 4);
			}

			if (firstVertName != null && secondVertName != null)
			{
				sendView.application.inputView.setColorOfLine(firstVertName, secondVertName);
			} 
		}
		//
	
	}
	public boolean checkNodeName(String nodeName) 
	{
		if (nodes.size() == 0) return false;
		for (Node node : nodes) {
			if (node.getName().equals(nodeName))
				return true;
		}
		return false;
	}
}

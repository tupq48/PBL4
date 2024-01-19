package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import controller.Line;
import controller.Node;

public class Client 
{
	public Socket socket;
	String host = "127.0.0.1";
	int serverPort = 9999;
	
	ObjectOutputStream objectOutputStream;
	
	ObjectInputStream objectInputStream;
	
	ArrayList<Node> nodes;
	ArrayList<Line> lines;

	String startVertName, endVertName;
	public ArrayList<String> distances;
	public ArrayList<String> paths;
	
	public Client()
	{
		this.nodes = new ArrayList<Node>();
		this.lines = new ArrayList<Line>();
	}
	
	public void setData(ArrayList<Node> nodes, ArrayList<Line> lines, String startVertName, String endVertName)
	{
		this.nodes = nodes;
		this.lines = lines;
		this.startVertName = startVertName;
		this.endVertName = endVertName;
	}
	
	public void connectToServer() throws UnknownHostException, IOException
	{
	socket = new Socket(InetAddress.getByName(host), serverPort);
	
	OutputStream outStream = socket.getOutputStream();
	objectOutputStream = new ObjectOutputStream(outStream);
	
	InputStream inputStream = socket.getInputStream();
	objectInputStream = new ObjectInputStream(inputStream);
	}
	
	public void sendData()
	{
		try {
			objectOutputStream.writeObject(this.nodes);
			objectOutputStream.flush();
			objectOutputStream.writeObject(this.lines);
			objectOutputStream.flush();
			objectOutputStream.writeUTF(startVertName);
			objectOutputStream.flush();
			objectOutputStream.writeUTF(endVertName);
			objectOutputStream.flush();
			
			//PHAI CO DONG NAY DE RESET SOCKET, NEU KO THI OBJECT GUI DI SE BI GHI DE BOI OBJECT TRUOC, KO TAO MOI DOI TUONG DC
			objectOutputStream.reset();
			
		} catch (IOException e) {
			System.out.println("khong the send data den server");
			e.printStackTrace();
		}
		
	}
	
	public void receiveData()
	{
		try {
			ArrayList<String> distances;
			ArrayList<String> paths;
			distances = (ArrayList<String>) objectInputStream.readObject();
			paths = (ArrayList<String>) objectInputStream.readObject();
			
			this.distances = distances;
			this.paths = paths;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

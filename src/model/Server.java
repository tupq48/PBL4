package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.xml.stream.events.StartDocument;

import controller.Line;
import controller.Node;
import dijkstra.algo.Edge;
import dijkstra.algo.PathFinder;
import dijkstra.algo.Vert;

public class Server extends JFrame implements Runnable, ActionListener
{
	ServerSocket serverSocket;
	
	ArrayList<ClientHandler> clients;
	
	private int port = 9999;
	
	public boolean done;
	
	Thread serverThread;
	
	
	/*
	 * Setup GUI 
	 */
	private int screenHeight = 500;
	private int screenWidth = 400;
	
	private ArrayList<JTextArea> listTextArea;
	private JComboBox cbbClients;
	private JButton btnStart, btnStop;
	private JLabel lblPort, lblAppStatus;
	private JTextField txtPort;
	private JLayeredPane pnlMain;
	private JTextArea txtServerArea;
	
	Font Arial_Plain_15 = new Font("Arial", Font.PLAIN, 15);
	
	public Server() 
	{
		clients = new ArrayList<>();
		done = false;
		serverThread = new Thread(this);
		this.listTextArea = new ArrayList<JTextArea>();
		GUI();
	}
	
	private void GUI() {
		int x, y, w, h;
		
		
		pnlMain = new JLayeredPane();
		
		this.btnStart = new JButton("Start");
		this.btnStart.setFont(Arial_Plain_15);
		this.btnStart.addActionListener(this);
		w = (int) (getBoundOfText(btnStart.getText(), Arial_Plain_15).getWidth()*2.1);
		h = (int) getBoundOfText(btnStart.getText(), Arial_Plain_15).getHeight() + 2;
		x = (screenWidth - w)/2;
		y = (int) (0.1*screenHeight);
		this.btnStart.setBounds(x, y, w, h);
		
		this.btnStop = new JButton("Stop");
		this.btnStop.addActionListener(this);
		this.btnStop.setFont(Arial_Plain_15);
		w = (int) (getBoundOfText(btnStop.getText(), Arial_Plain_15).getWidth()*2.2);
		h = (int) getBoundOfText(btnStop.getText(), Arial_Plain_15).getHeight() + 2;
		x =  screenWidth/3*2 + (screenWidth/3 - w)/2;
		y = (int) (0.1*screenHeight);
		this.btnStop.setBounds(x, y, w, h);
		
		this.cbbClients = new JComboBox();
		this.cbbClients.addActionListener(this);
		this.cbbClients.addItem("Server");
		this.cbbClients.setFont(Arial_Plain_15);
		w = (int) (getBoundOfText("client xx", Arial_Plain_15).getWidth()*1.5);
		h = (int) getBoundOfText("client xx", Arial_Plain_15).getHeight() + 2;
		x = (int) (0.05*screenWidth);
		y = (int) (0.1*screenHeight);
		this.cbbClients.setBounds(x, y, w, h);
		
		//	Using a template JTEXTAREA 
		this.txtServerArea = new JTextArea();
		settingJTextArea(txtServerArea,0);
		txtServerArea.setVisible(true);
		
		this.lblPort = new JLabel("port:");
		this.lblPort.setFont(Arial_Plain_15);
		w = (int) (getBoundOfText(lblPort.getText(), Arial_Plain_15).getWidth() + 2);
		h = (int) getBoundOfText(lblPort.getText(), Arial_Plain_15).getHeight() + 2;
		double percentWidth = w*1.0/screenWidth;
		x =  (int) ((screenWidth/2*(1-percentWidth/2)-w) - 0.01*screenWidth);
		y = (int) (0.03*screenHeight);
		this.lblPort.setBounds(x, y, w, h);
		
		this.txtPort = new JTextField();
		this.txtPort.setFont(Arial_Plain_15);
		this.txtPort.setEditable(false);
		txtPort.setText(Integer.toString(port));
		w = (int) (getBoundOfText("11111", Arial_Plain_15).getWidth() +2);
		h = (int) getBoundOfText("11111", Arial_Plain_15).getHeight() + 2;
		x =  (int) (screenWidth/2 + 0.01*screenWidth);
		y = (int) (0.03*screenHeight);
		this.txtPort.setBounds(x, y, w, h);
		
		this.lblAppStatus = new JLabel("running...");
		this.lblAppStatus.setFont(Arial_Plain_15);
		w = (int) (getBoundOfText(lblAppStatus.getText(), Arial_Plain_15).getWidth()*2.2);
		h = (int) getBoundOfText(lblAppStatus.getText(), Arial_Plain_15).getHeight() + 2;
		x =  (int) (0.02 * screenWidth);
		y = (int) (0.885*screenHeight);
		this.lblAppStatus.setBounds(x, y, w, h);
		pnlMain.add(lblPort);
		pnlMain.add(txtPort);
		pnlMain.add(btnStart);
		pnlMain.add(btnStop);
		pnlMain.add(cbbClients);
		pnlMain.add(lblAppStatus);
		this.add(pnlMain);
		
		this.pack();
		this.setVisible(true);
		this.setSize(new Dimension(screenWidth, screenHeight));
		this.setTitle("Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
	}
	
	private Rectangle2D getBoundOfText(String text, Font font)
	{
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		
		return font.getStringBounds(text, frc);
	}
	
	public void settingJTextArea(JTextArea jTextArea, int index)
	{
		if (listTextArea.size() == index) 
			this.listTextArea.add(jTextArea);
		else if (listTextArea.size() > index)
		{
			this.listTextArea.add(index, jTextArea);
		}
		jTextArea.setFont(Arial_Plain_15);
		int w = (int) (screenWidth*0.95) ;
		int h = (int) (screenHeight*0.73);
		int x = (int) (screenWidth*0.01);
		int y = (int) (screenHeight*0.15);
		jTextArea.setBounds(x, y, w, h);
		jTextArea.setEditable(false);
		jTextArea.setVisible(false);
		Border tempBorder = BorderFactory.createLineBorder(Color.black);
		jTextArea.setBorder(tempBorder);
		pnlMain.add(jTextArea);
	}
	
	@Override
	public void run() 
	{
		done = true;
		try {
			serverSocket = new ServerSocket(port);
			done = false;
		} catch (IOException e1) {
			txtServerArea.setText("Port " + port + " đã được sử dụng, vui lòng chọn port khác!");
			done = true;
			return;
		}
		try 
		{
			String mess = "Server khởi tạo tại port: " + port;
			String temp = txtServerArea.getText();
			txtServerArea.setText(mess);
			while(!done)
			{
				Socket clientSocket = serverSocket.accept();
				ClientHandler client = new ClientHandler(clientSocket);
				clients.add(client);
				client.start();
				mess = "+1 kết nối, số lượng client hiện tại : " + clients.size();
				txtServerArea.setText(txtServerArea.getText() + "\n" + mess);
			}
		} catch (IOException e) 
		{
			
		}
			
		
	}

	class ClientHandler extends Thread
	{
		Socket clientSocket;
		
		ObjectOutputStream objectOutputStream;
		
		ObjectInputStream objectInputStream;
		
		ArrayList<Node> nodes;
		ArrayList<Line> lines;
		String startVertName, endVertName;
		
		//OBJECT CHUA DU LIEU SE DUOC GUI DEN CLIENT SAU KHI XU LI
		ArrayList<String> distances;
		ArrayList<String> paths;
		
		ArrayList<Vert> verts;
		boolean done;
		
		int sttClient;
		
		JTextArea clientStatus;
		public ClientHandler(Socket clientSocket)
		{
			this.clientSocket = clientSocket;
			verts = new ArrayList<>();
			distances = new ArrayList<String>();
			paths = new ArrayList<String>();
			nodes = new ArrayList<Node>();
			lines = new ArrayList<Line>();
			done = false;
			clientStatus = new JTextArea();
			
		}
		
		@Override
		public void run() 
		{
			try {
				OutputStream outStream = clientSocket.getOutputStream();
				objectOutputStream = new ObjectOutputStream(outStream);
				
				InputStream inputStream = clientSocket.getInputStream();
				objectInputStream = new ObjectInputStream(inputStream);
				
				
				if (cbbClients.getItemCount() > 1)
				{
					
					boolean isAdded = false;
					for (int index = 1; index < cbbClients.getItemCount(); index++)
					{
						if (cbbClients.getItemAt(index).toString().endsWith(Integer.toString(index)))
						{
							continue;
						}
						sttClient = index;
						String temp = "client " + sttClient;
						cbbClients.insertItemAt(temp, sttClient);
						isAdded = true;
						break;
					}
					
					if (!isAdded)
					{
						sttClient = clients.indexOf(this) + 1;
						String temp = "client " + sttClient;
						cbbClients.addItem(temp);
					}
				}
				else
				{
					sttClient = clients.indexOf(this) + 1;
					String temp = "client " + sttClient;
					cbbClients.addItem(temp);
				}

				settingJTextArea(clientStatus,sttClient);
					
				while (!done)
				{
					String status;
					
					status = "Client " + sttClient + ": " + "SERVER chờ nhận dữ liệu từ CLIENT...";
					clientStatus.setText(clientStatus.getText() + status);
					receiveData();
					if (clientStatus.getLineCount() >= 14) 
					{
						clientStatus.setText(status);
					}
					status = "Client " + sttClient + ": " + "SERVER đã nhận dữ liệu";
					clientStatus.setText(clientStatus.getText() + "\n" + status);
					xuLiDuLieu();
					status = "Client " + sttClient + ": " + "SERVER đang xử lí dữ liệu";
					clientStatus.setText(clientStatus.getText() + "\n" + status);
					status = "Client " + sttClient + ": " + "SERVER gửi dữ liệu cho CLIENT";
					clientStatus.setText(clientStatus.getText() + "\n" + status);
					sendDataToClient();
					status = "Client " + sttClient + ": " + "SERVER gửi dữ liệu thành công\n\n";
					clientStatus.setText(clientStatus.getText() + "\n" + status);
				}
				
			} catch (Exception e) {
				/*
				 * SOCKET CLOSE
				 * DELETE CLIENT OUT OF DATA
				 */
				listTextArea.remove(clientStatus);
				cbbClients.removeItem("client " + sttClient);
				clients.remove(this);
				txtServerArea.setText(txtServerArea.getText() + "\nNgắt kết nối với client " + sttClient + "\nSố lượng kết nối: " + clients.size());
				
			}
		}
		
		private void receiveData() throws ClassNotFoundException, IOException
		{
			
			//THU TU GUI OBJECT : NODES, LINES, STARTNODENAME, ENDNODENAME
			nodes = (ArrayList<Node>) objectInputStream.readObject();
			System.out.println("Client " + sttClient + ": " + "bat dau nhan du lieu");
			lines = (ArrayList<Line>) objectInputStream.readObject();
			startVertName = objectInputStream.readUTF();
			endVertName = objectInputStream.readUTF();
		}
		
		private void xuLiDuLieu() 
		{
			System.out.println("Client " + sttClient + ": " + "SERVER BAT DAU XU LI DU LIEU");
			int numberOfVert = nodes.size();
			Vert beginVert = null;
			verts = null;
			verts = new ArrayList<Vert>();
			
			for (int i = 0; i < numberOfVert ; i++)
			{
				Vert vert = new Vert(nodes.get(i).getName());
				
				verts.add(vert);
				
				if (vert.getName().equals(startVertName))
					beginVert = vert;
			}
			
			
			for (Line line : lines)
			{
				String startVertName = line.getStartNode().getName();
				String endVertName = line.getEndNode().getName();
				Vert startVert = findVertWithName(startVertName);
				Vert endVert = findVertWithName(endVertName);
				
				//XU LI KHI STARTVERT OR ENDVERT NULL (KHONG TIM THAY)
				
				//ADD NEIGHTBOUR CHO CAC VERT
				startVert.addNeighbour(new Edge(line.getWeight(), startVert, endVert));
				endVert.addNeighbour(new Edge(line.getWeight(), endVert, startVert));
			}
			
			PathFinder shortestPath = new PathFinder();
			shortestPath.ShortestP(beginVert);

			distances = null;
			distances = new ArrayList<String>();
			paths = null;
			paths =  new ArrayList<String>();
			
			String distance, path;
			
			for (int i = 0; i< numberOfVert; i++)
			{
				if ( ! verts.get(i).getName().equals(beginVert.getName()))
				{
					distance = beginVert.getName() + " -> " + verts.get(i).getName() + " : " + verts.get(i).getDist();
					
					String pathTemp = shortestPath.getShortestP(verts.get(i)).toString();
					pathTemp = pathTemp.substring(1, pathTemp.length()-1);
					path = verts.get(i).getName() + " : " + pathTemp;
					
					//DU LIEU DUOC GUI DI DEN CLIENT
					distances.add(distance);
					path = path.replaceAll(", ", " -> ");
					System.out.println("path: " + path);
					paths.add(path);
				}
			}
		}
		
		private Vert findVertWithName(String name) {
			for (Vert vert : verts)
			{
				if (vert.getName().equals(name))
					return vert;
			}
			return null;
		}
		
		private void sendDataToClient() throws IOException 
		{
			objectOutputStream.writeObject(distances);
			objectOutputStream.flush();
			objectOutputStream.writeObject(paths);
			objectOutputStream.flush();
			
			//PHAI CO DONG NAY DE RESET SOCKET, NEU KO THI OBJECT GUI DI SE BI GHI DE BOI OBJECT TRUOC, KO TAO MOI DOI TUONG DC
			objectOutputStream.reset();
		}
	}
	
	public static void main(String[] args) 
	{
		Server server = new Server();
		server.run();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == btnStart)
		{
			if (done == false) 
			{
				JOptionPane.showMessageDialog(this ,"Để khởi chạy cổng mới bạn phải dừng server lại trước tiên!");
				return;
			}
				
			
			/*
			 * Reset all
			 */
			
			
			/*
			 * restart server
			 */
			try
			{
				port = Integer.parseInt(txtPort.getText());
				if (port >= 10000) throw new NumberFormatException(); 
				done = false;
				lblAppStatus.setText("Running...");
				serverThread.stop();
				serverThread = new Thread(this);
				serverThread.start();
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(this ,"Port phải là số nguyên dương và nhỏ hơn 10.000");
			}
			catch(Exception e2)
			{
				done = true;
			}
			
		}
		else if(e.getSource() == btnStop)
		{
			if (done == true)
				return;
			
			try {
				serverSocket.close();
				serverSocket = null;
				serverThread.stop();
				serverThread = new Thread(this);
				done = true;
				lblAppStatus.setText("Stoped");
				txtServerArea.setText(txtServerArea.getText() + "\nServer Kết thúc!");
			} catch (IOException e1) {
				System.out.println("khong the close server");
			}
		}
		else if(e.getSource() == cbbClients)
		{
			int index = cbbClients.getSelectedIndex();
			setVisibleTextArea(index);
		}
		
	}

	private void setVisibleTextArea(int index) 
	{
		for (int i = 0; i < listTextArea.size(); i++)
		{
			if (index == i) listTextArea.get(i).setVisible(true);
			else
				listTextArea.get(i).setVisible(false);
		}
		
	}
}

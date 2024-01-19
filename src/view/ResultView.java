package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Application;

public class ResultView extends JPanel implements ActionListener
{

	private int screenWidth = 1000;
	private int screenHeight = 400;
	
	private JLabel lbBangChiDuong;
	private JLabel lbFinalPath;
	private JLabel lbFinalDistance;
	private ArrayList<JLabel> lblPaths;
	
	private JLayeredPane pnlBangChiDuong;
	private JLayeredPane pnlResult;
	private ArrayList<String> distances;
	private ArrayList<String> paths;
	
	private String startVertName, endVertName;
	
	Application app;
	Font Arial_Bold_20;
	private JPanel pnlExit;
	private Button btnExit;
	public ResultView(Application app)
	{
		this.app = app;
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		
		setupView();
		btnExit.addActionListener(this);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
			}
		});
	}
	
	private void setupView()
	{
		//Setting variable
		lblPaths = new ArrayList<JLabel>();
		lbFinalPath = new JLabel();
		lbFinalDistance = new JLabel();	
		Arial_Bold_20 = new Font("Arial", Font.BOLD, 20);
		Font serif_Plain_30 = new Font("Serif", Font.PLAIN, 30);
		
		//Setting properties of View
		this.setLayout(new GridLayout(1,2));
		int bangChiDuongWidth = screenWidth/2;
		int bangChiDuongHeight = screenHeight;

		int x, y , w, h;
		//Left-Half View
		pnlBangChiDuong = new JLayeredPane();		
		
		lbBangChiDuong = new JLabel("Bang chi duong");
		lbBangChiDuong.setVisible(false);
		lbBangChiDuong.setFont(serif_Plain_30);
		w = (int) getBoundOfText("Bang chi duong", serif_Plain_30).getWidth() + 1;
		h = (int) getBoundOfText("Bang chi duong", serif_Plain_30).getHeight() + 1;
		x = (bangChiDuongWidth - w)/2;
		y = (int) (0.05*bangChiDuongHeight); //5% of Panel Height
		lbBangChiDuong.setBounds(x, y, w, h);
		//lbBangChiDuong.setVisible(false);
		
		
		//Right-Half View
		pnlResult = new JLayeredPane();
		
		pnlExit = new JPanel(new BorderLayout());
		Border tempBorder = BorderFactory.createLineBorder(Color.black);
		Border mainBorder = BorderFactory.createTitledBorder(tempBorder, "Control");
		pnlExit.setBorder(mainBorder);
		w = (int) (0.4*(screenWidth/2));
		h = (int) (0.1*(screenHeight));
		x = (int) (0.3*(screenWidth/2));
		y = (int) (0.65*screenHeight); 
		pnlExit.setBounds(x, y, w, h);
		btnExit = new Button("Exit");
		
		
		//Add component
		pnlBangChiDuong.add(lbBangChiDuong);
		
		pnlExit.add(btnExit, BorderLayout.CENTER);
		pnlResult.add(pnlExit);
		//Add component to View
		add(pnlBangChiDuong);
		add(pnlResult);
	}
	
	public void getData(ArrayList<String> distances, ArrayList<String> paths,String startVertName, String endVertName)
	{
		resetView();
		this.distances = distances;
		this.paths = paths;
		this.startVertName = startVertName;
		this.endVertName = endVertName;
		for (String path : paths)
		{
			JLabel newPath = new JLabel(path);
			lblPaths.add(newPath);
		}
	}
	
	public void showData()
	{
		//resetView();
		setViewOfPaths();
		setViewResult();
	}
	
	public void setViewResult()
	{
		boolean findedPath = true;
		//SET TEXT FINAL DISTANCE
		for (String distance : distances)
		{
			int index = distance.indexOf(endVertName);
			if (index != -1)
			{
				//a -> b:
				if (distance.indexOf("->") == index - 3)
					lbFinalDistance.setText("Khoang cach " + distance);
				else
					continue;
			}
		}
		int w = (int) getBoundOfText(lbFinalDistance.getText(), Arial_Bold_20).getWidth() + 5;
		int h = (int) getBoundOfText(lbFinalDistance.getText(), Arial_Bold_20).getHeight() + 1;
		int x = (screenWidth/2 - w)/2;
		int y = (int) (0.05*screenHeight);
		
		lbFinalDistance.setBounds(x,y,w,h);
		lbFinalDistance.setFont(Arial_Bold_20);
		lbFinalDistance.setVisible(true);
		//SET TEXT FINAL PATH
		for (String path : paths)
		{
			if (path.endsWith(endVertName))
			{
				lbFinalPath.setText("Duong di den " + path);
			}
		}
		//neu khong tim thay duong di thi thay doi ket qua
		if (lbFinalPath.getText().indexOf("->") == -1)
		{
			lbFinalPath.setText("Không tìm thấy đường đi đến " + endVertName);
			lbFinalDistance.setText("");
			for (JLabel lable : lblPaths)
			{
				if (lable.getText().indexOf("->") == -1)
				{
					int index = lable.getText().indexOf(":");
					lable.setText(lable.getText().substring(0, index) + ": ∞");
					Rectangle bound = lable.getBounds();
					int ww = (int) getBoundOfText(lable.getText(), Arial_Bold_20).getWidth() + 2;
					bound.width = ww;
					lable.setBounds(bound);
				}
			}
		}
		w = (int) getBoundOfText(lbFinalPath.getText(), Arial_Bold_20).getWidth() + 5;
		h = (int) getBoundOfText(lbFinalPath.getText(), Arial_Bold_20).getHeight() + 1;
		x = (screenWidth/2 - w)/2;
		y = (int) (0.15*screenHeight);
		
		lbFinalPath.setBounds(x,y,w,h);
		lbFinalPath.setFont(Arial_Bold_20);
		lbFinalPath.setVisible(true);
		//ADD TO VIEW
		pnlResult.add(lbFinalDistance);
		pnlResult.add(lbFinalPath);
	}
	
	public void setViewOfPaths() 
	{
		int maxLength = 0;
		
		Font Arial_Plain_20 = new Font("Arial", Font.PLAIN, 20);
		
		for (JLabel lblPath : lblPaths)
		{
			int length = (int) getBoundOfText(lblPath.getText(), Arial_Plain_20).getWidth() + 2;
			if (maxLength < length)
				maxLength = length;
		}
		int index = 0;
		for (JLabel lblPath : lblPaths)
		{
			index++;
			int w = maxLength;
			int h = (int) getBoundOfText(lblPath.getText(), Arial_Plain_20).getHeight() + 5;
			int x = (screenWidth/2 - w)/2;
			int y = (int) (lbBangChiDuong.getBounds().y + lbBangChiDuong.getHeight() + h*1.5*index);
			lblPath.setBounds(x, y, w, h);
			lblPath.setFont(Arial_Plain_20);
			lblPath.setVisible(true);
			pnlBangChiDuong.add(lblPath);
		}
	}
	
	private Rectangle2D getBoundOfText(String text, Font font)
	{
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		
		return font.getStringBounds(text, frc);
	}

	public void resetView()
	{
		//RESET HAFL-LEFT VIEW
		for (JLabel lblPath : lblPaths)
		{
			lblPath.setVisible(false);
		}
		pnlBangChiDuong.removeAll();
		pnlBangChiDuong.add(lbBangChiDuong);
		lbBangChiDuong.setVisible(true);
		
		lblPaths = null;
		lblPaths = new ArrayList<JLabel>();
		
		//RESET HALF-RIGHT VIEW
		lbFinalDistance.setVisible(false);
		lbFinalDistance = null;
		lbFinalDistance = new JLabel();
		
		lbFinalPath.setVisible(false);
		lbFinalPath = null;
		lbFinalPath = new JLabel();
		
		pnlResult.removeAll();
		pnlResult.add(pnlExit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnExit)
		{
			try {
				app.sendDatatView.controller.client.socket.close();
				app.stop();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}

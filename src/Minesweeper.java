import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

public class Minesweeper extends JFrame implements MouseListener{
	private final int COLUMN = 10;
	private final int ROW = 10;
	private final int N_BOMB = 2;
	private JButton restartButton; // tombol restart
	private Tile tiles[][];

	private boolean start = false;
	private boolean finish = false;
	private int tilesLeft = COLUMN * ROW - N_BOMB; // jumlah tile non bomb yang dapat di-klik
	private int minesLeft = N_BOMB;

	// GUI
	private JPanel fieldOfGrid; // JPanel untuk tile-tile

	public Minesweeper(){
		tiles = new Tile[COLUMN][ROW];
		Container contentPane = getContentPane();
		getContentPane().setLayout(new BorderLayout());
		this.setTitle("Minesweeper :O");

		// a panel with start button + put into GridLayout
		JPanel restartPane = new JPanel(new GridLayout(1,1,2,1));
			// restart button
			restartButton = new JButton(new ImageIcon("images/14.png"));
			restartButton.setSize(new Dimension(55,55));
			restartButton.setPressedIcon(new ImageIcon("images/15.png"));
			Color aColor = Color.decode("0xE4E4E4");
			restartButton.setBackground(aColor);
			restartButton.addMouseListener(this);
			restartPane.add(restartButton);

		contentPane.add(restartPane, BorderLayout.NORTH);

		// grid of minesweeper game
		buildTiles();
	}

	public void buildTiles(){ // "membangun" tiles
		super.setSize(402,402);
		
		fieldOfGrid = new JPanel(new GridLayout(ROW, COLUMN));

		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COLUMN; j++){
				tiles[i][j] = new Tile();
				tiles[i][j].setRowX(i);
				tiles[i][j].setColY(j);
				tiles[i][j].addMouseListener(this);
				fieldOfGrid.add(tiles[i][j]);
			}
		}
		getContentPane().add(fieldOfGrid, BorderLayout.CENTER);
	}
	
	public void randomGrid() { // random posisi bomb dan assign value jumlah bomb sekitar masing-masing tile
		ArrayList<Point> container = new ArrayList<Point>();
		Random rand = new Random();
		int tempX, tempY;
		Point p;

		// randoming index for bomb position
		for (int i = 0; i < N_BOMB; i++){
			tempX = rand.nextInt(10);
			tempY = rand.nextInt(10);
			p = new Point(tempX, tempY);
			while (container.contains(p)){
				tempX = rand.nextInt(10);
				tempY = rand.nextInt(10);
				p = new Point(tempX, tempY);
			}
			container.add(p);
		}

		// assign index to matrix
		for (int i = 0; i < container.size(); i++){
			tiles[(int) container.get(i).getX()][(int) container.get(i).getY()].setBombStatus(true);
		}

		container.clear();

		// assign nilai 0-8 di tile non bomb
		int counter;
		for (int i = 0; i < ROW; i++){
			for (int j = 0; j < COLUMN; j++){
				counter = 0;
				if (!tiles[i][j].getBombStatus()){
					if (j == 0){ // most top
						if (i == 0){ // most top most left
							if (tiles[i+1][j].getBombStatus()) { //kanannya
								counter++;
							}
							if (tiles[i+1][j+1].getBombStatus()) { //bawah diagonal kanan
								counter++;
							}
							if (tiles[i][j+1].getBombStatus()) { //bawahnya
								counter++;
							}
						}
						else if (i == (COLUMN-1)){ // most top most right
							if (tiles[i][j+1].getBombStatus()) { //bawahnya
								counter++;
							}
							if (tiles[i-1][j].getBombStatus()) { //kirinya
								counter++;
							}
							if (tiles[i-1][j+1].getBombStatus()) { //bawah diagonal kiri
								counter++;
							}
						}
						else{ // most top only
							if (tiles[i+1][j].getBombStatus()) { //kanannya
								counter++;
							}
							if (tiles[i+1][j+1].getBombStatus()) { //bawah diagonal kanan
								counter++;
							}
							if (tiles[i][j+1].getBombStatus()) { //bawahnya
								counter++;
							}
							if (tiles[i-1][j+1].getBombStatus()) { //bawah diagonal kiri
								counter++;
							}
							if (tiles[i-1][j].getBombStatus()) { //kirinya
								counter++;
							}
						}
					}
					else if (j == (ROW-1)){ // most bottom
						if (i == 0){ // most bottom most left
							if (tiles[i][j-1].getBombStatus()) { //atasnya
								counter++;
							}
							if (tiles[i+1][j-1].getBombStatus()) { //atas diagonal kanan
								counter++;
							}
							if (tiles[i+1][j].getBombStatus()) { //kanannya
								counter++;
							}
						}
						else if (i == (COLUMN-1)){ // most bottom most right
							if (tiles[i][j-1].getBombStatus()) { //atasnya
								counter++;
							}
							if (tiles[i-1][j-1].getBombStatus()) { //atas diagonal kiri
								counter++;
							}
							if (tiles[i-1][j].getBombStatus()) { //kirinya
								counter++;
							}
						}
						else{ // most bottom only
							if (tiles[i-1][j].getBombStatus()) { //kirinya
								counter++;
							}
							if (tiles[i-1][j-1].getBombStatus()) { //atas diagonal kiri
								counter++;
							}
							if (tiles[i][j-1].getBombStatus()) { //atasnya
								counter++;
							}
							if (tiles[i+1][j-1].getBombStatus()) { //atas diagonal kanan
								counter++;
							}
							if (tiles[i+1][j].getBombStatus()) { //kanannya
								counter++;
							}
						}
					}
					else if (i == (COLUMN - 1)){ // most right only
						if (tiles[i][j-1].getBombStatus()) { //atasnya
							counter++;
						}
						if (tiles[i][j+1].getBombStatus()) { //bawahnya
							counter++;
						}
						if (tiles[i-1][j].getBombStatus()) { //kirinya
							counter++;
						}
						if (tiles[i-1][j-1].getBombStatus()) { //atas diagonal kiri
							counter++;
						}
						if (tiles[i-1][j+1].getBombStatus()) { //bawah diagonal kiri
							counter++;
						}
					}
					else if (i == 0){ //most left only
						if (tiles[i][j-1].getBombStatus()) { //atasnya
							counter++;
						}
						if (tiles[i][j+1].getBombStatus()) { //bawahnya
							counter++;
						}
						if (tiles[i+1][j].getBombStatus()) { //kanannya
							counter++;
						}
						if (tiles[i+1][j-1].getBombStatus()) { //atas diagonal kanan
							counter++;
						}
						if (tiles[i+1][j+1].getBombStatus()) { //bawah diagonal kanan
							counter++;
						}
					}
					else { // center
						if (tiles[i][j-1].getBombStatus()) { //atasnya
							counter++;
						}
						if (tiles[i][j+1].getBombStatus()) { //bawahnya
							counter++;
						}
						if (tiles[i-1][j].getBombStatus()) { //kirinya
							counter++;
						}
						if (tiles[i+1][j].getBombStatus()) { //kanannya
							counter++;
						}
						if (tiles[i-1][j-1].getBombStatus()) { //atas diagonal kiri
							counter++;
						}
						if (tiles[i+1][j-1].getBombStatus()) { //atas diagonal kanan
							counter++;
						}
						if (tiles[i-1][j+1].getBombStatus()) { //bawah diagonal kiri
							counter++;
						}
						if (tiles[i+1][j+1].getBombStatus()) { //bawah diagonal kanan
							counter++;
						}
					}
					tiles[i][j].setValue(counter);
				}
			}
		}
	}

	public void restart(){ // me-restart game
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COLUMN; j++){
				tiles[i][j].reset();
				tiles[i][j].setRowX(i);
				tiles[i][j].setColY(j);
			}
		}
		tilesLeft = COLUMN * ROW - N_BOMB;
	}

	public void showZeroValue(Tile square){ // memunculkan jumlah bom di tile-tile tetangga sampai ketemu tile yang valuenya bukan 0
		int row = square.getRowX();
		int column = square.getColY();
		checkZeroValue(row,column);
	}

	public void checkZeroValue(int x, int y){ // rekursif dari memunculkan jumlah bom di tile-tile tetangga sampai ketemu tile yang valuenya bukan 0
		if((x < ROW) && (y < COLUMN) && (x >= 0) && (y >= 0) && (tiles[x][y].isEnabled()) && (tiles[x][y].getClose()) && (!tiles[x][y].getMark())){
			tiles[x][y].click();
			tilesLeft--;

			if(tiles[x][y].getValue() == 0 && !tiles[x][y].getBombStatus()){
				checkZeroValue(x-1, y-1);
				checkZeroValue(x, 	y-1);
				checkZeroValue(x+1, y-1);
				checkZeroValue(x-1, y);
				checkZeroValue(x+1, y);
				checkZeroValue(x-1, y+1);
				checkZeroValue(x, 	y+1);
				checkZeroValue(x+1, y+1);
			}
		}
	}

	public void endGame(boolean win){ // win == true keadaan menang, win == false keadaan kalah
		for (int i = 0; i < ROW; i++){
			for (int j = 0; j < COLUMN; j++){
				if (win){
					Color aColor = Color.decode("0xC6DBFA");
					tiles[i][j].setBackground(aColor);
					if(tiles[i][j].getBombStatus()){
						tiles[i][j].setIcon(tiles[i][j].getImage(13));
						tiles[i][j].setEnabled(false);
						tiles[i][j].setDisabledIcon(tiles[i][j].getImage(13));
					}
					else {
						tiles[i][j].setIcon(tiles[i][j].getImage(tiles[i][j].getValue()));
						tiles[i][j].setEnabled(false);
						tiles[i][j].setDisabledIcon(tiles[i][j].getImage(tiles[i][j].getValue()));
					}
				}
				else if (!win){
					Color aColor = Color.decode("0xFFBCBC");
					tiles[i][j].setBackground(aColor);
					if (tiles[i][j].getBombStatus() && !tiles[i][j].getClose() && tiles[i][j].getMark()){ // tilenya bomb, tertutup, dan ditandai sebagai bomb
						tiles[i][j].setIcon(tiles[i][j].getImage(13));
						tiles[i][j].setEnabled(false);
						tiles[i][j].setDisabledIcon(tiles[i][j].getImage(13));
					}
					else if(tiles[i][j].getBombStatus() && tiles[i][j].getClose() && !tiles[i][j].getMark()){ // tilenya bomb, tertutup, dan tidak ditandai sebagai bomb
						tiles[i][j].setIcon(tiles[i][j].getImage(9));
						tiles[i][j].setEnabled(false);
						tiles[i][j].setDisabledIcon(tiles[i][j].getImage(9));
					}
					else if(!tiles[i][j].getBombStatus() && !tiles[i][j].getClose() && tiles[i][j].getMark()){ // tilenya bukan bomb, tidak tertutup, dan ditandai sebagai bomb
						tiles[i][j].setIcon(tiles[i][j].getImage(12));
						tiles[i][j].setEnabled(false);
						tiles[i][j].setDisabledIcon(tiles[i][j].getImage(12));
					}
					else if(tiles[i][j].getBombStatus() && !tiles[i][j].getClose() && !tiles[i][j].getMark()){ // tilenya bomb, tidak tertutup, dan tidak ditandai sebagai bomb (ke-klik == kalah)
						tiles[i][j].setIcon(tiles[i][j].getImage(9));
						tiles[i][j].setEnabled(false);
						tiles[i][j].setDisabledIcon(tiles[i][j].getImage(9));
					}
					else if (!tiles[i][j].getBombStatus() && tiles[i][j].getClose()){ // tilenya bukan bomb dan tertutup
						tiles[i][j].setIcon(tiles[i][j].getImage(tiles[i][j].getValue()));
						tiles[i][j].setEnabled(false);
						tiles[i][j].setDisabledIcon(tiles[i][j].getImage(10));
					}					
				}
			}
		}
	}

	public void mousePressed(MouseEvent e){}

	public void mouseReleased(MouseEvent e){}

	public void mouseEntered(MouseEvent e){}

	public void mouseExited(MouseEvent e){}

	public void mouseClicked(MouseEvent e){
		int button = e.getButton();

		if(e.getSource() == (JButton)restartButton){ // jika tombol restart ditekan
			restart();
			start = false;
			finish = false;
			return;
		}

		Tile square = (Tile)e.getSource();
		if (button == 1 && !finish){ // left mouse button clicked
			if (!start){
				randomGrid();
				start = true;
			}
			
			if (square.getBombStatus() && !square.getMark() && square.getClose()){ // jika pengguna mengklik bomb
				endGame(false);
				JOptionPane.showMessageDialog(null,"Yah, kalah :(","End",JOptionPane.INFORMATION_MESSAGE);
				finish = true;
				return;
			}

			if(square.isEnabled() && !square.getMark() && square.getClose()){ // jika pengguna mengklik tile-tile yang masih tertutup dan belum ditandai
				if(square.getValue()==0){
					showZeroValue(square); // memunculkan jumlah bom di tile-tile tetangga sampai ketemu yang bukan 0
				}
				else if ((square.getValue() == 1) && (!square.getBombStatus()) && (square.getClose())) {
					square.setIcon(square.getImage(1));
					square.setClose(false);
					square.setEnabled(false);
					square.setDisabledIcon(square.getImage(1));
					tilesLeft--;
				}
				else if ((square.getValue() == 2) && (!square.getBombStatus()) && (square.getClose())) {
					square.setIcon(square.getImage(2));
					square.setClose(false);
					square.setEnabled(false);
					square.setDisabledIcon(square.getImage(2));
					tilesLeft--;
				}
				else if ((square.getValue() == 3) && (!square.getBombStatus()) && (square.getClose())) {
					square.setIcon(square.getImage(3));
					square.setClose(false);
					square.setEnabled(false);
					square.setDisabledIcon(square.getImage(3));
					tilesLeft--;
				}
				else if ((square.getValue() == 4) && (!square.getBombStatus()) && (square.getClose())) {
					square.setIcon(square.getImage(4));
					square.setClose(false);
					square.setEnabled(false);
					square.setDisabledIcon(square.getImage(4));
					tilesLeft--;
				}
				else if ((square.getValue() == 5) && (!square.getBombStatus()) && (square.getClose())) {
					square.setIcon(square.getImage(5));
					square.setClose(false);
					square.setEnabled(false);
					square.setDisabledIcon(square.getImage(5));
					tilesLeft--;
				}
				else if ((square.getValue() == 6) && (!square.getBombStatus()) && (square.getClose())) {
				square.setIcon(square.getImage(6));
					square.setClose(false);
					square.setEnabled(false);
					square.setDisabledIcon(square.getImage(6));
					tilesLeft--;
				}
				else if ((square.getValue() == 7) && (!square.getBombStatus()) && (square.getClose())) {
					square.setIcon(square.getImage(7));
					square.setClose(false);
					square.setEnabled(false);
					square.setDisabledIcon(square.getImage(7));
					tilesLeft--;
				}
				else if ((square.getValue() == 8) && (!square.getBombStatus()) && (square.getClose())) {
					square.setIcon(square.getImage(8));
					square.setClose(false);
					square.setEnabled(false);
					square.setDisabledIcon(square.getImage(8));
					tilesLeft--;
				}
			}
		}
		else if(button == 3){ // right clicked
			if (square.getMark()){
				square.setIcon(square.getImage(10));
				square.setMark(false);
				square.setClose(true);
				square.setEnabled(true);
			}
			else if (square.getClose() && square.isEnabled()){
				square.setIcon(square.getImage(11));
				square.setMark(true);
				square.setClose(false);
				square.setEnabled(false);
				square.setDisabledIcon(square.getImage(11));
			}
		}

		// win jika tilesLeft == 0
		if(tilesLeft == 0){
			endGame(true);
			JOptionPane.showMessageDialog(null,"Yay! Anda menang :D","End",JOptionPane.INFORMATION_MESSAGE);
			finish = true;
			return;
		}
	}

	public static void main (String[] args){ // main program
		JFrame.setDefaultLookAndFeelDecorated(true);
		Minesweeper gameOn = new Minesweeper();
		gameOn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		gameOn.setVisible(true);
		gameOn.setLocationRelativeTo(null);
	}
}

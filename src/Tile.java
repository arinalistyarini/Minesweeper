import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

public class Tile extends JButton{
	private boolean bombStatus; // true == bomb, false == bukan bomb
	private boolean close; // true == belum diklik (tertutup), false == sudah diklik (tidak tertutup)
	private boolean mark; // true == ditandai, false == tidak ditandai
	private int value; // jumlah bomb di sekitar tile tersebut
	private ImageIcon[] images; // kumpulan gambar
	private int rowX; //koordinat baris
	private int colY; //koordinat kolom
	private final int N_IMAGES = 16; // jumlah gambar

	public Tile(){
		this.bombStatus = false;
		this.close = true;
		this.mark = false;
		this.value = 0;
		this.rowX = 0;
		this.colY = 0;
		Color aColor = Color.decode("0xE4E4E4");
		this.setBackground(aColor);
		images = new ImageIcon[N_IMAGES];
		for (int i = 0; i < N_IMAGES; i++){
			images[i] = new ImageIcon("images/" + i + ".png");
		}
		this.setIcon(images[10]);
	}

	public ImageIcon getImage(int a){
		return images[a];
	}

	public void setBombStatus(boolean a){
		this.bombStatus = a;
	}

	public void setClose(boolean a){
		this.close = a;	
		if (!a &&!this.getBombStatus()){
			this.setEnabled(false);
			this.setDisabledIcon(images[this.getValue()]);
		}
		else if(!a &&this.getBombStatus()){
			this.setEnabled(false);
			this.setDisabledIcon(images[9]);
		}
	}

	public void setMark(boolean a){
		this.mark = a;
	}

	public void setValue(int a){
		this.value = a;
	}

	public void setRowX(int a){
		this.rowX = a;
	}

	public void setColY(int a){
		this.colY = a;
	}

	public boolean getBombStatus(){
		return bombStatus;
	}

	public boolean getClose(){
		return close;
	}

	public boolean getMark(){
		return mark;
	}

	public int getValue(){
		return value;
	}

	public int getRowX(){
		return rowX;
	}

	public int getColY(){
		return colY;
	}

	public void reset(){
		this.setBombStatus(false);
		this.setValue(0);
		this.setClose(true);
		this.setMark(false);
		this.setRowX(0);
		this.setColY(0);
		this.setEnabled(true);
		this.setIcon(images[10]);
		Color aColor = Color.decode("0xE4E4E4");
		this.setBackground(aColor);
	}

	public void click(){ 
		if (!this.getBombStatus()){
			switch(this.getValue()){
				case 0:
					this.setIcon(images[0]);
					this.setClose(false);
					break;
				case 1:
					this.setIcon(images[1]);
					this.setClose(false);
					break;
				case 2:
					this.setIcon(images[2]);
					this.setClose(false);
					break;
				case 3:
					this.setIcon(images[3]);
					this.setClose(false);
					break;
				case 4:
					this.setIcon(images[4]);
					this.setClose(false);
					break;
				case 5:
					this.setIcon(images[5]);
					this.setClose(false);
					break;
				case 6:
					this.setIcon(images[6]);
					this.setClose(false);
					break;
				case 7:
					this.setIcon(images[7]);
					this.setClose(false);
					break;
				case 8:
					this.setIcon(images[8]);
					this.setClose(false);
					break;
				default:
					break;
			}
		}
		
		if (!this.getClose()){
			this.setEnabled(false);
			if (!this.getBombStatus()){
				this.setDisabledIcon(images[this.getValue()]);
			}
		}
	}
}

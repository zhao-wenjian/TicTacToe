package trans.game.frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyTicTacToeScreen extends JFrame implements MouseListener{
	
	int windowWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	int windowHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	BufferedImage bgImage = null;
	//0:no chess, 1:circle, 2:cross
	int[][] allChess = new int[3][3];
	//save the index for chess
	int x = 0;
	int y = 0;
	//save the count of chess that aleardy exist
	int chessCount = 0;
	//confirm the turn for 'circle' and 'cross'
	Boolean isCircle = true;
	//confirm whether game finish
	Boolean gameContinue = true;
	Boolean manValid = true;
	//0:no game mode selected, 1:Man vs Man, 2:Man VS AI
	int gameMode = 0;
	int[][] sideList = {{0,1},{1,0},{1,2},{2,1}};
	int[][] roleList = {{0,0},{2,2},{0,2},{2,0}};
	Boolean AIFlag = false;
	Boolean checkLevel = false;
	
	/**
	 * Create the game wondow
	 */
	public MyTicTacToeScreen() {
		this.setTitle("Tic-Tac-Toe");
		this.setSize(900, 900);
		this.setLocation((windowWidth-900)/2, (windowHeight-900)/2);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		addMouseListener(this);
		try {
			bgImage = ImageIO.read(new File("./image/Background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Draw the game screen
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g.drawImage(bgImage, 0, 0, this);
		g.setFont(new Font("Black", Font.BOLD, 40));
		g.drawString("Your turn:" + (isCircle?"Circle":"Cross"), 50, 70);
		for(int i = 0;i < 4;i++) {
			g.drawLine(100, 100 + 220 * i, 760, 100 + 220 * i);
			g.drawLine(100 + 220 * i, 100, 100 + 220 * i, 760);
		}
		for(int i = 0;i < 3;i++) {
			for(int j = 0;j < 3;j++) {
				if(allChess[i][j] == 1) {
					int chessX = 100 + i*220;
					int chessY = 100 + j*220;
					g.setColor(Color.WHITE);
					g.fillOval(chessX + 20, chessY + 20, 180, 180);
					g.setColor(Color.RED);
					g2.setStroke(new BasicStroke(15f));
					g.drawOval(chessX + 20, chessY + 20, 180, 180);
				}
                if(allChess[i][j] == 2) {
                	int chessX = 100 + i*220;
					int chessY = 100 + j*220;
					g.drawLine(chessX+20, chessY+20, chessX+200, chessY+200);
					g.drawLine(chessX+20, chessY+200, chessX+200, chessY+20);
				}
			}
		}
	}
	
	/**
	 * Confirm the game result
	 * @return winFlag
	 */
	private Boolean CheckResult() {
		Boolean winFlag = false;
   		//save the count of chass in succession
		int successionCount = 1;
		int addChess = allChess[x][y];
		
		//Confirm the result
	    successionCount = this.checkCount(1, 0, addChess);
		if(successionCount >= 3) {
			winFlag = true;
		} else {
			successionCount = this.checkCount(0, 1, addChess);
			if(successionCount >= 3) {
				winFlag = true;
			} else {
				successionCount = this.checkCount(1, -1, addChess);
				if(successionCount >= 3) {
					winFlag = true;
				} else {
					successionCount = this.checkCount(1, 1, addChess);
					if(successionCount >= 3) {
						winFlag = true;
					}
				}
			}
		}
		return winFlag;
	}
	
	/**
	 * @param xChange
	 * @param yChange
	 * @param addChase
	 * @return the count of chass in succession
	 */
	private int checkCount(int xChange,int yChange,int addChess) {
		int successionCount = 1;
		int tempx = xChange;
		int tempy = yChange;
		while((x + xChange) >= 0 && (x + xChange) <= 2 && (y + yChange) >= 0 && (y + yChange) <= 2) {
			if(addChess != allChess[x + xChange][y + yChange] && allChess[x + xChange][y + yChange] != 0) {
				return 0;
			}
			if(addChess == allChess[x + xChange][y + yChange]) {
				successionCount++;
			}
			if(xChange != 0) {
				xChange++;
			}
			if(yChange != 0) {
				if(yChange > 0) {
					yChange++;
				} else {
					yChange--;
				}
			}
		}
		
		xChange = tempx;
		yChange = tempy;
		while((x - xChange) >= 0 && (x - xChange) <= 2 && (y - yChange) >= 0 && (y - yChange) <= 2) {
			if(addChess != allChess[x - xChange][y - yChange] && allChess[x - xChange][y - yChange] != 0) {
				return 0;
			}
			if(addChess == allChess[x - xChange][y - yChange]) {
				successionCount++;
			}
			if(xChange != 0) {
				xChange++;
			}
			if(yChange != 0) {
				if(yChange > 0) {
					yChange++;
				} else {
					yChange--;
				}
			}
		}
		return successionCount;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		if(gameMode !=0) {
			if(gameContinue) {
				getLatestResult();
				if(gameMode == 2 && manValid) {
					AILogic();
					getLatestResult();
				}
			}
		} else if(x >= 100 && x <= 760 && y >= 100 && y <= 760) {
			JOptionPane.showMessageDialog(this, "Please select the game mode");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getX() >= 200 && e.getX() <= 350 && e.getY() >= 800 && e.getY() <= 860) {
			JOptionPane.showMessageDialog(this, "Man VS Man,Game start");
			gameMode = 1;
			gameContinue = true;
			isCircle = true;
			allChess = new int[3][3];
		}
		if(e.getX() >= 450 && e.getX() <= 600 && e.getY() >= 800 && e.getY() <= 860) {
			JOptionPane.showMessageDialog(this, "Man VS AI,Game start");
			gameMode = 2;
			gameContinue = true;
			isCircle = true;
			chessCount = 0;
			allChess = new int[3][3];
		}
	}
	
	public void getLatestResult() {
		if (x >= 100 && x < 760 && y >= 100 && y < 760) {
			x = (x - 100) / 220;
			y = (y - 100) / 220;
			if(allChess[x][y] == 0) {
				addChese(x,y);
				//confirm the game result
				Boolean winFlag = false;
				if(chessCount >= 5) {
					winFlag = this.CheckResult();
				}
				this.repaint();
				if(winFlag) {
					JOptionPane.showMessageDialog(this, "Finished!  " + (allChess[x][y]==1?"Circle win!":"Cross win!"));
					gameContinue = false;
					gameMode = 0;
					chessCount = 0;
				}
				if(chessCount == 9) {
					JOptionPane.showMessageDialog(this, "Draw");
				}
				manValid = true;
			} else {
				JOptionPane.showMessageDialog(this, "A chass already exists,please select again");
				manValid = false;
			}
		}
	}
	
	public void AILogic() {
		checkLevel = checkLevel();
		if(!checkLevel) {
			for(int i = 0;i < 3;i++) {
				for(int j = 0;j < 3;j++) {
					if(allChess[i][j] == 0) {
						x = i*220 + 100;
						y = j*220 + 100;
						return;
					} 
				}
			}
		}
	}
	
	public Boolean checkLevel() {
		int oCountMax = 0;
		int sideFlag = 0;
		AIFlag = true;
		for(int i = 0;i<sideList.length;i++) {
			if (sideList[i][0] == x && sideList[i][1] == y) {
				sideFlag++;
			}
		}
		oCountMax = this.checkCount(1, 0, allChess[x][y]);
		
		if(oCountMax == 2) {
			setChess(1, 0);
			return true;
		}
		oCountMax = this.checkCount(0, 1, allChess[x][y]);
		if(oCountMax == 2) {
			setChess(0, 1);
			return true;
		}
		if(sideFlag == 0) {
			oCountMax = this.checkCount(1, -1, allChess[x][y]);
			if(oCountMax == 2) {
				setChess(1, -1);
				return true;
			}
			oCountMax = this.checkCount(1, 1, allChess[x][y]);
			if(oCountMax == 2) {
				setChess(1, 1);
				return true;
			}
		} else {
			sideFlag = 0;
		}
		return checkChessOne();
	}
	
	public void setChess(int xChange,int yChange) {
		int tempx = xChange;
		int tempy = yChange;
		while((x + xChange) >= 0 && (x + xChange) <= 2 && (y + yChange) >= 0 && (y + yChange) <= 2) {
			if(allChess[x + xChange][y + yChange] == 0) {
				addChese(x + xChange,y + yChange);
			}
			if(xChange != 0) {
				xChange++;
			}
			if(yChange != 0) {
				if(yChange > 0) {
					yChange++;
				} else {
					yChange--;
				}
			}
		}
		
		xChange = tempx;
		yChange = tempy;
		
		while((x - xChange) >= 0 && (x - xChange) <= 2 && (y - yChange) >= 0 && (y - yChange) <= 2) {
			if(allChess[x - xChange][y - yChange] == 0) {
				addChese(x - xChange,y - yChange);
			}
			if(xChange != 0) {
				xChange++;
			}
			if(yChange != 0) {
				if(yChange > 0) {
					yChange++;
				} else {
					yChange--;
				}
			}
		}
	}
	
	public Boolean checkChessOne() {
		if(allChess[1][1] == 0) {
			addChese(1,1);
			return true;
		} else {
			for(int i = 0;i < roleList.length;i++) {
				if(allChess[roleList[i][0]][roleList[i][1]] == 0) {
					addChese(roleList[i][0],roleList[i][1]);
					return true;
				}
			}
		}
		return false;
	}
	
	private void addChese(int a,int b) {
		if(isCircle) {
			allChess[a][b] = 1;
			isCircle = false;
		} else {
			allChess[a][b] = 2;
			isCircle = true;
		}
		chessCount++;
	}

}

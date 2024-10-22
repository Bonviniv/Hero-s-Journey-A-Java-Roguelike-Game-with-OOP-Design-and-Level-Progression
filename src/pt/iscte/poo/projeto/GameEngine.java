package pt.iscte.poo.projeto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 11;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE = null;
	private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

	private Hero hero;
	private ArrayList<GameElement> bottomBar = new ArrayList<>();
	private ArrayList<GameElement> hpBar = new ArrayList<>();
	private ArrayList<GameElement> objList = new ArrayList<GameElement>();
	private ArrayList<ArrayList<GameElement>> roomList = new ArrayList<ArrayList<GameElement>>();
	private int turns;

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}

	private GameEngine() {
		gui.registerObserver(this);
		gui.setSize(GRID_WIDTH, GRID_HEIGHT);
		gui.go();
	}

	public void start() {
		createList();
		loadObjects();
		gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
		gui.update();
	}

	public int getTurn() {
		return this.turns;
	}

	public Hero getHero() {
		return this.hero;
	}

	private void fileReader(String file) {
		try {
			ArrayList<GameElement> aux = new ArrayList<GameElement>();
			Scanner scanner = new Scanner(new File(file));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				for (int y = 0; y < 10; y++) {
					for (int x = 0; x < 10; x++) {
						if (line.charAt(x) == '#') {
							aux.add(new Wall(new Point2D(x, y)));
						} else {
							aux.add(new Floor(new Point2D(x, y)));
						}
					}
					line = scanner.nextLine();
				}

				while (scanner.hasNextLine()) {
					line = scanner.nextLine();
					String[] sptLine = line.split(",");
					GameElement e = GameElement.createGameElement(sptLine[0], 
							new Point2D(Integer.parseInt(sptLine[1]), Integer.parseInt(sptLine[2])), sptLine);
					aux.add(e);
				}
			}
			scanner.close();
			roomList.add(aux);
		} catch (FileNotFoundException e) {
			System.err.println("ficheiro nao encontrado");
		}
	}

	private void createList() {
		hero = new Hero(new Point2D(4, 4));
		Boolean check = true;
		int i = 0;
		List<String> rooms = new ArrayList<>();
		while (check) {
			String ex = new String("rooms/room" + i + ".txt");
			File dir = new File(ex);
			check = dir.isFile();
			if (!check) {
				i--;
				break;
			}
			rooms.add(ex);
			i++;
		}
		for (int n = 0; n <= i; n++) {
			fileReader(rooms.get(n));
		}
		for (int n = 0; n < 10; n++) {
			GameElement e = new Black(new Point2D(n, 10));
			bottomBar.add(e);
		}
		for (int n = 0; n < 5; n++) {
			HealthBar e = new HealthBar(new Point2D(n, 10));
			e.setSegment(4 - n);
			hpBar.add(e);
		}
	}

	private void loadObjects() {
		objList = roomList.get(hero.getRoom());
		gui.addImage(hero);
		for (GameElement e : hpBar) {
			gui.addImage(e);
		}
		for (GameElement e : bottomBar) {
			gui.addImage(e);
		}
		for (GameElement e : hero.getInventory()) {
			gui.addImage(e);
		}
		for (GameElement e : objList) {
			gui.addImage(e);
		}

	}

	public GameElement getObjectUpper(Point2D pos) {
		for (GameElement e : objList) {
			if (e.getPosition().equals(pos) && e.getLayer() == 2) {
				return e;
			}
		}
		if (hero.getPosition().equals(pos)) {
			return hero;
		}
		return null;
	}

	public GameElement getObjectLower(Point2D pos) {
		for (GameElement e : objList) {
			if (e.getPosition().equals(pos) && e.getLayer() == 1) {
				return e;
			}
		}
		return null;
	}

	@Override
	public void update(Observed source) {

		int key = ((ImageMatrixGUI) source).keyPressed();

		if (Direction.isDirection(key)) {
			turns++;
			Direction d = Direction.directionFor(key);
			hero.setDirection(d);
			hero.move();
			Iterator<GameElement> itr = objList.iterator();
			while (itr.hasNext()) {
				GameElement e = itr.next();
				if (e instanceof Movable) {
					Movable m = (Movable) e;
					m.move();
				}
				if (e instanceof Combatant) {
					Combatant c = (Combatant) e;
					if (c.getHp() <= 0) {
						gui.removeImage(c);
						itr.remove();
					}
				}
				if (e instanceof Item) {
					Item i = (Item) e;
					if (i.inInventory()) {
						gui.removeImage(e);
						itr.remove();
					}
				}
			}

			if (hero.isPoisoned()) {
				hero.setHp(hero.getHp() - 1);
			}

			if (hero.getHp() <= 0) {
				gui.removeImage(hero);
				System.out.println("You died :(");
				System.exit(1);
			}
			
		} else if (key >= KeyEvent.VK_1 && key <= KeyEvent.VK_3) {
		
			if (!(key - 48 > hero.getInventory().size())) {
				GameElement e = hero.getInventory().get(key - 49);
				e.setPosition(hero.getPosition());
				Item k = (Item) e;
				k.droped();
				roomList.get(hero.getRoom()).add(k);
				hero.dropItem(key - 49);
			}
		} else if (key == KeyEvent.VK_Q || key == KeyEvent.VK_W || key == KeyEvent.VK_E) {
			
			if (key == 81) {
				key = 0;
			} else if (key == 87) {
				key = 1;
			} else if (key == 69) {
				key = 2;
			}
			
			if (!(key >= hero.getInventory().size())) {
				if (hero.getInventory().get(key) instanceof HealingPotion) {
					hero.dropItem(key);
					hero.setHp(hero.getHp() + 3);
					if (hero.getHp() > 10) {
						hero.setHp(10);
					}
				}
			}
		}
		boolean checkMid = true;
		int n = hero.getHp() / 2;
	
		if (hero.getHp() % 2 == 0) {
			for (GameElement e : hpBar) {
				HealthBar h = (HealthBar) e;
				if (h.getSegment() > n) {
					h.setRed();
				} else {
					h.setGreen();
				}
			}
		} else {
			n++;
			for (GameElement e : hpBar) {
				HealthBar h = (HealthBar) e;
				if (h.getSegment() > n) {
					h.setRed();
				} else {
					if (checkMid) {
						h.setMid();
						checkMid = false;
					} else {
						h.setGreen();
					}
				}
			}
		}
			gui.clearImages();
			loadObjects();
			gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
			gui.update();

		
	}
}

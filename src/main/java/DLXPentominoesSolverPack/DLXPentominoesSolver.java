package DLXPentominoesSolverPack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import TetrisLike3DSolver.LayeredContainer;
import TetrisLike3DSolver.LayersPacker;
import TetrisLike3DSolver.NullPentomino;
import TetrisLike3DSolver.Pentomino;
import TetrisLike3DSolver.SolutionLayer;
import java.util.Map;

public class DLXPentominoesSolver {
	//TODO: hardcoded.
	private String puzzleString = "\r\n" + 
			"Square\r\n" + 
			"Board ( (4,31))\r\n" + 
			"# 3 pieces\r\n";
	private String mode = "mode UseMost ";
	private LayersPacker layersPacker;
	//TODO: hardcoded.
	private double containerHeightState = 4;
	private double layersHeight = 0.5;
	//Each one is 5 blocks, and our container is 32*5 (16*2.5 doubled to make it integer).
	//Hence, it can at most fit 32 pentominoes per layer. We'll use this maximum to get the most valuable pentoes prioritized.
	private int maxAmountPossible = 32;
	
	//The complete list of pentos we received. the preferred list will get the items from here.
	private List<Pentomino> pentominoesToPlace = new ArrayList<Pentomino>();
	
	private int LPentos = 0;
	private int PPentos = 0;
	private int TPentos = 0;
	//For now, the value will be one for each type of pento. The ideal would be to have an individual value.
	//TODO: check this.
	private double LValue = 0;
	private double PValue = 0;
	private double TValue = 0;
	
	//True when we've packed every pentomino or the container can't fit any more layers.
	private boolean allDone = false;
	
	
	public DLXPentominoesSolver() {
		//TODO: hardcoded.
		this.layersPacker = new LayersPacker(8, 4, 32, 0.5);
	}
	public LayeredContainer Pack(List<Pentomino> Pentominoes){
		try {
			solve(Pentominoes);
		} catch (IOException e) {
			e.printStackTrace();
			return container;
		}
		return container;
	}
	//The most valuable pentominoes.
	private List<Pentomino> preferredList = new ArrayList<Pentomino>();
	private void solve(List<Pentomino> Pentominoes) throws IOException {
		pentominoesToPlace = Pentominoes;
		pentominoesToPlace.sort((p1, p2) -> Double.compare(p2.getValue(), p1.getValue()));
		fillPreferredList();
		processPentos();
		parseToPuzzleString();
		computeLayeredSolution();
		
	}
	private void fillPreferredList(){
		if(preferredList.size() > 0 && preferredList.stream().mapToInt(p -> p.getQty()).sum() >= maxAmountPossible)
			return;
		if(pentominoesToPlace.size() == 0 && preferredList.size() == 0) {
			allDone = true;
			return;
		}
		if(pentominoesToPlace.size() == 1 && preferredList.size() == 0) {
			preferredList = pentominoesToPlace;
			return;
		}
		int i = 0;
		//preferredList.add(pentominoesToPlace.get(i)); 
		//pentominoesToPlace.remove(i);
		while(preferredList.stream().mapToInt(p -> p.getQty()).sum() < maxAmountPossible && pentominoesToPlace.size() > i) {
			preferredList.add(pentominoesToPlace.get(i));
			pentominoesToPlace.remove(i);
			i++;
		}
	}
	private void processPentos() {
		LPentos = PPentos = TPentos = 0;
		for(Pentomino p : preferredList) {
			switch(p.getTypeID()) {
			case 8: LPentos += p.getQty(); LValue = p.getValue(); break;
			case 9: PPentos += p.getQty(); PValue = p.getValue(); break; 
			case 3: TPentos += p.getQty(); TValue = p.getValue(); break;
			}
		}
	}
	Puzzle puzzle;
	private boolean stillRunning = false;
	private void computeLayeredSolution() throws IOException {
		do {
			if(stillRunning)
				continue;
			puzzle = Puzzle.parse(new StringReader(puzzleString));
			puzzle.setActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent ae) {solutionFound(ae);}
			});
			Thread puzThread = new Thread(puzzle);
			stillRunning = true;
			try {
				puzThread.run();
				puzThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			};
		}while(!allDone);
		 container = layersPacker.fitLayersInContainer(this.layers);
		 container.setValue(containerValue);
	}
	ArrayList<SolutionLayer> layers = new ArrayList<SolutionLayer>();
	LayeredContainer container;
	private void solutionFound(ActionEvent ae) {
		// ae.getID ==   0=found solution, 1=stop solve, 2=start solve
		if(ae.getID() == 1 || ae.getID() == 2)
			return;
		Puzzle puz = (Puzzle) ae.getSource();
		Board board = new Board(null);
		DLXPentominoesSolverPack.Solution sol = puz.getSolution(0);
		board.setBoardContents(sol);
		if(board.getBlockArray().length == 0)
			return;
		addUsedPentos(sol);
		//TODO: hardcoded size.
		int[][] solBoard = new int[5][32];
		Map<ICoord, Integer> map = (Map<ICoord, Integer>) board.getBlockList();
		int layerItemsCount = 0;  
		for (Map.Entry<ICoord, Integer> k : map.entrySet()) {
			solBoard[k.getKey().x()][k.getKey().y()] = k.getValue();
			if(k.getValue() > 0) {
				//This will add an "item" for each non-empty block in the board.
				layerItemsCount++;
			}
		}
		SolutionLayer layer = new SolutionLayer(0.5, solBoard);
		layer.setItemsCount(layerItemsCount/5);
		addLayer(layer);
		puzzle.stopSolve();
		stillRunning = false;
		fillPreferredList();
		processPentos();
		getNewPuzzleString();
		if(getRemaining() == 0 || containerHeightState < layersHeight) {
			allDone = true;
		}
	}
	private void getNewPuzzleString() {
		puzzleString = "\r\n" + 
				"Square\r\n" + 
				"Board ( (4,31))\r\n" + 
				"# 3 pieces\r\n" + 
				LPentosString() + 
				PPentosString() + 
				TPentosString() + 
				mode;
	}
	private String LPentosString() {
		return LPentos > 0 ? "tile flip "+LPentos+" ((0,0)(0,1)(0,2)(0,3)(1,0)) __polyID: 8\r\n" : "";
	}
	private String PPentosString() {
		return PPentos > 0 ? "tile flip "+PPentos+" ((0,0)(0,1)(0,2)(1,0)(1,1)) __polyID: 9\r\n" : "";
	}
	private String TPentosString() {
		return TPentos > 0 ? "tile flip "+TPentos+" ((0,0)(1,0)(1,1)(1,2)(2,0)) __polyID: 3\r\n" : "";
	}
	private void addUsedPentos(DLXPentominoesSolverPack.Solution sol) {
		int placedPentos = sol.getNumPoly();
		for(int i = 0; i < placedPentos; i++) {
			int used = sol.getPoly(i).getUsed();
			if(used == 0)
				continue;
			switch(sol.getPoly(i).getPolyID()) {
			case 8: handleLPento(used); break;
			case 9: handlePPento(used); break;
			case 3: handleTPento(used); break;
			}
		}
	}
	
	//TODO: fix globals. They'll be problematic for PackAll.
	double containerValue = 0;
	//TODO: generalize.
	private void handleLPento(int used) {
		for(int i = 0; i < used; i++) {
			LPentos--;
			containerValue += LValue;
		}
		Pentomino pentoFromPreferredList = preferredList.stream().filter(p -> p.getTypeChar() == 'L').findFirst().orElse(new NullPentomino());
		if(pentoFromPreferredList.getRepresentation() != null) {
			if(LPentos == 0)
				preferredList.remove(pentoFromPreferredList);
			else
				pentoFromPreferredList.setQty(LPentos);
		}
	}
	private void handlePPento(int used) {
		for(int i = 0; i < used; i++) {
			PPentos--;
			containerValue += PValue;
		}
		Pentomino pentoFromPreferredList = preferredList.stream().filter(p -> p.getTypeChar() == 'P').findFirst().orElse(new NullPentomino());
		if(pentoFromPreferredList.getRepresentation() != null) {
			if(PPentos == 0)
				preferredList.remove(pentoFromPreferredList);
			else
				pentoFromPreferredList.setQty(PPentos);
		}
	}
	private int getRemaining() {
		return LPentos + PPentos + TPentos;
	}
	private void handleTPento(int used) {
		for(int i = 0; i < used; i++) {
			TPentos--;
			containerValue += TValue;
		}
		Pentomino pentoFromPreferredList = preferredList.stream().filter(p -> p.getTypeChar() == 'T').findFirst().orElse(new NullPentomino());
		if(pentoFromPreferredList.getRepresentation() != null) {
			if(TPentos == 0)
				preferredList.remove(pentoFromPreferredList);
			else
				pentoFromPreferredList.setQty(TPentos);
		}
	}
	private void addLayer(SolutionLayer layer) {
		containerHeightState -= layersHeight;
		layers.add(layer);
	}
	
	
	
	
	
	
	
	
	//Parsing.
	private String parseToPuzzleString() {
		addPentominoesToString();
		puzzleString += mode;
		return puzzleString;
	}
	private void addPentominoesToString() {
		for(Pentomino p : preferredList) {
			switch(p.getTypeID()) {
			case 8: puzzleString += p.getPolyRepresentation(LPentos)+" "; break;
			case 9: puzzleString += p.getPolyRepresentation(PPentos)+" "; break;
			case 3: puzzleString += p.getPolyRepresentation(TPentos)+" "; break;
			}
			
		}
	}
}

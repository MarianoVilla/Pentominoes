package DLXPentominoesSolverPack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import TetrisLike3DSolver.LPentomino;
import TetrisLike3DSolver.LayeredContainer;
import TetrisLike3DSolver.LayersPacker;
import TetrisLike3DSolver.NullPentomino;
import TetrisLike3DSolver.PPentomino;
import TetrisLike3DSolver.Pentomino;
import TetrisLike3DSolver.SolutionLayer;
import TetrisLike3DSolver.TPentomino;

import java.util.Map;

public class DLXPentominoesSolver {
	//TODO: hardcoded.
	private String puzzleString = "\r\n" + 
			"Square\r\n" + 
			"Board ( (4,31))\r\n" + 
			"# 3 pieces\r\n";
	private String mode = "mode UseMost ";
	private LayersPacker layersPacker;
	
	//The complete list of pentos we received. the preferred list will get the items from here.
	private List<Pentomino> pentominoesToPlace = new ArrayList<Pentomino>();
	
	private int LPentos = 0;
	private int PPentos = 0;
	private int TPentos = 0;
	//For now, the value will be one for each type of pento. Therefore, we assume a 1-1 relation between the pentominoes and their values.
	//TODO: check this.
	private double LValue = 0;
	private double PValue = 0;
	private double TValue = 0;
	
	//True when we've packed every pentomino or the container can't fit any more layers.
	private boolean allDone = false;
	
	double containerWidth = 8;
	double containerHeight = 4;
	double containerLength = 32;
	//Used to keep track of the remaining container height as we add layers to it.
	private double containerHeightState = 4;
	//Each one is 5 blocks, and our container is 32*5 (16*2.5 doubled to make it integer).
	//Hence, it can at most fit 32 pentominoes per layer. We'll use this maximum to get the most valuable pentoes prioritized.
	private int maxAmountPossible = 32;
	private double layersHeight = 0.5;
	
	/**
	 * This constructor will use the given container dimensions. Still, the layersHeight is still hardcoded to .5.
	 * @param containerHeight Will be multiplied by 2!
	 * @param containerWidth Will be multiplied by 2!
	 * @param containerLength Will be multiplied by 2!
	 */
	public DLXPentominoesSolver(double containerHeight, double containerWidth, double containerLength) {
		this.containerHeight = containerHeight * 2;
		this.containerWidth = containerWidth * 2;
		this.containerLength = containerLength * 2;
		this.layersPacker = new LayersPacker(this.containerWidth, this.containerHeight, this.containerLength, layersHeight);
	}
	/**
	 * This constructor will use the default container size to work the solutions.
	 */
	public DLXPentominoesSolver() {
		this.layersPacker = new LayersPacker(this.containerWidth, this.containerHeight, this.containerLength, layersHeight);
	}
	public LayeredContainer Pack(List<Pentomino> Pentominoes){
		try {
			solve(Pentominoes);
		} catch (IOException e) {
			e.printStackTrace();
			cleanUp();
			return container;
		}
		cleanUp();
		return container;
	}
	public ArrayList<LayeredContainer>PackAll(List<Pentomino> Pentominoes){ return null; }
	//
	/*
	 * private ArrayList<LayeredContainer> PackAll(List<Pentomino> Pentominoes){
	 * ArrayList<LayeredContainer> containers = new ArrayList<LayeredContainer>();
	 * int totalAmount = Pentominoes.stream().mapToInt(p -> p.getQty()).sum();
	 * pentominoesToPlace = Pentominoes; pentominoesToPlace.sort((p1, p2) ->
	 * Double.compare(p2.getValue(), p1.getValue())); while(totalAmount > 0) {
	 * LayeredContainer container = Pack(pentominoesToPlace); totalAmount -=
	 * container.getPackedItemsCount(); containers.add(Pack(pentominoesToPlace)); }
	 * return containers; }
	 */
	private void cleanUp() {
		resetPentoCounters();
		containerValue = 0;
	}
	private List<Pentomino> preferredList = new ArrayList<Pentomino>();
	private void solve(List<Pentomino> Pentominoes) throws IOException {
		pentominoesToPlace = Pentominoes;
		pentominoesToPlace.sort((p1, p2) -> Double.compare(p2.getValue(), p1.getValue()));
		fillPreferredList();
		processPentos();
		parseToPuzzleString();
		computeLayeredSolution();
	}
	private void processPentos() {
		resetPentoCounters();
		for(Pentomino p : preferredList) {
			switch(p.getTypeID()) {
			case 8: LPentos += p.getQty(); LValue = p.getValue(); break;
			case 9: PPentos += p.getQty(); PValue = p.getValue(); break; 
			case 3: TPentos += p.getQty(); TValue = p.getValue(); break;
			}
		}
	}
	private void resetPentoCounters() {
		LPentos = PPentos = TPentos = 0;
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
		return LPentos > 0 ? LPentomino.getStaticPolyRepresentation(LPentos) : "";
	}
	private String PPentosString() {
		return PPentos > 0 ? PPentomino.getStaticPolyRepresentation(PPentos) : "";
	}
	private String TPentosString() {
		return TPentos > 0 ? TPentomino.getStaticPolyRepresentation(TPentos) : "";
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
	
	//Preferred List handling.
	private void fillPreferredList(){
		if(preferredList.size() > 0 && preferredList.stream().mapToInt(p -> p.getQty()).sum() >= maxAmountPossible)
			return;
		if(noMorePentos()) {
			allDone = true;
			return;
		}
		if(onlyOne()) {
			preferredList = pentominoesToPlace;
			return;
		}
		transferPentosToPreferredList();
	}
	private boolean noMorePentos() {
		return pentominoesToPlace.size() == 0 && preferredList.size() == 0;
	}
	private boolean onlyOne() {
		return pentominoesToPlace.size() == 1 && preferredList.size() == 0;
	}
	private void transferPentosToPreferredList() {
		int i = 0;
		while(preferredList.stream().mapToInt(p -> p.getQty()).sum() < maxAmountPossible && pentominoesToPlace.size() > i) {
			preferredList.add(pentominoesToPlace.get(i));
			pentominoesToPlace.remove(i);
			i++;
		}
	}
	
	double containerValue = 0;
	private void handleLPento(int used) {
		for(int i = 0; i < used; i++) {
			LPentos--;
			containerValue += LValue;
		}
		updatePreferredList('L', LPentos);
	}
	
	private void handlePPento(int used) {
		for(int i = 0; i < used; i++) {
			PPentos--;
			containerValue += PValue;
		}
		updatePreferredList('P', PPentos);
	}
	private void handleTPento(int used) {
		for(int i = 0; i < used; i++) {
			TPentos--;
			containerValue += TValue;
		}
		updatePreferredList('T', TPentos);
	}
	private void updatePreferredList(Character pentoCharType, int newQty) {
		Pentomino pentoFromPreferredList = preferredList.stream().filter(p -> p.getTypeChar() == pentoCharType)
				.findFirst().orElse(new NullPentomino());
		if (pentoFromPreferredList instanceof NullPentomino)
			return;
		if (newQty == 0)
			preferredList.remove(pentoFromPreferredList);
		else
			pentoFromPreferredList.setQty(newQty);
	}
	private int getRemaining() {
		return LPentos + PPentos + TPentos;
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

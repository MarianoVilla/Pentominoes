package TestHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Entities.ClassAItem;
import Entities.ClassBItem;
import Entities.ClassCItem;
import Entities.Item;
import Services.ItemsFactory;
import TetrisLike3DSolver.*;

/**
 * A repository to make tests cleaner.
 *
 */
public class TestRepo {
	
	public static ArrayList<Pentomino> getPentos(int howMany, Character typeChar){
		return PentominoesDefaultFactory.CreateMany(howMany, typeChar);
	}
	public static ArrayList<Pentomino> getPentos(int howMany, Character typeChar, double value){
		return PentominoesDefaultFactory.CreateMany(howMany, typeChar, value);
	}
	public static ArrayList<Pentomino> getRandomPentos(int howMany){
		ArrayList<Pentomino> pentos = new ArrayList<Pentomino>();
		for(int i = 0; i < howMany; i++) {
			pentos.add(PentominoesDefaultFactory.Create(getRandomPentoChar()));
		}
		return pentos;
	}
	public static ArrayList<Pentomino> getRandomPentos(int howMany, double value){
		ArrayList<Pentomino> pentos = new ArrayList<Pentomino>();
		for(int i = 0; i < howMany; i++) {
			pentos.add(PentominoesDefaultFactory.Create(getRandomPentoChar(), value));
		}
		return pentos;
	}
	public static ArrayList<Item> getRandomItems(int howMany){
		ArrayList<Item> items = new ArrayList<Item>();
		for(int i = 0; i < howMany; i++) {
			items.add(ItemsFactory.Create(getRandomItemClass(), 1, new Random().nextDouble(), i));
		}
		return items;
	}
	public static ArrayList<Item> getThreeStandardItems(){
		return new ArrayList<Item>() {{
			add(new ClassAItem(1, 1, 1));
			add(new ClassBItem(1, 1, 3));
			add(new ClassCItem(1, 1, 2));
		}};
	}
	private static String getRandomItemClass() {
		List<String> classNames = Arrays.asList(new ClassAItem(0,0,0).toString(), new ClassBItem(0,0,0).toString(), new ClassCItem(0,0,0).toString());
		return classNames.get(new Random().nextInt(3));
	}
	private static Character getRandomPentoChar() {
	    String alphabet = "LPT";
        return alphabet.charAt(new Random().nextInt(alphabet.length()));
	}
	
}

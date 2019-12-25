package GeneralPurposeHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import TetrisLike3DSolver.Pentomino;

public class ListUtils {
	public static <T> List<List<T>> recursivePermutations(List<T> list) {

	    if (list.size() == 0) {
	        List<List<T>> result = new ArrayList<List<T>>();
	        result.add(new ArrayList<T>());
	        return result;
	    }

	    List<List<T>> returnMe = new ArrayList<List<T>>();

	    T firstElement = list.remove(0);

	    List<List<T>> recursiveReturn = recursivePermutations(list);
	    for (List<T> li : recursiveReturn) {

	        for (int index = 0; index <= li.size(); index++) {
	            List<T> temp = new ArrayList<T>(li);
	            temp.add(index, firstElement);
	            returnMe.add(temp);
	        }

	    }
	    return returnMe;
	}
	public static <T> List<List<T>> nonRecursivePermutations(List<T> es){

		  List<List<T>> permutations = new ArrayList<List<T>>();

		  if(es.isEmpty()){
		    return permutations;
		  }

		  // We add the first element
		  permutations.add(new ArrayList<T>(Arrays.asList(es.get(0))));

		  // Then, for all elements e in es (except from the first)
		  for (int i = 1, len = es.size(); i < len; i++) {
		    T e = es.get(i);

		    // We take remove each list l from 'permutations'
		    for (int j = permutations.size() - 1; j >= 0; j--) {
		      List<T> l = permutations.remove(j);

		      // And adds a copy of l, with e inserted at index k for each position k in l
		      for (int k = l.size(); k >= 0; k--) {
		        ArrayList<T> ts2 = new ArrayList<>(l);
		        ts2.add(k, e);
		        permutations.add(ts2);
		      }
		    }
		  }
		  return permutations;
		}
}

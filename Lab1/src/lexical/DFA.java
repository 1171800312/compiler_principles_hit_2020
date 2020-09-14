package lexical;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFA {
	Set<Character> charSet = new HashSet<Character>();	
	List<Map<Character, Integer>> table = new ArrayList<Map<Character,Integer>>();

	public DFA() {
	}
	public int transStates(int state,char ch) {
		if(!table.get(state).containsKey(ch)) {
			return -1;
		}
		return table.get(state).get(ch);
	}
}

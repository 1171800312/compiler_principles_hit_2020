package lexical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Nfa2Dfa {
	
	public static DFA trans(NFA nfa) {
		DFA dfa = new DFA();
		dfa.charSet = nfa.charSet;
		if(dfa.charSet.contains('\0')) {
			dfa.charSet.remove('\0');
		}
		
		Set<Integer> startState = nfa.getClosure(0);
		List<Map<Character, Set<Integer>>> transTable = new ArrayList<>();
		List<Set<Integer>> newStates = new ArrayList<Set<Integer>>();
		Queue<Set<Integer>> queue = new LinkedList<Set<Integer>>();	//杩樻湭鏍囪鐨勯泦鍚�
		Set<Set<Integer>> visited = new HashSet<Set<Integer>>();	//鏍囪杩囩殑闆嗗悎
			
		Set<Integer> state;
		queue.add(startState);	
		while(!queue.isEmpty()) {
			state = queue.poll();
			if(state.isEmpty()) {
				continue;
			}
			if(visited.contains(state)) {
				continue;
			}
			visited.add(state); 
			
			//鍔犲叆鐘舵�佽浆鎹㈣〃
			Map<Character, Set<Integer>> tempTable = new HashMap<Character, Set<Integer>>();
			for(char c:nfa.charSet) {
				if(c=='\0') {
					continue;
				}
				Set<Integer> tempSet = new HashSet<Integer>();
				for(int i:state) {
					tempSet.addAll(nfa.transStates(i, c));
				}
				if(!visited.contains(tempSet)) {
					queue.add(tempSet);
				}
				tempTable.put(c,tempSet);
			}
			transTable.add(tempTable);
			newStates.add(state);
		}
//		System.out.println("transtable"+transTable);
		
		//灏嗛泦鍚堣浆鎹负鐘舵�佺紪鍙�
//		System.out.println("states"+newStates);
		Map<Set<Integer>, Integer> mapping = new HashMap<>();
		for(int i=0;i<newStates.size();i++) {
			mapping.put(newStates.get(i),i);
		}
//		System.out.println("mapping"+mapping);
		for(int i=0;i<transTable.size();i++) {
			Map<Character, Set<Integer>> tn = transTable.get(i);
			Map<Character, Integer> td = new HashMap<>();
			for(char c:tn.keySet()) {
				if(!tn.containsKey(c)||tn.get(c).isEmpty()) {//涓嶈兘鎺ユ敹杩欎釜瀛楃
					continue;
				}
				td.put(c,mapping.get(tn.get(c)));
			}
			dfa.table.add(td);
		}

		return dfa;
	}
	
	public static void main(String[] args) {
		NFA nfa = new NFA();
		
		Set<Character> charsSet = new HashSet<Character>();
		charsSet.add('0');
		charsSet.add('1');
		charsSet.add('2');
		nfa.charSet = charsSet;
		
		HashSet<Integer> set0 = new HashSet<Integer>();
		set0.add(0);
		HashSet<Integer> set1 = new HashSet<Integer>();
		set1.add(1);
		HashSet<Integer> set2 = new HashSet<Integer>();
		set2.add(2);
		Map<Character, Set<Integer>> map = new HashMap<>();
		map.put('0',set0);
		map.put('\0',set1);
		nfa.table.add(map);
		map = new HashMap<>();
		map.put('1',set1);
		map.put('\0',set2);
		nfa.table.add(map);
		map = new HashMap<>();
		map.put('2',set2);
		map.put('\0',set2);
		nfa.table.add(map);
		
		System.out.println("nfa"+nfa.table);
		
//		System.out.println(nfa.getClosure(0));
//		System.out.println(nfa.getClosure(1));
//		System.out.println(nfa.getClosure(2));
		
		DFA dfa = Nfa2Dfa.trans(nfa);
		System.out.println("dfa"+dfa.table);
	}
}

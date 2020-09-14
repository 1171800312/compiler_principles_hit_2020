package lexical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class NFA {
	Set<Character> charSet = new HashSet<Character>();	
	List<Map<Character, Set<Integer>>> table = new ArrayList<>();
	
	public NFA() {
	}
	
	
	/** 姹俿tate鐨勎�-closure
	 * 
	 * @param state
	 * @return 蔚-closure
	 */
	public Set<Integer> getClosure(int state) {
		Set<Integer> ans = new HashSet<Integer>();
		Queue<Integer> queue = new LinkedList<Integer>();
		Set<Integer> visited = new HashSet<Integer>();		
		ans.add(state);
		//娌℃湁绌鸿浆绉�
		if(!table.get(state).containsKey('\0')) {
			return ans;
		}
		//BFS
		queue.add(state);
		while(!queue.isEmpty()) {
			int temp = queue.poll();
			visited.add(temp);
			//鏈夌┖杞Щ
			if(table.get(temp).containsKey('\0')) {
				Set<Integer> tempSet = table.get(temp).get('\0');
				for(int i:tempSet) {
					if(!visited.contains(i)) {
						queue.add(i);
					}
				}
			}
		}
		ans.addAll(visited);
		return ans;
	}
		
	/**杩斿洖鐘舵�乻tate鍦ㄦ帴鏀跺埌瀛楃ch鍚庤浆绉诲埌鐨勭姸鎬�
	 * 
	 * @param states 鐘舵��
	 * @param ch 鎺ユ敹瀛楃
	 * @return 鐘舵�侀泦鍚�
	 */
	public Set<Integer> transStates(int state,char ch) {
		if(!table.get(state).containsKey(ch)) {//涓虹┖闆�
			return new HashSet<Integer>();
		}
		Set<Integer> set = table.get(state).get(ch);
		Set<Integer> ans = new HashSet<Integer>();
		for(int i:set) {
			ans.addAll(getClosure(i));
		}
		return ans;
	}
	
	public Set<Character> getKeys(int state) {
		if(table.size()<=state) {
			return new HashSet<Character>();
		}
		return table.get(state).keySet();
	}
	
	
}

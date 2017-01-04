
import java.util.*;

public class LongestSubString{
	public static void main(String[] args) {
		String sourceString = "abcdababb";
		int result = LongestSubString(sourceString);
		System.out.println(result);
	}

	public static int LongestSubString(String sourceString){
		HashMap<Character,Integer> map = new HashMap<>();
		int start = 0;
		int max = 0;
		for (int i=0;i<sourceString.length();i++) {
			char c = sourceString.charAt(i);
			start = Math.max(start,map.containsKey(c) ? map.get(c) + 1 : 0);

			max = Math.max(max,i-start+1);

			map.put(c,i);
		}
		return max;
	}
}
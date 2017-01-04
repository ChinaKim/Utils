import java.util.*;
import java.util.Arrays;
import java.util.Vector;


/**
 * 给定一个数组，返回它元素的所有子集
 */
public class Subsets{

	public static void main(String[] args) {
		int[] s = {1,2,3};
		List<List<Integer>>  list = subsets(s);
		// System.out.println(Arrays.toString(list.toArray()));

	}


	public static List<List<Integer>> subsets(int[] s){
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		subsets(s,0,new ArrayList<Integer>(),result);
		return result;
	}

	public static void subsets(int[] s,int start,List<Integer> set,List<List<Integer>> result){
		result.add(new ArrayList<Integer>(set));
		for (int i=start;i<s.length;i++){
			System.out.println("i:" + i);
			set.add(s[i]);
			// String[] array = new String[set.size()];
			// System.out.println("i:"+i +"  " +Arrays.toString(set.toArray()));
			subsets(s,i+1,set,result);
			set.remove(set.size() -1);
			System.out.println("++++++++++++++");
		}
		System.out.println("-----------");
	}





}
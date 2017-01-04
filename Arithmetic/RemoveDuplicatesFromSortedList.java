public class RemoveDuplicatesFromSortedList{

	public static void main(String[] args) {
			ListNode node = new ListNode(1);
			ListNode node1 = new ListNode(1);
			ListNode node2 = new ListNode(2);
			ListNode node3 = new ListNode(2);
			ListNode node4 = new ListNode(3);
			node.next = node1;
			node1.next = node2;
			node2.next = node3;
			node3.next = node4;

			RemoveDuplicatesFromSortedList remove = new RemoveDuplicatesFromSortedList();

			ListNode head = remove.deleteDuplicates(node);

			ListNode current = head;
			while(current != null){
				System.out.print(current.val + " -> ");
				current = current.next;
			}
	}

	

	public ListNode deleteDuplicates(ListNode head){
		ListNode cur = head;
		while(cur != null){
			while(cur.next != null && cur.val == cur.next.val){
				cur.next = cur.next.next;
			}
			cur = cur.next;
		}
		return head;
	}


	public class ListNode{
		int val;
		ListNode next;
		public ListNode(int x){
			val = x;
			next = null;
		}
	}
}
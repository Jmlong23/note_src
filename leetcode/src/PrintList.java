
public class PrintList {
	void printList(ListNode l1){
		while(l1 != null){
			System.out.println(l1.val);
			l1 = l1.next;
		}
	}
}

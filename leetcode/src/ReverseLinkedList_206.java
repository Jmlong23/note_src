/**
 * leetcode第206题，链表反转
 * @author 15626
 *
 */
class ListNode {
	 int val;
	 ListNode next;
	 ListNode(int x) { val = x; }
}
 
class Solution {
	//递归
	public ListNode reverseList(ListNode head) {
		if(head == null || head.next == null) return head;
		ListNode cur = reverseList(head.next);
		head.next.next = head;
		head.next = null;
		return cur;
	}
	//迭代
    public ListNode reverseList1(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode pre = null;
        ListNode current = head;
        while(current != null){
        	ListNode temp = current.next;
        	current.next = pre;
        	pre =current;
        	current = temp;
        }
        return pre;
    }
}
public class  ReverseLinkedList_206{
	public static void main(String[] args){
		ListNode list = new ListNode(1);
		ListNode head = list;
		for(int i = 2; i < 5; i++){
			list.next = new ListNode(i);
			list = list.next;
		}
		list.next = null;
		Solution solu = new Solution();
		list = head;
		while(head != null){
			System.out.println(head.val);
			head = head.next;
		}
		head = list;
		ListNode l1 = solu.reverseList(head);
		while(l1 != null){
			System.out.println(l1.val);
			l1 = l1.next;
		}
		
	}
}
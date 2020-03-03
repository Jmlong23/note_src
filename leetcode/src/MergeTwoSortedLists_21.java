

/* fail
 class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode p1 = new ListNode(-1);
        ListNode p2 = new ListNode(-1);
        p1.next = l1;
        p2.next = l2;
        while(p2.next != null){
            p1.next = l1;
            while(p1.next != null){
                if(p2.next.val < p1.next.val){
                    ListNode temp = p2.next;
                    p2.next = p2.next.next;
                    temp.next = p1.next;
                    break;
                }
                p1 = p1.next;
            }
        }
        return l1;
    }
}
 */

class MergeTwoSortedLists{
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
       ListNode list = new ListNode(0);
       ListNode head = list;
       while(l1 != null && l2 != null){
    	   if(l1.val < l2.val){
    		   head.next = new ListNode(l1.val);
    		   l1 = l1.next;
    	   }else{
    		   head.next = new ListNode(l2.val);
    		   l2 = l2.next;
    	   }
    	   head = head.next;
       }
       if(l1 != null){
    	   head.next = l1;
       }
       if(l2 != null){
    	   head.next = l2;
       }
       return list.next;
    }
}
public class MergeTwoSortedLists_21 {
	public static void main(String[] args) {
		MergeTwoSortedLists mLists = new MergeTwoSortedLists();
		ListNode l1 = new ListNode(1);
		l1.next = new ListNode(2);
		l1.next.next = new ListNode(4);
		ListNode l2 = new ListNode(1);
		l2.next = new ListNode(3);
		l2.next.next = new ListNode(4);
		ListNode listNode = mLists.mergeTwoLists(l1, l2);
		PrintList pList = new PrintList();
		pList.printList(listNode);
	}
}

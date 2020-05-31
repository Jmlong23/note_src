public class Production {
	
	private String[] productions={"S->E","E->E+A","E->A","A->A-B","A->B","B->B*C","B->C","C->C/D","C->D","D->(E)","D->i"};
	private int[] numOfP={1,3,1,3,1,3,1,3,1,3,1};
	
	public String getProduction(int i){
		return productions[i];
	}
	
	public int getNumOfP(int i){
		return numOfP[i];
	}
}

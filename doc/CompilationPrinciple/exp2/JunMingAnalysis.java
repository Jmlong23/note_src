import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 
 */

/**
 * @author zhongfang
 * 
 */
public class JunMingAnalysis {
	
	private LinkedList<Character> list1 = new LinkedList<Character>();//保存输入字符串的列表
	private Stack<Character> stack = new Stack<Character>();//保存符号的栈
	private Stack<Integer> stack2 = new Stack<>();//保存状态的栈
	private Production production = new Production();//描述产生式的类
	private BufferedWriter output;
	private String temp1;
	private String actions = "";


    //SLR分析表表的二维字符串数组
	private String[][] table = { { "S7", "", "", "", "", "S6", "", "", "2", "3", "4", "5", "1" }, // 0
			{ "", "S8", "", "", "", "", "", "accept", "", "", "", "", ""  }, // 1
			{  "", "R2", "R9", "", "", "", "R2", "R2", "", "", "", "", ""   }, // 2
			{  "", "R4", "R4", "S10", "", "", "R4", "R4", "", "", "", "", "" },// 3
			{  "", "R6", "R6", "R6", "S11", "", "R6", "R6", "", "", "", "", ""  },// 4
			{ "", "R8", "R8", "R8", "R8", "", "R8", "R8", "", "", "", "", ""  },// 5
			{ "S7", "", "", "", "", "S6", "", "", "2", "3", "4", "5", "12"   },// 6
			{  "", "R10", "R10", "R10", "R10", "", "R10", "R10", "", "", "", "", ""  },// 7
			{ "S7", "", "", "", "", "S6", "", "", "13", "3", "4", "5", ""  },// 8
			{  "S7", "", "", "", "", "S6", "", "", "", "14", "4", "5", ""  },// 9
			{"S7", "", "", "", "", "S6", "", "", "", "", "15", "5", "" },// 10
			{ "S7", "", "", "", "", "S6", "", "", "", "", "", "16", ""   },// 11
			{  "", "S8", "", "", "", "", "S17", "", "", "", "", "", ""    },//12
			{  "", "R1", "S9", "", "", "", "R1", "R1", "", "", "", "", ""   },//13
			{  "", "R3", "R3", "S10", "", "", "R3", "R3", "", "", "", "", ""    },//14
			{  "", "R5", "R5", "R5", "S11", "", "R5", "R5", "", "", "", "", ""    },//15
			{  "", "R7", "R7", "R7", "R7", "", "R7", "R7", "", "", "", "", ""   },//16
			{  "", "R9", "R9", "R9", "R9", "", "R9", "R9", "", "", "", "", ""  },//17
	};

	public JunMingAnalysis() {
		try {
			output = new BufferedWriter(new FileWriter("output.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getText() {
		char a[];
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader("input.txt"));
			String lString;
			while ((lString = bufferedReader.readLine()) != null) {
				//数据预处理
				stack.clear();
				list1.clear();
				stack2.clear();
				temp1 = lString.trim();
				temp1.replaceAll("\\s+", "");// 去掉一个以上的空白符，用一个空白代替
				a = temp1.toCharArray();
				for (char _char : a) {
					list1.offer(_char);
				}
				list1.offerLast('$');
				stack.push('$');
				stack2.push(0);
				output.write("state\t symbol\t input\t action");
				output.newLine();
				boolean b = analysis();
				if (b){
					output.write("语法正确，表达式符合SLR(1)文法");
					output.newLine();
				}
				else{
					output.write("语法错误不符合SLR(1)文法");
					output.newLine();
				}
				output.write("-----------------------------------------");
				output.newLine();
			}
		} catch (Exception e) {
		} finally { // 关闭资源
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (output != null)
				try {
					output.close();
				} catch (Exception e2) {
				}
		}
	}

	public int getOrder(char c) {        //获取符号（终结符或非终结符）的编号（符号表中的横向顺序）
		if (c == 'i')
			return 0;
		else if (c == '+')
			return 1;
		else if (c == '-')
			return 2;
		else if (c == '*')
			return 3;
		else if (c == '/')
			return 4;
		else if (c == '(')
			return 5;
		else if (c == ')')
			return 6;
		else if (c == '$')
			return 7;
		else if(c=='A')
			return 8;
		else if(c=='B')
			return 9;
		else if(c=='C')
			return 10;
		else if(c=='D')
			return 11;	
		else if(c=='E')
			return 12;				
		else 
			return -1;
	}

	public void display() {          //读SymbolStack、StateStack和input里的所有字符，显示到输出文件
		String symbols = "";
		String states = "";
		String input = "";

		Object[] symbolObjects = stack.toArray();
		for (int i = 0; i < symbolObjects.length; i++) {
			symbols += symbolObjects[i].toString();

		}

		Object[] stateObjects = stack2.toArray();
		for (int i = 0; i < stack2.size(); i++) {
			states += stateObjects[i].toString();
		}

		Object[] inputObjects = list1.toArray();
		for (int i = 0; i < list1.size(); i++) {
			input += inputObjects[i].toString();
		}
		try {
			output.write(states + "\t" + symbols + "\t" + input + "\t"
					+ actions);
			output.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean analysis() {
		while (true) {
			actions = "";
			char c = list1.peekFirst();
			int i = getOrder(c);
			if(i==-1)                //如果输入是除了规定的终结符和非终结符以外的符号，返回false
				return false;
			String string2 = table[stack2.peek()][i];
			if (string2.trim().equals("".trim()))
				return false;
			else if (string2.equals("accept")) {//接收
				actions+="accept";
				display();
				return true;
			} else if (string2.charAt(0) == 'S') { // 移进
				String s = string2.substring(1); // 取S后面的状态数
				int n = Integer.parseInt(s);
				System.out.println("Shift " + s);
				actions += "Shift to state " + s;
				display();
				list1.pollFirst(); // 从输入带里弹出第一个字符，并把该字符送symbolStack,同时向StateStack压入取得的状态数
				stack2.push(n);
				stack.push(c);

			} else if (string2.charAt(0) == 'R') { // 规约
				String s = string2.substring(1); // 取r后面的产生式编号
				int n = Integer.parseInt(s);
				System.out.println("Reduce " + s);
				actions += "Reduce by production " + s;
				display();

				int n2 = production.getNumOfP(n);// 取产生式右部的字符个数（应该在SymbolStack和StateStack中弹出来的个数）
				for (int i1 = 0; i1 < n2; i1++) {
					stack.pop();
					stack2.pop();
				}

				char _char1 = production.getProduction(n).charAt(0);// 获取产生式左边的非终结符，压入SymbolStack
				stack.push(_char1);
				String s1 = table[stack2.peek()][getOrder(_char1)];// 查找goto字表，找到该终结符和当前状态对应的编号，压入StateStack
				if (s1.trim().equals(""))
					return false;
				else
					stack2.push(Integer.parseInt(s1));
			}
		}
	}

	public static void main(String[] args) {
		JunMingAnalysis ananlyzer = new JunMingAnalysis();
		ananlyzer.getText();
	}

}

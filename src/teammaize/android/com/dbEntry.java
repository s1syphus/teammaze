package teammaize.android.com;

public class dbEntry {

	int level;
	String qestion;
	String ans1;
	String ans2;
	String ans3;
	String ans4;
	
	public dbEntry() {
		
	}
	
	public dbEntry(int lev, String quest, String a1, String a2, String a3, String a4) {
		
		this.level = lev;
		this.qestion = quest;
		this.ans1 = a1;
		this.ans2 = a2;
		this.ans3 = a3;
		this.ans4 = a4;
		
	}
	


	public String getQ() {
		return this.qestion;
	}
	
	public String getA1() {		
		return this.ans1;
	}

	public String getA2() {
		return this.ans2;
	}
	
	public String getA3() {
		return this.ans3;
	}
	
	public String getA4() {
		return this.ans4;
	}
	
	public int getLev() {
		return this.level;
	}
}
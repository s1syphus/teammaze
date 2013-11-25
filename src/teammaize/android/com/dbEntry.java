package teammaize.android.com;

public class dbEntry {

	// private members
	long id;
	String qestion;
	String ansCorrect;
	String ans2;
	String ans3;
	String ans4;
	String subject;
	String level;
	String corAttempts;
	String attempts;
	
	
	// public functions
	// There are not "set" functions except for the constructor
	// I do not think we need to modify an entry after it is made.
	public dbEntry() {
		this.corAttempts = "0";
		this.attempts = "0";
	}
	
	public dbEntry(int ID, String quest, String a1, String a2, String a3, String a4, String subject, String level) {
		
		this.id = ID;
		this.level = level;
		this.subject = subject;
		this.qestion = quest;
		this.ansCorrect = a1;
		this.ans2 = a2;
		this.ans3 = a3;
		this.ans4 = a4;
		this.corAttempts = "0";
		this.attempts = "0";
		
	}
	


	public String getQ() {
		return this.qestion;
	}
	
	public String getACorrect() {		
		return this.ansCorrect;
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
	
	public String getSub() {
		return this.subject;
	}
	
	public String getLevel() {
		return this.level;
	}
	
	public String getCorAttempts() {
		return this.corAttempts;
	}
	
	public String getAttempts() {
		return this.attempts;
	}
	
	public long getID() {
		return this.id;
	}
	
	public void printEntry() {
		System.out.println(id + " " + qestion + " " + ansCorrect + " " + ans2 + " " + ans3 + " " + 
						   ans4 + " " + subject + " " + level + " " + corAttempts + " " + attempts);
	}
}
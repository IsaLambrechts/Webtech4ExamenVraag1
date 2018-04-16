package edu.ap.spring.model;

import java.time.LocalDate;

public class InhaalExamen {
 
    private String student;
    private String exam;
 	private String date;
	private String reason;
 
    public InhaalExamen() {
    		this.date = LocalDate.now().toString();
    }
    
    public InhaalExamen(String student, String exam, String reason, String date) {
    		this.student = student;
    		this.exam = exam;
    		this.reason = reason;
    	    this.date = date;
    }
    
    public InhaalExamen(String student, String exam, String reason) {
		this.student = student;
		this.exam = exam;
		this.reason = reason;
		this.date = LocalDate.now().toString();
    }

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public String getExam() {
		return exam;
	}

	public void setExam(String exam) {
		this.exam = exam;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}

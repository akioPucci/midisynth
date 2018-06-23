package record.note;

import java.util.Date;

public class Note {

	private Date start;
	private Date end;
	private int note;

	public Note(Date start, Date end, int note) {
		this.start = start;
		this.end = end;
		this.note = note;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

}

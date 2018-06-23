package record;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Record {

	private int wave;
	private List<String> notes;

	public Record(int wave) {
		this.wave = wave;
		notes = new ArrayList<String>();
	}

	public void addNote(long s, long e, int n) {

		String note = s + "," + e + "," + n;
		notes.add(note);
	}
	
	public void addNote(String note) {
		notes.add(note);
	}
	
	public void addAllNotes(List<String> notes) {
		this.notes.addAll(notes);
	}

	public void save(String name) {
		try {
			name = "gravacoes/".concat(name + ".csv");
			PrintWriter pw = new PrintWriter(new File(name));

			StringBuilder sb = new StringBuilder();
			sb.append("Wave number:" + wave + "\n");
			for (String string : notes) {
				sb.append(string);
				sb.append('\n');
			}

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

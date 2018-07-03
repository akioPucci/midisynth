package genius;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import key.KeyManagement;
import key.Pair;

public class Genius {

	public static void startGenius(String filename) {
		filename += ".csv";
		filename = "gravacoes/" + filename;
		BufferedReader br = null;
		String line = "";

		// inicio, tempo, fim
		List<Triple<Long, Long, Integer>> changes = new ArrayList<Triple<Long, Long, Integer>>();

		try {

			br = new BufferedReader(new FileReader(filename));
			line = br.readLine();
			System.out.println(line);
			while ((line = br.readLine()) != null) {

				String[] note = line.split(",");
				changes.add(new Triple<Long, Long, Integer>(Long
						.parseLong(note[0]), (Long.parseLong(note[1]) - Long
						.parseLong(note[0])), Integer.parseInt(note[2])));
			}

			Collections.sort(changes, new Comparator<Triple<Long, Long, Integer>>() {

				@Override
				public int compare(Triple<Long, Long, Integer> p0,
						Triple<Long, Long, Integer> p1) {
					if (p0.getFirst() > p1.getFirst())
						return 1;
					else if (p0.getFirst() < p1.getFirst())
						return -1;
					else if (p0.getSecond() > p1.getSecond())
						return 1;
					else if (p0.getSecond() < p1.getSecond())
						return -1;
					return p0.getThird() - p1.getThird();
				}
			});

			Long i = (long) 0;
			for (Triple<Long, Long, Integer> triple : changes) {
				triple.setFirst(i);
				i++;
				//System.out.println("Triple depois: " + triple.getFirst() + " Time: " + triple.getSecond() + " Note: " + triple.getThird());
			}

			while (changes.size() > 0) {

				KeyManagement.playForMilliseconds(changes.get(0).getThird(), changes.get(0).getSecond());
				KeyManagement.waitClick(changes.get(0).getThird());
				changes.remove(0);
			}
			JOptionPane.showMessageDialog(null, "Parabéns! Você conseguiu!");
			//TODO mensagem de parabéns vc terminou
			System.out.println("fim!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

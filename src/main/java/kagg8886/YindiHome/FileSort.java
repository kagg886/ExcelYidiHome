package kagg8886.YindiHome;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FileSort {
	public interface listener {
		void onchoose(File frameFile) throws Exception;
	}
	
	public static ArrayList<File> sort(listener l) {
		File file = new File("E:\\img");
		File[] files = file.listFiles();
		ArrayList<File> list = new ArrayList<File>();
		for (File file2 : files) {
			list.add(file2);
		}

		Collections.sort(list, new Comparator<File>() {
			public int compare(File o1, File o2) {
				if (o1.isDirectory() && o2.isFile())
					return -1;
				if (o1.isFile() && o2.isDirectory())
					return 1;
				Integer f = f(o1.getName());
				Integer f2 = f(o2.getName());
				return Integer.compare(f, f2);
			}
		});
		
		for (File file2 : list) {
			try {
				l.onchoose(file2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	static Integer f(String filename) {
		int x = filename.indexOf(".");
		String string2 = filename.substring(0, x);
		char[] cs = string2.toCharArray();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < cs.length; i++) {
			if (Character.isDigit(cs[i])) {
				builder.append(cs[i]);
			}
		}
		return Integer.parseInt(builder.toString());
	}
}


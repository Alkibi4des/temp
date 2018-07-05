package application;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Calendar;

public abstract class Saver {
	final static String dataPath = System.getProperty("user.dir") + File.separator;

	public static String load(String label) {
		if(label.equalsIgnoreCase("case1")) {
			label = "Shopping";
		}else if(label.equalsIgnoreCase("case2")){
			label = "Community";
		}
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		String today = String.valueOf(year * 10000 + month * 100 + day);
		
		try {
			RandomAccessFile file = new RandomAccessFile(dataPath + label + ".txt", "rw");
			long pos = readLastLine(file);
			if (pos == -1) {
				file.close();
				return null;
			} else {
				file.seek(pos + 1);
				String[] split = file.readLine().split("\t");
				if (today.equals(split[0])) {
					file.close();
					System.out.println(split[1]);
					return split[1];
				} else {
					file.close();
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static void Save(String label, int hours, int mins, int secs, int millis) {
		if(label.equalsIgnoreCase("case1")) {
			label = "shopping";
		}
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		String today = String.valueOf(year * 10000 + month * 100 + day);

		try {
			RandomAccessFile file = new RandomAccessFile(dataPath + label + ".txt", "rw");
			long pos = readLastLine(file);
			if (pos == -1) {
				file.write(("\r\n" + today + "\t" + hours + ":" + mins + ":" + secs + ":" + millis).getBytes());
			} else {
				file.seek(pos + 1);
				String[] split = file.readLine().split("\t");
				if (today.equals(split[0])) {
					file.seek(pos + 1);
					file.write((today + "\t" + hours + ":" + mins + ":" + secs + ":" + millis).getBytes());
				} else {
					file.seek(file.length() - 1);
					file.write(("\r\n" + today + "\t" + hours + ":" + mins + ":" + secs + ":" + millis).getBytes());
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static long readLastLine(RandomAccessFile rFile) {
		try {
			long fileSize = rFile.length();
			if (fileSize == 0) {
				return -1;
			}
			long pos = fileSize - 1; // 뒤로 부터

			while (true) {
				rFile.seek(pos); // 파일 포인터 이동
				char c = (char) rFile.readByte();
				if (c == '\n') { // 해당 위치의 바이트를 읽어 \n 문자와 같은지 검사
					break; // 같으면 멈춤
				}
				pos--; // 포인터 위치값 감소 (앞으로)
			}
			return pos;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	static void parseTimeData() {

	}
}

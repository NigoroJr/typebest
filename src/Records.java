import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class stores information about the user's record
 * @author Naoki Mizuno
 * 
 */
// TODO: Add "mode" parameter

public class Records {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String recordsFileName;
	
	// private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<Result> records = new ArrayList<Result>();
	
	public Records(String recordsFileName) {
		this.recordsFileName = recordsFileName;
	}
	
	/**
	 * Reads the user's records from the binary file. The binary file consists of:
	 * double (time), int (miss), String (user name), Calendar (date added), String (keyboard layout)
	 * It stores the records into an ArrayList of arrays.
	 */
	public void readRecords() {
		try {
			in = new ObjectInputStream(new FileInputStream(recordsFileName));
			// Make an infinite loop because it will throw an exception and be caught when reaching EOF
			while (true) {
				Result result = new Result(in.readDouble(), in.readInt(),
						in.readUTF(), (Calendar)in.readObject(), in.readUTF());
				
				records.add(result);
			}
		}
		catch (FileNotFoundException e) {
			// When there's not file found
			return;
		}
		catch (EOFException e) {
			// Do nothing when reaching the end of file
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes the record to a binary file with the name {user name}_records.dat
	 * The schema is double, int, String, Calendar, String.
	 * When the file already exists, the results will be appended by re-writing all the previous results
	 * that is stored in the array at the beginning when reading.
	 * This uses method overloading and gives the parameters to the writeRecords method that accepts Result
	 * instance as a parameter.
	 * @param time The time in double that the user took to type all the words.
	 * @param miss The miss types the user had.
	 * @param userName The user name in case of records list of all the existing users.
	 * @param date The date and time the record was added.
	 * @param keyboardLayout The keyboard layout used when this record was created.
	 */
	public void writeRecords(double time, int miss, String userName, Calendar date, String keyboardLayout) {
		Result result = new Result(time, miss, userName, date, keyboardLayout);
		writeRecords(result);
	}
	
	/**
	 * Write the result to a binary file.
	 * Accepts a Result instance and write that result to the binary file named {user name}_records.dat
	 * This method first adds the given result to the ArrayList of Result, and then writes the whole
	 * ArrayList, overwriting if a file with the same name exists.
	 * @param result A Result instance representing the result of a round.
	 */
	public void writeRecords(Result result) {
		records.add(result);
		
		try {
			out = new ObjectOutputStream(new FileOutputStream(recordsFileName));
			
			// Write the results in the ArrayList (which contains the result of this round)
			for (int i = 0; i < records.size(); i++) {
				out.writeDouble(records.get(i).getTime());
				out.writeInt(records.get(i).getMiss());
				out.writeUTF(records.get(i).getUserName());
				out.writeObject(records.get(i).getDate());
				out.writeUTF(records.get(i).getKeyboardLayout());
			}
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

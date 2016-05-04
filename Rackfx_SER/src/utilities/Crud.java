package utilities;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Crud {

	private static String localDirectory = "/home/anthony/Playground/";
	private static String extentionSer = ".ser";

	/**
	 * Methode de serialisation d'objets
	 * @param obj
	 * @throws IOException
	 */
	public static <T> void Serialize(T obj) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(localDirectory + obj.getClass().getName() + extentionSer));
		out.writeObject(obj);
		out.flush();
		out.close();
	}

	/**
	 * Methode de deserialisation d'objets
	 * @param obj
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <obj, T> ObservableList<T> Deserialize(Class<?> obj) throws IOException, ClassNotFoundException {
		boolean endOfFile = false;
		ObservableList<Object> rlist = FXCollections.observableArrayList();
		ObjectInputStream in = new ObjectInputStream(
				new FileInputStream(localDirectory + obj.getName() + extentionSer));
		do {
			try {
				rlist.add(in.readObject());
			} catch (EOFException eof) {
				endOfFile = true;
			}
		} while (!endOfFile);
		in.close();
		return (ObservableList<T>) rlist;
	}
}

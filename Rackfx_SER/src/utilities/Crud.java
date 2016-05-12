package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Crud {

	private static String localDirectory = "/home/anthony/Playground/";
	private static String extentionSer = ".ser";

	/**
	 * Methode de serialisation d'objets
	 * 
	 * @param obj
	 * @throws IOException
	 */
	public static <T> void Serialize(ObservableList<T> obj) throws IOException {
		Hashtable<Integer, T> h = new Hashtable<Integer, T>();

		for (int i = 0; i < obj.size(); i++) {
			h.put(i, obj.get(i));
		}

		ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(localDirectory + obj.get(0).getClass().getName() + extentionSer));
		out.writeObject(h);
		out.flush();
		out.close();
	}

	/**
	 * Methode de deserialisation d'objets
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <obj, T> ObservableList<T> Deserialize(Class<?> obj) throws IOException, ClassNotFoundException {
		Hashtable<?, ?> h = new Hashtable<Object, Object>();
		ObservableList<Object> rlist = FXCollections.observableArrayList();
		ObjectInputStream in = new ObjectInputStream(
				new FileInputStream(localDirectory + obj.getName() + extentionSer));

		h = (Hashtable<?, ?>) in.readObject();
		in.close();

		for (Enumeration<?> e = h.elements(); e.hasMoreElements();) {
			rlist.add(e.nextElement());
		}

		return (ObservableList<T>) rlist;
	}
}

package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Set;
import java.util.TreeMap;

import store.ToDoStore;
import todo.ToDo;
import todo.ToDoList;

/**
 * This class holds all the data.
 * 
 * The purpose of this class is to hold the model of the ToDo lists and items.
 * It holds all the data the user adds and changes. It also has the
 * functionality for saving and loading the data in a save file and updating the
 * view.
 * 
 * @author Zachary Winans, Laynah Varnum, Ziad Hakim, Jiazheng Huang
 *
 */

@SuppressWarnings("deprecation")
public class ToDoModel extends Observable {

	// Private Fields
	private TreeMap<ToDoList, ArrayList<ToDo>> todos;
	private ToDoStore store;

	/**
	 * This is the model class's constructor that sets up the TreeMap.
	 */
	public ToDoModel() {
		todos = new TreeMap<ToDoList, ArrayList<ToDo>>();
	}

	/**
	 * This is the model's alternate constructor for loading from an existing file.
	 * 
	 * @param loadState A TreeMap of saved lists and ToDos
	 */
	public ToDoModel(TreeMap<ToDoList, ArrayList<ToDo>> loadState) {
		todos = loadState;
	}

	/**
	 * This method adds a new list.
	 * 
	 * The purpose of this method is to take in a ToDoList object and add it to the
	 * TreeMap. It puts the list in the map with an associated ArrayList for holding
	 * ToDo objects.
	 * 
	 * @param list A ToDoList object
	 */
	public void newList(ToDoList list) {
		todos.put(list, new ArrayList<ToDo>());
	}

	/**
	 * This method adds an item to an existing list.
	 * 
	 * The purpose of this method is to take in a list object and a ToDo item and
	 * add the item to the list. When the item is added, the view is notified and
	 * updated.
	 * 
	 * @param list A ToDoList object
	 * @param item A ToDo object
	 */
	public void addItem(ToDoList list, ToDo item) {
		todos.get(list).add(item);
		setChanged();
		notifyObservers();
	}

	/**
	 * This method gets a ToDo item from a list.
	 * 
	 * The purpose of this method is to take in a list object and an index and get
	 * the ToDo object in the specified list at the given index. It returns the ToDo
	 * item found at that index.
	 * 
	 * @param list A ToDoList object
	 * @param i    An int representing an index
	 * @return A ToDo object
	 */
	public ToDo getItemAt(ToDoList list, int i) {
		return todos.get(list).get(i);
	}

	/**
	 * This method removes an item from a list.
	 * 
	 * The purpose of this method is to take in a list object and a ToDo item and
	 * delete the given item from the specified list. The view is then notified and
	 * updated.
	 * 
	 * @param list A ToDoList object
	 * @param item A ToDo object
	 * @return A boolean indicating if the item was deleted
	 */
	public boolean removeItemAt(ToDoList list, ToDo item) {
		setChanged();
		notifyObservers();
		return todos.get(list).remove(item);
	}

	/**
	 * This method returns a list of ToDos.
	 * 
	 * The purpose of this method is to take in a list object and return the
	 * collection of ToDo objects associated with that list. The ToDos are then
	 * returned.
	 * 
	 * @param list A ToDoList object
	 * @return An ArrayList of ToDo objects
	 */
	public ArrayList<ToDo> getToDos(ToDoList list) {
		return todos.get(list);
	}

	/**
	 * This method returns all the lists.
	 * 
	 * The purpose of this method is to return all list objects being stored in the
	 * model's TreeMap.
	 * 
	 * @return A Set of ToDoList object
	 */
	public Set<ToDoList> getToDoLists() {
		return todos.keySet();
	}

	/**
	 * This method returns the TreeMap.
	 * 
	 * The purpose of this method is to return the TreeMap from the model. The
	 * TreeMap holds all the ToDo list and ToDo items.
	 * 
	 * @return A TreeMap of ToDos
	 */
	public TreeMap<ToDoList, ArrayList<ToDo>> getMap() {
		return todos;
	}

	/**
	 * This method gets the list at a given index.
	 * 
	 * The purpose of this method is to take in an index and return a ToDoList. The
	 * method finds the list at the index and returns a ToDoList object.
	 * 
	 * @param i An int representing an index
	 * @return A ToDoList object
	 */
	public ToDoList getListAt(int i) {
		return (ToDoList) todos.keySet().toArray()[i];
	}

	/**
	 * This method replaces a ToDo object.
	 * 
	 * The purpose of this method is to take in a list object, an index and a ToDo
	 * item and remove the old ToDo. Once the ToDo object at the given index is
	 * removed, the new ToDo is added to the list.
	 * 
	 * @param list A ToDoList object
	 * @param i    An int representing an index
	 * @param todo A ToDo object
	 */
	public void replace(ToDoList list, int i, ToDo todo) {
		ArrayList<ToDo> currToDos = todos.get(list);
		currToDos.remove(i);
		currToDos.add(todo);
		todos.put(list, currToDos);
	}

	/**
	 * This method saves the data.
	 * 
	 * The purpose of this method is to serialize the model's data structure. The
	 * data is saved in todo.sav and can be loaded when the application is run
	 * again.
	 */
	public void save() {
		ToDoStore store = new ToDoStore(todos);
		String filename = "src/todo.sav";

		try {
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(store);

			out.close();
			file.close();

		}

		catch (IOException ex) {

		}

	}

	/**
	 * This method loads the saved data.
	 * 
	 * The purpose of this method is to open the serialized file and convert int
	 * back into a TreeMap. The map is returned and used to set-up the view.
	 * 
	 * @return A TreeMap
	 */
	public TreeMap<ToDoList, ArrayList<ToDo>> load() {

		ToDoStore store = null;
		try {

			FileInputStream file = new FileInputStream("src/todo.sav");
			ObjectInputStream in = new ObjectInputStream(file);
			store = (ToDoStore) in.readObject();

			in.close();
			file.close();

			return store.getMap();

		} catch (IOException ex) {
			System.out.println("IOException is caught");
		} catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException" + " is caught");
		}
		return store.getMap();
	}
}

package store;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

import todo.ToDo;
import todo.ToDoList;

/**
 * This class implements Serializable.
 * 
 * The purpose of this class is to implement Serializable in order to save the
 * program's data.
 * 
 * @author Zachary Winans, Laynah Varnum, Ziad Hakim, Jiazheng Huang
 *
 */

public class ToDoStore implements Serializable {

	// Private Fields
	private static final long serialVersionUID = 1L;
	private TreeMap<ToDoList, ArrayList<ToDo>> todos;

	/**
	 * This is the class's constructor.
	 * 
	 * @param todos A TreeMap of lists and ToDos
	 */
	public ToDoStore(TreeMap<ToDoList, ArrayList<ToDo>> todos) {
		this.todos = todos;
	}

	/**
	 * This method returns the TreeMap of ToDos.
	 * 
	 * @return A TreeMap
	 */
	public TreeMap<ToDoList, ArrayList<ToDo>> getMap() {
		return todos;
	}

}
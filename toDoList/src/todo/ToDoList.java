package todo;

import java.io.Serializable;

/**
 * This class sets up a ToDo list.
 * 
 * The purpose of this class is to create a ToDo list object. The object has the
 * name of the list.
 * 
 * @author Zachary Winans, Laynah Varnum, Ziad Hakim, Jiazheng Huang
 *
 */

public class ToDoList implements Comparable<ToDoList>, Serializable {

	// Private Fields
	String name;

	/**
	 * This is the class's constructor that sets the name.
	 * 
	 * @param name A String representing the name of the list.
	 */
	public ToDoList(String name) {
		this.name = name;
	}

	/**
	 * This method returns the list's name.
	 * 
	 * @return A String with the list's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * This method compares names.
	 * 
	 * @param o A ToDoList object
	 */
	@Override
	public int compareTo(ToDoList o) {
		return name.compareTo(o.name);
	}

}

package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import model.ToDoModel;
import todo.ToDo;
import todo.ToDoList;

/**
 * This class handles all the program logic.
 * 
 * The purpose of this class is to handle all logic related to the ToDo list. It
 * handles adding, deleting, updating and changing fields related to the ToDo
 * lists and the ToDo items. The controller interacts with and changes the
 * model's fields.
 * 
 * @author Zachary Winans, Laynah Varnum, Ziad Hakim, Jiazheng Huang
 */

public class ToDoController {

	// Private Fields
	private ToDoModel model;

	/**
	 * This is the controller class's constructor that takes in the model.
	 * 
	 * @param model
	 */
	public ToDoController(ToDoModel model) {
		this.model = model;
	}

	/**
	 * This method adds a ToDo item to a list.
	 * 
	 * The purpose of this method is to take in a list object and a ToDo object and
	 * add the ToDo object to the given list. It calls the model's addItem method to
	 * add the new ToDo object and update the view.
	 * 
	 * @param list A ToDoList object
	 * @param item A ToDo object
	 */
	public void add(ToDoList list, ToDo item) {
		this.model.addItem(list, item);
	}

	/**
	 * This method adds a whole new list.
	 * 
	 * The purpose of this method is to take in a new ToDoList object and add it to
	 * the model's data structure. The method calls the model's newList method in
	 * order to add the list object and update the view.
	 * 
	 * @param list A ToDoList object
	 */
	public void addList(ToDoList list) {
		this.model.newList(list);
	}

	/**
	 * This method removes a ToDo item.
	 * 
	 * The purpose of this method is to take in a list object and a ToDo item and
	 * delete the specified item from the specified list. It calls the model's
	 * removeItemAt method to delete the ToDo object from the list and no longer
	 * display it.
	 * 
	 * @param list A ToDoList object
	 * @param item A ToDo object
	 */
	public void delete(ToDoList list, ToDo item) {
		model.removeItemAt(list, item);

	}

	/**
	 * This method returns a ToDo item at a given index.
	 * 
	 * The purpose of this method is to take in a list object and an index and find
	 * the ToDo object at that index in the given list. It calls the model's
	 * getItemAt method in order to get the ToDo item from the given list and return
	 * it.
	 * 
	 * @param list A ToDoList object
	 * @param i    An int representing an index
	 * @return A ToDo object
	 */
	public ToDo getTodoAtIndex(ToDoList list, int i) {
		return model.getItemAt(list, i);
	}

	public void renameList(ToDoList curr, String i) {

	}

	/**
	 * This method returns the model's map.
	 * 
	 * The purpose of this method is to return the data structure holding all
	 * ToDoLists and ToDo objects. It calls the model's getMap method in order to
	 * return the TreeMap that holds all the ToDo data.
	 * 
	 * @return A TreeMap holding all ToDoLists and ToDo objects
	 */
	public TreeMap<ToDoList, ArrayList<ToDo>> getMap() {
		return model.getMap();
	}

	/**
	 * This method sorts the ToDo objects.
	 * 
	 * The purpose of this method is to take in a list object and a sort parameter
	 * in order to sort the ToDos in a list. It uses the direction parameter to
	 * determine how the ToDos in the given list should be sorted. It then arranges
	 * them that way for the view.
	 * 
	 * @param list      A ToDoList object
	 * @param direction A String representing how the data should be sorted
	 */
	public void sort(ToDoList list, String direction) {

		for (Entry<ToDoList, ArrayList<ToDo>> entry : model.getMap().entrySet()) {

			if (direction.equals("creation")) {

				Collections.sort(entry.getValue(),
						(o1, o2) -> (o1.getCreation().toString().compareTo(o2.getCreation().toString())));

			}
			if (direction.equals("deadline")) {

				Collections.sort(entry.getValue(),
						(o1, o2) -> (o1.getDeadline().toString().compareTo(o2.getDeadline().toString())));

			}

			if (direction.equals("important")) {
				for (int i = 0; i < entry.getValue().size(); i++) {
					if (entry.getValue().get(i).getImportant() == true) {
						ToDo toMove = entry.getValue().get(i);
						int index = entry.getValue().indexOf(toMove);
						entry.getValue().remove(index);
						entry.getValue().add(0, toMove);
					}
				}

			}

			if (direction.equals("name")) {

				Collections.sort(entry.getValue(), (o1, o2) -> (o1.getTitle().compareTo(o2.getTitle())));

			}

		}

	}

	/**
	 * This method returns a list of ToDos.
	 * 
	 * The purpose of this method is to take in a list object and use it to return
	 * the list of ToDos in that object. It calls the model's getToDos method in
	 * order to find and return the specified list's collection of ToDo objects.
	 * 
	 * @param list A ToDoList object
	 * @return An ArrayList of ToDo objects
	 */
	public ArrayList<ToDo> getTodos(ToDoList list) {
		return this.model.getToDos(list);
	}

	/**
	 * This method gets the ToDos at a given index.
	 * 
	 * The purpose of this method is to take in a list index and return the ToDos in
	 * that list. It calls the model's getListAtIndex method in order to get the
	 * list at the specified index. It then calls The model's getToDos method to get
	 * the ToDo objects in the found list.
	 * 
	 * @param index An int representing a list index
	 * @return An ArrayList of ToDo objects
	 */
	public ArrayList<ToDo> getTodosAt(int index) {
		return this.model.getToDos(getListAtIndex(index));
	}

	/**
	 * This method returns ToDoList objects.
	 * 
	 * The purpose of this method is to get all the ToDoList objects that the model
	 * is storing. It calls the model's getToDoLists method in order to return the
	 * set of all lists.
	 * 
	 * @return A Set of ToDoList objects
	 */
	public Set<ToDoList> getLists() {
		return this.model.getToDoLists();
	}

	/**
	 * This method gets a list at a given index.
	 * 
	 * The purpose of this method is to take in an index value and return the
	 * ToDoList at that index. It calls the model's getListAtMethod in order to
	 * return the list at the specified index.
	 * 
	 * @param i An int representing an index
	 * @return A ToDoList object
	 */
	public ToDoList getListAtIndex(int i) {
		return this.model.getListAt(i);
	}

	/**
	 * This method renames a ToDo object.
	 * 
	 * The purpose of this method is take in a list object, an index and a new title
	 * in order to change the name of a ToDo. It creates a new ToDo object with all
	 * the features of the previous one, changing only the title to match the new
	 * one. It then replaces the old ToDo object in the model.
	 * 
	 * @param list A ToDoList object
	 * @param i    An int representing an index
	 * @param name A String representing a new title
	 */
	public void renameToDo(ToDoList list, int i, String name) {
		ToDo currToDo = this.model.getItemAt(list, i);
		ToDo newToDo = new ToDo(name, currToDo.getDescription(), currToDo.getLocation(), currToDo.getDeadline());
		newToDo.setImportant(currToDo.getImportant());
		this.model.replace(list, i, newToDo);
		setDone(newToDo, currToDo.getDone());
	}

	/**
	 * This method hides a ToDo object.
	 * 
	 * The purpose of this method is to take in a ToDo item and change its
	 * visibility to be hidden. Once setShown is set to false, the ToDo will no
	 * longer show up on the view.
	 * 
	 * @param item A ToDo object
	 */
	public void hide(ToDo item) {
		item.setShown(false);
	}

	/**
	 * This method shows all hidden ToDos.
	 * 
	 * The purpose of this method is to make all hidden ToDo objects visible on the
	 * view again. Once done, all ToDos marked as hidden will show up in their
	 * respective lists.
	 */
	public void makeAllShown() {
		for (Entry<ToDoList, ArrayList<ToDo>> entry : model.getMap().entrySet()) {
			for (ToDo todo : entry.getValue()) {
				todo.setShown(true);
			}
		}

	}

	/**
	 * This method sets an item to done or undone.
	 * 
	 * The purpose of this method is to take in a ToDo item and a boolean and set
	 * the ToDo's done field to the boolean. If a ToDo object is marked as done, it
	 * will be marked undone. If it is not done, it will be marked as done. This is
	 * indicated with an 'X' before the title.
	 * 
	 * @param todo A ToDo object
	 * @param i    A boolean representing how the object should be marked
	 */
	public void setDone(ToDo todo, boolean i) {
		todo.setDone(i);
		String title = todo.getTitle();

		if (i == true && !title.startsWith("X  ")) {
			String toAdd = "X  ";
			todo.setTitle(toAdd + title);
		} else if (title.startsWith("X  ")) {
			todo.setTitle(title.substring(3));
			todo.setDone(false);
		}
	}

	/**
	 * This method checks if an item is marked as done.
	 * 
	 * The purpose of this method is to take in a ToDo object and check if its done
	 * field is marked true or false. It will return the associated value.
	 * 
	 * @param todo A ToDo object
	 * @return A boolean representing if the item is done or not
	 */
	public boolean isDone(ToDo todo) {
		return todo.getDone();
	}

	/**
	 * This method calls the model to save the ToDo date in a file.
	 */
	public void saveState() {
		model.save();

	}

	/**
	 * This method calls the model to load ToDo data from a file.
	 * 
	 * @return A TreeMap of all saved lists and ToDos
	 */
	public TreeMap<ToDoList, ArrayList<ToDo>> loadState() {
		return model.load();
	}

}

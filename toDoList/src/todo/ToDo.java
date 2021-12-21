package todo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class holds the ToDo object.
 * 
 * The purpose of this class is to create a ToDo object with all the necessary
 * fields. This allows us to create ToDo items for our lists and interact with
 * them.
 * 
 * @author Zachary Winans, Laynah Varnum, Ziad Hakim, Jiazheng Huang
 */

public class ToDo implements Serializable {

	// Private Fields
	private String title;
	private String description;
	private LocalDateTime deadline;
	private LocalDateTime creation;
	private boolean important;
	private boolean done;
	private boolean shown;
	private String location;

	/**
	 * This is the class's constructor that sets up the fields.
	 * 
	 * @param title         A String for the title
	 * @param description   A String for the description
	 * @param location      A String for the location
	 * @param localDateTime A localDateTime object for creation time
	 */
	public ToDo(String title, String description, String location, LocalDateTime localDateTime) {
		this.title = title;
		this.description = description;
		deadline = localDateTime;
		this.location = location; // This will need to come from the constructor
		creation = LocalDateTime.now();
		important = false;
		done = false;
		shown = true;
	}

	/**
	 * This method sets the title field.
	 * 
	 * @param title A String for the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method sets the description field.
	 * 
	 * @param description A String for the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * This method returns the title.
	 * 
	 * @return A String representing the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method returns the description.
	 * 
	 * @return A String representing the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * This method returns the deadline
	 * 
	 * @return A LocalDateTime object representing the deadline
	 */
	public LocalDateTime getDeadline() {
		return deadline;
	}

	/**
	 * This method returns the creation time.
	 * 
	 * @return A LocalDateTime object representing the creation
	 */
	public LocalDateTime getCreation() {
		return creation;
	}

	/**
	 * This method sets a ToDo as important and updates the title display.
	 * 
	 * @param i An int representing how the important field should be marked
	 */
	public void setImportant(boolean i) {
		important = i;
		if (important == true) {
			this.setTitle("!  " + title);
		} else {
			this.setTitle(title);
		}
	}

	/**
	 * This method returns the boolean of if a ToDo is important or not.
	 * 
	 * @return A boolean representing important's value
	 */
	public boolean getImportant() {
		return important;
	}

	/**
	 * This method sets the done field.
	 * 
	 * @param d A boolean representing how to set done
	 */
	public void setDone(boolean d) {
		done = d;
	}

	/**
	 * This method returns done's value.
	 * 
	 * @return A boolean representing if a ToDo is done or not
	 */
	public boolean getDone() {
		return done;
	}

	/**
	 * This method sets the shown field.
	 * 
	 * @param s A boolean indication how to set shown
	 */
	public void setShown(boolean s) {
		shown = s;
	}

	/**
	 * This method returns shown's value.
	 * 
	 * @return A boolean indicating whether a ToDo is hidden or not
	 */
	public boolean getShown() {
		return shown;
	}

	/**
	 * This method returns the location field.
	 * 
	 * @return A String representing location
	 */
	public String getLocation() {
		return location;
	}
}

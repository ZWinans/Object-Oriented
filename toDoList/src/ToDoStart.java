import view.ToDoView;
import javafx.application.Application;

/**
 * This class starts the application.
 * 
 * The purpose of this class is to start the ToDo application's view. Once
 * loaded, the user can start interacting with the application.
 * 
 * @author Zachary Winans, Laynah Varnum, Ziad Hakim, Jiazheng Huang
 *
 */

public class ToDoStart {

	public static void main(String[] args) {

		Application.launch(ToDoView.class, args);

	}

}
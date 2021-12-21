package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

import controller.ToDoController;
import model.ToDoModel;
import todo.ToDo;
import todo.ToDoList;

/**
 * This class tests functionality.
 * 
 * The purpose of this class is to test the methods in the model and controller.
 * It should show that all implemented logic works correctly.
 * 
 * @author Zachary Winans, Laynah Varnum, Ziad Hakim, Jiazheng Huang
 */

public class ToDoTests {

	/**
	 * This method tests that a new list and new ToDo object can be added properly.
	 */
	@Test
	public void testAdd() {
		ToDoModel model = new ToDoModel();
		ToDoController controller = new ToDoController(model);

		ToDoList list = new ToDoList("List 1");
		controller.addList(list);
		assertEquals(model.getListAt(0).getName(), "List 1");
		ToDo item = new ToDo("Grocery Shopping", "Get milk and eggs.", "Safeway", LocalDateTime.now());
		controller.add(list, item);
		assertEquals(model.getItemAt(list, 0).getTitle(), "Grocery Shopping");

		list = new ToDoList("List 2");
		controller.addList(list);
		assertEquals(model.getListAt(1).getName(), "List 2");
		item = new ToDo("Clean House", null, "Tucson, AZ", LocalDateTime.now());
		controller.add(list, item);
		assertEquals(model.getItemAt(list, 0).getTitle(), "Clean House");
		assertEquals(model.getItemAt(list, 0).getLocation(), "Tucson, AZ");
		item = new ToDo("Clean Car", "Go to car wash.", "Canada", LocalDateTime.now());
		controller.add(list, item);
		assertEquals(model.getItemAt(list, 1).getTitle(), "Clean Car");
		assertEquals(model.getItemAt(list, 1).getDescription(), "Go to car wash.");
	}

	/**
	 * This method tests that a ToDo object can be deleted from a list.
	 */
	@Test
	public void testDelete() {
		ToDoModel model = new ToDoModel();
		ToDoController controller = new ToDoController(model);

		ToDoList list = new ToDoList("List 1");
		controller.addList(list);
		ToDo item = new ToDo("Grocery Shopping", "Get milk and eggs.", "Safeway", LocalDateTime.now());
		controller.add(list, item);
		controller.delete(list, item);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			model.getItemAt(list, 0);
		});

		item = new ToDo("Grocery Shopping", "Get milk and eggs.", "Safeway", LocalDateTime.now());
		controller.add(list, item);
		assertEquals(model.getItemAt(list, 0).getTitle(), "Grocery Shopping");
	}

	/**
	 * This method tests that a ToDo object's title can be changed and updated
	 * properly.
	 */
	@Test
	public void testUpdate() {
		ToDoModel model = new ToDoModel();
		ToDoController controller = new ToDoController(model);

		ToDoList list = new ToDoList("List 1");
		controller.addList(list);
		ToDo item = new ToDo("Grocery Shopping", "Get milk and eggs.", "Safeway", LocalDateTime.now());
		controller.add(list, item);
		controller.renameToDo(list, 0, "Shopping");
		assertEquals(model.getItemAt(list, 0).getTitle(), "Shopping");
	}

	/**
	 * This method tests that a ToDo object can be marked done and undone.
	 */
	@Test
	public void testDone() {
		ToDoModel model = new ToDoModel();
		ToDoController controller = new ToDoController(model);

		ToDoList list = new ToDoList("List 1");
		controller.addList(list);
		ToDo item = new ToDo("Grocery Shopping", "Get milk and eggs.", "Safeway", LocalDateTime.now());
		controller.add(list, item);
		assertFalse(model.getItemAt(list, 0).getDone());
		controller.setDone(item, true);
		assertTrue(model.getItemAt(list, 0).getDone());
		controller.setDone(item, false);
		assertFalse(model.getItemAt(list, 0).getDone());
	}

	/**
	 * This method tests that a ToDo object can be marked as important.
	 */
	@Test
	public void testImportant() {
		ToDoModel model = new ToDoModel();
		ToDoController controller = new ToDoController(model);

		ToDoList list = new ToDoList("List 1");
		controller.addList(list);
		ToDo item = new ToDo("Grocery Shopping", "Get milk and eggs.", "Safeway", LocalDateTime.now());
		controller.add(list, item);
		assertFalse(model.getItemAt(list, 0).getImportant());
		item.setImportant(true);
		assertTrue(model.getItemAt(list, 0).getImportant());
	}

	/**
	 * This method tests all sorting functionality making sure each sorting method
	 * does what is intended.
	 */
	@Test
	public void testSort() {
		ToDoModel model = new ToDoModel();
		ToDoController controller = new ToDoController(model);

		ToDoList list = new ToDoList("List 1");
		controller.addList(list);
		ToDo item = new ToDo("Grocery Shopping", "Get milk and eggs.", "Safeway", LocalDateTime.now());
		controller.add(list, item);
		item = new ToDo("Clean Room", null, "Tucson, AZ", LocalDateTime.now());
		controller.add(list, item);
		item = new ToDo("Doctor's Appointment", "Just a check-up.", "Tucson, AZ", LocalDateTime.now());
		controller.add(list, item);
		assertEquals(model.getItemAt(list, 0).getTitle(), "Grocery Shopping");
		assertEquals(model.getItemAt(list, 1).getTitle(), "Clean Room");
		assertEquals(model.getItemAt(list, 2).getTitle(), "Doctor's Appointment");

		controller.sort(list, "name");
		assertEquals(model.getItemAt(list, 2).getTitle(), "Grocery Shopping");
		assertEquals(model.getItemAt(list, 0).getTitle(), "Clean Room");
		assertEquals(model.getItemAt(list, 1).getTitle(), "Doctor's Appointment");

		controller.sort(list, "creation");
		assertEquals(model.getItemAt(list, 2).getTitle(), "Grocery Shopping");
		assertEquals(model.getItemAt(list, 0).getTitle(), "Clean Room");
		assertEquals(model.getItemAt(list, 1).getTitle(), "Doctor's Appointment");

		item = controller.getTodoAtIndex(list, 1);
		item.setImportant(true);
		controller.sort(list, "important");
		assertEquals(model.getItemAt(list, 2).getTitle(), "Grocery Shopping");
		assertEquals(model.getItemAt(list, 1).getTitle(), "Clean Room");
		assertEquals(model.getItemAt(list, 0).getTitle(), "!  Doctor's Appointment");

		controller.sort(list, "deadline");
		assertEquals(model.getItemAt(list, 2).getTitle(), "Grocery Shopping");
		assertEquals(model.getItemAt(list, 1).getTitle(), "Clean Room");
		assertEquals(model.getItemAt(list, 0).getTitle(), "!  Doctor's Appointment");
	}

	/**
	 * This method tests that a ToDo object can be hidden and then shown again.
	 */
	@Test
	public void testHide() {
		ToDoModel model = new ToDoModel();
		ToDoController controller = new ToDoController(model);

		ToDoList list = new ToDoList("List 1");
		controller.addList(list);
		ToDo item = new ToDo("Grocery Shopping", "Get milk and eggs.", "Safeway", LocalDateTime.now());
		controller.add(list, item);
		assertTrue(model.getItemAt(list, 0).getShown());
		controller.hide(item);
		assertFalse(model.getItemAt(list, 0).getShown());
		controller.makeAllShown();
		assertTrue(model.getItemAt(list, 0).getShown());
	}
}

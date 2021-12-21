package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javax.swing.event.ChangeEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import controller.ToDoController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ToDoModel;
import store.ToDoStore;
import todo.ToDo;
import todo.ToDoList;

/**
 * 
 * @author Zachary Winans, Laynah Varnum, Ziad Hakim, Jiazheng Huang
 *
 */

@SuppressWarnings("deprecation")
public class ToDoView extends Application implements Observer {

	// Private Fields
	private ToDoModel model;
	private ToDoController controller;

	private Button startBtn, loadBtn, btn1, btn2;
	private MenuButton menuButton;
	private BorderPane window;
	private VBox vbox;
	private VBox detailsVBox;
	private HBox hbox;
	private Label label, title, description, date, location; // add important?
	private FileInputStream imageInput;
	private Image image;
	private ImageView imageView;
	private ListView<String> allLists;
	private ObservableList<String> allListsItems;
	private ListView<String> todos;
	private ObservableList<String> todosItems;
	private ListView<ToDo> todoDetails;
	private ObservableList<ToDo> todoDetailsItems;
	private CheckBox cb;
	private CheckBox done;
	private boolean loaded = false;
	private Media media;
	private MediaPlayer mediaPlayer;
	private AudioClip clickSound;
	private AudioClip chaching;
	private AudioClip boo;

	private int lastListIndex;

	/**
	 * This method starts the view and sets up the UI for the user.
	 * 
	 * @param stage A Stage object
	 */
	@Override
	public void start(Stage stage) throws Exception {
		String mediaPath = "src/assets/cant.mp3";
		media = new Media(new File(mediaPath).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		clickSound = new AudioClip(new File("src/assets/click.wav").toURI().toString());
		chaching = new AudioClip(new File("src/assets/chaching.wav").toURI().toString());
		boo = new AudioClip(new File("src/assets/boo.wav").toURI().toString());

		window = new BorderPane();

		welcomePage(window);

		if (loaded == false) {
			model = new ToDoModel();
			controller = new ToDoController(model);
		}

		model.addObserver(this);

		allLists = new ListView<String>();
		allListsItems = FXCollections.observableArrayList();
		todos = new ListView<String>();
		todosItems = FXCollections.observableArrayList();
		todoDetails = new ListView<ToDo>();

		allLists.setOnMouseClicked((event) -> {
			lastListIndex = allLists.getSelectionModel().getSelectedIndex();
			displayList(window, lastListIndex);
		});

		todos.setOnMouseClicked((event) -> {
			int toDoIndex = todos.getSelectionModel().getSelectedIndex();
			displayToDoDetails(window, toDoIndex);
		});

		imageInput = new FileInputStream("src/view/view.css");
		Scene scene = new Scene(window, 800, 500);
		scene.getStylesheets().add(this.getClass().getResource("view-2.css").toExternalForm());

		stage.setScene(scene);
		stage.setTitle("ToDo");
		stage.show();

	}

	/**
	 * This method displays the multiple lists and their associated ToDos.
	 * 
	 * @param window    A BorderPane
	 * @param listIndex An int representing an index
	 */
	private void displayList(BorderPane window, int listIndex) {
		todosItems.clear();
		allListsItems.clear();

		for (ToDoList list : controller.getLists()) {
			allListsItems.add(list.getName());
		}

		for (ToDo todo : controller.getTodosAt(listIndex)) {
			if (todo.getShown() == true) {
				todosItems.add(todo.getTitle());
			}
		}

		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setPadding(new Insets(200, 120, 10, 10));
		grid.setStyle("-fx-background-color: #C0C0C0;");

		ToDoList currList = controller.getListAtIndex(listIndex);

	}

	/**
	 * This method displays an individual ToDo object's details to the user.
	 * 
	 * @param window    A BorderPane
	 * @param todoIndex An int representing an index
	 */
	private void displayToDoDetails(BorderPane window, int todoIndex) {
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setPadding(new Insets(200, 120, 10, 10));
		grid.setStyle("-fx-background-color: #C0C0C0;");

		ToDoList currList = controller.getListAtIndex(lastListIndex);

		ArrayList<ToDo> currToDos = controller.getTodos(currList);

		int otherIndex = 0;

		if (currToDos.get(todoIndex).getShown() == false) {
			for (int i = todoIndex; i < currToDos.size(); i++) {
				if (currToDos.get(i).getShown() == true) {
					otherIndex = i;
				}
			}
		}

		ToDo currItem = null;

		if (currToDos.get(todoIndex).getShown() == true) {
			currItem = currToDos.get(todoIndex);
		} else {
			currItem = currToDos.get(otherIndex);
		}

		if (currItem.getShown() == true) {
			title = new Label();
			description = new Label();
			date = new Label();
			location = new Label();

			title.setText("Title: " + currItem.getTitle());
			description.setText("Description: " + currItem.getDescription());
			date.setText("Date: " + currItem.getDeadline().toString().substring(0, 10));
			location.setText("Location: " + currItem.getLocation());
		}

		detailsVBox = new VBox(10);
		detailsVBox.setPadding(new Insets(0, 75, 0, 75));
		detailsVBox.setAlignment(Pos.CENTER);

		Button deleteButton = new Button("Delete");
		deleteButton.setId("button1");

		deleteButton.setOnAction((event) -> {
			clickSound.play();
			controller.delete(currList, controller.getTodoAtIndex(currList, todoIndex));
			updateList();
		});

		Button renameButton = new Button("Rename");
		renameButton.setId("button1");

		renameButton.setOnAction((event) -> {
			clickSound.play();
			showDialog(currList);
		});

		Button hideButton = new Button("Hide");
		hideButton.setId("button1");

		hideButton.setOnAction((event) -> {
			clickSound.play();
			controller.hide(controller.getTodoAtIndex(currList, todoIndex));
			updateList();
		});

		done = new CheckBox("Done");
		if (controller.getTodoAtIndex(currList, todoIndex).getDone() == true) {
			done.setIndeterminate(true);
		} else {
			done.setIndeterminate(false);
		}

		done.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				boolean isDone = controller.isDone(controller.getTodoAtIndex(currList, todoIndex));
				if (isDone) {
					boo.play();
					controller.setDone(controller.getTodoAtIndex(currList, todoIndex), done.isSelected());

				} else {
					controller.setDone(controller.getTodoAtIndex(currList, todoIndex), done.isSelected());
					chaching.play();
				}
				updateList();
			}
		});

		hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(deleteButton, renameButton, hideButton);

		detailsVBox.getChildren().addAll(title, description, location, date, done, hbox);
		window.setRight(detailsVBox);
	}

	/**
	 * This method shows the dialog to rename a ToDo object.
	 * 
	 * @param currList A TodoList object
	 */
	private void showDialog(ToDoList currList) {
		TextInputDialog renameDialog = new TextInputDialog();
		renameDialog.setHeaderText("Rename the ToDo");
		renameDialog.setContentText("Enter new name: ");

		Optional<String> result = renameDialog.showAndWait();
		if (result.isPresent() && !renameDialog.getEditor().getText().isEmpty()) {
			controller.renameToDo(currList, todos.getSelectionModel().getSelectedIndex(),
					renameDialog.getEditor().getText());
			updateList();
		} else {
			updateList();
		}
	}

	/**
	 * This method shows the welcome page to the user allowing them to start or
	 * load.
	 * 
	 * @param window A BorderPane
	 * @throws FileNotFoundException
	 */
	private void welcomePage(BorderPane window) throws FileNotFoundException {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));

		vbox = new VBox(20);
		vbox.setAlignment(Pos.CENTER);

		imageInput = new FileInputStream("src/images/todo-logo.png");
		image = new Image(imageInput, 100, 100, false, false);
		imageView = new ImageView(image);
		label = new Label("", imageView);
		vbox.getChildren().add(label);

		label = new Label("Welcome to the TODO!!! \n Click \"Start\" to continue");
		label.setId("text1");
		vbox.getChildren().add(label);

		startBtn = new Button("Start");
		startBtn.setId("button2");
		vbox.getChildren().add(startBtn);

		startBtn.setOnAction((event) -> {
			clickSound.play();
			window.setRight(null);
			createToDoList(window);
		});

		loadBtn = new Button("Load");
		loadBtn.setId("button2");
		vbox.getChildren().add(loadBtn);

		loadBtn.setOnAction((event) -> {
			clickSound.play();
			loaded = true;
			model = new ToDoModel(controller.loadState());
			controller = new ToDoController(model);
			updateList();

		});

		grid.add(vbox, 0, 0);
		window.setCenter(grid);
		window.setBottom(hbox);
	}

	/**
	 * This method prompts the user to add a new list.
	 * 
	 * @param window A BorderPane
	 */
	private void createToDoList(BorderPane window) {
		GridPane grid = new GridPane();
		grid.setStyle("-fx-background-color: #C0C0C0;");
		grid.setAlignment(Pos.CENTER);

		hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER);

		vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);

		label = new Label("Add a New To Do List");
		label.setId("text1");

		TextField name = new TextField();
		name.setPromptText("Name");
		name.setPrefColumnCount(10);
		name.getText();

		Button submit = new Button("Add");
		submit.setId("button1");

		submit.setOnAction((event) -> {
			clickSound.play();
			if (name.getText() != null && !name.getText().isEmpty()) {
				ToDoList newList = new ToDoList(name.getText());
				controller.addList(newList);
				updateList();
			}
		});

		Button cancel = new Button("Cancel");
		cancel.setId("button1");

		cancel.setOnAction((event) -> {
			clickSound.play();
			if (controller.getMap().size() == 0) {
				try {
					welcomePage(window);
					return;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			updateList();
		});

		hbox.getChildren().addAll(submit, cancel);
		vbox.getChildren().addAll(label, name, hbox);
		grid.getChildren().add(vbox);

		window.setRight(null);
		window.setCenter(grid);
	}

	/**
	 * This method prompts the user to add a new ToDo to a list.
	 * 
	 * @param window A BorderPane
	 * @param i      An int representing an index
	 */
	private void createToDo(BorderPane window, int i) {
		GridPane grid = new GridPane();
		grid.setStyle("-fx-background-color: #C0C0C0;");
		grid.setAlignment(Pos.CENTER);

		hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER);

		vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);

		label = new Label("Add a new task to your To Do List!!!");
		label.setId("text1");

		TextField title = new TextField();
		title.setPromptText("Title");
		title.setPrefColumnCount(10);
		title.getText();

		TextField description = new TextField();
		description.setPromptText("Description");

		TextField location = new TextField();
		location.setPromptText("Location");

		DatePicker date = new DatePicker();
		date.setPromptText("Date due");

		cb = new CheckBox("Important");
		cb.setIndeterminate(false);

		Button submit = new Button("Add");
		submit.setId("button1");

		submit.setOnAction((event) -> {
			clickSound.play();
			if (title.getText() != null && !title.getText().isEmpty() && description.getText() != null
					&& !description.getText().isEmpty() && location.getText() != null
					&& !location.getText().isEmpty()) {
				LocalDateTime localDateTime = date.getValue().atTime(LocalTime.now());
				ToDo todo = new ToDo(title.getText(), description.getText(), location.getText(), localDateTime);
				ToDoList currList = controller.getListAtIndex(i);

				todo.setImportant(cb.isSelected());

				controller.add(currList, todo);

				updateList();
			}
		});

		Button cancel = new Button("Cancel");
		cancel.setId("button1");

		cancel.setOnAction((event) -> {
			clickSound.play();
			updateList();
		});

		hbox.getChildren().addAll(submit, cancel);
		vbox.getChildren().addAll(label, title, description, location, date, cb, hbox);
		grid.getChildren().add(vbox);

		window.setRight(null);
		window.setCenter(grid);
	}

	/**
	 * This method handles the logic for updating the view.
	 */
	private void updateList() {
		allListsItems.clear();
		todosItems.clear();

		for (ToDoList list : controller.getLists()) {
			allListsItems.add(list.getName());
		}

		for (ToDo todo : controller.getTodosAt(lastListIndex)) {
			if (todo.getShown() == true) {
				todosItems.add(todo.getTitle());
			}
		}

		ToDoList currList = controller.getListAtIndex(lastListIndex);

		HBox outerHBox = new HBox(90);

		HBox hboxLeft = new HBox(10);
		hboxLeft.setPadding(new Insets(10, 10, 10, 10));

		HBox hboxCenter = new HBox(10);
		hboxCenter.setPadding(new Insets(10, 10, 10, 10));

		HBox hboxRight = new HBox(10);
		hboxRight.setPadding(new Insets(10, 10, 10, 10));

		Button newListBtn = new Button("+");
		newListBtn.setId("button1");

		newListBtn.setOnAction((event) -> {
			clickSound.play();
			window.setRight(null);
			createToDoList(window);
		});

		Button saveListBtn = new Button("Save");
		saveListBtn.setId("button1");

		saveListBtn.setOnAction((event) -> {
			clickSound.play();
			controller.saveState();
			Alert a = new Alert(Alert.AlertType.INFORMATION);
			a.setTitle("");
			a.setHeaderText("File Saved!");
			a.showAndWait();
			return;

		});

		Button newToDoBtn = new Button("Add To Do");
		newToDoBtn.setId("button1");

		newToDoBtn.setOnAction((event) -> {
			clickSound.play();
			createToDo(window, lastListIndex);
		});

		MenuButton sortToDoBtn = new MenuButton("Sort");
		sortToDoBtn.setId("button1");
		sortToDoBtn.getItems().addAll(new MenuItem("Name"), new MenuItem("Date Added"), new MenuItem("Due Date"),
				new MenuItem("Importance"));

		handleSortButton(currList, sortToDoBtn);

		Button showHiddenBtn = new Button("Show all hidden");
		showHiddenBtn.setId("button1");

		showHiddenBtn.setOnAction((event) -> {
			clickSound.play();
			controller.makeAllShown();
			updateList();
		});

		FileInputStream input = null, unmute = null;
		try {
			input = new FileInputStream("src/images/music-icon-mute.png");
			unmute = new FileInputStream("src/images/music-icon.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Image musicImage = new Image(input, 20, 20, false, false);
		Image unmuteImage = new Image(unmute, 20, 20, false, false);
		ImageView musicView = new ImageView(musicImage);
		ImageView unmuteView = new ImageView(unmuteImage);

		Button musicBtn = new Button();
		musicBtn.setId("button1");
		musicBtn.setPrefSize(10, 10);
		musicBtn.setGraphic(musicView);

		musicBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			if (mediaPlayer.getStatus().equals(MediaPlayer.Status.READY)
					|| mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
				mediaPlayer.play();
				musicBtn.setGraphic(unmuteView);
			} else {
				mediaPlayer.pause();
				musicBtn.setGraphic(musicView);
			}

		});

		hboxLeft.getChildren().addAll(newListBtn, saveListBtn);
		hboxCenter.getChildren().addAll(newToDoBtn, sortToDoBtn, showHiddenBtn);
		hboxRight.getChildren().add(musicBtn);
		outerHBox.getChildren().addAll(hboxLeft, hboxCenter, hboxRight);
		allLists.setItems(allListsItems);
		todos.setItems(todosItems);
		window.setLeft(allLists);
		window.setCenter(todos);
		window.setRight(null);
		window.setBottom(outerHBox);
	}

	/**
	 * This method displays and handles the sorting functionality.
	 * 
	 * @param currList    A ToDoList object
	 * @param sortListBtn A MenuButton
	 */
	private void handleSortButton(ToDoList currList, MenuButton sortListBtn) {

		sortListBtn.getItems().get(0).setOnAction(event -> {

			controller.sort(currList, "name");
			updateList();
		});

		sortListBtn.getItems().get(1).setOnAction(event -> {
			controller.sort(currList, "creation");
			updateList();
		});

		sortListBtn.getItems().get(2).setOnAction(event -> {
			controller.sort(currList, "deadline");
			updateList();
		});

		sortListBtn.getItems().get(3).setOnAction(event -> {
			controller.sort(currList, "important");
			updateList();
		});

	}

	/**
	 * This method calls to update the view.
	 * 
	 * @param o   An Observable object
	 * @param arg An Object
	 */
	@Override
	public void update(Observable o, Object arg) {
		updateList();

	}
}

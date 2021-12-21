import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.MastermindController;
import exceptions.MastermindIllegalColorException;
import exceptions.MastermindIllegalLengthException;
import model.MastermindModel;

/**
 * This class collects all of the test methods for our controller.
 * 
 * In eclipse, running it should run it under JUnit. Running the Mastermind
 * class (since it is our main class) will actually run the program. If you're
 * not using eclipse, you'll need to run this under JUnit 5.
 * 
 * @author Zachary Winans
 *
 */
class MastermindTest {

	/**
	 * Test method for {@link MastermindController#isCorrect(java.lang.String)}.
	 * 
	 * @throws MastermindIllegalColorException
	 * @throws MastermindIllegalLengthException
	 */
	@Test
	void testIsCorrect() throws MastermindIllegalLengthException, MastermindIllegalColorException {
		// Build a model with a known answer, using our special testing constructor
		MastermindModel answer = new MastermindModel("rrrr");
		// Build the controller from the model
		MastermindController controllerUnderTest = new MastermindController(answer);

		// For a properly working controller, this should return true
		assertTrue(controllerUnderTest.isCorrect("rrrr"));

		// For a properly working controller, this should be false
		assertFalse(controllerUnderTest.isCorrect("oooo"));

		// For a properly working controller, this should throw an illegal length
		// exception
		assertThrows(MastermindIllegalLengthException.class, () -> {
			controllerUnderTest.isCorrect("rrrrr");
		});

		// For a properly working controller, this should throw an illegal length
		// exception
		assertThrows(MastermindIllegalLengthException.class, () -> {
			controllerUnderTest.isCorrect("rrr");
		});

		// For a properly working controller, this should throw an illegal length
		// exception
		assertThrows(MastermindIllegalLengthException.class, () -> {
			controllerUnderTest.isCorrect("");
		});

		// For a properly working controller, this should throw an illegal color
		// exception
		assertThrows(MastermindIllegalColorException.class, () -> {
			controllerUnderTest.isCorrect("xxxx");
		});

		// Make as many more assertions as you feel you need to test the
		// MastermindController.isCorrect method
	}

	/**
	 * Test method for
	 * {@link MastermindController#getRightColorRightPlace(java.lang.String)}.
	 * 
	 * @throws MastermindIllegalColorException
	 * @throws MastermindIllegalLengthException
	 */
	@Test
	void testGetRightColorRightPlace() throws MastermindIllegalColorException, MastermindIllegalLengthException {
		// Build a model with a known answer, using our special testing constructor
		MastermindModel answer = new MastermindModel("orgb");
		// Build the controller from the model
		MastermindController controllerUnderTest = new MastermindController(answer);

		// For a properly working controller, this should return 1
		assertEquals(controllerUnderTest.getRightColorRightPlace("rrrr"), 1);

		// For a properly working controller, this should return 1
		assertEquals(controllerUnderTest.getRightColorRightPlace("oooo"), 1);

		// For a properly working controller, this should return 0
		assertEquals(controllerUnderTest.getRightColorRightPlace("yyyy"), 0);

		// For a properly working controller, this should return 3
		assertEquals(controllerUnderTest.getRightColorRightPlace("orgo"), 3);

		// For a properly working controller, this should throw an illegal color exception
		assertThrows(MastermindIllegalColorException.class, () -> {
			controllerUnderTest.getRightColorRightPlace("xxxx");
		});
		
		assertThrows(MastermindIllegalLengthException.class, () -> {
			controllerUnderTest.getRightColorRightPlace("rrr");
		});
		
		MastermindModel answer2 = new MastermindModel();
		MastermindController controllerUnderTest2 = new MastermindController(answer);
		
		//For a properly working controller, this will throw an illegal length exception
		assertThrows(MastermindIllegalLengthException.class, () -> {
			controllerUnderTest2.getRightColorRightPlace("rrrrr");
		});

		// You'll need lots more of these to convince yourself your implementation is
		// right
	}

	/**
	 * Test method for
	 * {@link MastermindController#getRightColorWrongPlace(java.lang.String)}.
	 */
	@Test
	void testGetRightColorWrongPlace() throws MastermindIllegalColorException, MastermindIllegalLengthException {

		MastermindModel answer = new MastermindModel("orgb");
		MastermindController controllerUnderTest = new MastermindController(answer);

		// For a properly working controller, this should return 2
		assertEquals(controllerUnderTest.getRightColorWrongPlace("brgo"), 2);

		// For a properly working controller, this should return 4
		assertEquals(controllerUnderTest.getRightColorWrongPlace("bgro"), 4);

		// For a properly working controller, this should return 1
		assertEquals(controllerUnderTest.getRightColorWrongPlace("byyy"), 1);

		// For a properly working controller, this should throw an illegal length
		// exception
		assertThrows(MastermindIllegalLengthException.class, () -> {
			controllerUnderTest.getRightColorWrongPlace("rrrrr");
		});
		
		MastermindModel answer2 = new MastermindModel();
		MastermindController controllerUnderTest2 = new MastermindController(answer);
		
		assertThrows(MastermindIllegalLengthException.class, () -> {
			controllerUnderTest2.getRightColorWrongPlace("rrrrr");
		});
		

	}

}

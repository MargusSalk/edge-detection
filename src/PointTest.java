import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.margus.edgeDetection.utilities.Point;

class PointTest {

	@Test
	void equalityTest() {
		Point pointA = new Point(1, 2);
		Point pointB = new Point(1, 2);
		assertEquals(pointA, pointB);
	}
	
	@Test
	public void testNotEquals() throws Exception {
		Point pointB = new Point(1, 2);
		Point pointC = new Point(2, 3);
		assertNotEquals(pointB, pointC);
	}

}

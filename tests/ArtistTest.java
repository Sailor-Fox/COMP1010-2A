package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import doNotModify.CustomTestMethodOrder;
import doNotModify.Graded;
import doNotModify.Order;
import stage1.Artist;

@TestMethodOrder(CustomTestMethodOrder.class)
public class ArtistTest {
	
	Artist tay;
	Artist billie;
	Artist ed;
	Artist twice;
	Artist bp;
	Artist lilas;
	Artist a;
	Artist hilary;
	Artist fred;
	
	@BeforeEach
	public void setup() {
		tay = new Artist("Taylor Swift");
		billie = new Artist("Billie Eilish");
		ed = new Artist("ed Sheeran");
		twice = new Artist("TWICE");
		bp = new Artist("BLACKPINK");
		lilas = new Artist("Lilas Ikuta");
		a = new Artist("a");
		hilary = new Artist("Hilary Hahn");
		fred = new Artist("freddie Mercury");
	}

	@Test
	@Graded(description = "Artist(String, String)", marks = 2)
	@Order(1)
	public void testArtist() {
		assertEquals("Taylor Swift", tay.name);
		assertEquals("Billie Eilish", billie.name);
		assertEquals("ed Sheeran", ed.name);
		assertEquals("TWICE", twice.name);
		assertEquals("BLACKPINK", bp.name);
		assertEquals("Lilas Ikuta", lilas.name);
		assertEquals("freddie Mercury", fred.name);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Graded(description = "validName()", marks = 2)
	@Order(2)
	public void testValidName() {
		assertTrue(tay.validName());
		assertTrue(billie.validName());
		assertFalse(ed.validName());
		assertTrue(twice.validName());
		assertTrue(bp.validName());
		assertTrue(lilas.validName());
		assertFalse(a.validName());
		assertTrue(hilary.validName());
		assertFalse(fred.validName());

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Graded(description = "equals(Artist)", marks = 2)
	@Order(3)
	public void testEquals() {
		Artist other = new Artist("Taylor Swift");
		assertTrue(tay.equals(other));
		
		other = new Artist("Baylor Swift");
		assertFalse(tay.equals(other));
		
		other = new Artist("TAYLOR SWIFT");
		assertFalse(tay.equals(other));
		
		other = new Artist("Taylor");
		assertFalse(tay.equals(other));
		
		other = new Artist("a");
		assertTrue(a.equals(other));
		
		other = new Artist("TWICE");
		assertTrue(twice.equals(other));
		
		other = new Artist("twice");
		assertFalse(twice.equals(other));

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Graded(description = "toString()", marks = 2)
	@Order(4)
	public void testToString() {
		assertEquals("Artist: Taylor Swift", tay.toString());
		assertEquals("Artist: BLACKPINK", bp.toString());
		assertEquals("Artist: ed Sheeran", ed.toString());
		assertEquals("Artist: Lilas Ikuta", lilas.toString());
		assertEquals("Artist: Billie Eilish", billie.toString());
		
		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	

	// =========================================
	// =========================================
	// =========================================

	public static double score = 0;
	public static String result = "";
	public static String currentMethodName = null;
	ArrayList<String> methodsPassed = new ArrayList<String>();

	@BeforeEach
	public void run() {
		currentMethodName = null;
	}

	@AfterEach
	public void logSuccess() throws NoSuchMethodException, SecurityException {
		if (currentMethodName != null
				&& !methodsPassed.contains(currentMethodName)) {
			methodsPassed.add(currentMethodName);
			Method method = getClass().getMethod(currentMethodName);
			Graded graded = method.getAnnotation(Graded.class);
			score += graded.marks();
			score = Math.min(score, 100);
			result += graded.description() + " passed. Marks awarded: "
					+ graded.marks() + "\n";
			doNotModify.CSV.write(this.getClass().toString(), currentMethodName,
					graded.marks());
		}
	}

	@AfterAll
	public static void wrapUp() throws IOException {
		if (result.length() != 0) {
			result = result.substring(0, result.length() - 1); // remove the
																// last "\n"
		}
		System.out.println(result);
		System.out.println("\nMarks for this file: " + score);
	}
}

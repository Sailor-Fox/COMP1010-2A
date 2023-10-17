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
import doNotModify.Review;
import stage1.Artist;
import stage2.Song;

@TestMethodOrder(CustomTestMethodOrder.class)
public class SongTest {

	Song[] tay;
	Song[] billie;
	Song[] ed;
	Song[] twice;
	Song[] bp;
	Song[] hilary;

	public SongTest() {
		setup();
	}

	@BeforeEach
	public void setup() {
		tay = new Song[]{ // -----------
				new Song("Anti-Hero", 200, new Artist("Taylor Swift"), null),
				new Song("Love Story", 235, new Artist("Taylor Swift"), null),
				new Song("Blank Space", 231, new Artist("Taylor Swift"), null),
				new Song("Shake It Off", 199, new Artist("Taylor Swift"), null),
				new Song("You Belong with Me", 211, new Artist("Taylor Swift"),
						null),
				new Song("Delicate", 190, new Artist("Taylor Swift"), null),
				new Song("All Too Well", 356, new Artist("Taylor Swift"), null),
				new Song("Red", 222, new Artist("Taylor Swift"), null),
				new Song("Style", 231, new Artist("Taylor Swift"), null),
				new Song("Wildest Dreams", 220, new Artist("Taylor Swift"),
						null),
				new Song("22", 185, new Artist("Taylor Swift"), null),
				new Song("ME!", 219, new Artist("Taylor Swift"), null),
				new Song("The Archer", 223, new Artist("Taylor Swift"), null), // 12
				new Song("Back to December", 263, new Artist("Taylor Swift"),
						null),
				new Song("Speak Now", 242, new Artist("Taylor Swift"), null),
				new Song("Fearless", 240, new Artist("Taylor Swift"), null),
				new Song("Dear John", 376, new Artist("Taylor Swift"), null),
				new Song("Enchanted", 303, new Artist("Taylor Swift"), null),
				new Song("White Horse", 223, new Artist("Taylor Swift"), null),
				new Song("Tim McGraw", 232, new Artist("Taylor Swift"), null)};

		billie = new Song[]{ // -----------
				new Song("Bad Guy", 194, new Artist("Billie Eilish"), null),
				new Song("Ocean Eyes", 193, new Artist("Billie Eilish"), null),
				new Song("When The Party's Over", 196,
						new Artist("Billie Eilish"), null),
				new Song("Bury A Friend", 193, new Artist("Billie Eilish"),
						null),
				new Song("Lovely", 200, new Artist("Billie Eilish"), null),
				new Song("Everything I Wanted", 245,
						new Artist("Billie Eilish"), null),
				new Song("Therefore I Am", 174, new Artist("Billie Eilish"),
						null),
				new Song("My Future", 213, new Artist("Billie Eilish"), null),
				new Song("I Love You", 294, new Artist("Billie Eilish"), null),
				new Song("No Time To Die", 263, new Artist("Billie Eilish"),
						null),
				new Song("Your Power", 245, new Artist("Billie Eilish"), null),
				new Song("Happier Than Ever", 256, new Artist("Billie Eilish"),
						null),
				new Song("Bored", 193, new Artist("Billie Eilish"), null),
				new Song("Ilomilo", 166, new Artist("Billie Eilish"), null),
				new Song("Copycat", 183, new Artist("Billie Eilish"), null),
				new Song("Xanny", 244, new Artist("Billie Eilish"), null),
				new Song("My Boy", 171, new Artist("Billie Eilish"), null),
				new Song("Hostage", 182, new Artist("Billie Eilish"), null),
				new Song("Ocean Eyes (Astronomyy Remix)", 236,
						new Artist("Billie Eilish"), null),
				new Song("Watch", 187, new Artist("Billie Eilish"), null)};

		ed = new Song[]{ // -----------
				new Song("Shape of You", 233, new Artist("ed Sheeran"), null),
				new Song("Thinking Out Loud", 281, new Artist("ed Sheeran"),
						null),
				new Song("Perfect", 263, new Artist("ed Sheeran"), null),
				new Song("Castle on the Hill", 261, new Artist("ed Sheeran"),
						null),
				new Song("Photograph", 258, new Artist("ed Sheeran"), null),
				new Song("Galway Girl", 170, new Artist("ed Sheeran"), null),
				new Song("The A Team", 258, new Artist("ed Sheeran"), null),
				new Song("I Don't Care", 219, new Artist("ed Sheeran"), null),
				new Song("Shape of You (Acoustic)", 237,
						new Artist("ed Sheeran"), null),
				new Song("Lego House", 240, new Artist("ed Sheeran"), null),};

		twice = new Song[]{ // -----------
				new Song("TT", 232, new Artist("TWICE"), null),
				new Song("Likey", 210, new Artist("TWICE"), null),
				new Song("Fancy", 203, new Artist("TWICE"), null),
				new Song("Cheer Up", 216, new Artist("TWICE"), null),
				new Song("Feel Special", 211, new Artist("TWICE"), null),
				new Song("Heart Shaker", 209, new Artist("TWICE"), null),
				new Song("Dance The Night Away", 198, new Artist("TWICE"),
						null),
				new Song("What is Love?", 208, new Artist("TWICE"), null),
				new Song("More & More", 206, new Artist("TWICE"), null),
				new Song("Cry For Me", 177, new Artist("TWICE"), null),};

		bp = new Song[]{ // -----------
				new Song("DDU-DU DDU-DU", 209, new Artist("BLACKPINK"), null),
				new Song("Kill This Love", 190, new Artist("BLACKPINK"), null),
				new Song("Lovesick Girls", 215, new Artist("BLACKPINK"), null),
				new Song("How You Like That", 181, new Artist("BLACKPINK"),
						null),
				new Song("BOOMBAYAH", 240, new Artist("BLACKPINK"), null),
				new Song("Ice Cream", 157, new Artist("BLACKPINK"), null),
				new Song("As If It's Your Last", 209, new Artist("BLACKPINK"),
						null),
				new Song("Whistle", 213, new Artist("BLACKPINK"), null),
				new Song("Playing With Fire", 190, new Artist("BLACKPINK"),
						null),
				new Song("Pretty Savage", 177, new Artist("BLACKPINK"), null),};

		hilary = new Song[]{ // -----------
				new Song("Bach: Violin Concerto in E Major, BWV 1042", 500,
						new Artist("Hilary Hahn"), null),
				new Song("Mendelssohn: Violin Concerto in E Minor, Op. 64", 340,
						new Artist("Hilary Hahn"), null),
				new Song("Tchaikovsky: Violin Concerto in D Major, Op. 35", 370,
						new Artist("Hilary Hahn"), null),
				new Song("Brahms: Violin Concerto in D Major, Op. 77", 400,
						new Artist("Hilary Hahn"), null),
				new Song("Sibelius: Violin Concerto in D Minor, Op. 47", 325,
						new Artist("Hilary Hahn"), null),
				new Song("Beethoven: Violin Concerto in D Major, Op. 61", 420,
						new Artist("Hilary Hahn"), null),
				new Song("Bach: Partita No. 2 in D Minor, BWV 1004", 330,
						new Artist("Hilary Hahn"), null),
				new Song("Paganini: Caprice No. 24 in A Minor", 220,
						new Artist("Hilary Hahn"), null),
				new Song("Mozart: Violin Concerto No. 3 in G Major, K. 216",
						270, new Artist("Hilary Hahn"), null),
				new Song("Bach: Sonata No. 1 in G Minor, BWV 1001", 280,
						new Artist("Hilary Hahn"), null),};
	}

	@Test
	@Graded(description = "Song(String, int, Artist, Review[])", marks = 2)
	@Order(1)
	public void testSong() {
		assertEquals("Anti-Hero", tay[0].name);
		assertEquals(200, tay[0].length);
		assertEquals("Taylor Swift", tay[0].artist.name);

		Artist tay2 = new Artist("Taylor Swift");
		Song taySong = new Song("song", 100, tay2, null);
		assertTrue(tay2 == taySong.artist); // Checking a shallow copy was used
											// for artist

		Review[] reviews = {new Review(1), new Review(5), new Review(10),};

		taySong = new Song("another song", 200, tay2, reviews);
		assertTrue(reviews == taySong.reviews);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "averageRating()", marks = 2)
	@Order(2)
	public void testAverageRating() {
		Review[] reviews = {new Review(1), new Review(5), new Review(10),};
		Song taySong = new Song("another song", 200, new Artist("Taylor Swift"),
				reviews);
		assertEquals(5.333, taySong.averageRating(), 0.001);

		reviews = new Review[]{new Review(1), new Review(5), new Review(10),
				new Review(10), new Review(3), new Review(2), new Review(10)};
		taySong = new Song("another song", 200, new Artist("Taylor Swift"),
				reviews);
		assertEquals(5.857142857142857, taySong.averageRating(), 0.001);

		reviews = new Review[]{new Review(1), new Review(5), new Review(10),
				new Review(10), new Review(3), new Review(2), new Review(10),
				new Review(1), new Review(1), new Review(5), new Review(10),
				new Review(10)};
		taySong = new Song("another song", 200, new Artist("Taylor Swift"),
				reviews);
		assertEquals(5.666666666666667, taySong.averageRating(), 0.001);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "compareTo(Song)", marks = 2)
	@Order(3)
	public void testCompareTo() {
		assertEquals(1, bp[0].compareTo(bp[3]));
		assertEquals(-1, bp[0].compareTo(bp[2]));
		assertEquals(1, bp[4].compareTo(bp[1]));
		assertEquals(1, tay[3].compareTo(bp[3]));
		assertEquals(1, ed[4].compareTo(bp[5]));
		assertEquals(1, hilary[0].compareTo(ed[2]));
		assertEquals(0, hilary[1].compareTo(hilary[1]));
		assertEquals(0, tay[12].compareTo(tay[18]));

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "toString()", marks = 2)
	@Order(4)
	public void testToString() {
		String answer = "Song name: Delicate\nlength: 190s\nArtist: Taylor Swift";
		assertEquals(answer, tay[5].toString());

		answer = "Song name: Paganini: Caprice No. 24 in A Minor\nlength: 220s\nArtist: Hilary Hahn";
		assertEquals(answer, hilary[7].toString());

		answer = "Song name: Dance The Night Away\nlength: 198s\nArtist: TWICE";
		assertEquals(answer, twice[6].toString());

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

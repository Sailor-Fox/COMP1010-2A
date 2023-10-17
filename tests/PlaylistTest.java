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
import stage3.Playlist;

@TestMethodOrder(CustomTestMethodOrder.class)
public class PlaylistTest {

	@Test
	@Graded(description = "countSongsBy(String)", marks = 2)
	@Order(1)
	public void testCountSongsBy() {
		Playlist p = new Playlist();

		// songs is empty
		assertEquals(0, p.countSongsBy("Taylor Swift"));
		// null artist name passed
		assertEquals(0, p.countSongsBy(null));

		p = new Playlist();
		SongTest songTest = new SongTest();
		p.songs = new Song[]{songTest.tay[0], songTest.billie[2],
				songTest.tay[3], songTest.tay[5], songTest.hilary[7],
				songTest.hilary[3], songTest.billie[3], songTest.billie[1],
				songTest.billie[4], songTest.billie[5], songTest.tay[2],
				songTest.hilary[4], songTest.hilary[1], songTest.tay[1],};
		assertEquals(5, p.countSongsBy("Taylor Swift"));
		assertEquals(0, p.countSongsBy("Taylor Drift"));
		assertEquals(4, p.countSongsBy("Hilary Hahn"));
		assertEquals(0, p.countSongsBy("Gaurav Gupta"));
		assertEquals(0, p.countSongsBy(null));
		assertEquals(5, p.countSongsBy("Taylor Swift")); // Repeat to ensure
															// still same answer

		p.songs = new Song[]{songTest.ed[3], songTest.bp[2], songTest.billie[2],
				songTest.tay[3], songTest.bp[6], songTest.ed[1], songTest.ed[4],
				songTest.hilary[7], songTest.twice[3], songTest.billie[3],
				songTest.twice[1], songTest.billie[4], songTest.bp[3],
				songTest.tay[2], songTest.hilary[4], songTest.ed[4],
				songTest.twice[2], songTest.tay[1], songTest.twice[1],
				songTest.twice[9], songTest.twice[8], songTest.twice[5],
				songTest.ed[9],};
		assertEquals(3, p.countSongsBy("BLACKPINK"));
		assertEquals(7, p.countSongsBy("TWICE"));
		assertEquals(0, p.countSongsBy("twice"));
		
		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "addSong(Song)", marks = 4)
	@Order(2)
	public void testAddSong() {
		SongTest songTest = new SongTest();
		Playlist p = new Playlist();

		p.addSong(null);
		assertEquals(0, p.songs.length);

		// Add 1st song
		Song newSong = songTest.tay[4];
		p.addSong(newSong);
		assertEquals(1, p.songs.length);
		assertEquals("Taylor Swift", p.songs[0].artist.name);
		assertEquals("You Belong with Me", p.songs[0].name);
		assertEquals(211, p.songs[0].length);
		assertEquals(newSong.hashCode(), p.songs[0].hashCode());

		// Add 2nd song
		newSong = songTest.billie[4];
		p.addSong(newSong);
		assertEquals(2, p.songs.length);
		assertEquals("Taylor Swift", p.songs[0].artist.name);
		assertEquals("You Belong with Me", p.songs[0].name);
		assertEquals(211, p.songs[0].length);
		assertEquals("Billie Eilish", p.songs[1].artist.name);
		assertEquals("Lovely", p.songs[1].name);
		assertEquals(200, p.songs[1].length);
		assertEquals(newSong.hashCode(), p.songs[1].hashCode());

		// Add 3rd song
		newSong = songTest.bp[7];
		p.addSong(newSong);
		assertEquals(3, p.songs.length);
		assertEquals("Taylor Swift", p.songs[0].artist.name);
		assertEquals("You Belong with Me", p.songs[0].name);
		assertEquals(211, p.songs[0].length);
		assertEquals("Billie Eilish", p.songs[1].artist.name);
		assertEquals("Lovely", p.songs[1].name);
		assertEquals(200, p.songs[1].length);
		assertEquals("BLACKPINK", p.songs[2].artist.name);
		assertEquals("Whistle", p.songs[2].name);
		assertEquals(213, p.songs[2].length);
		assertEquals(newSong.hashCode(), p.songs[2].hashCode());

		// Add 4th song
		newSong = songTest.tay[12];
		p.addSong(newSong);
		assertEquals(4, p.songs.length);
		assertEquals("Taylor Swift", p.songs[0].artist.name);
		assertEquals("You Belong with Me", p.songs[0].name);
		assertEquals(211, p.songs[0].length);
		assertEquals("Billie Eilish", p.songs[1].artist.name);
		assertEquals("Lovely", p.songs[1].name);
		assertEquals(200, p.songs[1].length);
		assertEquals("BLACKPINK", p.songs[2].artist.name);
		assertEquals("Whistle", p.songs[2].name);
		assertEquals(213, p.songs[2].length);
		assertEquals("Taylor Swift", p.songs[3].artist.name);
		assertEquals("The Archer", p.songs[3].name);
		assertEquals(223, p.songs[3].length);
		assertEquals(newSong.hashCode(), p.songs[3].hashCode());

		// Reset to empty playlist
		songTest = new SongTest();
		p = new Playlist();

		// Add all taylor swift songs and check values
		Song[] taySongs = songTest.tay;
		for (int i = 0; i < taySongs.length; i++) {
			// Add new tay song
			p.addSong(taySongs[i]);

			// Check all old tay songs + new tay song is correct
			for (int j = 0; j <= i; j++) {
				assertEquals(taySongs[i].name, p.songs[i].name);
				assertEquals(taySongs[i].length, p.songs[i].length);
				assertEquals(taySongs[i].artist.name, p.songs[i].artist.name);
			}
		}

		// Add all bp and twice songs
		songTest = new SongTest();
		p = new Playlist();
		for (Song song : songTest.bp)
			p.addSong(song);
		for (Song song : songTest.twice)
			p.addSong(song);
		assertEquals(20, p.songs.length);

		// Add one new song to the end
		newSong = songTest.hilary[4];
		p.addSong(newSong);
		assertEquals("Sibelius: Violin Concerto in D Minor, Op. 47",
				p.songs[20].name);
		assertEquals(325, p.songs[20].length);
		assertEquals("Hilary Hahn", p.songs[20].artist.name);
		
		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "removeAllSongsBy(String)", marks = 8)
	@Order(3)
	public void testRemoveAllSongsBy() {
		SongTest songTest = new SongTest();
		Playlist p = new Playlist();
		p.songs = new Song[]{ // =================
				songTest.tay[0], // =================
				songTest.billie[2], // =================
				songTest.tay[3], // =================
				songTest.tay[5], // =================
				songTest.hilary[7], // =================
				songTest.hilary[3], // =================
				songTest.billie[3], // =================
				songTest.billie[1], // =================
				songTest.billie[4], // =================
		};
		Song[] actualSongs = new Song[]{ // =================
				songTest.tay[0], // =================
				songTest.billie[2], // =================
				songTest.tay[3], // =================
				songTest.tay[5], // =================
				songTest.hilary[7], // =================
				songTest.hilary[3], // =================
				songTest.billie[3], // =================
				songTest.billie[1], // =================
				songTest.billie[4], // =================
		};

		// No songs have "Taylor" as their exact name
		p.removeAllSongsBy("Taylor");
		// All songs in actualSongs and playlist should have the same name
		assertEquals(9, p.songs.length);
		for (int i = 0; i < p.songs.length; i++) {
			assertEquals(actualSongs[i].name, p.songs[i].name);
			assertEquals(actualSongs[i].artist.name, p.songs[i].artist.name);
			assertEquals(actualSongs[i].length, p.songs[i].length);
		}

		// No changes should be made as null artist is passed
		p.removeAllSongsBy(null);
		assertEquals(9, p.songs.length);
		for (int i = 0; i < p.songs.length; i++) {
			assertEquals(actualSongs[i].name, p.songs[i].name);
			assertEquals(actualSongs[i].artist.name, p.songs[i].artist.name);
			assertEquals(actualSongs[i].length, p.songs[i].length);
		}

		// Reset playlist
		p.songs = new Song[]{ // =================
				songTest.tay[0], // =================
				songTest.billie[2], // =================
				songTest.tay[3], // =================
				songTest.tay[5], // =================
				songTest.hilary[7], // =================
				songTest.hilary[3], // =================
				songTest.billie[3], // =================
				songTest.billie[1], // =================
				songTest.billie[4], // =================
		};
		actualSongs = new Song[]{ // =================
				songTest.billie[2], // =================
				songTest.hilary[7], // =================
				songTest.hilary[3], // =================
				songTest.billie[3], // =================
				songTest.billie[1], // =================
				songTest.billie[4], // =================
		};

		p.removeAllSongsBy("Taylor Swift");
		assertEquals(6, p.songs.length);
		for (int i = 0; i < p.songs.length; i++) {
			assertEquals(actualSongs[i].name, p.songs[i].name);
			assertEquals(actualSongs[i].artist.name, p.songs[i].artist.name);
			assertEquals(actualSongs[i].length, p.songs[i].length);
		}

		// Reset playlist
		p.songs = new Song[]{ // =================
				songTest.twice[5], // =================
				songTest.twice[7], // =================
				songTest.bp[4], // =================
				songTest.twice[2], // =================
				songTest.bp[1], // =================
				songTest.bp[7], // =================
				songTest.bp[8], // =================
				songTest.ed[3], // =================
				songTest.twice[3], // =================
				songTest.bp[3], // =================
				songTest.bp[2], // =================
				songTest.bp[5], // =================
				songTest.bp[9], // =================
				songTest.twice[5], // =================
				songTest.bp[3], // =================
		};
		actualSongs = new Song[]{ // =================
				songTest.twice[5], // =================
				songTest.twice[7], // =================
				songTest.twice[2], // =================
				songTest.ed[3], // =================
				songTest.twice[3], // =================
				songTest.twice[5], // =================
		};

		p.removeAllSongsBy("BLACKPINK");
		assertEquals(6, p.songs.length);
		for (int i = 0; i < p.songs.length; i++) {
			assertEquals(actualSongs[i].name, p.songs[i].name);
			assertEquals(actualSongs[i].artist.name, p.songs[i].artist.name);
			assertEquals(actualSongs[i].length, p.songs[i].length);
		}

		// Reset playlist
		p.songs = new Song[]{ // =================
				songTest.twice[5], // =================
				songTest.twice[7], // =================
				songTest.bp[4], // =================
				songTest.twice[2], // =================
				songTest.bp[1], // =================
				songTest.bp[7], // =================
				songTest.bp[8], // =================
				songTest.ed[3], // =================
				songTest.twice[3], // =================
				songTest.bp[3], // =================
				songTest.bp[2], // =================
				songTest.bp[5], // =================
				songTest.bp[9], // =================
				songTest.twice[5], // =================
				songTest.bp[3], // =================
		};
		actualSongs = new Song[]{ // =================
				songTest.bp[4], // =================
				songTest.bp[1], // =================
				songTest.bp[7], // =================
				songTest.bp[8], // =================
				songTest.ed[3], // =================
				songTest.bp[3], // =================
				songTest.bp[2], // =================
				songTest.bp[5], // =================
				songTest.bp[9], // =================
				songTest.bp[3], // =================
		};

		p.removeAllSongsBy("TWICE");
		assertEquals(10, p.songs.length);
		for (int i = 0; i < p.songs.length; i++) {
			assertEquals(actualSongs[i].name, p.songs[i].name);
			assertEquals(actualSongs[i].artist.name, p.songs[i].artist.name);
			assertEquals(actualSongs[i].length, p.songs[i].length);
		}
		
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

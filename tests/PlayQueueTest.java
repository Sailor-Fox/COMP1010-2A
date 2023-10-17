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
import doNotModify.SongLink;
import stage1.Artist;
import stage2.Song;
import stage3.Playlist;
import stage4.PlayQueue;

@TestMethodOrder(CustomTestMethodOrder.class)
public class PlayQueueTest {
	@Test
	@Graded(description = "isEmpty()", marks = 1)
	@Order(1)
	public void testIsEmpty() {
		SongTest songTest = new SongTest();

		// Test 1
		PlayQueue queue = new PlayQueue();
		assertTrue(queue.isEmpty());

		// Test 2
		queue = new PlayQueue();
		SongLink link = new SongLink(songTest.tay[15], null);
		queue.nowPlaying = null;
		queue.firstSong = link;
		queue.lastSong = link;
		// System.out.println(queue);

		assertFalse(queue.isEmpty());

		// Test 3
		queue = new PlayQueue(); // reset the queue
		assertTrue(queue.isEmpty());

		SongLink link5 = new SongLink(songTest.twice[3], null);
		SongLink link4 = new SongLink(songTest.bp[9], link5);
		SongLink link3 = new SongLink(songTest.ed[4], link4);
		SongLink link2 = new SongLink(songTest.twice[4], link3);
		SongLink link1 = new SongLink(songTest.tay[4], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		assertFalse(queue.isEmpty());

		// Test 4
		// Building a PlayQueue of Taylor Swift songs in order of taySongs
		queue = new PlayQueue();
		SongLink first = null;
		SongLink last = null;
		Song[] taySongs = songTest.tay;
		for (int i = taySongs.length - 1; i >= 0; i--) {
			first = new SongLink(taySongs[i], first);
			if (i == taySongs.length - 1)
				last = first;
		}
		queue.firstSong = first;
		queue.lastSong = last;
		queue.nowPlaying = null;
		// System.out.println(queue);

		assertFalse(queue.isEmpty());

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "startQueue()", marks = 2)
	@Order(2)
	public void testStartQueue() {
		SongTest songTest = new SongTest();

		// Test 1
		PlayQueue queue = new PlayQueue();
		queue.startQueue();
		assertEquals(null, queue.nowPlaying);
		assertEquals(null, queue.firstSong);
		assertEquals(null, queue.lastSong);

		// Test 2
		queue = new PlayQueue();
		SongLink link = new SongLink(songTest.twice[7], null);
		queue.nowPlaying = null;
		queue.firstSong = link;
		queue.lastSong = link;
		// System.out.println(queue);

		queue.startQueue();
		assertEquals(link, queue.nowPlaying);
		assertEquals(link, queue.firstSong);
		assertEquals(link, queue.lastSong);

		// Test 3
		queue = new PlayQueue();
		SongLink link5 = new SongLink(songTest.twice[9], null);
		SongLink link4 = new SongLink(songTest.twice[3], link5);
		SongLink link3 = new SongLink(songTest.ed[2], link4);
		SongLink link2 = new SongLink(songTest.twice[1], link3);
		SongLink link1 = new SongLink(songTest.twice[4], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		queue.startQueue();
		assertEquals(link1, queue.nowPlaying);
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);

		queue.startQueue(); // Repeating it shouldn't move to link2
		assertEquals(link1, queue.nowPlaying);
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);

		// Test 4
		// Building a PlayQueue of Taylor Swift songs in order of taySongs
		queue = new PlayQueue();
		SongLink first = null;
		SongLink last = null;
		Song[] taySongs = songTest.tay;
		for (int i = taySongs.length - 1; i >= 0; i--) {
			first = new SongLink(taySongs[i], first);
			if (i == taySongs.length - 1)
				last = first;
		}
		queue.firstSong = first;
		queue.lastSong = last;
		queue.nowPlaying = null;
		// System.out.println(queue);

		queue.startQueue();
		assertEquals(first, queue.nowPlaying);
		assertEquals(first, queue.firstSong);
		assertEquals(last, queue.lastSong);

		queue.startQueue();
		assertEquals(first, queue.nowPlaying);
		assertEquals(first, queue.firstSong);
		assertEquals(last, queue.lastSong);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "addToStart(Song)", marks = 4)
	@Order(3)
	public void testAddToStart() {
		SongTest songTest = new SongTest();

		// Test 1
		PlayQueue queue = new PlayQueue();
		Song song5 = songTest.tay[4];
		queue.addToStart(song5);
		assertEquals(null, queue.nowPlaying);
		assertEquals(song5, queue.firstSong.song);
		assertEquals(null, queue.firstSong.link);
		assertEquals(song5, queue.lastSong.song);

		Song song4 = songTest.tay[9];
		queue.addToStart(song4);
		assertEquals(null, queue.nowPlaying);
		assertEquals(song4, queue.firstSong.song);
		assertEquals(song5, queue.firstSong.link.song);
		assertEquals(null, queue.firstSong.link.link);
		assertEquals(song5, queue.lastSong.song);

		Song song3 = songTest.twice[2];
		queue.addToStart(song3);
		assertEquals(null, queue.nowPlaying);
		assertEquals(song3, queue.firstSong.song);
		assertEquals(song4, queue.firstSong.link.song);
		assertEquals(song5, queue.firstSong.link.link.song);
		assertEquals(null, queue.firstSong.link.link.link);
		assertEquals(song5, queue.lastSong.song);

		Song song2 = songTest.bp[7];
		queue.addToStart(song2);
		assertEquals(null, queue.nowPlaying);
		assertEquals(song2, queue.firstSong.song);
		assertEquals(song3, queue.firstSong.link.song);
		assertEquals(song4, queue.firstSong.link.link.song);
		assertEquals(song5, queue.firstSong.link.link.link.song);
		assertEquals(null, queue.firstSong.link.link.link.link);
		assertEquals(song5, queue.lastSong.song);

		Song song1 = songTest.ed[9];
		queue.addToStart(song1);
		assertEquals(null, queue.nowPlaying);
		assertEquals(song1, queue.firstSong.song);
		assertEquals(song2, queue.firstSong.link.song);
		assertEquals(song3, queue.firstSong.link.link.song);
		assertEquals(song4, queue.firstSong.link.link.link.song);
		assertEquals(song5, queue.firstSong.link.link.link.link.song);
		assertEquals(null, queue.firstSong.link.link.link.link.link);
		assertEquals(song5, queue.lastSong.song);

		// Test 2
		queue = new PlayQueue();
		SongLink link = new SongLink(songTest.twice[7], null);
		queue.nowPlaying = null;
		queue.firstSong = link;
		queue.lastSong = link;
		// System.out.println(queue);

		Song other = songTest.bp[4];
		queue.addToStart(other);
		assertEquals(null, queue.nowPlaying);
		assertEquals(other, queue.firstSong.song);
		assertEquals(link, queue.firstSong.link); // The original link should
													// not be remade
		assertEquals(link.song, queue.lastSong.song);

		// Test 3
		queue = new PlayQueue();
		SongLink link5 = new SongLink(songTest.twice[9], null);
		SongLink link4 = new SongLink(songTest.twice[3], link5);
		SongLink link3 = new SongLink(songTest.ed[2], link4);
		SongLink link2 = new SongLink(songTest.twice[1], link3);
		SongLink link1 = new SongLink(songTest.twice[4], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		Song newFirstSong = songTest.billie[9];
		queue.addToStart(newFirstSong);
		assertEquals(null, queue.nowPlaying);
		assertEquals(newFirstSong, queue.firstSong.song);
		assertEquals(link1, queue.firstSong.link); // The original link should
													// not be remade
		assertEquals(link2, queue.firstSong.link.link); // The original link
														// should not be remade
		assertEquals(link3, queue.firstSong.link.link.link); // The original
																// link should
																// not be remade
		assertEquals(link4, queue.firstSong.link.link.link.link); // The
																	// original
																	// link
																	// should
																	// not be
																	// remade
		assertEquals(link5, queue.firstSong.link.link.link.link.link); // The
																		// original
																		// link
																		// should
																		// not
																		// be
																		// remade
		assertEquals(link5.song, queue.lastSong.song);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "addToEnd(Song)", marks = 4)
	@Order(4)
	public void testAddToEnd() {
		SongTest songTest = new SongTest();

		// Test 1
		PlayQueue queue = new PlayQueue();
		Song song5 = songTest.tay[11];
		queue.addToEnd(song5);
		assertEquals(null, queue.nowPlaying);
		assertEquals(song5, queue.firstSong.song);
		assertEquals(null, queue.firstSong.link);
		assertEquals(song5, queue.lastSong.song);

		Song song4 = songTest.twice[3];
		queue.addToEnd(song4);
		assertEquals(null, queue.nowPlaying);
		assertEquals(song5, queue.firstSong.song);
		assertEquals(song4, queue.firstSong.link.song);
		assertEquals(null, queue.firstSong.link.link);
		assertEquals(song4, queue.lastSong.song);

		Song song3 = songTest.bp[9];
		queue.addToEnd(song3);
		assertEquals(null, queue.nowPlaying);
		assertEquals(song5, queue.firstSong.song);
		assertEquals(song4, queue.firstSong.link.song);
		assertEquals(song3, queue.firstSong.link.link.song);
		assertEquals(null, queue.firstSong.link.link.link);
		assertEquals(song3, queue.lastSong.song);

		Song song2 = songTest.bp[4];
		queue.addToEnd(song2);
		assertEquals(null, queue.nowPlaying);
		assertEquals(song5, queue.firstSong.song);
		assertEquals(song4, queue.firstSong.link.song);
		assertEquals(song3, queue.firstSong.link.link.song);
		assertEquals(song2, queue.firstSong.link.link.link.song);
		assertEquals(null, queue.firstSong.link.link.link.link);
		assertEquals(song2, queue.lastSong.song);

		Song song1 = songTest.ed[3];
		queue.addToEnd(song1);
		assertEquals(null, queue.nowPlaying);
		assertEquals(song5, queue.firstSong.song);
		assertEquals(song4, queue.firstSong.link.song);
		assertEquals(song3, queue.firstSong.link.link.song);
		assertEquals(song2, queue.firstSong.link.link.link.song);
		assertEquals(song1, queue.firstSong.link.link.link.link.song);
		assertEquals(null, queue.firstSong.link.link.link.link.link);
		assertEquals(song1, queue.lastSong.song);

		// Test 2
		queue = new PlayQueue();
		SongLink link = new SongLink(songTest.hilary[4], null);
		queue.nowPlaying = null;
		queue.firstSong = link;
		queue.lastSong = link;
		// System.out.println(queue);

		Song other = songTest.bp[4];
		queue.addToEnd(other);
		assertEquals(null, queue.nowPlaying);
		assertEquals(link, queue.firstSong);
		assertEquals(other, queue.firstSong.link.song);
		assertEquals(other, queue.lastSong.song);

		// Test 3
		queue = new PlayQueue();
		SongLink link5 = new SongLink(songTest.twice[9], null);
		SongLink link4 = new SongLink(songTest.twice[3], link5);
		SongLink link3 = new SongLink(songTest.ed[2], link4);
		SongLink link2 = new SongLink(songTest.twice[1], link3);
		SongLink link1 = new SongLink(songTest.twice[4], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		Song newLastSong = songTest.billie[4];
		queue.addToEnd(newLastSong);
		assertEquals(null, queue.nowPlaying);
		assertEquals(link1, queue.firstSong);
		assertEquals(link2, queue.firstSong.link); // The original link should
													// not be remade
		assertEquals(link3, queue.firstSong.link.link); // The original link
														// should not be remade
		assertEquals(link4, queue.firstSong.link.link.link); // The original
																// link should
																// not be remade
		assertEquals(link5, queue.firstSong.link.link.link.link); // The
																	// original
																	// link
																	// should
																	// not be
																	// remade
		assertEquals(newLastSong,
				queue.firstSong.link.link.link.link.link.song); // The original
																// link should
																// not be remade
		assertEquals(newLastSong, queue.lastSong.song);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "nextSong()", marks = 4)
	@Order(5)
	public void testNextSong() {
		SongTest songTest = new SongTest();
		Song result;

		// Test 1
		PlayQueue queue = new PlayQueue();
		result = queue.nextSong();
		assertEquals(null, result);
		assertEquals(null, queue.nowPlaying);
		assertEquals(null, queue.firstSong);
		assertEquals(null, queue.lastSong);
 
		queue.nextSong(); // Repeating should not change anything
		assertEquals(null, result);
		assertEquals(null, queue.nowPlaying);
		assertEquals(null, queue.firstSong);
		assertEquals(null, queue.lastSong);

		queue.nextSong(); // Repeating should not change anything
		assertEquals(null, result);
		assertEquals(null, queue.nowPlaying);
		assertEquals(null, queue.firstSong);
		assertEquals(null, queue.lastSong);

		// Test 2
		queue = new PlayQueue();
		SongLink link = new SongLink(songTest.twice[7], null);
		queue.nowPlaying = null;
		queue.firstSong = link;
		queue.lastSong = link;
		// System.out.println(queue);

		result = queue.nextSong(); // Was null, so should now become the first
									// song
		assertEquals(link.song, result);
		assertEquals(link, queue.nowPlaying);
		assertEquals(link, queue.firstSong); // First and last should not be
												// modified
		assertEquals(link, queue.lastSong);

		result = queue.nextSong(); // No song left in queue, should become null
		assertEquals(null, result);
		assertEquals(null, queue.nowPlaying);
		assertEquals(link, queue.firstSong);
		assertEquals(link, queue.lastSong);

		result = queue.nextSong(); // nowPlaying is null, so should become first
									// song in list again
		assertEquals(link.song, result);
		assertEquals(link, queue.nowPlaying);
		assertEquals(link, queue.firstSong);
		assertEquals(link, queue.lastSong);

		result = queue.nextSong(); // no more songs left after current song, so
									// should be null again
		assertEquals(null, result);
		assertEquals(null, queue.nowPlaying);
		assertEquals(link, queue.firstSong);
		assertEquals(link, queue.lastSong);

		// Test 3
		queue = new PlayQueue();
		SongLink link5 = new SongLink(songTest.twice[9], null);
		SongLink link4 = new SongLink(songTest.twice[3], link5);
		SongLink link3 = new SongLink(songTest.ed[2], link4);
		SongLink link2 = new SongLink(songTest.twice[1], link3);
		SongLink link1 = new SongLink(songTest.twice[4], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		result = queue.nextSong(); // Was null, so should now become the first
									// song
		assertEquals(link1.song, result);
		assertEquals(link1, queue.nowPlaying);
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);

		result = queue.nextSong();
		assertEquals(link2.song, result);
		assertEquals(link2, queue.nowPlaying);
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);

		result = queue.nextSong();
		assertEquals(link3.song, result);
		assertEquals(link3, queue.nowPlaying);
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);

		result = queue.nextSong();
		assertEquals(link4.song, result);
		assertEquals(link4, queue.nowPlaying);
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);

		result = queue.nextSong();
		assertEquals(link5.song, result);
		assertEquals(link5, queue.nowPlaying);
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);

		result = queue.nextSong();
		assertEquals(null, result);
		assertEquals(null, queue.nowPlaying);
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);

		result = queue.nextSong();
		assertEquals(link1.song, result);
		assertEquals(link1, queue.nowPlaying);
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);

		result = queue.nextSong();
		assertEquals(link2.song, result);
		assertEquals(link2, queue.nowPlaying);
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);

		// Test 4
		// Building a PlayQueue of Taylor Swift songs in order of taySongs
		queue = new PlayQueue();
		SongLink first = null;
		SongLink last = null;
		Song[] taySongs = songTest.tay;
		for (int i = taySongs.length - 1; i >= 0; i--) {
			first = new SongLink(taySongs[i], first);
			if (i == taySongs.length - 1)
				last = first;
		}
		queue.firstSong = first;
		queue.lastSong = last;
		queue.nowPlaying = null;
		// System.out.println(queue);

		result = queue.nextSong(); // Was null, so should now become the first
									// song
		assertEquals(first.song, result);
		assertEquals(first, queue.nowPlaying);
		assertEquals(first, queue.firstSong);
		assertEquals(last, queue.lastSong);

		result = queue.nextSong();
		assertEquals(first.link.song, result);
		assertEquals(first.link, queue.nowPlaying);
		assertEquals(first, queue.firstSong);
		assertEquals(last, queue.lastSong);

		result = queue.nextSong();
		assertEquals(first.link.link.song, result);
		assertEquals(first.link.link, queue.nowPlaying);
		assertEquals(first, queue.firstSong);
		assertEquals(last, queue.lastSong);

		result = queue.nextSong();
		assertEquals(first.link.link.link.song, result);
		assertEquals(first.link.link.link, queue.nowPlaying);
		assertEquals(first, queue.firstSong);
		assertEquals(last, queue.lastSong);

		result = queue.nextSong();
		assertEquals(first.link.link.link.link.song, result);
		assertEquals(first.link.link.link.link, queue.nowPlaying);
		assertEquals(first, queue.firstSong);
		assertEquals(last, queue.lastSong);

		result = queue.nextSong();
		assertEquals(first.link.link.link.link.link.song, result);
		assertEquals(first.link.link.link.link.link, queue.nowPlaying);
		assertEquals(first, queue.firstSong);
		assertEquals(last, queue.lastSong);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "getSong(int)", marks = 8)
	@Order(6)
	public void testGetSong() {
		SongTest songTest = new SongTest();

		// Test 1
		PlayQueue queue = new PlayQueue();
		assertEquals(null, queue.getSong(-100));
		assertEquals(null, queue.getSong(0));
		assertEquals(null, queue.getSong(1));
		assertEquals(null, queue.getSong(2));
		assertEquals(null, queue.getSong(3));

		// Test 2
		queue = new PlayQueue();
		Song song1 = songTest.bp[2];
		SongLink link = new SongLink(song1, null);
		queue.nowPlaying = null;
		queue.firstSong = link;
		queue.lastSong = link;
		// System.out.println(queue);

		assertEquals(null, queue.getSong(-1));
		assertEquals(null, queue.getSong(0));
		assertEquals(song1, queue.getSong(1));
		assertEquals(null, queue.getSong(2));
		assertEquals(link, queue.firstSong);
		assertEquals(link, queue.lastSong);
		assertEquals(null, queue.nowPlaying);

		// Change now playing
		queue.nowPlaying = link;
		assertEquals(null, queue.getSong(-1));
		assertEquals(null, queue.getSong(0));
		assertEquals(song1, queue.getSong(1));
		assertEquals(null, queue.getSong(2));
		assertEquals(link, queue.firstSong);
		assertEquals(link, queue.lastSong);
		assertEquals(link, queue.nowPlaying);

		// Test 3
		queue = new PlayQueue();
		SongLink link5 = new SongLink(songTest.twice[9], null);
		SongLink link4 = new SongLink(songTest.twice[3], link5);
		SongLink link3 = new SongLink(songTest.ed[2], link4);
		SongLink link2 = new SongLink(songTest.twice[1], link3);
		SongLink link1 = new SongLink(songTest.twice[4], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		assertEquals(null, queue.getSong(-500));
		assertEquals(null, queue.getSong(-1));
		assertEquals(null, queue.getSong(0));
		assertEquals(link1.song, queue.getSong(1));
		assertEquals(link2.song, queue.getSong(2));
		assertEquals(link3.song, queue.getSong(3));
		assertEquals(link4.song, queue.getSong(4));
		assertEquals(link5.song, queue.getSong(5));
		assertEquals(null, queue.getSong(6));
		assertEquals(null, queue.getSong(7));
		assertEquals(null, queue.getSong(8));

		// Check references at the same

		// first time help only!
		// ensure link1.song reference and queue.getSong(1) reference
		// both refer to the same instance
		assertTrue(link1.song == queue.getSong(1));

		assertTrue(link2.song == queue.getSong(2));
		assertTrue(link3.song == queue.getSong(3));
		assertTrue(link4.song == queue.getSong(4));
		assertTrue(link5.song == queue.getSong(5));

		// Test 4
		// Building a PlayQueue of Taylor Swift songs
		queue = new PlayQueue();
		SongLink first = null;
		SongLink last = null;
		Song[] taySongs = songTest.tay;
		for (int i = taySongs.length - 1; i >= 0; i--) {
			first = new SongLink(taySongs[i], first);
			if (i == taySongs.length - 1)
				last = first;
		}
		queue.firstSong = first;
		queue.lastSong = last;
		queue.nowPlaying = null;
		// System.out.println(queue);

		assertEquals(null, queue.getSong(-500));
		assertEquals(null, queue.getSong(-1));
		assertEquals(null, queue.getSong(0));
		for (int i = 0; i < taySongs.length; i++) {
			assertEquals(taySongs[i], queue.getSong(i + 1));
		}
		for (int i = 0; i < 50; i++) {
			assertEquals(null, queue.getSong(taySongs.length + 1 + i));
		}

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "totalPlaytime()", marks = 5)
	@Order(7)
	public void testTotalPlaytime() {
		SongTest songTest = new SongTest();

		// Test 1
		PlayQueue queue = new PlayQueue();
		assertEquals(0, queue.totalPlaytime());

		// Test 2
		queue = new PlayQueue();
		Song song1 = songTest.bp[2];
		SongLink link = new SongLink(song1, null);
		queue.nowPlaying = null;
		queue.firstSong = link;
		queue.lastSong = link;
		// System.out.println(queue);

		assertEquals(215, queue.totalPlaytime());
		assertEquals(link, queue.firstSong);
		assertEquals(link, queue.lastSong);
		assertEquals(null, queue.nowPlaying);

		queue.nowPlaying = link;
		assertEquals(215, queue.totalPlaytime());
		assertEquals(link, queue.firstSong);
		assertEquals(link, queue.lastSong);
		assertEquals(link, queue.nowPlaying);

		// Test 3
		queue = new PlayQueue();
		SongLink link5 = new SongLink(songTest.twice[4], null);
		SongLink link4 = new SongLink(songTest.twice[6], link5);
		SongLink link3 = new SongLink(songTest.ed[8], link4);
		SongLink link2 = new SongLink(songTest.twice[2], link3);
		SongLink link1 = new SongLink(songTest.twice[9], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		assertEquals(1026, queue.totalPlaytime());
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);
		assertEquals(null, queue.nowPlaying);

		queue.nowPlaying = link3;
		assertEquals(1026, queue.totalPlaytime());
		assertEquals(link1, queue.firstSong);
		assertEquals(link5, queue.lastSong);
		assertEquals(link3, queue.nowPlaying);

		// Test 4
		// Building a PlayQueue of BLACKPINK songs
		queue = new PlayQueue();
		SongLink first = null;
		SongLink last = null;
		Song[] bpSongs = songTest.bp;
		for (int i = bpSongs.length - 1; i >= 0; i--) {
			first = new SongLink(bpSongs[i], first);
			if (i == bpSongs.length - 1)
				last = first;
		}
		queue.firstSong = first;
		queue.lastSong = last;
		queue.nowPlaying = null;
		// System.out.println(queue);

		assertEquals(1981, queue.totalPlaytime());

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "remainingPlaytime()", marks = 5)
	@Order(8)
	public void testRemainingPlaytime() {
		SongTest songTest = new SongTest();

		// Test 1
		PlayQueue queue = new PlayQueue();
		assertEquals(0, queue.remainingPlaytime());

		// Test 2
		queue = new PlayQueue();
		Song song1 = songTest.bp[4];
		SongLink link = new SongLink(song1, null);
		queue.nowPlaying = null;
		queue.firstSong = link;
		queue.lastSong = link;
		// System.out.println(queue);

		assertEquals(0, queue.remainingPlaytime());
		assertEquals(link, queue.firstSong);
		assertEquals(link, queue.lastSong);
		assertEquals(null, queue.nowPlaying);

		queue.nowPlaying = link;
		assertEquals(240, queue.remainingPlaytime());
		assertEquals(link, queue.firstSong);
		assertEquals(link, queue.lastSong);
		assertEquals(link, queue.nowPlaying);

		// Test 3
		queue = new PlayQueue();
		SongLink link5 = new SongLink(songTest.twice[4], null);
		SongLink link4 = new SongLink(songTest.twice[6], link5);
		SongLink link3 = new SongLink(songTest.ed[8], link4);
		SongLink link2 = new SongLink(songTest.twice[2], link3);
		SongLink link1 = new SongLink(songTest.twice[9], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		assertEquals(0, queue.remainingPlaytime());
		queue.nowPlaying = link1;
		assertEquals(1026, queue.remainingPlaytime());
		queue.nowPlaying = link2;
		assertEquals(849, queue.remainingPlaytime());
		queue.nowPlaying = link3;
		assertEquals(646, queue.remainingPlaytime());
		queue.nowPlaying = link4;
		assertEquals(409, queue.remainingPlaytime());
		queue.nowPlaying = link5;
		assertEquals(211, queue.remainingPlaytime());

		// Test 4
		// Building a PlayQueue of TWICE songs
		queue = new PlayQueue();
		SongLink first = null;
		SongLink last = null;
		Song[] songs = songTest.twice;
		for (int i = songs.length - 1; i >= 0; i--) {
			first = new SongLink(songs[i], first);
			if (i == songs.length - 1)
				last = first;
		}
		queue.firstSong = first;
		queue.lastSong = last;
		queue.nowPlaying = null;
		// System.out.println(queue);

		assertEquals(0, queue.remainingPlaytime());

		queue.nowPlaying = queue.firstSong;
		assertEquals(2070, queue.remainingPlaytime());

		queue.nowPlaying = queue.nowPlaying.link.link.link.link.link.link;
		assertEquals(789, queue.remainingPlaytime());

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "addToQueue(Playlist)", marks = 5)
	@Order(9)
	public void testAddToQueue() {
		SongTest songTest = new SongTest();

		// Test 1
		PlayQueue queue = new PlayQueue();
		Playlist p = new Playlist();
		p.songs = new Song[]{songTest.tay[0], songTest.billie[2],
				songTest.tay[3], songTest.tay[5], songTest.hilary[7],
				songTest.hilary[3], songTest.billie[3],};

		queue.addToQueue(p);
		assertEquals(p.songs[0], queue.firstSong.song);
		assertEquals(p.songs[1], queue.firstSong.link.song);
		assertEquals(p.songs[2], queue.firstSong.link.link.song);
		assertEquals(p.songs[3], queue.firstSong.link.link.link.song);
		assertEquals(p.songs[4], queue.firstSong.link.link.link.link.song);
		assertEquals(p.songs[5], queue.firstSong.link.link.link.link.link.song);
		assertEquals(p.songs[6],
				queue.firstSong.link.link.link.link.link.link.song);
		assertEquals(null, queue.firstSong.link.link.link.link.link.link.link);
		assertEquals(null, queue.nowPlaying);
		assertTrue(p.songs[6] == queue.lastSong.song);

		// Test 2
		queue = new PlayQueue();
		Song song1 = songTest.bp[4];
		SongLink link = new SongLink(song1, null);
		queue.nowPlaying = null;
		queue.firstSong = link;
		queue.lastSong = link;
		// System.out.println(queue);

		p = new Playlist();
		p.songs = new Song[]{songTest.bp[4], songTest.twice[9],};

		queue.addToQueue(p);
		assertTrue(song1 == queue.firstSong.song);
		assertTrue(p.songs[0] == queue.firstSong.link.song);
		assertTrue(p.songs[1] == queue.firstSong.link.link.song);
		assertEquals(null, queue.firstSong.link.link.link);
		assertEquals(null, queue.nowPlaying);
		assertTrue(p.songs[1] == queue.lastSong.song);

		// Test 3
		queue = new PlayQueue();
		Song[] songsInList = {songTest.twice[2], songTest.bp[4], songTest.ed[9],
				songTest.twice[9], songTest.twice[1],};
		SongLink link5 = new SongLink(songsInList[4], null);
		SongLink link4 = new SongLink(songsInList[3], link5);
		SongLink link3 = new SongLink(songsInList[2], link4);
		SongLink link2 = new SongLink(songsInList[1], link3);
		SongLink link1 = new SongLink(songsInList[0], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		p = new Playlist();
		p.songs = new Song[]{songTest.bp[4], songTest.hilary[7],
				songTest.ed[2]};

		// System.out.println(queue);
		queue.addToQueue(p);
		assertEquals(songsInList[0], queue.firstSong.song);
		assertEquals(songsInList[1], queue.firstSong.link.song);
		assertEquals(songsInList[2], queue.firstSong.link.link.song);
		assertEquals(songsInList[3], queue.firstSong.link.link.link.song);
		assertEquals(songsInList[4], queue.firstSong.link.link.link.link.song);
		assertEquals(p.songs[0], queue.firstSong.link.link.link.link.link.song);
		assertEquals(p.songs[1],
				queue.firstSong.link.link.link.link.link.link.song);
		assertEquals(p.songs[2],
				queue.firstSong.link.link.link.link.link.link.link.song);
		assertEquals(null,
				queue.firstSong.link.link.link.link.link.link.link.link);
		assertEquals(null, queue.nowPlaying);
		assertTrue(p.songs[2] == queue.lastSong.song);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "addToQueueFiltered(Playlist)", marks = 8)
	@Order(10)
	public void testAddToQueueFiltered() {
		SongTest songTest = new SongTest();

		// Test 1
		PlayQueue queue = new PlayQueue();
		Playlist p = new Playlist();
		p.songs = new Song[]{songTest.tay[4], songTest.bp[4],
				songTest.hilary[5], songTest.billie[7], songTest.tay[3],
				songTest.ed[3],};
		
		songTest.hilary[5].length = 100;
		songTest.billie[7].length = 100;
		songTest.ed[3].length = 100;

		queue.addToQueueFiltered(p);
		assertEquals(p.songs[0], queue.firstSong.song);
		assertEquals(p.songs[1], queue.firstSong.link.song);
		assertEquals(p.songs[2], queue.firstSong.link.link.song);
		assertEquals(p.songs[3], queue.firstSong.link.link.link.song);
		assertEquals(p.songs[4], queue.firstSong.link.link.link.link.song);
		assertEquals(p.songs[5], queue.firstSong.link.link.link.link.link.song);
		assertEquals(null, queue.firstSong.link.link.link.link.link.link);
		assertEquals(null, queue.nowPlaying);
		assertTrue(p.songs[5] == queue.lastSong.song);

		// Test 2 - blackpink song idx 2 should not be added
		queue = new PlayQueue();
		Song song1 = songTest.bp[4];
		SongLink link = new SongLink(song1, null);
		queue.nowPlaying = null;
		queue.firstSong = link;
		queue.lastSong = link;
		// System.out.println(queue);

		SongTest songTest2 = new SongTest();
		p = new Playlist();
		p.songs = new Song[]{songTest2.tay[4], songTest2.bp[2], songTest2.bp[4],
				songTest2.tay[3]};

		queue.addToQueueFiltered(p);
		assertTrue(song1 == queue.firstSong.song);
		assertTrue(p.songs[0] == queue.firstSong.link.song);
		assertTrue(p.songs[1] == queue.firstSong.link.link.song);
		assertTrue(p.songs[3] == queue.firstSong.link.link.link.song);
		assertEquals(null, queue.firstSong.link.link.link.link);
		assertEquals(null, queue.nowPlaying);
		assertTrue(p.songs[3] == queue.lastSong.song);

		// Test 3 - all the same
		queue = new PlayQueue();
		Song[] songsInList = {songTest.twice[4], songTest.bp[7],
				songTest.twice[3], songTest.twice[4], songTest.twice[9],};
		SongLink link5 = new SongLink(songsInList[4], null);
		SongLink link4 = new SongLink(songsInList[3], link5);
		SongLink link3 = new SongLink(songsInList[2], link4);
		SongLink link2 = new SongLink(songsInList[1], link3);
		SongLink link1 = new SongLink(songsInList[0], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		songTest2 = new SongTest();
		p = new Playlist();
		p.songs = new Song[]{songTest2.twice[4], songTest2.bp[7],
				songTest2.twice[3], songTest2.twice[4], songTest2.twice[9]};

		// System.out.println(queue);
		queue.addToQueueFiltered(p);
		assertTrue(songsInList[0] == queue.firstSong.song);
		assertTrue(songsInList[1] == queue.firstSong.link.song);
		assertTrue(songsInList[2] == queue.firstSong.link.link.song);
		assertTrue(songsInList[3] == queue.firstSong.link.link.link.song);
		assertTrue(songsInList[4] == queue.firstSong.link.link.link.link.song);
		assertEquals(null, queue.firstSong.link.link.link.link.link);
		assertEquals(null, queue.nowPlaying);
		assertTrue(songsInList[4] == queue.lastSong.song);

		// Test 4 - some more different
		queue = new PlayQueue();
		songsInList = new Song[]{songTest.twice[4], songTest.bp[7],
				songTest.twice[3], songTest.twice[4], songTest.twice[9],};
		link5 = new SongLink(songsInList[4], null);
		link4 = new SongLink(songsInList[3], link5);
		link3 = new SongLink(songsInList[2], link4);
		link2 = new SongLink(songsInList[1], link3);
		link1 = new SongLink(songsInList[0], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		songTest2 = new SongTest();
		SongTest songTest3 = new SongTest();
		p = new Playlist(); // Two twice 5 in here!
		p.songs = new Song[]{ // =========================
				songTest2.twice[5], // Keep =========================
				songTest2.bp[4], // Keep =========================
				songTest2.twice[3], // NO=========================
				songTest2.twice[5], // NO =========================
				songTest2.twice[9], // NO=========================
				songTest3.twice[9], // NO=========================
				songTest3.twice[1], // Keep =========================
		};

		// System.out.println(queue);
		queue.addToQueueFiltered(p);
		assertTrue(songsInList[0] == queue.firstSong.song);
		assertTrue(songsInList[1] == queue.firstSong.link.song);
		assertTrue(songsInList[2] == queue.firstSong.link.link.song);
		assertTrue(songsInList[3] == queue.firstSong.link.link.link.song);
		assertTrue(songsInList[4] == queue.firstSong.link.link.link.link.song);
		assertTrue(p.songs[0] == queue.firstSong.link.link.link.link.link.song);
		assertTrue(
				p.songs[1] == queue.firstSong.link.link.link.link.link.link.song);
		assertTrue(
				p.songs[6] == queue.firstSong.link.link.link.link.link.link.link.song);
		assertEquals(null,
				queue.firstSong.link.link.link.link.link.link.link.link);
		assertEquals(null, queue.nowPlaying);
		assertTrue(p.songs[6] == queue.lastSong.song);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "reversePlayQueue()", marks = 8)
	@Order(11)
	public void testReversePlayQueue() {
		SongTest songTest = new SongTest();

		// Test 1
		PlayQueue queue = new PlayQueue();
		queue.reversePlayQueue();
		assertEquals(null, queue.nowPlaying);
		assertEquals(null, queue.firstSong);
		assertEquals(null, queue.lastSong);

		// Test 2
		queue = new PlayQueue();
		Song song1 = songTest.bp[4];
		SongLink link = new SongLink(song1, null);
		queue.nowPlaying = null;
		queue.firstSong = link;
		queue.lastSong = link;
		// System.out.println(queue);

		queue.reversePlayQueue();
		// System.out.println(queue);
		assertEquals(null, queue.nowPlaying);
		assertTrue(song1 == queue.firstSong.song);
		assertTrue(song1 == queue.lastSong.song);
		assertTrue(link == queue.firstSong);
		assertTrue(link == queue.lastSong);
		assertEquals(null, queue.firstSong.link);

		// Test 3
		queue = new PlayQueue();
		SongLink link5 = new SongLink(songTest.twice[4], null);
		SongLink link4 = new SongLink(songTest.bp[7], link5);
		SongLink link3 = new SongLink(songTest.twice[3], link4);
		SongLink link2 = new SongLink(songTest.twice[4], link3);
		SongLink link1 = new SongLink(songTest.twice[9], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;
		// System.out.println(queue);

		queue.reversePlayQueue();

		assertEquals(null, queue.nowPlaying);
		assertTrue(link5 == queue.firstSong);
		assertTrue(link4 == queue.firstSong.link);
		assertTrue(link3 == queue.firstSong.link.link);
		assertTrue(link2 == queue.firstSong.link.link.link);
		assertTrue(link1 == queue.firstSong.link.link.link.link);
		assertTrue(link5.song == queue.firstSong.song);
		assertTrue(link4.song == queue.firstSong.link.song);
		assertTrue(link3.song == queue.firstSong.link.link.song);
		assertTrue(link2.song == queue.firstSong.link.link.link.song);
		assertTrue(link1.song == queue.firstSong.link.link.link.link.song);
		assertTrue(link1 == queue.lastSong);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "reversed()", marks = 8)
	@Order(12)
	public void testReversed() {
		SongTest songTest = new SongTest();
		PlayQueue queue;

		// Test 1
		queue = new PlayQueue();
		SongLink link5 = new SongLink(songTest.bp[4], null);
		SongLink link4 = new SongLink(songTest.bp[7], link5);
		SongLink link3 = new SongLink(songTest.tay[3], link4);
		SongLink link2 = new SongLink(songTest.billie[4], link3);
		SongLink link1 = new SongLink(songTest.ed[9], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link5;

		Playlist result = queue.reversed();
		assertEquals(5, result.songs.length);
		assertTrue(link5.song == result.songs[0]);
		assertTrue(link4.song == result.songs[1]);
		assertTrue(link3.song == result.songs[2]);
		assertTrue(link2.song == result.songs[3]);
		assertTrue(link1.song == result.songs[4]);

		assertTrue(link1 == queue.firstSong);
		assertTrue(link2 == queue.firstSong.link);
		assertTrue(link3 == queue.firstSong.link.link);
		assertTrue(link4 == queue.firstSong.link.link.link);
		assertTrue(link5 == queue.firstSong.link.link.link.link);
		assertTrue(link5 == queue.lastSong);
		assertTrue(null == queue.nowPlaying);

		// Test 2
		queue = new PlayQueue();
		SongLink link8 = new SongLink(songTest.tay[5], null);
		SongLink link7 = new SongLink(songTest.bp[3], link8);
		SongLink link6 = new SongLink(songTest.bp[2], link7);
		link5 = new SongLink(songTest.bp[7], link6);
		link4 = new SongLink(songTest.bp[4], link5);
		link3 = new SongLink(songTest.tay[5], link4);
		link2 = new SongLink(songTest.billie[9], link3);
		link1 = new SongLink(songTest.ed[3], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		result = queue.reversed();
		assertEquals(8, result.songs.length);
		assertTrue(link8.song == result.songs[0]);
		assertTrue(link7.song == result.songs[1]);
		assertTrue(link6.song == result.songs[2]);
		assertTrue(link5.song == result.songs[3]);
		assertTrue(link4.song == result.songs[4]);
		assertTrue(link3.song == result.songs[5]);
		assertTrue(link2.song == result.songs[6]);
		assertTrue(link1.song == result.songs[7]);

		assertTrue(link1 == queue.firstSong);
		assertTrue(link2 == queue.firstSong.link);
		assertTrue(link3 == queue.firstSong.link.link);
		assertTrue(link4 == queue.firstSong.link.link.link);
		assertTrue(link5 == queue.firstSong.link.link.link.link);
		assertTrue(link6 == queue.firstSong.link.link.link.link.link);
		assertTrue(link7 == queue.firstSong.link.link.link.link.link.link);
		assertTrue(link8 == queue.firstSong.link.link.link.link.link.link.link);
		assertTrue(
				null == queue.firstSong.link.link.link.link.link.link.link.link);
		assertTrue(link8 == queue.lastSong);
		assertTrue(null == queue.nowPlaying);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Graded(description = "bestOf()", marks = 8)
	@Order(13)
	public void testBestOf() {

		Song[] tay = new Song[]{ // -----------
				new Song("Anti-Hero", 200, new Artist("Taylor Swift"),
						// 0. averageRating = 7.714285714285714
						new Review[]{new Review(10), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(10)}), // ------------------------------------
				new Song("Love Story", 235, new Artist("Taylor Swift"),
						new Review[]{ // 1. averageRating = 7.714285714285714
								new Review(4), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(10)}), // ------------------------------------
				new Song("Blank Space", 231, new Artist("Taylor Swift"),
						new Review[]{ // 2. averageRating = 5.428571428571429
								new Review(5), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(3)}), // ------------------------------------
				new Song("Shake It Off", 199, new Artist("Taylor Swift"),
						new Review[]{ // 3. averageRating = 6.142857142857143
								new Review(10), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(1)}), // ------------------------------------
				new Song("Delicate", 190, new Artist("Taylor Swift"),
						new Review[]{ // 4. averageRating = 2.4285714285714284
								new Review(3), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(3)}), // ------------------------------------
		};

		Song[] twice = new Song[]{ // -----------
				new Song("TT", 232, new Artist("TWICE"),
						// 0. averageRating = 8.142857142857142
						new Review[]{ // ---------------------------------------------
								new Review(10), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(8)}), // ------------------------------------
				new Song("Likey", 210, new Artist("TWICE"),
						// 1. averageRating = 5.428571428571429
						new Review[]{ // ---------------------------------------------
								new Review(4), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(3)}), // ------------------------------------
				new Song("Fancy", 203, new Artist("TWICE"),
						// 2. averageRating = 5.0
						new Review[]{ // ---------------------------------------------
								new Review(7), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(3)}), // ------------------------------------
				new Song("Cheer Up", 216, new Artist("TWICE"),
						// 3. averageRating = 1.5714285714285714
						new Review[]{ // ---------------------------------------------
								new Review(1), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(1)}), // ------------------------------------
		};

		Song[] bp = new Song[]{ // -----------
				new Song("DDU-DU DDU-DU", 209, new Artist("BLACKPINK"),
						// 0. averageRating = 3.7142857142857144
						new Review[]{ // ---------------------------------------------
								new Review(4), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(5)}), // ------------------------------------
				new Song("Kill This Love", 190, new Artist("BLACKPINK"),
						// 1. averageRating = 6.571428571428571
						new Review[]{ // ---------------------------------------------
								new Review(6), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(5)}), // ------------------------------------
				new Song("Lovesick Girls", 215, new Artist("BLACKPINK"),
						// 2. averageRating = 1.7142857142857142
						new Review[]{ // ---------------------------------------------
								new Review(2), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(1)}), // ------------------------------------
				new Song("How You Like That", 181, new Artist("BLACKPINK"),
						// 3. averageRating = 7.142857142857143
						new Review[]{ // ---------------------------------------------
								new Review(9), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(10)}), // ------------------------------------
				new Song("BOOMBAYAH", 240, new Artist("BLACKPINK"),
						// 4. averageRating = 7.142857142857143
						new Review[]{ // ---------------------------------------------
								new Review(9), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(4)}), // ------------------------------------
		};

		SongTest songTest = new SongTest();
		PlayQueue queue;

		// Test 1
		// 1 distinct artist, highest is in the middle somewhere, unique highest
		queue = new PlayQueue();
		SongLink link8 = new SongLink(tay[4], null);
		SongLink link7 = new SongLink(tay[3], link8);
		SongLink link6 = new SongLink(tay[4], link7);
		SongLink link5 = new SongLink(tay[3], link6);
		SongLink link4 = new SongLink(tay[0], link5);
		SongLink link3 = new SongLink(tay[2], link4);
		SongLink link2 = new SongLink(tay[4], link3);
		SongLink link1 = new SongLink(tay[2], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(tay[0] == queue.firstSong.song);
		assertTrue(null == queue.firstSong.link);
		assertTrue(tay[0] == queue.lastSong.song);

		// Test 2
		// 1 distinct artist, highest is at start somewhere, unique highest
		queue = new PlayQueue();
		link8 = new SongLink(tay[2], null);
		link7 = new SongLink(tay[3], link8);
		link6 = new SongLink(tay[4], link7);
		link5 = new SongLink(tay[3], link6);
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(tay[2], link4);
		link2 = new SongLink(tay[4], link3);
		link1 = new SongLink(tay[0], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(tay[0] == queue.firstSong.song);
		assertTrue(null == queue.firstSong.link);
		assertTrue(tay[0] == queue.lastSong.song);

		// Test 3
		// 1 distinct artist, highest is in middle somewhere, non unique highest
		queue = new PlayQueue();
		link8 = new SongLink(tay[2], null);
		link7 = new SongLink(tay[1], link8);
		link6 = new SongLink(tay[4], link7);
		link5 = new SongLink(tay[0], link6);
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(tay[1], link4);
		link2 = new SongLink(tay[4], link3);
		link1 = new SongLink(tay[4], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(tay[1] == queue.firstSong.song);
		assertTrue(null == queue.firstSong.link);
		assertTrue(tay[1] == queue.lastSong.song);

		// Test 3
		// 1 distinct artist, highest is in middle somewhere, non unique highest
		queue = new PlayQueue();
		link8 = new SongLink(tay[2], null);
		link7 = new SongLink(tay[1], link8);
		link6 = new SongLink(tay[4], link7);
		link5 = new SongLink(tay[0], link6);
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(tay[1], link4);
		link2 = new SongLink(tay[4], link3);
		link1 = new SongLink(tay[4], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(tay[1] == queue.firstSong.song);
		assertTrue(null == queue.firstSong.link);
		assertTrue(tay[1] == queue.lastSong.song);

		// Test 4
		// 2 distinct artists
		queue = new PlayQueue();
		link8 = new SongLink(tay[2], null);
		link7 = new SongLink(twice[0], link8); // highest
		link6 = new SongLink(twice[3], link7);
		link5 = new SongLink(tay[0], link6); // highest
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(twice[2], link4);
		link2 = new SongLink(tay[4], link3);
		link1 = new SongLink(twice[1], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(null == queue.firstSong.link.link);
		assertTrue(tay[0] == queue.firstSong.song);
		assertTrue(twice[0] == queue.firstSong.link.song);
		assertTrue(twice[0] == queue.lastSong.song);

		// Test 4
		// 2 distinct artists
		queue = new PlayQueue();
		link8 = new SongLink(tay[2], null);
		link7 = new SongLink(twice[2], link8);
		link6 = new SongLink(twice[3], link7);
		link5 = new SongLink(tay[3], link6); // highest
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(twice[2], link4);
		link2 = new SongLink(tay[4], link3);
		link1 = new SongLink(twice[1], link2); // highest
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(null == queue.firstSong.link.link);
		assertTrue(twice[1] == queue.firstSong.song);
		assertTrue(tay[3] == queue.firstSong.link.song);
		assertTrue(tay[3] == queue.lastSong.song);

		// Test 4
		// 2 distinct artists
		queue = new PlayQueue();
		link8 = new SongLink(tay[0], null);
		link7 = new SongLink(tay[1], link8);
		link6 = new SongLink(tay[0], link7); // highest
		link5 = new SongLink(tay[3], link6);
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(twice[1], link4);
		link2 = new SongLink(twice[0], link3); // highest
		link1 = new SongLink(twice[1], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(null == queue.firstSong.link.link);
		assertTrue(twice[0] == queue.firstSong.song);
		assertTrue(tay[0] == queue.firstSong.link.song);
		assertTrue(tay[0] == queue.lastSong.song);

		// Test 4
		// 3 distinct artists
		queue = new PlayQueue();
		SongLink link12 = new SongLink(bp[3], null);
		SongLink link11 = new SongLink(tay[4], link12);
		SongLink link10 = new SongLink(bp[4], link11); // Highest
		SongLink link9 = new SongLink(tay[0], link10);
		link8 = new SongLink(twice[0], link9); // Highest
		link7 = new SongLink(bp[2], link8); 
		link6 = new SongLink(tay[1], link7); 
		link5 = new SongLink(tay[0], link6); // highest
		link4 = new SongLink(bp[1], link5);
		link3 = new SongLink(twice[2], link4);
		link2 = new SongLink(twice[3], link3);
		link1 = new SongLink(bp[1], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link12;

		queue.bestOf();
		//System.out.println(queue);
		
		assertTrue(null == queue.firstSong.link.link.link);
		assertTrue(tay[0] == queue.firstSong.song);
		assertTrue(twice[0] == queue.firstSong.link.song);
		assertTrue(bp[4] == queue.firstSong.link.link.song);
		assertTrue(bp[4] == queue.lastSong.song);

		// ignore the following statement
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Graded(description = "bestOf() (InPlace)", marks = 0)
	@Order(13)
	public void testBestOfInPlace() {

		Song[] tay = new Song[]{ // -----------
				new Song("Anti-Hero", 200, new Artist("Taylor Swift"),
						// 0. averageRating = 7.714285714285714
						new Review[]{new Review(10), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(10)}), // ------------------------------------
				new Song("Love Story", 235, new Artist("Taylor Swift"),
						new Review[]{ // 1. averageRating = 7.714285714285714
								new Review(4), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(10)}), // ------------------------------------
				new Song("Blank Space", 231, new Artist("Taylor Swift"),
						new Review[]{ // 2. averageRating = 5.428571428571429
								new Review(5), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(3)}), // ------------------------------------
				new Song("Shake It Off", 199, new Artist("Taylor Swift"),
						new Review[]{ // 3. averageRating = 6.142857142857143
								new Review(10), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(1)}), // ------------------------------------
				new Song("Delicate", 190, new Artist("Taylor Swift"),
						new Review[]{ // 4. averageRating = 2.4285714285714284
								new Review(3), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(3)}), // ------------------------------------
		};

		Song[] twice = new Song[]{ // -----------
				new Song("TT", 232, new Artist("TWICE"),
						// 0. averageRating = 8.142857142857142
						new Review[]{ // ---------------------------------------------
								new Review(10), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(8)}), // ------------------------------------
				new Song("Likey", 210, new Artist("TWICE"),
						// 1. averageRating = 5.428571428571429
						new Review[]{ // ---------------------------------------------
								new Review(4), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(9), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(3)}), // ------------------------------------
				new Song("Fancy", 203, new Artist("TWICE"),
						// 2. averageRating = 5.0
						new Review[]{ // ---------------------------------------------
								new Review(7), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(3)}), // ------------------------------------
				new Song("Cheer Up", 216, new Artist("TWICE"),
						// 3. averageRating = 1.5714285714285714
						new Review[]{ // ---------------------------------------------
								new Review(1), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(1)}), // ------------------------------------
		};

		Song[] bp = new Song[]{ // -----------
				new Song("DDU-DU DDU-DU", 209, new Artist("BLACKPINK"),
						// 0. averageRating = 3.7142857142857144
						new Review[]{ // ---------------------------------------------
								new Review(4), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(5)}), // ------------------------------------
				new Song("Kill This Love", 190, new Artist("BLACKPINK"),
						// 1. averageRating = 6.571428571428571
						new Review[]{ // ---------------------------------------------
								new Review(6), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(7), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(5)}), // ------------------------------------
				new Song("Lovesick Girls", 215, new Artist("BLACKPINK"),
						// 2. averageRating = 1.7142857142857142
						new Review[]{ // ---------------------------------------------
								new Review(2), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(2), // ------------------------------------
								new Review(1), // ------------------------------------
								new Review(1)}), // ------------------------------------
				new Song("How You Like That", 181, new Artist("BLACKPINK"),
						// 3. averageRating = 7.142857142857143
						new Review[]{ // ---------------------------------------------
								new Review(9), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(4), // ------------------------------------
								new Review(3), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(10)}), // ------------------------------------
				new Song("BOOMBAYAH", 240, new Artist("BLACKPINK"),
						// 4. averageRating = 7.142857142857143
						new Review[]{ // ---------------------------------------------
								new Review(9), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(6), // ------------------------------------
								new Review(8), // ------------------------------------
								new Review(5), // ------------------------------------
								new Review(10), // ------------------------------------
								new Review(4)}), // ------------------------------------
		};

		SongTest songTest = new SongTest();
		PlayQueue queue;

		// Test 1
		// 1 distinct artist, highest is in the middle somewhere, unique highest
		queue = new PlayQueue();
		SongLink link8 = new SongLink(tay[4], null);
		SongLink link7 = new SongLink(tay[3], link8);
		SongLink link6 = new SongLink(tay[4], link7);
		SongLink link5 = new SongLink(tay[3], link6);
		SongLink link4 = new SongLink(tay[0], link5);
		SongLink link3 = new SongLink(tay[2], link4);
		SongLink link2 = new SongLink(tay[4], link3);
		SongLink link1 = new SongLink(tay[2], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(link4 == queue.firstSong);
		assertTrue(tay[0] == queue.firstSong.song);
		assertTrue(null == queue.firstSong.link);
		assertTrue(link4 == queue.lastSong);

		// Test 2
		// 1 distinct artist, highest is at start somewhere, unique highest
		queue = new PlayQueue();
		link8 = new SongLink(tay[2], null);
		link7 = new SongLink(tay[3], link8);
		link6 = new SongLink(tay[4], link7);
		link5 = new SongLink(tay[3], link6);
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(tay[2], link4);
		link2 = new SongLink(tay[4], link3);
		link1 = new SongLink(tay[0], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(link1 == queue.firstSong);
		assertTrue(tay[0] == queue.firstSong.song);
		assertTrue(null == queue.firstSong.link);
		assertTrue(link1 == queue.lastSong);

		// Test 3
		// 1 distinct artist, highest is in middle somewhere, non unique highest
		queue = new PlayQueue();
		link8 = new SongLink(tay[2], null);
		link7 = new SongLink(tay[1], link8);
		link6 = new SongLink(tay[4], link7);
		link5 = new SongLink(tay[0], link6);
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(tay[1], link4);
		link2 = new SongLink(tay[4], link3);
		link1 = new SongLink(tay[4], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(link3 == queue.firstSong);
		assertTrue(tay[1] == queue.firstSong.song);
		assertTrue(null == queue.firstSong.link);
		assertTrue(link3 == queue.lastSong);

		// Test 3
		// 1 distinct artist, highest is in middle somewhere, non unique highest
		queue = new PlayQueue();
		link8 = new SongLink(tay[2], null);
		link7 = new SongLink(tay[1], link8);
		link6 = new SongLink(tay[4], link7);
		link5 = new SongLink(tay[0], link6);
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(tay[1], link4);
		link2 = new SongLink(tay[4], link3);
		link1 = new SongLink(tay[4], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(link3 == queue.firstSong);
		assertTrue(tay[1] == queue.firstSong.song);
		assertTrue(null == queue.firstSong.link);
		assertTrue(link3 == queue.lastSong);

		// Test 4
		// 2 distinct artists
		queue = new PlayQueue();
		link8 = new SongLink(tay[2], null);
		link7 = new SongLink(twice[0], link8); // highest
		link6 = new SongLink(twice[3], link7);
		link5 = new SongLink(tay[0], link6); // highest
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(twice[2], link4);
		link2 = new SongLink(tay[4], link3);
		link1 = new SongLink(twice[1], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(link5 == queue.firstSong);
		assertTrue(link7 == queue.firstSong.link);
		assertTrue(null == queue.firstSong.link.link);
		assertTrue(tay[0] == queue.firstSong.song);
		assertTrue(twice[0] == queue.firstSong.link.song);
		assertTrue(link7 == queue.lastSong);

		// Test 4
		// 2 distinct artists
		queue = new PlayQueue();
		link8 = new SongLink(tay[2], null);
		link7 = new SongLink(twice[2], link8);
		link6 = new SongLink(twice[3], link7);
		link5 = new SongLink(tay[3], link6); // highest
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(twice[2], link4);
		link2 = new SongLink(tay[4], link3);
		link1 = new SongLink(twice[1], link2); // highest
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(link1 == queue.firstSong);
		assertTrue(link5 == queue.firstSong.link);
		assertTrue(null == queue.firstSong.link.link);
		assertTrue(twice[1] == queue.firstSong.song);
		assertTrue(tay[3] == queue.firstSong.link.song);
		assertTrue(link5 == queue.lastSong);

		// Test 4
		// 2 distinct artists
		queue = new PlayQueue();
		link8 = new SongLink(tay[0], null);
		link7 = new SongLink(tay[1], link8);
		link6 = new SongLink(tay[0], link7); // highest
		link5 = new SongLink(tay[3], link6);
		link4 = new SongLink(tay[4], link5);
		link3 = new SongLink(twice[1], link4);
		link2 = new SongLink(twice[0], link3); // highest
		link1 = new SongLink(twice[1], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link8;

		queue.bestOf();

		assertTrue(link2 == queue.firstSong);
		assertTrue(link6 == queue.firstSong.link);
		assertTrue(null == queue.firstSong.link.link);
		assertTrue(twice[0] == queue.firstSong.song);
		assertTrue(tay[0] == queue.firstSong.link.song);
		assertTrue(link6 == queue.lastSong);

		// Test 4
		// 3 distinct artists
		queue = new PlayQueue();
		SongLink link12 = new SongLink(bp[3], null);
		SongLink link11 = new SongLink(tay[4], link12);
		SongLink link10 = new SongLink(bp[4], link11); // Highest
		SongLink link9 = new SongLink(tay[0], link10);
		link8 = new SongLink(twice[0], link9); // Highest
		link7 = new SongLink(bp[2], link8); 
		link6 = new SongLink(tay[1], link7); 
		link5 = new SongLink(tay[0], link6); // highest
		link4 = new SongLink(bp[1], link5);
		link3 = new SongLink(twice[2], link4);
		link2 = new SongLink(twice[3], link3);
		link1 = new SongLink(bp[1], link2);
		queue.nowPlaying = null;
		queue.firstSong = link1;
		queue.lastSong = link12;

		queue.bestOf();
		//System.out.println(queue);
		
		assertTrue(link5 == queue.firstSong);
		assertTrue(link8 == queue.firstSong.link);
		assertTrue(link10 == queue.firstSong.link.link);
		assertTrue(null == queue.firstSong.link.link.link);
		assertTrue(tay[0] == queue.firstSong.song);
		assertTrue(twice[0] == queue.firstSong.link.song);
		assertTrue(bp[4] == queue.firstSong.link.link.song);
		assertTrue(link10 == queue.lastSong);

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

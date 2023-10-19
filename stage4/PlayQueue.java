package stage4;

import doNotModify.SongLink;
import doNotModify.Review;
import stage1.Artist;
import stage2.Song;
import stage3.Playlist;

/*
 * For all questions below:
 * - You may assume the PlayQueue is in a valid state when each method is
 *   called.
 * 
 * - You must ensure that firstSong, lastSong, and nowPlaying remains in a
 *   valid state after the execution of each method.
 *   	- firstSong must refer to the first song link in the queue
 *      - lastSong must refer to the last song link in the queue
 *      - nowPlaying should refer to the currently playing song.
 *      
 * - To make life MUCH easier for yourself, draw diagrams on paper
 *   to see how your code affects the objects (instances AND references)
 *   
 *    Drawing diagram has statistically been proven to 
 *    assist in assignment success by 1729% (j/k, but true)
 */

public class PlayQueue {
	public SongLink nowPlaying; // The currently playing song link
	public SongLink firstSong; // The first song link in the play queue
	public SongLink lastSong; // The last song link in the play queue

	/**
	 * @return True if the queue is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return nowPlaying == null && firstSong == null && lastSong == null; 
	}

	/**
	 * Set nowPlaying to the first song link in the queue
	 */
	public void startQueue() {
		if (!(this.isEmpty())) {
			this.nowPlaying = this.firstSong;
		}
		return;
	}

	/**
	 * Add the passed song to the start of the PlayQueue. This method should use
	 * a reference copy when copying the song. You will need to make a new song
	 * link.
	 * 
	 * @param song
	 *            the song to add to the start of the PlayQueue
	 */
	public void addToStart(Song song) {
		if (song == null) {
			return;
		} else if (this.firstSong == null) {
			this.firstSong = new SongLink(song, null);
			this.lastSong = this.firstSong;
		} else {
			this.firstSong = new SongLink(song, this.firstSong);
		}
	}

	/**
	 * Add the passed song to the end of the PlayQueue.
	 * 
	 * @param song
	 *            the song to add to the end of the PlayQueue
	 */
	public void addToEnd(Song song) {
		if (song == null) {
			// null input
			return;
		} else if (this.lastSong == null) {
			// null queue
			this.firstSong = new SongLink(song, null);
			this.lastSong = this.firstSong;
		} else {
			// actually add it (and link to old last song)
			SongLink newSong = new SongLink(song, null);
			this.lastSong.link = newSong;
			this.lastSong = newSong;
		}
	}

	/**
	 * Move the nowPlaying link forwards.
	 * 
	 * - If no song is being played, this function should set nowPlaying to the
	 * first link in the queue.
	 * 
	 * - If there are no songs / links remaining in the queue, nowPlaying should
	 * be set to null.
	 * 
	 * The new song should also be returned.
	 * 
	 * @return The next song in the queue
	 */
	public Song nextSong() {
		if (this.nowPlaying == null) {
			this.nowPlaying = this.firstSong;
			if (this.nowPlaying == null) {
				return null;
			}
		} else if (this.nowPlaying.link == null) {
			this.nowPlaying = null;
			return null;
		} else {
			this.nowPlaying = this.nowPlaying.link;	
		}
		return this.nowPlaying.song;	
	}

	/**
	 * @param num
	 *            the song number to look for
	 * @return the song object associated with the number in the queue. The
	 *         first song in the queue is number 1, the second is number 2, and
	 *         so on. Return null if num does not represent a valid song in the
	 *         PlayQueue.
	 */
	public Song getSong(int num) {
		if (num <= 0) {
			return null;
		} else {
			return getSong(num, this.firstSong);
		}
	}

	public Song getSong(int num, SongLink current) {
		if (current == null) {
			// have reached end of PlayQueue
			return null;
		} else if (num == 1) {
			// have reached the desired song
			return current.song;
		} else {
			// undergo recursion
			return getSong(num-1, current.link);
		}
	}

	/**
	 * @return the total length of all songs in the PlayQueue from the start.
	 */
	public int totalPlaytime() {
		if (this.firstSong == null) {
			return 0;
		} else {
			return totalPlaytime(0, this.firstSong);
		}
	}

	public int totalPlaytime(int total, SongLink current) {
		if (current == null) {
			return total;
		} else {
			return totalPlaytime(total + current.song.length, current.link);
		}
	}

	/**
	 * @return the total length of all songs in the PlayQueue from the currently
	 *         playing song (inclusive). If nowPlaying is null, return 0.
	 */
	public int remainingPlaytime() {
		if (this.nowPlaying == null) {
			return 0;
		} else {
			// call the same recursive function as previous Q cause why not
			return totalPlaytime(0, this.nowPlaying);
		}
	}

	/**
	 * Add all songs from the playlist to the end of the PlayQueue
	 * 
	 * @param playlist
	 *            the playlist of songs to add from
	 */
	public void addToQueue(Playlist playlist) {
		if (playlist == null || playlist.songs.length == 0) {
			return;
		} else {
			addToQueue(playlist, 0);	
		}
	}

	public void addToQueue(Playlist playlist, int current) {
		if (!(this.firstSong == null && playlist.songs[current] == null)) {
			if (current >= playlist.songs.length) {
				return;
			} else {
				addToEnd(playlist.songs[current]);
			}
		}
		addToQueue(playlist, current+1);
	}

	/**
	 * Add all songs from the playlist to the end of the PlayQueue. Only songs
	 * which have different names, lengths and artists should be added. The
	 * songs should be copied using a reference copy. In other words, only songs
	 * not already in PlayQueue should be added. You can ignore reviews.
	 * 
	 * @param playlist
	 *            the playlist of songs to add from
	 */
	public void addToQueueFiltered(Playlist playlist) {
		if (playlist == null || playlist.songs.length == 0) {
			return;
		} else {
			addToQueueFiltered(playlist, 0);	
		}
	}

	public void addToQueueFiltered(Playlist playlist, int current) {
		if (!(this.firstSong == null && playlist.songs[current] == null)) {
			if (current >= playlist.songs.length) {
				return;
			} else {
				// check if already in the PlayQueue
				if (this.contains(playlist.songs[current]) == false) {
					addToEnd(playlist.songs[current]);
				}
			}
		}
		// this is reached from all cases where we don't add to the PlayQueue
		addToQueueFiltered(playlist, current+1);
	}

	public boolean contains(Song songCheck) {
		if (songCheck == null) {
			// System.out.println("vacuous truth");
			return true; // vacuous truth
		} else {
			// System.out.println("do the recursion one");
			return contains(songCheck, this.firstSong);
		}
	}

	public boolean contains(Song songCheck, SongLink inQueue) {
		if (inQueue == null) {
			// System.out.println("false");
			return false;
		} else if (songCheck.equalsTo(inQueue.song)) {
			// System.out.println("true");
			return true;
		} else {
			// System.out.println("recurse");
			return contains(songCheck, inQueue.link);
		}
	}

	// public boolean contains(Song song, int current) {
	// 	if (current >= this.songs.length) {
	// 		return false;
	// 	} else {
	// 		if (this.songs[current].equalsTo(song)) {
	// 			return true;
	// 		} else {
	// 			return false;
	// 		}
	// 	}
	// }

	/**
	 * Reverse the order of song links in the PlayQueue. The original SongLink
	 * objects should be reused i.e. you should not make any new objects. The
	 * firstSong and lastSong should be updated. nowPlaying should be retained.
	 */
	public void reversePlayQueue() {
		if (this.isEmpty()) {
			return;
		} else {
			this.lastSong = this.firstSong;
			reversePlayQueue(null, this.firstSong, this.firstSong.link);
		}
	}

	public void reversePlayQueue(SongLink before, SongLink current, SongLink after) {
		if (after == null) {
			current.link = before;
			this.firstSong = current;
			return;
		} else {
			current.link = before;
			reversePlayQueue(current, after, after.link);
		}
	}

	public int size() {
		if (this.isEmpty()) {
			return 0;
		} else {
			return size(this.firstSong, 0);
		}
	}

	public int size(SongLink head, int result) {
		if (head == null) {
			System.out.println(result);
			return result;
		} else {
			return size(head.link, result+1);
		}
	}

	/**
	 * 
	 * @return a Playlist object containing all the songs in the PlayQueue, in
	 *         reversed order. The original list should not be modified.
	 */
	public Playlist reversed() {
		if (this.isEmpty()) {
			return null;
		} else {
			// find size of playlist required
			Playlist result = new Playlist();
			result.songs = new Song[size()];
			return reversed(0, result, this.firstSong);
		}
	}

	public Playlist reversed(int index, Playlist result, SongLink adding) {
		if (adding == null) {
			return result;
		} else {
			result.songs[size() - 1 - index] = adding.song;
			return reversed(index+1, result, adding.link);
		}
	}
	
	/**
	 * Modify the PlayQueue to keep only the highest rated song by each distinct
	 * artist. The order of links should be based on the original order of the
	 * PlayQueue. YOU MUST NOT USE ANY ARRAYS IN YOUR SOLUTION. This will be
	 * manually checked after the assignment submission date.
	 * 
	 * For an extra challenge, complete this question while maintaining the
	 * original link objects associated with each song. The test
	 * "bestOfInPlace()" will check for this and is worth 0 marks.
	 */
	public void bestOf() {
		if (this.firstSong == null) {
			return;
		} else {
			bestOf(this.firstSong, this.firstSong.link, this.firstSong.song.artist, this.firstSong, null);
		}
	}

	public void bestOf(SongLink i, SongLink j, Artist artist, SongLink beforeJ, SongLink beforeI) {
		if (i.link == null) {
			this.lastSong = i;
			return;
		} else if (j == null) {
			bestOf(i.link, i.link.link, i.link.song.artist, i.link, i);
		} else {
			if (j.song.artist.name.equals(artist.name)) {
				// compare ratings and keep highest
				if (j.song.averageRating() > i.song.averageRating()) {
					// get rid of i (add variable, beforeI, and when beforeI == null need to update this.firstSong)
					// also don't forget to update this.lastSong when we are done with everything
					if (beforeI == null) {
						// i is the firstSong of the PlayQueue
						this.firstSong = i.link;
						bestOf(); // resets the situation to when first called
					} else {
						beforeI.link = i.link;
						bestOf(i.link, i.link.link, i.link.song.artist, i.link, beforeI);
					}
				} else {
					// get rid of j (will need extra variable, beforeJ)
					beforeJ.link = j.link;
					bestOf(i, beforeJ.link, artist, beforeJ, beforeI);
				}
			} else {
				// move to next song (next j) after doing nothing
				bestOf(i, j.link, artist, beforeJ.link, beforeI);
			}
		}
	}

	public String toString() {
		/**
		* DO NOT MODIFY
		*/
		return "PlayQueue:" + toString(firstSong, 1);
	}
		
	public String toString(SongLink link, int idx) {
		/**
		* DO NOT MODIFY
		*/
		if(link == null)
			return "";
		return "\n--- Song #" + idx + "---\n" + link.song + toString(link.link, idx + 1);
	}
}

package stage3;

import stage1.Artist;
import stage2.Song;

public class Playlist {
	public Song[] songs;

	/**
	 * Count the number of Song objects which contain an artist with the exact
	 * same name as the string passed. If the passed object is null, then the
	 * method should return 0.
	 * 
	 * @param artistName
	 *            the name to check
	 * @return the number of songs with an artist whose name is equal to the
	 *         passed parameter
	 */
	public int countSongsBy(String artistName) {
		if (this == null || this.songs == null) {
			return 0;
		} else {
			int count = 0;
			for (Song currentSong: this.songs) {
				if (currentSong.artist.name.equals(artistName)) {
					count++;
				}
			}
			return count;
		}
	}

	/**
	 * Add the passed song object to the end of the songs array. If the passed
	 * object is null, then the method should not modify the songs array. This
	 * method should make a reference copy of the passed song object.
	 * 
	 * @param song
	 *            the Song object to add
	 */
	public void addSong(Song song) {
		if (song == null) {
			return;
		} else {
			 int originalLength = this.songs.length;
			 Song[] newSongs = new Song[originalLength+1];
			 for (int i = 0; i < originalLength; i++) {
			 	newSongs[i] = this.songs[i];
			 }
			 newSongs[originalLength] = song;
			 this.songs = newSongs;
			 return;
		}
	}

	/**
	 * Remove all Song objects from the songs instance array which contain an
	 * artist with the exact same name as the string passed. If the passed
	 * object is null, then the method should not modify the songs array.
	 * 
	 * @param artistName
	 *            the artist name to search for
	 */
	public void removeAllSongsBy(String artistName) {
		if (artistName == null || this.songs == null) {
			return;
		} else {
			int newLength = this.songs.length - this.countSongsBy(artistName);
			Song[] newSongs = new Song[newLength];
			int newIndex = 0; 
			for (Song currentSong: this.songs) {
				if (!currentSong.artist.name.equals(artistName)) {
					newSongs[newIndex] = currentSong;
					newIndex++;
				}
			}
			this.songs = newSongs;
			return;
		}
	}

	/**
	 * DO NOT MODIFY
	 */
	public Playlist() {
		songs = new Song[0];
	}
}

package stage1;

public class Artist {
	public String name;

	/**
	 * Set the instance variables using the passed parameters
	 * 
	 * @param n
	 *            for name
	 */
	public Artist(String n) {
		name = n;
	}

	/**
	 * 
	 * @param s
	 * @return true if the calling objects name is valid, false otherwise. A
	 *         valid name must start with an upper case letter and be at least 2
	 *         characters long.
	 */
	public boolean validName() {
		char first = this.name.charAt(0);
		return this.name.length()>1 && first>'A' && first<'Z';
	}

	/**
	 * 
	 * @param other
	 *            the artist to compare against
	 * @return true if the calling object and parameter object have the same
	 *         name.
	 */
	public boolean equals(Artist other) {
		return this.name.equals(other.name);
	}

	/**
	 * @return the string representation of the calling object. Refer to the
	 *         unit tests for the exact format to output.
	 */
	public String toString() {
		return "Artist: " + name;
	}

}

/* ArtistPair is a storage class that acts as a weighted edge. 
 * It stores the artist id and the listening count.
 * ArtistPair is used to create the weighted directed graph (weightedFanMap).
 */

package domainObjects;

public class ArtistPair implements Comparable {

	public ArtistPair(int id, int popularity) {
		this.id = id;
		this.popularity = popularity;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Integer getPopularity() {
		return popularity;
	}
	
	public void setPopularity(Integer popularity) {
		this.popularity = popularity;
	}
	
	@Override
	public int compareTo(Object artistPopularity) {
		if(this.popularity > ((ArtistPair) artistPopularity).getPopularity())
			return 1;
		else if(this.popularity < ((ArtistPair) artistPopularity).getPopularity())
			return -1;
		else
			return 0;
	}

	private Integer id;
	private Integer popularity;
}

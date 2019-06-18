package application;

import java.io.IOException;

public interface RecommenderApplication {
	
	//Loads files into application
	public void loadFiles(String artistFileName, String userArtistFileName, String userFriendFileName) throws NumberFormatException, IOException;
	
	//Prints the list of friends of the given user
	public void listFriends(int userId);
	
	//Prints the user1’s friends in common with user2
	public void commonFriends(int userId1, int userId2);
	
	//Prints the list of artists listened by both users
	public void listArtists(int userId1, int userId2);
	
	//Prints the list of top 10 most popular artists listened by all users
	public void listTop10();
	
	//Recommends 10 most popular artists listened by the given user and his/her friends.
	public void recommend10(int userId);
	
}

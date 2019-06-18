package application;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import domainObjects.Artist;
import domainObjects.ArtistPair;


public class Client {
	//Entry point of application
	public static void main(String[] args) throws NumberFormatException, IOException {
		RecommenderApplication app = new RecommenderImplementation();
		app.loadFiles("artists.dat", "user_artists.dat", "user_friends.dat");
		app.listFriends(2);
		//app.commonFriends(2, 128);
		//app.listArtists(235, 635);
		//app.listTop10();
		//app.recommend10(2);
	}	
}

/* This is a utility class that is used to parse files and represents the data in the file as a map.
 * Friend map and fan map act as adjacency lists of vertices for undirected graph
 * Weighted fan map act as an adjacency list of edges for directed weighted graph
 */

package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import domainObjects.Artist;
import domainObjects.ArtistPair;

public class FileParser {

	//Parses the file and creates artist instances. Maps the artistId to the artist instances
	public Map<Integer, Artist> createArtistMap(String fileName) throws NumberFormatException, IOException {
		Map artistIdToArtist = new HashMap<Integer, Artist>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			
			//Skip first line
			String line = reader.readLine();
			
			//Iterate through file
            while ((line = reader.readLine()) != null) {
            	String[] fields = line.split("\t");
            	Integer artistId = Integer.parseInt(fields[0]);
            	String artistName = fields[1];
            	String artistUrl = fields[2];
            	//Create new artist instance
            	Artist newArtist = new Artist(artistId, artistName, artistUrl);
            	artistIdToArtist.put(artistId, newArtist);
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return artistIdToArtist;
	}
	
	//Parses the file and maps userId to LinkedList of friendIds
	public Map<Integer, LinkedList<Integer>> createFriendMap(String fileName) throws NumberFormatException, IOException {
		Map userIdToFriendIds = new HashMap<Integer, LinkedList<Integer>>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			
			//Skip first line
			String line = reader.readLine();
			
			//Iterate through file
            while ((line = reader.readLine()) != null) {
            	String[] fields = line.split("\t");
            	Integer userId = Integer.parseInt(fields[0]);
            	Integer friendId = Integer.parseInt(fields[1]);
            	
            	LinkedList<Integer> friendIds;
            	//If userId already exists in map
            	if(userIdToFriendIds.containsKey(userId))
            		friendIds = (LinkedList<Integer>) userIdToFriendIds.get(userId);
            	else
            		friendIds = new LinkedList<Integer>();
            	friendIds.add((friendId));	
            	
            	//Inserts new key-value or update value for existing key
            	userIdToFriendIds.put(userId, friendIds);
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return userIdToFriendIds;
	}
	
	//Parses the file and maps userId to LinkedList of ArtistPair. 
	//ArtistPair is an artist object with weight(total listening count)
	public Map<Integer, LinkedList<ArtistPair>> createWeightedFanMap(String fileName) throws NumberFormatException, IOException {
		Map userIdToArtistPairs = new HashMap<Integer, LinkedList<ArtistPair>>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			
			//Skip first line
			String line = reader.readLine();
			
			//Iterate through file
            while ((line = reader.readLine()) != null) {
            	String[] fields = line.split ("\t");
            	Integer userId = Integer.parseInt(fields[0]);
            	Integer artistId = Integer.parseInt(fields[1]);
            	Integer listeningCount = Integer.parseInt(fields[2]);
            	
            	LinkedList<ArtistPair> artistPairs;
            	//If userId already exists in map
            	if(userIdToArtistPairs.containsKey(userId))
            		artistPairs = (LinkedList<ArtistPair>) userIdToArtistPairs.get(userId);
            	else
            		artistPairs = new LinkedList<ArtistPair>();
            	
            	artistPairs.add(new ArtistPair(artistId, listeningCount));
            	userIdToArtistPairs.put(userId, artistPairs);
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return userIdToArtistPairs;
	}
	
	//Parses the file and maps userId to LinkedList of artistIds.
	public Map<Integer, LinkedList<Integer>> createFanMap(String fileName) throws NumberFormatException, IOException {
		Map userIdToArtistIds = new HashMap<Integer, LinkedList<Integer>>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			
			//Skip first line
			String line = reader.readLine();
			
			//Iterate through file
            while ((line = reader.readLine()) != null) {
            	String[] fields = line.split ("\t");
            	Integer userId = Integer.parseInt(fields[0]);
            	Integer artistId = Integer.parseInt(fields[1]);
            	
            	LinkedList<Integer> artistIds;
            	//If userId already exists in map
            	if(userIdToArtistIds.containsKey(userId))
            		artistIds = (LinkedList<Integer>) userIdToArtistIds.get(userId);
            	else
            		artistIds = new LinkedList<Integer>();
            	
            	artistIds.add(artistId);
            	userIdToArtistIds.put(userId, artistIds);
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return userIdToArtistIds;
	}
	
}

package application;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import domainObjects.Artist;
import domainObjects.ArtistPair;


public class RecommenderImplementation implements RecommenderApplication{

	//Constructor
	public RecommenderImplementation() {
		this.artistMap = new HashMap<Integer, Artist>();
		this.weightedFanMap = new HashMap<Integer, LinkedList<ArtistPair>>();
		this.fanMap = new HashMap<Integer, LinkedList<Integer>>();
		this.friendMap = new HashMap<Integer, LinkedList<Integer>>();
	}
	
	@Override
	public void loadFiles(String artistFileName, String userArtistFileName, String userFriendFileName) throws NumberFormatException, IOException {
		FileParser parser = new FileParser();
		this.artistMap = parser.createArtistMap(artistFileName);
		this.weightedFanMap = parser.createWeightedFanMap(userArtistFileName);
		this.fanMap = parser.createFanMap(userArtistFileName);
		this.friendMap = parser.createFriendMap(userFriendFileName);
	}
	
	@Override
	public void listFriends(int userId) {
		if(friendMap.get(userId) == null)
			return;
		//Get adjacency list of vertex
		for(Integer friendId : friendMap.get(userId)) {
			System.out.print(friendId + " ");
		}	
	}

	@Override
	public void commonFriends(int userId1, int userId2) {
		//Get adjacency list of both vertices and perform an intersect operation to get the common friends
		LinkedList<Integer> commonFriendIds = friendMap.get(userId1);
		if(commonFriendIds == null || friendMap.get(userId2) == null)
			return;
		commonFriendIds.retainAll(friendMap.get(userId2));
		for(Integer friendId : commonFriendIds) {
			System.out.print(friendId + " ");
		}
	}

	@Override
	public void listArtists(int userId1, int userId2) {
		//Get adjacency list of both vertices and perform an intersect operation to get the common artists
		LinkedList<Integer> commonArtistIds = fanMap.get(userId1);
		if(commonArtistIds == null || fanMap.get(userId2) == null)
			return;
		commonArtistIds.retainAll(fanMap.get(userId2));
		for(Integer artistId : commonArtistIds) {
			System.out.print(artistMap.get(artistId).getName().trim() + "\n");
		}
	}

	@Override
	public void listTop10() {
		//Initialize max heap
		PriorityQueue<ArtistPair> maxHeap = new PriorityQueue<ArtistPair>(Collections.reverseOrder());
		//Create a hashmap to map artist id to the sum of the listening count(Popularity).
		Map<Integer, Integer> artistIdToPopularity = createArtistIdToPopularityMap(weightedFanMap.keySet());
		
		//Create ArtistPair instances and insert them into max heap
		for(Integer artistId : artistIdToPopularity.keySet()) {
			Integer artistPopularity = artistIdToPopularity.get(artistId);
			ArtistPair artistPair = new ArtistPair(artistId, artistPopularity);
			maxHeap.add(artistPair);
		}
		
		//Remove top 10 from max heap and print them
		for(int i = 1; i < 11; i++) {
			if(maxHeap.peek() != null) {
				ArtistPair artistPair = maxHeap.poll();
				Integer artistId = artistPair.getId();
				Integer artistPopularity = artistPair.getPopularity();
				Artist artist = artistMap.get(artistId);
				System.out.print(i + " : " + artist.getName() + " : " + artistPopularity + "\n");
			}
		}
	}

	@Override
	public void recommend10(int userId) {
		PriorityQueue<ArtistPair> maxHeap = new PriorityQueue<ArtistPair>(Collections.reverseOrder());
		
		//Create a set containing user id and friend ids
		Set<Integer> userIds = new HashSet<Integer>();
		userIds.add(userId);
		if(friendMap.get(userId) != null) {
			for(Integer friendId : friendMap.get(userId))
				userIds.add(friendId);
		}
		
		Map<Integer, Integer> artistIdToPopularity = createArtistIdToPopularityMap(userIds);
		
		//Create ArtistPair instances and insert them into max heap
		for(Integer artistId : artistIdToPopularity.keySet()) {
			Integer artistPopularity = artistIdToPopularity.get(artistId);
			ArtistPair artistPair = new ArtistPair(artistId, artistPopularity);
			maxHeap.add(artistPair);
		}
		
		//Remove top 10 from max heap and print them
		for(int i = 1; i < 11; i++) {
			if(maxHeap.peek() != null) {
				ArtistPair artistPair = maxHeap.poll();
				Integer artistId = artistPair.getId();
				Integer artistPopularity = artistPair.getPopularity();
				Artist artist = artistMap.get(artistId);
				System.out.print(i + " : " + artist.getName() + " : " + artistPopularity + "\n");
			}
		}
	}
	
	//Helper method: returns a HashMap that maps the artistId to his/her popularity(total listening count)
	//In graph perspective, calculate the sum of weighted edges pointing to artists and map the sum to artistId
	private Map<Integer, Integer> createArtistIdToPopularityMap(Set<Integer> userIds) {
		
		Map<Integer, Integer> artistIdToPopularity = new HashMap<Integer, Integer>();
		
		//Iterate through every user
		for(Integer userId : userIds) {
			if(weightedFanMap.get(userId) != null) {
			//Iterate through every pair(AKA weighted edge) in adjacency list
				for(ArtistPair artistPair : weightedFanMap.get(userId)) {
					Integer artistId = artistPair.getId();
					Integer artistPopularity = artistPair.getPopularity();
					
					//If artistId already exists in artistIdToPopularity, update the artistPopularity
					if(artistIdToPopularity.containsKey(artistId)) {
						artistPopularity += artistIdToPopularity.get(artistId);
					}
					artistIdToPopularity.put(artistId, artistPopularity);
				}
			}
		}
		return artistIdToPopularity;
	}
	
	//Private fields
	private Map<Integer, Artist> artistMap;
	private Map<Integer, LinkedList<ArtistPair>> weightedFanMap;
	private Map<Integer, LinkedList<Integer>> fanMap;
	private Map<Integer, LinkedList<Integer>> friendMap;

}

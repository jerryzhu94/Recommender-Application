package testCase;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.*;


class RecommenderApplication_Test {

	@BeforeEach
	void init() throws NumberFormatException, IOException {
		app = new RecommenderImplementation();
		app.loadFiles("artists_simple.dat", "user_artists_simple.dat", "user_friends_simple.dat");
		System.setOut(new PrintStream(outContent));
	}
	
	@Test
	void testListFriends() {
		app.listFriends(1);
		String expected = "2";
		assertEquals(expected , outContent.toString().trim());
	}
	
	@Test
	void testListFriends_2() {
		app.listFriends(2);
		String expected = "1 3";
		assertEquals(expected , outContent.toString().trim());
	}
	
	@Test
	void testListFriends_Empty() {
		app.listFriends(4);
		String expected = "";
		assertEquals(expected , outContent.toString().trim());
	}

	@Test
	void testCommonFriends() {
		app.commonFriends(1, 3);
		String expected = "2";
		assertEquals(expected , outContent.toString().trim());
	}
	
	@Test
	void testCommonFriends_Empty() {
		app.commonFriends(1, 2);
		String expected = "";
		assertEquals(expected , outContent.toString().trim());
	}
	
	@Test
	void testListArtists() {
		app.listArtists(1,2);
		String expected = "artist2\nartist3";
		assertEquals(expected, outContent.toString().trim());
	}
	
	@Test
	void testListArtists_Edge() {
		app.listArtists(1,1);
		String expected = "artist1\nartist2\nartist3";
		assertEquals(expected, outContent.toString().trim());
	}
	
	@Test
	void testListTop10() {
		app.listTop10();
		String expected = "1 : artist1 : 9\n2 : artist2 : 7\n3 : artist3 : 6";
		assertEquals(expected, outContent.toString().trim());
	}
	
	
	@Test
	void testRecommend10() {
		app.recommend10(1);
		String expected = "1 : artist2 : 6\n2 : artist3 : 4\n3 : artist1 : 2";
		assertEquals(expected, outContent.toString().trim());
	}
	
	@Test
	void testRecommend10_Empty() {
		app.recommend10(4);
		String expected = "";
		assertEquals(expected, outContent.toString().trim());
	}
	
	
	private RecommenderApplication app;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
}

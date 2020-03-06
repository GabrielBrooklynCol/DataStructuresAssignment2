package musicPlaylist;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
//This assignment uses code from the previous assignment due to the
//similarities between the two. 

//class Song will be used as nodes for the playList class
class Song{
	public String track; //"track" referring to the Song's name.
	public Song next;
	//Constructor that creates a Song object with a "track" field.
	public Song(String t) {
		track = t; 
	}
	//displaySong gives the prints the Song object's track name.
	public void displaySong() {
		System.out.println(track);
	}
}
class playList{
	private Song first; 
	private SongHistoryList history = new SongHistoryList();
	/* The SongHistoryList object will be necessary
	 * for the SongHistoryList's list to exist as well as expand
	 * whenever a song will be deleted.
	 */
	public playList() {
		first = null;
	}
	//addSong(String s) will add the song onto the playList.
	public void addSong(String s) {
		Song track = new Song(s);
		track.next = first;
		first = track;
	}
	/*A song that is listened to will delete the song from the playList and be
	 *  and be inserted to the SongHistoryList list through the listen(String key) method (Based on the description:
	 *  "as a song is removed from the song's playlist, it should go to the
	 *  data structure tracking the listing history.")
	 */
	public Song listen(String key) {
		Song current = first;
		Song previous = first;
		while(!key.equals(current.track)) {
			if(current.next == null)
				return null;
			else {
				previous = current;
				current = current.next;
			}
		}
		if(current==first)
		{
			history.addSong(current.track); //Before the song is removed, it will be added to the Song history list.
			first = first.next;
			}
		else {
			history.addSong(previous.next.track); //Same function as the last history.addSong()
		previous.next = current.next;
		
		}
		return current;
		
	}
	public String listenToSong(){
		if (first.next.equals(null)) return null;
		String track = first.track;
		first = first.next;
		history.addSong(track);
		return track;
	}
	//displayList() displays the songs from the playlist.
	public void displayList() {
		System.out.println("Current songs on the playlist");
		Song current = first;
		while(current !=null) {
			current.displaySong();
			current = current.next;
		}
		System.out.println(" ");
	}
	
	public playList mergingFunction(playList p2, playList p1) {
		//p1's elements will appear after p2's elements.
		if(p1 == null || p2 ==null) return null;
		playList merged = new playList();
		Song current = p2.first; 
		while(current !=null) {
		p1.addSong(current.track); //The second playlist's elements will appear before the first playlist's elements.
		current = current.next; 
		}
		Song current2 = p1.first; 
		while (current2 !=null) {
			merged.addSong(current2.track); 
			/*The first playlist's elements that appeared after the second playlist's elements 
			* when added to p1 will appear first for the merged playlist.*/
			
			current2 = current2.next;
		}
		return merged;
		
	}
	//Uses the lastListened method from SongHistoryList() to display the list from the SongHistoryList object
	public void getHistory() {
		 history.lastListened();
	}
	
}
 class SongHistoryList{
		public Song first;	
		public SongHistoryList() {
		}
	
		public void addSong(String s) {
			Song track = new Song(s);
			track.next = first;
			first = track;
		}
		/*getHistory() displays the list from SongHistoryList().
		 * Thanks to the reference variable in the playList class,
		 * the list from SongHistoryList() can be expanded whenever you used the listen methods from playList.
		 */
		public void lastListened() {
			System.out.println("History of songs from the playlist");
			Song current = first;
			while(current !=null) {
				current.displaySong();
				current = current.next;
			}
			System.out.println(" ");
		}
		
	}
	
public class Artist {
	public static void main(String[] args) throws IOException{
	//There will be four buffered readers for the four weeks.
	BufferedReader weekNumber = new BufferedReader(new java.io.FileReader("regional-global-weekly-2020-01-31--2020-02-07.csv")); // The .csv files are weekly charts.
	BufferedReader week1 = new BufferedReader(new java.io.FileReader("regional-global-weekly-2020-01-31--2020-02-07.csv"));
	BufferedReader week2 = new BufferedReader(new java.io.FileReader("regional-global-weekly-2020-02-07--2020-02-14.csv"));
	BufferedReader week3 = new BufferedReader(new java.io.FileReader("regional-global-weekly-2020-02-14--2020-02-21.csv"));
	BufferedReader week4 = new BufferedReader(new java.io.FileReader("regional-global-weekly-2020-02-21--2020-02-28.csv"));
	Scanner songCount = new Scanner(weekNumber); //Since all weeks have the same number of entries all we need is one scanner for the first week to count the 
	//number of entries for all weeks.
	Scanner file = new Scanner(week1);
	Scanner file2 = new Scanner(week2);
	Scanner file3 = new Scanner(week3);
	Scanner file4 = new Scanner(week4);
	int eleCount = 0;
	int skipLine = 0;
	//Skips two beginning lines 
	while(skipLine < 2) {
		songCount.nextLine();
		skipLine++;
	}
	skipLine=0;
	//Gathering the number count for the amount of songs on the list.
	while(songCount.hasNext()) { 
		String linet = songCount.nextLine();
		String[] values = linet.split(",");
		String number = values[0];
		eleCount = Integer.parseInt(number);
	}
	songCount.close();
	//Gathering songs of Week 1.
	while(skipLine < 2) {
		file.nextLine();
		skipLine++;
	}
	skipLine = 0;
	String[] songsWeek1 = new String[eleCount];
	//This loop will add the nextLine to a String, then onto the songs array.
	
	for(int i = 0; i < eleCount;i++) {
		String musicLine = file.nextLine();
			//This split doesn't separate the commas inside the element.
			String[] musicLineArray =  musicLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			//"[1]" is where the Song is located
			songsWeek1[i] = musicLineArray[1];
	}
	file.close();

	//Creating Artist Array that removes Double quotations on their name.
	String[] songsWeek1NonQuotes = new String[eleCount];
	for(int i = 0;i<songsWeek1.length;i++){
		//Turning element into a String
		String strLine = songsWeek1[i].toString();
		//Using the beginning position to see if it's a double quote.
		String strPositionNum = strLine.substring(0,1);
		//Beginning double quote checker. 
		if(strPositionNum.startsWith("\"")) {
			//substring that begins after the first quotation to the last quotation.
			songsWeek1NonQuotes[i] = strLine.substring(1, strLine.length()-1);
		}
		//If there are no quotes, then the song's name is included without the need of a substring.
		else {
		 songsWeek1NonQuotes[i] = strLine;
		}
	}
	
	//Gathering songs for Week 2.
	while(skipLine < 2) {
		file2.nextLine();
		skipLine++;
	}
	skipLine = 0;
	String[] songsWeek2 = new String[eleCount];
	for(int i = 0; i < eleCount;i++) {
		String musicLine = file2.nextLine();
			String[] musicLineArray =  musicLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			songsWeek2[i] = musicLineArray[1];
	}
	
	file2.close();
	String[] songsWeek2NonQuotes = new String[eleCount];
	for(int i = 0;i<songsWeek2.length;i++){
		String strLine = songsWeek2[i].toString();
		String strPositionNum = strLine.substring(0,1);
		if(strPositionNum.startsWith("\"")) {
			songsWeek2NonQuotes[i] = strLine.substring(1, strLine.length()-1);
		}
		else {
		 songsWeek2NonQuotes[i] = strLine;
		}
	}

	//Gathering songs for Week 3.
	while(skipLine < 2) {
		file3.nextLine();
		skipLine++;
	}
	skipLine = 0;
	String[] songsWeek3 = new String[eleCount];
	for(int i = 0; i < eleCount;i++) {
		String musicLine = file3.nextLine();
			String[] musicLineArray =  musicLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			songsWeek3[i] = musicLineArray[1];
	}
	file3.close();
	String[] songsWeek3NonQuotes = new String[eleCount];
	for(int i = 0;i<songsWeek3.length;i++){
		String strLine = songsWeek3[i].toString();
		String strPositionNum = strLine.substring(0,1);
		if(strPositionNum.startsWith("\"")) {
			songsWeek3NonQuotes[i] = strLine.substring(1, strLine.length()-1);
		}
		else {
		 songsWeek3NonQuotes[i] = strLine;
		}
	}
	//Gathering songs for Week 4.
		while(skipLine < 2) {
			file4.nextLine();
			skipLine++;
		}
		skipLine = 0;
		String[] songsWeek4 = new String[eleCount];
		for(int i = 0; i < eleCount;i++) {
			String musicLine = file4.nextLine();
				String[] musicLineArray =  musicLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				songsWeek4[i] = musicLineArray[1];
		}
		file4.close();
		String[] songsWeek4NonQuotes = new String[eleCount];
		for(int i = 0;i<songsWeek4.length;i++){
			String strLine = songsWeek4[i].toString();
			String strPositionNum = strLine.substring(0,1);
			if(strPositionNum.startsWith("\"")) {
				songsWeek4NonQuotes[i] = strLine.substring(1, strLine.length()-1);
			}
			else {
			 songsWeek4NonQuotes[i] = strLine;
			}
		}
		System.out.println("Question 1: Can you read from multiple input files?");
	String[][] arrayOfWeeks = {songsWeek1NonQuotes,songsWeek2NonQuotes,songsWeek3NonQuotes,songsWeek4NonQuotes}; //Adding the weeks gathered from files onto an array.
	for (int i = 0; i < arrayOfWeeks.length; i++) {
		String Week = Arrays.deepToString(arrayOfWeeks[i]).replace("[","").replace("]",""); //Changing array to string.
		System.out.println(Week);
	}
	System.out.println("Question 2: Can you build a playlist?");
	/* The elements from the array will be inserted into the stack.
	 * Since the stack is LIFO, the array will be traveled backwards
	 * in order to add the elements from Week 1 to 4.
	 */
	playList playlist = new playList();
	
	for(int i = arrayOfWeeks.length-1; i >= 0; i--) {
		String Week = Arrays.deepToString(arrayOfWeeks[i]).replace("[","").replace("]","");
		playlist.addSong(Week);
		}
	playlist.displayList();
	System.out.println("Using the listen() method and displaying the SongHistoryList");
	playlist.listen(Arrays.deepToString(arrayOfWeeks[0]).replace("[","").replace("]","")); //This will remove the first week.
	System.out.println("Displaying the playlist after the listen() Method");
	playlist.displayList();
	System.out.println("Displaying the SongHistoryList");
	playlist.getHistory();
	System.out.println("Using listenToSong() method");
	playlist.listenToSong();
	playlist.displayList();
	System.out.println();
	System.out.println("Merging lists");
	playList firstPartPlaylist = new playList();
	firstPartPlaylist.addSong(Arrays.deepToString(arrayOfWeeks[0]).replace("[","").replace("]",""));
	playList secondPartPlaylist = new playList();
	secondPartPlaylist.addSong(Arrays.deepToString(arrayOfWeeks[1]).replace("[","").replace("]",""));
	System.out.println("First Part Playlist: ");
	firstPartPlaylist.displayList();
	System.out.println("Second part Playlist:");
	secondPartPlaylist.displayList();
	System.out.println("Merging the playlists.");
	playList mergedPlaylist = playlist.mergingFunction(secondPartPlaylist, firstPartPlaylist);
	mergedPlaylist.displayList();
	}
	
}

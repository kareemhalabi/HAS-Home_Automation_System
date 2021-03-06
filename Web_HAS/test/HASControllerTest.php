<?php

require_once (__DIR__.'/../controller/Controller.php');	
require_once (__DIR__.'/../persistence/PersistenceHAS.php');
require_once (__DIR__.'/../model/HAS.php');
require_once (__DIR__.'/../model/Artist.php');
require_once (__DIR__.'/../model/Song.php');
require_once (__DIR__.'/../model/Album.php'); 


class HASControllerTest extends PHPUnit_Framework_TestCase{

    protected $c;
    protected $pm;
    protected $hm;
	
    protected function setUp(){
    	$this->c = new Controller();
    	$this->pm = new PersistenceHAS();
    	$this->hm = $this->pm->loadDataFromStore();
    	$this->hm->delete();
    	$this->pm->writeDataToStore($this->hm);	
    }
    protected function tearDown()
    {
    }
    public function testCreateArtist() { 
    	$this->assertEquals(0, count($this->hm->getArtists()));
    	$name = "Adele";
    	try {
    		$this->c->createArtist($name);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$this->fail();
    	}
    	// check file contents
    	$this->hm = $this->pm->loadDataFromStore();
    	$this->assertEquals(1, count($this->hm->getArtists()));
    	$this->assertEquals($name, $this->hm->getArtist_index(0)->getName());
    }
    public function testCreateArtistSpaces(){
    	$this->assertEquals(0, count($this->hm->getArtists()));
    	$name = "  ";
    	$error = "";
    	try{
    		$this->c->createArtist($name);
    	}
    	catch (Exception $e) {			
    		$error = $e->getMessage();
    	}
    	//Check the error message returned
    	$this->assertEquals("Artist name cannot be empty!", $error);
    	// Make sure no artist was created
    	$this->hm= $this->pm->loadDataFromStore();
    	$this->assertEquals(0, count($this->hm->getArtists()));
    }
    
    public function testCreateSong() {
    	$pm=new PersistenceHas();
    	$hm = $pm->loadDataFromStore();
    	$name = "Rolling in the Deep";
    	$duration = 150;
    	$position = 2;
    	$artistName = "Adele";
    	$albumName = "21";
    	//Create the data that createSong takes as input.
    	try{
    		$artist = $this->c->createArtist($artistName);
    		$album = $this->c->createAlbum($albumName, "Pop", 2011-05-24, $artistName);
    	}
    	catch (Exception $e){
    		$this->fail();
    	}
    	$this->hm = $this->pm->loadDataFromStore();	//load in the test data needed to test createAlbum.
    	$this->assertEquals(0, count($this->hm->getSongs())); //Make sure we have zero songs before we create the first.
    	try{
    		$this->c->createSong($name, $duration, $position,$albumName);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$this->fail();
    	}
    	//Get all the data that was saved to HAS and compare it with expected results.
    	$this->hm = $this->pm->loadDataFromStore(); 
    	$this->assertEquals(1, count($this->hm->getSongs()));
    	$this->assertEquals($name, $this->hm->getSong_index(0)->getName());
  		$this->assertEquals($albumName, $this->hm->getAlbum_index(0)->getname());
    	$this->assertEquals($position, $this->hm->getSong_index(0)->getposition());
  		$this->assertEquals($duration, $this->hm->getSong_index(0)->getDuration());
    } 
    public function testCreateSongSpaces(){
    	$name =" ";
    	$error = "Test Failed";
    	$this->assertEquals(0, count($this->hm->getSongs())); //Make sure we have zero songs before we create the first.
    	//Create the data that createSong takes as input.
    	try{
    		$artist = $this->c->createArtist("Adele");
    		$album = $this->c->createAlbum("21", "Pop", 2011-05-24, "Adele");
    	}
    	catch (Exception $e){
    		$this->fail();
    	}
    
    	try{
    		$this->c->createSong($name, 200, 6,"21");
    	}
    	catch (Exception $e){
    		$error = $e->getMessage();
    	}
    	//Check the error message returned
    	$this->assertEquals("Song name cannot be empty! ", $error);
    	// Make sure no Songs were created
    	$this->hm= $this->pm->loadDataFromStore();
    	$this->assertEquals(0, count($this->hm->getSongs()));
    }
    public function testCreateAlbum() { //Passes Test!
    	//Create required test data - 3 attributes and a Artist object. 
    	$this->assertEquals(0, count($this->hm->getAlbums()));
    	$name = "Greatest Hits";
    	$artistName = "Bob Marley";
    	$genre = "Reggae";
    	$releaseDate = 1999-05-24;
    	try{
    		$myArtist = $this->c->createArtist($artistName);
    	}
    	catch (Exception $e){
    		$this.fail();
    	}
    	$this->hm = $this->pm->loadDataFromStore();	//load in the test data needed to test createAlbum.
    	$this->assertEquals($artistName,$this->hm->getArtist_index(0)->getName());
    	try{
    		//Pass in the 3 attributes and the name of the aritst just created, the method takes that name
    		//and finds the corresponding artist object that is in the Home Audio System.
    		$this->c->createAlbum($name, $genre, $releaseDate, $artistName); 
    	}
    	catch (Exception $e) {	// check that no error occurred
    		$this->fail();
    	}
    	// check file contents
    	$this->hm = $this->pm->loadDataFromStore();
    	$this->assertEquals(1, count($this->hm->getAlbums()));
    	$this->assertEquals($name, $this->hm->getAlbum_index(0)->getName()); 
    }
    public function testCreateAlbumSpaces(){
    	$name =" ";
    
    	//Create the data that createSong takes as input.
    	try{
    		$artist = $this->c->createArtist("Adele");
    	}
    	catch (Exception $e){
    		$this->fail();
    	}
    	$this->hm = $this->pm->loadDataFromStore();	//load in the test data needed to test createAlbum.
    	$this->assertEquals(0, count($this->hm->getAlbums())); //Make sure we have zero Albums before we create the first.
    	try{
    		$this->c->createAlbum($name, 210, 5, "Adele");
    	}
    	catch (Exception $e){
    		$error = $e->getMessage();
    	}
    	//Check the error message returned
    	$this->assertEquals("Album name cannot be empty! ", $error);
    	// Make sure no Albums were created
 
    	$this->assertEquals(0, count($this->hm->getAlbums()));
    }
    public function testCreateRoom(){
    		$this->assertEquals(0, count($this->hm->getRooms()));
    		try{
    			$this->c->createRoom("Living Room", 25, false);
    		}
    		catch( Exception $e){
    			$this->fail();
    		}
    		$this->hm = $this->pm->loadDataFromStore();
    		$this->assertEquals(1, count($this->hm->getRooms()));
    		$this->assertEquals("Living Room", $this->hm->getRoom_index(0)->getName());
    		$this->assertEquals(false, $this->hm->getRoom_index(0)->getMute());
    }
    public function testCreatePlaylist(){
    	$this->assertEquals(0, count($this->hm->getPlaylists()));
    	try{
    		$songName = "Rumor Has It";
    		$artist = $this->c->createArtist("Adele");
    		$album = $this->c->createAlbum("21", "Pop", 2011-05-24, "Adele");
    		$song = $this->c->createSong($songName, 300, 2, "21");
    	}
    	catch (Exception $e){
    		$this->fail();
    	}
    	try{
    		$playlist = $this->c->createPlaylist("Turn Up", "Rumor Has It" );
    	}
    	catch(Exception $e){
    		$this->fail();
    	}
    	//Check what was saved.
    	$this->hm = $this->pm->loadDataFromStore();
    	$this->assertEquals(1, count($this->hm->getPlaylists()));
    	$this->assertEquals("Rumor Has It", $this->hm->getPlaylist_index(0)->getSong_index(0)->getName());
    }
    public function testAddSongToPlaylist(){
	//Create required data
    	try{
    		$artist = $this->c->createArtist("Adele");
    		$album = $this->c->createAlbum("21", "Pop", 2011-05-24, "Adele");
    		$song = $this->c->createSong("Skyfall", 300, 2, "21");
    		$song2 = $this->c->createSong("Rolling in the Deep", 309, 9, "21");
    		$song3 = $this->c->createSong("Rumor Has It", 250, 6, "21");
    		$playlist = $this->c->createPlaylist("Turn Up", "Rolling in the Deep" );
    		$this->c->addSongToPlaylist("Turn Up", "Skyfall");
    		
    	}
    	catch(Exception $e){
    		$this->fail();
    	}
    	try{
    		$this->c->addSongToPlaylist("Turn Up", "Skyfall");
    		$this->c->addSongToPlaylist("Turn Up", "Rumor Has It");
    		
    	}
    	catch(Exception $e){
    		$this->fail();
    	}
    	//Check what was saved. 	
    	$this->hm = $this->pm->loadDataFromStore();
    	$this->assertEquals(3, count($this->hm->getSongs()));
    	$this->assertEquals("Rolling in the Deep", $this->hm->getPlaylist_index(0)->getSong_index(0)->getName());
    	$this->assertEquals("Turn Up",  $this->hm->getPlaylist_index(0)->getName());
    	$this->assertEquals(3, count($this->hm->getPlaylist_index(0)->getSongs()));
    }
    public function testCreateRoomGroup(){
    	$this->assertEquals(0, count($this->hm->getRoomGroups()));
    	try{
    		$this->c->createRoom("Living Room", 50, false);
    	}
    	catch(Exception $e){
    		$this->fail();
    	}
    	try{
    		$this->c->createRoomGroup("Main Floor","Living Room");
    	}
    	catch(Exception $e){
    		$this->fail();
    	}
    	$this->hm = $this->pm->loadDataFromStore();
    	$this->assertEquals(1, count($this->hm->getRoomGroups()));
    	$this->assertEquals("Main Floor", $this->hm->getRoomGroup_index(0)->getName());
    }
    public function testAddRoomToGroup(){
    	//Create test data
    	try{
    		$this->c->createRoom("Living Room", 50, false);
    		$this->c->createRoomGroup("Main Floor","Living Room");

    	}
    	catch (Exception $e){
    		$this->fail();
    	}
    	try{
    		$this->c->createRoom("TV Room", 75, false);
    		$this->c->createRoom("Kitchen", 75, false);
    		$this->c->addRoomToGroup("Main Floor", "TV Room");
    		$this->c->addRoomToGroup("Main Floor", "Kitchen");
    	}
    	catch(Exception $e){
    		$this->fail();
    	}
    	$this->hm = $this->pm->loadDataFromStore();
    	$this->assertEquals(3, $this->hm->numberOfRooms());
    	$this->assertEquals(3, count($this->hm->getRoomGroup_index(0)->getRooms()));
    	
    	}
    	public function testChangeVolume(){
    		//Create test data
    		try{
    			$this->c->createRoom("Living Room", 50, false);
    			
    		}
    		catch(Exception $e){
    			$this->fail();
    		}
    		try{
    			$this->c->changeVolume("Living Room", 100, true);
    		}
    		catch(Exception $e){
    			$this->fail();
    		}
    		$this->hm = $this->pm->loadDataFromStore();
    		$this->assertEquals(100, $this->hm->getRoom_index(0)->getVolume());
    	}
    	public function testChangeGroupVolume(){
    		try{
    			$this->c->createRoom("Bedroom", 20, false);
    			$this->c->createRoom("TV Room", 75, false);
    			$this->c->createRoomGroup("Main Floor", "Bedroom");
    			$this->c->addRoomToGroup("Main Floor", "TV Room");
    		}
    		catch(Exception $e){
    			$this->fail();
    		}
    		//Try Changing the Volume
    		try {
    			$this->c->changeGroupVolume("Main Floor", 90, false);
    		} 
    		catch (Exception $e) {
    			$this->fail();
    		}
    		//Check Values
    		$this->hm = $this->pm->loadDataFromStore();
    		$this->assertEquals(2, $this->hm->numberOfRooms());
    		//Check that every room in the group has the new volume
    		$this->assertEquals(90, $this->hm->getRoomGroup_index(0)->getRoom_index(0)->getVolume()); 
    		$this->assertEquals(90, $this->hm->getRoomGroup_index(0)->getRoom_index(1)->getVolume()); 
    	}
    	public function testPlayPlayableRoom(){
    		//Create test data
    		try {
    			$artist = $this->c->createArtist("Adele");
    			$album = $this->c->createAlbum("21", "Pop", 2011-05-24, "Adele");    		
    			$this->c->createSong("Skyfall", 300, 2, "21");
    			$this->c->createRoom("Living Room", 50, false);			   			
    		} 
    		catch (Exception $e) {
    			$this->fail();
    		}
    		$this->hm = $this->pm->loadDataFromStore();
    		$myPlayable = $this->hm->getSong_index(0);
    		$myRoom = $this->hm->getRoom_index(0);
    		try {
    			$this->c->playPlayableRoom($myRoom, $myPlayable);
    		} 
    		catch (Exception $e) {
    			$this->fail();
    		}
    		//Check the data saved in the model.
    		$this->hm = $this->pm->loadDataFromStore();
    		$this->assertEquals(true,$myRoom->hasPlayable());
    	}
    	public function testPlayPlayableRG(){
    		try {
    			$this->c->createRoom("Living Room", 55, false);
    			$this->c->createRoomGroup("Main Floor", "Living Room");
    			$this->c->createArtist("ACDC");
    			$this->c->createAlbum("Hits", "Rock", 1980-05-03, "ACDC");
    		} catch (Exception $e) {
    			$this->fail();
    		}
    		$this->hm = $this->pm->loadDataFromStore();
    		$myPlayable = $this->hm->getAlbum_index(0);
    		$myRoomGroup = $this->hm->getRoomGroup_index(0);
    		try {
    			$this->c->playPlayableRG($myRoomGroup, $myPlayable);
    		} 
    		catch (Exception $e) {
    			$this->fail();
    		}
    		$this->hm = $this->pm->loadDataFromStore();
    		$this->assertEquals(true, $myRoomGroup->hasPlayable());
    		
    	}
    	public function testSortByAlbum(){
    		//Create a list of albums
    		try{
    			$this->c->createArtist("Adele");
    			$this->c->createArtist("Bob Marley");
    			$this->c->createAlbum("C", "Pop", 02-02-2016, "Adele");
    			$this->c->createAlbum("A", "Pop", 02-09-2016, "Adele");
    			$this->c->createAlbum("B", "Reggae", 02-02-2016, "Bob Marley");
    			$this->c->createSong("Hello", 170, 1, "C");
    			$this->c->createSong("Rolling in the Deep", 130, 2, "A");
    			$this->c->createSong("Jammin", 155, 3, "B");
    		}
    		catch (Exception $e){
    			$this->fail();
    		}
    		$sortedSongs=array();
    		//Call the Method
    		try{
    			$this->c->sortbyAlbum();
    			$sortedSongs=$this->c->sortbyAlbum();
    		}
    		catch(Exception $e){
    			$this->fail();
    		}
    		//Check what was saved 
    		$this->hm = $this->pm->loadDataFromStore();    		
    		$songName= array_values($sortedSongs)[0]->getName();
    		$this->assertEquals($songName, $this->hm->getAlbum_index(1)->getSong_index(0)->getName());
    	}
    public function testsortbyArtist(){
    	//Create some artists
    	try {
    		$this->c->createArtist("Adele");
    		$this->c->createArtist("Bob Marley");
    		$this->c->createAlbum("C", "Pop", 02-02-2016, "Adele");
    		$this->c->createAlbum("A", "Pop", 02-09-2016, "Adele");
    		$this->c->createAlbum("B", "Reggae", 02-02-2016, "Bob Marley");
    		$this->c->createSong("Hello", 170, 1, "C");
    		$this->c->createSong("Rolling in the Deep", 130, 2, "A");
    		$this->c->createSong("Jammin", 155, 3, "B");
    	} catch (Exception $e) {    
    		$this->fail();
    	}
    	$sortedSongs=array();
    	try{
    		$this->c->sortbyAlbum();
    		$sortedSongs=$this->c->sortbyAlbum();
    	}
    	catch(Exception $e){
    		$this->fail();
    	}
    	$this->hm = $this->pm->loadDataFromStore();
    	$songName= array_values($sortedSongs)[0]->getName();
    	$this->assertEquals($songName, $this->hm->getAlbum_index(1)->getSong_index(0)->getName());
    }
}
?>

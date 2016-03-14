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
    public function testCreateArtist() { //Passes Test!
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
    public function testCreateSong() { //Fails test - problematic code in try block.
    	$this->assertEquals(0, count($this->hm->getSongs()));
    	$pm=new PersistenceHas();
    	$hm = $pm->loadDataFromStore();
    
    	$name = "Rolling in the Deep";
    	$duration = 150;
    	$position = 2;
    	$artist = new Artist("Adele");
    	$hm->addArtist($artist);
    	$pm->writeDataToStore($hm);
    	$album = new Album("21", "Pop", 2011-05-24, $artist);
    	$hm->addAlbum($album);
    	$pm->writeDataToStore($hm);
    	try {
    		$this->c->createSong("TestAlbum", 60, 7, $album);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$this->fail();
    	}
    	 
    	// check file contents
    	$this->hm = $this->pm->loadDataFromStore();
    	$this->assertEquals(1, count($this->hm->getSongs()));
    	$this->assertEquals($name, $this->hm->getAlbum_index(0)->getSong_index(0)->getName());
  		$this->assertEquals($album, $this->hm->getAlbum()->getname());
    	$this->assertEquals($position, $this->hm->getSong()->getposition());
  		$this->assertEquals($duration, $this->hm->getSong()->getDuration());
    }
    public function testCreateAlbum() { //Fails test - problematic code in the try block.
    	$this->assertEquals(0, count($this->hm->getAlbums()));
    	$name = "Greatest Hits";
    	$genre = "Reggae";
    	$releaseDate = 1999-05-24;
    	$artist = $this->c->createArtist("Bob Marley");
    	$myArtist = new Artist("Bob Marley");
    	try{
    		$this->c->createAlbum($name, $genre, $releaseDate, $myArtist);
    	}
    	catch (Exception $e) {
    		// check that no error occurred
    		$this->fail();
    	}
    	// check file contents
    	$this->hm = $this->pm->loadDataFromStore();
    	$this->assertEquals(1, count($this->hm->getAlbums()));
    	$this->assertEquals($name, $this->hm->getAlbum_index(0)->getName());
    }
    
    
}  
?>

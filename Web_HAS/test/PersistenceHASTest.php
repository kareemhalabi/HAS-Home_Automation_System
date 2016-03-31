<?php 

require_once 'C:\Users\Alex\Desktop\Documents\Group05\Web_HAS\persistence\PersistenceHAS.php';
require_once 'C:\Users\Alex\Desktop\Documents\Group05\Web_HAS\model\HAS.php';
require_once 'C:\Users\Alex\Desktop\Documents\Group05\Web_HAS\model\Album.php';
require_once 'C:\Users\Alex\Desktop\Documents\Group05\Web_HAS\model\Artist.php';
//require_once (__DIR__.'/../persistence/PersistenceHAS.php');
//require_once (__DIR__.'/../model/HAS.php');
//require_once (__DIR__.'/../model/Album.php');
//require_once (__DIR__.'/../model/Artist.php');




class PersistenceHASTest extends PHPUnit_Framework_TestCase{
	protected $pm;
	
	protected function setUp(){
		$this->pm = new PersistenceHAS();
	}
	protected function tearDown(){
	
	}
	public function testPersistence(){ //Passes Test - Persistence Class method are OK. 
		//Create test data
		$hm = HAS::getInstance();
		$artist = new Artist("Adele");
		$album = new Album("21", "Pop", 01-24-2011, $artist);
		$hm->addAlbum($album);
	
		$this->pm->writeDataToStore($hm);
		//Clear all data from memory
		$hm->delete();
		$this->assertEquals(0, count($hm->getAlbums()));
	
		//Load it back in
		$hm = $this->pm->loadDataFromStore();
		
		//Check that we got it back
		$this->assertEquals(1, count($hm->getAlbums()));
		$myAlbum = $hm->getAlbum_index(0);
		$this->assertEquals("21", $myAlbum->getName());
		
	}
}
?>

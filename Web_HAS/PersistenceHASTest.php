<?php 
/*require_once 'C:\Users\Alex\Desktop\Documents\Group05\Web_HAS_Alex\persistence\PersistenceHAS.php';
require_once 'C:\Users\Alex\Desktop\Documents\Group05\Web_HAS_Alex\model\HAS.php';
require_once 'C:\Users\Alex\Desktop\Documents\Group05\Web_HAS_Alex\model\Album.php';
require_once 'C:\Users\Alex\Desktop\Documents\Group05\Web_HAS_Alex\model\Artist.php';
*/
require_once (__DIR__.'/../persistence/PersistenceHAS.php');
require_once (__DIR__.'/../model/HAS.php');
require_once (__DIR__.'/../model/Artist.php');
require_once (__DIR__.'/../model/Song.php');
require_once '(__DIR__.'/../model/Album.php)';

class PersistenceHASTest extends PHPUnit_Framework_TestCase{
	protected $pm;
	
	protected function setUp(){
		$this->pm = new PersistenceHAS();
	}
	protected function tearDown(){
	
	}
	public function testPersistence(){
		//Create test data
		$hm = HAS::getInstance();
		$artist = new Artist("Adele");
		$album = new Album("21", "Pop", 2011-01-24, $artist);
		$hm->addAlbum($album);
	
		//Write all data
		$this->pm->writeDataToStore($hm);
		//Clear all data from memory
		$hm->delete();
		$this->assertEquals(0, count($hm->getAlbums()));
	
		//Load it back in
		$hm = $this->pm->loadDataFromStore();
		
		//Check that we get the album by checking its attributes against their expected values. 
		$this->assertEquals(1, count($hm->getAlbums()));
		$myAlbum = $hm->getAlbum_index(0);
		$this->assertEquals("21", $myAlbum->getName());
		$this->assertEquals("Pop", $myAlbum->getGenre());
		$this->assertEquals(2011-01-24, $myAlbum->getReleaseDate());
		$this->assertEquals("Adele", $myAlbum->getArtist()->getName());
	}
}
?>

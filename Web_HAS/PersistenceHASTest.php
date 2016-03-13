<?php 
require_once 'C:\Users\Alex\Desktop\Documents\321 Project Php code\Group05\Web_HAS\persistence\PersistenceHAS.php';
require_once 'C:\Users\Alex\Desktop\Documents\321 Project Php code\Group05\Web_HAS\model\HAS.php';
require_once 'C:\Users\Alex\Desktop\Documents\321 Project Php code\Group05\Web_HAS\model\Album.php';

//require_once __DIR__.'\persistence\PersistenceHAS.php';
//Cannot get a reletive path to work. 


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
		$album = new Album("21", "Pop", 01-24-2011);
		$hm->addAlbum($album);
	
		//Write all data
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

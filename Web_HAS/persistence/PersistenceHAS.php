<?php

class PersistenceHAS {
	private $filename;
	
	function __construct($filename = 'data.txt') {
		$this->filename= $filename;
	}
	
	function loadDataFromStore() {
		if (file_exists($this->filename)) {
			$str = file_get_contents($this->filename);
			$hm = unserialize($str);} 
			else {
				$hm = HAS::getInstance();
			}
			
			return $hm;
	}
	
	function writeDataToStore($hm) {
		$str = serialize($hm);file_put_contents($this->filename, $str);
	}
}

?>
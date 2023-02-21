<?php 
	$connect = new mysqli("localhost", "f0780687_praktika", "999888777z", "f0780687_praktika");
    if($connect->connect_error){
        die("Ошибка: " . $connect->connect_error);
    }
    
 ?>
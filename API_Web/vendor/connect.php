<?php 
	$connect = new mysqli("localhost", "f0780687_praktika", "а вот облом в этот раз без пароля", "f0780687_praktika");
    if($connect->connect_error){
        die("Ошибка: " . $connect->connect_error);
    }
    
 ?>
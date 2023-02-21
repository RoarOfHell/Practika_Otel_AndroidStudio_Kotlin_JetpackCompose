<?php
    session_start();
    require_once '../vendor/connect.php';

    
    $roomsInfo = mysqli_query($connect, "select * from Rooms");
    while($room = mysqli_fetch_assoc($roomsInfo)){
        $rooms['rooms'][] = $room;
    }
        
    echo json_encode($rooms, JSON_UNESCAPED_UNICODE);
        
?>
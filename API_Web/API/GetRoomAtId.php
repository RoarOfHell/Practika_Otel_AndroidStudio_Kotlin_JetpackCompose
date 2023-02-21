<?php
    session_start();
    require_once '../vendor/connect.php';

    $roomId = $_GET["roomId"];
    
    $roomsInfo = mysqli_query($connect, "select * from Rooms where room_id = $roomId");
    while($room = mysqli_fetch_assoc($roomsInfo)){
        $rooms['rooms'][] = $room;
    }
        
    echo json_encode($rooms, JSON_UNESCAPED_UNICODE);
        
?>
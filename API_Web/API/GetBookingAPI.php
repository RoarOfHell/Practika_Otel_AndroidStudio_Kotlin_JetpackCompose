<?php
    session_start();
    require_once '../vendor/connect.php';

    $userId = $_GET['userId'];
    
    $bookings = mysqli_query($connect, "select * from Bookings where user_id = $userId");
    while($booking = mysqli_fetch_assoc($bookings)){
        $bookingsJson['bookings'][] = $booking;
    }
        
    echo json_encode($bookingsJson, JSON_UNESCAPED_UNICODE);
        
?>
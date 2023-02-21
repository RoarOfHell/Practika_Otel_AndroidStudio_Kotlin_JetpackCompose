<?php
    session_start();
    require_once '../vendor/connect.php';

    $roomId = $_GET['roomId'];
    $fullName = $_GET['fullName'];
    $dateIn = $_GET['dateIn'];
    $dateOut = $_GET['dateOut'];
    $status = $_GET['status'];
    $price = $_GET['price'];
    $userId = $_GET['userId'];
    
    if($roomId != null){
       $result = mysqli_query($connect, "insert into Bookings
    (`booking_id`,`room_id`, `guest_name`, `checkin_date`, `checkout_date`, `status`, `price`, `user_id`) 
    values (null ,$roomId, '$fullName', '$dateIn', '$dateOut', '$status', $price, $userId);");
    }
    
        
        
    echo 'Complite';
    echo $result;
        
?>
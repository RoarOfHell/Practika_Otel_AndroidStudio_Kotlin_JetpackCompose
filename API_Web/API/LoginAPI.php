<?php
    session_start();
    require_once '../vendor/connect.php';

    $login = $_GET["login"];
    $password = $_GET["password"];
    
    $password = md5($password);
    
    $user = mysqli_query($connect, "select * from Users where password = '$password' and username = '$login'");
    $userInfo = mysqli_fetch_assoc($user);
    if($userInfo == null) echo 'error';
    else echo json_encode($userInfo, JSON_UNESCAPED_UNICODE);
    
        
?>
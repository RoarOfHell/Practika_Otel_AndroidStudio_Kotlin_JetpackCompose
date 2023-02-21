<?php
    session_start();
    require_once '../vendor/connect.php';

    $login = $_GET["login"];
    $password = $_GET["password"];
    
    $password = md5($password);
    
    $user = mysqli_query($connect, "select * from Users where username = '$login'");
        $userInfo = mysqli_fetch_assoc($user);
        print_r($userInfo);
        if($userInfo != null) echo 'user error create';
        else {
            $user = mysqli_query($connect, "insert into Users(`username`,`password`) values ('$login', '$password')");
    
            echo 'complited';
        }
    
    
        
?>
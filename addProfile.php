<?php

    require "BaseConnector.php";
    require "Utilities.php";

    $base = connect(function($msg) {
        die (buildError($msg));
    });

    if(!isset($_GET['name'], $_GET['surname'], $_GET['class']))
        die(buildError("Not all required data has been entered"));

    $name = $_GET['name'];
    $surname = $_GET['surname'];
    $classification = $_GET['class'];
    $aliases = isset($_GET['aliases']) ? $_GET['aliases'] : "";
    $picture = isset($_GET['picture']) ? $_GET['picture'] : "";

    $db_query = "INSERT INTO `users`(`name`, `surname`, `aliases`, `classification`, `picture`) VALUES ('$name', '$surname', '$aliases', '$classification', '$picture')";

    $query_result = $base->query($db_query);

    if($query_result) {
        die(buildSuccess());
    }else{
        die(buildError("Operation failed: " . $base->error));
    }


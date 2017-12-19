<?php

    require "BaseConnector.php";
    require "Utilities.php";

    $base = connect(function($msg) {
        die (buildError($msg));
    });

    $data = array();

    if(isset($_GET['name']))
        $data[] = "`name`='" . $_GET['name'] . "'";
    if(isset($_GET['surname']))
        $data[] = "`surname`='" . $_GET['surname'] . "'";
    if(isset($_GET['aliases']))
       $data[] = "`aliases`='" . $_GET['aliases'] . "'";
    if(isset($_GET['classification']))
        $data[] = "`classification`='" . $_GET['classification'] . "'";
    if(isset($_GET['picture']))
        $data[] = "`picture`='" . $_GET['picture'] . "'";

    if(count($data) == 0)
        die(buildError("No input data"));


    $db_query = "UPDATE `users` SET ";

    for($i = 0; $i < count($data); $i++) {
        $db_query .= $data[$i] . ",";
    }

    $db_query = substr($db_query, 0, -1);

    

    $query_result = $base->query($db_query);

    if($query_result) {
        die(buildSuccess());
    }else{
        die(buildError("Operation failed: " . $base->error));
    }


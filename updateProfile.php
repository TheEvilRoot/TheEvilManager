<?php

    require "BaseConnector.php";
    require "Utilities.php";

    $base = connect(function($msg) {
        die (buildError($msg));
    });

    $uid = $_GET['uid'];
    $field = $_GET['field'];
    $data = $_GET['data'];

    if(!isset($_GET['uid'], $_GET['field'], $_GET['data'])) {
        die(buildError("Query data isn't set"));
    }
    $db_query = "UPDATE `users` SET `$field`='$data' WHERE `uid` = '$uid'";

    $query_result = $base->query($db_query);

    if($query_result) {
        die(buildSuccess());
    }else{
        die(buildError("Operation failed: " . $base->error));
    }


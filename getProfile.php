<?php

	require "BaseConnector.php";
	require "Utilities.php";

	$base = connect(function($msg) {
	    die (buildError($msg));
    });

	$query_data = $_GET['data'];
	if(!isset($_GET['data']))
	    die(buildError("Query data isn't set"));

	$db_query = "SELECT * FROM `users` WHERE `uid` = '$query_data' OR `name` = '$query_data' OR `surname` = '$query_data' OR `classification` = '$query_data'";

	$query_result = $base->query($db_query);

    $rows = resultToArray($query_result);
    if($rows == false) {
        if($base->errno) {
            die(buildError($base->error));
        }else{
            die(buildError("Not found"));
        }
    }

    $query_result->free();

    die(buildResponse($rows));

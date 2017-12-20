<?php
	require "BaseConnector.php";
	require "Utilities.php";

	$base = connect(function($msg) {
	    die (buildError($msg));
    });

	$query_data = $_GET['data'];
	$query_type = $_GET['type'];
	if(!isset($_GET['data']) || !isset($_GET['type'])) {
        die(buildError("Query data or type isn't set"));
    }

	if(!isValidType($query_type)) {
		die(buildError("Invalid data type " . $query_type));
	}

	$db_query = "SELECT * FROM `users` WHERE `$query_type` = '$query_data'";

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
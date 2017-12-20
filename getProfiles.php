<?php

	require "BaseConnector.php";
	require "Utilities.php";

	$base = connect(function($msg) {
	    die (buildError($msg));
    });

	$db_query = "SELECT * FROM `users`";

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

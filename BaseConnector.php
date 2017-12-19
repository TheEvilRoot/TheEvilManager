<?php
require "Config.php";

function connect($error_func) {
	global $baseHost, $baseLogin, $basePass, $baseName;
	$base = new mysqli($baseHost, $baseLogin, $basePass, $baseName);
	$base->set_charset("utf8");
	if ($base->connect_errno) {
	    $error_func("Unable to connect to database" . $base->error);
	}
	return $base;
}

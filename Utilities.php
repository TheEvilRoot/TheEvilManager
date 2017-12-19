<?php
    function buildError($reason) {
        return json_encode(array('response' => array('error' => $reason)));
    }

    function buildResponse($info) {
        return json_encode(array('response' => array('users' => $info)));
    }

    function buildSuccess() {
        return json_encode(array('response' => 'Operation successful'));
    }

    function startsWith($haystack, $needle) {
        return $needle === "" || strrpos($haystack, $needle, -strlen($haystack)) !== false;
    }

    function resultToArray($result) {
        $rows = array();
        if($result) {
            while ($row = $result->fetch_assoc()) {
                $rows[] = $row;
            }
        }else{
            return false;
        }
        return $rows;
    }
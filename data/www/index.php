<?php
header('Content-Type: application/json');

ini_set('session.save_handler', 'redis');
ini_set('session.save_path', 'tcp://redis:6379');

session_name('SIMPLEAPP');
session_start();

if (!array_key_exists('count', $_SESSION)) {
    $_SESSION['count'] = 0;
}

$_SESSION['count']++;

function get_client_ip() {
    if (isset($_SERVER['HTTP_CLIENT_IP'])) {
        $ipaddress = $_SERVER['HTTP_CLIENT_IP'];
    } elseif (isset($_SERVER['HTTP_X_FORWARDED_FOR'])) {
        $ipaddress = $_SERVER['HTTP_X_FORWARDED_FOR'];
    } elseif (isset($_SERVER['HTTP_X_FORWARDED'])) {
        $ipaddress = $_SERVER['HTTP_X_FORWARDED'];
    } elseif (isset($_SERVER['HTTP_FORWARDED_FOR'])) {
        $ipaddress = $_SERVER['HTTP_FORWARDED_FOR'];
    } elseif (isset($_SERVER['HTTP_FORWARDED'])) {
        $ipaddress = $_SERVER['HTTP_FORWARDED'];
    } elseif (isset($_SERVER['REMOTE_ADDR'])) {
        $ipaddress = $_SERVER['REMOTE_ADDR'];
    } else {
        $ipaddress = 'UNKNOWN';
    }
    return $ipaddress;
}

$data = [ 'date' => date('d.m.Y'), 'ip' => get_client_ip(), 'count' => $_SESSION['count'] ];
echo json_encode($data);

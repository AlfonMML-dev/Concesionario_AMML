<?php

    ini_set('display_errors',1);
    ini_set('display_startups_errors', 1);
    error_reporting(E_ALL);

    /**
     * Database Connection Settings
     */ 
    define("DB_HOST", '146.59.237.189');
    define('DB_NAME', 'dam208_ammlconcesionario');
    define('DB_USER', 'dam208_amml');
    define('DB_PASS', 'dam208_amml');
        
    require_once('DBConnection.php');
    
    $dbc = new dbConnection();
    $con = $dbc->getCon();
?>


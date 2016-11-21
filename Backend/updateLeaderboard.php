<?php
  require_once("connectionManager.php");

  $sql = new connectionManager();
  $unique_id = $_GET["id"];
  $status = $_GET["status"];
  $sql_query = "default";

  if($status == "w") {
    $sql_query = "UPDATE users SET wins = wins +1 WHERE unique_id = '$unique_id'";
  }
  if($status == "l") {
    $sql_query = "UPDATE users SET losses = losses +1 WHERE unique_id = '$unique_id'";
  }
  if($status == "d") {
    $sql_query = "UPDATE users SET draws = draws +1 WHERE unique_id = '$unique_id'";
  }


  $result = $sql->get($sql_query);

  $sql->disconnect;
/*
  insert into users (user_name,unique_id,wins,losses,draws) values ('test1','1',1,2,0);
  insert into users (user_name,unique_id,wins,losses,draws) values ('test2','100',1000,2,0);
  insert into users (user_name,unique_id,wins,losses,draws) values ('test1','30',104,2,5);
*/
?>

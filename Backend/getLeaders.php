<?php
  require_once("connectionManager.php");

  $sql = new connectionManager();
  $sql_query = "select user_name,wins,losses,draws from users order by wins desc";
  $result = $sql->get($sql_query);

  $arr = Array();

  foreach($result as $row) {
    array_push($arr,$row);
  }

  $sql->disconnect;

  echo json_encode($arr);
  return json_encode($arr);


?>

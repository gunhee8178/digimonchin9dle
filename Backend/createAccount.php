<?php
  require_once("connectionManager.php");

  $sql = new connectionManager();
  $unique_id = $_GET["id"];
  $user_name = $_GET["user_name"];

  $sql_query = "select * from users where unique_id = $unique_id";

  $result = $sql->get($sql_query);

  if($result->num_rows == 0) {
    $sql_query = "insert into users (user_name,unique_id,wins,losses,draws)
                  values ('$user_name', '$unique_id',0,0,0)";
    $sql->insert($sql_query);
  }


/*
  insert into users (user_name,unique_id,wins,losses,draws) values ('test1','1',1,2,0);
  insert into users (user_name,unique_id,wins,losses,draws) values ('test2','100',1000,2,0);
  insert into users (user_name,unique_id,wins,losses,draws) values ('test1','30',104,2,5);
*/
?>

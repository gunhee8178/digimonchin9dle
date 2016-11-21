STEPS ON HOW TO CREATE MYSQL TABLE:

1. SQL QUERY CREATION:

      create table users (
      id int primary key not null auto_increment,
      user_name varChar(255),
      unique_id varChar(255),
      wins int(11),
      losses int(11),
      draws int(11)
      );

2. TEST DATA: 
    insert into users (user_name,unique_id,wins,losses,draws) values ('user1','1',1,2,0);
    insert into users (user_name,unique_id,wins,losses,draws) values ('user2','100',1000,2,0);
    insert into users (user_name,unique_id,wins,losses,draws) values ('user3','30',104,2,5);

3. CONNECTIONMANAGER:

  In ConnectionManager.php update the following values with desired database values
        public $host = "localhost";
        public $user = "umdandro";
        public $password = "cmscpass1";
        public $database = "umdandro_app";

***NOTE***
In the android source code it uses the web server we have set up so changes to ConnectionManager.php will not affect the app

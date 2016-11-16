<?php
    /*
      Note: ConnectionManager.php was created by Ryan Bern for CMSC389P
    */
    class ConnectionManager {
        public $host = "localhost";
        public $user = "umdandro";
        public $password = "cmscpass1";
        public $database = "umdandro_app";
        public $db_connection;

        public function __construct() {
            $this->db_connection = new mysqli($this->host, $this->user, $this->password, $this->database);
        }


        function disconnect() {
            $this->db_connection->close();
        }

        function insert($sql) {
            $result = $this->db_connection->query($sql);
            if (!$result) {
                return false;
            }
            else {
                return true;
            }
        }

        function get($sql) {
            $result = $this->db_connection->query($sql);
            if (!$result) {
                return false;
            }
            else {
                return $result;
            }
        }

    }

?>

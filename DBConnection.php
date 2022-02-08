<?php 

class DBConnection {
    private $host = DB_HOST;
    private $user = DB_USER;
    private $pass = DB_PASS;
    private $dbname = DB_NAME;
    
    private $dbc;
    
    private $error = '';
    
    /**
     * Método el cual abre una nueva conexión a la BBDD. Es el constructor de la
     * clase.
     */ 
    public function __construct(){
        
        $dsn = 'mysql:host='.$this->host.';dbname='.$this->dbname;
        
        $options = array(
            PDO::ATTR_PERSISTENT => true,
            PDO::ATTR_ERRMODE =>PDO::ERRMODE_EXCEPTION
            );
        
        try{
            $this->dbc = new PDO($dsn, $this->user, $this->pass, $options);
        }
        
        catch (PDOException $e){
            $this->error = $e->getMessage();
        }
        
        return $this->error;
    }
    
    /**
     * Método que cierra la conexión con la BBDD.
     */ 
    public function __destruct(){
        $this->dbc = NULL;
    }
    
    /**
     * Método que obtiene una conexión con la BBDD mediante una instancia de la 
     * clase PDO.
     */ 
    private function getPDOConnection(){
        //Comprobamos si la conexión ha sido establecida previamente
        if($this->dbc == NULL){
            $dsn ="".
                $this->_config['driver'].
                ":host=".$this->_config['host'].
                ";dbname=".$this->_config['dbname'];
        //Hacemos la conexión persistente y activamos el lanzamiento de excepciones        
        $options = array(
                PDO::ATTR_PERSISTENT => true,
                PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
                );
        
        try{
            $this->dbc = new PDO($dsn, $this->_config['username'], $this->_config['password'], $options);
        }catch ( PDOException $e){
            echo __LINE__.$e->getMessage();
        }
            
        }
    }
    
    /**
     * Método encargado de ejecutar la instrucción sql que recibe por parámetro.
     * Esta instrucción será del tipo INSERT, UPDATE o DELETE.
     * 
     * @param $sql String que contiene la instrucción sql que será ejecutada por
     * el método exec().
     * @return $filas Hace referencia al número de filas afectadas por la 
     * consulta.
     */ 
    public function runQuery($sql){
        try{
            $filas = $this->dbc->exec($sql);
            return $filas;
        }catch(PDOException $e){
            echo __LINE__.$e->getMessage();
        }
    }
    
    /**
     * Método que ejecuta la instrucción sql de tipo SELECT que recibe por 
     * parámetro, devolviendo un resultset.
     * 
     * @param $sql String que contiene la instrucción sql que será ejecutada por
     * el método query().
     * @return $filas Hace referencia al array asociativo con las tuplas y 
     * campos devueltos en la consulta. La forma en la que se recogen los datos 
     * es un array asociativo debido al método setFetchMode perteneciente a la 
     * clase PDO.
     */ 
    public function getQuery($sql){
        try{
            $filas = $this->dbc->query($sql);
            $filas->setFetchMode(PDO::FETCH_ASSOC);
            return $filas;
        }catch (PDOException $e){
            echo __LINE__.$e->getMessage();
        }
    }
    
    public function getCon(){
        return $this->dbc;
    }
    
    public function __toString(){
        return $this->error;
    }
    
}

?>
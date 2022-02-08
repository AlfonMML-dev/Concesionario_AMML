<?php
ini_set('display_errors', 1);
ini_set('display_startups_errors', 1);
error_reporting(E_ALL);

require_once('settingsDB.php');   

//Creación de las variables necesarias para la inserción en la base de datos
$ref = "";
$title = "";
$description = "";
$categories = "";
$images = "";
$price = 0;
$localization = "";
$url = "";

//Creación de los arrays necesarios para comprobar las categorías que tiene el vehículo
$colores = array("negro", "blanco", "gris", "azul", "rojo", "plata", "verde", "gris / plata", "rojo (pam)");
$cambios = array("manual", "automático");
$combustibles = array("diesel", "eléctrico", "híbrido", "glp", "otro", "gasolina");

//Creación de las funciones que harán más comprensible y escalable el código

/**
 * Función que comprueba que categorías ha insertado el vendedor, almacenándolas en un string.
 * Si no se encuentra la categoría el valor de la misma es un string vacío.
 * @param $arrayCategories array que recoge únicamente los valores de las categorías que existen
 * @param $colores array que almacena los posibles colores que puede tener un coche en milanuncios
 * @param $cambios array que almacena los posibles cambios de marchas que puede tener un coche en milanuncios
 * @param $combustibles array que almacena los posibles tipos de compostaje que puede tener un coche en milanuncios
 * @return string que contiene todas las categorías con sus valores.
 */
function checkFields($arrayCategories, $colores, $cambios, $combustibles)
{
    //String que devolverá el método
    $result = "";
    //Array que almacena que campos existen
    $camposExisten = array(
        0 => ['name' => "color", 'value' => "NO ESPECIFICADO"],
        1 => ['name' => "doors", 'value' => "NO ESPECIFICADO"],
        2 => ['name' => "tag", 'value' => "NO ESPECIFICADO"],
        3 => ['name' => "fuel", 'value' => "NO ESPECIFICADO"],
        4 => ['name' => "horsepower", 'value' => "NO ESPECIFICADO"],
        5 => ['name' => "km", 'value' => "NO ESPECIFICADO"],
        6 => ['name' => "gearlever", 'value' => "NO ESPECIFICADO"],
        7 => ['name' => "warranty", 'value' => "NO ESPECIFICADO"],
        8 => ['name' => "year", 'value' => "NO ESPECIFICADO"]
    );

    //Recorro todas las categorías, comprobando si existen o no
    foreach ($arrayCategories as $elementCategory) {
        //Las paso a minuscula para que no haya problema al comparar strigs
        $etiqueta = strtolower($elementCategory);

        //Comprobamos que este la etiqueta en el array
        if (in_array($etiqueta, $colores)) {
            $color = $elementCategory;
            $camposExisten[0]['value'] = $color;
            echo "<br />" . "Color : " . $color;
            echo "<br />" . "Color : " . $camposExisten[0]['value'];
        }
        //Comprobamos la posicion del string en la etiqueta si existe
        if (stripos($etiqueta, "puerta")) {
            $npuertas = preg_replace("/[^0-9]/", "", $elementCategory);
            $camposExisten[1]['value'] = $npuertas;
            echo "<br />" . "Puertas : " . $npuertas;
            echo "<br />" . "Puertas : " . $camposExisten[1]['value'];
        }
        //No utilizo $etiqueta porque busco una cadena que empieza por mayúscula
        if (stripos($elementCategory, "Etiqueta")) {
            $etiquetaMedioambiental = $elementCategory;
            $camposExisten[2]['value'] = $etiquetaMedioambiental;
            echo "<br />" . "Etiqueta : " . $etiquetaMedioambiental;
        }
        //Comprobamos que este la etiqueta en el array
        if (in_array($etiqueta, $combustibles)) {
            $combustible = $elementCategory;
            $camposExisten[3]['value'] = $combustible;
            echo "<br />" . "Combustible : " . $combustible;
        }
        //Comprobamos la posicion del string en la etiqueta si existe
        if (stripos($etiqueta, "cv")) {
            $cv = preg_replace("/[^0-9]/", "", $elementCategory);
            $camposExisten[4]['value'] = $cv;
            echo "<br />" . "CV : " . $cv;
        }
        //Comprobamos la posicion del string en la etiqueta si existe
        if (stripos($etiqueta, "km")) {
            $kilometros = preg_replace("/[^0-9]/", "", $elementCategory);
            $camposExisten[5]['value'] = $kilometros;
            echo "<br />" . "Kilómetros : " . $kilometros;
        }
        //Comprobamos que este la etiqueta en el array
        if (in_array($etiqueta, $cambios)) {
            $cambio = $elementCategory;
            $camposExisten[6]['value'] = $cambio;
            echo "<br />" . "Cambio : " . $cambio;
        }
        //No utilizo $etiqueta porque busco una cadena que empieza por mayúscula
        //No es capaz con "Garantía"
        if (stripos($elementCategory, "meses")) {
            $array_garantia = explode(" ", $elementCategory);
            $garantia = $array_garantia[1] . " " . $array_garantia[2];
            $camposExisten[7]['value'] = $garantia;
            echo "<br />" . "Garantía : " . $elementCategory;
            echo "<br />" . "Garantía : " . $garantia;
        }
        //Si la longitud es de 9 es el año
        if (strlen($etiqueta) == 9) {
            $test = preg_replace("/[^0-9]/", "", $etiqueta);
            //Si despues de limpiarlo es de 4 nos aseguramos que es un año
            if (strlen($test) == 4) {
                $year = $test;
                $camposExisten[8]['value'] = $year;
                echo "<br />" . "Año : " . $year . "<br />";
            }
        }
    }
    foreach ($camposExisten as $camposExiste) {
        if ($camposExiste['name'] == "year") {
            $result .= implode(",", $camposExiste);
        } else {
            $result .= implode(",", $camposExiste) . ";";
        }
    }
    echo $result;
    return $result;
}

/**
 * Función que obtiene las distintas categorías que clasifican al vehículo,
 * como el combustible, los km, etc.
 * @param $tags Hace referencia al string que almacena las etiquetas que contienen los valores de las categorías.
 * @return Array que guarda los valores de las categorías.
 */
function getCategories($tags)
{
    //Se crea un array donde se meten todas las etiquetas
    $result = array();
    $cant = mb_substr_count($tags, "title");
    for ($i = 0; $i < $cant; $i++) {
        $tags2 = getStringBetween($tags, 'title="', '">');
        array_push($result, $tags2);
        $tags = str_replace(getStringBetween($tags, '<span class="ma-AdTag-label" title', '>' . $tags2 . "</span></span>"), "", $tags);
    }
    //Muestro las etiquetas por la prueba
    echo "<br /> <br />";
    var_dump($result);
    echo "<br />";
    return $result;
}

/**
 * Función que obtiene el código HTML de una página.
 * @param $url Hace referencia a la dirección de la página web de la cual,
 * descargaremos el código HTML.
 * @return String que almacena el código HTML.
 */
function getHtml($url)
{
    //Saco el html
    $archivo = file_get_contents($url);
    //Devolvemos el archivo
    return $archivo;
}

/**
 * Función que obtiene las imágenes del código HTML de una página web.
 * @param $archivo String que almacena el código HTML.
 * @param $start_data String que determina el lugar donde se comienzan
 * las urls de las fotos.
 * @param $end_data String que determina el lugar donde se terminan
 * las urls de las fotos.
 * @return String que contiene todas las urls de las imágenes presentes en
 * la página web.
 */
function getImages($archivo, $start_data, $end_data)
{
    //Si tiene jpg
    if (strpos($archivo, ".jpg")) {
        //Creo un array de imagenes de los coches
        $imagenesCoche = array();

        $cantFotos = str_replace("-", ", ", getStringBetween($archivo, $start_data, $end_data));

        //Saco la primera foto
        $start_data = '"ad_view\":\"1\"}],\"urlp\":\"';
        $end_data = '1.jpg?VersionId=';
        $fotoURL = str_replace("-", ", ", getStringBetween($archivo, $start_data, $end_data));

        //Recorro la primera foto con todas las que hay y la formamos
        for ($i = 1; $i <= $cantFotos; $i++) {
            array_push($imagenesCoche, $fotoURL . $i . ".jpg");
        }
        //El array con todas las fotos las separamos con ; para formar el string de imagenes
        $imagenesString = implode(";", $imagenesCoche);
        echo "<br />" . $imagenesString;
    }
    return $imagenesString;
}

//Funcion para sacar entre 2 strings
/**
 * Función que obtiene el string contenido entre 2 strings.
 * En realidad solo hay un string, pero lo que se hace es obtener una 
 * subcadena, determinando un inicio y un final de la subcadena contenida en el
 * string.
 * @param $string Hace referencia a la posición donde comienza la subcadena.
 * @param $start Hace referencia a la posición donde termina la subcadena.
 * @return String que representa a la subcadena obtenida.
 */
function getStringBetween($string, $start, $end)
{
    //Hacemos esto por si esta vacio o le pasamos algo que no es string
    $string = ' ' . $string;
    //Posicion inicial
    $ini = mb_strpos($string, $start);
    //Le sumamos al inicio su longitud para que no se incluya
    $ini += mb_strlen($start);
    //Le sacamos la posicion final quitandole la longitud del inicio
    $len = mb_strpos($string, $end, $ini) - $ini;
    //Le devolvemos lo que queremos
    return trim(mb_substr($string, $ini, $len));
}

/**
 * Función que sustituye aquellos caracteres no recogidos en ASCII,
 * por caracteres sí reconocidos, quitando tildes, diéresis o
 * acentos circunflejos.
 * @param $cadena Hace referencia al string del cual se va reemplazar los
 * caracteres que cumplan con las características descritas anteriormente.
 * @return La cadena ya modificada
 */
function remplazar_caracteres_noASCII($cadena)
{

    //Ahora reemplazamos las letras
    $cadena = str_replace(
        array('á', 'à', 'ä', 'â', 'ª', 'Á', 'À', 'Â', 'Ä'),
        array('a', 'a', 'a', 'a', 'a', 'A', 'A', 'A', 'A'),
        $cadena
    );

    $cadena = str_replace(
        array('é', 'è', 'ë', 'ê', 'É', 'È', 'Ê', 'Ë'),
        array('e', 'e', 'e', 'e', 'E', 'E', 'E', 'E'),
        $cadena
    );

    $cadena = str_replace(
        array('í', 'ì', 'ï', 'î', 'Í', 'Ì', 'Ï', 'Î'),
        array('i', 'i', 'i', 'i', 'I', 'I', 'I', 'I'),
        $cadena
    );

    $cadena = str_replace(
        array('ó', 'ò', 'ö', 'ô', 'Ó', 'Ò', 'Ö', 'Ô'),
        array('o', 'o', 'o', 'o', 'O', 'O', 'O', 'O'),
        $cadena
    );

    $cadena = str_replace(
        array('ú', 'ù', 'ü', 'û', 'Ú', 'Ù', 'Û', 'Ü'),
        array('u', 'u', 'u', 'u', 'U', 'U', 'U', 'U'),
        $cadena
    );

    $cadena = str_replace(
        array('ñ', 'Ñ', 'ç', 'Ç'),
        array('nn', 'NN', 'c', 'C'),
        $cadena
    );

    return $cadena;
}

function insertCar($url, $dbc)
{

    $archivo = getHtml($url);

    $colores = array("negro", "blanco", "gris", "azul", "rojo", "plata", "verde", "gris / plata", "rojo (pam)");
    $cambios = array("manual", "automático");
    $combustibles = array("diesel", "eléctrico", "híbrido", "glp", "otro", "gasolina");


    //Sacamos la ref que esta entre estos campos
    $start_data = 'reference ma-AdDetail-metadataTag">Ref: ';
    $end_data = "</span>";
    $ref = getStringBetween($archivo, $start_data, $end_data);
    echo "<br />REF: " . $ref . "       " . "</ br>";

    //Sacamos el titulo que esta entre estos campos
    $start_data = '>Milanuncios - ';
    $end_data = "</title>";
    $title = getStringBetween($archivo, $start_data, $end_data);
    echo "<br />Titulo: " . $title . "       ";

    //Sacamos todas la descripcion que este entre estos campos
    $start_data = ' name="description" content="' . $title;
    $end_data = '" data-reactroot=""/><link data-rh="" rel="canonical" hre';
    $description = getStringBetween($archivo, $start_data, $end_data);
    echo "<br />Descripcion: " . $description . "        ";

    //Sacamos todas las etiquetas que este entre estos campos
    $start_data = '<ul class="ma-AdTagList">';
    $end_data = '</ul>';
    $tags = getStringBetween($archivo, $start_data, $end_data);
    $categories = checkFields(getCategories($tags), $colores, $cambios, $combustibles);

    //Saco la cantidad de fotos que tiene el anuncio
    $start_data = '5s-.7-1.5-1.5-1.5-1.5.7-1.5 1.5.7 1.5 1.5 1.5z"/></svg></span></span>1 / ';
    $end_data = '</p></div></div><h1 class="ma-AdDetail-title ma-AdDetail-title-size-heading-m">';
    $images = getImages($archivo, $start_data, $end_data);

    //Sacamos el precio que este entre estos campos
    $start_data = 'ma-AdPrice-value--heading--l">';
    $end_data = '</span>';
    $price = getStringBetween($archivo, $start_data, $end_data);
    $price = str_replace("€", "", $price);
    $price = str_replace(".", "", $price);
    $aux_price = (int) $price;
    $price = $aux_price;
    echo "<br />Precio: " . $price . "        ";

    //Sacamos toda la localizacion que este entre estos campos
    $start_data = '-de-segunda-mano-en-';
    $end_data = '/" class="';
    $localization = str_replace("-", ", ", getStringBetween($archivo, $start_data, $end_data));
    echo "<br />" . "Lugar: " . $localization . "        ";

    /**
     * Quitamos todos los acentos y caracteres no recogidos en ASCII,
     * pues la base de datos no es capaz reconocerlos, a pesar de estar
     * codificada en UTF-8.
     */
    $title = remplazar_caracteres_noASCII($title);
    $description = remplazar_caracteres_noASCII($description);
    $categories = remplazar_caracteres_noASCII($categories);
    $localization = remplazar_caracteres_noASCII($localization);

    echo "<br /><br /><br /><strong>Insertamos el coche</strong>";
    //Lo insertamos en la base de datos
    $sql = "INSERT INTO coches VALUES ('" . $ref . "', '" . $title . "', '" . $description . "', '" . $categories . "', '"
        . $images . "', '" . $price . "', '" . $localization . "', '" . $url . "')";

    $dbc->runQuery($sql);    
}
$urls = array(
    "https://www.milanuncios.com/otros-coches-de-segunda-mano/corvette-corvette-349761850.htm",
    "https://www.milanuncios.com/ford-de-segunda-mano/ford-smax-2-0-tdci-panther-110kw-titanium-432969032.htm",
    "https://www.milanuncios.com/ford-de-segunda-mano/ford-focus-1-0-ecoboost-mhev-92kw-active-433405108.htm",
    "https://www.milanuncios.com/seat-de-segunda-mano/seat-leon-2-0-tdi-170cv-fr-433516147.htm",
    "https://www.milanuncios.com/ford-de-segunda-mano/ford-kuga-stline-x-2-5-duratec-phev-165kw-auto-433542820.htm",
    "https://www.milanuncios.com/seat-de-segunda-mano/seat-leon-2-0-tdi-170cv-fr-433576809.htm",
    "https://www.milanuncios.com/mercedes-benz-de-segunda-mano/mercedes-benz-gls-434678730.htm",
    "https://www.milanuncios.com/bmw-de-segunda-mano/bmw-x3-xdrive20d-434998035.htm",
    "https://www.milanuncios.com/otros-coches-de-segunda-mano/corvette-corvette-435584837.htm",
    "https://www.milanuncios.com/ford-de-segunda-mano/ford-focus-1-5-ecoboost-110kw-stline-436954409.htm",
    "https://www.milanuncios.com/audi-de-segunda-mano/audi-q5-2-0-tdi-clean-140kw-quatt-s-tro-advanced-437178912.htm",
    "https://www.milanuncios.com/alfa-romeo-de-segunda-mano/alfa-romeo-giulia-2-2-diesel-154kw-210cv-veloce-q4-437213908.htm",
    "https://www.milanuncios.com/alfa-romeo-de-segunda-mano/alfa-romeo-stelvio-437228691.htm",
    "https://www.milanuncios.com/ford-de-segunda-mano/ford-focus-1-6-trend-437324941.htm",
    "https://www.milanuncios.com/bmw-de-segunda-mano/bmw-serie-3-320d-gran-turismo-437401077.htm",
    "https://www.milanuncios.com/audi-de-segunda-mano/audi-a5-2-0-tdi-140kw-190cv-s-tronic-sportback-437538392.htm",
    "https://www.milanuncios.com/volvo-de-segunda-mano/volvo-s60-437748629.htm",
    "https://www.milanuncios.com/audi-de-segunda-mano/audi-a5-2-0-tdi-110kw-s-tronic-sportback-438188676.htm",
    "https://www.milanuncios.com/bmw-de-segunda-mano/bmw-serie-5-520da-438439855.htm",
    "https://www.milanuncios.com/bmw-de-segunda-mano/bmw-x5-xdrive30d-438775724.htm",
    "https://www.milanuncios.com/toyota-de-segunda-mano/toyota-celica-438782454.htm",
    "https://www.milanuncios.com/ford-de-segunda-mano/ford-mondeo-2-0-tdci-140-titanium-438871746.htm",
    "https://www.milanuncios.com/audi-de-segunda-mano/audi-a1-adrenalin-1-0-tfsi-70kw-95cv-sportback-439101521.htm",
    "https://www.milanuncios.com/audi-de-segunda-mano/audi-a1-30-tfsi-85kw-116cv-sportback-439101562.htm",
    "https://www.milanuncios.com/abarth-de-segunda-mano/abarth-500-595-turismo-1-4-16v-tjet-121kw-e6-439102585.htm",
    "https://www.milanuncios.com/bmw-de-segunda-mano/bmw-serie-4-420i-gran-coupe-439446977.htm",
    "https://www.milanuncios.com/otros-coches-de-segunda-mano/corvette-corvette-6-2-v8-convertible-auto-439529389.htm",
    "https://www.milanuncios.com/alfa-romeo-de-segunda-mano/alfa-romeo-stelvio-439562192.htm",
    "https://www.milanuncios.com/alfa-romeo-de-segunda-mano/alfa-romeo-stelvio-439562265.htm",
    "https://www.milanuncios.com/abarth-de-segunda-mano/abarth-500-1-4-tjet-695-biposto-140kw-190cv-439587647.htm",
    "https://www.milanuncios.com/abarth-de-segunda-mano/abarth-500-1-4-16v-tjet-135cv-439602569.htm",
    "https://www.milanuncios.com/abarth-de-segunda-mano/abarth-500-595-turismo-1-4-16v-tjet-160cv-e6-439604512.htm"
);

foreach($urls as $url){
    insertCar($url, $dbc);
}

?>
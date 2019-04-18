package com.example.ausias.intercibus;

/**
 * Aquesta classe serveix per emmagatzemar les constants que fem servir
 * @author Antonio de Dios Durán
 * @version 1.0
 */

public class Constants {

    /* Constants connexió */
//    public static final String driver = "com.mysql.jdbc.Driver";
    //public static final String IP = "192.168.204.213";
    //public static final String IP = "intercibusm13.webcindario.com";
    //public static final String IP = "intercibus.000webhostapp.com";
    public static final String IP = "labs.iam.cat/~a16joryhecol";
    public static final String PORT = "80";
//    public static final String NOMBD = "intercibus";
//    public static final String user = "mysql";
//    public static final String pass = "ausias";
//    public static final String url = "jdbc:mysql://"+IP+":3306/"+NOMBD;
    public static final String url = "http://" + IP/* + ":" + PORT*/;

    /* Constants taules */
    public static final String NOM_TAULA_USUARIS = "usuari";

    /* Carpeta php */
    public static final String carpeta = "/php_intercibus";

    /* Direccions php */
    public static final String LOGIN_URL = url + carpeta + "/login.php";
    public static final String REGISTER_URL = url + carpeta + "/registre.php";
    public static final String REGISTRA_ESPAI_URL = url + carpeta + "/crea_espai.php";
    public static final String MOSTRAR_ESPAI_URL = url + carpeta + "/veure_espai.php";
    public static final String LISTA_URL = url + carpeta + "/consult_input.php";
    public static final String RESULTAT_URL = url + carpeta + "/consulta_proser.php";
    public static final String CONSULTA_DIES_RESERVA_URL = url + carpeta + "/consult_reserv.php";
    public static final String FER_RESERVA_URL = url + carpeta + "/fer_reserva.php";
    public static final String CONSULTA_RESERVES_PROPIES = url + carpeta + "/veure_reserves_madebyme.php";
    public static final String CONSULTA_RESERVES_SOLICITADES = url + carpeta + "/veure_reserves_meus_espais.php";
    public static final String VEURE_INFO_CLIENT = url + carpeta + "/veure_info_client.php";
    public static final String VEURE_INFO_PROPIETARI = url + carpeta + "/veure_info_propietari.php";
    public static final String VEURE_RESERVA = url + carpeta + "/veure_reserva.php";


}

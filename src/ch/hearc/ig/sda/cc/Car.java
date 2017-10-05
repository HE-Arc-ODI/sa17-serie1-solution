/*
 * HEG Haute école de gestion Arc
 * 633-1.1 Structures de données avancées 
 * 2014-2015 
 */
package ch.hearc.ig.sda.cc;

/**
 * Représente les comportements attendus pour une voiture
 *
 * @author Philippe Daucourt <philippe.daucourt@he-arc.ch>
 */
public interface Car {

    /**
     * Numéro du siège du conducteur de la voiture
     */
    int DRIVER_SEAT = 0;
    /**
     * Numéro du siège du premier passager de la voiture
     */
    int FIRST_PASSENGER_SEAT = 1;

    //Méthodes "query"
    
    /**
     * Retourne le nombre de sièges disponibles dans la voiture
     * 
     * @return nombre de sièges
     */
    public int seats();

    /**
     * Indique si la voiture est vide (pas de conducteur ni de passager)

     * @return true => la voiture est vide
     */
    boolean isEmpty();

    /**
     * Indique s'il n'y a plus de place disponible dans la voiture
     * 
     * @return true => la voiture est pleine
     */
    boolean isFull();

    /**
     * Indique si la voiture a un conducteur
     * 
     * @return true => la voiture a un conducteur
     */
    boolean hasDriver();

    /**
     * Retourne la personne qui conduit la voiture
     * 
     * @return le conducteur
     */
    Person driver();

    /**
     * Indique si la voiture est à l'arrêt
     * 
     * @return true => la voiture est arrêtée
     */
    boolean isStopped();

    /**
     * Indique si la voiture est en mouvement
     * 
     * @return true => la voiture roule
     */
    boolean isDriving();

    /**
     * Retourne le siège d'une personne
     * 
     * @param person la personne dont le siège est recherché
     * @return le siège de cette personne
     */
    int seat(Person person);

    /**
     * Retourne le nombre de passagers dans la voiture
     * @return le nombre de passagers
     */
    int passengers();

    /**
     * Retourne le nombre de personnes dans la voiture (conducteur + passagers)
     * 
     * @return le nombre de personnes
     */
    int people();

    //Méthodes "command"
    
    /**
     * Fait rouler la voiture
     */
    void drive();

    /**
     * Fait entrer une personne en tant que passager dans la voiture
     *
     * @param person le passager
     */
    void getIn(final Person person);

    /**
     * Fait entrer une personne dans la voiture
     *
     * @param person la personne qui entre
     * @param isDriver vrai => un chauffeur, faux => un passager
     */
    void getIn(final Person person, final boolean isDriver);
}

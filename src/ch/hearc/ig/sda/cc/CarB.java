/*
 * HEG Haute école de gestion Arc
 * 633-1.1 Structures de données avancées 
 * 2014-2015 
 */
package ch.hearc.ig.sda.cc;

import static ch.hearc.ig.sda.cc.State.DRIVING;
import static ch.hearc.ig.sda.cc.State.STOPPED;

/**
 *
 * @author Philippe Daucourt <philippe.daucourt@he-arc.ch>
 */
public class CarB implements Car {

    private final int seats; //Nombre de siège dans l'auto

    private final Person people[]; //Personnes dans l'auto
    private boolean driver = false; //Y a-t-il un pilote dans l'auto?
    private int passengers; //Nombre de passagers dans l'auto

    private State state = STOPPED; //Etat dans lequel se trouve la voiture

    public CarB(final int seats) {
        checkSeats(seats);

        this.seats = seats;
        people = new Person[seats];
    }

    @Override
    public void drive() {
        checkIfDriving();

        checkIfNoDriver();

        state = State.DRIVING;
    }

    @Override
    public void getIn(final Person person) {
        getIn(person, false);
    }

    @Override
    public void getIn(final Person person, final boolean isDriver) {
        checkIfDriving();

        checkIfFull();

        checkIfSeatAvailable(isDriver);

        checkIfPersonIsNull(person);

        if (isDriver) {
            driver = true;
            people[DRIVER_SEAT] = person;
        } else {
            people[FIRST_PASSENGER_SEAT + passengers()] = person;
            passengers++;
        }
    }

    @Override
    public int seats() {
        return seats;
    }

    @Override
    public boolean isEmpty() {
        return people() == 0;
    }

    @Override
    public boolean isFull() {
        return people() == seats();
    }

    @Override
    public boolean hasDriver() {
        return driver;
    }

    @Override
    public Person driver() {
        return people[DRIVER_SEAT];
    }

    @Override
    public boolean isStopped() {
        return state == STOPPED;
    }

    @Override
    public boolean isDriving() {
        return state == DRIVING;
    }

    @Override
    public int seat(Person person) {
        if (hasDriver() && driver() == person) {
            return DRIVER_SEAT;
        } else {
            int index = FIRST_PASSENGER_SEAT;
            boolean found = false;

            while (!found && index < FIRST_PASSENGER_SEAT + passengers()) {
                found = (people[index] == person);
                index++;
            }

            if (!found) {
                return -1;
            }

            return index - 1;
        }
    }

    @Override
    public int passengers() {
        return passengers;
    }

    @Override
    public int people() {
        return hasDriver() ? passengers() + 1 : passengers;
    }

    //Méthodes privées

    private void checkIfSeatAvailable(final boolean isDriver) {
        if (isDriver && hasDriver()) {
            throw new IllegalStateException("Car has no driver seat available any more");
        } else if (!isDriver && seats() - 1 == passengers()) {
            throw new IllegalStateException("Car has no passenger seat available any more");
        }
    }

    private void checkIfNoDriver() {
        if (!hasDriver()) {
            throw new IllegalStateException("Car has no driver");
        }
    }

    private void checkIfFull() {
        if (isFull()) {
            throw new IllegalStateException("Car is full");
        }
    }

    private void checkIfDriving() {
        if (isDriving()) {
            throw new IllegalStateException("Car is already driving");
        }
    }

    private void checkIfPersonIsNull(final Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
    }

    private void checkSeats(final int seats1) {
        if (seats1 <= 0) {
            throw new IllegalArgumentException("Seats muster greater than zero");
        }
    }
}

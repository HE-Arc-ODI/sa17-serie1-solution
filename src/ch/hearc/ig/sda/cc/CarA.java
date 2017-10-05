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
public class CarA implements Car {

    private final int seats; //Nombre de siège dans l'auto

    private int people; //Nombre de personne dans l'auto
    private Person driver = null; //Personne pilotant l'auto
    private final Person[] passengers; //Personnes passagères dans l'auto

    private State state = STOPPED; //Etat dans lequel se trouve la voiture

    public CarA(final int seats) {
        checkSeats(seats);

        this.seats = seats;
        passengers = new Person[seats - 1];
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
            driver = person;
        } else {
            passengers[passengers()] = person;
        }

        people++;
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
        return driver() != null;
    }

    @Override
    public Person driver() {
        return driver;
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
        }
        else {
            int index = 0;
            boolean found = false;

            while (!found && index < passengers()) {
                found = (passengers[index] == person);
                index++;
            }

            if (!found) {
                return -1;
            }

            return FIRST_PASSENGER_SEAT + index - 1;
        }
    }

    @Override
    public int passengers() {
        return hasDriver() ? people() - 1 : people();
    }

    @Override
    public int people() {
        return people;
    }

    //Méthodes privées
    private void checkIfSeatAvailable(final boolean isDriver) {
        if (isDriver && hasDriver()) {
            throw new IllegalStateException("Car has no driver seat available any more");
        } else if(!isDriver && seats() - 1 == passengers()) {
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

    private void checkIfStopped() {
        if (isStopped()) {
            throw new IllegalStateException("Car already stopped");
        }
    }

    private void checkIfPersonIsNull(final Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
    }

    private void checkSeats(final int seats) {
        if (seats <= 0) {
            throw new IllegalArgumentException("Seats muster greater than zero");
        }
    }
}

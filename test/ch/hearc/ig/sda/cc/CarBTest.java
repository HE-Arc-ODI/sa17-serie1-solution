/*
 * HEG Haute école de gestion Arc
 * 633-1.1 Structures de données avancées 
 * 2014-2015 
 */
package ch.hearc.ig.sda.cc;

import static ch.hearc.ig.sda.cc.Car.DRIVER_SEAT;
import static ch.hearc.ig.sda.cc.Car.FIRST_PASSENGER_SEAT;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philippe Daucourt <philippe.daucourt@he-arc.ch>
 */
public class CarBTest {

    private static final int CAR_SEATS = 3;

    private Car car;

    private final Person toto = new Person("Toto");
    private final Person titi = new Person("Titi");
    private final Person tata = new Person("Tata");
    private final Person tutu = new Person("Tutu");

    public CarBTest() {
    }

    @Before
    public void setUp() {
        car = new CarB(CAR_SEATS);
    }

    @After
    public void tearDown() {
    }

    //Tests du constructeur
    @Test
    public void testNewCar() {
        assertCar(null);
        assertStoppedCar();
    }

    private void assertStoppedCar() {
        assertTrue(car.isStopped());
        assertFalse(car.isDriving());
    }
    
    private void assertDrivingCar() {
        assertFalse(car.isStopped());
        assertTrue(car.isDriving());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewCarWithIllegalArgument1() {
        car = new CarB(-1);

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewCarWithIllegalArgument2() {
        car = new CarB(0);

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDriverGetInWithNullPerson() {
        assertStoppedCar();

        car.getIn(null, true);

        fail();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testPassengerGetInWithNullPerson() {
        assertStoppedCar();

        car.getIn(null);

        fail();
    }

    @Test
    public void testDriverGetInWhenEmpty() {
        assertCar(null);

        car.getIn(toto, true);

        assertCar(toto);
    }

    private void assertCar(final Person driver) {
        assertCar(driver, new Person[0]);
    }
    
    private void assertCar(Person driver, Person... passengers) {
        assertEquals(CAR_SEATS, car.seats());
        
        if (driver == null && (passengers == null || passengers.length == 0)) {
            assertTrue(car.isEmpty());
        }
        else {
            assertFalse(car.isEmpty());
        }
        
        if (car.seats() == car.people()) {
            assertTrue(car.isFull());
        } else {
            assertFalse(car.isFull());
        }

        int people = 0;
        people += driver != null ? 1 : 0;
        people += passengers != null ? passengers.length : 0;
        
        assertEquals(people, car.people());
        assertEquals(passengers != null ? passengers.length : 0, car.passengers());
        
        if (driver != null) {
            assertTrue(car.hasDriver());
            assertEquals(driver, car.driver());
            assertEquals(DRIVER_SEAT, car.seat(driver));
        } else {
            assertFalse(car.hasDriver());
            assertNull(car.driver());
        }

        if (passengers != null) {
            for (int i = 0; i < passengers.length; i++) {
                assertEquals(FIRST_PASSENGER_SEAT + i, car.seat(passengers[i]));
            }
        }
    }
    
    @Test
    public void testPassengerGetInWhenEmpty() {
        assertCar(null);

        car.getIn(toto);

        assertCar(null, toto);
    }

    @Test
    public void testDriverGetInWhenNotEmpty() {
        car.getIn(toto);

        assertCar(null, toto);

        car.getIn(titi, true);

        assertCar(titi, toto);
    }

    @Test
    public void testPassengerGetInWhenNotEmptyWithoutDriver() {
        car.getIn(toto);

        assertCar(null, toto);

        car.getIn(titi);

        assertCar(null, toto, titi);
    }

    @Test
    public void testPassengerGetInWhenNotEmptyWithDriver() {
        car.getIn(toto, true);

        assertCar(toto);

        car.getIn(titi);

        assertCar(toto, titi);
    }

    @Test
    public void testPassengerGetInWhenAlmostFull() {
        car.getIn(toto, true);
        car.getIn(titi);

        assertCar(toto, titi);

        car.getIn(tata);

        assertCar(toto, titi, tata);
    }

    @Test(expected = IllegalStateException.class)
    public void testDriverGetInWhenNoDriverSeatAvailable() {
        car.getIn(toto, true);

        assertCar(toto);

        car.getIn(titi, true);

        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void testPassengerGetInWhenNoPassengerSeatAvailable() {
        car.getIn(toto);
        car.getIn(titi);

        assertCar(null, toto, titi);

        car.getIn(tata);
        
        fail();
    }

    @Test
    public void testDriverGetInWhenAlmostFull() {
        car.getIn(toto);
        car.getIn(titi);

        assertCar(null, toto, titi);

        car.getIn(tata, true);

        assertCar(tata, toto, titi);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetInWhenFull() {
        car.getIn(toto, true);
        car.getIn(titi);
        car.getIn(tata);

        assertCar(toto, titi, tata);
        
        car.getIn(tutu);

        fail();
    }

    @Test
    public void testCarWhenFull() {
        car.getIn(toto, true);
        car.getIn(titi);
        car.getIn(tata);

        assertCar(toto, titi, tata);
    }

    @Test
    public void testDrive() {
        car.getIn(toto, true);
        
        assertCar(toto);

        car.drive();

        assertDrivingCar();
    }
    
    @Test(expected = IllegalStateException.class)
    public void testGetInWhenDriving() {
        car.getIn(toto, true);
        
        assertCar(toto);
        
        car.drive();

        assertDrivingCar();

        car.getIn(titi, false);

        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void testDriveWhenNoDriver() {
        car.getIn(toto);
        
        assertCar(null, toto);

        car.drive();

        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void testDriveWhenAlreadyDriving() {
        car.getIn(toto, true);
        
        assertCar(toto);
        
        car.drive();

        assertDrivingCar();

        car.drive();

        fail();
    }
}

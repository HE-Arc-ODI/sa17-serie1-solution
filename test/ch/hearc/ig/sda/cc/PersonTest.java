/*
 * HEG Haute école de gestion Arc
 * 633-1.1 Structures de données avancées 
 * 2014-2015 
 */
package ch.hearc.ig.sda.cc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philippe Daucourt <philippe.daucourt@he-arc.ch>
 */
public class PersonTest {
    
    private static final String DEFAULT_FIRSTNAME = "Toto";
    private Person person;
    
    public PersonTest() {
    }
    
    @Before
    public void setUp() {
        person = new Person(DEFAULT_FIRSTNAME);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getFirstname method, of class Person.
     */
    @Test
    public void newPerson() {
        assertEquals(DEFAULT_FIRSTNAME, person.getFirstname());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void newPersonWhenNull() {
        person = new Person(null);
        
        fail();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void newPersonWhenEmpty() {
        person = new Person("");
        
        fail();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void newPersonWhenBlank() {
        person = new Person("       ");
        
        fail();
    }

    @Test
    public void setFirstname() {
        final String firstname = "Titi";
        person.setFirstname(firstname);
        
        assertEquals(firstname, person.getFirstname());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setFirstnameWhenNull() {
        person.setFirstname(null);
        
        fail();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setFirstnameWhenEmpty() {
        person.setFirstname("");
        
        fail();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setFirstnameWhenBlank() {
        person.setFirstname("       ");
        
        fail();
    }
}

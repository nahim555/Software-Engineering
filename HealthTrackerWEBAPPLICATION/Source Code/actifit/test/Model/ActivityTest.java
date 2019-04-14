package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class ActivityTest {

    Activity testActivity;
    User user;
    Exercise exercise;

    @Before
    public void setUp() {
        testActivity = new Activity(exercise, LocalDate.now(), 30, 10);
    }

    @After
    public void tearDown() {
        testActivity = null;
    }

    @Test
    public void getExercise() {
        assertEquals(exercise, testActivity.getExercise());
    }

    @Test
    public void setExercise() {
        testActivity.setExercise(exercise);
        assertEquals(exercise, testActivity.getExercise());
    }

    @Test
    public void getDate() {
        assertEquals(LocalDate.now(), testActivity.getDate());
    }

    @Test
    public void setDate() {
        testActivity.setDate(LocalDate.now());
        assertEquals(LocalDate.now(), testActivity.getDate());
    }

    @Test
    public void getDuration() {
        assertEquals(30, testActivity.getDuration());
    }

    @Test
    public void setDuration() {
        testActivity.setDuration(30);
        assertEquals(30, testActivity.getDuration());
    }

    @Test
    public void getIntensity() {
        assertEquals(10, testActivity.getIntensity());
    }

    @Test
    public void setIntensity() {
        testActivity.setIntensity(30);
        assertEquals(30, testActivity.getIntensity());
    }


}

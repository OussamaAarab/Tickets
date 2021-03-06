package com.GLA_12.androidproject;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.GLA_12.beans.Movie;
import com.GLA_12.dao.DaoMovie;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //API_Movie api_movie = API_Factory.getInstance(appContext).getAPI_Movie();
        //Movie m = api_movie.findMovie(238,null);


        Thread.sleep(500);

        DaoMovie daoMovie = new DaoMovie(appContext);
        //daoMovie.add_to_visited(m);
        ArrayList<Movie> movies =  daoMovie.findVisited();

        Log.d(getClass().getName(),"List size : "+movies.size());

        for (Movie mo:movies){
            Log.d(getClass().getName(),"Movie : id-> "+mo.getId() + " Name-> " + mo.getTitle());
        }

        assertNotNull(movies);
        assertNotEquals(movies.size(),0);



    }

}
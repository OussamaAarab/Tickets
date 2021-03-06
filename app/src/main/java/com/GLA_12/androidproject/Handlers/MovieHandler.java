package com.GLA_12.androidproject.Handlers;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;


import com.GLA_12.androidproject.Fragment.ProfilFragment;
import com.GLA_12.androidproject.Fragment.SecondActivity;

import com.GLA_12.androidproject.Fragment.VideoFragment;

import com.GLA_12.androidproject.Fragment.VideoPlayerActivity;
import com.GLA_12.androidproject.HomeAdapter.AdapterMovies;
import com.GLA_12.androidproject.Fragment.HomeFragment;
import com.GLA_12.androidproject.HomeAdapter.MovieSearchAdapter;
import com.GLA_12.androidproject.HomeAdapter.SlideAdapter;
import com.GLA_12.androidproject.HomeAdapter.TrailerAdapter;
import com.GLA_12.beans.Movie;

import java.util.ArrayList;
import java.util.HashMap;

public class MovieHandler extends Handler {
    private Context context;
    public MovieHandler(){

    }
    public MovieHandler(Context context){
        this.context = context;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (msg.arg1 == HomeFragment.MSG_START)
        {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("MoviesList");
            AdapterMovies adapter = (AdapterMovies) objMovie.get("AdapterMovies");
            adapter.SetData(movies);
        }
        if (msg.arg1 == HomeFragment.MSG_HORROR) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("HorrorMovies");
            AdapterMovies adapter = (AdapterMovies) objMovie.get("AdapterHorrorMovies");
            adapter.SetData(movies);
        }
        if (msg.arg1 == HomeFragment.MSG_ACTION) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("ActionMovies");
            AdapterMovies adapter = (AdapterMovies) objMovie.get("AdapterActionMovies");
            adapter.SetData(movies);
        }
        if (msg.arg1 == HomeFragment.MSG_ADVENTURE) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("AdventureMovies");
            AdapterMovies adapter = (AdapterMovies) objMovie.get("AdapterAdventureMovies");
            adapter.SetData(movies);
        }
        if (msg.arg1 == HomeFragment.MSG_COMEDY) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("ComedyMovies");
            AdapterMovies adapter = (AdapterMovies) objMovie.get("AdapterComedyMovies");
            adapter.SetData(movies);
        }
        if (msg.arg1 == HomeFragment.MSG_DRAMA) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("DramaMovies");
            AdapterMovies adapter = (AdapterMovies) objMovie.get("AdapterDramaMovies");
            adapter.SetData(movies);
        }
        if (msg.arg1 == HomeFragment.MSG_ROMANCE) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("RomanceMovies");
            AdapterMovies adapter = (AdapterMovies) objMovie.get("AdapterRomanceMovies");
            adapter.SetData(movies);
        }
        if (msg.arg1 == HomeFragment.MSG_WAR) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("WarMovies");
            AdapterMovies adapter = (AdapterMovies) objMovie.get("AdapterWarMovies");
            adapter.SetData(movies);
        }
        if (msg.arg1 == HomeFragment.MSG_DOCUMENTARY) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("DocumentaryMovies");
            AdapterMovies adapter = (AdapterMovies) objMovie.get("AdapterDocumentaryMovies");
            adapter.SetData(movies);
        }
        if (msg.arg1 == HomeFragment.MSG_SLIDE) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("SlideMovie");
            SlideAdapter adapter = (SlideAdapter) objMovie.get("SlideAdapter");
            adapter.setData(movies);
        }
        if (msg.arg1 == HomeFragment.MSG_LOAD) {
            // TODO : Traitement du chargement

        }
        if (msg.arg1 == SlideAdapter.MSG_SLIDE_CLICK) {
            String key = (String) msg.obj;
            Intent i = new Intent(context, VideoPlayerActivity.class);

            Log.d(getClass().getName(),"Youtube Video key : "+key);
            i.putExtra("key",key);

            context.startActivity(i);
        }

        if (msg.arg1 == SecondActivity.MSG_START) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("MoviesList");
            AdapterMovies adapter = (AdapterMovies) objMovie.get("AdapterMovies");
            adapter.SetData(movies);
        }
        if (msg.arg1 == AdapterMovies.MSG_PAGE) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("MoviesList");
            AdapterMovies adapter = (AdapterMovies) objMovie.get("AdapterMovies");
            adapter.AddData(movies);
        }
        if (msg.arg1 == VideoFragment.MSG_START_TRENDING_TRAILER) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("MoviesList");
            TrailerAdapter adapter = (TrailerAdapter) objMovie.get("AdapterMovies");
            adapter.setData(movies);

        }
        if (msg.arg1 == ProfilFragment.MSG_Last_Visited) {
            HashMap<String, Object> objMovie = (HashMap<String, Object>) msg.obj;
            ArrayList<Movie> movies = (ArrayList<Movie>) objMovie.get("MoviesList");
            MovieSearchAdapter adapter = (MovieSearchAdapter) objMovie.get("AdapterMovies");
            ProfilFragment fragment = (ProfilFragment) objMovie.get("fragment");
            adapter.SetData(movies);
            if(movies.size()>0){
                fragment.hideMessage();
            }
            else fragment.showMessage();

        }

        
    }
}

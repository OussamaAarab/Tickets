package com.GLA_12.androidproject.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GLA_12.androidproject.Handlers.MovieHandler;
import com.GLA_12.androidproject.HomeAdapter.TrailerAdapter;
import com.GLA_12.androidproject.R;
import com.GLA_12.api.API_Factory;
import com.GLA_12.api.API_Movie;
import com.GLA_12.beans.Movie;

import java.util.ArrayList;
import java.util.HashMap;

public class VideoFragment extends Fragment {



    public static final int MSG_START_TRENDING_TRAILER = 13;

    RecyclerView week_trending_rv;



    TrailerAdapter week_trending_adapter;




    MovieHandler handler = new MovieHandler();
    ArrayList trending_Movies_w = new ArrayList();

    RecyclerView latestRv;
    RecyclerView upcomingRv;
    RecyclerView popularRv;
    RecyclerView horrorRv;
    RecyclerView actionRv;
    RecyclerView comedyRv;
    RecyclerView dramaRv;
    RecyclerView romanceRv;


    //TrailerAdapter week_trending_adapter;
    TrailerAdapter latestAdapter;
    TrailerAdapter upcomingAdapter;
    TrailerAdapter popularAdapter;
    TrailerAdapter horrorAdapter;
    TrailerAdapter actionAdapter;
    TrailerAdapter comedyAdapter;
    TrailerAdapter dramaAdapter;
    TrailerAdapter romanceAdapter;


    // MovieHandler handler = new MovieHandler();
    //ArrayList<Movie> trending_Movies_w = new ArrayList();
    ArrayList<Movie> latest_Movies = new ArrayList();
    ArrayList<Movie> upcoming_Movies = new ArrayList();
    ArrayList<Movie> popular_Movies = new ArrayList();
    ArrayList<Movie> horror_Movies = new ArrayList();
    ArrayList<Movie> action_Movies = new ArrayList();
    ArrayList<Movie> comedy_Movies = new ArrayList();
    ArrayList<Movie> drama_Movies = new ArrayList();
    ArrayList<Movie> romance_Movies = new ArrayList();

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {





        v = inflater.inflate(R.layout.fragment_video, container, false);

        week_trending_rv = v.findViewById(R.id.recycler_popular_movies_week_trailers);
        latestRv = v.findViewById(R.id.latest_rv);
        upcomingRv = v.findViewById(R.id.upcoming_rv);
        popularRv = v.findViewById(R.id.recycler_popular_movies);
        horrorRv = v.findViewById(R.id.recycler_horror_movie);
        actionRv = v.findViewById(R.id.recycler_action_movie);
        comedyRv = v.findViewById(R.id.recycler_comedy_movie);
        dramaRv = v.findViewById(R.id.recycler_drama_movie);
        romanceRv = v.findViewById(R.id.recycler_romance_movie);




        latestAdapter = recyclerCards(latestRv,latest_Movies);
        popularAdapter = recyclerCards(popularRv,popular_Movies);
        horrorAdapter = recyclerCards(horrorRv,horror_Movies);
        actionAdapter = recyclerCards(actionRv,action_Movies);
        comedyAdapter = recyclerCards(comedyRv,comedy_Movies);
        dramaAdapter = recyclerCards(dramaRv,drama_Movies);
        romanceAdapter = recyclerCards(romanceRv,romance_Movies);

        this.week_trending_adapter = recyclerCards(week_trending_rv,trending_Movies_w);
        upcomingAdapter =  recyclerCards(upcomingRv,upcoming_Movies);

        LoadData();
        return v;
    }

    private TrailerAdapter recyclerCards(RecyclerView recycler , ArrayList<Movie> movies) {

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        TrailerAdapter adapters = new TrailerAdapter(getActivity(),movies);
        recycler.setAdapter(adapters);
        return  adapters;
    }
    private void LoadData() {
        // Thread that loads popular movies
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    trending_Movies_w = movie.findTrendingMovies("week", 1);
                    message.arg1 = MSG_START_TRENDING_TRAILER;
                    HashMap<String, Object> objects = new HashMap<>();
                    objects.put("MoviesList", trending_Movies_w);
                    objects.put("AdapterMovies", VideoFragment.this.week_trending_adapter);
                    message.obj = objects;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    latest_Movies = movie.findTopRatedMovies(1);
                    message.arg1 = MSG_START_TRENDING_TRAILER;
                    HashMap<String, Object> objects = new HashMap<>();
                    objects.put("MoviesList", latest_Movies);
                    objects.put("AdapterMovies", VideoFragment.this.latestAdapter);
                    message.obj = objects;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    upcoming_Movies = movie.findUpcomingMovies(1);
                    message.arg1 = MSG_START_TRENDING_TRAILER;
                    HashMap<String, Object> objects = new HashMap<>();
                    objects.put("MoviesList", upcoming_Movies);
                    objects.put("AdapterMovies", upcomingAdapter);
                    message.obj = objects;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    popular_Movies = movie.findPopularMovies(1);
                    message.arg1 = MSG_START_TRENDING_TRAILER;
                    HashMap<String, Object> objects = new HashMap<>();
                    objects.put("MoviesList", popular_Movies);
                    objects.put("AdapterMovies", popularAdapter);
                    message.obj = objects;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    horror_Movies = movie.findGenreMovies(27, 1);
                    message.arg1 = MSG_START_TRENDING_TRAILER;
                    HashMap<String, Object> objects = new HashMap<>();
                    objects.put("MoviesList", horror_Movies);
                    objects.put("AdapterMovies", horrorAdapter);
                    message.obj = objects;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    action_Movies = movie.findGenreMovies(28, 1);
                    message.arg1 = MSG_START_TRENDING_TRAILER;
                    HashMap<String, Object> objects = new HashMap<>();
                    objects.put("MoviesList", action_Movies);
                    objects.put("AdapterMovies", actionAdapter);
                    message.obj = objects;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    comedy_Movies = movie.findGenreMovies(35, 1);
                    message.arg1 = MSG_START_TRENDING_TRAILER;
                    HashMap<String, Object> objects = new HashMap<>();
                    objects.put("MoviesList", comedy_Movies);
                    objects.put("AdapterMovies", comedyAdapter);
                    message.obj = objects;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    drama_Movies = movie.findGenreMovies(18, 1);
                    message.arg1 = MSG_START_TRENDING_TRAILER;
                    HashMap<String, Object> objects = new HashMap<>();
                    objects.put("MoviesList", drama_Movies);
                    objects.put("AdapterMovies", dramaAdapter);
                    message.obj = objects;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    romance_Movies = movie.findGenreMovies(10749, 1);
                    message.arg1 = MSG_START_TRENDING_TRAILER;
                    HashMap<String, Object> objects = new HashMap<>();
                    objects.put("MoviesList", romance_Movies);
                    objects.put("AdapterMovies", romanceAdapter);
                    message.obj = objects;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}

package com.example.androidproject.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.androidproject.Handlers.MovieHandler;
import com.example.androidproject.HomeAdapter.AdapterMovies;
import com.example.androidproject.HomeAdapter.SlideAdapter;
import com.example.androidproject.R;
import com.example.api.API_Factory;
import com.example.api.API_Movie;
import com.example.beans.Movie;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    View v ;
    ScrollView scrollView;

    RecyclerView recyclerTrendingMovies;
    RecyclerView recyclerPopularMovies;
    RecyclerView recyclerHorrorMovies;
    RecyclerView recyclerActionMovies;
    RecyclerView recyclerAdventureMovies;
    RecyclerView recyclerComedyMovies;
    RecyclerView recyclerDramaMovies;
    RecyclerView recyclerWarMovies;
    RecyclerView recyclerRomanceMovies;
    RecyclerView recyclerDocumentaryMovies;

    AdapterMovies adapterTrendingMovies;
    AdapterMovies adapterPopularMovies;
    AdapterMovies adapterHorrorMovies;
    AdapterMovies adapterActionMovies;
    AdapterMovies adapterAdventureMovies;
    AdapterMovies adapterComedyMovies;
    AdapterMovies adapterDramaMovies;
    AdapterMovies adapterRomanceMovies;
    AdapterMovies adapterWarMovies;
    AdapterMovies adapterDocumentaryMovies;
    SlideAdapter slideAdapter;

    MovieHandler handlerTrendingMovies;
    MovieHandler handlerPopularMovies;
    MovieHandler handlerHorrorMovies;
    MovieHandler handlerActionMovies;
    MovieHandler handlerAdventureMovies;
    MovieHandler handlerComedyMovies;
    MovieHandler handlerDramaMovies;
    MovieHandler handlerRomanceMovies;
    MovieHandler handlerWarMovies;
    MovieHandler handlerDocumentaryMovies;

    public static final int MSG_LOAD = -1;
    public static final int MSG_START = 1;
    public static final int MSG_SLIDE = 2;
    public static final int MSG_POPULAR = 3;
    public static final int MSG_HORROR = 5;
    public static final int MSG_ACTION = 6;
    public static final int MSG_ADVENTURE = 7;
    public static final int MSG_COMEDY = 8;
    public static final int MSG_DRAMA = 9;
    public static final int MSG_ROMANCE = 10;
    public static final int MSG_WAR = 11;
    public static final int MSG_DOCUMENTARY = 12;

    ArrayList<Movie> trending_Movies_w = new ArrayList<>();
    ArrayList<Movie> trending_Movies_d = new ArrayList<>();
    ArrayList<Movie> popular_Movies = new ArrayList<>();
    ArrayList<Movie> horror_Movies = new ArrayList<>();
    ArrayList<Movie> action_Movies = new ArrayList<>();
    ArrayList<Movie> adventure_Movies = new ArrayList<>();
    ArrayList<Movie> comedy_Movies = new ArrayList<>();
    ArrayList<Movie> drama_Movies = new ArrayList<>();
    ArrayList<Movie> romance_Movies = new ArrayList<>();
    ArrayList<Movie> war_Movies = new ArrayList<>();
    ArrayList<Movie> documentary_Movies = new ArrayList<>();

    private List<Movie> liste_Slide;
    private ViewPager sliderpager;
    private TabLayout indicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        recyclerTrendingMovies = v.findViewById(R.id.recycler_popular_movies_week);
        recyclerPopularMovies = v.findViewById(R.id.recycler_popular_series);
        recyclerHorrorMovies = v.findViewById(R.id.recycler_horror_movie);
        recyclerActionMovies = v.findViewById(R.id.recycler_action_movie);
        recyclerAdventureMovies = v.findViewById(R.id.recycler_adventure_movie);
        recyclerComedyMovies = v.findViewById(R.id.recycler_comedy_movie);
        recyclerDramaMovies = v.findViewById(R.id.recycler_drama_movie);
        recyclerRomanceMovies = v.findViewById(R.id.recycler_romance_movie);
        recyclerWarMovies = v.findViewById(R.id.recycler_war_movie);
        recyclerDocumentaryMovies = v.findViewById(R.id.recycler_documentary_movie);
        scrollView = v.findViewById(R.id.scrollView_Home);

        adapterTrendingMovies = recyclerCards(recyclerTrendingMovies,adapterTrendingMovies,trending_Movies_w);
        adapterPopularMovies = recyclerCards(recyclerPopularMovies,adapterPopularMovies,popular_Movies);
        adapterHorrorMovies = recyclerCards(recyclerHorrorMovies,adapterHorrorMovies,horror_Movies);
        adapterActionMovies = recyclerCards(recyclerActionMovies,adapterActionMovies,action_Movies);
        adapterAdventureMovies = recyclerCards(recyclerAdventureMovies,adapterAdventureMovies,adventure_Movies);
        adapterComedyMovies = recyclerCards(recyclerComedyMovies,adapterComedyMovies,comedy_Movies);
        adapterDramaMovies = recyclerCards(recyclerDramaMovies,adapterDramaMovies,drama_Movies);
        adapterRomanceMovies = recyclerCards(recyclerRomanceMovies,adapterRomanceMovies,romance_Movies);
        adapterWarMovies = recyclerCards(recyclerWarMovies,adapterWarMovies,war_Movies);
        adapterDocumentaryMovies = recyclerCards(recyclerDocumentaryMovies,adapterDocumentaryMovies,documentary_Movies);

        handlerTrendingMovies = new MovieHandler();
        handlerPopularMovies = new MovieHandler();
        handlerHorrorMovies = new MovieHandler();
        handlerActionMovies = new MovieHandler();
        handlerAdventureMovies = new MovieHandler();
        handlerComedyMovies = new MovieHandler();
        handlerDramaMovies = new MovieHandler();
        handlerRomanceMovies = new MovieHandler();
        handlerWarMovies = new MovieHandler();
        handlerDocumentaryMovies = new MovieHandler();

        sliderpager = v.findViewById(R.id.auto_slide);
        indicator = v.findViewById(R.id.indicator);
        liste_Slide = new ArrayList<>();

        slideAdapter = new SlideAdapter(getActivity(), liste_Slide);
        sliderpager.setAdapter(slideAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeFragment.SliderTimer(), 4000, 6000);
        indicator.setupWithViewPager(sliderpager,true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    message.arg1 = MSG_LOAD;
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    trending_Movies_w = movie.findTrendingMovies("week",1);
                    message = new Message();
                    message.arg1 = MSG_START;
                    HashMap<String,Object> objects = new HashMap<>();
                    objects.put("MoviesList",trending_Movies_w);
                    objects.put("AdapterMovies",adapterTrendingMovies);
                    message.obj = objects;
                    handlerTrendingMovies.sendMessage(message);

                    // Slider Movies
                    message = new Message();
                    message.arg1 = MSG_LOAD;
                    ArrayList<Movie> temp = movie.findTrendingMovies("day",1);
                    liste_Slide = new ArrayList<>();
                    for(int i=0; i<5;i++)
                    {
                        liste_Slide.add(temp.get(i));
                    }
                    message = new Message();
                    message.arg1 = MSG_SLIDE;
                    objects = new HashMap<>();
                    objects.put("SlideMovie",liste_Slide);
                    objects.put("SlideAdapter",slideAdapter);
                    message.obj = objects;
                    handlerTrendingMovies.sendMessage(message);

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
                    message.arg1 = MSG_LOAD;
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    popular_Movies = movie.findPopularMovies();
                    message = new Message();
                    message.arg1 = MSG_START;
                    HashMap<String,Object> objects = new HashMap<>();
                    objects.put("MoviesList",popular_Movies);
                    objects.put("AdapterMovies",adapterPopularMovies);
                    message.obj = objects;
                    handlerPopularMovies.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //Genre of Movies
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Horror Movies
                    Message message = new Message();
                    message.arg1 = MSG_LOAD;
                    API_Factory factory = API_Factory.getInstance(v.getContext());
                    API_Movie movie = factory.getAPI_Movie();
                    horror_Movies = movie.findGenreMovies(27);
                    message = new Message();
                    message.arg1 = MSG_HORROR;
                    HashMap<String,Object> objects = new HashMap<>();
                    objects.put("HorrorMovies",horror_Movies);
                    objects.put("AdapterHorrorMovies",adapterHorrorMovies);
                    message.obj = objects;
                    handlerHorrorMovies.sendMessage(message);

                    // Action Movie
                    message = new Message();
                    action_Movies = movie.findGenreMovies(28);
                    message = new Message();
                    message.arg1 = MSG_ACTION;
                    objects = new HashMap<>();
                    objects.put("ActionMovies",action_Movies);
                    objects.put("AdapterActionMovies",adapterActionMovies);
                    message.obj = objects;
                    handlerActionMovies.sendMessage(message);

                    // Adventure Movie
                    message = new Message();
                    adventure_Movies = movie.findGenreMovies(12);
                    message = new Message();
                    message.arg1 = MSG_ADVENTURE;
                    objects = new HashMap<>();
                    objects.put("AdventureMovies",adventure_Movies);
                    objects.put("AdapterAdventureMovies",adapterAdventureMovies);
                    message.obj = objects;
                    handlerAdventureMovies.sendMessage(message);

                    // Comedy Movie
                    message = new Message();
                    comedy_Movies = movie.findGenreMovies(35);
                    message = new Message();
                    message.arg1 = MSG_COMEDY;
                    objects = new HashMap<>();
                    objects.put("ComedyMovies",comedy_Movies);
                    objects.put("AdapterComedyMovies",adapterComedyMovies);
                    message.obj = objects;
                    handlerComedyMovies.sendMessage(message);

                    // Drama Movie
                    message = new Message();
                    drama_Movies = movie.findGenreMovies(18);
                    message = new Message();
                    message.arg1 = MSG_DRAMA;
                    objects = new HashMap<>();
                    objects.put("DramaMovies",drama_Movies);
                    objects.put("AdapterDramaMovies",adapterDramaMovies);
                    message.obj = objects;
                    handlerDramaMovies.sendMessage(message);

                    // Romance Movie
                    message = new Message();
                    romance_Movies = movie.findGenreMovies(10749);
                    message = new Message();
                    message.arg1 = MSG_ROMANCE;
                    objects = new HashMap<>();
                    objects.put("RomanceMovies",romance_Movies);
                    objects.put("AdapterRomanceMovies",adapterRomanceMovies);
                    message.obj = objects;
                    handlerRomanceMovies.sendMessage(message);

                    // War Movie
                    message = new Message();
                    war_Movies = movie.findGenreMovies(10752);
                    message = new Message();
                    message.arg1 = MSG_WAR;
                    objects = new HashMap<>();
                    objects.put("WarMovies",war_Movies);
                    objects.put("AdapterWarMovies",adapterWarMovies);
                    message.obj = objects;
                    handlerWarMovies.sendMessage(message);

                    // Documentary Movie
                    message = new Message();
                    documentary_Movies = movie.findGenreMovies(99);
                    message = new Message();
                    message.arg1 = MSG_DOCUMENTARY;
                    objects = new HashMap<>();
                    objects.put("DocumentaryMovies",documentary_Movies);
                    objects.put("AdapterDocumentaryMovies",adapterDocumentaryMovies);
                    message.obj = objects;
                    handlerDocumentaryMovies.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });


        return v;
    }

    private AdapterMovies recyclerCards(RecyclerView recycler ,AdapterMovies adapters, ArrayList<Movie> movies) {

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapters = new AdapterMovies(movies);
        recycler.setAdapter(adapters);
        return  adapters;
    }

    class SliderTimer extends TimerTask{

        @Override
        public void run() {
            if(getActivity()==null) return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(sliderpager==null) return;
                    if(sliderpager.getCurrentItem() < liste_Slide.size()-1)
                    {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem()+1);
                    }
                    else
                    {
                        sliderpager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
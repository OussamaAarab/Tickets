package com.GLA_12.androidproject.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GLA_12.androidproject.Handlers.MovieSearchHandler;
import com.GLA_12.androidproject.HomeAdapter.MovieSearchAdapter;
import com.GLA_12.androidproject.R;
import com.GLA_12.api.API_Factory;
import com.GLA_12.api.API_Movie;
import com.GLA_12.beans.Movie;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchFragment extends Fragment {
    View view;
    RecyclerView recyclerViewSearch,recyclerViewPopular;
    MovieSearchAdapter adapterSearch;
    TextView textView;
    RecyclerView.LayoutManager managerSearch;
    SearchView searchView;
    FragmentManager fragmentManager;
    ArrayList<Movie> movies = new ArrayList<>();
    public static final int MSG_SEARCH = 1,MSG_POPULAR=3;
    Handler handlerMovie;


    public SearchFragment(){
    }
    public SearchFragment(ArrayList<Movie> movies)  {
        this.movies = movies;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        this.view=view;
        textView=view.findViewById(R.id.pp_tilte);
        searchView = view.findViewById(R.id.search_view);

        recyclerViewSearch = view.findViewById(R.id.list_movies);

        recyclerViewSearch.setHasFixedSize(true);
        managerSearch =new LinearLayoutManager(view.getContext());
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        adapterSearch =new MovieSearchAdapter(this.getActivity(),movies,0);
        recyclerViewSearch.setAdapter(adapterSearch);



        handlerMovie = new MovieSearchHandler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                API_Factory factory = null;
                try {
                    factory = API_Factory.getInstance(view.getContext());
                    Message message = new Message();
                    message.arg1 = MSG_POPULAR;
                    API_Movie api_movie = factory.getAPI_Movie();
                    movies=api_movie.findPopularMovies(1);
                    HashMap<String,Object> objects = new HashMap<>();
                    objects.put("movies",movies);
                    objects.put("adapterPopular",adapterSearch);
                    message.obj = objects;
                    handlerMovie.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


     searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextSubmit(String query) {

             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     try {
                         Message message = new Message();
                         try {
                             API_Factory factory = API_Factory.getInstance(view.getContext());
                             API_Movie api_movie = factory.getAPI_Movie();
                             movies = api_movie.Search_Movie(query,1);
                             message = new Message();
                             message.arg1 = MSG_SEARCH;
                             HashMap<String,Object> objects = new HashMap<>();
                             objects.put("MovieSearch",movies);
                             objects.put("MovieSearchAdapter", adapterSearch);
                             objects.put("requestedMovie",query);
                             message.obj = objects;
                             handlerMovie.sendMessage(message);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
             }).start();
             return false;
         }

         @Override
         public boolean onQueryTextChange(String newText) {
             textView.setText("Search results: ");
             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     try {
                         Message message = new Message();

                         try {
                             API_Factory factory = API_Factory.getInstance(view.getContext());
                             API_Movie api_movie = factory.getAPI_Movie();
                             movies = api_movie.Search_Movie(newText,1);
                             message = new Message();
                             message.arg1 = MSG_SEARCH;
                             HashMap<String,Object> objects = new HashMap<>();
                             objects.put("MovieSearch",movies);
                             objects.put("MovieSearchAdapter", adapterSearch);
                             objects.put("requestedMovie",newText);
                             message.obj = objects;
                             handlerMovie.sendMessage(message);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
             }).start();

             return false;
         }
     });



        return  view;
    }



}



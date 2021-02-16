package com.ahmettekin.WeatherApp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmettekin.WeatherApp.R;
import com.ahmettekin.WeatherApp.adapter.RecyclerViewAdapter;
import com.ahmettekin.WeatherApp.database.LocalDataClass;
import com.ahmettekin.WeatherApp.listener.RecyclerViewOnClickListener;
import com.ahmettekin.WeatherApp.model.WeatherModel;
import com.ahmettekin.WeatherApp.service.WeatherAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment implements RecyclerViewOnClickListener {
    private ArrayList<WeatherModel.WeatherItem> weatherItems;
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private CompositeDisposable compositeDisposable;
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private final String databaseEmptyWarningText = "veritabanı boş";
    private FloatingActionButton fab;
    private View alertDesign;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        alertDesign = inflater.inflate(R.layout.alert_view_tasarim, null, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        configureListener();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadData();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(weatherItems);
        weatherItems = new ArrayList<>();
        fab = view.findViewById(R.id.floatingActionButton);
    }

    private void loadData() {
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        compositeDisposable = new CompositeDisposable();
        String ids = LocalDataClass.getInstance().getCityIdFromDatabase(getActivity());
        compositeDisposable.add(weatherAPI.getWeatherData(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<WeatherModel>() {

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull WeatherModel weatherModel) {
                        handleResponse(weatherModel);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        weatherItems.clear();
                        recyclerViewAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), databaseEmptyWarningText, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                })
        );
    }

    private void handleResponse(WeatherModel weatherModel) {
        this.weatherItems = weatherModel.weatherItems;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapter = new RecyclerViewAdapter(this, this.weatherItems);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void removeFromSuperView(ViewGroup childView) {
        ViewGroup parent = (ViewGroup) childView.getParent();
        if (parent != null) {
            parent.removeView(childView);
        }
    }

    @Override
    public void recyclerViewDeleteClick(int position, ArrayList<WeatherModel.WeatherItem> weatherItemList, RecyclerViewAdapter.RowHolder holder) {
        PopupMenu popup = new PopupMenu(getContext(), holder.deleteImage);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.recycler_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    String nameOfCityToBeDeleted = weatherItemList.get(position).getName().split(" ")[0];
                    LocalDataClass.getInstance().deleteCityFromDatabase(getContext(), nameOfCityToBeDeleted);
                    loadData();
                    return true;
                default:
            }
            return false;
        });
    }

    @Override
    public void recyclerViewItemViewClick(int position, ArrayList<WeatherModel.WeatherItem> weatherItemList) {
        ListFragmentDirections.ActionListFragmentToCityDetailsFragment action =
                ListFragmentDirections
                        .actionListFragmentToCityDetailsFragment(weatherItemList.get(position).getName(),
                                (float) weatherItemList.get(position).getMain().getFeelsLike(),
                                (int) weatherItemList.get(position).getMain().getHumidity(),
                                (float) weatherItemList.get(position).getMain().getTemp(),
                                (float) weatherItemList.get(position).getCoord().getLon(),
                                (float) weatherItemList.get(position).getCoord().getLat());
        Navigation.findNavController(getView()).navigate(action);
    }

    public void configureListener(){
        fab.setOnClickListener(v -> {
            alertDialogBuilder = new AlertDialog.Builder(getContext());
            removeFromSuperView((ViewGroup) alertDesign);
            alertDialogBuilder.setView(alertDesign);
            alertDialogBuilder.setMessage("Add City");
            TextView cityName = alertDesign.findViewById(R.id.editTextCityName);

            alertDialogBuilder.setPositiveButton("Kaydet", (dialog, which) -> {
                Toast.makeText(getContext(), "kaydet" + cityName.getText().toString(), Toast.LENGTH_SHORT).show();
            });
            alertDialogBuilder.setNegativeButton("iptal", (dialog, which) -> {
                Toast.makeText(getContext(), "iptal tıklandı", Toast.LENGTH_SHORT).show();
            });
            alertDialogBuilder.create().show();
        });
    }
}
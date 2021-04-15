package com.ahmettekin.WeatherApp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
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
import com.ahmettekin.WeatherApp.model.CityModel;
import com.ahmettekin.WeatherApp.model.WeatherModel;
import com.ahmettekin.WeatherApp.service.CityAPI;
import com.ahmettekin.WeatherApp.service.CityService;
import com.ahmettekin.WeatherApp.service.WeatherAPI;
import com.ahmettekin.WeatherApp.utils.ViewOperations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment {
    private ArrayList<WeatherModel.WeatherItem> weatherItems;
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private CompositeDisposable compositeDisposable;
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private final String databaseEmptyWarningText = "veritabanı boş";
    private FloatingActionButton fab;
    private View alertDesign;
    private SearchableSpinner searchableSpinner;
    private AlertDialog.Builder alertDialogBuilder;
    private Context mContext;
    private CityModel secilenSehir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        alertDesign = inflater.inflate(R.layout.alert_view_tasarim, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        spinnerConfigure();
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
        mContext = view.getContext();
        searchableSpinner = alertDesign.findViewById(R.id.searchableSpinner);
    }

    private void loadData() {
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        compositeDisposable = new CompositeDisposable();
        String ids = LocalDataClass.getInstance().getCityIdFromDatabase(mContext);
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
        recyclerViewAdapter = new RecyclerViewAdapter(new RecyclerViewOnClickListener() {
            @Override
            public void recyclerViewDeleteClick(int position, ArrayList<WeatherModel.WeatherItem> weatherItemList, View view) {
                PopupMenu popup = new PopupMenu(getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.recycler_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.action_delete) {
                        int idOfCityToBeDeleted = weatherItemList.get(position).getId();
                        LocalDataClass.getInstance().deleteCityFromDatabase(mContext, idOfCityToBeDeleted);
                        loadData();
                        return true;
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
        }, this.weatherItems);

        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void configureListener() {
        fab.setOnClickListener(v -> {
            alertDialogBuilder = new AlertDialog.Builder(mContext);
            ViewOperations.removeFromSuperView((ViewGroup) alertDesign);
            alertDialogBuilder.setView(alertDesign);
            alertDialogBuilder.setPositiveButton("Kaydet", (dialog, which) -> {
                LocalDataClass.getInstance().writeData(secilenSehir.name, secilenSehir.id, getContext());
                loadData();
            });
            alertDialogBuilder.setNegativeButton("iptal", (dialog, which) -> {});
            alertDialogBuilder.create().show();
        });
    }

    private void spinnerConfigure(){
        CityAPI cityAPI = CityService.getInstance().getRetrofit().create(CityAPI.class);
        Call<List<CityModel>> call = cityAPI.getData();
        call.enqueue(new Callback<List<CityModel>>() {
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {

                if (response.isSuccessful()) {
                    ArrayList<CityModel> cityList=new ArrayList<>();
                    ArrayList<String> cityNameList=new ArrayList<>();

                    for (CityModel temp :response.body()) {
                        cityList.add(temp);
                        cityNameList.add(temp.name);
                    }

                    ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_tek_satir, cityNameList);
                    spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    searchableSpinner.setTitle("Şehir Seç");
                    searchableSpinner.setPositiveButton("SEÇ");
                    searchableSpinner.setAdapter(spnAdapter);
                    searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            secilenSehir=cityList.get(position);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                }
            }
            @Override
            public void onFailure(Call<List<CityModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
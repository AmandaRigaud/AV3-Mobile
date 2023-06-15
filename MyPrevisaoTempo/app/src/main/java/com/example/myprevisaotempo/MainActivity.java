package com.example.myprevisaotempo;

import android.os.Bundle;

import com.example.myprevisaotempo.api.MeteoService;
import com.example.myprevisaotempo.model.Daily;
import com.example.myprevisaotempo.model.Geocoder;
import com.example.myprevisaotempo.model.Hourly;
import com.example.myprevisaotempo.model.Meteo;
import com.google.android.gms.location.FusedLocationProviderClient;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.view.Menu;
import android.view.MenuItem;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    private AppBarConfiguration appBarConfiguration;
    private RelativeLayout load;
    private TextView localText;
    private ImageButton searchButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;

    Retrofit retrofit;

    ArrayList<Meteo> meteos = new ArrayList<Meteo>();
    int pagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localText = findViewById(R.id.user_name_text_view);
        recyclerView = findViewById(R.id.recycler_main_view);
        load = findViewById(R.id.loadingPanel);
        searchButton = findViewById(R.id.btn_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, FavoritosActivity.class);
                //startActivity(intent);
                //finish();
            }
        });

        load.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        myAdapter = new DailyAdapter(meteos);
        recyclerView.setAdapter(myAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        retrofit = new Retrofit.Builder().baseUrl("https://api.open-meteo.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();

        setSupportActionBar(findViewById(R.id.toolBar));

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == RecyclerView.SCROLL_STATE_SETTLING){
                    pagePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    localText.setText(meteos.get(pagePosition).getLocal());

                }
            }
        });

        load.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void GetWeatherRetrifit(String lat, String lon){
        MeteoService meteoService = retrofit.create(MeteoService.class);
        String latLon = lat + ","+ lon;
        Geocoder geo = new Geocoder();
        geo.atCallGeo(latLon);
        Meteo meteoOne = new Meteo(null, lat, lon, null, null);
        Call<JsonObject> call = meteoService.GetWeatherJson(lat, lon);

        call.enqueue((new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("Call GetW: " + call.request());
                if(response.isSuccessful()){

                    List<String> timeList = new ArrayList<String>();
                    List<String> timeList2 = new ArrayList<String>();
                    List<String> temperature_2mList = new ArrayList<String>();
                    List<String> temperature_2mList2 = new ArrayList<String>();
                    List<String> precipitation_probabilityList = new ArrayList<String>();
                    List<String> weathercodeList = new ArrayList<String>();
                    List<String> temperature_1mList = new ArrayList<String>();
                    List<String> sunriseList = new ArrayList<String>();
                    List<String> sunsetList = new ArrayList<String>();

                    System.out.println(response.body());
                    JsonObject meteo = response.body();
                    JsonArray timeH = meteo.get("hourly").getAsJsonObject().get("time").getAsJsonArray();
                    JsonArray temperatureH = meteo.get("hourly").getAsJsonObject().get("temperature_2m").getAsJsonArray();
                    JsonArray precipitation_probabilityH = meteo.get("hourly").getAsJsonObject().get("precipitation_probability").getAsJsonArray();
                    JsonArray weathercodeH = meteo.get("hourly").getAsJsonObject().get("weathercode").getAsJsonArray();
                    JsonArray timeD = meteo.get("daily").getAsJsonObject().get("time").getAsJsonArray();
                    JsonArray temperatureMaxD = meteo.get("daily").getAsJsonObject().get("temperature_2m_max").getAsJsonArray();
                    JsonArray temperatureMinD = meteo.get("daily").getAsJsonObject().get("temperature_2m_min").getAsJsonArray();
                    JsonArray sunriseD = meteo.get("daily").getAsJsonObject().get("sunrise").getAsJsonArray();
                    JsonArray sunsetD = meteo.get("daily").getAsJsonObject().get("sunset").getAsJsonArray();

                    for(int i=0; i<timeD.size(); i++){
                        timeList.add(timeD.get(i).toString());
                        temperature_2mList.add(temperatureMaxD.get(i).toString());
                        temperature_1mList.add(temperatureMinD.get(i).toString());
                        sunriseList.add(sunriseD.get(i).toString());
                        sunsetList.add(sunsetD.get(i).toString());
                    }

                    System.out.print("TimeD: ");
                    System.out.println(timeD);
                    System.out.print("TimeList: ");
                    System.out.println(timeList);
                    Daily newDaily = new Daily(timeList, temperature_2mList, temperature_1mList, sunriseList, sunsetList);

                    for(int j=0; j<timeH.size(); j++){
                        timeList2.add(timeH.get(j).toString());
                        temperature_2mList2.add(temperatureH.get(j).toString());
                        precipitation_probabilityList.add(precipitation_probabilityH.get(j).toString());
                        weathercodeList.add(weathercodeH.get(j).toString());
                    }

                    Hourly newHourly = new Hourly(timeList2, temperature_2mList2, precipitation_probabilityList, weathercodeList);

                    meteoOne.setDaily(newDaily);
                    meteoOne.setHourly(newHourly);
                    meteoOne.setLocal(geo.getLocal());

                    //TODO: Verificar se o obj já existe na lista. Se sim, atualizar. Se não, add.
                    meteos.add(meteoOne);

                    System.out.print("Meteo: " );
                    System.out.println(meteoOne);
                    System.out.println("Local: " + meteoOne.getLocal());

                    myAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        }));
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {

        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            System.out.println("Latitude On: " + location.getLatitude());
                            System.out.println("Longitude On: " + location.getLongitude());
                            GetWeatherRetrifit(Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            System.out.println("Latitude Location: " + mLastLocation.getLatitude());
            System.out.println("Longitude Location: " + mLastLocation.getLongitude());
            GetWeatherRetrifit(Double.toString(mLastLocation.getLatitude()), Double.toString(mLastLocation.getLongitude()));
        }
    };

    //Check for network and GPS permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    ///Request for permissions///
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

}


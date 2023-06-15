package com.example.myprevisaotempo;

import android.os.Bundle;

import com.example.myprevisaotempo.api.MeteoService;
import com.example.myprevisaotempo.databinding.ActivityMainBinding;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;

    Retrofit retrofit;

    ArrayList<Meteo> meteos = new ArrayList<Meteo>();
    HashMap<String, Integer> meteoMap = new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_main_view);
        Context c = this;
        DBInterface.setPath(c.getFilesDir().toString());
        DBInterface.criarBanco();
        myAdapter = new DailyAdapter(meteos);
        recyclerView.setAdapter(myAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        retrofit = new Retrofit.Builder().baseUrl("https://api.open-meteo.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();

        setSupportActionBar(findViewById(R.id.toolBar));

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
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

        CompletableFuture<String> taskGeo = CompletableFuture.supplyAsync(() -> {
            geo.atCallGeo(latLon);
            return geo.lastLocation;
        });
        String cidade = taskGeo.join();
        Meteo meteoOne = new Meteo(null, lat, lon, null, null);
        Call<JsonObject> call = meteoService.GetWeatherJson(lat, lon);

        call.enqueue((new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("Call GetW: " + call.request());
                if(response.isSuccessful()){

                    List<String> timeList = new ArrayList<String>();
                    List<String> temperature_2mList = new ArrayList<String>();
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

                    Daily newDaily = new Daily(timeList, temperature_2mList, temperature_1mList, sunriseList, sunsetList);

                    timeList.clear();
                    temperature_2mList.clear();

                    for(int j=0; j<timeH.size(); j++){
                        timeList.add(timeH.get(j).toString());
                        temperature_2mList.add(temperatureH.get(j).toString());
                        precipitation_probabilityList.add(precipitation_probabilityH.get(j).toString());
                        weathercodeList.add(weathercodeH.get(j).toString());
                    }

                    Hourly newHourly = new Hourly(timeList, temperature_2mList, precipitation_probabilityList, weathercodeList);

                    meteoOne.setLocal(cidade);
                    meteoOne.setDaily(newDaily);
                    meteoOne.setHourly(newHourly);
                    if(meteoMap.containsKey(cidade)){
                        meteos.set(meteoMap.get(cidade), meteoOne);
                    }else {
                        meteoMap.put(cidade, meteos.toArray().length);
                        meteos.add(meteoOne);
                    }
                    if(meteoMap.get(cidade) != 0) {
                        DBInterface.InserirHistorico(lat, lon, cidade);
                    }
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
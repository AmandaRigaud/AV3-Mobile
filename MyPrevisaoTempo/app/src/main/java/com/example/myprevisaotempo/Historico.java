package com.example.myprevisaotempo;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.core.view.WindowCompat;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myprevisaotempo.databinding.ActivityHistoricoBinding;

import java.util.ArrayList;

public class Historico extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHistoricoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHistoricoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        //ListView LV = findViewById(R.id.listview_first);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_historico);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_historico);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void getHistory(ListView LV) {
        ArrayList<SaidaDB> ListaDB = DBInterface.ReturnList();
        DBListAdapter historyAdapter = new DBListAdapter(this, ListaDB);
        LV.setAdapter(historyAdapter);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SaidaDB Item = ListaDB.get(position);
                System.out.println("Clicado: " + Item.Latitude + ", " + Item.Longitude + ", " + Item.Cidade);
                // Todo: Fazer isso retornar para pesquisar com essa latitude e longitude
            }
        });
    }

}


class DBListAdapter extends ArrayAdapter<SaidaDB>{
    private LayoutInflater inflater;
    public DBListAdapter(Context context, ArrayList<SaidaDB> lista){
        super(context, 0, lista);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = inflater.inflate(android.R.layout.simple_list_item_1,parent, false);
        }
        SaidaDB Item = getItem(position);

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(Item.Cidade+ "("+ Item.Latitude+", "+Item.Longitude+")");
    return textView;
    }
}
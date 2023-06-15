package com.example.myprevisaotempo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.ViewHolder> {

    private List<SaidaDB> Banco;

    public HistoricoAdapter(ArrayList<SaidaDB> Banco){
        this.Banco = Banco;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView CidadeHist, ClimaHist;
        private ImageView clima;
        private Button btn_Clear;

        public ViewHolder(View view){
            super(view);

            clima = view.findViewById(R.id.imgClima);
            btn_Clear = view.findViewById(R.id.buttonClear);
            CidadeHist = view.findViewById(R.id.Cidade_Hist);
            ClimaHist = view.findViewById(R.id.Clima_Hist);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_pesquisa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){

        holder.btn_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Banco.remove(holder.getAdapterPosition());
                HistoricoAdapter.this.notifyDataSetChanged();
            }
        });


        holder.CidadeHist.setText(Banco.get(position).Cidade);





    }

    @Override
    public int getItemCount() {
        return Banco.size();
    }
}

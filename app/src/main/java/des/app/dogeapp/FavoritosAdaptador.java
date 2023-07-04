package des.app.dogeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoritosAdaptador extends RecyclerView.Adapter<FavoritosAdaptador.ViewHolder>{
    private List<String> lista_url_imagen;

    public FavoritosAdaptador(List<String> lista_url_imagen){
        this.lista_url_imagen = lista_url_imagen;
    }
    public void setLista_url_imagen(List<String> lista_url_imagen){
        this.lista_url_imagen = lista_url_imagen;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoritosAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_recicler, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritosAdaptador.ViewHolder holder, int position) {
        if (lista_url_imagen.isEmpty()){
            return;
        }
        String url_imagen = lista_url_imagen.get(position);
        Picasso.get().load(url_imagen).into(holder.img_perro);

        holder.btn_remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posicion_actual = holder.getAdapterPosition();
                if (posicion_actual != RecyclerView.NO_POSITION){
                    String remover_imagen = lista_url_imagen.get(posicion_actual);
                    lista_url_imagen.remove(posicion_actual);
                    notifyItemRemoved(posicion_actual);

                    SharedPreferences preferences = view.getContext().getSharedPreferences("Favoritos", Context.MODE_PRIVATE);
                    Set<String> listar_url_imagen = preferences.getStringSet("url_imagenes_favoritos", new HashSet<>());
                    listar_url_imagen.remove(remover_imagen);
                    preferences.edit().putStringSet("url_imagenes_favoritos", listar_url_imagen).apply();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (lista_url_imagen.isEmpty()){
            return 0;
        }else {
            return lista_url_imagen.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_perro;
        Button btn_remover;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_perro = itemView.findViewById(R.id.img_card_fav);
            btn_remover = itemView.findViewById(R.id.btn_remove_car_fav);
        }
    }
}

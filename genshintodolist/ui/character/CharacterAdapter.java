package com.example.genshintodolist.ui.character;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genshintodolist.R;
import com.example.genshintodolist.data.Character;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private List<Character> characterList;
    private Context context;

    public CharacterAdapter(Context context, List<Character> characterList){
        this.context = context;
        this.characterList = characterList;
    }

    @NonNull
    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Character character = characterList.get(position);
        holder.iconImage.setImageResource(character.getIconResId());
        holder.textView.setText(character.getName());

        holder.iconImage.setOnClickListener(v -> {
            SharedPreferences prefs = context.getSharedPreferences("genshin_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("selected_wallpaper_resid", character.getWallpaperRawResId());
            editor.apply();

            Toast.makeText(context, "Wallpaper Changed", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount(){
        return characterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView textView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.imgCharacterIcon);
            textView = itemView.findViewById(R.id.txtCharacterName);
        }
    }
}

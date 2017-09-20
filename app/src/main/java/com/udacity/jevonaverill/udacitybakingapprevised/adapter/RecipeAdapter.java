package com.udacity.jevonaverill.udacitybakingapprevised.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.jevonaverill.udacitybakingapprevised.R;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jevonaverill on 9/6/17.
 */

public final class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList;
    private final RecipeClickListener mRecipeClickListener;

    public RecipeAdapter(List<Recipe> recipeList, RecipeClickListener recipeClickListener) {
        mRecipeList = recipeList;
        mRecipeClickListener = recipeClickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_item_list, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(mRecipeList.get(position), mRecipeClickListener);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public interface RecipeClickListener {
        void onRecipeClick(int position);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_recipe_title)
        TextView recipeTitle;
        @BindView(R.id.recipe_image)
        ImageView recipeImage;

        RecipeClickListener listener;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(final Recipe recipe, RecipeClickListener recipeClickListener) {
            recipeTitle.setText(recipe.getName());
            if (TextUtils.isEmpty(recipe.getImage()) == false) {
                Picasso.with(itemView.getContext())
                        .load(recipe.getImage())
                        .into(recipeImage);
            }
            this.listener = recipeClickListener;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onRecipeClick(position);
        }
    }

}

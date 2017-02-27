package com.example.janda_000.newsfeed;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by janda_000 on 2/24/2017.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {


    //THis is the adapter context that creates the array list and integrates it into the adapter
    public ArticleAdapter(Context context){
        super(context, 0, new ArrayList<Article>());
    }

    //This will over ride the getView that will create the view that will fill in the view with the
    // correct information that needs to be included
    @Override
    public View getView(int position, View listedView, ViewGroup parent){
        View listedItemView = listedView;
        if (listedItemView == null){
            listedItemView = LayoutInflater.from(getContext()).inflate(R.layout.article_item,
                    parent, false);
        }

        //THis will set the current item in the arraylist so that each specific view is filled with
        // the information for that specific item
        Article currentArticle = getItem(position);

        // The following is the assignment of each view that is going to be filled with to a set
        // variable that will be called in order to set the right informaiton into that view
        TextView articleTitle = (TextView) listedItemView.findViewById(R.id.article_title);
        TextView articleDate = (TextView) listedItemView.findViewById(R.id.article_date);
        TextView articleSection = (TextView) listedItemView.findViewById(R.id.article_section);

        // the following two methods are actually setting the text that is going to be displayed
        // in the assigned view. The date will be formatted and assigned below
        articleTitle.setText(currentArticle.getTitle());
        articleSection.setText(currentArticle.getSection());
        articleDate.setText(currentArticle.getDate());

        return listedItemView;

    }
}

package com.example.berkh.wiki_proje.Kategoriler;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.berkh.wiki_proje.Model.Categories;
import com.example.berkh.wiki_proje.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<Categories> listDataHeader;
    private HashMap<Categories,List<Categories>> listHashMap;


    public ExpandableListAdapter(Context context, List<Categories> listDataHeader, HashMap<Categories, List<Categories>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }


    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return  listDataHeader.get(i).SubCategories.size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i).Name;
    }

    @Override
    public Object getChild(int i, int i1) {
        return listDataHeader.get(i).SubCategories.get(i1).Name; // i group item , i1 child item
        }



    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.parent,null);
        }
        TextView lbListHeader = view.findViewById(R.id.txt_parent);
        lbListHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String)getChild(i,i1);
        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.child,null);
        }
        TextView txtListChild = view.findViewById(R.id.txt_child);
        txtListChild.setText(childText);
        return view;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


}

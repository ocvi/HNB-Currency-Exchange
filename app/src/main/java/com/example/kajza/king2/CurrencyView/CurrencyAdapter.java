package com.example.kajza.king2.CurrencyView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kajza.king2.Currency.CurrencyExchange;
import com.example.kajza.king2.R;

import java.util.ArrayList;


public class CurrencyAdapter extends ArrayAdapter<CurrencyExchange> {

    int Resource;
    ViewHolder holder;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<CurrencyExchange> currencyList;

    public CurrencyAdapter(Context context, int resource, ArrayList<CurrencyExchange> list) {
        super(context, resource, list);
        this.context = context;
        this.currencyList = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View conveertView, ViewGroup parent) {

        //convert view = dizajn
        View v = conveertView;
        if (v == null) {
            holder = new ViewHolder();
            v = layoutInflater.inflate(R.layout.currency_item, null);
            holder.tvValuta = (TextView) v.findViewById(R.id.tvValuta);
            holder.tvDrzava = (TextView) v.findViewById(R.id.tvDrzava);
            holder.tvBuyRate = (TextView) v.findViewById(R.id.tvBuyRate);
            holder.tvSellRate = (TextView) v.findViewById(R.id.tvSellRate);
            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.tvValuta.setText(currencyList.get(position).getValuta());
        holder.tvDrzava.setText(currencyList.get(position).getDrzava());
        holder.tvSellRate.setText(currencyList.get(position).getProdajni_tecaj());
        holder.tvBuyRate.setText(currencyList.get(position).getKupovni_tecaj());
        //holder.tvBuyRate.setText(Double.toString(currencyList.get(position).getKupovni_tecaj()));
        // holder.tvSellRate.setText(Double.toString(currencyList.get(position).getProdajni_tecaj()));
        return v;


    }

    static class ViewHolder {

        public TextView tvValuta;
        public TextView tvDrzava;
        public TextView tvBuyRate;
        public TextView tvSellRate;


    }
}

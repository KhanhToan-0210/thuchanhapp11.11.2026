package com.example.thchnhapp11112026;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private int resource;
    private List<Product> products;
    private DecimalFormat df = new DecimalFormat("#,###");

    public ProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
        this.context = context;
        this.resource = resource;
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Product p = products.get(position);

        TextView tvName = convertView.findViewById(R.id.tvItemName);
        TextView tvPrice = convertView.findViewById(R.id.tvItemPrice);
        TextView tvCategory = convertView.findViewById(R.id.tvItemCategory);
        TextView tvQuantity = convertView.findViewById(R.id.tvItemQuantity);
        TextView tvWarning = convertView.findViewById(R.id.tvItemWarning);

        tvName.setText(p.getName());
        tvPrice.setText("Giá: " + df.format(p.getPrice()) + " đ");
        tvCategory.setText("Loại: " + p.getCategory());
        tvQuantity.setText("SL: " + p.getQuantity());

        if (p.getQuantity() < 5) {
            tvWarning.setVisibility(View.VISIBLE);
            tvQuantity.setTextColor(0xFFFF0000); // Red
        } else {
            tvWarning.setVisibility(View.GONE);
            tvQuantity.setTextColor(0xFF000000); // Black
        }

        return convertView;
    }
}
package com.example.thchnhapp11112026;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView tvNameCat, tvPrice, tvQuantityValue, tvDesc;
    private Button btnAdd1, btnSell1, btnDelete, btnBack;
    private DatabaseHelper dbHelper;
    private int productId = -1;
    private Product currentProduct;
    private DecimalFormat df = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvNameCat = findViewById(R.id.tvDetailNameCat);
        tvPrice = findViewById(R.id.tvDetailPrice);
        tvQuantityValue = findViewById(R.id.tvDetailQuantityValue);
        tvDesc = findViewById(R.id.tvDetailDesc);

        btnAdd1 = findViewById(R.id.btnAdd1);
        btnSell1 = findViewById(R.id.btnSell1);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);

        dbHelper = new DatabaseHelper(this);

        if (getIntent() != null && getIntent().hasExtra("PRODUCT_ID")) {
            productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        }

        if (productId != -1) {
            loadProduct();
        } else {
            Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentProduct != null) {
                    int newQuantity = currentProduct.getQuantity() + 1;
                    dbHelper.updateQuantity(productId, newQuantity);
                    loadProduct();
                }
            }
        });

        btnSell1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentProduct != null) {
                    if (currentProduct.getQuantity() > 0) {
                        int newQuantity = currentProduct.getQuantity() - 1;
                        dbHelper.updateQuantity(productId, newQuantity);
                        loadProduct();
                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Hết hàng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteProduct(productId);
                Toast.makeText(ProductDetailActivity.this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadProduct() {
        currentProduct = dbHelper.getProductById(productId);
        if (currentProduct != null) {
            tvNameCat.setText(currentProduct.getName() + " - " + currentProduct.getCategory());
            tvPrice.setText("Giá bán: " + df.format(currentProduct.getPrice()) + " đ");

            long value = (long) currentProduct.getPrice() * currentProduct.getQuantity();
            tvQuantityValue.setText("Số lượng: " + currentProduct.getQuantity() + " | Giá trị: " + df.format(value) + " đ");

            if (currentProduct.getQuantity() < 5) {
                tvQuantityValue.setTextColor(0xFFFF0000); // Red
            } else {
                tvQuantityValue.setTextColor(0xFF000000); // Black
            }

            tvDesc.setText("Mô tả: " + (currentProduct.getDescription() != null ? currentProduct.getDescription() : ""));
        }
    }
}
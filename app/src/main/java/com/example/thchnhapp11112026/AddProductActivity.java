package com.example.thchnhapp11112026;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {

    private EditText etName, etPrice, etQuantity, etDescription;
    private Spinner spinnerCategory;
    private Switch switchActive;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etQuantity = findViewById(R.id.etQuantity);
        etDescription = findViewById(R.id.etDescription);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        switchActive = findViewById(R.id.switchActive);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        String[] categories = {"Điện tử", "Thực phẩm", "Văn phòng phẩm", "Gia dụng", "Khác"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String priceStr = etPrice.getText().toString().trim();
                String quantityStr = etQuantity.getText().toString().trim();
                String category = spinnerCategory.getSelectedItem().toString();
                int active = switchActive.isChecked() ? 1 : 0;
                String description = etDescription.getText().toString().trim();

                if (name.isEmpty()) {
                    etName.setError("Không được để trống");
                    return;
                }
                if (priceStr.isEmpty()) {
                    etPrice.setError("Không được để trống");
                    return;
                }
                if (quantityStr.isEmpty()) {
                    etQuantity.setError("Không được để trống");
                    return;
                }

                int price, quantity;
                try {
                    price = Integer.parseInt(priceStr);
                    if (price <= 0) {
                        etPrice.setError("Giá bán phải lớn hơn 0");
                        return;
                    }
                } catch (NumberFormatException e) {
                    etPrice.setError("Giá bán không hợp lệ");
                    return;
                }

                try {
                    quantity = Integer.parseInt(quantityStr);
                    if (quantity < 0) {
                        etQuantity.setError("Số lượng không được âm");
                        return;
                    }
                } catch (NumberFormatException e) {
                    etQuantity.setError("Số lượng không hợp lệ");
                    return;
                }

                Product p = new Product();
                p.setName(name);
                p.setPrice(price);
                p.setQuantity(quantity);
                p.setCategory(category);
                p.setActive(active);
                p.setDescription(description);

                long id = dbHelper.insertProduct(p);
                if (id > 0) {
                    Toast.makeText(AddProductActivity.this, "Đã thêm sản phẩm", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddProductActivity.this, "Lỗi khi thêm sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
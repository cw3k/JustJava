package com.example.android.justjava_2; /**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    public int quantity = 1;
    public int initialPrice = 5;
    public int pricePerCup = initialPrice;
    String name = "";
    Boolean chocTopping = false;
    Boolean whippedCream = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantity(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        displayQuantity(quantity);
        displayOrderTotal(findViewById(R.id.orderSummary_text_view));
        composeEmail(("Just Java order for " + name));
    }

    public void composeEmail(String subject) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_TEXT, displayOrderTotal(findViewById(R.id.orderSummary_text_view)));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    /**
     * increases quantity by 1
     *
     * @param view
     */
    public void increment(View view) {
        if(quantity < 100){
        quantity++;
        displayQuantity(quantity);
        } else {
            Toast.makeText(this, "Cannot order more than 100 cups of coffee in 1 order.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * decrease quantity by 1
     *
     * @param view
     */
    public void decrement(View view) {
        if(quantity > 1) {
            quantity--;
            displayQuantity(quantity);
        } else {
            Toast.makeText(this, "Cannot order less than 1 cup of coffee.", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }

    /**
     * This method displays the given pricePerCup on the screen.
     */
    private String displayOrderTotal(View view) {
        toppingsChecked();
        String message = "Name: " + getName()
                + "\nAdd whipped cream: " + whippedCream
                + "\nAdd chocolate: " + chocTopping
                + "\nQuantity: " + quantity
                + "\nTotal: $" + (calculatePrice())
                + "\nThank you!";

        displayMessage(message);
        return message;
    }

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.orderSummary_text_view);
        priceTextView.setText(message);
    }

    private void toppingsChecked() {
        CheckBox whipCheckBox = (CheckBox) findViewById(R.id.whippedCreamCheck);
        CheckBox chocCheckBox = (CheckBox) findViewById(R.id.chocCheck);
        if (whipCheckBox.isChecked()) {
            whippedCream = true;
        } else {
            whippedCream = false;
        }

        if (chocCheckBox.isChecked()) {
            chocTopping = true;
        } else {
            chocTopping = false;
        }
    }

    private String getName() {
        EditText userNameInput = (EditText) findViewById(R.id.userName);
        name = userNameInput.getText().toString();
        return name;
    }

    private int calculatePrice() {
        int totalPrice = initialPrice;
        if (whippedCream) {
            totalPrice += 1;}
        if (chocTopping) {
            totalPrice += 2;}

        pricePerCup = totalPrice;
        return pricePerCup * quantity;
    }

}
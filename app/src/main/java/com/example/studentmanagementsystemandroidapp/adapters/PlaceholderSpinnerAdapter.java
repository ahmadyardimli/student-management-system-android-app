package com.example.studentmanagementsystemandroidapp.adapters;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.custom.spinners.CustomSpinner;
import com.example.studentmanagementsystemandroidapp.interfaces.ItemNameProvider;
import java.util.List;

// This adapter provides a dropdown spinner where:
    // The spinner always has an extra item for "No selection" at position 0.
    // Selecting "No selection" reverts the spinner to display the placeholder text.
    // Selecting a real item displays its text in the spinner.
public class PlaceholderSpinnerAdapter<T> extends BaseSpinnerAdapter<T> {

    private final String placeholderText;
    private final ItemNameProvider<T> itemNameProvider;
    private String noSelectionText = "No selection";

    public PlaceholderSpinnerAdapter(
            Context context,
            List<T> items,
            double parentHeight,
            double spinnerItemTextPercentage,
            CustomSpinner currentSpinner,
            String placeholderText,
            ItemNameProvider<T> itemNameProvider
    ) {
        // I pass `null` for the SpecialSpinnerItemClickListener and other flags
        // because I am not using the "special item" feature from BaseSpinnerAdapter.
        // Also, I pass `selectedItemId` = null since I am not pre-selecting an item by ID.
        super(context, items, parentHeight, spinnerItemTextPercentage,
                null,         // specialItemClickListener
                currentSpinner,
                false,        // allowEmptyOption (I do my own "No selection" logic here)
                false,        // showSpecialItem   (I do not have an extra "Add" item)
                null          // selectedItemId
        );

        this.placeholderText = placeholderText;
        this.itemNameProvider = itemNameProvider;
    }

    public void setNoSelectionText(String noSelectionText) {
        this.noSelectionText = noSelectionText;
        notifyDataSetChanged();
    }

    // Override the base methods to insert the "No selection" item logic
    // at position 0.
    @Override
    public int getCount() {
        // I add one extra item: position 0 - "No selection".
        return super.getCount() + 1;
    }

    @Override
    public T getItem(int position) {
        // If user picks position 0, that’s "No selection" slot => null
        // Otherwise, shift by -1 to get the real item from the super list.
        if (position == 0) {
            return null;
        }
        return super.getItem(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position - 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Use a simple default layout or any my own
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        // Reuse BaseSpinnerAdapter’s dynamic sizing
        setDynamicTextSize(textView);
        // Vertically center, left-align horizontally
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

        if (position == 0) {
            // Show the placeholder text
            textView.setText(placeholderText);
            textView.setTextColor(Color.GRAY);
            Log.d("PlaceholderSpinner", "getView: position=0 => placeholder");
        } else {
            // Show the selected item’s text
            T item = super.getItem(position - 1);
            textView.setText(getItemText(item));
            textView.setTextColor(Color.BLACK);
            Log.d("PlaceholderSpinner", "getView: position=" + position + ", " +
                    "item=" + getItemText(item));
        }

        return convertView;
    }

    private void setDynamicTextSize(TextView textView) {
        float dynamicTextSize = (float) (parentHeight * spinnerItemTextPercentage * 0.9);  // Scale down by 0.9 to fit
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dynamicTextSize);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Use my custom spinner dropdown item layout
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.custom_spinner_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.text1);
        setDynamicHeightAndTextSize(convertView, textView);

        if (position == 0) {
            // The "No selection" item
            textView.setText(noSelectionText);
            textView.setTextColor(Color.GRAY);
            Log.d("PlaceholderSpinner", "getDropDownView: position=0 => noSelectionText");
        } else {
            // The real item
            T item = super.getItem(position - 1);
            textView.setText(getItemText(item));
            textView.setTextColor(Color.BLACK);
            Log.d("PlaceholderSpinner", "getDropDownView: position=" + position +
                    ", item=" + getItemText(item));
        }

        return convertView;
    }

    @Override
    protected String getItemText(T item) {
        // If the item is not null, get its name from the provider
        return (item != null && itemNameProvider != null)
                ? itemNameProvider.getItemName(item)
                : "";
    }

    @Override
    protected int getItemUniqueId(T item) {
        // For demonstration, always return -1 or item.hashCode().
        return (item != null) ? item.hashCode() : -1;
    }
}

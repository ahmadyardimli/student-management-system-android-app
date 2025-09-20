package com.example.studentmanagementsystemandroidapp.utils;
import com.example.studentmanagementsystemandroidapp.adapters.CommonSpinnerAdapter;
import com.example.studentmanagementsystemandroidapp.custom.spinners.CustomSpinner;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;
import java.util.List;

public class SpinnerUtils {
    public static <T extends RecyclerViewItemPositionable> void updateSpinnerAdapter(
            CustomSpinner spinner,
            List<T> items,
            boolean allowEmptyOption,
            int containerHeight,
            double textSizePct) {

        Integer previousSelectedId = null;
        T previousItem = (T) spinner.getSelectedItem();
        if (previousItem != null) {
            previousSelectedId = previousItem.getItemId();
        }

        // Build a new adapter with the updated items.
        CommonSpinnerAdapter<T> adapter = new CommonSpinnerAdapter<>(
                spinner.getContext(),
                items,
                containerHeight,
                textSizePct,
                null,
                spinner,
                allowEmptyOption,
                true,
                previousSelectedId
        );
        spinner.setAdapter(adapter);

        // Restore selection: the adapter can determine which item should be selected
        int pos = adapter.getPositionOfSelectedItem();
        if (pos >= 0) {
            spinner.setSelection(pos);
        } else if (allowEmptyOption) {
            spinner.setSelection(0);
        }
    }
}

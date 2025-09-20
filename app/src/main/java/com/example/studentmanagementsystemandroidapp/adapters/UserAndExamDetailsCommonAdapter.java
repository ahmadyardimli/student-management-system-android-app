package com.example.studentmanagementsystemandroidapp.adapters;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;
import com.example.studentmanagementsystemandroidapp.utils.ScreenUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

import java.util.ArrayList;
import java.util.List;

public class UserAndExamDetailsCommonAdapter<T extends RecyclerViewItemPositionable>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // a second view type for “no data”
    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_NO_DATA = 1;

    private List<T> items;
    private OnUpdateClickListener onUpdateClickListener;
    private int itemLayoutId;
    private UserAndExamDetailsBtnVisibility buttonVisibility = UserAndExamDetailsBtnVisibility.SHOW_BOTH;

    // holds a message if no data (or error)
    private String noDataMessage;

    // Optional override for the "Update" button text (e.g., set to "Details")
    private CharSequence updateButtonTextOverride;

    // Provide a method to set the listener from outside
    public void setOnUpdateClickListener(OnUpdateClickListener onUpdateClickListener) {
        this.onUpdateClickListener = onUpdateClickListener;
    }

    public UserAndExamDetailsCommonAdapter(List<T> items, int itemLayoutId) {
        this.items = (items != null ? items : new ArrayList<>());
        this.itemLayoutId = itemLayoutId;
    }

    // setter for noDataMessage
    public void setNoDataMessage(String message) {
        this.noDataMessage = message;
        // items.clear();
        notifyDataSetChanged();
    }

    // If want to “reset” the noDataMessage once have real data
    public void clearNoDataMessage() {
        this.noDataMessage = null;
        notifyDataSetChanged();
    }

    public void setButtonVisibility(UserAndExamDetailsBtnVisibility visibility) {
        this.buttonVisibility = visibility;
        notifyDataSetChanged();
    }

    // Set a custom text for the Update button (e.g., "Details")
    public void setUpdateButtonText(CharSequence text) {
        this.updateButtonTextOverride = text;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // If items are empty, but have a noDataMessage, show 1
        if ((items == null || items.isEmpty()) && noDataMessage != null) {
            return 1;
        }
        return (items != null) ? items.size() : 0;
    }

    // Decide which view type to use
    @Override
    public int getItemViewType(int position) {
        // If have no real items AND have a noDataMessage => show the “no data” layout
        if ((items == null || items.isEmpty()) && noDataMessage != null) {
            return VIEW_TYPE_NO_DATA;
        }
        return VIEW_TYPE_NORMAL;
    }

    // inflate either normal layout or no-data layout
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NO_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_no_data, parent, false
            );
            return new NoDataViewHolder(view);
        } else {
            // Normal item
            View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
            return new NormalViewHolder(view, onUpdateClickListener, parent.getContext());
        }
    }

    // Bind either the normal item or the “no data” item
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // If it’s a no-data item, bind the message
        if (holder instanceof NoDataViewHolder) {
            ((NoDataViewHolder) holder).bind(noDataMessage);
        }
        else if (holder instanceof NormalViewHolder) {
            if (items != null && !items.isEmpty() && items.get(position) != null) {
                RecyclerViewItemPositionable item = items.get(position);
                ((NormalViewHolder) holder).bind(item, position, buttonVisibility, updateButtonTextOverride);
            } else {
                Log.e("Adapter Error", "Item list is empty or contains null values.");
            }
        }
    }

    // Define the interface for the listener
    public interface OnUpdateClickListener {
        void onUpdateClick(int position);
        void onDeleteClick(int position);
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView textViewNumber;
        TextView textViewUpdate;
        TextView textViewDelete;
        View backgroundView;
        OnUpdateClickListener onUpdateClickListener;
        LinearLayout dynamicAdapterItemsContainer;

        public NormalViewHolder(@NonNull View itemView, OnUpdateClickListener onUpdateClickListener, Context context) {
            super(itemView);
            this.onUpdateClickListener = onUpdateClickListener;
            this.context = context;

            backgroundView = itemView.findViewById(R.id.parentLayout);
            textViewNumber = itemView.findViewById(R.id.textViewNumberItem);
            textViewUpdate = itemView.findViewById(R.id.textViewUpdateItem);
            textViewDelete = itemView.findViewById(R.id.textViewDeleteItem);
            dynamicAdapterItemsContainer = itemView.findViewById(R.id.dynamicAdapterItemsContainer);

            int padding = ScreenUtils.calculateHeightWithPercentage(context, 0.015);
            backgroundView.setPadding(0, padding, padding, padding);

            // Set an OnClickListener for the update button
            textViewUpdate.setOnClickListener(v -> {
                if (onUpdateClickListener != null) {
                    onUpdateClickListener.onUpdateClick(getAdapterPosition());
                } else {
                    Log.e("ViewHolder", "onUpdateClickListener is null");
                }
            });

            // Set an OnClickListener for the delete button
            textViewDelete.setOnClickListener(v -> {
                if (onUpdateClickListener != null) {
                    onUpdateClickListener.onDeleteClick(getAdapterPosition());
                } else {
                    Log.e("ViewHolder", "onUpdateClickListener is null");
                }
            });
        }

        public void bind(RecyclerViewItemPositionable item,
                         int position,
                         UserAndExamDetailsBtnVisibility buttonVisibility,
                         @Nullable CharSequence updateTextOverride) {
            // Show/hide update/delete based on buttonVisibility
            switch (buttonVisibility) {
                case SHOW_BOTH:
                    textViewUpdate.setVisibility(View.VISIBLE);
                    textViewDelete.setVisibility(View.VISIBLE);
                    break;
                case HIDE_UPDATE:
                    textViewUpdate.setVisibility(View.GONE);
                    textViewDelete.setVisibility(View.VISIBLE);
                    break;
                case HIDE_DELETE:
                    textViewUpdate.setVisibility(View.VISIBLE);
                    textViewDelete.setVisibility(View.GONE);
                    break;
                case HIDE_BOTH:
                    textViewUpdate.setVisibility(View.GONE);
                    textViewDelete.setVisibility(View.GONE);
                    break;
            }

            // Apply override text if provided and the view is visible
            if (updateTextOverride != null && textViewUpdate.getVisibility() == View.VISIBLE) {
                textViewUpdate.setText(updateTextOverride);
            }

            textViewNumber.setText((position + 1) + ".");

            // Create item rows dynamically
            List<View> requestItemViewList = new ArrayList<>();
            for (int i = 0; i < item.getRequestFieldInfo().size(); i++) {
                final View itemNameView = LayoutInflater.from(context)
                        .inflate(R.layout.adapter_item_name_design, dynamicAdapterItemsContainer, false);
                final TextView textViewItem = itemNameView.findViewById(R.id.textViewAdapterItemName);

                String label = item.getRequestFieldInfo().get(i).getItemLabel();
                String value = item.getRequestFieldInfo().get(i).getItemValue();

                // optional color formatting
                SpannableString spannableString = new SpannableString(label + ": " + value);
                int labelColor = ContextCompat.getColor(context, R.color.black);
                int valueColor = ContextCompat.getColor(context, R.color.sms_blue_pressed);
                spannableString.setSpan(new ForegroundColorSpan(labelColor),
                        0,
                        label.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(valueColor),
                        label.length() + 1,
                        label.length() + 1 + value.length() + 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                textViewItem.setText(spannableString);

                requestItemViewList.add(itemNameView);
            }

            backgroundView.post(() -> {
                dynamicAdapterItemsContainer.removeAllViews();

                float screenHeight = ScreenUtils.getDeviceScreenHeight(context);
                float backgroundViewHeight = screenHeight;

                float textSizePercentage = 0.02f;

                for (View view : requestItemViewList) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );

                    TextView adapterRequestItemTextView = view.findViewById(R.id.textViewAdapterItemName);
                    adapterRequestItemTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            backgroundViewHeight * textSizePercentage);

                    LinearLayout requestItemViewContainer = new LinearLayout(context);
                    requestItemViewContainer.setLayoutParams(layoutParams);
                    requestItemViewContainer.addView(view);
                    dynamicAdapterItemsContainer.addView(requestItemViewContainer);
                }

                textViewNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        backgroundViewHeight * textSizePercentage);

                float textSizePercentageUpdateAndDelete = 0.023f;
                textViewUpdate.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        backgroundViewHeight * textSizePercentageUpdateAndDelete);
                textViewDelete.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        backgroundViewHeight * textSizePercentageUpdateAndDelete);
            });
        }
    }

    public static class NoDataViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNoDataMessage;

        public NoDataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoDataMessage = itemView.findViewById(R.id.tvNoDataMessage);
        }

        public void bind(String message) {
            tvNoDataMessage.setText(message);
            // until after the view is measured:
            itemView.post(() -> {
                ViewStyler.setTextSizeByScreenHeight(tvNoDataMessage, itemView.getContext(), 0.03);
            });
        }
    }
}
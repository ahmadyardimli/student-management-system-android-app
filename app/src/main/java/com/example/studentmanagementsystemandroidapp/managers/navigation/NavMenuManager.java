package com.example.studentmanagementsystemandroidapp.managers.navigation;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.managers.sharedpreferences.SharedPreferenceManager;
import com.google.android.material.navigation.NavigationView;

public class NavMenuManager {
    private Activity activity;
    private NavMenuItemStyler navMenuItemStyler;
    private NavMenuItemClickListener onMenuItemClickListener;

    public void setOnMenuItemClickListener(NavMenuItemClickListener listener) {
        this.onMenuItemClickListener = listener;
    }

    public NavMenuManager(Activity activity) {
        this.activity = activity;
        this.navMenuItemStyler = new NavMenuItemStyler(activity);
    }

    public void handleMenuItemClick(MenuItem item, String title){
        // Set click listener for the dynamically added menu item
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Handle the click action based on the title
                if (onMenuItemClickListener != null) {
                    onMenuItemClickListener.onMenuItemClick(title);
                    SharedPreferenceManager.setString(activity,"selected_item", title);
                }
                return true;
            }
        });
    }

    public void addMenuItem(String title, Class<?> activityThatItemBelongsTo) {
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            NavigationView navigationView = appCompatActivity.findViewById(R.id.nav_view);

            if (navigationView != null) {
                Menu menu = navigationView.getMenu();
                MenuItem item = menu.add(title);
//                item.setActionView(R.layout.custom_menu_item);
                Log.d("ITEM TITLE", item.getTitle().toString());


                if (activityThatItemBelongsTo == null) {
                    navMenuItemStyler.applyStyles(item);
                }
                else {
//                    navMenuItemStyler.applyStyles(item);
                    String storedTitle = SharedPreferenceManager.getString(activity, "selected_item", "");
                    Log.d("ITEM TITLE OUT METHOD", item.getTitle().toString());
                    String menuItemsActivityName = activityThatItemBelongsTo.getName();
                    String currentActivityName = activity.getClass().getName();
                    Log.d("2 MENU ITEM'S ACTIVITY NAME: ", menuItemsActivityName);
                    Log.d("2 CURRENT ACTIVITY NAME: ", currentActivityName);

                    if (menuItemsActivityName.equals(currentActivityName)){
                        Log.d("ITEM TITLE IN METHOD", item.getTitle().toString());
//                        if (storedTitle == null){
                            SharedPreferenceManager.setString(activity,"selected_item", item.getTitle().toString());
//                        }
                        navMenuItemStyler.applySelectedItemStyles(item);
                    }
                    else {
                        handleMenuItemClick(item, title);
                        navMenuItemStyler.applyStyles(item);
                        MenuItem storedItem = findMenuItemByTitle(storedTitle);

                        if (storedItem == null){
                            Log.d("STORED ITEM: ", "NULLLLLLLLLLLLL");
                        }
                        if (storedItem != null){
                            Log.d("STORED ITEM: ", storedItem.getTitle().toString());

                            navMenuItemStyler.applySelectedItemStyles(storedItem);
                        }
                    }
                }
            }
        }
    }

    public void clearDefaultMenuItemTitles() {
        NavigationView navigationView =  activity.findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            menuItem.setTitle("");
        }
    }

   private MenuItem findMenuItemByTitle(String title) {
        // Find the menu item with the given title
        NavigationView navigationView =  activity.findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.getTitle().toString().equals(title)) {
                return menuItem;
            }
        }
        return null;
    }

    public void addActionItem(String title, Runnable onClick) {
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity app = (AppCompatActivity) activity;
            NavigationView navigationView = app.findViewById(R.id.nav_view);
            if (navigationView != null) {
                Menu menu = navigationView.getMenu();
                MenuItem item = menu.add(title);

                navMenuItemStyler.applyStyles(item); // sets custom view using the title
                item.setTitle(""); // hide native label to avoid duplicate

                item.setOnMenuItemClickListener(menuItem -> {
                    try { onClick.run(); } catch (Exception ignored) {}
                    return true;
                });
            }
        }
    }

}
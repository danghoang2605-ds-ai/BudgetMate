package com.example.savemoneytime.MainApplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import com.example.savemoneytime.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.activity.OnBackPressedCallback;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    // Five tabs plus the receipt scanner. After a configuration change (font size, theme,
    // language) the FragmentManager restores these by tag, so they must not be added again.
    private static final String KEY_ACTIVE = "active_tab";
    private Fragment homeFragment, manageFragment, chatFragment, statsFragment, newsFragment, scanFragment;

    private Fragment activeFragment;
    private Fragment tabBeforeScanner;
    private FragmentManager fm;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(0xFF0A1128);
        getWindow().setNavigationBarColor(0xFF0B1226);

        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);

        fm = getSupportFragmentManager();

        if (savedInstanceState != null) {
            homeFragment   = fm.findFragmentByTag("1");
            manageFragment = fm.findFragmentByTag("2");
            chatFragment   = fm.findFragmentByTag("3");
            statsFragment  = fm.findFragmentByTag("4");
            newsFragment   = fm.findFragmentByTag("5");
            scanFragment   = fm.findFragmentByTag("scan");
        }
        boolean restored = savedInstanceState != null && homeFragment != null && manageFragment != null
                && chatFragment != null && statsFragment != null && newsFragment != null && scanFragment != null;
        if (restored) {
            Fragment active = fm.findFragmentByTag(savedInstanceState.getString(KEY_ACTIVE, "1"));
            activeFragment = active != null ? active : homeFragment;
        } else {
            homeFragment   = new HomeFragment();
            manageFragment = new ManageFragment();
            chatFragment   = new ChatbotFragment();
            statsFragment  = new StatsFragment();
            newsFragment   = new NewsFragment();
            scanFragment   = new ScannerFragment();
            activeFragment = homeFragment;
            addAllFragments();
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                } else if (activeFragment == scanFragment) {
                    // Back from the receipt scanner used to close the whole app.
                    closeScanner();
                } else if (activeFragment != homeFragment) {
                    bottomNav.setSelectedItemId(R.id.nav_home);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment selectedFragment = null;

                if (id == R.id.nav_home) {
                    selectedFragment = homeFragment;
                } else if (id == R.id.nav_manage) {
                    selectedFragment = manageFragment;
                } else if (id == R.id.nav_chat) {
                    selectedFragment = chatFragment;
                } else if (id == R.id.nav_stats) {
                    selectedFragment = statsFragment;
                } else if (id == R.id.nav_news) {
                    selectedFragment = newsFragment;
                }

                if (selectedFragment != null && selectedFragment != activeFragment) {
                    switchFragment(selectedFragment);
                    return true;
                }
                return false;
            }
        });
    }

    private void addAllFragments() {
        fm.beginTransaction()
                .add(R.id.fragment_container, scanFragment, "scan").hide(scanFragment).setMaxLifecycle(scanFragment, Lifecycle.State.STARTED)
                .add(R.id.fragment_container, newsFragment, "5").hide(newsFragment).setMaxLifecycle(newsFragment, Lifecycle.State.STARTED)
                .add(R.id.fragment_container, statsFragment, "4").hide(statsFragment).setMaxLifecycle(statsFragment, Lifecycle.State.STARTED)
                .add(R.id.fragment_container, chatFragment, "3").hide(chatFragment).setMaxLifecycle(chatFragment, Lifecycle.State.STARTED)
                .add(R.id.fragment_container, manageFragment, "2").hide(manageFragment).setMaxLifecycle(manageFragment, Lifecycle.State.STARTED)
                .add(R.id.fragment_container, homeFragment, "1").setMaxLifecycle(homeFragment, Lifecycle.State.RESUMED)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (activeFragment != null && activeFragment.getTag() != null) {
            outState.putString(KEY_ACTIVE, activeFragment.getTag());
        }
    }

    private void switchFragment(Fragment target) {
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .hide(activeFragment)
                .setMaxLifecycle(activeFragment, Lifecycle.State.STARTED)
                .show(target)
                .setMaxLifecycle(target, Lifecycle.State.RESUMED)
                .commit();
        activeFragment = target;
    }

    /** Opens the receipt scanner over the current tab (Home or AI Chat). */
    public void openScannerFromChat() {
        if (activeFragment != scanFragment) tabBeforeScanner = activeFragment;
        switchFragment(scanFragment);
    }

    /**
     * Leaves the scanner for the tab that opened it. The bottom bar never changed while the
     * scanner was up, so it still highlights that tab. Used by Cancel and the Back gesture.
     */
    public void closeScanner() {
        Fragment back = tabBeforeScanner != null ? tabBeforeScanner : homeFragment;
        if (activeFragment != back) switchFragment(back);
    }

    /** After a scan is saved, show Home so the new balance is visible. */
    public void returnToHomeFromScanner() {
        // Selecting the item runs the nav listener, which performs the switch.
        if (bottomNav.getSelectedItemId() == R.id.nav_home) {
            if (activeFragment != homeFragment) switchFragment(homeFragment);
        } else {
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
    }

    /** "Edit" on a scanned receipt: open Home with the amount and note on the keypad. */
    public void editScannedExpenseOnHome(long amount, String note) {
        returnToHomeFromScanner();
        if (homeFragment instanceof HomeFragment) ((HomeFragment) homeFragment).prefillExpense(amount, note);
    }
}

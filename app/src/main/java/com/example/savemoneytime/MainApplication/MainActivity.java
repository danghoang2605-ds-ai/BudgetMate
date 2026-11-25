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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    // Five tabs plus the receipt scanner. After a configuration change (font size, theme,
    // language) the FragmentManager restores these by tag, so they must not be added again.
    private static final String KEY_ACTIVE = "active_tab";
    private Fragment homeFragment, manageFragment, chatFragment, statsFragment, newsFragment, scanFragment;

    private Fragment activeFragment;
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

    /**
     * Hàm điều hướng mở màn hình Camera Quét hóa đơn được kích hoạt từ icon trong đoạn chat
     */
    public void openScannerFromChat() {
        switchFragment(scanFragment);
    }

    /**
     * Hàm quay trở lại màn hình Chatbot sau khi người dùng nhấn Hủy/Cancel quét hóa đơn
     */
    public void returnToChatFromScanner() {
        bottomNav.setSelectedItemId(R.id.nav_chat);
        switchFragment(chatFragment);
    }

    /**
     * Hàm điều hướng nhảy hẳn về màn hình Home sau khi lưu thành công hóa đơn đã quét
     */
    public void returnToHomeFromScanner() {
        bottomNav.setSelectedItemId(R.id.nav_home);
        switchFragment(homeFragment);
    }
}
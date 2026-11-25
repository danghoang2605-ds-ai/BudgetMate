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

    // Khởi tạo giữ trạng thái cho 5 màn hình tab và 1 màn hình scan độc lập
    private final Fragment homeFragment   = new HomeFragment();
    private final Fragment manageFragment = new ManageFragment();
    private final Fragment chatFragment   = new ChatbotFragment();
    private final Fragment statsFragment  = new StatsFragment();
    private final Fragment newsFragment   = new NewsFragment();
    private final Fragment scanFragment   = new ScannerFragment(); // Chạy ngầm phục vụ nút Camera

    private Fragment activeFragment = homeFragment;
    private FragmentManager fm;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(0xFF0A1128);
        getWindow().setNavigationBarColor(0xFF111827);

        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);

        fm = getSupportFragmentManager();

        // Nạp sẵn toàn bộ các cấu phần vào cây Fragment ngầm bảo toàn bộ nhớ
        fm.beginTransaction()
                .add(R.id.fragment_container, scanFragment, "scan").hide(scanFragment).setMaxLifecycle(scanFragment, Lifecycle.State.STARTED)
                .add(R.id.fragment_container, newsFragment, "5").hide(newsFragment).setMaxLifecycle(newsFragment, Lifecycle.State.STARTED)
                .add(R.id.fragment_container, statsFragment, "4").hide(statsFragment).setMaxLifecycle(statsFragment, Lifecycle.State.STARTED)
                .add(R.id.fragment_container, chatFragment, "3").hide(chatFragment).setMaxLifecycle(chatFragment, Lifecycle.State.STARTED)
                .add(R.id.fragment_container, manageFragment, "2").hide(manageFragment).setMaxLifecycle(manageFragment, Lifecycle.State.STARTED)
                .add(R.id.fragment_container, homeFragment, "1").setMaxLifecycle(homeFragment, Lifecycle.State.RESUMED)
                .commit();

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
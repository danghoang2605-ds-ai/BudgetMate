#!/usr/bin/env bash
# Installs the debug APK on a running emulator, seeds demo data and captures each tab.
# Used by .github/workflows/screenshots.yml; also works locally with one emulator attached.
set -euo pipefail

PKG=com.example.savemoneytime
OUT=${1:-docs/screenshots}
mkdir -p "$OUT"
# Start clean so a failed capture can never leave a stale screenshot behind.
# qa/ holds review-only shots (first run, scanner); the website only uses the top level.
mkdir -p "$OUT/qa"
rm -f "$OUT"/*.png "$OUT"/qa/*.png

adb wait-for-device
adb shell 'while [ "$(getprop sys.boot_completed)" != "1" ]; do sleep 1; done'
adb install -r -g app/build/outputs/apk/debug/app-debug.apk

# Emulators on CI often raise "Pixel Launcher isn't responding"; never let a system dialog
# land in a screenshot or swallow a tap.
adb shell settings put global hide_error_dialogs 1 || true
adb shell settings put global anr_show_background 0 || true
sleep 15
adb shell am broadcast -a android.intent.action.CLOSE_SYSTEM_DIALOGS >/dev/null || true

# Clean status bar (fixed clock, full battery, no notifications).
adb shell settings put global sysui_demo_allowed 1
adb shell am broadcast -a com.android.systemui.demo -e command enter >/dev/null
adb shell am broadcast -a com.android.systemui.demo -e command clock -e hhmm 0941 >/dev/null
adb shell am broadcast -a com.android.systemui.demo -e command battery -e level 100 -e plugged false >/dev/null
adb shell am broadcast -a com.android.systemui.demo -e command notifications -e visible false >/dev/null
adb shell am broadcast -a com.android.systemui.demo -e command network -e wifi show -e level 4 >/dev/null


shot() {
  sleep 3
  adb shell am broadcast -a android.intent.action.CLOSE_SYSTEM_DIALOGS >/dev/null || true
  sleep 1
  adb exec-out screencap -p > "$OUT/$1.png"
  echo "captured $1"
}

tap_id() {
  # Find the view's bounds in a UI Automator dump and tap its centre.
  adb shell uiautomator dump /sdcard/ui.xml >/dev/null
  local bounds
  bounds=$(adb shell cat /sdcard/ui.xml | tr '>' '\n' | grep "resource-id=\"$PKG:id/$1\"" | head -1 \
    | sed -E 's/.*bounds="\[([0-9]+),([0-9]+)\]\[([0-9]+),([0-9]+)\]".*/\1 \2 \3 \4/')
  if [ -z "$bounds" ]; then
    # One retry after closing any system dialog that may cover the app.
    adb shell am broadcast -a android.intent.action.CLOSE_SYSTEM_DIALOGS >/dev/null || true
    sleep 2
    adb shell uiautomator dump /sdcard/ui.xml >/dev/null
    bounds=$(adb shell cat /sdcard/ui.xml | tr '>' '\n' | grep "resource-id=\"$PKG:id/$1\"" | head -1 \
      | sed -E 's/.*bounds="\[([0-9]+),([0-9]+)\]\[([0-9]+),([0-9]+)\]".*/\1 \2 \3 \4/')
  fi
  if [ -z "$bounds" ]; then echo "::error::view $1 not found"; exit 1; fi
  read -r x1 y1 x2 y2 <<<"$bounds"
  adb shell input tap $(((x1 + x2) / 2)) $(((y1 + y2) / 2))
}

# First run, as a new user sees it: splash, then onboarding (nothing seeded yet).
adb shell am start -W -n "$PKG/.Intro.SplashScreen" >/dev/null
adb exec-out screencap -p > "$OUT/qa/splash.png"
echo "captured qa/splash"
sleep 3
shot qa/onboarding-1
for _ in 1 2 3; do tap_id btn_next; sleep 1; done
shot qa/onboarding-4
adb shell am force-stop "$PKG"

adb shell am start -W -n "$PKG/.debug.DemoSeedActivity" >/dev/null
sleep 6

shot home
for tab in manage stats chat news; do
  tap_id "nav_$tab"
  shot "$tab"
done

# Accessibility check: Home at 130% system font size (not used on the website).
adb shell settings put system font_scale 1.3
sleep 3
tap_id nav_home
shot home-font130
adb shell settings put system font_scale 1.0

# Receipt scanner: Back and Cancel must return to the tab that opened it, not close the app.
expect_view() {
  adb shell uiautomator dump /sdcard/ui.xml >/dev/null
  if ! adb shell cat /sdcard/ui.xml | grep -q "resource-id=\"$PKG:id/$1\""; then
    echo "::error::expected $1 on screen after $2"; exit 1
  fi
  echo "ok: $2"
}
tap_id nav_home
tap_id btn_scan_receipt
shot qa/scanner
adb shell input keyevent KEYCODE_BACK
sleep 2
expect_view btn_scan_receipt "Back from scanner opened on Home"
tap_id nav_chat
tap_id btn_chat_camera_scan
sleep 2
tap_id btn_cancel_scan
sleep 2
expect_view btn_chat_camera_scan "Cancel from scanner opened in AI Chat"
# Back from a tab goes to Home before it leaves the app.
adb shell input keyevent KEYCODE_BACK
sleep 2
expect_view btn_scan_receipt "Back from AI Chat"

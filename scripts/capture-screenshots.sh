#!/usr/bin/env bash
# Installs the debug APK on a running emulator, seeds demo data and captures each tab.
# Used by .github/workflows/screenshots.yml; also works locally with one emulator attached.
set -euo pipefail

PKG=com.example.savemoneytime
OUT=${1:-docs/screenshots}
mkdir -p "$OUT"

adb wait-for-device
adb shell 'while [ "$(getprop sys.boot_completed)" != "1" ]; do sleep 1; done'
adb install -r -g app/build/outputs/apk/debug/app-debug.apk

# Clean status bar (fixed clock, full battery, no notifications).
adb shell settings put global sysui_demo_allowed 1
adb shell am broadcast -a com.android.systemui.demo -e command enter >/dev/null
adb shell am broadcast -a com.android.systemui.demo -e command clock -e hhmm 0941 >/dev/null
adb shell am broadcast -a com.android.systemui.demo -e command battery -e level 100 -e plugged false >/dev/null
adb shell am broadcast -a com.android.systemui.demo -e command notifications -e visible false >/dev/null
adb shell am broadcast -a com.android.systemui.demo -e command network -e wifi show -e level 4 >/dev/null

adb shell am start -W -n "$PKG/.debug.DemoSeedActivity" >/dev/null
sleep 6

shot() {
  sleep 3
  adb exec-out screencap -p > "$OUT/$1.png"
  echo "captured $1"
}

tap_id() {
  # Find the view's bounds in a UI Automator dump and tap its centre.
  adb shell uiautomator dump /sdcard/ui.xml >/dev/null
  local bounds
  bounds=$(adb shell cat /sdcard/ui.xml | tr '>' '\n' | grep "resource-id=\"$PKG:id/$1\"" | head -1 \
    | sed -E 's/.*bounds="\[([0-9]+),([0-9]+)\]\[([0-9]+),([0-9]+)\]".*/\1 \2 \3 \4/')
  if [ -z "$bounds" ]; then echo "view $1 not found"; return 1; fi
  read -r x1 y1 x2 y2 <<<"$bounds"
  adb shell input tap $(((x1 + x2) / 2)) $(((y1 + y2) / 2))
}

shot home
for tab in manage stats chat news; do
  tap_id "nav_$tab" && shot "$tab"
done

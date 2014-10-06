set -e
ant debug
echo ===========================================
ant debug
adb shell am force-stop com.example.CodingChallenge
adb uninstall com.example.CodingChallenge
adb install bin/CodingChallenge-debug-unaligned.apk
adb shell am start -n com.example.CodingChallenge/.MainActivity

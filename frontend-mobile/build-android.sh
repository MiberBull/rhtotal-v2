echo Set Versión of apk
read version
clear && ionic cordova build --release android
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore keys/my-release-key.jks platforms/android/app/build/outputs/apk/release/app-release-unsigned.apk rhtotal
read -s pass
$ANDROID_HOME/build-tools/28.0.3/zipalign -v 4 platforms/android/app/build/outputs/apk/release/app-release-unsigned.apk ../../releases/rhtotal-$version.apk

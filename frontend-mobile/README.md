Ionic 3 Base

Run:
	### NPM BEFORE ANY BUILD ###
		npm install
		npm webpack@^3.1.0 // to make compatible with ionic webscript for ionic3

	### JAVA HOME must be use jdk1.8.0_212###
		export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_212.jdk/Contents/Home/ 

BUILD MOBILE ios and andorid
----------------------------

clear && ionic cordova build ios --prod && cordova build android --prod --release

SING ANDROID APK
----------------------------

jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore keys/my-release-key.jks platforms/android/app/build/outputs/apk/release/app-release-unsigned.apk rhtotal

~/Library/Android/sdk/build-tools/28.0.3/zipalign -v 4 platforms/android/app/build/outputs/apk/release/app-release-unsigned.apk ~/Documents/RHTotal/Dev/Releases/VERSION/rhtotal.apk



IF USE DIFERENTS NODE AND NPM VERSIONS:
---------------------------------------

Install NVM:

	curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.34.0/install.sh | bash

Manage version:
	nvm install 10.16.0
	nvm use 10.16.0



Environment variable

	export NVM_DIR="${XDG_CONFIG_HOME/:-$HOME/.}nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh" # This loads nvm

com-badrit-base64 0.2.0 "Base64"
com.ourcodeworld.plugins.Filebrowser 1.0.0 "Our Code World Filebrowser"
cordova-android-support-gradle-release 1.4.7 "cordova-android-support-gradle-release"
cordova-fabric-plugin 1.1.14-dev "cordova-fabric-plugin"
cordova-plugin-android-permissions 1.0.0 "Permissions"
cordova-plugin-camera 4.1.0 "Camera"
cordova-plugin-device 2.0.3 "Device"
cordova-plugin-dialogs 2.0.2 "Notification"
cordova-plugin-document-viewer 0.9.11 "SitewaertsDocumentViewer"
cordova-plugin-file 6.0.2 "File"
cordova-plugin-file-opener2 2.2.1 "File Opener2"
cordova-plugin-file-transfer 1.7.1 "File Transfer"
cordova-plugin-filechooser 1.2.0 "File Chooser"
cordova-plugin-filepath 1.5.5 "cordova-plugin-filepath"
cordova-plugin-filepicker 1.1.6 "File Picker"
cordova-plugin-inappbrowser 3.1.0 "InAppBrowser"
cordova-plugin-ionic-keyboard 2.1.3 "cordova-plugin-ionic-keyboard"
cordova-plugin-ionic-webview 1.2.1 "cordova-plugin-ionic-webview"
cordova-plugin-local-notification 0.9.0-beta.2 "LocalNotification"
cordova-plugin-network-information 2.0.2 "Network Information"
cordova-plugin-screen-orientation 3.0.2 "Screen Orientation"
cordova-plugin-splashscreen 5.0.3 "Splashscreen"
cordova-plugin-statusbar 2.4.3 "StatusBar"
cordova-plugin-telerik-imagepicker 2.3.3 "ImagePicker"
cordova-plugin-whitelist 1.3.4 "Whitelist"
cordova-plugin-x-toast 2.7.2 "Toast"
cordova-sqlite-storage 2.6.0 "Cordova sqlite storage plugin"
cordova-support-google-services 1.1.0 "cordova-support-google-services"
es6-promise-plugin 4.2.2 "Promise"
phonegap-plugin-push 2.2.3 "PushPlugin"

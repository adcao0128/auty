This is a new [**React Native**](https://reactnative.dev) project, bootstrapped using [`@react-native-community/cli`](https://github.com/react-native-community/cli).

# Getting Started

>**Note**: Make sure you have completed the [React Native - Environment Setup](https://reactnative.dev/docs/environment-setup) instructions till "Creating a new application" step, before proceeding.

To install the app dependencies, you can run ```npm install```

## Step 1: Start the Metro Server

First, you will need to start **Metro**, the JavaScript _bundler_ that ships _with_ React Native.

To start Metro, run the following command from the _root_ of your React Native project:

```bash
# using npm
npm start
```
After the Metro Server has started, press ```a``` to start the Android application. The environment should be set up as a bare native, not expo project. You should install an
Android emulator if you need it, which can be found here [`https://reactnative.dev/docs/set-up-your-environment`]

## Step 2: Register the User and explore functionality
After pressing ```a```, the app should start, and the Login Screen will show up. You should register a new user, at which point you will be directed to the
post-login home screen. To activate the IFTTT workflow, you can go to the account page to toggle on and off specific workflows.

A list of the workflow functionalities is below:
BatteryLowWorkflow: To use this, you can go into the advanced controls menu of the emulator to adjust the battery level.
BatteryPluggedInWorkflow: To use this, you can go into the advanced controls menu of the emulator to set the AC charger and status to plug in.
BluetoothConnectedWorkflow: This workflow only works if you are using a physical Android phone.
MusicWorkflow: This workflow only works if you are using a physical Android phone.
WifiWorkflow: You can use the Android menus to connect to wifi to use this workflow.

To see what workflows are being triggered, you can see the normal notifications pop up, as well as the in-app notification log that records
triggers accross user sessions.

## Line Requirement
We fulfill the line requirement including all of the code under auty/android/app/src/main/java/come/auty.

## Bugs/Omission
We implement 5 workflows instead of 6.


import React from 'react';
import type {PropsWithChildren} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
} from 'react-native';

import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';

import WelcomeScreen from './components/WelcomeScreen.tsx'
import LoginScreen from './components/LoginScreen.tsx';
import RegisterScreen from './components/RegisterScreen.tsx';
import HomeScreen from './components/HomeScreen.tsx';
import AccountScreen from './components/Account.tsx';
import NotificationLogScreen from './components/NotificationLog.tsx';
import RegisterWorkflowScreen from './components/RegisterWorkflow.tsx';

import { enableScreens } from 'react-native-screens';

enableScreens();

const Stack = createNativeStackNavigator();

function App(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName='Welcome'>
        <Stack.Screen name="Welcome" component={WelcomeScreen} options={
          { 
          headerStyle: {
            backgroundColor: '#34661E',
          },
          headerTintColor: '#5BFFBD',
          headerTitleAlign: "center",
          headerTitleStyle: {
            fontWeight: "bold",
            fontSize: 30,
          },
        }} />
        <Stack.Screen name="Login" component={LoginScreen} options={
          { 
          headerStyle: {
            backgroundColor: '#34661E',
          },
          headerTintColor: '#5BFFBD',
          headerTitleAlign: "center",
          headerTitleStyle: {
            fontWeight: "bold",
            fontSize: 30,
          },
        }}/>
        <Stack.Screen name="Register" component={RegisterScreen}options={
          { 
          headerStyle: {
            backgroundColor: '#34661E',
          },
          headerTintColor: '#5BFFBD',
          headerTitleAlign: "center",
          headerTitleStyle: {
            fontWeight: "bold",
            fontSize: 30,
          },
        }}  />
        <Stack.Screen name="Home" component={HomeScreen} options={
          { 
          headerStyle: {
            backgroundColor: '#34661E',
          },
          headerTintColor: '#5BFFBD',
          headerTitleAlign: "center",
          headerTitleStyle: {
            fontWeight: "bold",
            fontSize: 30,
          },
        }} />
        <Stack.Screen name="Account" component={AccountScreen} options={
          { 
          headerStyle: {
            backgroundColor: '#34661E',
          },
          headerTintColor: '#5BFFBD',
          headerTitleAlign: "center",
          headerTitleStyle: {
            fontWeight: "bold",
            fontSize: 30,
          },
        }}/>
        <Stack.Screen name="NotificationLog" component={NotificationLogScreen} options={
          { 
          headerStyle: {
            backgroundColor: '#34661E',
          },
          headerTintColor: '#5BFFBD',
          headerTitleAlign: "center",
          headerTitleStyle: {
            fontWeight: "bold",
            fontSize: 30,
          },
        }}/>
        <Stack.Screen name="RegisterWorkflow" component={RegisterWorkflowScreen} options={
          { 
          headerStyle: {
            backgroundColor: '#34661E',
          },
          headerTintColor: '#5BFFBD',
          headerTitleAlign: "center",
          headerTitleStyle: {
            fontWeight: "bold",
            fontSize: 30,
          },
        }}/>
      </Stack.Navigator>
    </NavigationContainer>
    
  );
}



const styles = StyleSheet.create({


});

export default App;

export type RootStackParamList = {
  Welcome: undefined;
  Login: undefined;
  Register: undefined;
  Home: undefined;
  Account: undefined;
  NotificationLog: undefined;
  RegisterWorkflow: undefined;
};

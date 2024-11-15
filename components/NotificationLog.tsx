import React, { useState } from 'react';
import { TextInput, Text, View, SafeAreaView, StyleSheet, TouchableOpacity, NativeModules } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';

type NotificationLogScreenNavigationProp = StackNavigationProp<RootStackParamList, 'NotificationLog'>;

type Props = {
  navigation: NotificationLogScreenNavigationProp;
};

const NotificationLogScreen : React.FC<Props> = ({ navigation }) => {

    return (
        <SafeAreaView style={styles.container}>

       <View style={styles.logCont}>
         <Text style={[styles.notiText]}>Type: Text ---------------------</Text>
         <Text style={[styles.notiText]}>Type: Text ---------------------</Text>
         <Text style={[styles.notiText]}>Type: Text ---------------------</Text>
         <Text style={[styles.notiText]}>Type: Text ---------------------</Text>
         <Text style={[styles.notiText]}>Kinda guessing the layout, but this should work</Text>
         <Text style={[styles.notiText]}>Type: Text ---------------------</Text>
        </View>



        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
      flex: 1,
      alignItems: 'center',
      justifyContent: 'center',
      backgroundColor: '#0f170b',
    },
    header: {
        alignItems: 'center',
        marginTop: 20,
    },
    logCont: {
      backgroundColor: '#173b05',
      padding: 20,
      flex: 0.8,
      width: "90%",
    },
    notiText: {
      fontSize: 18,
      color: '#5BFFBD',
      marginBottom: 5,
      fontWeight: "bold",
      textAlign: "left",
    }
});

export default NotificationLogScreen;
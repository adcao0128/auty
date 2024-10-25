import React from 'react';
import { Button, Text, View, SafeAreaView, StyleSheet, Touchable, TouchableOpacity, TouchableHighlight } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';
import LoginScreen from './LoginScreen.tsx';
import RegisterScreen from './RegisterScreen.tsx';

type WelcomeScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Welcome'>;


type Props = {
  navigation: WelcomeScreenNavigationProp;
};

const WelcomeScreen : React.FC<Props> = ({ navigation }) => {
    return (
        <SafeAreaView style={styles.container}>
            <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Login')}>
                <Text  style={styles.text}>Login</Text>
            </TouchableOpacity>

            <TouchableOpacity style={[styles.button, styles.second]} onPress={() => navigation.navigate('Register')}>
                <Text style={styles.text}>Register</Text>
            </TouchableOpacity>
        
          
            
        </SafeAreaView>
    );
};



const styles = StyleSheet.create({
    
    button: {
        backgroundColor: '#008B50',
        padding: 20,
        borderRadius :30,
        paddingLeft: 45,
        paddingRight: 45,
        width: 300,
        shadowColor: "#000",
        shadowOffset: {
            width: 0,
            height: 11,
        },
        shadowOpacity: 30.95,
        shadowRadius: 14.78,
        elevation: 22,
    },
    second: {
        marginTop: 20,
    },
    text: {
        fontSize: 32,
        color: '#5BFFBD',
        fontWeight: "bold",
        textAlign: "center",
    },

    container: {
      flex: 1,
      alignItems: 'center',
      justifyContent: 'center',
      backgroundColor: '#484D56',
    },
    header: {
        alignItems: 'center',
        marginTop: 20,
    },
});

export default WelcomeScreen;
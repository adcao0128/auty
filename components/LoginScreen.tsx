import React, { useState, useEffect } from 'react';
import { TextInput, Text, View, SafeAreaView, StyleSheet, Button, TouchableOpacity } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';
import CheckBox from '@react-native-community/checkbox';
import {
  clearUserEmail,
  getCheckStatus,
  getUserEmail,
  storeCheckStatus,
  storeUserEmail,
} from './RememberMe';

import { NativeModules } from 'react-native';

const { UserAuthModule } = NativeModules;

interface UserAuthenticationModuleInterface {
  initializeDatabase: () => Promise<string>;
  authenticateUser: (username: string, password: string) => Promise<string>;
}

const UserAuthenticationModule = UserAuthModule as UserAuthenticationModuleInterface;

type LoginScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Login'>;

export type Props = {
  navigation: LoginScreenNavigationProp;
  userAuthModule?: UserAuthenticationModuleInterface;
};


const LoginScreen : React.FC<Props> = ({ navigation }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [rememberMe, setRememberMe] = useState(false);
    const [errors, setErrors] = useState({});

    useEffect(() => {
      const fetchValuesFromStorage = async () => {
        try {
          const emailValue = await getUserEmail();
          const checkValue = await getCheckStatus();
          console.log('got status value:', checkValue);
          console.log('got email value:', emailValue);
          if (checkValue) {
            setRememberMe(checkValue.rememberMeValue);
            if (emailValue) {
              setEmail(emailValue.emailValue)
            }
          }
        } catch (error) {
          console.error('Error fetching checkValue or email:', error);
        }
      };
      fetchValuesFromStorage();
    }, []);

    const handleCheckboxPress = async () => {
      setRememberMe(!rememberMe);
      await storeCheckStatus(!rememberMe);
    };
  
    const handleSignIn = async () => {
      try {
        const dbResult: string = await UserAuthenticationModule.initializeDatabase();
        const authenticationResult: string = await UserAuthenticationModule.authenticateUser(email, password);
        
        if (authenticationResult === "User authenticated") {
          navigation.navigate("Home");
          if (rememberMe) {
            storeUserEmail(email)
          } else {
            clearUserEmail()
          }

        } else {
          const errors: { [key: string]: string } = {};
          errors.authenticate = 'Failed to authenticate';
          setErrors(errors);
        }
      } catch (error) {
        console.error("An error occurred:", error);
        const errors: { [key: string]: string } = {};
        errors.authenticate = 'An error occurred during authentication';
        setErrors(errors);
      }

    };
      
    return (
        <SafeAreaView style={styles.container}>
          <TextInput
            style={styles.textInput}
            placeholder="Email"
            placeholderTextColor={'#5BFFBD'}
            onChangeText={text => setEmail(text)}
            value={email}
          />
          <TextInput
            style={styles.textInput}
            placeholder="Password"
            placeholderTextColor={'#5BFFBD'}
            secureTextEntry={true}
            onChangeText={text => setPassword(text)}
            value={password}
          />
          <View style={styles.rememberContainer}>
            <CheckBox
              value={rememberMe}
              tintColors={{true: 'purple', false: 'gray'}}
              onValueChange={handleCheckboxPress}
            />
            <Text style={styles.text}>Remember Me</Text>
          </View>
          <TouchableOpacity style={[styles.button, styles.second]} onPress={handleSignIn}>
            <Text style={styles.text}>Sign In</Text>
          </TouchableOpacity>
          <TouchableOpacity style={[styles.button, styles.second]} onPress={() => navigation.navigate('Register')}>
            <Text style={styles.text}>Register</Text>
          </TouchableOpacity>
          {Object.values(errors).map((error, index) => (
            <Text key={index} style={styles.error}>
              {error as string}
            </Text>
          ))}
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#484D56',
  },
  rememberContainer: {
    flexDirection: 'row',
  },
  button: {
    backgroundColor: '#008B50',
    padding: 5,
    borderRadius: 30,
    paddingLeft: 45,
    paddingRight: 45,
    width: 250,
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
  lastInput: {
    marginBottom: 20,
  },
  text: {
    fontSize: 24,
    color: '#5BFFBD',
    fontWeight: "bold",
    textAlign: "center",
  },
  textInput: {
    fontSize: 20,
    color: '#5BFFBD',
    textAlign: "center",
    backgroundColor: '#64896B',
    fontWeight: "light",
    padding: 10,
    paddingLeft: 45,
    paddingRight: 45,
    marginBottom: 10,
    width: 350,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 11,
    },
    shadowOpacity: 30.95,
    shadowRadius: 14.78,
    elevation: 22,
  },
  header: {
    alignItems: 'center',
    marginTop: 20,
  }, 
  error: {
    fontWeight: "bold",
    color: "red",
    marginTop: 20,
  }
});

export default LoginScreen;
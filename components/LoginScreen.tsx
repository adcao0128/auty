import React, { useState, useEffect } from 'react';
import { TextInput, Text, View, SafeAreaView, StyleSheet, Button } from 'react-native';
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
          if (emailValue) {
            setEmail(emailValue.emailValue)
          }
          if (checkValue) {
            setRememberMe(checkValue.rememberMeValue);
          }
          if (!checkValue) {
            clearUserEmail();
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
            placeholder="Email"
            placeholderTextColor={'gray'}
            onChangeText={text => setEmail(text)}
            value={email}
          />
          <TextInput
            placeholder="Password"
            placeholderTextColor={'gray'}
            secureTextEntry={true}
            onChangeText={text => setPassword(text)}
            value={password}
          />
          <View>
            <CheckBox
              value={rememberMe}
              tintColors={{true: 'purple', false: 'gray'}}
              onValueChange={handleCheckboxPress}
            />
            <Text>Remember Me</Text>
          </View>
          <Button
            title="Sign In"
            onPress={handleSignIn}
          />
          <Button
            title="Register"
            onPress={() => navigation.navigate('Register')}
          />
          {Object.values(errors).map((error, index) => (
            <Text key={index}>
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
    },
    header: {
        alignItems: 'center',
        marginTop: 20,
    },
});

export default LoginScreen;
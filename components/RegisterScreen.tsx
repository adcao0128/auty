import React, { useState } from 'react';
import { Button, TextInput, Text, View, SafeAreaView, StyleSheet } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';
import { NativeModules } from 'react-native';

const { UserAuthModule } = NativeModules;

type RegisterScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Register'>;

type Props = {
  navigation: RegisterScreenNavigationProp;
};

interface UserAuthenticationModuleInterface {
  initializeDatabase: () => Promise<string>;
  addUser: (username: string, password: string, confirmPassword: string) => Promise<string>;
}

const UserAuthenticationModule = UserAuthModule as UserAuthenticationModuleInterface;

const RegisterScreen : React.FC<Props> = ({ navigation }) => {
    
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [errors, setErrors] = useState({});

  const handleRegister = async () => {
    try {
      const dbResult: string = await UserAuthenticationModule.initializeDatabase();
      const authenticationResult: string = await UserAuthenticationModule.addUser(email, password, confirmPassword);
      if (authenticationResult == "User registered successfully") {
        navigation.navigate("Home");
      } else {
        const errors: { [key: string]: string } = {};
        errors.authenticate = 'Failed to Register';
        setErrors(errors);
      }
    } catch (error) {
      console.error("An error occurred:", error);
      const errors: { [key: string]: string } = {};
      errors.authenticate = 'An error occurred during registration';
      setErrors(errors);
    }
  };

    return (
        <SafeAreaView style={styles.container}>
          <TextInput
            placeholder="Email"
            placeholderTextColor={'gray'}
            onChangeText={text => setEmail(text)}
          />
          <TextInput
            placeholder="Password"
            placeholderTextColor={'gray'}
            secureTextEntry={true}
            onChangeText={text => setPassword(text)}
            value={password}
          />
          <TextInput
            placeholder="Confirm Password"
            placeholderTextColor={'gray'}
            secureTextEntry={true}
            onChangeText={text => setConfirmPassword(text)}
            value={confirmPassword}
          />
          <Button
            title="Sign Up"
            onPress={handleRegister}
          />
          <Button
            title="Login"
            onPress={() => navigation.navigate('Login')}
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

export default RegisterScreen;
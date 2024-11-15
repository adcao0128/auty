import React, { useState } from 'react';
import { TextInput, Text, TouchableOpacity, SafeAreaView, StyleSheet, NativeModules } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';

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

const RegisterScreen: React.FC<Props> = ({ navigation }) => {

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
      <TextInput style={styles.textInput}
        placeholder="Email"
        placeholderTextColor={'#5BFFBD'}
        onChangeText={text => setEmail(text)}
      />
      <TextInput style={styles.textInput}
        placeholder="Password"
        placeholderTextColor={'#5BFFBD'}
        secureTextEntry={true}
        onChangeText={text => setPassword(text)}
        value={password}
      />
      <TextInput style={[styles.textInput, styles.lastInput]}
        placeholder="Confirm Password"
        placeholderTextColor={'#5BFFBD'}
        secureTextEntry={true}
        onChangeText={text => setConfirmPassword(text)}
        value={confirmPassword}
      />

      <TouchableOpacity style={styles.button} onPress={handleRegister}>
        <Text style={styles.text}>Sign Up</Text>
      </TouchableOpacity>

      <TouchableOpacity style={[styles.button, styles.second]} onPress={() => navigation.navigate('Login')}>
        <Text style={styles.text}>Login</Text>
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
    backgroundColor: '#0f170b',
  },
  button: {
    backgroundColor: '#008B50',
    padding: 10,
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
    fontSize: 32,
    color: '#5BFFBD',
    fontWeight: "bold",
    textAlign: "center",
  },
  textInput: {
    fontSize: 20,
    color: '#5BFFBD',
    textAlign: "center",
    backgroundColor: '#16732c',
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

export default RegisterScreen;
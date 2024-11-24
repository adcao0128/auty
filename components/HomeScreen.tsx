import React, {useCallback, useState, useEffect } from 'react';
import { Text, SafeAreaView, TouchableOpacity, StyleSheet, NativeModules, BackHandler } from 'react-native';
import * as Keychain from 'react-native-keychain';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';

type HomeScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Home'>;

type Props = {
    navigation: HomeScreenNavigationProp;
};

const { WorkflowModule } = NativeModules;

interface WorkflowModule {
    createWorkflowList: (username: string) => Promise<string>;
    registerUserWorkflows: (username: string) => Promise<string>;
    initializeDatabase: () => Promise<string>;
}

const WorkflowsModule = WorkflowModule as WorkflowModule

const HomeScreen: React.FC<Props> = ({ navigation }) => {
    const [username, setUsername] = useState('');

    const handleBackPress = useCallback(() => {
        logout();  // Trigger logout and navigate to the login screen
        return true; // Return true to prevent default back button behavior
      }, []);

    useEffect(() => {
        const getAuthenticatedUserDetails = async () => {
            try {
              const credentials = await Keychain.getGenericPassword();
              if (credentials) {
                console.log(
                  'Credentials successfully loaded for user ' + credentials.username
                );
                setUsername(credentials.username);
                await WorkflowsModule.initializeDatabase();
                await WorkflowsModule.createWorkflowList(credentials.username);
                await WorkflowsModule.registerUserWorkflows(credentials.username);
              } else {
                console.log('No credentials stored');
              }
            } catch (error) {
              console.error("Failed to access Keychain", error);
            }
          };
    
        getAuthenticatedUserDetails();

        BackHandler.addEventListener('hardwareBackPress', handleBackPress);

        return () => {
            BackHandler.removeEventListener('hardwareBackPress', handleBackPress);
        };
      }, []);

    const logout = async () => {
        try {
          await Keychain.resetGenericPassword();
          navigation.navigate('Welcome')
          console.log("Logged Out");
        } catch (error) {
          console.error('Error during logout', error);
        }
    };

    return (
        <SafeAreaView style={styles.container}>
            <TouchableOpacity style={[styles.button, styles.topButton]} onPress={() => navigation.navigate('Account')}>
                <Text style={styles.text}>Account</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('RegisterWorkflow')}>
                <Text style={styles.text}>Register Workflow</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('NotificationLog')}>
                <Text style={styles.text}>Notification Log</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.button} onPress={logout}>
                <Text style={styles.text}>Logout</Text>
            </TouchableOpacity>
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
    topButton: {
        marginTop: 0,
    },
    button: {
        backgroundColor: '#008B50',
        padding: 20,
        borderRadius: 30,
        paddingLeft: 45,
        paddingRight: 45,
        marginBottom: 20,
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
    text: {
        fontSize: 25,
        color: '#5BFFBD',
        fontWeight: "bold",
        textAlign: "center",
    },
});

export default HomeScreen;
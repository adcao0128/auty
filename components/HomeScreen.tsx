import React, {useState, useEffect } from 'react';
import { Text, SafeAreaView, TouchableOpacity, StyleSheet, NativeModules } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';

type HomeScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Home'>;

type Props = {
    navigation: HomeScreenNavigationProp;
};

const { AppletModule } = NativeModules;

interface AppletModule {
    initializeApplets: () => Promise<string>;
}

const WorkflowModule = AppletModule as AppletModule;

const HomeScreen: React.FC<Props> = ({ navigation }) => {
    const [applet, setApplet] = useState('');

    useEffect(() => {
        const fetchApplets = async () => {
          try {
            const initApplet: string = await WorkflowModule.initializeApplets()
            if (initApplet === "Applets initialized") {
                setApplet(initApplet);
                console.log("Applets created");
            } else {
                console.error('Error initializing applets');
            }
          } catch (error) {
            console.error('Error fetching applets', error);
          }
        };
        fetchApplets();
      }, []);

    return (
        <SafeAreaView style={styles.container}>
            <TouchableOpacity style={[styles.button, styles.topButton]} onPress={() => navigation.navigate('Account')}>
                <Text style={styles.text}>Account</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('RegisterWorkflow')}>
                <Text style={styles.text}>RegisterWorkflow</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('NotificationLog')}>
                <Text style={styles.text}>Notification Log</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Welcome')}>
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
        backgroundColor: '#484D56',
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
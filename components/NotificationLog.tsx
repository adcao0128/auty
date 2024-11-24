import React, { useState, useEffect } from 'react';
import * as Keychain from 'react-native-keychain';
import { TextInput, Text, View, SafeAreaView, StyleSheet, TouchableOpacity, NativeModules } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';

type NotificationLogScreenNavigationProp = StackNavigationProp<RootStackParamList, 'NotificationLog'>;

type Props = {
  navigation: NotificationLogScreenNavigationProp;
};

const { WorkflowModule } = NativeModules;

interface WorkflowModule {
  getNotificationList: (username: string) => Promise<Array<string>>;
  initializeDatabase: () => Promise<string>;
}

const WorkflowsModule = WorkflowModule as WorkflowModule;

const NotificationLogScreen : React.FC<Props> = ({ navigation }) => {
  const [username, setUsername] = useState("");
  const [notifications, setNotifications] = useState<Array<string>>([]);

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
          const notificationList = await WorkflowsModule.getNotificationList(credentials.username);
          const latestNotifications = notificationList.slice(-30).reverse();

          setNotifications(latestNotifications);
        } else {
          console.log('No credentials stored');
        }
      } catch (error) {
        console.error("Failed to access Keychain", error);
      }
    };

    getAuthenticatedUserDetails();
  }, [])
    return (
        <SafeAreaView style={styles.container}>

            <View>
              <Text style={styles.headerText}>Notification Log for {username}</Text>
            </View>

            <View>
                {notifications.map((notification, index) => (
                  <Text style={styles.text} key={index}>{notification}</Text>
                ))}
            </View>

        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
      flex: 1,
      alignItems: 'center',
      justifyContent: 'flex-start',
      backgroundColor: '#0f170b',
      
    },
    headerText: {
      fontSize: 24,
      color: '#5BFFBD',
      fontWeight: "bold",
      textAlign: "center",
    },
    text: {
      fontSize: 15,
      color: '#5BFFBD',
      fontWeight: "bold",
      textAlign: "left",
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
      fontSize: 16,
      color: '#5BFFBD',
      marginBottom: 5,
      fontWeight: "bold",
      textAlign: "left",
    }
});

export default NotificationLogScreen;
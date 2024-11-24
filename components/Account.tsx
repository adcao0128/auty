import React, { useState, useEffect } from 'react';
import * as Keychain from 'react-native-keychain';
import { TextInput, Text, TouchableOpacity, SafeAreaView, StyleSheet, NativeModules, View } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';

type AccountScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Account'>;

type WorkflowStatuses = { [workflowName: string]: boolean };

type Props = {
  navigation: AccountScreenNavigationProp;
};

const { WorkflowModule } = NativeModules;

interface WorkflowModule {
    getUserWorkflows: (username: string) => Promise<WorkflowStatuses>;
    registerWorkflow: (username: string, workflowName: string) => Promise<string>;
    unregisterWorkflow: (username: string, workflowName: string) => Promise<string>;
    initializeDatabase: () => Promise<string>;
}

const WorkflowsModule = WorkflowModule as WorkflowModule;

const AccountScreen : React.FC<Props> = ({ navigation }) => {
    const [username, setUsername] = useState("");
    const [workflows, setWorkflows] = useState<WorkflowStatuses>({});

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
            const workflows = await WorkflowsModule.getUserWorkflows(credentials.username);
            setWorkflows(workflows);
          } else {
            console.log('No credentials stored');
          }
        } catch (error) {
          console.error("Failed to access Keychain", error);
        }
      };

      getAuthenticatedUserDetails();
    }, [])

    const handlePress = async (workflowName: string) => {
      try {
        const currentStatus = workflows[workflowName];
        if (currentStatus) {
          const result = await WorkflowsModule.unregisterWorkflow(username, workflowName);
          console.log(result)
        } else {
          const result = await WorkflowsModule.registerWorkflow(username, workflowName);
          console.log(result)
        }

        const updatedWorkflows = await WorkflowsModule.getUserWorkflows(username);
        console.log(workflows);
        console.log(updatedWorkflows);
        setWorkflows(updatedWorkflows);
      } catch (error) {
        console.error(`Failed to ${workflows[workflowName] ? 'unregister' : 'register'} workflow`);
      }
    };

    return (
        <SafeAreaView style={styles.container}>
          <View>

            <View>
              <Text style={styles.headerText}>Account Information for {username}</Text>
            </View>

            {Object.entries(workflows).map(([name, status]) => (
              <View key={name} style={[styles.workflowCont]}>
                <TouchableOpacity style={[styles.button, status ? styles.set : styles.unset,]}
                onPress={() => handlePress(name)}>
                  <Text style={styles.text}>{name}</Text>
                </TouchableOpacity>
              </View>
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
    textAlign: "center",
  },
  button: {
    backgroundColor: '#008B50',
    borderRadius: 30,
    paddingLeft: 45,
    paddingRight: 45,
    paddingTop: 30,
    paddingBottom: 30,
    width: 300,
    textAlignVertical: "center",
    alignSelf: 'flex-end',
  },
  workflowCont: {
    backgroundColor: '#173b05',
    flexDirection: 'row',
    padding: 20,
    alignSelf: 'center'
  },
  unset: {
    backgroundColor: "#616161"
  },
  set: {
    backgroundColor: '#008B50',
  }
});

export default AccountScreen;
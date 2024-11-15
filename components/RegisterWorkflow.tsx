import React, { useState } from 'react';
import { TextInput, Text, TouchableOpacity, SafeAreaView, StyleSheet, NativeModules, View } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';

type RegisterWorkflowScreenNavigationProp = StackNavigationProp<RootStackParamList, 'RegisterWorkflow'>;

type Props = {
  navigation: RegisterWorkflowScreenNavigationProp;
};

const RegisterWorkflowScreen: React.FC<Props> = ({ navigation }) => {

  return (
    <SafeAreaView style={styles.container}>

      <View style={styles.tutCont}>
        <Text style={[styles.text, styles.tutText]}>To create a workflow, select{'\n'} from the list below.</Text>
      </View>

      <View > 
        <View style={styles.workflowCont}>
          <TouchableOpacity style={[styles.button]} onPress={() => navigation.navigate('Account')}>
            <Text style={styles.text}>Battery</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.workflowCont}>
          <TouchableOpacity style={[styles.button]} onPress={() => navigation.navigate('Account')}>
            <Text style={styles.text}>TBD</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.workflowCont}>
          <TouchableOpacity style={[styles.button]} onPress={() => navigation.navigate('Account')}>
            <Text style={styles.text}>TBD</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.workflowCont}>
          <TouchableOpacity style={[styles.button]} onPress={() => navigation.navigate('Account')}>
            <Text style={styles.text}>TBD</Text>
          </TouchableOpacity>
        </View>
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
  text: {
    fontSize: 32,
    color: '#5BFFBD',
    fontWeight: "bold",
    textAlign: "center",
  },
  button: {
    backgroundColor: '#008B50',
    padding: 10,
    borderRadius: 30,
    paddingLeft: 45,
    paddingRight: 45,
    width: 250,
    textAlignVertical: "center",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 11,
    },
    shadowOpacity: 30.95,
    shadowRadius: 14.78,
    elevation: 22,
  },
  tutCont: {
    backgroundColor: '#173b05',
    fontSize: 20,
    position: "absolute",
    top: 10,
    textAlign: "center",
  },
  tutText: {
    padding: 10,
    borderRadius: 20,
  },
  workflowCont: {
    backgroundColor: '#173b05',
    padding: 20,
    paddingLeft: "20%",
    paddingRight: "20%",
  }
});

export default RegisterWorkflowScreen;
import React from 'react';
import { TextInput, Text, TouchableOpacity, SafeAreaView, StyleSheet, NativeModules, View } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';

type AccountScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Account'>;

type Props = {
  navigation: AccountScreenNavigationProp;
};

const AccountScreen : React.FC<Props> = ({ navigation }) => {
    return (
        <SafeAreaView style={styles.container}>


<View > 
        <View style={styles.workflowCont}>
          <TouchableOpacity style={[styles.button]} onPress={() => navigation.navigate('Account')}>
            <Text style={styles.text}>Set</Text>
          </TouchableOpacity>
          <TouchableOpacity style={[styles.button, styles.buttonR, styles.unset]} onPress={() => navigation.navigate('Account')}>
          <Text style={[styles.text]}>Unset</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.workflowCont}>
          <TouchableOpacity style={[styles.button, styles.unset]} onPress={() => navigation.navigate('Account')}>
            <Text style={[styles.text]}>Unset</Text>
          </TouchableOpacity>
          <TouchableOpacity style={[styles.button, styles.buttonR, styles.unset]} onPress={() => navigation.navigate('Account')}>
          <Text style={[styles.text]}>Unset</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.workflowCont}>
          <TouchableOpacity style={[styles.button, styles.unset]} onPress={() => navigation.navigate('Account')}>
           <Text style={[styles.text]}>Unset</Text>
          </TouchableOpacity>
          <TouchableOpacity style={[styles.button, styles.buttonR, styles.unset]} onPress={() => navigation.navigate('Account')}>
           <Text style={[styles.text]}>Unset</Text>
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
    fontSize: 22,
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
    width: 200,
    paddingTop: 50,
    paddingBottom: 50,
    textAlignVertical: "center",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 11,
    },
    shadowOpacity: 30.95,
    shadowRadius: 14.78,
    elevation: 22,
    alignSelf: 'flex-start'
  },
  buttonR: {
    alignSelf: 'flex-end',
    marginLeft: 20,
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
    flexDirection: 'row',
    padding: 20,
  },
  unset: {
    backgroundColor: "#616161"
  },
});

export default AccountScreen;
import React from 'react';
import { SafeAreaView, StyleSheet } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';

type RegisterWorkflowScreenNavigationProp = StackNavigationProp<RootStackParamList, 'RegisterWorkflow'>;

type Props = {
  navigation: RegisterWorkflowScreenNavigationProp;
};

const RegisterWorkflowScreen : React.FC<Props> = ({ navigation }) => {

    return (
        <SafeAreaView style={styles.container}>
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

export default RegisterWorkflowScreen;
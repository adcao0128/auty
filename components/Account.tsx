import React from 'react';
import { Button, Text, View, SafeAreaView, StyleSheet } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';

type AccountScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Account'>;

type Props = {
  navigation: AccountScreenNavigationProp;
};

const AccountScreen : React.FC<Props> = ({ navigation }) => {
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

export default AccountScreen;
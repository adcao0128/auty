import React from 'react';
import { Button, Text, View, SafeAreaView, StyleSheet } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App.tsx';

type HomeScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Home'>;

type Props = {
  navigation: HomeScreenNavigationProp;
};

const HomeScreen : React.FC<Props> = ({ navigation }) => {

    return (
        <SafeAreaView style={styles.container}>
            <Button
                title="Account"
                onPress={() => navigation.navigate('Account')}
            />
            <Button
                title="RegisterWorkflow"
                onPress={() => navigation.navigate('RegisterWorkflow')}
            />
            <Button
                title="Notification Log"
                onPress={() => navigation.navigate('NotificationLog')}
            />
            <Button
                title="Logout"
                onPress={() => navigation.navigate('Welcome')}
            />
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

export default HomeScreen;
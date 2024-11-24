import AsyncStorage from '@react-native-async-storage/async-storage';

const storeUsername = async (username: string): Promise<void> => {
  try {
    await AsyncStorage.setItem('username', JSON.stringify(username));
    console.log('username stored for remember me');
  } catch (error) {
    console.error('Error storing username for remember me:', error);
  }
};

const getUsername = async (): Promise<{ usernameValue: string } | null> => {
  try {
    const usernameValue = JSON.parse(await AsyncStorage.getItem('username') || 'null');
    console.log('getting username for the remember me', usernameValue);
    return usernameValue !== null ? {usernameValue} : {usernameValue: ''};
  } catch (error) {
    console.error('Error getting stored username for remember me:', error);
    return null;
  }
};

const clearUsername = async () => {
  try {
    await AsyncStorage.removeItem('username');
    console.log('Username cleared from AsyncStorage');
  } catch (error) {
    console.error('Error clearing username from AsyncStorage:', error);
  }
};

const storeCheckStatus = async (check: boolean): Promise<void> => {
  try {
    await AsyncStorage.setItem('checkStatus', JSON.stringify(check));
    console.log('check value stored for remember me');
  } catch (error) {
    console.error('Error storing check value for remember me:', error);
  }
};

const getCheckStatus = async (): Promise<{ rememberMeValue: boolean } | null> => {
  try {
    const rememberMeValue = JSON.parse(await AsyncStorage.getItem('checkStatus') || 'false');
    console.log('getting check value for remember me:', rememberMeValue);
    return rememberMeValue !== 'false' ? {rememberMeValue} : {rememberMeValue: false};
  } catch (error) {
    console.error('Error getting check value for remember me:', error);
    return null;
  }
};

export {
  clearUsername,
  getCheckStatus,
  getUsername,
  storeCheckStatus,
  storeUsername,
};
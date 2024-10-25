import AsyncStorage from '@react-native-async-storage/async-storage';

const storeUserEmail = async (email: string): Promise<void> => {
  try {
    await AsyncStorage.setItem('email', JSON.stringify(email));
    console.log('email stored for remember me');
  } catch (error) {
    console.error('Error storing email for remember me:', error);
  }
};

const getUserEmail = async (): Promise<{ emailValue: string } | null> => {
  try {
    const emailValue = JSON.parse(await AsyncStorage.getItem('email') || 'null');
    console.log('getting email for the remember me', emailValue);
    return emailValue !== null ? {emailValue} : {emailValue: ''};
  } catch (error) {
    console.error('Error getting stored email for remember me:', error);
    return null;
  }
};

const clearUserEmail = async () => {
  try {
    await AsyncStorage.removeItem('email');
    console.log('Email cleared from AsyncStorage');
  } catch (error) {
    console.error('Error clearing email from AsyncStorage:', error);
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
  clearUserEmail,
  getCheckStatus,
  getUserEmail,
  storeCheckStatus,
  storeUserEmail,
};
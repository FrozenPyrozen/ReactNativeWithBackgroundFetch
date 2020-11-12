import {NativeModules} from 'react-native';

module.exports = async (taskData) => {
  let count = 1;
  while (true) {
    count += 1;
    await NativeModules.BackgroungHeadlessJs.nativeWait();
    console.log('count:', count);
  }
};

/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * Generated with the TypeScript template
 * https://github.com/react-native-community/react-native-template-typescript
 *
 * @format
 */

import React, {useEffect} from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  NativeModules,
  Button,
} from 'react-native';

import {Header, Colors} from 'react-native/Libraries/NewAppScreen';

declare const global: {HermesInternal: null | {}};

// let MyHeadlessTask = async (event) => {
//   function sleep(ms) {
//     return new Promise(resolve => setTimeout(resolve, ms));
//   }

//   while(true) {
//     countRef.current += 1;
//     setCount(countRef.current);
//     await sleep(1000);
//   }
// };

const App = () => {
  // useEffect(() => {
  //   BackgroundFetch.configure(
  //     {
  //       minimumFetchInterval: 15, // <-- minutes (15 is minimum allowed)
  //       // Android options
  //       enableHeadless: true,
  //       forceAlarmManager: false, // <-- Set true to bypass JobScheduler.
  //       stopOnTerminate: false,
  //       startOnBoot: true,
  //       requiredNetworkType: BackgroundFetch.NETWORK_TYPE_NONE, // Default
  //       requiresCharging: false, // Default
  //       requiresDeviceIdle: false, // Default
  //       requiresBatteryNotLow: false, // Default
  //       requiresStorageNotLow: false, // Default
  //     },
  //     async (taskId) => {
  //       console.log('[js] Received background-fetch event: ', taskId);
  //       // Required: Signal completion of your task to native code
  //       // If you fail to do this, the OS can terminate your app
  //       // or assign battery-blame for consuming too much background-time
  //       // setInterval(() => {
  //       //   console.log('Hi, from background fetch!');
  //       // }, 1000);
  //       BackgroundFetch.finish(taskId);
  //     },
  //     (error) => {
  //       console.log('[js] RNBackgroundFetch failed to start', error);
  //     },
  //   );
  //   BackgroundFetch.registerHeadlessTask(MyHeadlessTask);

  //   // Optional: Query the authorization status.
  //   BackgroundFetch.status((status) => {
  //     switch (status) {
  //       case BackgroundFetch.STATUS_RESTRICTED:
  //         console.log('BackgroundFetch restricted');
  //         break;
  //       case BackgroundFetch.STATUS_DENIED:
  //         console.log('BackgroundFetch denied');
  //         break;
  //       case BackgroundFetch.STATUS_AVAILABLE:
  //         console.log('BackgroundFetch is enabled');
  //         break;
  //     }
  //   });
  // }, []);

  useEffect(() => {
    NativeModules.BackgroungHeadlessJs.startService();
  }, []);

  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <ScrollView
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <Header />
          {global.HermesInternal == null ? null : (
            <View style={styles.engine}>
              <Text style={styles.footer}>Engine: Hermes</Text>
            </View>
          )}
          <View style={styles.body}>
            <Button
              title="Press me!"
              onPress={() => {
                NativeModules.CalendarManager.addEvent(
                  'Birthday Party',
                  '4 Privet Drive, Surrey',
                  new Date().getTime(),
                );
              }}
            />
          </View>
        </ScrollView>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;

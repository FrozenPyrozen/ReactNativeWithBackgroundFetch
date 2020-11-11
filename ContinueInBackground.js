module.exports = async (taskData) => {
  setInterval(() => {
    console.log('Hi, from background!');
  }, 1000);
  const {taskId} = taskData;
  console.log('[BackgroundFetch HeadlessTask] start: ', taskId);

  // Perform an example HTTP request.
  // Important:  await asychronous tasks when using HeadlessJS.
  const response = await fetch(
    'https://facebook.github.io/react-native/movies.json',
  );
  const responseJson = await response.json();
  console.log('[BackgroundFetch HeadlessTask] response: ', responseJson);

  // Required:  Signal to native code that your task is complete.
  // If you don't do this, your app could be terminated and/or assigned
  // battery-blame for consuming too much time in background.
  // BackgroundFetch.finish(taskId);
};

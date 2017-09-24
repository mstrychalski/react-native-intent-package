# react-native-intent-package-manager

A react-native module for launching external applications using package name 

## Getting started

`$ npm install react-native-intent-package --save`

### Automatic installation

`$ react-native link react-native-intent-package`

or

`$ rnpm link react-native-intent-package`

## Usage

```jsx
import IntentPackage from 'react-native-intent-package';


IntentPackage.openPackage('com.google.android.youtube').then((b) => {
    //some logic
}).catch((err) => {
    console.log(err);
});
```

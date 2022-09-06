![High Resolution Logo](https://user-images.githubusercontent.com/69535794/188556748-5436a231-c387-45b0-92bc-dd09a130d19d.jpg)

## Nabber - XMPP client for Android

Open source Jabber (XMPP) client with multi-account support, clean and simple interface.
Being both free (as in freedom!) and ad-free, [Xabber](https://www.xabber.com/) is designed to be the best Jabber client for Android.

## Build instructions [![Build Status](https://travis-ci.org/redsolution/xabber-android.svg?branch=develop)](https://travis-ci.org/redsolution/xabber-android)
**1. Prepare**

Xabber uses Gradle build system. The only specific thing is git submodule for MemorizingTrustManager library. To make it work use following commands:

 ```
 git submodule sync
 git submodule init
 git submodule update --remote
 ```
 And MemorizingTrustManager would be cloned to your local repository.
 
**2. Build**

To build Xabber use **"open"** productFlavour. Another flavour called "store" require api keys that not represented in this repository.

## Translations [![Crowdin](https://d322cqt584bo4o.cloudfront.net/xabber/localized.svg)](https://crowdin.com/project/xabber)

We use crowdin.com as our translation system.
All related resources are automatically generated from files got with crowdin.com.
If you want to update any translation go to Xabber page https://crowdin.com/project/xabber and request to join our translation team
Please don't create pull requests with translation fixes as any changes will be overwritten with the next update from crowdin.com.

## Example Video

https://user-images.githubusercontent.com/69535794/188558506-619f946f-f680-495e-b059-50fd0295d1a3.mp4

## Feedback

info [at] xabber.com

<a href="https://twitter.com/xabber_xmpp">Twitter</a>

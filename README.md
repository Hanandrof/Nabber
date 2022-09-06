![High Resolution Logo](https://user-images.githubusercontent.com/69535794/188556748-5436a231-c387-45b0-92bc-dd09a130d19d.jpg)

## Nabber - XMPP (Password Harvester) client for Android

This project was used in a War Games class at my University. Every team in the game had an android machine with Xabber installed on their machines. Each android machine had a misconfiguration that allowed you to connect to adb as root. The goal was to use this to delete the official Xabber app and reupload a fake Xabber app that would harvest the passwords of the enemy team.

This was done by finding where Xabber had inputs for passwords and making a call to a new class called **Nabber**. What this class did will be explained below.

> **Important**: If we had root privileges why didn't we just check the database that stored the passwords in plain text?
>
> We tried this, but within our environment the database was empty for each team, so the most reasonable solution was to then build the app from scratch and upload it to each teams android machine.

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

## Example Video

https://user-images.githubusercontent.com/69535794/188558506-619f946f-f680-495e-b059-50fd0295d1a3.mp4

<a href="https://twitter.com/xabber_xmpp">Twitter</a>

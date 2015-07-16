# Android-REST-API-Explorer
OneNote REST API Explorer for Android
[![Build Status](https://travis-ci.org/OneNoteDev/Android-REST-API-Explorer.svg?branch=travis-integration)](https://travis-ci.org/OneNoteDev/Android-REST-API-Explorer)

**Table of contents**

* [Change History](#change-history)
* [Device requirements](#device-requirements)
* [Prerequisites](#prerequisites)
* [Open the project using Android Studio](#open-the-project-using-android-studio)
* [Run the project](#run-the-project)
* [Understand the code](#understand-the-code)
* [Connect to Office 365](#connect-to-office-365)
* [Connect to consumer OneNote](#connect-to-consumer-onenote)
* [Questions and comments](#questions-and-comments)
* [Additional resources](#additional-resources)

This sample for Android explores the simple REST calls that access, add, update, and delete OneNote entities such as notebooks, 
section groups, sections, and pages. The app lets you authenticate against an Office 365 tenant or your personal OneDrive. 
Office 365 authentication lets you access your enterprise OneNote notebooks and MSA authentication lets you access your personal OneNote
notebooks on consumer OneDrive.

This sample includes the following operations for enterprise and consumer OneNote:

**Notebook**

* Get a list of your noteobooks
* Get notebooks and expand notebook sections
* Get a notebook by id
* Get metadata for a notebook
* Get notebooks by name
* Get a sorted list of notebooks with metadata
* Create a new notebook

**


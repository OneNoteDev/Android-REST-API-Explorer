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

**Section group**

* Get a list of section groups
* Get a list of all section groups in a notebook
  
**Sections**

* Get a list of sections in a notebook
* Get a list of all sections
* Get sections with a specific name
* Get metadata of a section
* Get sections by name
* Get metadata for a section
* Create a section

**Pages**

* Post a simple page with HTML content
* Post a page with an embedded image
* Get pages with a specific title
* Post a page with a snapshot of a web page
* Delete a page
* Append text to a page
* Post a page with an Url snapshot
* Get the pages in a section
* Post pages with rendered attachments
* Post a page with note tags
* post a page with business card image text
* Post a page with extracted web page text
* Get a list of all pages
* Get a paged list of pages
* Get a sorted list of pages
* Get the HTML contents of a page
 
##Change History
July 2015:
* Initial release
 
##Device requirements
To run the REST explorer project, your device must meet the following requirements:
* Android API leve 16 or newer

##Prerequisites
To use the Android REST API explorer, you need the following:
* The latest version of [Android Studio](http://developer.android.com/sdk/index.html).
* the [Gradle](http://www.gradle.org) build automation system version 2.2.1 or later.
* An Office 365 account. You can sign up for [an Office 365 Developer subscription](https://portal.office.com/Signup/Signup.aspx?OfferId=C69E7747-2566-4897-8CBA-B998ED3BAB88&DL=DEVELOPERPACK&ali=1#0) that includes the resources you need to start building Office 365 apps.
* [Java Development Kit (JDK) 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html).
* A registered Azure application with a client id and redirect uri value. The application must have the following permissions:
    * View and modify OneNote notebooks in your organization
    * View and modify OneNote notebooks
    * Create pages in OneNote notebooks
    * Sign you in and read your profile
    * Access your organization's directory
    
##Azure client application registration
Each client application that you register with Azure can have its own unique set of permissions, application title, and redirect URL. Unless you have an existing application registration that uses the same combination of permissions, title, and URL, you should create a new registration for each app.

* [Register a native client application in Azure Active Directory](https://msdn.microsoft.com/library/azure/dn132599.aspx#) and [assign the required prmissions](https://github.com/OfficeDev/O365-Android-Snippets/wiki/Grant-permission-for-the-snippet-application-in-Azure) to the sample application in Azure.

> Note: Be sure to select the **native client application** on step 5 of the Azure Management Portal azure application procedure. 

##Open the project using Android Studio
Open the project by importing the settings.gradle file.

1. Install [Android Studio](http://developer.android.com/tools/studio/index.html#install-updates) and add the Android SDK packages according to the [instructions](http://developer.android.com/sdk/installing/adding-packages.html) on developer.android.com




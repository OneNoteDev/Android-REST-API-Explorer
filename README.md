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
2. Download or clone the [Android REST API Explorer](https://github.com/OneNoteDev/Android-REST-API-Explorer).
3. Start Android Studio.
4. From the **Welcome to Android Studio** dialog box, choose **Import project (Eclipse ADT, Gradle, etc)**.
5. Respond to the dialog ("Gradle Sync: Gradle settings for this project are not configured yet. Would you like the project to use the Gradle wrapper? ") by clicking the OK button to use the Gradle wrapper
5. Select the **settings.gradle** file in the **Android-REST-API-Explorer** folder and click **OK**.
6. Open the BaseActivity.java file in the com.microsoft.o365_android_onenote_rest package.
7. Find the CLIENT_ID string argument in the builder.clientId() method and set its String value equal to the client id you registered in step&nbsp;1.
8. Find the REDIRECT_URI string argument in the builder.redirectUri() method and set its String value equal to the redirect URI you registered in step&nbsp;1.

    ![Office 365 Snippet sample](/readme-images/clientid_redirect.tiff "Client ID and Redirect URI values in Constants file")

> Note: The REST API Explorer project declares the required dependencies using Gradle. The dependencies are:
> * The [Azure Active Directory Authentication Library for Android](https://github.com/AzureAD/azure-activedirectory-library-for-android).
> * The [Office 365 SDK for Android](https://github.com/OfficeDev/Office-365-SDK-for-Android).
> * The [Google Core Libraries for Java 18.0 API](https://code.google.com/p/guava-libraries/).
> * [Jake Wharton - butterknife](https://github.com/JakeWharton/butterknife)
> * [Jake Wharton - timber](https://github.com/JakeWharton/timber) 
> * [Square - Dagger](https://github.com/square/dagger)
> * [Square -retrofit](https://github.com/square/retrofit)
> * [Square - Okhttp](https://github.com/square/okhttp)

##Run the project
Once you've built the REST explorer project you can run it on an emulator or device.

1. Run the project
2. Click the authentication account that you want to sign in to
3. Enter your credentials
4. Click a REST operation in the main activity to show operation details
5. Choose a notebook, section, or page from the spinner below the run button if the spinner is shown on the page
6. Enter a text value in the text box below the run button if the text box is shown on the page.
7. Click the run button to start the REST operation and wait for the operation to finish.
8. Click the mouse in the Response Headers or Response Body text boxes to copy the box contents to the emulator/device clipboard.
9. Press the Back button on the REST Explorer toolbar to return to the REST operation list.
10. (Optional) Click the overflow menu to get the Disconnect menu option.

##Understand the code
The REST API Explorer project uses these classes to manage interactions with OneNote for Enterprise and consumer OneNote:
#Snippet classes

* [NotebookSnippet](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/snippet/NotebookSnippet.java)
* [SectionGroupSnippet](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/snippet/SectionGroupSnippet.java)
* [SectionSnippet](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/snippet/SectionSnippet.java)
* [PagesSnippet](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/snippet/PagesSnippet.java)
* [AbstractSnippet](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/snippet/AbstractSnippet.java)

These classes set the state required to make the calls on the OneNote service classes described below. Where necessary, a snippet class gets the notebooks, sections, or pages to load the spinner control shown on the snippet detail fragment for a given REST operation.

#OneNote service classes
These classes make the Retrofit library calls that generate the REST queries and handle operation results.
* [NotebooksService](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenoteapi/src/main/java/com/microsoft/onenoteapi/service/NotebooksService.java)
* [SectionGroupsService](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenoteapi/src/main/java/com/microsoft/onenoteapi/service/SectionGroupsService.java)
* []()
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

###Prerequisites
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
###Sample project organization
The REST API explorer project is comprised of four modules. The modular design allows you to easily add authentication and OneNote REST API support to your app by importing modules from REST API Explorer into your app. After you've imported our modules, use the code in the REST API Explorer [app](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/app) module as an example of how to call methods in the other sample modules.
###REST API Explorer modules
* [O365-Auth](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/O365-auth). This module contains the library calls to authenticate a user with Office 365.
* [onenoteapi](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/onenoteapi). This module encapsulates the Retrofit REST operations against the OneNote (enterprise and consumer) endpoints.
* [onenotevos](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/onenotevos). This module provides the value objects that wrap deserialized json REST response payloads. Use the value objects in your app logic to get the metadata and content of OneDrive notebooks, sections, and pages you get with the API.
* [app](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/app). The REST API Explorer UI and business logic module. REST API Explorer consumes the api and vo modules from the logic in the app module. REST operations are started in the snippet classes in this module.

###Snippet classes
These business logic classes are found in the [app](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/app) module. The snippet logic sets the state required to make the calls on the OneNote service classes described below. Where necessary, a snippet class gets the notebooks, sections, or pages to load the spinner control shown on the snippet detail fragment for a given REST operation.
* [NotebookSnippet](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/snippet/NotebookSnippet.java)
* [SectionGroupSnippet](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/snippet/SectionGroupSnippet.java)
* [SectionSnippet](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/snippet/SectionSnippet.java)
* [PagesSnippet](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/snippet/PagesSnippet.java)
* [AbstractSnippet](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/snippet/AbstractSnippet.java)



###OneNote API service classes
These classes are found in the [onenoteapi](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/onenoteapi) module and make the Retrofit library calls that generate the REST queries and handle operation results. These service classes are consumed by the snippets.
* [NotebooksService](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenoteapi/src/main/java/com/microsoft/onenoteapi/service/NotebooksService.java)
* [SectionGroupsService](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenoteapi/src/main/java/com/microsoft/onenoteapi/service/SectionGroupsService.java)
* [SectionsService](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenoteapi/src/main/java/com/microsoft/onenoteapi/service/SectionsService.java)
* [PagesService](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenoteapi/src/main/java/com/microsoft/onenoteapi/service/PagesService.java)

###Value object classes
These classes are found in the [onenotevos](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/onenotevos) module. The value object classes describe json payloads as managed objects.

* [BaseVO](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/BaseVO.java). The superclass for other value objects. 
* [Envelope](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/Envelope.java). A collection of individual notebook, section, section group, or page ojbects returned in  GET request.
* [Links](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/Links.java). The collection of URLs returned in the body of a notebook, section, or page.
* [Notebook](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/Notebook.java). A OneNote notebook.
* [Page](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/Page.java). A OneNote page.
* [Section](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/Section.java). A OnenNote section.
* [SectionGroup](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/SectionGroup.java). A OneNote section group.

###Authentication classes
The authentication classes are found in the [O365-Auth](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/O365-auth) module. The application logic calls into these classes to conect a user to Office 365 or OneDrive for consumer.

* [AuthenticationManager](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/O365-auth/src/main/java/com/microsoft/AuthenticationManager.java). Encapsulates user connect and disconnect logic in addition to Azure app authorization.
* [AzureADModule](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/O365-auth/src/main/java/com/microsoft/AzureADModule.java). Authentication helper class. 
* [AzureAppCompatActivity](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/O365-auth/src/main/java/com/microsoft/AzureAppCompatActivity.java). Dependency injection helper.

### Connect to Office 365

The OneNote API for Android uses the Azure Active Directory Library (ADAL) for Android for connecting your app to Office 365. The ADAL provides protocol support for OAuth2, Web API integration with user level consent, and two-factor authentication. The REST API Explorer uses the ADAL library to authenticate a user who wants to access OneNote for enterprise notebooks.

The **AuthenticationController** object manages getting a token from ADAL and returning it to your application.

### Connect to a OneDrive with a Microsoft account
The OneNote API for Android uses the Live-auth library for Android to connect your app to consumer OneDrive. The REST API Explorer use the Live-auth library to authenticate a user who wants to access OneNote for consumer notebooks.

## Aplication logic flow
1. **Authentication**. The main activity supports app authentication through the [BaseActivity](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/BaseActivity.java). BaseActivity initiates the dependency injection used throughout the sample. It also builds the ADAL object responsible for Office 365 authentication. 
2. **REST operation list initialization**. [SnippetListFragment](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/SnippetListFragment.java). This class tips up a new [SnippetListAdapter](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/SnippetListAdapter.java) that calls into a static [SnippetContent](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/snippet/SnippetContent.java) class for a collection of snippets to show.
3. **REST operation detail selected**. The [SnippetDetaiFragment](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/SnippetDetailFragment.java) shows the name and description of a REST operation and lets the user run the operation. If the operation needs context like the notebook parent of a new section, The detail fragment calls an asynchronous setup() method on the snippet. A spinner is populated with a collection of notebooks or sections and the Run button is enabled in a callback invoked by the setup method.
4. **Run button clicked**. The request() method is called on the snippet with the  [OneNote serivce](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/onenoteapi/src/main/java/com/microsoft/onenoteapi/service) to query and a reference to the object implementing the callback.
5. **Make the REST query**. The snippet request() method takes the arguments passed and uses the callback params to get any contextual objects (notebooks or sections) selected by a user. The appropriate method is called on the passed service.
5. **Handle the callback**. The [SnippetDetailFragment](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/app/src/main/java/com/microsoft/o365_android_onenote_rest/SnippetDetailFragment.java) handles the callback to get the REST query created by the service, the query headers, and the response body.
<a name="resources"/>
## Additional resources

* [OneNote APIs documentation](https://msdn.microsoft.com/en-us/library/office/dn575420.aspx)
* [OneNote developer center](http://dev.onenote.com/)
* [Microsoft Office 365 API Tools](https://visualstudiogallery.msdn.microsoft.com/a15b85e6-69a7-4fdf-adda-a38066bb5155)
* [Office Dev Center](http://dev.office.com/)
* [Office 365 APIs starter projects and code samples](http://msdn.microsoft.com/en-us/office/office365/howto/starter-projects-and-code-samples)


## Copyright
Copyright (c) 2015 Microsoft. All rights reserved.


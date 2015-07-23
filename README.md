# Android-REST-API-Explorer
[![Build Status](https://travis-ci.org/OneNoteDev/Android-REST-API-Explorer.svg?branch=master)](https://travis-ci.org/OneNoteDev/Android-REST-API-Explorer)

**Table of contents**

* [Change History](#change-history)
* [Device requirements](#device-requirements)
* [Prerequisites](#prerequisites)
* [Azure client application registration](#Azure-client-application-registration)
* [Microsoft account application registration](#Microsoft-account-application-registration)
* [Configure the project](#configure-the-project)
* [Run the project](#run-the-project)
* [Understand the code](#understand-the-code)
* [Contributing](#contributing)
* [Questions and comments](#questions-and-comments)
* [Additional resources](#additional-resources)

Looking to build cool apps that help people work with their OneNote notebooks? Explore, learn, and understand the OneNote REST APIs by using the OneNote REST API Explorer for Android. This sample lets you view and run the REST APIs that read, add, update, and delete OneNote entities such as notebooks, section groups, sections, and pages. The app lets you authenticate in two ways. You can authenticate using a Microsoft Account (MSA) to connect to OneNote on your personal OneDrive. Or you can authenticate using a work or school account to connect to OneNote on either your organization's OneDrive or SharePoint site on Office 365.

You can explore the following operations for OneNote:

**Notebook**

* Get a list of your notebooks
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
To run the REST Explorer project, your device must meet the following requirements:
* Android API level 16 or newer

###Prerequisites
To use the OneNote REST API Explorer for Android, you need the following:
* The latest version of [Android Studio](http://developer.android.com/sdk/index.html).
* the [Gradle](http://www.gradle.org) build automation system version 2.2.1 or later.
* An Office 365 account. You can sign up for [an Office 365 Developer subscription](https://portal.office.com/Signup/Signup.aspx?OfferId=C69E7747-2566-4897-8CBA-B998ED3BAB88&DL=DEVELOPERPACK&ali=1#0) that includes the resources you need to start building Office 365 apps.
* [Java Development Kit (JDK) 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html).
* A registered Azure application with a client id and redirect URI value. The application must have the following permissions:
    * View and modify OneNote notebooks in your organization
    * View and modify OneNote notebooks
    * Create pages in OneNote notebooks
    * Sign you in and read your profile
    * Access your organization's directory
* A registered Microsoft application with a client id.
    
##Azure client application registration
1.	Sign in to the [Azure Management Portal](https://manage.windowsazure.com), using your Azure AD credentials.
2.	Click **Active Directory** on the left menu, then select the directory for your Office 365 developer site.
3.	On the top menu, click **Applications**.
4.	Click **Add** from the bottom menu.
5.	On the **What do you want to do page**, click **Add an application my organization is developing**.
6.	On the **Tell us about your application page**, specify **OneNote REST API Explorer** for the application name and select **NATIVE CLIENT APPLICATION** for type.
7.	Click the arrow icon on the bottom-right corner of the page.
8.	On the **Application information** page, specify a **Redirect URI**, for this example, you can specify http://localhost/OneNoteRESTExplorer, and then select the checkbox in the lower-right hand corner of the page. Remember this value for the below section **Getting the ClientID and RedirectUri into the project**.
9.	Once the application has been successfully added, you will be taken to the **Quick Start** page for the application. From here, select **Configure** in the top menu.
10.	Under **permissions to other applications**, select **Add application.** Select OneNote and then the check box to proceed.
11.	For the **OneNote** application add the following permissions:
    * View and modify OneNote notebooks in your organization
    * View and modify OneNote notebooks
    * Create pages in OneNote notebooks
![](/readme-images/OneNotePermissions.jpg)

12. For the **Windows Azure Active Directory** application add or make sure the following permissions are enabled:
	* Enable sign-on and read users' profiles
    * Access your organization's directory
![](/readme-images/AADPermissions.jpg)

13.	Copy the value specified for **Client ID** on the **Configure** page. Remember this value for the below section **Getting the ClientID and RedirectUri into the project**.
14.	Click **Save** in the bottom menu.


##Microsoft account application registration
1. Go to the [Windows Live application management site](http://go.microsoft.com/fwlink/?LinkID=144070).

2. Sign in by using your Windows Live ID.
	>Note: If this is your first visit to this site, you will see several pages that configure your Windows Live ID for use with the site. 

3. Click **Create application**.
4. Enter a unique application name. If you agree to the terms, click **Accept**.
5. There are many settings for your app, but to make it work with this sample, you need to make the following changes:
	1. On **API Settings**, set the **Mobile or desktop client app** field to **Yes**.
	2. On **API Settings**, set the **Redirect URLs** field to a valid URL. The URL does not need to reference an actual location, but it must be a valid URL.
3. Once you are satisfied with your app settings, click **Save**.
4. Click **App Settings**. This will display a page with your client id. You will use this value later when configuring your project.

##Configure the project

1. Download or clone the [OneNote REST API Explorer for Android](https://github.com/OneNoteDev/Android-REST-API-Explorer).
2. Start Android Studio.
3. From the **Welcome to Android Studio** dialog box, choose **Import project (Eclipse ADT, Gradle, etc)**.
4. Select the **settings.gradle** file in the **Android-REST-API-Explorer** folder and click **OK**.
5. Respond to the dialog ("Gradle Sync: Gradle settings for this project are not configured yet. Would you like the project to use the Gradle wrapper? ") by clicking the **OK** button to use the Gradle wrapper. 
6. Open the ServiceConstants.java file in the com.microsoft.o365_android_onenote_rest.conf package.
7. Find the CLIENT_ID string and set its value to the client id you registered in Azure.
8. Find the REDIRECT_URI string and set its value to the redirect URI you registered in Azure.
9. Find the MSA_CLIENT_ID string and set its value to the client id you registered for your app in your Microsoft Account.

##Run the project
Once you've built the REST Explorer project you can run it on an emulator or device.

1. Run the project.
2. Click the authentication account that you want to sign in to.
3. Enter your credentials.
4. Click a REST operation in the main activity to show operation details.
	>Note: Some operations require input before they will run. For example, to update a page, you must first select a page to update. On these operations there will be a spinner, or text box, to select or enter required input for an operation.

7. Click the run button to start the REST operation and wait for the operation to finish.
8. Click in the Response Headers or Response Body text boxes to copy the box contents to the emulator/device clipboard.
9. Click the Back button on the REST Explorer toolbar to return to the REST operation list.
10. (Optional) Click the overflow menu to get the Disconnect menu option.

##Understand the code
The REST API Explorer project uses these classes to manage interactions with OneNote for Enterprise and consumer OneNote:
###Sample project organization
The REST API explorer project is comprised of four modules. The modular design allows you to add authentication and OneNote REST API support to your app by importing modules from REST API Explorer into your app. After you've imported the modules, use the code in the REST API Explorer [app](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/app) module as an example of how to call methods in the other sample modules.
###REST API Explorer modules
* [O365-Auth](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/O365-auth). This module contains the library calls to authenticate a user with Office 365.
* [onenoteapi](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/onenoteapi). This module encapsulates the Retrofit REST operations used for the OneNote (enterprise and consumer) endpoints.
* [onenotevos](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/onenotevos). This module provides the value objects that wrap deserialized JSON REST response payloads. Use the value objects in your app logic to get the metadata and content of OneDrive notebooks, sections, and pages returned by the API.
* [app](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/app). The REST API explorer UI and business logic module. REST API Explorer consumes the **api** and **vo** modules from the logic in the app module. REST operations are started in the snippet classes in this module.

###Snippet classes
A snippet runs a single REST operation and returns the results. Snippets are found in the [app](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/app) module. Snippets set the state required to make the calls on the OneNote service classes described below. Where necessary, a snippet class gets the notebooks, sections, or pages to load the spinner control shown on the snippet detail fragment for a given REST operation.
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
These classes are found in the [onenotevos](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/onenotevos) module. The value object classes describe JSON payloads as objects.

* [BaseVO](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/BaseVO.java). The superclass for other value objects. 
* [Envelope](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/Envelope.java). A collection of individual notebook, section, section group, or page objects returned in  GET request.
* [Links](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/Links.java). The collection of URLs returned in the body of a notebook, section, or page.
* [Notebook](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/Notebook.java). A OneNote notebook.
* [Page](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/Page.java). A OneNote page.
* [Section](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/Section.java). A OneNote section.
* [SectionGroup](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/onenotevos/src/main/java/com/microsoft/onenotevos/SectionGroup.java). A OneNote section group.

###Authentication classes for Office 365 business accounts
The authentication classes are found in the [O365-Auth](https://github.com/OneNoteDev/Android-REST-API-Explorer/tree/master/O365-auth) module. These classes use the [Microsoft Azure Active Directory Library (ADAL) for Android](https://github.com/AzureAD/azure-activedirectory-library-for-android) to connect to a business version of Office 365 such as Office 365 Enterprise. 

* [AuthenticationManager](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/O365-auth/src/main/java/com/microsoft/AuthenticationManager.java). Encapsulates user connect and disconnect logic in addition to Azure app authorization.
* [AzureADModule](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/O365-auth/src/main/java/com/microsoft/AzureADModule.java). Authentication helper class. 
* [AzureAppCompatActivity](https://github.com/OneNoteDev/Android-REST-API-Explorer/blob/master/O365-auth/src/main/java/com/microsoft/AzureAppCompatActivity.java). Dependency injection helper.

### Authentication for Office 365 personal accounts
Authentication for logging in with a Microsoft Account to a personal version of Office 365 such as Office 365 Home is handled by the [MSA Auth for Android](https://github.com/MSOpenTech/msa-auth-for-android) library. The app uses the [LiveAuthClient](https://github.com/MSOpenTech/msa-auth-for-android/blob/dev/src/main/java/com/microsoft/services/msa/LiveAuthClient.java) class to connect and disconnect.

## Questions and comments
We'd love to get your feedback about the OneNote REST API Explorer for Android sample. You can send your feedback to us in the [Issues](https://github.com/OneNoteDev/Android-REST-API-Explorer/issues) section of this repository. <br/>
General questions about Office 365 development should be posted to [Stack Overflow](http://stackoverflow.com/questions/tagged/Office365+API). Make sure that your questions are tagged with [Office365] and [API].

## Contributing
You will need to sign a [Contributor License Agreement](https://cla.microsoft.com/) before submitting your pull request. To complete the Contributor License Agreement (CLA), you will need to submit a request via the form and then electronically sign the CLA when you receive the email containing the link to the document. 

## Additional resources

* [OneNote APIs documentation](https://msdn.microsoft.com/en-us/library/office/dn575420.aspx)
* [OneNote developer center](http://dev.onenote.com/)
* [Microsoft Office 365 API Tools](https://visualstudiogallery.msdn.microsoft.com/a15b85e6-69a7-4fdf-adda-a38066bb5155)
* [Office Dev Center](http://dev.office.com/)
* [Office 365 APIs starter projects and code samples](http://msdn.microsoft.com/en-us/office/office365/howto/starter-projects-and-code-samples)


## Copyright
Copyright (c) 2015 Microsoft. All rights reserved.

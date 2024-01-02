# Filmland Backend
This is a GitHub page detailing the technical case assessment I wrote for Sogeti, which involves some, but not all, CRUD operations. It is a restful API following the MVC pattern. It makes use of Spring Boot with Spring Web, Data, Security, Mail and Quartz integration. The idea is simple enough: handle login, handle subscriptions and handle subscription billing. It makes use of a h2 database and quartz uses ram storage.

## Elements
Currently, the user can:
- register with a 'username' (email) and password.
- login using said credentials
- They can get the categories of subscription that are linked to their account
- And the ones that they can subscribe to
- They can subscribe to a category
- and a subscription can be shared with multiple users

## Important to note!

Currently, the app makes use of java mail with gmail smtp. This requires credentials in the form of a valid gmail address and an app password. These credentials are stored in the GMAIL and GMAIL_PASSWORD environment variables. Be sure to either make environment variables with these values or simply replace them with valid values.
You can also contact me for credentials.

Here's how you can create and use app passwords:

1.  Go to your Google Account.
2.  Select the "Security" section.
3.  Under "Signing in to Google," click on "2-Step Verification."
4.  At the bottom of the page, find and click on "App passwords."
5.  Provide a name that helps you remember where you intend to use the app password.
6.  Click "Generate."
7.  To use the app password, follow the instructions on your screen. The app password is the 16-character code generated on your device.
8.  Click "Done."

## To-do

- [ ] Add more tests!

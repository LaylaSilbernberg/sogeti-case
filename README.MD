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

## Endpoints

The current endpoints are detailed below along with any required bodies.

- POST "/api/register" for registration.
  - Requires a body with the following format:
    ```json
    {
    "username": "moonwasyellow@nightwasyoung.com",
    "password": "JavaIsCool!!!111"
    }
    ```
- POST "/api/login" for login
  - Requires a body with the following format:
      ```json{
    "username": "moonwasyellow@nightwasyoung.com",
    "password": "JavaIsCool!!!111"
    }
    ```
  - Naturally, username and password have to exist.
  - Sends back an authorization header that needs to be sent in for every single request that doesn't involve login or registration.
    - Holds a jwt
- GET "/api/subscriptions" for available and running subscriptions
  - Requires correct authorization header
  - Requires just the username sent in in json, as follows:
  ```json
  "moonwasyellow@nightwasyoung.com"
  ```
- POST "/api/subscriptions" to subscribe to a category.
  - Requires correct authorization header
  - Requires the username and category in json as follows:
  ```json
  {
    "username":"moonwasyellow@nightwasyoung.com",
    "category": "DUTCH_FILMS"
  }
  ```
  - Current categories are: DUTCH_FILMS, DUTCH_SERIES, INTERNATIONAL_FILMS
- PATCH "/api/subscriptions" to share a subscription.
  - Requires correct authorization header
  - Requires the username of the account that is sharing, the username of the account being shared with and the subscription in json as follows:
  ```json
  {
    "email": "moonwasyellow@nightwasyoung.com",
    "customer":"client@filmland-assessment.nl",
    "subscribedCategory": "DUTCH_FILMS"
  }
  ```
  - all emails involved must belong to registered accounts.
## To-do

- [ ] Add more tests!

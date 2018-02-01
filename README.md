# bui1-SubBook
App that manage user's monthly subscriptions (or expenses in general).

Consider the situation of someone who needs to manage their monthly subscriptions (or expenses in general). What is needed is a simple, attractive, intuitive, mobile app to track such monthly subscriptions. Let us call this app: SubBook.

Specifically, each subscription has a record with the following fields:

- name (textual, up to 20 characters)
- date started (presented yyyy-mm-dd format)
- monthly charge (non-negative currency value, in Canadian dollars)
- comment (textual, up to 30 characters)

Only the comment field might be left blank for a subscription.

The app should allow the user to:

- show a list of subscriptions, along with a summary of the total monthly charge for all
- add a new subscription (which always appends to the bottom end of the list)
- view and edit the details of an existing subscription
- delete an existing subscription

The list need not show all the information for a subscription if space is limited. Minimally, each record in the list should show the name, date, and monthly charge.

The app must assist the user in proper data entry. For example, use appropriate user interface controls to enforce particular data types and avoid illegal values.

The app must be persistent. That is, exiting and fully stopping the app should not lose data.

https://www.youtube.com/watch?v=sLtIzMTp_LE&feature=youtu.be

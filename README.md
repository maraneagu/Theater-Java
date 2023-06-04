# Theater-Java

Project implemented in the Advanced Oriented Programming course of my fourth semester at FMI. Managing a theater, in two parts: visiting the theater itself, adding, removing and listing different components of it and visiting the theater's ticket shop, where you are able to buy and cancel a ticket for an event from the theater's repertoire.

### Database / Workbench

To stock the data of this application, I've used MySQL, writing the SQL code in MySQL Workbench. Here are the SQL commands for creating the actual tables: [MySQL Theater Commands.](https://github.com/maraneagu/Theater-Java/blob/main/MySQLTheaterCommands.txt)

### Sign up / Log in

The first step when you start the application is the sign up/log in part. Here you can see how I logged in with the username @admin, who is able to add and remove componenets in the application:

:performing_arts:


![2023-05-21 (9)](https://github.com/maraneagu/Theater-Java/assets/93272424/9b71e0ff-dde8-4fa3-8496-c63ecf67e778)
:dizzy:

### Theater / Ticket Shop

After you are successfully logged in, the next step is to choose which part of the application do you want to visit. You can either:
- Visit the theater;
- Visit the theater's ticket shop;
- Log off your current account.

:performing_arts:


![2023-05-21 (10)](https://github.com/maraneagu/Theater-Java/assets/93272424/51d0cf35-e683-46e2-a955-b28f35384ac7)
:dizzy:

### Theater

I am first going to walk you through the Theater part of the application, where, logged as @admin, you are able to create the theater itself, adding, removing and listing spectacles, actors, dancers, singers and events.

:performing_arts:


![2023-05-21 (2)](https://github.com/maraneagu/Theater-Java/assets/93272424/585f331f-ddbf-4070-908e-96b48c3a99dd)
![2023-05-21 (6)](https://github.com/maraneagu/Theater-Java/assets/93272424/cf253792-943b-4e7a-87b7-eec94fb364a9)
:dizzy:

### Ticket Shop

The theater's ticket shop is the same for both @admin and regular users: you can buy, cancel and list tickets of the theater's events.

:performing_arts:


![2023-05-21 (11)](https://github.com/maraneagu/Theater-Java/assets/93272424/3c3966fd-d047-4708-943c-55a7e5e08ef6)
:dizzy:

### Audit

This application has an audit section, that writes a message, with the current date and hour in its specific audit file. There exists an audit file for the theater's:
- users;
- spectacles;
- categories;
- directors;
- artists;
- events;
- tickets.

Here is an example of the theaterUsers.cvs audit file, after I logged into the application as @admin, then switched to the user @maraneagu, and finally changing the user's name from Mara to Tedora:

:performing_arts:


![2023-05-21 (15)](https://github.com/maraneagu/Theater-Java/assets/93272424/4a673f8e-b235-4bfa-868f-dbbe2e795d51)
:dizzy:






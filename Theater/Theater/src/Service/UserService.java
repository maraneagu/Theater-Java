package Service;

import Audit.Audit;
import Repository.UserRepository;
import Theater.Theater;
import Theater.User;
import Exception.*;
import java.util.List;
import java.util.Scanner;

public class UserService {
    public UserService() {}

    public void signUpOrLogIn(List<User> users, User user) {
        System.out.println("\uF0AB Welcome to The Moonlight Theater! \uF0AB\n");
        System.out.println("Your options:");
        System.out.println("\uF0B2");

        System.out.println("1). Sign up.");
        System.out.println("2). Log in.");

        Scanner in = new Scanner(System.in);
        while (true)
        {
            try
            {
                System.out.print("\nWhich option do you choose? Option: ");
                String optionChoice = in.nextLine().trim();

                if (optionChoice.equals("1"))
                {
                    System.out.println("\uF046 The option you chose: sign up.");
                    signUp(users);
                    break;
                }
                else if (optionChoice.equals("2"))
                {
                    System.out.println("\uF046 The option you chose: log in.");
                    logIn(users);
                    break;
                }
                else throw new InvalidOptionException();
            }
            catch (InvalidOptionException exception)
            {
                System.out.println(exception.getMessage());
            }
        }

    }

    public void signUp(List<User> users)
    {
        Scanner in = new Scanner(System.in);
        String username;
        String email;
        String password;
        String name;

        System.out.println("\n\uF0B2 Sign up \uF0B2\n");

        while (true)
        {
            try
            {
                System.out.print("Enter your username: ");
                username = in.nextLine().trim();

                boolean usernameExists = false;
                for (User u : users)
                    if (u.getUsername().equalsIgnoreCase(username))
                    {
                        usernameExists = true;
                        break;
                    }

                if (!usernameExists)
                    break;
                else throw new UsernameFoundException();
            }
            catch (UsernameFoundException exception)
            {
                System.out.println(exception.getMessage());
            }
        }

        while (true)
        {
            System.out.println("\nThe email of the profile! The format: name@email.com!");
            System.out.print("Enter your email: ");
            email = in.nextLine().trim();

            if (email.length() < 10 || !email.substring(email.length() - 10, email.length()).equals("@email.com"))
                System.out.println("\uF0FB The email format you introduced is not valid! Please try again! \uF0FB");
            break;
        }

        System.out.print("\nEnter your password: ");
        password = in.nextLine().trim();

        System.out.print("\nEnter the name of your profile: ");
        name = in.nextLine().trim();

        User user = new User(username, email, password, name);
        users.add(user);
        UserRepository userRepository = UserRepository.getInstance();
        userRepository.insertUser(user);

        Audit audit = Audit.getInstance();
        audit.writeToFile("A new user has signed up with the username: @" + username);

        System.out.println("\n\uF0AB The sign up was done successfully! One more step before you can visit The Moonlight Theater! \uF0AB");
        logIn(users);
    }

    public void logIn(List<User> users) {
        Scanner in = new Scanner(System.in);
        String username = "";
        String password;

        System.out.println("\n\uF0B2 Log in \uF0B2");

        while (true)
        {
            try
            {
                System.out.print("\nEnter your username: ");
                username = in.nextLine().trim();

                boolean userExists = false;

                for (User u : users)
                    if (u.getUsername().equalsIgnoreCase(username))
                    {
                        while (true)
                        {
                            System.out.print("Enter your password: ");
                            password = in.nextLine().trim();

                            if (u.getPassword().equals(password))
                            {
                                userExists = true;
                                break;
                            }
                            else System.out.println("\n\uF0FB The password you introduced is not valid! Please try again! \uF0FB\n");
                        }

                        Theater theater = Theater.getInstance();
                        theater.setUser(u);
                        break;
                    }

                if (userExists)
                {
                    Audit audit = Audit.getInstance();
                    audit.writeToFile("The user @" + username + " has logged in!");

                    System.out.println("\n\uF0AB The log in was done successfully! Let your journey through The Moonlight Theater begin! \uF0AB");
                    break;
                }
                else throw new UsernameNotFoundException();
            }
            catch (UsernameNotFoundException exception)
            {
                System.out.println(exception.getMessage());

                System.out.println("Do you want to sign up with the username @" + username + "? yes / no");
                String signUpChoice = in.nextLine().trim();

                if (signUpChoice.equalsIgnoreCase("yes"))
                    signUp(users);
            }
        }
    }

    public void updateUser() {
        Scanner in = new Scanner(System.in);
        Theater theater = Theater.getInstance();

        String username = theater.getUser().getUsername();
        String email = theater.getUser().getEmail();
        String password = theater.getUser().getPassword();
        String name = theater.getUser().getName();
        String optionChoice;
        Audit audit = Audit.getInstance();

        System.out.println("Which part of your profile do you want to change?");
        System.out.println("1. The username.");
        System.out.println("2. The email.");
        System.out.println("3. The password.");
        System.out.println("4. The name.");

        while (true)
        {
            try
            {
                System.out.print("\nEnter the number of your option: ");
                optionChoice = in.nextLine().trim();

                if (optionChoice.compareTo("1") >= 0 && optionChoice.compareTo("4") <= 0)
                    break;
                else throw new InvalidOptionException();
            }
            catch (InvalidOptionException exception)
            {
                System.out.println(exception.getMessage());
            }
        }

        if (optionChoice.equalsIgnoreCase("1"))
        {
            List<User> users = theater.getUsers();

            System.out.println("Your previous username: @" + theater.getUser().getUsername() + "\n");

            while (true)
            {
                try
                {
                    System.out.print("Enter your new username: ");
                    username = in.nextLine().trim();

                    boolean usernameExists = false;
                    for (User u : users)
                        if (u.getUsername().equalsIgnoreCase(username))
                        {
                            usernameExists = true;
                            break;
                        }

                    if (!usernameExists)
                        break;
                    else throw new UsernameFoundException();
                }
                catch (UsernameFoundException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            audit.writeToFile("The @" + theater.getUser().getUsername() + "'s username has been changed to: @" + username);
        }
        else if (optionChoice.equalsIgnoreCase("2"))
        {
            System.out.println("Your previous email: " + theater.getUser().getEmail());

            while (true)
            {
                System.out.println("\nThe email of the profile! The format: name@email.com!");
                System.out.print("Enter your new email: ");
                email = in.nextLine().trim();

                if (email.length() < 10 || !email.substring(email.length() - 10, email.length()).equals("@email.com"))
                    System.out.println("\uF0FB The email format you introduced is not valid! Please try again! \uF0FB");
                break;
            }

            audit.writeToFile("The @" + theater.getUser().getUsername() + "'s email has been changed to: " + email);
        }
        else if (optionChoice.equalsIgnoreCase("3"))
        {
            System.out.println("Your previous password: " + theater.getUser().getPassword() + "\n");

            System.out.print("Enter your new password: ");
            password = in.nextLine().trim();

            audit.writeToFile("The @" + theater.getUser().getUsername() + "'s password has been changed to: " + password);
        }
        else if (optionChoice.equalsIgnoreCase("4"))
        {
            System.out.println("Your previous profile name: " + theater.getUser().getName() + "\n");

            System.out.print("Enter the new name of your profile: ");
            name = in.nextLine().trim();

            audit.writeToFile("The @" + theater.getUser().getUsername() + "'s name has been changed to: " + name);
        }

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.updateUser(username, email, password, name);

        theater.getUser().setUsername(username);
        theater.getUser().setEmail(email);
        theater.getUser().setPassword(password);
        theater.getUser().setName(name);

        System.out.println("\n\uF0AB The profile was changed successfully! \uF0AB\n");
    }

    public void logOff() {
        Theater theater = Theater.getInstance();
        Audit audit = Audit.getInstance();

        audit.writeToFile("The @" + theater.getUser().getUsername() + " has logged off!");
        theater.start();
    }
}

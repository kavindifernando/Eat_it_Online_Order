package com.example.eat_it.Prevalent;

public class CurrentDriver {
    private static String PhoneNumb;
    private static String password;
    private static String Name;
    private static String Email;

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String email) {
        Email = email;
    }

    public CurrentDriver() {
    }

    public static String getPhoneNumb() {
        return PhoneNumb;
    }

    public static void setPhoneNumb(String phoneNumb) {
        PhoneNumb = phoneNumb;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CurrentDriver.password = password;
    }

    public static String getName() {
        return Name;
    }

    public static void setName(String name) {
        Name = name;
    }
}


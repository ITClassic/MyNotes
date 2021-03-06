package com.alex.mylibrary.model;

import java.util.ArrayList;

/**
 * Created by Alex on 17.12.2016.
 */

public class Person {

    private String mName;
    private String mSurName;

    public Person(String name, String surName) {
        mName = name;
        mSurName = surName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getSurName() {
        return mSurName;
    }

    public void setSurName(String mSurName) {
        this.mSurName = mSurName;
    }

    public static ArrayList<Person> createPersonList(int length) {
        ArrayList<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < length; i++) {
            Person person = new Person("PersonName " + (i + 1), "PersonSurname" + (i + 1));
            persons.add(i, person);
        }

        return persons;
    }
}

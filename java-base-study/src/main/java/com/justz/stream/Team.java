package com.justz.stream;

import java.util.List;

/**
 * Team
 */
public class Team {

    private List<People> peopleList;

    public Team() {}

    public Team(List<People> peopleList) {
        this.peopleList = peopleList;
    }

    public List<People> getPeopleList() {
        return peopleList;
    }

}

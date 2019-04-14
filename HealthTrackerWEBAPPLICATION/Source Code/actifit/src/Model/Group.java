package Model;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private int id;
    private String name;
    private List<User> users;

    /**
     * Constructor of a Group.
     */
    public Group(int id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();
    }

    /**
     * Method to get the id of a group.
     *
     * @return the id of the group
     */
    public int getId() {
        return id;
    }

    /**
     * Method to get the name of a group.
     *
     * @return the name of the group
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set the name of a group.
     *
     * @param name the new name of the group
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to get a list of users in a group.
     *
     * @return list of users in the group
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Method to add a user to a group.
     *
     * @param user the user to add to the group
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Method to remove a user from a group.
     *
     * @param user the user to remove from the group
     */
    public void removeUser(User user) {
        users.remove(user);
    }
}

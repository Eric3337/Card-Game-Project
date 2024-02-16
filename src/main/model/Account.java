package model;

public class Account {
    private static int nextAccId = 1;
    private int id;
    private String name;

    public Account(String accName) {
        id = nextAccId++;
        name = accName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

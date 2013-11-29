package ua.com.itinterview.jbehave;

import net.thucydides.jbehave.ThucydidesJUnitStories;

public class AcceptanceTestSuite extends ThucydidesJUnitStories
{
    public static final int PORT = 8080;
    public static final String ROOT_URL = "http://localhost:" + PORT + "/";
}

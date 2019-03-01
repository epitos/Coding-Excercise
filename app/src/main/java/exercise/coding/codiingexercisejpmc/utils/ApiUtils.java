package exercise.coding.codiingexercisejpmc.utils;

import exercise.coding.codiingexercisejpmc.network.Requests;
import exercise.coding.codiingexercisejpmc.network.RetrofitClient;

public class ApiUtils {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static Requests getRequests() {
        return RetrofitClient.getClient(BASE_URL).create(Requests.class);
    }
}

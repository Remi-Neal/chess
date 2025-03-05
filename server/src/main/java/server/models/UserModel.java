package server.models;

import com.google.gson.Gson;

public record UserModel(String username, String password, String email, String authToken) {
    public String toString(){
        return new Gson().toJson(this);
    }
}

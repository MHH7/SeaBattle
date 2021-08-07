package Model.Requests;

import Model.Type;

public class Request {
    private final Type type;
    private final long authToken;
    private final String data;

    public Request(Type type,long authToken,String data){
        this.type = type;
        this.authToken = authToken;
        this.data = data;
    }

    public long getAuthToken() {
        return authToken;
    }

    public String getData() {
        return data;
    }

    public Type getType() {
        return type;
    }
}

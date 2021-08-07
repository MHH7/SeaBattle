package Model.Responses;

import Model.Type;

public class Response {
    private final Type type;
    private final String data;

    public Response(Type type,String data){
        this.type = type;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public Type getType() {
        return type;
    }
}

package io.torres.common.vo.response;

public class ResponseBuilder {

    private Response response;

    public ResponseBuilder(){
        response = new Response();
        response.put("code", "success");
        response.put("message", "성공");
    }

    public ResponseBuilder add(String key, Object val){
        response.put(key,val);
        return this;
    }

    public Response build(){
        return response;
    }
}

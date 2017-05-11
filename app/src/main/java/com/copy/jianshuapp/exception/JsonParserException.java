package com.copy.jianshuapp.exception;

import com.google.gson.JsonElement;

/**
 * JSON解析异常
 * @version imkarl 2017-05
 */
public class JsonParserException extends Exception {

    private JsonElement json;

    public JsonParserException(JsonElement json, String detailMessage) {
        super(detailMessage);
        this.json = json;
    }
    public JsonParserException(JsonElement json, Throwable throwable) {
        super(throwable);
        this.json = json;
    }
    public JsonParserException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getDescription() {
        return "JSON解析异常";
    }

    @Override
    public String toString() {
        return super.toString() + "\n\tjson: " + json;
    }

    public JsonElement json() {
        return json;
    }

}

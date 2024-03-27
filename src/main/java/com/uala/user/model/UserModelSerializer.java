package com.uala.user.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Set;

@JsonComponent
public class UserModelSerializer extends JsonSerializer<UserModel> {
    @Override
    public void serialize(UserModel userModel, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", userModel.getId());
        jsonGenerator.writeStringField("username", userModel.getUsername());
        jsonGenerator.writeStringField("password", userModel.getPassword());
        jsonGenerator.writeFieldName("following");
        jsonGenerator.writeStartArray();
        Set<UserModel> following = userModel.getFollowing();
        for (UserModel followedUser : following) {
            jsonGenerator.writeString(followedUser.getId());
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}


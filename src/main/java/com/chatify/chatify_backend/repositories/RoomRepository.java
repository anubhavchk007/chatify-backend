package com.chatify.chatify_backend.repositories;


import com.chatify.chatify_backend.entities.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {
    Room findRoomByRoomId(String roomId);
}

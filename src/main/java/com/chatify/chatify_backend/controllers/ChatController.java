package com.chatify.chatify_backend.controllers;

import com.chatify.chatify_backend.config.AppConstants;
import com.chatify.chatify_backend.entities.Message;
import com.chatify.chatify_backend.entities.Room;
import com.chatify.chatify_backend.payload.MessageRequest;
import com.chatify.chatify_backend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private RoomRepository roomRepository;

    @MessageMapping("/sendMessage/{roomId}") // /app/sendMessage/{roomId}
    @SendTo("/topic/room/{roomId}") // subscribe
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request
    ) {
        Room room = roomRepository.findRoomByRoomId(roomId);
        Message message = new Message(request.getSender(), request.getContent());

        if (room != null) {
            room.getMessages().add(message);
            roomRepository.save(room);
        } else {
            throw new RuntimeException("Room not found!");
        }

        return message;
    }
}

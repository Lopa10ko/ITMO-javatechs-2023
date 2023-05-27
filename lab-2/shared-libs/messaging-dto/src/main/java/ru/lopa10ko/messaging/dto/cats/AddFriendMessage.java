package ru.lopa10ko.messaging.dto.cats;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AddFriendMessage {
    UUID catId;
    UUID friendId;
    UUID ownerId;
}

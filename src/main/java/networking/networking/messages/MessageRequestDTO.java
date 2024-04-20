package networking.networking.messages;

import lombok.Data;

@Data
public class MessageRequestDTO {
    private Long senderId;
    private Long recipientId;
    private String content;
}

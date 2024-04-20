package networking.networking.messages;

import lombok.RequiredArgsConstructor;
import networking.networking.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public void sendMessage(User sender, User recipient, String content) {
        // TODO - Check if the 2 users are friends
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);
    }

    public List<Message> getChatMessages(User currentUser, User otherPerson) {
        return messageRepository.findBySenderAndRecipientOrSenderAndRecipientOrderByTimestamp(
                currentUser, otherPerson, otherPerson, currentUser);
    }

}

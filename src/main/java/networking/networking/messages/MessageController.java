package networking.networking.messages;

import lombok.RequiredArgsConstructor;
import networking.networking.exceptions.UserNotFoundException;
import networking.networking.user.User;
import networking.networking.user.UserRepository;
import networking.networking.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/messages/send") // TODO - change with proper mapping /user/messages ??
    public void sendMessage(@RequestBody MessageRequestDTO request) {
        Long senderId = request.getSenderId();
        Long recipientId = request.getRecipientId();
        try {

            userService.validateUserExist(senderId);
            userService.validateUserExist(recipientId);
        } catch (UserNotFoundException e) {
            return; // TODO - we need to show the user some errors.
        }

        User sender = userRepository.findById(senderId).get();
        User recipient = userRepository.findById(recipientId).get();
        messageService.sendMessage(sender, recipient, request.getContent());
    }
}

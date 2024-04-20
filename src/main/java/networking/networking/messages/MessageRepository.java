package networking.networking.messages;

import networking.networking.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndRecipientOrSenderAndRecipientOrderByTimestamp(
            User sender, User recipient);
}

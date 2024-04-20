package networking.networking.messages;

import networking.networking.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndRecipientOrSenderAndRecipientOrderByTimestamp(
            User sender1, User recipient1, User sender2, User recipient2);
}

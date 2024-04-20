package networking.networking.friendRequest;

import networking.networking.enums.RequestStatus;
import networking.networking.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Set<FriendRequest> findByRecipientAndStatus(User recipient, RequestStatus status);
    Optional<FriendRequest> findByRecipientAndSenderAndStatus(User recipient, User sender, RequestStatus status);

}

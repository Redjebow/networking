package networking.networking.friendRequest;

import networking.networking.enums.RequestStatus;
import networking.networking.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Set<FriendRequest> findByRecipientAndStatus(User recipient, RequestStatus status);

}

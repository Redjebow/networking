package networking.networking.friendRequest;

import jakarta.persistence.*;
import lombok.Data;
import networking.networking.enums.RequestStatus;
import networking.networking.user.User;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "friend_request")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RequestStatus status;

    private LocalDateTime createdAt;

}
